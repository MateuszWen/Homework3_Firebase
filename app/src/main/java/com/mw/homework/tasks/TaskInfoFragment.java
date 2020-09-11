package com.mw.homework.tasks;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.mw.homework.MainActivity;
import com.mw.homework.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskInfoFragment extends Fragment {

    public TaskInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_task_info, container, false);
        return v;
    }

    public void displayTask(TaskListContent.Book bookDetails){                             // <-- służy do wyświetlania szczegółów danego Task'a
        FragmentActivity activity = getActivity(); //get the holding Activity

        //Find the elements used to display the data of the Task
        TextView authorName = activity.findViewById(R.id.bookInfoAuthor);
        TextView bookTitle = activity.findViewById(R.id.bookInfoTitle);

        //Apply the data
        authorName.setText(bookDetails.authorName);
        bookTitle.setText( bookDetails.bookTitle);
    }

    //metoda służy do odczytywania danych dostarczonych z klasu MainActivity w obiekcie klasy Intent (że jak na oknie gównym wybraliśmy któryś kontakt, to metoda ta odpowiada za przesłanie informacji o tym kontakcie do kolejnego Activity)
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        //Get the intent golding the Task details and display them
        Intent intent = getActivity().getIntent();
        if(intent != null){
            TaskListContent.Book reveivedBook = intent.getParcelableExtra(MainActivity.taskExtra);
            if(reveivedBook != null){
                displayTask(reveivedBook);
            }
        }
    }
}
