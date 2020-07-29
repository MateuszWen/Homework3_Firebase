package com.mw.homework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.mw.homework.tasks.TaskListContent;

import java.util.Random;

public class AddBookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
    }

    public void onClickButtonAdd(View view) {

        EditText author_name = findViewById(R.id.edittext_author_name);
        EditText book_title = findViewById(R.id.edittext_book_title);
        String author_nameString = author_name.getText().toString();
        String book_titleString = book_title.getText().toString();

        //Add a default Task object to myTask list if no data is input in EditTexts
        TaskListContent.addItem(new TaskListContent.Book(((TaskListContent.ITEMS.get(TaskListContent.ITEMS.size()-1).getId())), book_titleString, author_nameString));
        //Notify the TaskFragment adapter that the dataset change

        //Automatically hide the keyboard after AddButton press
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        finish();
    }

    @Override
    public void finish(){
        Intent returnIntent = new Intent();
        setResult(RESULT_OK, returnIntent); //By not passing the intent in the result, the calling activity will get null data.
        super.finish();
    }
}
