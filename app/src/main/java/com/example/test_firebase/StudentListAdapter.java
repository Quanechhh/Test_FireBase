package com.example.test_firebase;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class StudentListAdapter extends ArrayAdapter<Student> {
    private Activity context;
    private List<Student> studentList;

    public StudentListAdapter(Activity context, List<Student> studentList) {
        super(context, R.layout.layout_student_list, studentList);
        this.context = context;
        this.studentList = studentList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_student_list, null, true);

        TextView textViewName = listViewItem.findViewById(R.id.textViewName);
        TextView textViewEmail = listViewItem.findViewById(R.id.textViewEmail);

        Student student = studentList.get(position);
        textViewName.setText(student.name);
        textViewEmail.setText(student.email);

        return listViewItem;
    }
}

