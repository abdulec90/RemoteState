package com.remotestate;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.remotestate.database.LocationDao;
import com.remotestate.database.LocationDatabase;
import com.remotestate.database.LocationTable;

import java.util.List;

public class LocationRepository {

    private LocationDao locationDao;
    private LiveData<List<LocationTable>> allLocation;

    public LocationRepository(Application application) { //application is subclass of context
        LocationDatabase locationDatabase = LocationDatabase.getInstance(application);
        locationDao = locationDatabase.locationDao();
        allLocation = locationDao.getAllLatLng(50,0);
    }

    //methods for database operations :-
    public void insert(LocationTable note) {
        new InsertNoteAsyncTask(locationDao).execute(note);
    }

    public void update(LocationTable note) {
        new UpdateNoteAsyncTask(locationDao).execute(note);
    }

    public void delete(LocationTable note) {
        new DeleteNoteAsyncTask(locationDao).execute(note);
    }

    public void deleteAllNotes() {
        new DeleteAllNotesAsyncTask(locationDao).execute();
    }

    public LiveData<List<LocationTable>> getAllLatLngs() {
        return allLocation;
    }


    private static class InsertNoteAsyncTask extends AsyncTask<LocationTable, Void, Void> { //static : doesnt have reference to the
        // repo itself otherwise it could cause memory leak!
        private LocationDao locationDao;

        private InsertNoteAsyncTask(LocationDao locationDao) {
            this.locationDao = locationDao;
        }

        @Override
        protected Void doInBackground(LocationTable... notes) { // ...  is similar to array
            locationDao.Insert(notes[0]); //single note
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<LocationTable, Void, Void> {
        private LocationDao locationDao;

        private UpdateNoteAsyncTask(LocationDao noteDao) { //constructor as the class is static
            this.locationDao = noteDao;
        }

        @Override
        protected Void doInBackground(LocationTable... notes) {
            locationDao.Update(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<LocationTable, Void, Void> {
        private LocationDao locationDao;

        private DeleteNoteAsyncTask(LocationDao locationDao) {
            this.locationDao = locationDao;
        }

        @Override
        protected Void doInBackground(LocationTable... notes) {
            locationDao.Delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {
        private LocationDao locationDao;

        private DeleteAllNotesAsyncTask(LocationDao locationDao) {
            this.locationDao = locationDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            locationDao.DeleteAllNotes();
            return null;
        }
    }
}
