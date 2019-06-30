package com.example.hawk.mydb;

import android.provider.BaseColumns;

public class DatabaseContract {

    static String TABLE_CATATAN_KULIAH = "catatan";
    static final class CatatanKuliahColumns implements BaseColumns {
        //Note title
        static String MATKUL = "matkul";
        //Note description
        static String DESCRIPTION = "description";
        //Note date
        static String DATE = "date";
    }
}
