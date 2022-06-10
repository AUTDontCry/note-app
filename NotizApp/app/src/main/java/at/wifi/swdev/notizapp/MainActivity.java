package at.wifi.swdev.notizapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import java.util.List;

import at.wifi.swdev.notizapp.databinding.ActivityMainBinding;
import at.wifi.swdev.notizapp.persistence.Note;
import at.wifi.swdev.notizapp.view.NoteListAdapter;
import at.wifi.swdev.notizapp.view.NoteViewModel;
import at.wifi.swdev.notizapp.view.NoteViewModelFactory;
import at.wifi.swdev.notizapp.view.SwipeToDeleteCallback;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private RecyclerView recyclerView;
    private NoteViewModel viewModel;

    private static final int CREATE_NOTE_REQUEST = 1;
    private static final int EDIT_NOTE_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.Theme_NotizApp);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        recyclerView = binding.recyclerView;


        //Ans  ViewModel "andocken"
        viewModel = new ViewModelProvider(this, new NoteViewModelFactory(getApplication())).get(NoteViewModel.class);





        // Daten -> Adapter

        NoteListAdapter adapter = new NoteListAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        viewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                // Animation einbinden
                LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(recyclerView.getContext(), R.anim.layout_animation);
                recyclerView.setLayoutAnimation(animationController);

                // Es gibt neue Daten
                // - > Daten in den Adapter stecken
                adapter.setNotes(notes);
            }
        });



        // Adapter -> Liste

        recyclerView.setAdapter(adapter);

        // Auf geklickten Notizen reagieren
        adapter.getClickedNote().subscribe(new io.reactivex.rxjava3.core.Observer<Note>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                // egal
            }

            @Override
            public void onNext(@NonNull Note note) {
                // Auf diese Note wurde gerade geklickt
                // -> Intent starten
                Intent intent = new Intent(MainActivity.this, EditNoteActivity.class);
                intent.putExtra(EditNoteActivity.UPDATE_NOTE_EXTRA, note);
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }




            @Override
            public void onError(@NonNull Throwable e) {
                // egal
            }

            @Override
            public void onComplete() {
                //egal
            }
        });


        // "Swipe to Delete" aufbindne
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(adapter, viewModel));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public void startNewNoteActivity(View view){
        // Neue Activity starten und als Result die neue Notiz bekommen

        // Intent Bauen

        Intent intent = new Intent(this, CreateNoteActivity.class);
        startActivityForResult(intent, CREATE_NOTE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Ist das unser Request?
        if(resultCode == RESULT_OK && data != null){
            //Sind auch Daten dabei?

            Note note;

            switch(requestCode){

                case CREATE_NOTE_REQUEST:
                    note = (Note) data.getSerializableExtra(CreateNoteActivity.NEW_NOTE_EXTRA);
                    viewModel.insert(note);
                    break;
                case EDIT_NOTE_REQUEST:
                    note = (Note) data.getSerializableExtra(EditNoteActivity.UPDATE_NOTE_EXTRA);
                    viewModel.update(note);
                    break;
            }
        }
    }
}