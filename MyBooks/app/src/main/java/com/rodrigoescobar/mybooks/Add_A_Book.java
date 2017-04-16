/*
 * My Books - A database App for Android
 * Created by: Rodrigo Escobar
 * as a MIDTERM program for: Mobile Computing - CSIS-4020 course
 * at Nova South Eastern University
 * on 13 March 2016
 * Add_A_Book.java is a class that will handle the procedure to add a book information
 * to the database. Use the validate method to verify none of the fields are empty before
 * adding the data, this class also check the data was added to the database by checking
 * the return value of the insertData method from the Database helper class.
*/

package com.rodrigoescobar.mybooks;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Program Name : My Books
 * Created by Rodrigo Escobar on 3/6/2016 @ 07:31 pm EST.
 * Assignment # : MidTerm
 *
 * Updated on 03/28/2016 @ 09:45 pm EST.
 *
 * This class will grab the values entered by the user into the fields
 * and add those values to the database
 */
public class Add_A_Book extends AppCompatActivity implements View.OnClickListener {

    // Variables
    private static final int ABOUT = Menu.FIRST + 1;
    private static Button button_sbm;
    DatabaseHelper myDb = new DatabaseHelper(this);
    EditText add_Title_EditText, add_Author_EditText, add_Description_EditText,
            add_Isbn_EditText, add_Price_EditText, add_Year_EditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_a_book);
        // On Click Listener for buttons interaction
        OnClickButtonListenerSave();
        OnClickButtonListenerCancel();

        //Initialize EditText Variables
        add_Title_EditText = (EditText) findViewById(R.id.add_Title_EditText);
        add_Author_EditText = (EditText) findViewById(R.id.add_Author_EditText);
        add_Description_EditText = (EditText) findViewById(R.id.add_Description_EditText);
        add_Isbn_EditText = (EditText) findViewById(R.id.add_ISBN_EditText);
        add_Price_EditText = (EditText) findViewById(R.id.add_Price_EditText);
        add_Year_EditText = (EditText) findViewById(R.id.add_Year_EditText);
    }


    @Override
    public void onClick(View v) {
    }

    // Save a Book Listener
    public void OnClickButtonListenerSave() {
        button_sbm = (Button) findViewById(R.id.add_Save_Button);
        button_sbm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        // Call of the method Validate to check if EditText are empty
                        boolean fieldsOK = validate(new EditText[]{add_Title_EditText,
                                add_Author_EditText,
                                add_Description_EditText,
                                add_Isbn_EditText,
                                add_Price_EditText,
                                add_Year_EditText});
                        if (fieldsOK == true) {

                            boolean isInserted = myDb.insertData(add_Title_EditText.getText().toString(),
                                    add_Author_EditText.getText().toString(),
                                    add_Description_EditText.getText().toString(),
                                    add_Isbn_EditText.getText().toString(),
                                    add_Price_EditText.getText().toString(),
                                    add_Year_EditText.getText().toString());
                            if (isInserted == true) {
                                Toast.makeText(Add_A_Book.this, "Data Inserted!", Toast.LENGTH_SHORT).show();
                                // Reset fields
                                add_Title_EditText.setText("");
                                add_Author_EditText.setText("");
                                add_Description_EditText.setText("");
                                add_Isbn_EditText.setText("");
                                add_Price_EditText.setText("");
                                add_Year_EditText.setText("");
                                // Return to Home Class
                                Intent intent = new Intent(Add_A_Book.this, Home.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            } else {
                                Toast.makeText(Add_A_Book.this, "Data not Inserted!", Toast.LENGTH_SHORT).show();
                            } // close if loop
                        } else {
                            Toast.makeText(Add_A_Book.this, "Please enter the missing information", Toast.LENGTH_LONG).show();
                        }
                    } // end onClick
                }
        );
    } // END of OnClickButtonListenerSave

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

    // Cancel a Book Listener
    public void OnClickButtonListenerCancel() {
        button_sbm = (Button) findViewById(R.id.add_Cancel_button);
        button_sbm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Return to Home Class
                        Intent intent = new Intent(Add_A_Book.this, Home.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }
        );
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
}
