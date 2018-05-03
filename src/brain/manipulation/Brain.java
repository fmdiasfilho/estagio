package brain.manipulation;

import com.google.gson.Gson;
import com.ibm.watson.developer_cloud.assistant.v1.model.DialogNode;
import com.ibm.watson.developer_cloud.assistant.v1.model.DialogNodeCollection;
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
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class Brain {

    //Access data
    private static final String CONV_USERNAME = "9eecfbe1-6284-41b7-aa41-4e637f3f12ca";
    private static final String CONV_PASSWORD = "Q6JOwfeBWhxa";
    private static final String CONV_VERSION = "2018-02-16";
    private static final String CURRENT_NODE = "node_4_1524150306493";
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

    @Test
    public void updateCurrentNode() throws Exception {
        Calendar rightNow = Calendar.getInstance();
        assertEquals(12,rightNow.get(Calendar.HOUR_OF_DAY));
        String hour;

        if(Calendar.MINUTE < 10)
            hour = String.format("%d:0%d",rightNow.get(Calendar.HOUR_OF_DAY), rightNow.get(Calendar.MINUTE));
        else
            hour = String.format("%d:%d",rightNow.get(Calendar.HOUR_OF_DAY), rightNow.get(Calendar.MINUTE));

        MyWeatherModel weatherModel = (MyWeatherModel) data.getDocument("Lisbon",RequestTypes.Current);

        assertNotNull(weatherModel);

        String newOutput = String.format(CURRENT_FORMAT, hour, weatherModel.getCurrent().temp_c,
                weatherModel.getCurrent().getCondition().getText().toLowerCase());
        conversation.updateDialogNode(CURRENT_NODE,newOutput);
    }
    @Test
   public void fillYesterday() throws Exception {
        List<Hour> hours = data.getAllDocs("Lisbon", RequestTypes.Yesterday);
        assertTrue(hours.size() == 24);
        int counter = 0;
        for(Hour h : hours){
            String output = String.format(PAST_FORMAT, h.getTime().substring(11,16), h.getTempC(),h.getCondition().getText().toLowerCase());
            conversation.updateDialogNode("At" + counter,output);
            counter++;
        }
    }

    @Test
    public void fillToday() throws Exception{
        List<Hour> hours = data.getAllDocs("Lisbon",RequestTypes.Today);
        assertTrue(hours.size() == 11);
        int rightNow =  Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        assertEquals(12,rightNow);
        int counter = 0;
        DialogNodeCollection list = conversation.listDialogNodes();
        for(Hour h : hours){
                String output = String.format(TODAY_FORMAT,h.getTime().substring(11,16),
                        h.getTempC(),h.getCondition().getText().toLowerCase());
                if(conversation.getDialogNode("Today at" + counter) == null){
                    conversation.createDialogNode("Today at" + counter, "Today at" + counter, TODAY_NODE,"#At" + counter,output);
                }else{
                    conversation.updateDialogNode("Today at" + counter, output);
                }
            counter++;
        }
        for(DialogNode d : list.getDialogNodes()){
            if(d != null && d.getTitle().equals("Today at" + counter)){
                conversation.deleteDialogNode(d.getTitle());
                counter++;
            }else
                break;
        }
        counter = 0;
    }



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
