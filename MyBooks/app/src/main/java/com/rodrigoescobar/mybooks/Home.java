/*
 * My Books - A database App for Android
  * Created by: Rodrigo Escobar
  * as a MIDTERM program for: Mobile Computing - CSIS-4020 course
  * at Nova South Eastern University
  * on 13 March 2016
  * This is the main menu class, it will handle the action of the 6
  * buttons to provide the user options for interaction.
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

/**
 * Program Name : My Books
 * Created by Rodrigo Escobar on 3/6/2016 @ 07:31 pm EST.
 * Assignment # : MidTerm
 *
 * Updated on 03/28/2016 @ 09:45 pm EST.
 *
 *
 * Main Class, will display after the splash finish,
 * serve as a main menu with different database interaction options to the user.
 * any intent (class) could be open from here.
 */
public class Home extends AppCompatActivity {

    private static final int ABOUT = Menu.FIRST + 1;
    // Variables
    private static Button button_sbm;
    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // On Click Listener for buttons interaction
        OnClickButtonListenerAdd();
        OnClickButtonListenerList();
        OnClickButtonListenerView();
        OnClickButtonListenerEdit();
        OnClickButtonListenerDelete();
        OnClickButtonListenerSearch();

        // Database new instance to call the constructor and create the db
        myDB = new DatabaseHelper(this);
    }

    // Add a Book Listener
    public void OnClickButtonListenerAdd() {
        button_sbm = (Button) findViewById(R.id.main_Menu_Add_Button);
        button_sbm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.rodrigoescobar.mybooks.Add_A_Book");
                        startActivity(intent);
                    }
                }
        );
    }

    // List Books Listener
    public void OnClickButtonListenerList() {
        button_sbm = (Button) findViewById(R.id.main_Menu_List_Button);
        button_sbm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.rodrigoescobar.mybooks.View_My_Books");
                        startActivity(intent);
                    }
                }
        );
    }

    // View A Book Listener
    public void OnClickButtonListenerView() {
        button_sbm = (Button) findViewById(R.id.main_Menu_View_Button);
        button_sbm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.rodrigoescobar.mybooks.View_A_Book");
                        startActivity(intent);
                    }
                }
        );
    }

    // Edit A Book Listener
    public void OnClickButtonListenerEdit() {
        button_sbm = (Button) findViewById(R.id.main_Menu_Edit_Button);
        button_sbm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.rodrigoescobar.mybooks.Edit_A_Book");
                        startActivity(intent);
                    }
                }
        );
    }

    // Delete A Book Listener
    public void OnClickButtonListenerDelete() {
        button_sbm = (Button) findViewById(R.id.main_Menu_Delete_Button);
        button_sbm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.rodrigoescobar.mybooks.Delete_A_Book");
                        startActivity(intent);
                    }
                }
        );
    }

    // Delete A Book Listener
    public void OnClickButtonListenerSearch() {
        button_sbm = (Button) findViewById(R.id.main_Menu_Search_Button);
        button_sbm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.rodrigoescobar.mybooks.Search_For_A_Book");
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