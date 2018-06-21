package com.example.musa.studentdatabase.DATABASE;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.provider.ContactsContract;

@Entity(tableName = "student")
public class StudentDetails {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String Sname;
    public String Fname;
    public int Age;
    public int ImageResource;

    public int getImageResource() {
        return ImageResource;
    }

    public void setImageResource(int imageResource) {
        ImageResource = imageResource;
    }

    @Ignore
    public StudentDetails(String sname, String fname, int age) {
        Sname = sname;
        Fname = fname;
        Age = age;
    }

    public StudentDetails(String Sname, String Fname, int Age, int id) {
        this.Sname = Sname;
       this. Fname = Fname;
        this.Age = Age;
        this.id = id;
    }

    public int getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }

    public String getSname() {
        return Sname;
    }

    public void setSname(String sname) {
        Sname = sname;
    }

    public String getFname() {
        return Fname;
    }

    public void setFname(String fname) {
        Fname = fname;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }
}
