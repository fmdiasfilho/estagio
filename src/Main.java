

import brain.manipulation.Brain;
import data.threads.UpdatingData;

import java.util.Scanner;

public class Main {

    private static final String MENU = "%d stands for %s\n";

    public static void main(String[] args) throws Exception {
        Brain b = new Brain();
        new UpdatingData();
        Scanner in = new Scanner(System.in);
        int opt;
        Thread.sleep(5000);
       do{
           System.out.format(MENU,1,"Update current node");
           System.out.format(MENU,2,"Fill yesterday");
           System.out.format(MENU,3,"Fill today");
           opt = in.nextInt();
           in.nextLine();
            switch (opt){
                case 1:
                    b.updateCurrentNode();
                    break;
                case 2:
                    b.fillYesterday();
                    break;
                case 3:
                    b.fillToday();
                    break;
                default:
                    break;
            }
        }while (opt != 0);
       System.exit(0);
    }

}
