package comcesar1287.github.www.collie.controller.domain;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    public String nameFather, nameChild, ageChild;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String nameFather, String nameChild, String ageChild) {
        this.nameFather = nameFather;
        this.nameChild = nameChild;
        this.ageChild = ageChild;
    }
}
