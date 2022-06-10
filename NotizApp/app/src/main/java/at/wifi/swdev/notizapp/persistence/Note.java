package at.wifi.swdev.notizapp.persistence;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity(tableName = "notes")
public class Note implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String title;

    @NonNull
    public String content;

    public boolean done;

    @ColumnInfo(name = "created_at")
    public LocalDateTime createdAt;


    public Note(@NonNull String title, @NonNull String content) {
        this.title = title;
        this.content = content;
        done = false;
        createdAt =  LocalDateTime.now();
    }
}

