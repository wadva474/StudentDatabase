package com.example.musa.studentdatabase.DATABASE;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public  interface StudentDao {

    @Query("SELECT * FROM student ORDER BY Sname")
    LiveData<List<StudentDetails>> loadStudents();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateStudents(StudentDetails studentDetails);

    @Insert
    void insertStudents(StudentDetails studentDetails);

    @Delete
    void deleteStudents(StudentDetails studentDetails);

    @Query("SELECT * FROM student WHERE id =:ID")
    LiveData<StudentDetails> loadStudentById(int ID);
}
