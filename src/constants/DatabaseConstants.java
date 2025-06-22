package constants;

public class DatabaseConstants {
    public static class CountersTable {
        public static final String TABLE_NAME = "counters";
        public static final String FIELD_ID = "id";
        public static final String FIELD_START_VALUE = "start_value";
        public static final String FIELD_END_VALUE = "end_value";
        public static final String FIELD_CURRENT_VALUE = "current_value";
    }

    public static class ShortURLTable {
        public static final String TABLE_NAME = "short_url";
        public static final String FIELD_ID = "id";
        public static final String FIELD_ORIGINAL_URL = "original_url";
        public static final String FIELD_SHORT_URL = "short_url";
    }
}
