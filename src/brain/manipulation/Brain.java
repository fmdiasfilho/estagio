package brain.manipulation;

import com.weatherlibrary.datamodel.Hour;
import data.DatabaseManipulation;
import data.Enumerations.RequestTypes;
import data.weatherRepository.MyCurrentClass;
import data.weatherRepository.historyRequests.MyWeatherModel;
import javafx.util.Pair;
import org.junit.Test;
import web.ChatManipulation;
import web.ChatManipulationServer;

import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Pedro Feiteira, n48119
 * This class 
 */
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
    private ChatManipulation chat;
    private DatabaseManipulation data;

    public Brain() throws Exception {
        conversation = new DialogNodeManipulation(CONV_USERNAME, CONV_PASSWORD, CONV_VERSION);
        data = new DatabaseManipulation();
    }


    public void updateCurrentNode() throws Exception {
        Pair<Integer, Integer> rightNowHour = getHour();
        String outputHour = createHour(rightNowHour);
        MyWeatherModel weatherModel = getDocument("Lisbon", RequestTypes.Current);
        String newOutput = makeCurrentOutput(outputHour, weatherModel);
        conversation.updateDialogNode(CURRENT_NODE, newOutput);
    }

    public void fillYesterday() {
        List<Hour> hours = data.getAllDocs("Lisbon", RequestTypes.Yesterday);
        int counter = 0;
        for (Hour h : hours) {
            String output = createHistoryOutput(PAST_FORMAT, h);
            conversation.updateDialogNode("At" + counter, output);
            counter++;
        }
    }

    public void fillToday() {
        List<Hour> hours = data.getAllDocs("Lisbon", RequestTypes.Today);
        int counter = 0;
        for (Hour h : hours) {
            String output = createHistoryOutput(TODAY_FORMAT, h);
            conversation.updateDialogNode("Today at" + counter, output);
            counter++;
        }
    }

    private String makeCurrentOutput(String outputHour, MyWeatherModel weatherModel) {
        MyCurrentClass current = weatherModel.getCurrent();
        double temperature = current.getTempC();
        String condition = current.getCondition().getText().toLowerCase();

        return String.format(CURRENT_FORMAT, outputHour, temperature, condition);
    }

    private MyWeatherModel getDocument(String city, RequestTypes type) throws Exception {
        return (MyWeatherModel) data.getDocument(city, type);
    }

    private String createHour(Pair<Integer, Integer> rightNowHour) {
        int hour = rightNowHour.getKey();
        int minute = rightNowHour.getValue();
        if (minute < 10)
            return String.format("%d:0%d", hour, minute);
        else
            return String.format("%d:%d", hour, minute);
    }

    private Pair<Integer, Integer> getHour() {
        Calendar righNow = Calendar.getInstance();
        int hour = righNow.get(Calendar.HOUR_OF_DAY);
        int minute = righNow.get(Calendar.MINUTE);
        return new Pair<>(hour, minute);
    }

    private String createHistoryOutput(String format, Hour h) {
        String time = h.getTime().substring(11, 16);
        double temperature = h.getTempC();
        String condition = h.getCondition().getText().toLowerCase();
        return String.format(format, time, temperature, condition);
    }


    @Test
    public void updateCurrentNodeTest() throws Exception {
        Calendar now = Calendar.getInstance();
        int testHour = now.get(Calendar.HOUR_OF_DAY);
        int testMinute = now.get(Calendar.MINUTE);

        Pair<Integer, Integer> rightNowHour = getHour();
        int hour = rightNowHour.getKey();
        assertTrue(testHour == hour);
        int minute = rightNowHour.getValue();
        assertTrue(testMinute == minute);

        String outputHour = createHour(rightNowHour);
        assertNotNull(outputHour);

        MyWeatherModel weatherModel = getDocument("Lisbon", RequestTypes.Current);
        assertNotNull(weatherModel);

        String newOutput = makeCurrentOutput(outputHour, weatherModel);
        assertEquals(newOutput, String.format(CURRENT_FORMAT, outputHour, weatherModel.getCurrent().getTempC(), weatherModel.getCurrent().getCondition().getText().toLowerCase()));

        conversation.updateDialogNode(CURRENT_NODE, newOutput);
    }

    @Test
    public void fillYesterdayTest() {
        List<Hour> hours = data.getAllDocs("Lisbon", RequestTypes.Yesterday);
        assertTrue(hours.size() == 24);
        int counter = 0;
        for (Hour h : hours) {
            String testoutput = String.format(PAST_FORMAT, h.getTime().substring(11, 16), h.getTempC(), h.getCondition().getText().toLowerCase());
            String output = createHistoryOutput(PAST_FORMAT, h);
            assertEquals(testoutput, output);
            conversation.updateDialogNode("At" + counter, output);
            counter++;
        }
    }

    // Unreachable nodes will be forecasts
    @Test
    public void fillTodayTest() {
        List<Hour> hours = data.getAllDocs("Lisbon", RequestTypes.Today);
        assertTrue(hours.size() == 14);
        int counter = 0;
        for (Hour h : hours) {
            String outputtest = String.format(TODAY_FORMAT, h.getTime().substring(11, 16),
                    h.getTempC(), h.getCondition().getText().toLowerCase());
            String output = createHistoryOutput(TODAY_FORMAT, h);
            assertEquals(outputtest, output);
            conversation.updateDialogNode("Today at" + counter, output);
            counter++;
        }
    }


}
