package com.remotestate;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.remotestate.database.LocationTable;

import java.util.List;

public class LocationViewModel extends AndroidViewModel {

    private LocationRepository repository;
    private LiveData<List<LocationTable>> allNotes;

    public LocationViewModel(@NonNull Application application) {
        super(application);
        repository = new LocationRepository(application);
        allNotes = repository.getAllLatLngs();
    }


    public void insert(LocationTable locationTable) {
        repository.insert(locationTable);
    }

    public void update(LocationTable locationTable) {
        repository.update(locationTable);
    }

    public void delete(LocationTable locationTable) {
        repository.delete(locationTable);
    }

    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }

    public LiveData<List<LocationTable>> getAllNotes() {
        return allNotes;
    }
}
