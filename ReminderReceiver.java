
// مستقبل التذكيرات - 
package com.dua.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.dua.app.ui.PopupReminderActivity;
import com.dua.app.util.NotificationHelper;

public class ReminderReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        long noteId = intent.getLongExtra("NOTE_ID", -1);
        String title = intent.getStringExtra("NOTE_TITLE");
        String content = intent.getStringExtra("NOTE_CONTENT");
        
        // عرض الإشعار
        NotificationHelper notificationHelper = new NotificationHelper(context);
        notificationHelper.showReminderNotification(noteId, title, content);
        
        // فتح النافذة المنبثقة
        Intent popupIntent = new Intent(context, PopupReminderActivity.class);
        popupIntent.putExtra("NOTE_ID", noteId);
        popupIntent.putExtra("NOTE_TITLE", title);
        popupIntent.putExtra("NOTE_CONTENT", content);
        popupIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(popupIntent);
    }
}
