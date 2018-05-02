package data;

import com.google.gson.Gson;
import com.mongodb.MongoClientURI;
import com.mongodb.*;
import com.weatherlibrary.datamodel.Forecast;
import com.weatherlibrary.datamodel.WeatherModel;
import com.weatherlibraryjava.RequestBlocks;
import data.Enumerations.RequestTypes;
import data.weatherRepository.MyRepository;
import data.weatherRepository.historyRequests.HistoryWeatherModel;
import data.weatherRepository.historyRequests.MyRequestBlocks;
import data.weatherRepository.historyRequests.MyWeatherModel;
import org.bson.Document;

import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;

public class DatabaseManipulation {


    private static final String API_KEY = "d9b745b8828d4af9bba110610181904";
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private MyRepository r;
    private Gson gson;
    private DataBaseOperations db;
    private int forecastHourCounter;
    private int historyHourCounter;
    private List<String> cities;

    public DatabaseManipulation(String city) throws Exception {
       r = new MyRepository();
       gson = new Gson();
       db = new DataBaseOperations();
       cities = new LinkedList<>();
       addCity(city);
       requestWeather(RequestTypes.Current,city,null,null);
    }

    public void addCity(String newCity){
        cities.add(newCity);
    }


    private void requestWeather(RequestTypes type, String value, String date ,RequestBlocks.Days ForecastOfDays) throws Exception {
        String request = "";
        String idValue = "";
        switch (type) {
            case Current:
                request = r.GetWeatherData(API_KEY, RequestBlocks.GetBy.CityName, value);
                idValue = "current";
                break;
            case Forecast:
                request = r.GetWeatherData(API_KEY, RequestBlocks.GetBy.CityName, value, ForecastOfDays);
                idValue = "forecast" + forecastHourCounter++;
                break;
            case History:
                request = r.GetWeatherDataByHistory(API_KEY, RequestBlocks.GetBy.CityName, value, date);
                idValue = "history" + historyHourCounter++;
                break;
            default:
                break;
        }
        Document doc = Document.parse(request);
        doc.append("_id", idValue);
        db.addDocument(value,type.toString(),doc);
    }

    public MyWeatherModel getCurrentDocument(String city, String type) throws Exception {
        String doc = db.getDocument(city,type,"_id","current");
        return gson.fromJson(doc,MyWeatherModel.class);
    }
}
