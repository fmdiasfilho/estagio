package data;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.weatherlibrary.datamodel.Hour;
import com.weatherlibraryjava.RequestBlocks;
import data.Enumerations.RequestTypes;
import data.weatherRepository.MyRepository;
import data.weatherRepository.historyRequests.HistoryWeatherModel;
import data.weatherRepository.historyRequests.MyWeatherModel;
import org.bson.Document;
import org.junit.Test;

import javax.naming.OperationNotSupportedException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class DatabaseManipulation {


    private static final String API_KEY = "d9b745b8828d4af9bba110610181904";
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private MyRepository r;
    private Gson gson;
    private DataBaseOperations db;
    private List<String> cities;
    private int counter;

    public DatabaseManipulation() throws Exception {
       r = new MyRepository();
       gson = new Gson();
       db = new DataBaseOperations();
       cities = new LinkedList<>();
    }

    public void addCity(String newCity){
        cities.add(newCity);
    }


    public Object updateWeather(RequestTypes type, String city, String date) throws Exception {
        String request = requestWeather(type, city, date);
        Object model = (date == null) ? gson.fromJson(request,MyWeatherModel.class) : gson.fromJson(request,HistoryWeatherModel.class);
        processRequestToDB(city,type,model);
        return model;
    }

    public Object getDocument(String city, RequestTypes type) throws Exception {
        String id = type.toString().toLowerCase();
        String doc = db.getDocument(city,id,"_id",id);
        Object result = (type.equals(RequestTypes.Current))? gson.fromJson(doc,MyWeatherModel.class) : gson.fromJson(doc,Hour.class);
        return result;
    }

    @Test
    public List<Hour> getAllDocs(String city, RequestTypes type){
        List<Document> list = db.getAllCollection(city,type.toString().toLowerCase());
        assertNotNull(list);
        List<Hour> result = translateDocuments(list);
        assertNotNull(result);
        return result;
    }

    private List<Hour> translateDocuments(List<Document> list) {
        List<Hour> result = new LinkedList<>();
        for(Document d : list){
            String temp = gson.toJson(d);
                Hour obj = gson.fromJson(temp,Hour.class);
                result.add(obj);
            }
        return result;
    }

    private String translateDocument(Document doc){
        return gson.toJson(doc);
    }


    private String requestWeather(RequestTypes type, String value, String date) throws Exception {
        if(!cities.contains(value)){
            addCity(value);
        }
        String request = "";
        if(type.equals(RequestTypes.Current))
            request = r.GetWeatherData(API_KEY, RequestBlocks.GetBy.CityName, value);
        else if(type.equals(RequestTypes.Today) || type.equals(RequestTypes.Yesterday)){
            request = r.GetWeatherDataByHistory(API_KEY,RequestBlocks.GetBy.CityName,value,date);
        }else{
            return null;
        }
       return request;
    }

    private void processRequestToDB(String database, RequestTypes type, Object model) {
        Document doc = null;
        String id = type.toString().toLowerCase();
        if(type.equals(RequestTypes.Current)){
            doc = Document.parse(gson.toJson(model));
            doc.append("_id", id);
            if(db.getDocument(database,id,"_id",id) == null){
                db.addDocument(database,id,doc);
            }else{
                db.updateDocument(database, id, "_id", id,doc);
            }

        }else{
            List<Hour> list = ((HistoryWeatherModel)model).getForecast().getForecastday().get(0).getHour();
            int rightNowHour = type.equals(RequestTypes.Today) ? Calendar.getInstance().get(Calendar.HOUR_OF_DAY) : list.size();
            for(Hour h: list){
                    if(counter < rightNowHour){
                    doc = Document.parse(gson.toJson(h));
                    doc.append("_id", id + counter);
                        if(db.getDocument(database,id,"_id",id + counter) == null){
                            db.addDocument(database,id,doc);
                        }else{
                            db.updateDocument(database, id, "_id", id + counter,doc);
                        }
                    counter++;
                    }else{
                        break;
                    }
            }
            counter = 0;
        }
    }

    @Test
    private void processAPIRequestTest() throws Exception {
        //Current weather to DB
        String currentRequest = requestWeather(RequestTypes.Current, "Lisbon", null);
        MyWeatherModel current = gson.fromJson(currentRequest, MyWeatherModel.class);
        assertNotNull(current);
        processRequestToDB("Lisbon", RequestTypes.Current, current);
        //Today weather to DB
        String todayRequest = requestWeather(RequestTypes.Today, "Lisbon", "2018-05-03");
        HistoryWeatherModel today = gson.fromJson(todayRequest, HistoryWeatherModel.class);
        assertNotNull(today);
        processRequestToDB("Lisbon",RequestTypes.Today, today);
        //Yesterday weather to DB
        String yesterdayRequest = requestWeather(RequestTypes.Yesterday,"Lisbon", "2018-05-02");
        HistoryWeatherModel yesterday = gson.fromJson(yesterdayRequest,HistoryWeatherModel.class);
        assertNotNull(yesterday);
        processRequestToDB("Lisbon", RequestTypes.Yesterday, yesterday);

        db.dropDatabase("Lisbon");
        assertTrue(db.listDB() == 2);
    }

    @Test
    private void updateWeatherTest() throws Exception {
        Object currentModel = updateWeather(RequestTypes.Current,"Lisbon", null);
        assertNotNull(currentModel);
        System.out.println(gson.toJson(currentModel));
        Object todayModel = updateWeather(RequestTypes.Today,"Lisbon", "2018-05-03");
        assertNotNull(todayModel);
        System.out.println(gson.toJson(todayModel));
        Object yesterdayModel = updateWeather(RequestTypes.Yesterday,"Lisbon", "2018-05-02");
        assertNotNull(yesterdayModel);
        System.out.println(gson.toJson(yesterdayModel));
    }
}
