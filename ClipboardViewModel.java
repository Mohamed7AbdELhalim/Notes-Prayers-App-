
// نموذج العرض - ClipboardViewModel.java
package com.dua.app.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;
import com.dua.app.data.ClipboardRepository;
import com.dua.app.model.ClipboardEntity;
import java.util.Date;

public class ClipboardViewModel extends AndroidViewModel {
    private ClipboardRepository repository;
    private LiveData<List<ClipboardEntity>> todayClipboardItems;
    
    public ClipboardViewModel(Application application) {
        super(application);
        repository = new ClipboardRepository(application);
        todayClipboardItems = repository.getTodayItems();
    }
    
    public LiveData<List<ClipboardEntity>> getTodayItems() {
        return todayClipboardItems;
    }
    
    public LiveData<List<ClipboardEntity>> getItemsByDateRange(Date start, Date end) {
        return repository.getItemsByDateRange(start, end);
    }
    
    public void insert(ClipboardEntity item) {
        repository.insert(item);
    }
    
    public void delete(ClipboardEntity item) {
        repository.delete(item);
    }
    
    public void clearOldItems(Date olderThan) {
        repository.clearOldItems(olderThan);
    }
}
