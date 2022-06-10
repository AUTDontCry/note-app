package at.wifi.swdev.notizapp.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import at.wifi.swdev.notizapp.R;
import at.wifi.swdev.notizapp.persistence.Note;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteViewHolder> {

    private Context context;
    private LayoutInflater inflater; // Aufblaser
    private List<Note> notes;
    private PublishSubject<Note> onClickSubject = PublishSubject.create();

    public NoteListAdapter(Context context){

        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    public void setNotes(List<Note> notes) {
        this.notes = notes;
        // Dem Adapter mitteilen, das sich die Daten geändert haben
        notifyDataSetChanged();
    }




    public Context getContext(){
        return context;
    }


    public Note getNoteAt(int position){

        // Gibt es die Liste schon?

        if(notes != null){
            return notes.get(position);

        }

        //Null, wenn nicht
        return null;
    }


    public Observable<Note> getClickedNote(){

        return onClickSubject;

    }



// Erstellen der View
    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Hier erzeugen wir viewHolder-Objekte (Objekt pro verwendeten Listenelement)

        //1 Aus dem XML - Layout ein Objekt erstllen

        View view = inflater.inflate(R.layout.note_list_item,parent,false);

        // 2 Aus View einen ViewHolder erzeugen

        return new NoteViewHolder(view);
    }





    // Aufbinden der Werte auf die Felder
    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {

        // Datensatz im Listenelement (ViewHolder) anzeigen
        // "Anzeigfläche"

        // Haben wir schon Daten(Notizen)

        if(notes != null){

            // Notiz an der Stelle "position"

            Note note = notes.get(position);

            holder.titelTv.setText(note.title);
            holder.contentTv.setText(note.content);



            holder.doneIv.setVisibility(note.done ? View.VISIBLE : View.INVISIBLE);
            //Längere Form macht das gleiche wie oben!

        //    if(note.done){
           //     holder.doneIv.setVisibility(View.VISIBLE);
          //  }else{
            //    holder.doneIv.setVisibility(View.INVISIBLE);
            //}



          //Click - Handler aufbinden
          holder.itemView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  // Wir schicken die geklickte Note "durch den Kanal"!
                  onClickSubject.onNext(note);
              }
          });
        }
    }

    @Override
    public int getItemCount() {

        // Wenn Notizen vorhanden sind geben wir eine Anzahl der Elemente zurück ansonsten springt das runter zu return 0!!!!

        if (notes !=null){
            return notes.size();
        }

        return 0;
    }


    // Innere Klassen für den ViewHolder
    //Brücke von Adapter zu Layout-Datei

    public class NoteViewHolder extends RecyclerView.ViewHolder{

        private final TextView titelTv;
        private final TextView contentTv;
        private final ImageView doneIv;



        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            titelTv =  itemView.findViewById(R.id.noteTitle);
            contentTv= itemView.findViewById(R.id.noteContent);
            doneIv = itemView.findViewById(R.id.done);


        }
    }



}
