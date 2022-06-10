package at.wifi.swdev.notizapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;

import at.wifi.swdev.notizapp.databinding.ActivityCreateNoteBinding;
import at.wifi.swdev.notizapp.persistence.Note;

public class CreateNoteActivity extends AppCompatActivity {

    private ActivityCreateNoteBinding binding;

    public static final String NEW_NOTE_EXTRA = "newNote";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCreateNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // Title der Actionbar setzen
        ActionBar actionBar = getSupportActionBar();
        if(actionBar !=null){
            actionBar.setTitle("Neue Notiz erstellen");
        }

    }

    public void saveNote(View view) {

        String title = binding.newTitle.getText().toString();
        String content = binding.newContent.getText().toString();


        // Note-Objekt erstellen


        //TODO: Validierung
        Note note = new Note(title, content);



        // Als Resultat zurücksenden

        Intent intent = new Intent();
        intent.putExtra(NEW_NOTE_EXTRA, note);

        setResult(RESULT_OK, intent);

        finish();
    }
}