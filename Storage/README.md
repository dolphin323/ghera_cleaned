1.  **ExternalStorage-DataInjection-Lean**
Apps that read files stored in External Storage are vulnerable to malicious data injection attacks.

2.  **ExternalStorage-InformationLeak-Lean**
Apps that write sensitive information to files stored in External Storage can potentially leak sensitive information.

3.  **InternalStorage-DirectoryTraversal-Lean**
Apps that write files into internal storage are vulnerable to directory traversal attack if sanitization checks for file paths are absent.

4. **InternalToExternalStorage-InformationLeak-Lean**
Apps that allow files in internal storage to be downloaded to external storage are vulnerable to information leak.

5. **SQLite-execSQL-Lean**
Apps that use *SQLiteDatabase.execSQL()* method to construct non-parameterized SQL queries are vulnerable to SQL injection attacks.

6. **SQLlite-RawQuery-SQLInjection-Lean**
Apps that use *SQLiteDatabase.rawQuery()* method to construct non-parameterized SQL queries are vulnerable to SQL injection attacks.

7. **SQLlite-SQLInjection-Lean**
Apps that do not use *selectionArgs* to construct *select, update, and delete* queries are vulnerable to SQL injection attacks.
