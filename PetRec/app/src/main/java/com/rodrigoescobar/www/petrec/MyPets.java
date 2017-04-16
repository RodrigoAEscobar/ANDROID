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
 * This class will display the pet name(s) that are register.
 * A context menu will provide four options:
 *      - View Pet Details
 *      - Edit Pet Details
 *      - Delete Pet Info
 *      - Add a Record
 */
public class MyPets extends ListActivity {

    // Class variables
    private static final int ABOUT = Menu.FIRST + 1;
    private static final int VIEW_PET_INFO = Menu.FIRST + 0;
    private static final int EDIT_PET_INFO = Menu.FIRST + 1;
    private static final int DELETE_PET_INFO = Menu.FIRST + 2;
    private static final int ADD_A_RECORD = Menu.FIRST + 3;

    private DB_Helper db = null;
    private Cursor petsCursor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(MyPets.this, "Long press on the pet name for more options.", Toast.LENGTH_LONG).show();
        // Database instance
        db = new DB_Helper(this);
        // Assign _ID and TITLE fields data from Database table to the cursor
        petsCursor = db.getReadableDatabase()
                .rawQuery("SELECT _ID AS _id, Pet_Name FROM pet_info_table ORDER BY _ID", null);

        @SuppressWarnings("deprecation")
        ListAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.row_layout_view_my_pets, petsCursor,
                new String[]{DB_Helper.Pet_Name},
                new int[]{R.id.data_row_textView});
        setListAdapter(adapter);
        registerForContextMenu(getListView());
    } // END onCreate

    // Context Menu definition
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(Menu.NONE, VIEW_PET_INFO, Menu.NONE, R.string.pet_details)
                .setAlphabeticShortcut('v');
        menu.add(Menu.NONE, EDIT_PET_INFO, Menu.NONE, R.string.edit_a_pet)
                .setAlphabeticShortcut('e');
        menu.add(Menu.NONE, DELETE_PET_INFO, Menu.NONE, R.string.delete_a_pet)
                .setAlphabeticShortcut('d');
        menu.add(Menu.NONE, ADD_A_RECORD, Menu.NONE, R.string.add_a_record)
                .setAlphabeticShortcut('a');
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case VIEW_PET_INFO:
                AdapterView.AdapterContextMenuInfo info1 =
                        (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

                review(info1.id);
                return (true);
            case EDIT_PET_INFO:
                AdapterView.AdapterContextMenuInfo info2 =
                        (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

                edit(info2.id);
                return (true);
            case DELETE_PET_INFO:
                AdapterView.AdapterContextMenuInfo info3 =
                        (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

                delete(info3.id);
                return (true);
            case ADD_A_RECORD:
                AdapterView.AdapterContextMenuInfo info4 =
                        (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

                add(info4.id);
                return (true);
        }

        return (super.onOptionsItemSelected(item));
    }

    private void review(final long rowId) {
        if (rowId > 0) {

            String[] args = {String.valueOf(rowId)};
            int rowNumber = (((int) rowId));
            Intent intentBundle = new Intent(MyPets.this, View_A_Pet.class);
            Bundle bundle = new Bundle();
            bundle.putInt("rowId", rowNumber - 1);
            intentBundle.putExtras(bundle);
            startActivity(intentBundle);
        }
    }


    private void edit(final long rowId) {
        if (rowId > 0) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.edit_a_pet)
                    .setPositiveButton(R.string.ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int whichButton) {

                                    String[] args = {String.valueOf(rowId)};
                                    int rowNumber = (((int) rowId));
                                    Intent intentBundle = new Intent(MyPets.this, Edit_A_Pet.class);
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
            Intent intentBundle = new Intent(MyPets.this, Delete_A_Pet.class);
            Bundle bundle = new Bundle();
            bundle.putInt("rowId", rowNumber - 1);
            intentBundle.putExtras(bundle);
            startActivity(intentBundle);
        }
    }

    private void add(final long rowId) {
        if (rowId > 0) {

            String[] args = {String.valueOf(rowId)};
            int rowNumber = (((int) rowId));
            Intent intentBundle = new Intent(MyPets.this, Add_A_Record.class);
            Bundle bundle = new Bundle();
            bundle.putInt("rowId", rowNumber - 1);
            intentBundle.putExtras(bundle);
            startActivity(intentBundle);
        }

    } // END Context Menu

} // END MyPets Class