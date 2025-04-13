
// مدير الثيمات - 
package com.dua.app.util;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;

public class ThemeManager {
    private static final String KEY_TEXT_COLOR = "text_color";
    private static final String KEY_BACKGROUND_COLOR = "background_color";
    private static final String KEY_THEME_TYPE = "theme_type";
    
    private SharedPreferences preferences;
    
    public ThemeManager(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }
    
    public String getTextColor() {
        return preferences.getString(KEY_TEXT_COLOR, "#000000"); // أسود افتراضي
    }
    
    public void setTextColor(String color) {
        preferences.edit().putString(KEY_TEXT_COLOR, color).apply();
    }
    
    public String getBackgroundColor() {
        return preferences.getString(KEY_BACKGROUND_COLOR, "#FFFFFF"); // أبيض افتراضي
    }
    
    public void setBackgroundColor(String color) {
        preferences.edit().putString(KEY_BACKGROUND_COLOR, color).apply();
    }
    
    public String getThemeType() {
        return preferences.getString(KEY_THEME_TYPE, "islamic_green"); // ثيم إسلامي أخضر افتراضي
    }
    
    public void setThemeType(String themeType) {
        preferences.edit().putString(KEY_THEME_TYPE, themeType).apply();
    }
    
    public boolean isIslamicGreenTheme() {
        return "islamic_green".equals(getThemeType());
    }
    
    public boolean isDarkTheme() {
        return "dark".equals(getThemeType());
    }
    
    public int getPrimaryColor() {
        if (isIslamicGreenTheme()) {
            return android.graphics.Color.parseColor("#0c6b31"); // اللون الأخضر الإسلامي
        } else if (isDarkTheme()) {
            return android.graphics.Color.parseColor("#121212"); // لون داكن
        } else {
            return android.graphics.Color.parseColor("#3F51B5"); // لون أزرق افتراضي
        }
    }
}
