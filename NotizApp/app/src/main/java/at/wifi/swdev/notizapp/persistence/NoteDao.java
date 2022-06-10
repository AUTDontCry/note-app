package at.wifi.swdev.notizapp.persistence;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {

    // Queries

    // bringt mir die ID des neu erstellten

    @Insert
    void insert(Note note);

    @Insert
    void insert(Note... notes);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("DELETE FROM notes")
    void deleteAll();


    //Livedata sorgt das es in einen eigenen Thread geführt wird und man benachrichtigt wird soblad sich etwas verändert
    @Query("SELECT * FROM notes ORDER BY created_at ASC")
    LiveData<List<Note>> getAllNotes();


    @Query("SELECT * FROM notes WHERE id = :id")
    LiveData<Note> getNoteById(int id);

}
