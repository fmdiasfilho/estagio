package data.threads;

import data.DatabaseManipulation;
import data.Enumerations.RequestTypes;

public class CurrentThread implements Runnable{

        private void fillCurrent() throws Exception {
            DatabaseManipulation db = new DatabaseManipulation();
            db.updateWeather(RequestTypes.Current,"Lisbon",null);
        }

        public void run(){
            try {
                while(true){
                fillCurrent();
                    System.out.println("Current updated!");
                Thread.sleep(300000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
