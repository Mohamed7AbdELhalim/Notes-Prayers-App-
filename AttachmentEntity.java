
// النموذج الأساسي (Model) - AttachmentEntity.java
package com.dua.app.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import java.util.Date;

@Entity(tableName = "attachments",
       foreignKeys = @ForeignKey(entity = NoteEntity.class,
                                 parentColumns = "id",
                                 childColumns = "noteId",
                                 onDelete = ForeignKey.CASCADE))
public class AttachmentEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long noteId;
    private String filePath;
    private String fileType;
    private Date addedDate;
    
    // Getters and Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    
    public long getNoteId() { return noteId; }
    public void setNoteId(long noteId) { this.noteId = noteId; }
    
    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
    
    public String getFileType() { return fileType; }
    public void setFileType(String fileType) { this.fileType = fileType; }
    
    public Date getAddedDate() { return addedDate; }
    public void setAddedDate(Date addedDate) { this.addedDate = addedDate; }
}

