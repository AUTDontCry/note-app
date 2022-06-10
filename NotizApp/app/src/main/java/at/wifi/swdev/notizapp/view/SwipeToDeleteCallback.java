package at.wifi.swdev.notizapp.view;

import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import at.wifi.swdev.notizapp.R;
import at.wifi.swdev.notizapp.persistence.Note;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {

    private NoteListAdapter adapter;
    private NoteViewModel viewModel;
    private Note recentlyDeletedNote;
    private Drawable icon;
    private ColorDrawable background;




    public SwipeToDeleteCallback(NoteListAdapter adapter, NoteViewModel viewModel) {
        //Konfigurieren, in welche Richtungen geswiped und gedragged werden kann
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);

        this.adapter = adapter;
        this.viewModel = viewModel;

        // Drawable Resourcen laden

        icon = ContextCompat.getDrawable(adapter.getContext(), R.drawable.ic_delete);
        background = new ColorDrawable(adapter.getContext().getResources().getColor(R.color.red));

    }

    @Override
    public void onChildDraw(@NonNull Canvas canvas, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        View itemView = viewHolder.itemView;


        //Margin für das Icon ausrechnen

        int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;

        // Obere und untere Kante des Icons berechnen
        int iconTop = itemView.getTop() + iconMargin;
        int iconBottom = iconTop + icon.getIntrinsicHeight();

// Alternativ
//        iconBottom = itemView.getBottom() - iconMargin;







        // In welche Richtung wird geswiped?
        if (dX > 0) {
            // wir swipen von links nach rechts

            // Icon "reingleiten"

            int slideIn = 0;

            if((int) dX < iconMargin + icon.getIntrinsicWidth() + iconMargin){
                slideIn = (int) dX - icon.getIntrinsicWidth() - 2 * iconMargin;
            }


            background.setBounds(
                    itemView.getLeft(),
                    itemView.getTop(),
                    itemView.getLeft() + (int) dX,
                    itemView.getBottom()
            );

            icon.setBounds(
                    itemView.getLeft() + iconMargin + slideIn,
                    iconTop,
                    itemView.getLeft() + iconMargin + icon.getIntrinsicWidth() + slideIn,
                    iconBottom
            );
        } else if (dX < 0) {
            // swipe von rechts nach links

            int slideIn =0;

            if((int)dX > - 2 * iconMargin - icon.getIntrinsicWidth()){
                slideIn = (int) dX + icon.getIntrinsicWidth() + 2 * iconMargin;
            }


            background.setBounds(
                    itemView.getRight() + (int) dX,  // dX ist schon kleiner als 0, daher PLUS
                    itemView.getTop(),
                    itemView.getRight(),
                    itemView.getBottom()
            );

            icon.setBounds(
                    itemView.getRight() - iconMargin - icon.getIntrinsicWidth() + slideIn,
                    iconTop,
                    itemView.getRight() - iconMargin + slideIn,
                    iconBottom
            );


        } else {
            // View in Ausgangsposition
            background.setBounds(0,0,0,0);
            icon.setBounds(0,0,0,0);
        }

        // Hintergrund & Icon zeichnen
        // Reihenfolge ist wichtig
        background.draw(canvas);
        icon.draw(canvas);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

        // Egal wir sind nur an Swipe Gesten interessiert - wäre z.b. das halten der Notiz und neu anordnen wäre möglich
        return false;
    }


    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        // Element soll gelöscht werden

        // Welches Element soll gelöscht werden?
        int position = viewHolder.getAdapterPosition();

        // Notiz an der jeweiligen Position aus dem Adapter holen
        recentlyDeletedNote = adapter.getNoteAt(position);

        //Notiz aus der DB löschen
        viewModel.delete(recentlyDeletedNote);
        showUndoSnackbar(viewHolder.itemView);





    }

    private void showUndoSnackbar(View view) {

        // Snackbar anzeigen mit Undo- Button

        Snackbar.make(view, "Notiz gelöscht", Snackbar.LENGTH_LONG).setAction("Rückgängig", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Notiz wiederherstellen
                viewModel.insert(recentlyDeletedNote);
            }
        }).show();


    }


}
