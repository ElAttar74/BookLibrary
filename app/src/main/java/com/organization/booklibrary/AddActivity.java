package com.organization.booklibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {
    EditText title_input, author_input, pages_input;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        title_input = (EditText) findViewById(R.id.title_input2);
        author_input = (EditText) findViewById(R.id.author_input2);
        pages_input = (EditText) findViewById(R.id.pages_input2);

        btnAdd = (Button)findViewById(R.id.btnUpdate);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this);
                myDB.addBook(title_input.getText().toString().trim(),
                        author_input.getText().toString().trim(),
                        Integer.valueOf(pages_input.getText().toString().trim()));
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });
    }
}