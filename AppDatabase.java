// قاعدة البيانات - AppDatabase.java
package com.dua.app.data;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import com.dua.app.model.NoteEntity;
import com.dua.app.model.AttachmentEntity;
import com.dua.app.model.ClipboardEntity;

@Database(entities = {NoteEntity.class, AttachmentEntity.class, ClipboardEntity.class}, version = 1)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;
    
    public abstract NoteDao noteDao();
    public abstract AttachmentDao attachmentDao();
    public abstract ClipboardDao clipboardDao();
    
    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                context.getApplicationContext(),
                AppDatabase.class,
                "dua_notes_database")
                .fallbackToDestructiveMigration()
                .build();
        }
        return instance;
    }
}

