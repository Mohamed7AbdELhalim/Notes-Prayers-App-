
// واجهة المستخدم - 
package com.dua.app.ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import com.dua.app.R;
import com.dua.app.service.ClipboardMonitorService;
import com.dua.app.service.ReminderService;
import com.dua.app.util.ThemeManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private ThemeManager themeManager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        themeManager = new ThemeManager(this);
        applyTheme();
        
        setContentView(R.layout.activity_main);
        
        // إعداد شريط التنقل السفلي
        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
        
        // بدء الخدمات
        startService(new Intent(this, ReminderService.class));
        startService(new Intent(this, ClipboardMonitorService.class));
    }
    
    private void applyTheme() {
        if (themeManager.isDarkTheme()) {
            setTheme(R.style.Theme_DuaApp_Dark);
        } else if (themeManager.isIslamicGreenTheme()) {
            setTheme(R.style.Theme_DuaApp_IslamicGreen);
        } else {
            setTheme(R.style.Theme_DuaApp_Light);
        }
    }
}
