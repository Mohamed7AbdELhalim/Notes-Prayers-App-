// نموذج العرض - NotesViewModel.java
package com.dua.app.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;
import com.dua.app.data.NoteRepository;
import com.dua.app.model.NoteEntity;

public class NotesViewModel extends AndroidViewModel {
    private NoteRepository repository;
    private LiveData<List<NoteEntity>> allNotes;
    private LiveData<List<NoteEntity>> reminderNotes;
    
    public NotesViewModel(Application application) {
        super(application);
        repository = new NoteRepository(application);
        allNotes = repository.getAllNotes();
        reminderNotes = repository.getReminderNotes();
    }
    
    public LiveData<List<NoteEntity>> getAllNotes() {
        return allNotes;
    }
    
    public LiveData<List<NoteEntity>> getReminderNotes() {
        return reminderNotes;
    }
    
    public void insert(NoteEntity note) {
        repository.insert(note);
    }
    
    public void update(NoteEntity note) {
        repository.update(note);
    }
    
    public void delete(NoteEntity note) {
        repository.delete(note);
    }
}
