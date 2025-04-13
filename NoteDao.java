// واجهة Data Access - NoteDao.java
package com.dua.app.data;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import java.util.List;
import com.dua.app.model.NoteEntity;

@Dao
public interface NoteDao {
    @Insert
    void insert(NoteEntity note);
    
    @Update
    void update(NoteEntity note);
    
    @Delete
    void delete(NoteEntity note);
    
    @Query("SELECT * FROM notes ORDER BY creationDate DESC")
    LiveData<List<NoteEntity>> getAllNotes();
    
    @Query("SELECT * FROM notes WHERE isReminder = 1 ORDER BY reminderDate ASC")
    LiveData<List<NoteEntity>> getReminderNotes();
    
    @Query("SELECT * FROM notes WHERE id = :id")
    LiveData<NoteEntity> getNoteById(long id);
}

