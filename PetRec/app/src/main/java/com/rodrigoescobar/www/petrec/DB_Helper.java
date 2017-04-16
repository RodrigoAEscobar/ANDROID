package com.rodrigoescobar.www.petrec;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Rodrigo on 4/19/2016.
 */
public class DB_Helper extends SQLiteOpenHelper {

    // Variables Declaration
    static final String _ID = "_ID"; //PRIMARY KEY AUTOINCREMENT
    static final String Pet_Name = "Pet_Name";
    static final String Pet_Type = "Pet_Type";
    static final String Pet_Dob = "Pet_Dob";
    static final String Pet_Sex = "Pet_Sex";
    static final String Owner = "Owner";
    static final String Address = "Address";
    static final String Phone = "Phone";
    static final String Visit_Reason = "Visit_Reason";
    static final String Visit_Date = "Visit_Date";
    static final String Diagnostic = "Diagnostic";
    static final String Cost = "Cost";
    static final String Vet_Name = "Vet_Name";



    // Database name and table
    private static final String DATABASE_NAME = "petrec_db";
    private static final String TABLE_NAME = "pet_info_table";
    private static final String RECORDS_TABLE = "records_table";


    // DB Constructor
    public DB_Helper(Context context) {
        super(context, DATABASE_NAME, null, 1);
//        SQLiteDatabase db = this.getReadableDatabase();
    }

    // On Create Method
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creates the pet_info_table
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Pet_Name TEXT, " +
                "Pet_Type TEXT, " +
                "Pet_Dob TEXT, " +
                "Pet_Sex TEXT(6), " +
                "Owner TEXT, " +
                "Address TEXT, " +
                "Phone TEXT(15))");
        // Creates the records_table
        db.execSQL("CREATE TABLE IF NOT EXISTS " + RECORDS_TABLE +
                "(_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Pet_Name TEXT , " +
                "Visit_Reason TEXT(180), " +
                "Visit_Date TEXT, " +
                "Diagnostic TEXT, " +
                "Cost TEXT(6), " +
                "Vet_Name TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXIST " + RECORDS_TABLE);
        onCreate(db);
    }

    /*
     * Check if a table is empty and return a int
     */
    public boolean isEmpty() {
        SQLiteDatabase db = this.getWritableDatabase();
        String count = "SELECT count(*) FROM " + TABLE_NAME;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        return icount <= 0;
    }

    /*
     * Add data to the DataBase
     */
    public boolean insertData(String pet_Name,
                              String pet_Type,
                              String pet_Dob,
                              String pet_Sex,
                              String owner_Name,
                              String owner_Address,
                              String owner_Phone
    ) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(Pet_Name, pet_Name);
        values.put(Pet_Type, pet_Type);
        values.put(Pet_Dob, pet_Dob);
        values.put(Pet_Sex, pet_Sex);
        values.put(Owner, owner_Name);
        values.put(Address, owner_Address);
        values.put(Phone, owner_Phone);
        // to check if data was inserted
        long result = db.insert(TABLE_NAME, null, values);
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
    public Cursor getPetInfo(int rowId, SQLiteDatabase db) {
        db = this.getWritableDatabase();
        String[] petInfo = {DB_Helper._ID,
                DB_Helper.Pet_Name,
                DB_Helper.Pet_Type,
                DB_Helper.Pet_Dob,
                DB_Helper.Pet_Sex,
                DB_Helper.Owner,
                DB_Helper.Address,
                DB_Helper.Phone};
        Cursor petCursor = db.query(DB_Helper.TABLE_NAME, petInfo, null, null, null, null, null);
        return petCursor;
    } // END of getPetInfo

    /*
    * Update Database
    */
    public boolean updateData(Long _id, String name, String type, String birthday,
                              String gender, String owner, String address, String phone) {
        String[] args = {String.valueOf(_id)};
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(_ID, _id);
        contentValues.put(Pet_Name, name);
        contentValues.put(Pet_Type, type);
        contentValues.put(Pet_Dob, birthday);
        contentValues.put(Pet_Sex, gender);
        contentValues.put(Owner, owner);
        contentValues.put(Address, address);
        contentValues.put(Phone, phone);
        db.update(TABLE_NAME, contentValues, "_ID=?", args);
        int id = _id.intValue();
        Cursor bookCursor = getPetInfo(id, db);
        bookCursor.requery();
        return true;

    } // END UpdateData

    /*
    * Delete all the data from a row using the indicated _ID
    */
    public boolean deleteData(long _id) {
        String[] args = {String.valueOf(_id)};
        SQLiteDatabase db = this.getWritableDatabase();

        if (_id > 0) {
            db.delete("pet_info_table", "_ID=?", args);
            db.execSQL("REINDEX pet_info_table");
            int id = ((int) _id);
            Cursor bookCursor = getPetInfo(id, db);
            bookCursor.requery();

            return true;
        } else {
            return false;
        }
    } // END DeleteData

    /*
    * Add record to the DataBase
    */
    public boolean insertRecord(String Rec_Pet_ID,
                                String Rec_Visit_Reason,
                                String Rec_Date,
                                String Rec_Diagnostic,
                                String Rec_Cost,
                                String Rec_Vet_Name
    ) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(Pet_Name, Rec_Pet_ID);
        values.put(Visit_Reason, Rec_Visit_Reason);
        values.put(Visit_Date, Rec_Date);
        values.put(Diagnostic, Rec_Diagnostic);
        values.put(Cost, Rec_Cost);
        values.put(Vet_Name, Rec_Vet_Name);

        // to check if data was inserted
        long result = db.insert(RECORDS_TABLE, null, values);
        return result != -1;
    } // end of insertRecord

    /*
     * Get all data for a specific _ID and return it into a cursor
     */
    public Cursor getRecordInfo(int rowId, SQLiteDatabase db) {
        db = this.getWritableDatabase();
        String[] recordInfo = {
                DB_Helper._ID,
                DB_Helper.Pet_Name,
                DB_Helper.Visit_Reason,
                DB_Helper.Visit_Date,
                DB_Helper.Diagnostic,
                DB_Helper.Cost,
                DB_Helper.Vet_Name};
        Cursor recordsCursor = db.query(DB_Helper.RECORDS_TABLE, recordInfo, null, null, null, null, null);
        return recordsCursor;
    } // END of getPetInfo

    /*
     * Delete all the record data from a row using the indicated _ID
     */
    public boolean deleteRecord(long _id) {
        String[] args = {String.valueOf(_id)};
        SQLiteDatabase db = this.getWritableDatabase();

        if (_id > 0) {
            db.delete("records_table", "_ID=?", args);
            db.execSQL("REINDEX records_table");
            int id = ((int) _id);
            Cursor recordsCursor = getRecordInfo(id, db);
            recordsCursor.requery();

            return true;
        } else {
            return false;
        }
    } // END DeleteRecord

    /*
     * Update Database
     */
    public boolean updateRecord(Long Rec_ID,
                                String Rec_Pet_ID,
                                String Rec_Visit_Reason,
                                String Rec_Date,
                                String Rec_Diagnostic,
                                String Rec_Cost,
                                String Rec_Vet_Name) {

        String[] args = {String.valueOf(Rec_ID)};
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(_ID, Rec_ID);
        contentValues.put(Pet_Name, Rec_Pet_ID);
        contentValues.put(Visit_Reason, Rec_Visit_Reason);
        contentValues.put(Visit_Date, Rec_Date);
        contentValues.put(Diagnostic, Rec_Diagnostic);
        contentValues.put(Cost, Rec_Cost);
        contentValues.put(Vet_Name, Rec_Vet_Name);
        db.update("records_table", contentValues, "_ID=?", args);
        int id = Rec_ID.intValue();
        Cursor bookCursor = getPetInfo(id, db);
        bookCursor.requery();
        return true;

    } // END UpdateRecord

    /*
 * Search methods used by specific reference
 */
    public long searchByDate(String searchValue) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT _ID AS _id  FROM records_table WHERE Visit_Date LIKE '" + searchValue + "%'", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            Long rowId = cursor.getLong(0);
            return rowId;
        } else {
            return 0;
        }
    }

    public long searchByPet(String searchValue) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM records_table WHERE Pet_Name LIKE '" + searchValue + "%'", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            Long rowId = cursor.getLong(0);
            return rowId;
        } else {
            return 0;
        }
    }


    public long searchByReason(String searchValue) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT _ID AS _id  FROM records_table WHERE Visit_Reason LIKE '" + searchValue + "%'", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            Long rowId = cursor.getLong(0);
            return rowId;
        } else {
            return 0;
        }
    }

    public long searchByVet(String searchValue) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT _ID AS _id  FROM records_table WHERE Vet_Name LIKE '" + searchValue + "%'", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            Long rowId = cursor.getLong(0);
            return rowId;
        } else {
            return 0;
        }
    }

    public long searchByCost(String searchValue) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT _ID AS _id  FROM records_table WHERE Cost LIKE '" + searchValue + "%'", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            Long rowId = cursor.getLong(0);
            return rowId;
        } else {
            return 0;
        }
    }

}
