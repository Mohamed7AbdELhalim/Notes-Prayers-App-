
// واجهة المستخدم - 
package com.dua.app.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.dua.app.R;
import com.dua.app.model.NoteEntity;
import com.dua.app.util.ThemeManager;
import com.dua.app.viewmodel.ReminderViewModel;

public class PopupReminderActivity extends AppCompatActivity {
    private ReminderViewModel viewModel;
    private ThemeManager themeManager;
    private long noteId;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        themeManager = new ThemeManager(this);
        if (themeManager.isDarkTheme()) {
            setTheme(R.style.Theme_DuaApp_Popup_Dark);
        } else if (themeManager.isIslamicGreenTheme()) {
            setTheme(R.style.Theme_DuaApp_Popup_IslamicGreen);
        } else {
            setTheme(R.style.Theme_DuaApp_Popup_Light);
        }
        
        setContentView(R.layout.activity_popup_reminder);
        
        viewModel = new ViewModelProvider(this).get(ReminderViewModel.class);
        
        TextView titleTextView = findViewById(R.id.text_title);
        TextView contentTextView = findViewById(R.id.text_content);
        Button closeButton = findViewById(R.id.button_close);
        Button postponeButton = findViewById(R.id.button_postpone);
        
        if (getIntent().hasExtra("NOTE_ID")) {
            noteId = getIntent().getLongExtra("NOTE_ID", -1);
            String title = getIntent().getStringExtra("NOTE_TITLE");
            String content = getIntent().getStringExtra("NOTE_CONTENT");
            
            titleTextView.setText(title);
            contentTextView.setText(content);
            
            viewModel.loadNote(noteId);
        }
        
        closeButton.setOnClickListener(v -> finish());
        
        postponeButton.setOnClickListener(v -> {
            viewModel.postponeReminder(noteId, 30); // تأجيل 30 دقيقة
            finish();
        });
    }
}
