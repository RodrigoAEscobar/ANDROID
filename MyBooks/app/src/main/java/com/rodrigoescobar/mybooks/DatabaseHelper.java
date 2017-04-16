package com.rodrigoescobar.mybooks;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Program Name : My Books
 * Created by Rodrigo Escobar on 3/6/2016 @ 07:31 pm EST.
 * Assignment # : MidTerm
 *
 * Updated on 03/28/2016 @ 09:45 pm EST.
 *
 *
 * Extends SQLiteOpenHelper
 *
 * This class will create the database with one table and handle all the database interactions
 * requested from all other clases.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Variables Declaration
    static final String _ID = "_ID"; //PRIMARY KEY AUTOINCREMENT
    static final String TITLE = "title";
    static final String AUTHOR = "author";
    static final String DESCRIPTION = "description";
    static final String ISBN = "isbn";
    static final String PRICE = "price";
    static final String YEAR = "year";
    // Database name and table
    private static final String DATABASE_NAME = "my_books_db";
    private static final String TABLE_NAME = "book_info_table";

    // DB Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    // On Create Method
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creates the book_info_table
        db.execSQL("CREATE TABLE " +
                TABLE_NAME +
                "(_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "TITLE TEXT, " +
                "AUTHOR TEXT, " +
                "DESCRIPTION TEXT, " +
                "ISBN TEXT(10), " +
                "PRICE TEXT, " +
                "YEAR TEXT(4))");

        // Add 3 book info to the Database when created
        ContentValues bookValues = new ContentValues();

        bookValues.put(TITLE, "The Linux Programming Interface");
        bookValues.put(AUTHOR, "Michael Kerrisk");
        bookValues.put(DESCRIPTION, "The Linux Programming Interface is the definitive guide to " +
                "the Linux and UNIX programming interface");
        bookValues.put(ISBN, "1593272200");
        bookValues.put(PRICE, "62.89");
        bookValues.put(YEAR, "2010");
        db.insert("book_info_table", null, bookValues);

        bookValues.put(TITLE, "Android Boot Camp for Developers Using Java");
        bookValues.put(AUTHOR, "Corinne Hoisington");
        bookValues.put(DESCRIPTION, "A GUIDE TO CREATING YOUR FIRST ANDROID APPS, 3E.");
        bookValues.put(ISBN, "1305857992");
        bookValues.put(PRICE, "107.43");
        bookValues.put(YEAR, "2015");
        db.insert("book_info_table", null, bookValues);

        bookValues.put(TITLE, "Big Java");
        bookValues.put(AUTHOR, "Cay S. Horstmann");
        bookValues.put(DESCRIPTION, "This book introduces programmers to objects at a gradual pace.");
        bookValues.put(ISBN, "0470509481");
        bookValues.put(PRICE, "24.99");
        bookValues.put(YEAR, "2009");
        db.insert("book_info_table", null, bookValues);
    }

    //On Upgrade Method to drop the table if exist
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + TABLE_NAME);
        onCreate(db);
    }

    /*
     * Add data to the DataBase
     */
    public boolean insertData(String title, String author, String description, String isbn, String price, String year) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE, title);
        contentValues.put(AUTHOR, author);
        contentValues.put(DESCRIPTION, description);
        contentValues.put(ISBN, isbn);
        contentValues.put(PRICE, price);
        contentValues.put(YEAR, year);
        // to check if data was inserted
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    } // end of insertData

    /*
     * Pulls all data from the DataBase table. and assign it to the variable result
     */
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + TABLE_NAME, null);
        return result;
    } // End of getAllData

    /*
     * Get all data for a specific _ID and return it into a cursor
     */
    public Cursor getBookInfo(int rowId, SQLiteDatabase db) {
        db = this.getWritableDatabase();
        String[] bookInfo = {DatabaseHelper._ID,
                DatabaseHelper.TITLE,
                DatabaseHelper.AUTHOR,
                DatabaseHelper.DESCRIPTION,
                DatabaseHelper.ISBN,
                DatabaseHelper.PRICE,
                DatabaseHelper.YEAR};
        Cursor bookCursor = db.query(DatabaseHelper.TABLE_NAME, bookInfo, null, null, null, null, null);
        return bookCursor;
    } // END of getBookInfo

    /*
     * Update Database
     */
    public boolean updateData(Long _id, String title, String author, String description,
                              String isbn, String price, String year) {
        String[] args = {String.valueOf(_id)};
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(_ID, _id);
        contentValues.put(TITLE, title);
        contentValues.put(AUTHOR, author);
        contentValues.put(DESCRIPTION, description);
        contentValues.put(ISBN, isbn);
        contentValues.put(PRICE, price);
        contentValues.put(YEAR, year);
        db.update(TABLE_NAME, contentValues, "_ID=?", args);
        int id = _id.intValue();
        Cursor bookCursor = getBookInfo(id, db);
        bookCursor.requery();
        return true;

    }

    /*
     * Delete all the data from a row using the indicated _ID
     */
    public boolean deleteData(long _id) {
        String[] args = {String.valueOf(_id)};
        SQLiteDatabase db = this.getWritableDatabase();

        if (_id > 0) {
            db.delete("book_info_table", "_ID=?", args);
            db.execSQL("REINDEX book_info_table");
            int id = ((int) _id);
            Cursor bookCursor = getBookInfo(id, db);
            bookCursor.requery();

            return true;
        } else {
            return false;
        }
    }

    /*
     * Search methods used by specific reference
     */
    public long searchByTitle(String searchValue) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT _ID AS _id  FROM book_info_table WHERE title LIKE '" + searchValue + "%'", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            Long rowId = cursor.getLong(0);
            return rowId;
        } else {
            return 0;
        }
    }

    public long searchByAuthor(String searchValue) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT _ID AS _id  FROM book_info_table WHERE author LIKE '" + searchValue + "%'", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            Long rowId = cursor.getLong(0);
            return rowId;
        } else {
            return 0;
        }
    }


    public long searchByIsbn(String searchValue) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT _ID AS _id  FROM book_info_table WHERE isbn LIKE '" + searchValue + "%'", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
        Long rowId = cursor.getLong(0);
        return rowId;
        } else {
            return 0;
        }
    }

    public long searchByPrice(String searchValue) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT _ID AS _id  FROM book_info_table WHERE price LIKE '" + searchValue + "%'", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
        Long rowId = cursor.getLong(0);
        return rowId;
        } else {
            return 0;
        }
    }

    public long searchByYear(String searchValue) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT _ID AS _id  FROM book_info_table WHERE year LIKE '" + searchValue + "%'", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
        Long rowId = cursor.getLong(0);
        return rowId;
        } else {
            return 0;
        }
    }
}
