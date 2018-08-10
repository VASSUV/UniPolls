package ru.mediasoft.unipolls.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static ru.mediasoft.unipolls.other.Constants.SurveyMonkeyDatabase.*;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PagesTable.Queries.TABLE_CREATE);
        db.execSQL(PollsTable.Queries.TABLE_CREATE);
        db.execSQL(QuestionsTable.Queries.TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(PagesTable.Queries.TABLE_DROP);
        db.execSQL(PollsTable.Queries.TABLE_DROP);
        db.execSQL(QuestionsTable.Queries.TABLE_DROP);
        onCreate(db);
    }

}