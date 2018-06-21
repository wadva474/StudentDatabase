package com.example.musa.studentdatabase.Activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.musa.studentdatabase.APPEXECUTORS.AppExecutors;
import com.example.musa.studentdatabase.DATABASE.AppDatabase;
import com.example.musa.studentdatabase.DATABASE.StudentDetails;
import com.example.musa.studentdatabase.R;
import com.example.musa.studentdatabase.ViewModel.StudentDetailViewModel;
import com.example.musa.studentdatabase.ViewModel.StudentDetailsViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddStudentsDetails extends AppCompatActivity {
   @BindView(R.id.EditTextFirstName)android.support.design.widget.TextInputEditText Firstname;
    @BindView(R.id.EditTextSurname)android.support.design.widget.TextInputEditText Surname;
    @BindView(R.id.addStudent)Button mButton;
private AppDatabase mdb;

    String Fname,Sname;
    int age;
    private static final int DEFAULT_TASK_ID = -1;
    private int mTaskId = DEFAULT_TASK_ID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_students_details);
        ButterKnife.bind(this);
        mdb = AppDatabase.getInstance(getApplicationContext());
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("StudentId")) {
            mButton.setText(R.string.update);
            if (mTaskId == DEFAULT_TASK_ID) {
                // populate the UI
                mTaskId = intent.getIntExtra("StudentId", DEFAULT_TASK_ID);
                final StudentDetailsViewModel studentDetailsViewModel=new StudentDetailsViewModel(mTaskId,mdb);
                final StudentDetailViewModel studentDetailViewModel=ViewModelProviders.of(this,studentDetailsViewModel).get(StudentDetailViewModel.class);
                studentDetailViewModel.getLiveData().observe(this, new Observer<StudentDetails>() {
                    @Override
                    public void onChanged(@Nullable StudentDetails studentDetails) {
                        studentDetailViewModel.getLiveData().removeObserver(this);
                        populateUI(studentDetails);
                    }
                });
            }
        }
    }

    @OnClick(R.id.addStudent)
    public void AddUpdateStudent(View view ){
        OnsavedButtonClicked();
        finish();
    }

    public void OnsavedButtonClicked(){
        Getters();
        final StudentDetails studentDetails=new StudentDetails(Fname,Sname,5);
        AppExecutors.getsintance().getDataIO().execute(new Runnable() {
            @Override
            public void run() {
                if (mTaskId==DEFAULT_TASK_ID){
                    mdb.studentDao().insertStudents(studentDetails);
                }
                else studentDetails.setid(mTaskId);
                mdb.studentDao().updateStudents(studentDetails);
            }
        });
        finish();



    }




    private void populateUI(StudentDetails studentDetails) {
        if (studentDetails == null) {
            return;
        }

        Firstname.setText(studentDetails.getFname());
        Surname.setText(studentDetails.getSname());
    }
    public void Getters(){
        Fname=Firstname.getText().toString();
        Sname=Surname.getText().toString();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=new MenuInflater(getApplicationContext());
        menuInflater.inflate(R.menu.adding,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int Selected =item.getItemId();
        switch (Selected){

            case R.id.addStudent:
                Getters();
                    if (!(Fname.isEmpty()||Sname.isEmpty()))
                    AppExecutors.getsintance().getDataIO().execute(new Runnable() {
                        @Override
                        public void run() {
                         mdb.studentDao().insertStudents(new StudentDetails(Sname,Fname,age));
                        }
                    });
                    else Toast.makeText(getApplicationContext(),"Enter a valid student Detail",Toast.LENGTH_LONG).show();
                    finish();
                }

        return super.onOptionsItemSelected(item);
    }



}
