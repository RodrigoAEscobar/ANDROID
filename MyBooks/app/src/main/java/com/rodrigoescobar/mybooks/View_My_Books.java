package com.rodrigoescobar.mybooks;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;

/**
 * Program Name : My Books
 * Created by Rodrigo Escobar on 3/6/2016 @ 07:31 pm EST.
 * Assignment # : MidTerm
 *
 * Updated on 03/28/2016 @ 09:45 pm EST.
 *
 *
 * This class will display all the books in the database using a listView.
 * Used also to display all the books if search button executed with empty fields.
 * If user select a book by long pressing, the context menu will display 3 options as:
 * - See Book Details: Open View_A_Book class displaying the selected book information.
 * - Edit a Book: Open Edit_A_Book class to edit selected book.
 * - Delete This Book: Open the Delete_A_Book class to prompt user to delete selected book.
 */
public class View_My_Books extends ListActivity {

    // Class variables
    private static final int ABOUT = Menu.FIRST + 1;
    private static final int VIEW_BOOK_INFO = Menu.FIRST + 0;
    private static final int EDIT_BOOK_INFO = Menu.FIRST + 1;
    private static final int DELETE_BOOK_INFO = Menu.FIRST + 2;
    private DatabaseHelper db = null;
    private Cursor booksCursor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Database instance
        db = new DatabaseHelper(this);
        // Assign _ID and TITLE fields data from Database table to the cursor
        booksCursor = db.getReadableDatabase()
                .rawQuery("SELECT _ID AS _id, TITLE AS title FROM book_info_table ORDER BY _ID", null);

        @SuppressWarnings("deprecation")
        ListAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.row_layout_view_my_books, booksCursor,
                new String[]{DatabaseHelper.TITLE},
                new int[]{R.id.title_row_textView});
        setListAdapter(adapter);
        registerForContextMenu(getListView());

    } // END of onCreate

    @Override
    public void onDestroy() {
        super.onDestroy();
        booksCursor.close();
        db.close();
    }

    // Context Menu definition
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(Menu.NONE, VIEW_BOOK_INFO, Menu.NONE, R.string.book_details)
                .setAlphabeticShortcut('v');
        menu.add(Menu.NONE, EDIT_BOOK_INFO, Menu.NONE, R.string.edit_a_book)
                .setAlphabeticShortcut('e');
        menu.add(Menu.NONE, DELETE_BOOK_INFO, Menu.NONE, R.string.delete_a_Book)
                .setAlphabeticShortcut('d');
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case VIEW_BOOK_INFO:
                AdapterView.AdapterContextMenuInfo info1 =
                        (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

                review(info1.id);
                return (true);
            case EDIT_BOOK_INFO:
                AdapterView.AdapterContextMenuInfo info2 =
                        (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

                edit(info2.id);
                return (true);
            case DELETE_BOOK_INFO:
                AdapterView.AdapterContextMenuInfo info3 =
                        (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

                delete(info3.id);
                return (true);
        }

        return (super.onOptionsItemSelected(item));
    }

    private void review(final long rowId) {
        if (rowId > 0) {

            String[] args = {String.valueOf(rowId)};
            int rowNumber = (((int) rowId));
            Intent intentBundle = new Intent(View_My_Books.this, View_A_Book.class);
            Bundle bundle = new Bundle();
            bundle.putInt("rowId", rowNumber - 1);
            intentBundle.putExtras(bundle);
            startActivity(intentBundle);
        }
    }


    private void edit(final long rowId) {
        if (rowId > 0) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.edit_a_Book)
                    .setPositiveButton(R.string.ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int whichButton) {

                                    String[] args = {String.valueOf(rowId)};
                                    int rowNumber = (((int) rowId));
                                    Intent intentBundle = new Intent(View_My_Books.this, Edit_A_Book.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("rowId", rowNumber - 1);
                                    intentBundle.putExtras(bundle);
                                    startActivity(intentBundle);
                                }
                            })
                    .setNegativeButton(R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int whichButton) {
                                    // ignore, just dismiss
                                }
                            })
                    .show();
        }
    }

    private void delete(final long rowId) {
        if (rowId > 0) {

            String[] args = {String.valueOf(rowId)};
            int rowNumber = (((int) rowId));
            Intent intentBundle = new Intent(View_My_Books.this, Delete_A_Book.class);
            Bundle bundle = new Bundle();
            bundle.putInt("rowId", rowNumber - 1);
            intentBundle.putExtras(bundle);
            startActivity(intentBundle);
        }
    }

    // Options menu to shout about info
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, ABOUT, Menu.NONE, "About")
                .setIcon(R.drawable.add)
                .setAlphabeticShortcut('a');
        return (super.onCreateOptionsMenu(menu));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case ABOUT:
                add();
                return (true);
        }

        return (super.onOptionsItemSelected(item));
    }

    private void add() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View addView = inflater.inflate(R.layout.about, null);
        new AlertDialog.Builder(this)
                .setTitle(R.string.about)
                .setView(addView)
                .setNegativeButton(R.string.close,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                // ignore, just dismiss
                            }
                        })
                .show();
    }
} // END of View_My_Books class.

