package data.threads;

import data.DatabaseManipulation;
import data.Enumerations.RequestTypes;

public class TodayThread implements Runnable{

    private void fillTodayCollection() throws Exception {
        DatabaseManipulation db = new DatabaseManipulation();
        db.updateWeather(RequestTypes.Today,"Lisbon","2018-05-03");
    }

    public void run(){
        try {
            while(true){
            fillTodayCollection();
                System.out.println("Today updated!");
            Thread.sleep(3600000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
