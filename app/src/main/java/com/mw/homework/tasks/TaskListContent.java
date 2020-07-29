package com.mw.homework.tasks;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mw.homework.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.INPUT_SERVICE;

//Task - reprezentuje pojedynczy item na liście

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class TaskListContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<Book> ITEMS = new ArrayList<Book>(); //lista<Task> ITEMS zawira wszystko co dodaliśmy do listy "rzeczy"...
                                                                // UWAGA! Powyższa metoda nie odpowiada za wyświetlanie. Metody wyświetlające znajdują się w MyTaskRecyclerViewAdapter

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, Book> ITEM_MAP = new HashMap<String, Book>();


    static {
            addItem();
    }

    public static void addItem() {

        refreshListAndUpdateFirebase();

        for (Map.Entry<String, Book> entry : ITEM_MAP.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().authorName;
            Log.d("ITEM_MAP", key +" : "+value );
        }

    }

    public static void addItem(Book bookToAdd) {

        int idInt = Integer.parseInt(bookToAdd.id) + 1;
        String idString = idInt + "";

        bookToAdd.id = idString;

        MainActivity.ref.child(idString).setValue(bookToAdd);

        refreshListAndUpdateFirebase();
    }

    public static void removeItem(int position){
        //Get the id to locate the item in the items map
        String itemId = ITEMS.get(position).id;
        //remove the item from List
        ITEMS.remove(position);
        //remove the item from map
        ITEM_MAP.remove(itemId);

        MainActivity.ref.child(itemId).removeValue();
    }


    public static void refreshListAndUpdateFirebase(){

        MainActivity.ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                ITEMS.clear();
                ITEM_MAP.clear();

                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    String id = dataSnapshot1.child("id").getValue().toString();
                    String authorName = dataSnapshot1.child("authorName").getValue().toString();
                    String bookTitle = dataSnapshot1.child("bookTitle").getValue().toString();

                    Log.d("Cos", "ID: " + id + " | authorName: " + authorName + " | bookTitle: " + bookTitle);


                    ITEMS.add(new Book(id, bookTitle, authorName));
                    ITEM_MAP.put(id, new Book(id, bookTitle, authorName));
                }

                //Log.d("Cos", "Value is: " + value);
                //Log.d( "Cos", "Children count: " + dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(INPUT_SERVICE, "Failed to read value.", error.toException());
            }
        });
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class Book implements Parcelable {
        public String id;
        public final String bookTitle;
        public final String authorName;

        public Book(String id, String bookTitle, String authorName) {
            this.id = id;
            this.bookTitle = bookTitle;
            this.authorName = authorName;
        }
        public Book() {
            this.id = ""+(ITEMS.size()+1);
            this.bookTitle = "PrzykladowyTytul";
            this.authorName = "PrzykladoweImieAutora";
        }

        protected Book(Parcel in) {
            id = in.readString();
            bookTitle = in.readString();
            authorName = in.readString();
        }

        public String getId() {
            return id;
        }

        public String getBookTitle() {
            return bookTitle;
        }

        public String getAuthorName() {
            return authorName;
        }



        public static final Creator<Book> CREATOR = new Creator<Book>() {
            @Override
            public Book createFromParcel(Parcel in) {
                return new Book(in);
            }

            @Override
            public Book[] newArray(int size) {
                return new Book[size];
            }
        };

        @Override
        public String toString() {
            return bookTitle + authorName;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(bookTitle);
            dest.writeString(authorName);
        }
    }
}
