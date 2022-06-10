package at.wifi.swdev.notizapp.view;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class NoteViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    // Ziel: Ein NoteViewModel Objekt bauen


    private Application application;

    public NoteViewModelFactory(Application application) {
        this.application = application;

    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

       if(modelClass == NoteViewModel.class){
           return (T) new NoteViewModel(application);
       }


        return super.create(modelClass);
    }

}
