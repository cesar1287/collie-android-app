package comcesar1287.github.www.collie.controller.domain;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Children {

    public String idFather, block, latitude, longitude;

    public Children() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Children(String idFather, String block, String latitude, String longitude) {
        this.idFather = idFather;
        this.block = block;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
