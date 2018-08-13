package ru.mediasoft.unipolls.data.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ru.mediasoft.unipolls.data.db.DBHelper;
import ru.mediasoft.unipolls.domain.dataclass.polldetails.Heading;
import ru.mediasoft.unipolls.domain.dataclass.polllist.Poll;
import ru.mediasoft.unipolls.domain.dataclass.pollpages.Page;
import ru.mediasoft.unipolls.domain.dataclass.pollquestiondetail.Answers;
import ru.mediasoft.unipolls.domain.dataclass.pollquestiondetail.Choice;
import ru.mediasoft.unipolls.domain.dataclass.pollquestiondetail.HeadingQuestion;
import ru.mediasoft.unipolls.domain.dataclass.pollquestiondetail.SearchResultQuestionDetails;
import ru.mediasoft.unipolls.domain.dataclass.pollquestions.Question;
import ru.mediasoft.unipolls.other.Constants;
import ru.mediasoft.unipolls.other.Constants.SurveyMonkeyDatabase.*;

public class DBRepository {

    public static final String DB_NAME = "sm_db";
    public static final int DB_VERSION = 20;

    private DBHelper dbHelper;

    public DBRepository(Context ctx) {
        this.dbHelper = new DBHelper(ctx, DB_NAME, null, DB_VERSION);
    }

    public void savePage(Page page, String pollId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery(PagesTable.Queries.SELECT_ALL, null);
        if (cursor.moveToFirst()) {
            do {
                String dbId = cursor.getString(cursor.getColumnIndex(PagesTable.Columns.COLUMN_PAGE_ID));
                if (page.id.equals(dbId)) {
                    //TODO ПЕРЕЗАПИСАТЬ
                    return;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();

        ContentValues cv = new ContentValues();
        cv.put(PagesTable.Columns.COLUMN_PAGE_ID, page.id);
        cv.put(PagesTable.Columns.COLUMN_POLL_ID, pollId);
        db.insert(PagesTable.TABLE_NAME, null, cv);

        db.close();
    }

    public void savePoll(Poll poll) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery(PollsTable.Queries.SELECT_ALL, null);
        if (cursor.moveToFirst()) {
            do {
                String dbId = cursor.getString(cursor.getColumnIndex(PollsTable.Columns.COLUMN_ID));
                if (poll.id.equals(dbId)) {
                    //TODO ПЕРЕЗАПИСАТЬ
                    return;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();

        ContentValues cv = new ContentValues();

        cv.put(PollsTable.Columns.COLUMN_ID, poll.id);
        cv.put(PollsTable.Columns.COLUMN_NAME, poll.title);
        db.insert(PollsTable.TABLE_NAME, null, cv);

        db.close();
    }

    public void saveQuestion(Question question, String pageId, String pollId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery(QuestionsTable.Queries.SELECT_ALL, null);
        if (cursor.moveToFirst()) {
            do {
                String dbId = cursor.getString(cursor.getColumnIndex(QuestionsTable.Columns.COLUMN_QUESTION_ID));
                if (question.id.equals(dbId)) {
                    //TODO ПЕРЕЗАПИСАТЬ
                    return;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();

        ContentValues cv = new ContentValues();

        cv.put(QuestionsTable.Columns.COLUMN_QUESTION_ID, question.id);
        cv.put(QuestionsTable.Columns.COLUMN_NAME, question.heading);
        cv.put(QuestionsTable.Columns.COLUMN_POSITION, question.position);
        cv.put(QuestionsTable.Columns.COLUMN_PAGE_ID, pageId);
        cv.put(QuestionsTable.Columns.COLUMN_POLL_ID, pollId);

        db.insert(QuestionsTable.TABLE_NAME, null, cv);

        getQuestionsToLogs();

        db.close();
    }

    public String getQuestionId(String position, String pollId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String questionId = null;

        Cursor cursor = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME + " WHERE " + QuestionsTable.Columns.COLUMN_POSITION + " = ? AND " + QuestionsTable.Columns.COLUMN_POLL_ID + " = ?", new String[]{position, pollId});

        if (cursor.moveToFirst()) {
            do {
                questionId = cursor.getString(cursor.getColumnIndex(QuestionsTable.Columns.COLUMN_QUESTION_ID));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return questionId;
    }

    public List<Page> getPageList(String pollId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        List<Page> pageList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + PagesTable.TABLE_NAME + " WHERE " + PagesTable.Columns.COLUMN_POLL_ID + "=" + pollId, null);

        if (cursor.moveToFirst()) {
            do {
                Page page = new Page();
                page.setId(cursor.getString(cursor.getColumnIndex(PagesTable.Columns.COLUMN_PAGE_ID)));
                pageList.add(page);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return pageList;
    }

    public List<Poll> getPollList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        List<Poll> pollList = new ArrayList<>();
        Cursor cursor = db.rawQuery(PollsTable.Queries.SELECT_ALL, null);

        if (cursor.moveToFirst()) {
            do {
                Poll poll = new Poll();
                poll.title = cursor.getString(cursor.getColumnIndex(PollsTable.Columns.COLUMN_NAME));
                poll.id = cursor.getString(cursor.getColumnIndex(PollsTable.Columns.COLUMN_ID));
                pollList.add(poll);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return pollList;
    }

    public String getPageId(String position, String pollId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String pageId = null;

        Cursor cursor = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME + " WHERE " + QuestionsTable.Columns.COLUMN_POSITION + " = ? AND " + QuestionsTable.Columns.COLUMN_POLL_ID + " = ?", new String[]{position, pollId});

        if (cursor.moveToFirst()) {
            do {
                pageId = cursor.getString(cursor.getColumnIndex(QuestionsTable.Columns.COLUMN_PAGE_ID));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return pageId;
    }

    public int getQuestionCount(String pollId) {
        int count = 0;

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME + " WHERE " + QuestionsTable.Columns.COLUMN_POLL_ID + "=" + pollId, null);

        count = cursor.getCount();

        cursor.close();
        db.close();

        return count;
    }

    public void getPagesToLogs() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(PagesTable.Queries.SELECT_ALL, null);

        if (cursor.moveToFirst()) {
            do {
                Log.i(Constants.LOG_TAG_DB, "page_id - " + cursor.getString(cursor.getColumnIndex(PagesTable.Columns.COLUMN_PAGE_ID)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
    }

    public void getPollsToLogs() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(PollsTable.Queries.SELECT_ALL, null);

        if (cursor.moveToFirst()) {
            do {
                Log.i(Constants.LOG_TAG_DB, "poll_id - " + cursor.getString(cursor.getColumnIndex(PollsTable.Columns.COLUMN_ID)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
    }

    public void getQuestionsToLogs() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(QuestionsTable.Queries.SELECT_ALL, null);

        if (cursor.moveToFirst()) {
            do {
                Log.i(Constants.LOG_TAG_DB, "question_id - " + cursor.getString(cursor.getColumnIndex(QuestionsTable.Columns.COLUMN_QUESTION_ID)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
    }

    public void saveAnswers(String questionId, List<Choice> answers) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        Gson gson = new Gson();
        String answersJson = gson.toJson(answers);

        cv.put(QuestionsTable.Columns.COLUMN_ANSWERS, answersJson);
        db.update(QuestionsTable.TABLE_NAME, cv, QuestionsTable.Columns.COLUMN_QUESTION_ID + " = ?", new String[]{questionId});

        db.close();
    }

    public SearchResultQuestionDetails getQuestionByPosition(String position, String pollId) {
        SearchResultQuestionDetails searchResultQuestionDetails = new SearchResultQuestionDetails();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME + " WHERE " + QuestionsTable.Columns.COLUMN_POSITION + " = ? AND " + QuestionsTable.Columns.COLUMN_POLL_ID + " = ?", new String[]{position, pollId});

        if (cursor.moveToFirst()) {
            do {
                String heading = cursor.getString(cursor.getColumnIndex(QuestionsTable.Columns.COLUMN_NAME));
                String answersJson = cursor.getString(cursor.getColumnIndex(QuestionsTable.Columns.COLUMN_ANSWERS));

                List<HeadingQuestion> headings = new ArrayList<>();
                HeadingQuestion headingQuestion = new HeadingQuestion();
                headingQuestion.heading = heading;
                headings.add(headingQuestion);

                Gson gson = new Gson();
                Type type = new TypeToken<List<Choice>>() {
                }.getType();
                List<Choice> choices = gson.fromJson(answersJson, type);
                Answers answers = new Answers();
                answers.choices = choices;

                searchResultQuestionDetails.heading = headings;
                searchResultQuestionDetails.answers = answers;
            } while (cursor.moveToNext());
        }

        db.close();

        return searchResultQuestionDetails;
    }

    public void saveDateCreated(String pollId, String dateCreated) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(PollsTable.Columns.COLUMN_DATE_CREATED, dateCreated);
        db.update(PollsTable.TABLE_NAME, cv, PollsTable.Columns.COLUMN_ID + " = ?", new String[]{pollId});

        db.close();
    }

    public void saveDateModified(String pollId, String dateModified) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(PollsTable.Columns.COLUMN_DATE_MODIFIED, dateModified);
        db.update(PollsTable.TABLE_NAME, cv, PollsTable.Columns.COLUMN_ID + " = ?", new String[]{pollId});

        db.close();
    }

    public void saveResponseCount(String pollId, String responseCount) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(PollsTable.Columns.COLUMN_RESPONSE_COUNT, responseCount);
        db.update(PollsTable.TABLE_NAME, cv, PollsTable.Columns.COLUMN_ID + " = ?", new String[]{pollId});

        db.close();
    }

    public String getDateCreated(String pollId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String dateCreated = null;

        Cursor cursor = db.rawQuery("SELECT * FROM " + PollsTable.TABLE_NAME + " WHERE " + PollsTable.Columns.COLUMN_ID + " = ?", new String[]{pollId});

        if (cursor.moveToFirst()) {
            do {
                dateCreated = cursor.getString(cursor.getColumnIndex(PollsTable.Columns.COLUMN_DATE_CREATED));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return dateCreated;
    }

    public String getDateModified(String pollId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String dateModified = null;

        Cursor cursor = db.rawQuery("SELECT * FROM " + PollsTable.TABLE_NAME + " WHERE " + PollsTable.Columns.COLUMN_ID + " = ?", new String[]{pollId});

        if (cursor.moveToFirst()) {
            do {
                dateModified = cursor.getString(cursor.getColumnIndex(PollsTable.Columns.COLUMN_DATE_MODIFIED));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return dateModified;
    }

    public String getResponseCount(String pollId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String responseCount = null;

        Cursor cursor = db.rawQuery("SELECT * FROM " + PollsTable.TABLE_NAME + " WHERE " + PollsTable.Columns.COLUMN_ID + " = ?", new String[]{pollId});

        if (cursor.moveToFirst()) {
            do {
                responseCount = cursor.getString(cursor.getColumnIndex(PollsTable.Columns.COLUMN_RESPONSE_COUNT));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return responseCount;
    }

    public boolean isQuestions(String pollId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME + " WHERE " + QuestionsTable.Columns.COLUMN_POLL_ID + "=" + pollId, null);

        if (cursor.moveToFirst()) {
            return true;
        }

        cursor.close();
        db.close();

        return false;
    }
}
