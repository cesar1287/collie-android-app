package comcesar1287.github.www.collie.controller.domain;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Block {

    public String type, options, nameTask1, bonusTask1, nameTask2, bonusTask2;

    public Block() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Block(String type, String options, String nameTask1, String bonusTask1, String nameTask2, String bonusTask2) {
        this.type = type;
        this.options = options;
        this.nameTask1 = nameTask1;
        this.bonusTask1 = bonusTask1;
        this.nameTask2 = nameTask2;
        this.bonusTask2 = bonusTask2;
    }
}
