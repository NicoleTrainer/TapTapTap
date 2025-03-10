package com.TechInfinityStudios.TapTapTap;

import android.content.Context;
import android.content.SharedPreferences;

public class scoreStorage {
    private static final String PREF_NAME = "HighScores";
    private static final String LEVEL_PREFIX = "Level_";

    // Save score for a specific level
    public static void saveScore(Context context, int level, int score) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        String key = LEVEL_PREFIX + level;

        // Retrieve existing highest score for the level
        int highestScore = getHighestScore(context, level);

        // Compare with the new score and update if it's higher
        if (score > highestScore) {
            editor.putInt(key, score);
            editor.apply();
        }
    }

    // Retrieve highest score for a specific level
    public static int getHighestScore(Context context, int level) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String key = LEVEL_PREFIX + level;
        return preferences.getInt(key, 0); // Return 0 if no score is found
    }
}
