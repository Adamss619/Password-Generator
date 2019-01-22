import sun.security.util.Password;
import sun.tools.tree.ThisExpression;
import java.util.Random;
import java.nio.charset.Charset;

public class PasswordLogic {

    private String Password;
    private String AccountName;
    private String Key;
    private String HashedPassword;
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnop";

    //New account creation
    public PasswordLogic(){
        this.Password = null;
        this.AccountName = null;
        this.Key = null;
    }
    //Reading old account to dehash password
    public PasswordLogic(String HPassword, String Account, String key){
        this.AccountName = Account;
        this.Key = key;
        deHashPassword(key,HPassword); //Method sets password after decrypt
    }

    public void setAccount(String Account, String key){
        this.AccountName = Account;
        this.Key = key;
    }

    public void setPassword(String password){
        this.Password = password;
    }

    public String getAccount(){
        return this.AccountName;
    }

    public String getPassword(){
        return this.Password;
    }

    public String getHashedPassword(){
        return this.HashedPassword;
    }

    public void generatePassword(int Length){

        StringBuilder builder = new StringBuilder();
        while (Length-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        this.Password = builder.toString();
    }

    public void hashPassword(String key){
        String encryptedString = AES.encrypt(this.Password, key) ;
        this.HashedPassword = encryptedString;
    }

    public void deHashPassword(String Key, String Password){
        String decryptedString = AES.decrypt(Password, Key);
        this.Password = decryptedString;
    }


    /**
     * account setter and getter
     * password getter
     * password hashing
     * password dehashing
     * password generator
     */
}
