package com.rodrigoescobar.www.petrec;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Program Name : Petrec
 * Created by Rodrigo Escobar on 4/24/2016 @ 07:31 pm EST.
 * Assignment # : Final
 *
 * Updated on 05/1/2016 @ 10:45 pm EST.
 *
 * This class will display the information of the selected record, allow the user to edit any field
 * and update the information in the database.
 */
public class Edit_A_Record extends AppCompatActivity implements View.OnClickListener {

    // Variables
    private static final int ABOUT = Menu.FIRST + 1;
    private static ImageButton button_sbm;
    SQLiteDatabase sqLiteDatabase;
    DB_Helper db = new DB_Helper(this);
    DB_Helper myDb = new DB_Helper(this);
    Cursor recordsCursor;
    String Pet_Name;
    int recordID;
    Long id;
    EditText edit_Reason_EditText, edit_Date_EditText, edit_Diagnostic_EditText,
            edit_Cost_EditText, edit_Vet_EditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();
        if (extrasBundle != null) {
            recordID = extrasBundle.getInt("rowId", Edit_A_Record.DEFAULT_KEYS_SHORTCUT);
        }

        setContentView(R.layout.activity_edit_a_record);

        // On Click Listener for buttons interaction
        OnClickButtonListenerSave();
        OnClickButtonListenerCancel();

        //Initialize EditText Variables
        edit_Reason_EditText = (EditText) findViewById(R.id.edit_record_reason_editText);
        edit_Date_EditText = (EditText) findViewById(R.id.edit_record_date_editText);
        edit_Diagnostic_EditText = (EditText) findViewById(R.id.edit_record_diagnostic_editText);
        edit_Cost_EditText = (EditText) findViewById(R.id.edit_record_cost_editText);
        edit_Vet_EditText = (EditText) findViewById(R.id.edit_record_vet_name_editText);


        // Method call for viewing from main menu
        recordEditField(recordID, recordsCursor);
    }

    @Override
    public void onClick(View v) {
    }

    public void recordEditField(int rowId, Cursor cursor) {
        /*
         * Check if method was called without passing any parameter value.
         * If the edit a record button is pressed from main menu,
         * it will show the information of the database first record.
         */
        if (rowId == 0 && cursor == null) {
            cursor = recordsCursor = myDb.getRecordInfo(rowId, sqLiteDatabase);
            recordsCursor.moveToFirst();
            id = cursor.getLong(0);
            Pet_Name = cursor.getString(1);
            String reason = cursor.getString(2);
            String date = cursor.getString(3);
            String diagnostic = cursor.getString(4);
            String cost = cursor.getString(5);
            String vet = cursor.getString(6);

            TextView reasonTextElement = (TextView) findViewById(R.id.edit_record_reason_editText);
            reasonTextElement.setText(reason);

            TextView dateTextElement = (TextView) findViewById(R.id.edit_record_date_editText);
            dateTextElement.setText(date);

            TextView diagnosticTextElement = (TextView) findViewById(R.id.edit_record_diagnostic_editText);
            diagnosticTextElement.setText(diagnostic);

            TextView costTextElement = (TextView) findViewById(R.id.edit_record_cost_editText);
            costTextElement.setText(cost);

            TextView vetTextElement = (TextView) findViewById(R.id.edit_record_vet_name_editText);
            vetTextElement.setText(vet);
        } else {
            cursor = recordsCursor = myDb.getRecordInfo(rowId, sqLiteDatabase);
            recordsCursor.moveToPosition(rowId);
            id = cursor.getLong(0);
            Pet_Name = cursor.getString(1);
            String reason = cursor.getString(2);
            String date = cursor.getString(3);
            String diagnostic = cursor.getString(4);
            String cost = cursor.getString(5);
            String vet = cursor.getString(6);

            TextView reasonTextElement = (TextView) findViewById(R.id.edit_record_reason_editText);
            reasonTextElement.setText(reason);

            TextView dateTextElement = (TextView) findViewById(R.id.edit_record_date_editText);
            dateTextElement.setText(date);

            TextView diagnosticTextElement = (TextView) findViewById(R.id.edit_record_diagnostic_editText);
            diagnosticTextElement.setText(diagnostic);

            TextView costTextElement = (TextView) findViewById(R.id.edit_record_cost_editText);
            costTextElement.setText(cost);

            TextView vetTextElement = (TextView) findViewById(R.id.edit_record_vet_name_editText);
            vetTextElement.setText(vet);
        }
    } // END of recordInfoField

    // Save a Record Listener
    public void OnClickButtonListenerSave() {
        button_sbm = (ImageButton) findViewById(R.id.edit_record_save_button);
        button_sbm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // Call of the method Validate to check if EditText fields are empty
                        boolean fieldsOK = validate(new EditText[]{
                                edit_Reason_EditText,
                                edit_Date_EditText,
                                edit_Diagnostic_EditText,
                                edit_Cost_EditText,
                                edit_Vet_EditText,});
                        if (fieldsOK == true) {
                            boolean isUpdated = db.updateRecord(
                                    id,
                                    Pet_Name,
                                    edit_Reason_EditText.getText().toString(),
                                    edit_Date_EditText.getText().toString(),
                                    edit_Diagnostic_EditText.getText().toString(),
                                    edit_Cost_EditText.getText().toString(),
                                    edit_Vet_EditText.getText().toString());
                            if (isUpdated == true) {
                                Toast.makeText(Edit_A_Record.this, "Record Updated!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Edit_A_Record.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            } else {
                                Toast.makeText(Edit_A_Record.this, "Record not Updated!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Edit_A_Record.this, "Empty fields not allowed!", Toast.LENGTH_LONG).show();
                        }
                    }

                }
        );
    }

    // Date Picker
    public void onStart() {
        super.onStart();
        EditText txtDate = (EditText) findViewById(R.id.edit_record_date_editText);
        txtDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    DateDialog dialog = new DateDialog(v);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    dialog.show(ft, "DatePicker");
                }
            }
        });
    }

    // For loop to validate EditText Fields are not empty
    private boolean validate(EditText[] fields) {
        for (int i = 0; i < fields.length; i++) {
            EditText currentField = fields[i];
            if (currentField.getText().toString().length() <= 0) {
                return false;
            }
        }
        return true;
    }

    // Cancel a Record Listener
    public void OnClickButtonListenerCancel() {
        button_sbm = (ImageButton) findViewById(R.id.edit_record_cancel_button);
        button_sbm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // Return to Records Class
                        Intent intent = new Intent(Edit_A_Record.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    }
                }
        );
    }

    public void setId(Long id) {
        this.id = id;
    }

}
