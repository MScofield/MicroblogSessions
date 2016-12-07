package IronYard;

import java.util.ArrayList;

/**
 * Created by scofieldservices on 12/5/16.
 */
public class User {
    String name;
    String password;
    ArrayList<Messages> messageList = new ArrayList();

    public User (String n, String p){
        this.name = n;
        this.password = p;
    }
}
