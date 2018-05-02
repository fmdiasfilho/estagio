

import brain.manipulation.Brain;
import brain.manipulation.IntentManipulation;
import brain.manipulation.conv.ConversationManipulation;

import java.util.Scanner;

public class Main {

    private static final String USERNAME = "9eecfbe1-6284-41b7-aa41-4e637f3f12ca";
    private static final String PASSWORD = "Q6JOwfeBWhxa";
    private static final String VERSION_DATE = "2018-02-16";
    private static final String MENU = "%d stands for %s\n";

    public static void main(String[] args) throws Exception {
        Brain b = new Brain();
        Scanner in = new Scanner(System.in);
        int opt;
       do{
           System.out.format(MENU,1,"Update current node");
           System.out.format(MENU,2,"List nodes");
           System.out.format(MENU,3,"Fill yesterday");
           System.out.format(MENU,4,"Fill today");
           System.out.format(MENU,5,"Remove nodes");
           System.out.format(MENU,6,"Remove today nodes");
           opt = in.nextInt();
           in.nextLine();
            switch (opt){
                case 1:
                    b.updateCurrentNode("node_4_1524150306493");
                    break;
                case 2:
                    b.list();
                    break;
                case 3:
                    b.fillYesterday();
                    break;
                case 4:
                    b.fillToday();
                    break;
                case 5:
                    b.removeNodes();
                    break;
                case 6:
                    b.removeTodayNodes();
                    break;
                default:
                    break;
            }
        }while (opt != 0);
    }
}
