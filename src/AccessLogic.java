import java.io.*;
import java.util.Scanner;

public class AccessLogic {
    private static String decryptedKey;
    private static Scanner keyboard = new Scanner(System.in);
    private static String[] localAccount;

    public static void AccountInfo() {
        System.out.println("What is the index of the account you are looking for?");
        int index = keyboard.nextInt();
        System.out.print("Account Name: ");
        System.out.println(localAccount[index]);
        System.out.println(decryptedKey);
        PasswordLogic logic = new PasswordLogic(localAccount[index + 1], localAccount[index], decryptedKey);
        System.out.println("Password: " + logic.getPassword());
    }

    public static String[] List() {
        String[] accounts = new String[50];
        int numberOfA = 0;
        try {
            File pFile = new File("passwordAccounts.txt");
            Scanner passwordSC = new Scanner(pFile);
            while (passwordSC.hasNext()) {
                accounts[numberOfA] = passwordSC.next(); //takes every line in the password file
                if ((numberOfA % 2 == 0)) {
                    System.out.println(accounts[numberOfA] + " [" + numberOfA + "]");
                }
                numberOfA++;
            }
        } catch (FileNotFoundException x) {

            System.out.println("No previous accounts");
        }
        localAccount = accounts;
        return accounts;
    }

    public static boolean verifyLogin() {
        boolean passwordLog = false;
        try {
            File file = new File("accountInfo.txt");
            Scanner sc = new Scanner(file);
            Scanner keyboard = new Scanner(System.in);

            String userName = sc.nextLine();
            System.out.println("Welcome back " + userName);

            System.out.println("Would you please enter your password:)");
            String passwordCheck = keyboard.nextLine();
            String password = sc.nextLine();
            String key = sc.nextLine();
            decryptedKey = AES.decrypt(key, userName);
            String decryptedPass = AES.decrypt(password, decryptedKey);
            if (passwordCheck.equals(decryptedPass)) {
                System.out.println("Password was correct");
                passwordLog = true;
            }
        } catch (FileNotFoundException e) {
            try {
                Scanner account = new Scanner(System.in);
                PrintWriter writer = new PrintWriter("accountInfo.txt", "UTF-8");
                PrintWriter writers = new PrintWriter("passwordAccounts.txt", "UTF-8");
                System.out.println("It seems this is your first time using this program");
                System.out.println("You need to create an account to manage your passwords");
                System.out.println("Please input an account username");
                String accountS = account.nextLine();
                System.out.println("Nice to meet you " + accountS);
                writer.write(accountS + "\n");

                System.out.println("Would you also input a password. You will use this password everytime this program is accessed");
                String passwordS = account.nextLine();
                PasswordLogic userAccount = new PasswordLogic();
                PasswordLogic keyHash = new PasswordLogic();
                userAccount.setPassword(passwordS);

                System.out.println("last but not least would you please enter a key. This key will be used to encrypt all of your passwords:)");
                String keyS = account.nextLine();
                userAccount.hashPassword(keyS);
                keyHash.setPassword(keyS);
                keyHash.hashPassword(accountS);

                System.out.println(userAccount.getHashedPassword());
                writer.write(userAccount.getHashedPassword() + "\n");

                writer.write(keyHash.getHashedPassword() + "\n");

                writer.close();

                System.out.println("Please Restart the program :)");
            } catch (FileNotFoundException | UnsupportedEncodingException n) {
                System.out.println(n);
            }
        }

        return passwordLog;
    }

    public static void newAccount() {
        System.out.println("New Selected");
        System.out.println("Please enter the account name");
        String accountName = keyboard.nextLine();

        PasswordLogic newAccount = new PasswordLogic();
        newAccount.setAccount(accountName, decryptedKey);
        newAccount.generatePassword(10);
        newAccount.hashPassword(decryptedKey);

        System.out.println("Generated Password: " + newAccount.getPassword());
        try {
            File passFile = new File("passwordAccounts.txt");
            FileWriter fw = new FileWriter(passFile, true);
            fw.write("\n");
            fw.write(accountName + "\n");
            fw.write(newAccount.getHashedPassword());
            fw.close();
        } catch (IOException n) {
            try {
                PrintWriter writer = new PrintWriter("passwordAccounts.txt", "UTF-8");
                System.out.println("It seems that a password file was never created please restart the program.");
            } catch (UnsupportedEncodingException | FileNotFoundException a) {
            }

            System.out.println("Account could not be created.");
        }
        //create new a file and use that account range in file to find password in password file.
    }
}
