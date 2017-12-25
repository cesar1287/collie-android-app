package comcesar1287.github.www.collie.controller.domain;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Block {

    public String type, options;

    public Block() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Block(String type, String options) {
        this.type = type;
        this.options = options;
    }
}
