package com.example.musa.studentdatabase.Activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;

import com.example.musa.studentdatabase.APPEXECUTORS.AppExecutors;
import com.example.musa.studentdatabase.DATABASE.AppDatabase;
import com.example.musa.studentdatabase.DATABASE.StudentDetails;
import com.example.musa.studentdatabase.R;
import com.example.musa.studentdatabase.RecyclerAdapter.StudentAdapter;
import com.example.musa.studentdatabase.ViewModel.MainViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements StudentAdapter.OnItemCLickListener {
       @BindView(R.id.RecyclerViewForStudent) RecyclerView recyclerView;
       @BindView(R.id.toolbar) Toolbar toolbar;
       @BindView(R.id.fab) FloatingActionButton fab;
        private StudentAdapter Adapter;
        private AppDatabase mdb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mdb= AppDatabase.getInstance(getApplicationContext());
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        Adapter=new StudentAdapter(this,this);
        DividerItemDecoration decoration=new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(decoration);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                AppExecutors.getsintance().getDataIO().execute(new Runnable() {
                    @Override
                    public void run() {
                       mdb.studentDao().deleteStudents(StudentAdapter.getStudentdetails().get(viewHolder.getAdapterPosition()));
                    }
                });
            }
        }).attachToRecyclerView(recyclerView);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(Adapter);

        SetupViewModel();
    }

    @OnClick(R.id.fab)
    public void StartAddStudent(){
        Intent intent=new Intent(this,AddStudentsDetails.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onitemclicklistener(int elementid) {

    }
    public void SetupViewModel(){
        MainViewModel mainViewModel= ViewModelProviders.of(this).get(MainViewModel.class);
       mainViewModel.getStudentDetailsLiveData().observe(this, new Observer<List<StudentDetails>>() {
           @Override
           public void onChanged(@Nullable List<StudentDetails> studentDetails) {
               Adapter.setStudentdetails(studentDetails);
           }
       });
    }
}
