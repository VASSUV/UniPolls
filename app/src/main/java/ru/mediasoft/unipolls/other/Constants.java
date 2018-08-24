package ru.mediasoft.unipolls.other;

import android.provider.BaseColumns;

public class Constants {

    public static final String LOG_TAG_DB = "mDb";

    public static class SurveyMonkeyApi {
        public static final String BASE_URL = "https://api.surveymonkey.com";
        public static final String CONTENT_TYPE = "Content-Type: application/json";
    }

    public static class BundleKeys {
        public static final String POLL_TITLE_KEY = "poll_title";
        public static final String POLL_ID_KEY = "poll_id";
        public static final String PAGES_COUNT = "pages_count";
        public static final String PAGE_QUESTIONS_COUNT = "questions_count";
        public static final String PAGE_ID_KEY = "page_id";
        public static final String QUESTION_POSITION = "question_pos";
        public static final String QUESTION_TITLE_KEY = "qusetion_title";
        public static final String QUESTION_ID_KEY = "qusetion_id";
    }

    public static class SurveyMonkeyAuthApi {
        public static final String CLIENT_ID = "5Rwe_g_nQMOZdnC80Riq0Q";
        public static final String REDIRECT_URI = "https://www.surveymonkey.com";
        public static final String CLIENT_SECRET = "327907589871769526989995616616281591565";
        public static final String GRANT_TYPE = "authorization_code";
    }

    public static class SurveyMonkeyDatabase {

        public static class PagesTable {
            public static final String TABLE_NAME = "pages_table";

            public static class Columns implements BaseColumns {
                public static final String COLUMN_PAGE_ID = "page_id";
                public static final String COLUMN_POLL_ID = "poll_id";
            }

            public static class Queries {
                public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + "(" +
                        Columns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        Columns.COLUMN_POLL_ID + " TEXT not null, " +
                        Columns.COLUMN_PAGE_ID + " TEXT not null);";
                public static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;
                public static final String SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
            }
        }

        public static class PollsTable {
            public static final String TABLE_NAME = "polls_table";

            public static class Columns {
                public static final String COLUMN_ID = "poll_id";
                public static final String COLUMN_NAME = "poll_name";

                public static final String COLUMN_DATE_CREATED = "date_created";
                public static final String COLUMN_DATE_MODIFIED = "date_modified";
                public static final String COLUMN_RESPONSE_COUNT = "response_count";
            }

            public static class Queries {
                public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + "(" +
                        Columns.COLUMN_NAME + " TEXT not null, " +
                        Columns.COLUMN_DATE_CREATED + " TEXT, " +
                        Columns.COLUMN_DATE_MODIFIED + " TEXT, " +
                        Columns.COLUMN_RESPONSE_COUNT + " TEXT, " +
                        Columns.COLUMN_ID + " TEXT PRIMARY KEY not null);";
                public static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;
                public static final String SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
                public static final String DELETE_OLD_POLLS = "DELETE FROM " + TABLE_NAME + " WHERE " + Columns.COLUMN_ID + " NOT IN (%s)";
            }
        }

        public static class QuestionsTable {
            public static final String TABLE_NAME = "questions_table";

            public static class Columns implements BaseColumns {
                public static final String COLUMN_QUESTION_ID = "question_id";
                public static final String COLUMN_PAGE_ID = "page_id";
                public static final String COLUMN_NAME = "question_name";
                public static final String COLUMN_ANSWERS = "question_answers";
                public static final String COLUMN_POSITION = "question_position";
                public static final String COLUMN_POLL_ID = "poll_id";
            }

            public static class Queries {
                public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + "(" +
                        Columns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        Columns.COLUMN_NAME + " TEXT not null, " +
                        Columns.COLUMN_PAGE_ID + " TEXT not null, " +
                        Columns.COLUMN_ANSWERS + " TEXT, " +
                        Columns.COLUMN_POSITION + " TEXT not null, " +
                        Columns.COLUMN_POLL_ID + " TEXT not null, " +
                        Columns.COLUMN_QUESTION_ID + " TEXT not null);";
                public static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;
                public static final String SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
            }
        }
    }
}

