package com.rodrigoescobar.www.petrec;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Program Name : Petrec
 * Created by Rodrigo Escobar on 4/24/2016 @ 07:31 pm EST.
 * Assignment # : Final
 *
 * Updated on 05/1/2016 @ 10:45 pm EST.
 *
 * This class will display the record information of the one selected from Records or
 * the search results.
 */
public class View_A_Record extends AppCompatActivity {

    // Class Variables
    private static final int ABOUT = Menu.FIRST + 1;
    private static Button button_sbm;
    Cursor recordsCursor;
    SQLiteDatabase sqLiteDatabase;
    DB_Helper db = new DB_Helper(this);
    int petID;
    Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();
        if (extrasBundle != null) {
            petID = (extrasBundle.getInt("rowId", View_A_Record.DEFAULT_KEYS_SHORTCUT));
        }
        setContentView(R.layout.activity_view_a_record);
        // Method call for viewing from main menu
        recordInfoField(petID, recordsCursor);
        OnClickButtonListenerCancel();

    } // END of onCreate method

    public void recordInfoField(int rowId, Cursor cursor) {

        /*
         * Check if method was called without passing any parameter value.
         * If the view a record button is pressed from main menu,
         * it will show the information of the first record of the database records table.
         */
        if (rowId == 0) {
            cursor = recordsCursor = db.getRecordInfo(rowId, sqLiteDatabase);
            recordsCursor.moveToFirst();
            id = cursor.getLong(0);
            String reason = cursor.getString(2);
            String date = cursor.getString(3);
            String diagnostic = cursor.getString(4);
            String cost = cursor.getString(5);
            String vet = cursor.getString(6);

            TextView reasonTextElement = (TextView) findViewById(R.id.view_record_reason_editText);
            reasonTextElement.setText(reason);

            TextView dateTextElement = (TextView) findViewById(R.id.view_record_date_editText);
            dateTextElement.setText(date);

            TextView diagnosticTextElement = (TextView) findViewById(R.id.view_record_diagnostic_editText);
            diagnosticTextElement.setText(diagnostic);

            TextView costTextElement = (TextView) findViewById(R.id.view_record_cost_editText);
            costTextElement.setText(cost);

            TextView vetTextElement = (TextView) findViewById(R.id.view_record_vet_name_editText);
            vetTextElement.setText(vet);
        } else {
            cursor = recordsCursor = db.getRecordInfo(rowId, sqLiteDatabase);
            recordsCursor.moveToPosition(rowId);
            id = cursor.getLong(0);
            String reason = cursor.getString(2);
            String date = cursor.getString(3);
            String diagnostic = cursor.getString(4);
            String cost = cursor.getString(5);
            String vet = cursor.getString(6);

            TextView reasonTextElement = (TextView) findViewById(R.id.view_record_reason_editText);
            reasonTextElement.setText(reason);

            TextView dateTextElement = (TextView) findViewById(R.id.view_record_date_editText);
            dateTextElement.setText(date);

            TextView diagnosticTextElement = (TextView) findViewById(R.id.view_record_diagnostic_editText);
            diagnosticTextElement.setText(diagnostic);

            TextView costTextElement = (TextView) findViewById(R.id.view_record_cost_editText);
            costTextElement.setText(cost);

            TextView vetTextElement = (TextView) findViewById(R.id.view_record_vet_name_editText);
            vetTextElement.setText(vet);
        }
    } // END of recordInfoField

    // Cancel a Record Listener
    public void OnClickButtonListenerCancel() {
        button_sbm = (Button) findViewById(R.id.view_record_cancel_button);
        button_sbm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // Return to Records Class
                        Intent intent = new Intent(View_A_Record.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    }
                }
        );
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        recordsCursor.close();
        db.close();
    }


} // END of View_A_Record




