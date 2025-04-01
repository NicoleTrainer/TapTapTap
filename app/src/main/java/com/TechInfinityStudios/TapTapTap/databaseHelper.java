package com.TechInfinityStudios.TapTapTap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class databaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "highscore.db";
    public static final int DATABASE_VERSION = 1;

    public databaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS highscore (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "level INTEGER, " +
                "score INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS highscore");
        onCreate(db);
    }

    public void insertScore(int level, int score) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Insert the new score
        ContentValues values = new ContentValues();
        values.put("level", level);
        values.put("score", score);
        db.insert("highscore", null, values);

        // Ensure only the top 5 scores are kept for this level
        db.execSQL("DELETE FROM highscore WHERE id NOT IN (" +
                        "SELECT id FROM highscore WHERE level = ? " +
                        "ORDER BY score DESC LIMIT 5)",
                new String[]{String.valueOf(level)});

        db.close();
    }

    public List<Integer> getAllScoresForLevel(int level) {
        List<Integer> scores = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT score FROM highscore WHERE level = ? ORDER BY score DESC",
                new String[]{String.valueOf(level)});

        if (cursor.moveToFirst()) {
            do {
                scores.add(cursor.getInt(0));  // Add each score to the list
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return scores;
    }

    public int getHighScore(int level) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT MAX(score) FROM highscore WHERE level = ?",
                new String[]{String.valueOf(level)});

        int highScore = 0;
        if (cursor.moveToFirst()) {
            highScore = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return highScore;
    }
}
