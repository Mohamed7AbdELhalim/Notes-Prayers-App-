
// مساعد الإشعارات - 
package com.dua.app.util;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import com.dua.app.R;
import com.dua.app.ui.PopupReminderActivity;

public class NotificationHelper {
    private static final String CHANNEL_ID = "dua_reminder_channel";
    private static final String CHANNEL_NAME = "التذكيرات";
    private Context context;
    private NotificationManager notificationManager;
    
    public NotificationHelper(Context context) {
        this.context = context;
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificationChannel();
    }
    
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("قناة تذكيرات الأدعية والملاحظات");
            notificationManager.createNotificationChannel(channel);
        }
    }
    
    public void showReminderNotification(long noteId, String title, String content) {
        Intent intent = new Intent(context, PopupReminderActivity.class);
        intent.putExtra("NOTE_ID", noteId);
        intent.putExtra("NOTE_TITLE", title);
        intent.putExtra("NOTE_CONTENT", content);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        
        PendingIntent pendingIntent = PendingIntent.getActivity(
            context,
            (int) noteId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );
        
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true);
        
        notificationManager.notify((int) noteId, builder.build());
    }
}
