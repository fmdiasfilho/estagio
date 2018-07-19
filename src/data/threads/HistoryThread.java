package data.threads;

import data.DatabaseManipulation;
import data.Enumerations.RequestTypes;

/**
 * @author Pedro Feiteira, n48119
 * Thread class that updates automatically the database history weather information documents
 */
public class HistoryThread implements Runnable {

    private void fillTodayCollection() throws Exception {
        DatabaseManipulation dbToday = new DatabaseManipulation();
        dbToday.updateWeather(RequestTypes.Today, "Lisbon", dbToday.dates.get(0));
    }

    public void run() {
        try {
            while (true) {
                fillTodayCollection();
                System.out.println("Today updated!");
                //1 hour
                Thread.sleep(3600000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
