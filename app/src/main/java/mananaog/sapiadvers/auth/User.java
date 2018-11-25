package mananaog.sapiadvers.auth;

import java.io.Serializable;

public class User implements Serializable {

    public String firstName;
    public String lastName;
    public String phone;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String firstName, String lastName, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }

}