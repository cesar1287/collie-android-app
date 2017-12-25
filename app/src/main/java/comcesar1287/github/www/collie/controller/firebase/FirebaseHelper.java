package comcesar1287.github.www.collie.controller.firebase;

import com.google.firebase.database.DatabaseReference;

import comcesar1287.github.www.collie.controller.domain.Block;
import comcesar1287.github.www.collie.controller.domain.User;

public class FirebaseHelper {

    private static final String FIREBASE_DATABASE_USERS = "users";
    private static final String FIREBASE_DATABASE_CONFIG = "config";

    public static void writeNewUser(DatabaseReference mDatabase, String userId, String nameFather,
                                    String nameChild, String ageChild) {

        User user = new User(nameFather, nameChild, ageChild);

        mDatabase.child(FIREBASE_DATABASE_USERS).child(userId).setValue(user);
    }

    public static void writeNewConfigBlock(DatabaseReference mDatabase, String userId, String type,
                                    String options) {

        Block block = new Block(type, options);

        mDatabase.child(FIREBASE_DATABASE_CONFIG).child(userId).setValue(block);
    }
}
