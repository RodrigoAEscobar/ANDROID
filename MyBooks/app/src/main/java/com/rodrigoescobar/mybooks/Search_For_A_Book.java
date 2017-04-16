package com.rodrigoescobar.mybooks;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
 *
 * Search the database with any field except description,
 * after receiving the row _ID from the query, will execute the View_A_Book class
 * to display the searched book info.
 */
public class Search_For_A_Book extends AppCompatActivity implements View.OnClickListener {

    // Variables
    private static Button button_sbm;
    SQLiteDatabase sqLiteDatabase;
    DatabaseHelper db = new DatabaseHelper(this);
    DatabaseHelper myDb = new DatabaseHelper(this);
    Cursor booksCursor, cursor;
    int bookID, title, author, isbn, price, year;
    Long id;
    EditText search_Title_EditText, search_Author_EditText, search_Isbn_EditText, search_Price_EditText, search_Year_EditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_for_a_book);
        // On Click Listener for buttons interaction
        OnClickButtonListenerSearch();

        //Initialize EditText Variables
        search_Title_EditText = (EditText) findViewById(R.id.search_Title_EditText);
        search_Author_EditText = (EditText) findViewById(R.id.search_Author_EditText);
        search_Isbn_EditText = (EditText) findViewById(R.id.search_ISBN_EditText);
        search_Price_EditText = (EditText) findViewById(R.id.search_Price_EditText);
        search_Year_EditText = (EditText) findViewById(R.id.search_Year_EditText);
    }

    // Search a Book Listener
    public void OnClickButtonListenerSearch() {
        button_sbm = (Button) findViewById(R.id.search_Search_Button);
        button_sbm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        title = search_Title_EditText.getText().toString().length();
                        author = search_Author_EditText.getText().toString().length();
                        isbn = search_Isbn_EditText.getText().toString().length();
                        price = search_Price_EditText.getText().toString().length();
                        year = search_Year_EditText.getText().toString().length();
                        String searchValue;
                        Long rowId;

                        // Call of the method Validate to check if EditText are empty
                        if (title != 0) {
                            searchValue = search_Title_EditText.getText().toString();
                            rowId = myDb.searchByTitle(searchValue);
                            int rowNumber = rowId.intValue() - 1;
                            if (rowNumber >= 0) {
                                Intent intentBundle = new Intent(Search_For_A_Book.this, View_A_Book.class);
                                Bundle bundle = new Bundle();
                                bundle.putInt("rowId", rowNumber);
                                intentBundle.putExtras(bundle);
                                startActivity(intentBundle);
                            } else {
                                Toast.makeText(Search_For_A_Book.this, "Title not found!", Toast.LENGTH_SHORT).show();
                            }


                        } else if (author != 0) {
                            searchValue = search_Author_EditText.getText().toString();
                            rowId = myDb.searchByAuthor(searchValue);
                            int rowNumber = rowId.intValue() - 1;
                            if (rowNumber >= 0) {
                            Intent intentBundle = new Intent(Search_For_A_Book.this, View_A_Book.class);
                            Bundle bundle = new Bundle();
                                bundle.putInt("rowId", rowNumber);
                            intentBundle.putExtras(bundle);
                            startActivity(intentBundle);
                            } else {
                                Toast.makeText(Search_For_A_Book.this, "Author not found!", Toast.LENGTH_SHORT).show();
                            }

                        } else if (isbn != 0) {
                            searchValue = search_Isbn_EditText.getText().toString();
                            rowId = myDb.searchByIsbn(searchValue);
                            int rowNumber = rowId.intValue() - 1;
                            if (rowNumber >= 0) {
                            Intent intentBundle = new Intent(Search_For_A_Book.this, View_A_Book.class);
                            Bundle bundle = new Bundle();
                                bundle.putInt("rowId", rowNumber);
                            intentBundle.putExtras(bundle);
                            startActivity(intentBundle);
                            } else {
                                Toast.makeText(Search_For_A_Book.this, "ISBN not found!", Toast.LENGTH_SHORT).show();
                            }

                        } else if (price != 0) {
                            searchValue = search_Price_EditText.getText().toString();
                            rowId = myDb.searchByPrice(searchValue);
                            int rowNumber = rowId.intValue() - 1;
                            if (rowNumber >= 0) {
                            Intent intentBundle = new Intent(Search_For_A_Book.this, View_A_Book.class);
                            Bundle bundle = new Bundle();
                                bundle.putInt("rowId", rowNumber);
                            intentBundle.putExtras(bundle);
                            startActivity(intentBundle);
                            } else {
                                Toast.makeText(Search_For_A_Book.this, "Price not found!", Toast.LENGTH_SHORT).show();
                            }

                        } else if (year != 0) {
                            searchValue = search_Year_EditText.getText().toString();
                            rowId = myDb.searchByYear(searchValue);
                            int rowNumber = rowId.intValue() - 1;
                            if (rowNumber >= 0) {
                            Intent intentBundle = new Intent(Search_For_A_Book.this, View_A_Book.class);
                            Bundle bundle = new Bundle();
                                bundle.putInt("rowId", rowNumber);
                            intentBundle.putExtras(bundle);
                            startActivity(intentBundle);
                            } else {
                                Toast.makeText(Search_For_A_Book.this, "Year not found!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Intent intent = new Intent("com.rodrigoescobar.mybooks.View_My_Books");
                            startActivity(intent);
                        }
                    } // end onClick
                }
        );

    } // END of OnClickButtonListenerSave

    @Override
    public void onClick(View v) {

    }
} // END of Search_for_A_Book


