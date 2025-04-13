
// خدمة مراقبة الحافظة - 
package com.dua.app.service;

import android.app.Service;
import android.content.ClipboardManager;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;
import com.dua.app.data.ClipboardRepository;
import com.dua.app.model.ClipboardEntity;
import com.dua.app.util.FileManager;
import java.util.Date;
import java.io.File;
import java.io.FileOutputStream;

public class ClipboardMonitorService extends Service {
    private ClipboardManager clipboardManager;
    private ClipboardRepository clipboardRepository;
    private FileManager fileManager;
    
    @Override
    public void onCreate() {
        super.onCreate();
        clipboardRepository = new ClipboardRepository(getApplication());
        fileManager = new FileManager(this);
        
        clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
                saveClipboardContent();
            }
        });
    }
    
    private void saveClipboardContent() {
        if (!clipboardManager.hasPrimaryClip()) {
            return;
        }
        
        ClipData clipData = clipboardManager.getPrimaryClip();
        if (clipData == null || clipData.getItemCount() == 0) {
            return;
        }
        
        ClipData.Item item = clipData.getItemAt(0);
        ClipboardEntity entity = new ClipboardEntity();
        entity.setSavedDate(new Date());
        
        if (item.getText() != null) {
            // نص
            entity.setContentType("TEXT");
            entity.setContent(item.getText().toString());
        } else if (item.getUri() != null) {
            // صورة أو ملف
            try {
                String filePath = fileManager.saveClipboardFile(item.getUri());
                entity.setContentType("FILE");
                entity.setFilePath(filePath);
                entity.setContent("File: " + fileManager.getFileNameFromPath(filePath));
            } catch (Exception e) {
                e.printStackTrace();
                return; // فشل حفظ الملف
            }
        }
        
        clipboardRepository.insert(entity);
        fileManager.appendToClipboardDailyLog(entity.getContent(), entity.getSavedDate());
    }
    
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
