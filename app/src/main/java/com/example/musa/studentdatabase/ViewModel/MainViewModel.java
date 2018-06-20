package com.example.musa.studentdatabase.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import com.example.musa.studentdatabase.DATABASE.AppDatabase;
import com.example.musa.studentdatabase.DATABASE.StudentDetails;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<StudentDetails>> studentDetailsLiveData;
    public MainViewModel(@NonNull Application application) {
        super(application);
        AppDatabase appdatabase = AppDatabase.getInstance(this.getApplication());
        studentDetailsLiveData = appdatabase.studentDao().loadStudents();

    }
    public LiveData<List<StudentDetails>> getStudentDetailsLiveData() {
        return studentDetailsLiveData;
    }

}

