package data.threads;

import data.DatabaseManipulation;
import data.Enumerations.RequestTypes;

/**
 * @author Pedro Feiteira, n48119
 * Thread class that updates automatically the database current weather information document
 */
public class CurrentThread implements Runnable {

    private void fillCurrent() throws Exception {
        DatabaseManipulation db = new DatabaseManipulation();
        db.updateWeather(RequestTypes.Current, "Lisbon", null);
    }

    public void run() {
        try {
            while (true) {
                fillCurrent();
                System.out.println("Current updated!");
                //5 minutes -> 30000
                Thread.sleep(60000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
