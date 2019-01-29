import java.util.Scanner;

public class Main {

    private static boolean passwordLog = false;
    private static String[] accounts = new String[50];
    private static int numberOfA = 1;

    public static void main(String[] args) {
        System.out.println("Welcome to PasswordGenerator V0.1");
        System.out.println("Loading Up Presets");
        boolean passwordLog = AccessLogic.verifyLogin();
        while ((passwordLog)) { //logic for active program
            System.out.println("For a list of options type Help");
            Scanner keyboard = new Scanner(System.in);
            String entry = keyboard.nextLine();

            switch (entry.toLowerCase()) {
                case "help":
                    System.out.println("To create new account type: New");
                    System.out.println("To look at already created accounts type: List");
                    System.out.println("To access already created account type: Account Info");
                    break;
                case "new":
                    AccessLogic.newAccount();
                    break;
                case "list":
                    AccessLogic.List();
                    break;
                case "account info":
                    AccessLogic.AccountInfo();
                    break;
            }
        }

        //wait

    }
}
