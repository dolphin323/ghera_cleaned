package edu.ksu.cs.benign;

import android.provider.BaseColumns;

public final class MyDatabase {

    private MyDatabase() {
    }

    public static class Table1 implements BaseColumns {
        public static String TABLE_NAME = "table1";
        public static String COLUMN_NAME_KEY = "key";
        public static String COLUMN_NAME_VALUE = "value";
    }
}
