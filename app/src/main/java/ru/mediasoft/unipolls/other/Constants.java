package ru.mediasoft.unipolls.other;

import android.provider.BaseColumns;

public class Constants {
    public static final String LOG_TAG_DB = "mDb";

    public static class SurveyMonkeyApi {
        public static final String BASE_URL = "https://api.surveymonkey.com/v3/";
        public static final String AUTH_KEY = "Authorization:bearer LBiQHvyhTbN3YqEM1ItHOjefSF2SinkRVKaPaJcRG7wRtyt0E9rww1BSTLBnuN5zysaxkemzk.ydEqZNqiXlrzBLMBK-wNurxQpoagNoto6xFL.KRakvJePVtmB1SAHz";
        public static final String CONTENT_TYPE = "Content-Type: application/json";
    }

    public static class BundleKeys {
        public static final String POLL_TITLE_KEY = "poll_title";
        public static final String POLL_ID_KEY = "poll_id";
        public static final String PAGES_COUNT = "pages_count";
        public static final String PAGE_QUESTIONS_COUNT = "questions_count";
        public static final String PAGE_ID_KEY = "page_id";
        public static final String QUESTION_POSITION = "question_pos";
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

        public static class PollsTable{
            public static final String TABLE_NAME = "polls_table";

            public static class Columns implements BaseColumns {
                public static final String COLUMN_ID = "poll_id";
                public static final String COLUMN_NAME = "poll_name";
            }

            public static class Queries {
                public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + "(" +
                        Columns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        Columns.COLUMN_NAME + " TEXT not null, " +
                        Columns.COLUMN_ID + " TEXT not null);";
                public static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;
                public static final String SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
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
