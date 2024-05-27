package com.example.test_firebase;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText editTextName, editTextEmail;
    Button buttonAdd;
    ListView listViewStudents;

    ArrayList<Student> students;
    StudentListAdapter adapter;

    DatabaseReference databaseStudents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseStudents = FirebaseDatabase.getInstance().getReference("students");

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        buttonAdd = findViewById(R.id.buttonAdd);
        listViewStudents = findViewById(R.id.listViewStudents);

        students = new ArrayList<>();
        adapter = new StudentListAdapter(this, students);
        listViewStudents.setAdapter(adapter);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStudent();
            }
        });

        databaseStudents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                students.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Student student = postSnapshot.getValue(Student.class);
                    students.add(student);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Failed to load students.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addStudent() {
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();

        if (!name.isEmpty() && !email.isEmpty()) {
            String id = databaseStudents.push().getKey();
            Student student = new Student(id, name, email);
            databaseStudents.child(id).setValue(student);

            editTextName.setText("");
            editTextEmail.setText("");

            Toast.makeText(this, "Student added", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Please enter a name and email", Toast.LENGTH_SHORT).show();
        }
    }
}
