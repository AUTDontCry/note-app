package at.wifi.swdev.notizapp.persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(version = 1, entities = {Note.class}, exportSchema = false)
@TypeConverters({Converter.class})
public abstract class NoteDatebase extends RoomDatabase {


    // SingeLeton

    private static NoteDatebase INSTANCE;

    // Threads für DB Abfragen zuweisen standart GUI (dürfen nicht im UI Thread Laufen)

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService datbaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    public static NoteDatebase getInstance(Context context) {

        // Gibt es schon eine Instanz?
        if (INSTANCE == null) {
            // Nein, wir erzeugen die einzige Instanz


            // Erzeugt das diesen Bereich nur von einen Thread betreten werden kann
            synchronized (NoteDatebase.class) {

                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, NoteDatebase.class, "database.sqlite").build();
                }
            }
        }

        return INSTANCE;
    }

    // Getter für die DAOs
    public abstract NoteDao noteDao();

}