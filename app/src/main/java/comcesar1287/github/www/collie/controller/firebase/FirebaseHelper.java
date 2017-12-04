package comcesar1287.github.www.collie.controller.firebase;

import com.google.firebase.database.DatabaseReference;

import comcesar1287.github.www.collie.controller.domain.User;

public class FirebaseHelper {

    public static final String FIREBASE_DATABASE_USERS = "users";

    public static void writeNewUser(DatabaseReference mDatabase, String userId, String nameFather,
                                    String nameChild, String ageChild) {

        User user = new User(nameFather, nameChild, ageChild);

        mDatabase.child(FIREBASE_DATABASE_USERS).child(userId).setValue(user);
    }
}
