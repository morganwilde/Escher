package co.developertime.android.escher.database;

/**
 * Created by morganwilde on 24/11/2015.
 */
public class EscherDBSchema {
    public static final class LocationTable {
        // Table name
        public static final String NAME = "locations";

        // Table columns
        public static final class Columns {
            public static final String ACCURACY = "accuracy";
            public static final String ALTITUDE = "altitude";
            public static final String LATITUDE = "latitude";
            public static final String LONGITUDE = "longitude";
            public static final String TIME = "time";
        }
    }
}
