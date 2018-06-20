package com.example.musa.studentdatabase.Activities;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.musa.studentdatabase.APPEXECUTORS.AppExecutors;
import com.example.musa.studentdatabase.DATABASE.AppDatabase;
import com.example.musa.studentdatabase.DATABASE.StudentDetails;
import com.example.musa.studentdatabase.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddStudentsDetails extends AppCompatActivity {
   @BindView(R.id.EditTextFirstName)android.support.design.widget.TextInputEditText Firstname;
    @BindView(R.id.EditTextSurname)android.support.design.widget.TextInputEditText Surname;
private AppDatabase mdb;

    String Fname,Sname;
    int age;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_students_details);
        ButterKnife.bind(this);
        mdb=AppDatabase.getInstance(getApplicationContext());
    }
    public void Getters(){
        Fname=Firstname.getText().toString();
        Sname=Surname.getText().toString();
        age=5;
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
