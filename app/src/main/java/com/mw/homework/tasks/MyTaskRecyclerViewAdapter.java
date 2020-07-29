package com.mw.homework.tasks;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mw.homework.R;
import com.mw.homework.tasks.TaskFragment.OnListFragmentInteractionListener;


import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link TaskListContent.Book} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */

//klasa odpowiada za zarządzanie wyświetlanymi treściami na ekranie
public class MyTaskRecyclerViewAdapter extends RecyclerView.Adapter<MyTaskRecyclerViewAdapter.ViewHolder> {

    private final List<TaskListContent.Book> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyTaskRecyclerViewAdapter(List<TaskListContent.Book> ITEMS_FROM_TaskListContent, OnListFragmentInteractionListener listener) {
        mValues = ITEMS_FROM_TaskListContent;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_task, parent, false);
        //configureImageButton();
        return new ViewHolder(view);
    }

    //metoda odpowiada za umieszczenie odpowiednich danych w danym, jednym tasku
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //Bind the ViewHolder to the element at position
        TaskListContent.Book book = mValues.get(position);
        holder.mItem = book;
        //Set the book title
        holder.mItemBookTitle.setText(book.bookTitle);
        //Set the book author
        holder.mItemAuthorName.setText(book.authorName);

        //metoda uruchamiająca metodę w MainActivity(za pośrednictwem mListener <- bo mListener w tej klasie "symbolizuje" poniekąd MainActivity)
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //<-- 'holder' - jest obiektem klasy ViewHolder która "trzyma" w sobie wszystkie wymagane dane o określonym Tasku (w tym cały widok - 'mView')
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentClickInteraction(holder.mItem, position); //<-- jako, że mListener w tej klasie "symbolizuje" poniekąd MainActivity to do metody w tej klasie mozemy odkołac się tak jak widac w tej linii
                }
            }
        });

        holder.mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentButtonClickInteraction(position);//<-- jako, że mListener w tej klasie "symbolizuje" poniekąd MainActivity to do metody w tej klasie mozemy odkołac się tak jak widac w tej linii
                }
            }
        });

        //Set an onLongClickListener for the list element
        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v){
                // Notify the active callbacks interface (the activity, if the fragment is attached to one) that an item has been selected.
                mListener.onListFragmentLongClickInteraction(holder.mItem, position);//<-- jako, że mListener w tej klasie "symbolizuje" poniekąd MainActivity to do metody w tej klasie mozemy odkołac się tak jak widac w tej linii
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    //ViewHolder zaawiera wszystkie elementy wyświetlane w każdym z TaskFragmentów
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mItemBookTitle;
        public final TextView mItemAuthorName;
        public TaskListContent.Book mItem;
        public final ImageButton mImageButton;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mItemBookTitle = (TextView) view.findViewById(R.id.book_title_fragment_task);
            mItemAuthorName = (TextView) view.findViewById(R.id.author_name_fragment_task);
            mImageButton = (ImageButton) view.findViewById(R.id.bin_image_button);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mItemAuthorName.getText() + "'";
        }


    }
}
