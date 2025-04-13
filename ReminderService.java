
// خدمة تشغيل التذكيرات - 
package com.dua.app.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import com.dua.app.data.NoteRepository;
import com.dua.app.model.NoteEntity;
import com.dua.app.util.NotificationHelper;
import com.dua.app.receiver.ReminderReceiver;
import java.util.List;

public class ReminderService extends Service {
    private NoteRepository noteRepository;
    
    @Override
    public void onCreate() {
        super.onCreate();
        noteRepository = new NoteRepository(getApplication());
        
        // مراقبة الملاحظات التي تحتوي على تذكيرات
        noteRepository.getReminderNotes().observeForever(notes -> {
            scheduleReminders(notes);
        });
    }
    
    private void scheduleReminders(List<NoteEntity> reminderNotes) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        
        for (NoteEntity note : reminderNotes) {
            if (note.getReminderDate() != null && note.isReminder()) {
                Intent intent = new Intent(this, ReminderReceiver.class);
                intent.putExtra("NOTE_ID", note.getId());
                intent.putExtra("NOTE_TITLE", note.getTitle());
                intent.putExtra("NOTE_CONTENT", note.getContent());
                
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    this,
                    (int) note.getId(),
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
                );
                
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    note.getReminderDate().getTime(),
                    pendingIntent
                );
            }
        }
    }
    
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
