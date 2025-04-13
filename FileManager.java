
// مدير الملفات - 
package com.dua.app.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FileManager {
    private static final String TAG = "FileManager";
    private Context context;
    
    public FileManager(Context context) {
        this.context = context;
    }
    
    public String saveAttachment(Uri uri, long noteId) throws IOException {
        File directory = getAttachmentsDirectory(noteId);
        return copyFileToDirectory(uri, directory);
    }
    
    public String saveClipboardFile(Uri uri) throws IOException {
        File directory = getClipboardFilesDirectory();
        return copyFileToDirectory(uri, directory);
    }
    
    private String copyFileToDirectory(Uri uri, File directory) throws IOException {
        if (!directory.exists()) {
            directory.mkdirs();
        }
        
        String fileName = getFileName(uri);
        File destinationFile = new File(directory, fileName);
        
        // إضافة رقم عشوائي إذا كان الملف موجود بالفعل
        if (destinationFile.exists()) {
            String name = fileName.substring(0, fileName.lastIndexOf('.'));
            String extension = fileName.substring(fileName.lastIndexOf('.'));
            destinationFile = new File(directory, name + "_" + System.currentTimeMillis() + extension);
        }
        
        try (InputStream inputStream = context.getContentResolver().openInputStream(uri);
             OutputStream outputStream = new FileOutputStream(destinationFile)) {
            
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            
            return destinationFile.getAbsolutePath();
        }
    }
    
    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = context.getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (columnIndex != -1) {
                        result = cursor.getString(columnIndex);
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "Error getting file name", e);
            }
        }
        
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        
        return result;
    }
    
    public String getFileNameFromPath(String path) {
        if (path == null) return null;
        int cut = path.lastIndexOf('/');
        if (cut != -1) {
            return path.substring(cut + 1);
        }
        return path;
    }
    
    private File getAttachmentsDirectory(long noteId) {
        File directory = new File(context.getExternalFilesDir(null), "attachments/" + noteId);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        return directory;
    }
    
    private File getClipboardFilesDirectory() {
        File directory = new File(context.getExternalFilesDir(null), "clipboard_files");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        return directory;
    }
    
    private File getClipboardLogsDirectory() {
        File directory = new File(context.getExternalFilesDir(null), "clipboard_logs");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        return directory;
    }
    
    public void appendToClipboardDailyLog(String content, Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        
        String currentDate = dateFormat.format(date);
        String currentTime = timeFormat.format(date);
        
        File directory = getClipboardLogsDirectory();
        File logFile = new File(directory, "clipboard_" + currentDate + ".txt");
        
        try (FileOutputStream fos = new FileOutputStream(logFile, true)) {
            String entry = "[" + currentTime + "] " + content + "\n\n";
            fos.write(entry.getBytes());
        } catch (IOException e) {
            Log.e(TAG, "Error writing to log file", e);
        }
    }
    
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }
}
