package com.example.dictionary;

import android.app.Activity;
import android.content.Intent;

public class NavigationHelper {
    public static void navigateToMainActivity(Activity fromActivity) {
        Intent intent = new Intent(fromActivity, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        fromActivity.startActivity(intent);
        fromActivity.finish();
    }
}
