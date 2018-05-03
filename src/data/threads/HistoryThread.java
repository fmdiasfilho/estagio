package data.threads;

import brain.manipulation.Brain;
import data.DatabaseManipulation;
import data.Enumerations.RequestTypes;

public class HistoryThread implements Runnable{

    private void fillTodayCollection() throws Exception {
        DatabaseManipulation db = new DatabaseManipulation();
        db.updateWeather(RequestTypes.Today,"Lisbon",db.dates.get(0));
    }

    public void run(){
        try {
            while(true){
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
