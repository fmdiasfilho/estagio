

import brain.manipulation.Brain;

import java.util.Scanner;

public class Main {

    private static final String MENU = "%d stands for %s\n";

    public static void main(String[] args) throws Exception {
        Brain b = new Brain();
        Scanner in = new Scanner(System.in);
        int opt;
        Thread.sleep(5000);
       do{
           System.out.format(MENU,1,"Update current node");
           System.out.format(MENU,2,"List nodes");
           System.out.format(MENU,3,"Fill yesterday");
           System.out.format(MENU,4,"Fill today");
           System.out.format(MENU,5,"Remove nodes");
           opt = in.nextInt();
           in.nextLine();
            switch (opt){
                case 1:
                    b.updateCurrentNode();
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
                default:
                    break;
            }
        }while (opt != 0);
       System.exit(0);
    }

}
