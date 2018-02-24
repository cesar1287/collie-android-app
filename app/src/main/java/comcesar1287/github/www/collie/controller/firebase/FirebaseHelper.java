package comcesar1287.github.www.collie.controller.firebase;

import com.google.firebase.database.DatabaseReference;

import comcesar1287.github.www.collie.controller.domain.Block;
import comcesar1287.github.www.collie.controller.domain.Children;
import comcesar1287.github.www.collie.controller.domain.User;

public class FirebaseHelper {

    private static final String FIREBASE_DATABASE_USERS = "users";
    private static final String FIREBASE_DATABASE_CONFIG = "config";
    private static final String FIREBASE_DATABASE_CHILDREN = "children";

    public static void writeNewUser(DatabaseReference mDatabase, String userId, String nameFather,
                                    String nameChild, String ageChild) {

        User user = new User(nameFather, nameChild, ageChild);

        mDatabase.child(FIREBASE_DATABASE_USERS).child(userId).setValue(user);
    }

    public static void writeNewConfigBlock(DatabaseReference mDatabase, String userId, String type,
                                    String options, String nameTask1, String bonusTask1, String nameTask2, String bonusTask2) {

        Block block = new Block(type, options, nameTask1, bonusTask1, nameTask2, bonusTask2);

        mDatabase.child(FIREBASE_DATABASE_CONFIG).child(userId).setValue(block);
    }

    public static void writeNewChildren(DatabaseReference mDatabase, String idChildren, String idFather,
                                        String block, String latitude, String longitude) {

        Children children = new Children(idFather, block, latitude, longitude);

        mDatabase.child(FIREBASE_DATABASE_CHILDREN).child(idChildren).setValue(children);
    }
}