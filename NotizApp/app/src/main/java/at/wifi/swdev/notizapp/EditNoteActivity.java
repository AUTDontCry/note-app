package at.wifi.swdev.notizapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.time.format.DateTimeFormatter;

import at.wifi.swdev.notizapp.databinding.ActivityEditNoteBinding;
import at.wifi.swdev.notizapp.persistence.Note;

public class EditNoteActivity extends AppCompatActivity {

    public static final String UPDATE_NOTE_EXTRA = "updateNote";
    private ActivityEditNoteBinding binding;
    private Note note;

    //Zeit formatiererer
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEditNoteBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        // Felder mit den akutellen Wert der Notiz befüllen
        // 1 Wir brauchen die Notiz aus dem Intent

        Intent intent = getIntent();
        note = (Note) intent.getSerializableExtra(UPDATE_NOTE_EXTRA);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Notiz bearbeiten!");
            actionBar.setSubtitle("Notiz erstellt am" + note.createdAt.format(formatter));
            // TODO:
        }


        // 2 Wir setzen die Werte der Notiz in die Textfelder

        binding.editTitle.setText(note.title);
        binding.editContent.setText(note.content);
        binding.editDone.setChecked(note.done);
    }

    public void updateNote(View view) {

        // UE
        //Felder auslesen

        note.title = binding.editTitle.getText().toString();
        note.content = binding.editContent.getText().toString();
        note.done = binding.editDone.isChecked();

        // UE - Entity um ein Updatet_at Feld erweitern


        //In Notiz verpacken
        //*Keine* neue Notiz erstellen (bzw. unbedingt die ID mitkopieren)
        // Sonst schlägt Update fehl da keine ID

        Intent intent = new Intent();
        intent.putExtra(UPDATE_NOTE_EXTRA, note);


        setResult(RESULT_OK, intent);
        finish();

    }
}