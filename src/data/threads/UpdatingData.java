package data.threads;

/**
 * @author Pedro Feiteira, n48119
 * Class that launch the updating threads
 */
public class UpdatingData {

    public UpdatingData() {
        new Thread(new CurrentThread()).start();
        new Thread(new HistoryThread()).start();
    }

}
