package com.rodrigoescobar.www.petrec;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Program Name : Petrec
 * Created by Rodrigo Escobar on 3/6/2016 @ 07:31 pm EST.
 * Assignment # : MidTerm
 * <p/>
 * Updated on 03/28/2016 @ 09:45 pm EST.
 * <p/>
 * This class will delete from the database the book selected from View_My_Books class.
 */
public class Delete_A_Record extends AppCompatActivity implements View.OnClickListener {

    // Variables
    private static Button button_sbm;
    SQLiteDatabase sqLiteDatabase;
    DB_Helper db = new DB_Helper(this);
    DB_Helper myDb = new DB_Helper(this);
    Cursor recordsCursor;
    int record_ID;
    Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();
        if (extrasBundle != null) {
            record_ID = extrasBundle.getInt("rowId", View_A_Pet.DEFAULT_KEYS_SHORTCUT);
        }
        setContentView(R.layout.activity_delete_a_record);
        // On Click Listener for buttons interaction
        OnClickButtonListenerDelete();
        OnClickButtonListenerCancel();

        // Method call for viewing from main menu
        deleteRecord(record_ID, recordsCursor);
    }

    @Override
    public void onClick(View v) {

    }

    public void deleteRecord(int rowId, Cursor cursor) {

        /*
         * Check if method was called without passing any parameter value.
         * If the delete a book button is pressed from main menu,
         * the screen will display the first book of the database
         */
        if (rowId == 0 && cursor == null) {
            cursor = recordsCursor = db.getRecordInfo(rowId, sqLiteDatabase);
            recordsCursor.moveToFirst();
            id = cursor.getLong(0);
            String reason = cursor.getString(2);
            String date = cursor.getString(3);
            String diagnostic = cursor.getString(4);
            String cost = cursor.getString(5);
            String vet = cursor.getString(6);


            TextView nameTextElement = (TextView) findViewById(R.id.delete_record_reason_editText);
            nameTextElement.setText(reason);

            TextView typeTextElement = (TextView) findViewById(R.id.delete_record_date_editText);
            typeTextElement.setText(date);

            TextView birthdayTextElement = (TextView) findViewById(R.id.delete_record_diagnostic_editText);
            birthdayTextElement.setText(diagnostic);

            TextView genderTextElement = (TextView) findViewById(R.id.delete_record_cost_editText);
            genderTextElement.setText(cost);

            TextView ownerTextElement = (TextView) findViewById(R.id.delete_record_vet_name_editText);
            ownerTextElement.setText(vet);

        } else {
            cursor = recordsCursor = db.getRecordInfo(rowId, sqLiteDatabase);
            recordsCursor.moveToPosition(rowId);
            id = cursor.getLong(0);
            String reason = cursor.getString(2);
            String date = cursor.getString(3);
            String diagnostic = cursor.getString(4);
            String cost = cursor.getString(5);
            String vet = cursor.getString(6);


            TextView nameTextElement = (TextView) findViewById(R.id.delete_record_reason_editText);
            nameTextElement.setText(reason);

            TextView typeTextElement = (TextView) findViewById(R.id.delete_record_date_editText);
            typeTextElement.setText(date);

            TextView birthdayTextElement = (TextView) findViewById(R.id.delete_record_diagnostic_editText);
            birthdayTextElement.setText(diagnostic);

            TextView genderTextElement = (TextView) findViewById(R.id.delete_record_cost_editText);
            genderTextElement.setText(cost);

            TextView ownerTextElement = (TextView) findViewById(R.id.delete_record_vet_name_editText);
            ownerTextElement.setText(vet);
        }
    } // END of recordInfoField


    public void OnClickButtonListenerDelete() {
        button_sbm = (Button) findViewById(R.id.delete_a_record_delete_button);
        button_sbm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        userConfirmation((long) record_ID);
                    }
                }
        );
    }

    // Cancel a record Listener
    public void OnClickButtonListenerCancel() {
        button_sbm = (Button) findViewById(R.id.delete_a_record_cancel_button);
        button_sbm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Return to Records Class
                        Intent intent = new Intent(Delete_A_Record.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }
        );
    }

    // Display a dialog to obtain user confirmation before deleting the book
    public void userConfirmation(final Long recordID) {
        if (recordID > 0) {
            new AlertDialog.Builder(this)
                    .setTitle("Delete Record?")
                    .setPositiveButton(R.string.ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int whichButton) {
                                    boolean isDeleted = myDb.deleteRecord(recordID + 1);
                                    if (isDeleted == true) {
                                        Toast.makeText(Delete_A_Record.this, "Record Deleted!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Delete_A_Record.this, MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(Delete_A_Record.this, "Record NOT Deleted!", Toast.LENGTH_SHORT).show();
                                    }
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
}