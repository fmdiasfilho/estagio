package data.threads;

import data.Enumerations.RequestTypes;

public class UpdatingData {

    public UpdatingData(){
        new Thread(new CurrentThread()).start();
        new Thread(new HistoryThread()).start();
    }

}
