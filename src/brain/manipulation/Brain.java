package brain.manipulation;

import com.google.gson.Gson;
import com.ibm.watson.developer_cloud.assistant.v1.model.DialogNode;
import com.weatherlibrary.datamodel.Forecastday;
import com.weatherlibrary.datamodel.Hour;
import com.weatherlibrary.datamodel.WeatherModel;
import com.weatherlibraryjava.RequestBlocks;
import data.DataBaseOperations;
import data.DatabaseManipulation;
import data.Enumerations.RequestTypes;
import data.weatherRepository.MyRepository;
import data.weatherRepository.historyRequests.HistoryWeatherModel;
import data.weatherRepository.historyRequests.MyWeatherModel;

import java.util.*;

public class Brain {

    //Access data
    private static final String CONV_USERNAME = "9eecfbe1-6284-41b7-aa41-4e637f3f12ca";
    private static final String CONV_PASSWORD = "Q6JOwfeBWhxa";
    private static final String CONV_VERSION = "2018-02-16";
    private static final String YESTERDAY_NODE = "node_2_1525252196706";
    private static final String TODAY_NODE = "node_1_1525255385552";

    //Formats to dialog nodes texts
    private static final String CURRENT_FORMAT = "Right now at %s and the temperature is %.1f and, for now, apparently will be a %s day!";
    private static final String PAST_FORMAT = "Yesterday at %s, the temperature was %.1f and it was a %s day at this hour!";
    private static final String TODAY_FORMAT = "Today at %s, the temperature was %.1f and a %s condition!";

    private DialogNodeManipulation conversation;
    private IntentManipulation intentManipulation;
    private ExamplesManipulation examplesManipulation;

    private DatabaseManipulation data;

    public Brain() throws Exception {
        conversation = new DialogNodeManipulation(CONV_USERNAME,CONV_PASSWORD,CONV_VERSION);
        intentManipulation = new IntentManipulation(CONV_USERNAME,CONV_PASSWORD,CONV_VERSION);
        examplesManipulation = new ExamplesManipulation(CONV_USERNAME,CONV_PASSWORD,CONV_VERSION);
        data = new DatabaseManipulation();
    }

    public void updateCurrentNode(String currentNode) throws Exception {
        Calendar rightNow = Calendar.getInstance();
        String hour;

        if(Calendar.MINUTE < 10)
            hour = String.format("%d:0%d",rightNow.get(Calendar.HOUR_OF_DAY), rightNow.get(Calendar.MINUTE));
        else
            hour = String.format("%d:%d",rightNow.get(Calendar.HOUR_OF_DAY), rightNow.get(Calendar.MINUTE));

        MyWeatherModel weatherModel = (MyWeatherModel) data.updateWeather(RequestTypes.Current,"Lisbon",null);

        String newOutput = String.format(CURRENT_FORMAT, hour, weatherModel.getCurrent().temp_c,
                weatherModel.getCurrent().getCondition().getText().toLowerCase());
        conversation.updateDialogNode(currentNode,newOutput);
    }

  /* public void fillYesterday() throws Exception {
        String historyRequest = r.GetWeatherDataByHistory(API_KEY,RequestBlocks.GetBy.CityName,"Lisbon","2018-04-26");
        System.out.println(historyRequest);
        HistoryWeatherModel history = gson.fromJson(historyRequest,HistoryWeatherModel.class);
        Forecastday forecastday = history.getForecast().getForecastday().get(0);
        List<Hour> hours = forecastday.getHour();
        int counter = 0;
        for(Hour h : hours){
            String output = String.format(PAST_FORMAT, h.getTime().substring(11,16), h.getTempC(),h.getCondition().getText().toLowerCase());
            conversation.createDialogNode("At" + counter,"At" + counter,
                    YESTERDAY_NODE, "#" + getIntent("At" + counter),output);
            System.out.println(h.getTime() + "\n" + output);
            counter++;
        }
    }

    public void fillToday() throws Exception{
        String request = r.GetWeatherDataByHistory(API_KEY,RequestBlocks.GetBy.CityName,"Lisbon", "2018-05-02");
        System.out.println(request);
        HistoryWeatherModel history = gson.fromJson(request, HistoryWeatherModel.class);
        Forecastday forecastday = history.getForecast().getForecastday().get(0);
        List<Hour> hours = forecastday.getHour();
        Calendar rightNow = Calendar.getInstance();
        for(int i = 0; i < rightNow.get(Calendar.HOUR_OF_DAY); i++){
            Hour current = hours.get(i);
            String output = String.format(TODAY_FORMAT,current.getTime().substring(11,16),
                    current.getTempC(),current.getCondition().getText().toLowerCase());
            conversation.createDialogNode("Today at" + i, "Today at" + i,
                    TODAY_NODE, "#At" + i,output);
            System.out.println(current.getTime() + "\n" + output);
        }
    }*/

    public String addIntents(int counter, String hour){
        String result = "";
        intentManipulation.createIntent("At" + counter,"When user writes " + counter + "hour");
        examplesManipulation.createExample("At" + counter,hour);
        result = "At" + counter;
        return result;
    }

    public void removeNodes(){

        for(DialogNode d : conversation.listDialogNodes().getDialogNodes()){
            if(d.getParent() != null && d.getParent().equalsIgnoreCase(YESTERDAY_NODE)){
                conversation.deleteDialogNode(d.getTitle());
            }
        }
    }

    public String getIntent(String intentName){
        return intentManipulation.getIntent(intentName).getIntentName();
    }

    public void removeTodayNodes(){
        for(DialogNode d : conversation.listDialogNodes().getDialogNodes()){
            if(d.getParent() != null && d.getParent().equalsIgnoreCase(TODAY_NODE)){
                conversation.deleteDialogNode(d.getTitle());
            }
        }
    }

    public void list(){
        for(DialogNode d : conversation.listDialogNodes().getDialogNodes()){
            System.out.println(d);
        }
    }
}
