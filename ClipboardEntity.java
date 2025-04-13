// النموذج الأساسي (Model) - ClipboardEntity.java
package com.dua.app.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.Date;

@Entity(tableName = "clipboard_items")
public class ClipboardEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String content;
    private String contentType; // TEXT, IMAGE, FILE
    private String filePath; // For non-text content
    private Date savedDate;
    
    // Getters and Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    
    public String getContentType() { return contentType; }
    public void setContentType(String contentType) { this.contentType = contentType; }
    
    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
    
    public Date getSavedDate() { return savedDate; }
    public void setSavedDate(Date savedDate) { this.savedDate = savedDate; }
}

