package at.wifi.swdev.notizapp.persistence;


import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {

    private NoteDatebase noteDatebase;
    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application) {

        // Datenbank holen
        noteDatebase = NoteDatebase.getInstance(application);
        noteDao = noteDatebase.noteDao();
        allNotes = noteDao.getAllNotes();
    }


    public void insert(Note note) {
        // Das Runnable führt die Datenbank-Abfrage in einem eigenen Thread aus   // hier wird gezielt gesagt das es in einen eigenen Thread laufen muss
        NoteDatebase.datbaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                noteDao.insert(note);
            }
        });
    }

    public void update(Note note) {
        NoteDatebase.datbaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                noteDao.update(note);
            }
        });
    }


    public void delete(Note note) {
        NoteDatebase.datbaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                noteDao.delete(note);
            }
        });
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }


}
