package com.rodrigoescobar.www.petrec;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

/**
 * Program Name : Petrec
 * Created by Rodrigo Escobar on 4/24/2016 @ 07:31 pm EST.
 * Assignment # : Final
 *
 * Updated on 05/1/2016 @ 10:45 pm EST.
 *
 * This class will display the date and pet name of all the records stored in the database.
 * Records list is organized by date
 * Context menu provides 3 options:
 *          - View Record Details
 *          - Edit Record Details
 *          - Delete Record
 */
public class Records extends ListActivity {

    // Class variables
    private static final int ABOUT = Menu.FIRST + 1;
    private static final int VIEW_REC_INFO = Menu.FIRST + 0;
    private static final int EDIT_REC_INFO = Menu.FIRST + 1;
    private static final int DELETE_REC_INFO = Menu.FIRST + 2;
    private DB_Helper db = null;
    private Cursor recordsCursor = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(Records.this, "Long press on the record for more options.", Toast.LENGTH_LONG).show();

        // Database instance
        db = new DB_Helper(this);

        // Assign Visit_Date and Pet_Name fields data from Database table to the cursor
        recordsCursor = db.getReadableDatabase()
                .rawQuery("SELECT _ID as _id, Visit_Date, Pet_Name FROM records_table ORDER BY _ID", null);


        @SuppressWarnings("deprecation")
        ListAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.row_layout_view_my_records, recordsCursor,
                new String[]{DB_Helper.Visit_Date, DB_Helper.Pet_Name},
                new int[]{R.id.data_row_textView, R.id.pet_row_textView});
        setListAdapter(adapter);
        registerForContextMenu(getListView());
    } // END onCreate

    // Context Menu definition
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(Menu.NONE, VIEW_REC_INFO, Menu.NONE, "View Record Details")
                .setAlphabeticShortcut('v');
        menu.add(Menu.NONE, EDIT_REC_INFO, Menu.NONE, "Edit Record Details")
                .setAlphabeticShortcut('e');
        menu.add(Menu.NONE, DELETE_REC_INFO, Menu.NONE, "Delete Record")
                .setAlphabeticShortcut('d');
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case VIEW_REC_INFO:
                AdapterView.AdapterContextMenuInfo info1 =
                        (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

                review(info1.id);
                return (true);
            case EDIT_REC_INFO:
                AdapterView.AdapterContextMenuInfo info2 =
                        (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

                edit(info2.id);
                return (true);
            case DELETE_REC_INFO:
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
            Intent intentBundle = new Intent(Records.this, View_A_Record.class);
            Bundle bundle = new Bundle();
            bundle.putInt("rowId", rowNumber - 1);
            intentBundle.putExtras(bundle);
            startActivity(intentBundle);
        }
    }


    private void edit(final long rowId) {
        if (rowId > 0) {
            new AlertDialog.Builder(this)
                    .setTitle("Edit Record Details?")
                    .setPositiveButton(R.string.ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int whichButton) {

                                    String[] args = {String.valueOf(rowId)};
                                    int rowNumber = (((int) rowId));
                                    Intent intentBundle = new Intent(Records.this, Edit_A_Record.class);
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
            Intent intentBundle = new Intent(Records.this, Delete_A_Record.class);
            Bundle bundle = new Bundle();
            bundle.putInt("rowId", rowNumber - 1);
            intentBundle.putExtras(bundle);
            startActivity(intentBundle);
        }
    } // END Context Menu

} // END MyPets Class