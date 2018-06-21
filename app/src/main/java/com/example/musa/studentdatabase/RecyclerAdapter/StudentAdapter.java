package com.example.musa.studentdatabase.RecyclerAdapter;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.musa.studentdatabase.DATABASE.StudentDetails;
import com.example.musa.studentdatabase.R;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {
    public static OnItemCLickListener mlistner;
    public static List<StudentDetails> studentdetails;
    private  Context mcontext;

    public StudentAdapter(Context context,OnItemCLickListener listener ) {
        mlistner=listener;
        this.mcontext=context;

    }

    public static List<StudentDetails> getStudentdetails() {
        return studentdetails;
    }

    public void setStudentdetails(List<StudentDetails> studentdetails) {
        StudentAdapter.studentdetails = studentdetails;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.layoutforstudentrecyclerview,parent,false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StudentDetails studentDetails=studentdetails.get(position);
        holder.Surname.setText(studentDetails.getSname());
        holder.firstname.setText(studentDetails.getFname());
        holder.imageView.setImageResource(studentDetails.getImageResource());

    }

    @Override
    public int getItemCount() {
        if (studentdetails==null){
            return 0;
        }
        else  return studentdetails.size();
    }

    public interface OnItemCLickListener{
        void onitemclicklistener(int elementid);
        void onimageclicked();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView firstname;
        private TextView Surname;
        private ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            firstname=itemView.findViewById(R.id.Firstname);
            Surname=itemView.findViewById(R.id.Surname);
            imageView=itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(this);
            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == itemView) {
                int Selected = studentdetails.get(getAdapterPosition()).getid();
                mlistner.onitemclicklistener(Selected);
            }
            else if (v instanceof ImageView){
                mlistner.onimageclicked();
            }

        }
    }
}
