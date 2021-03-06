package com.denisimusit.p0341_simplesqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String LOG_TAG = "My log";
    Button btnAdd, btnRead, btnClear;
    EditText etName, etEmail, etSurname;
    TextView textViewContentsTable;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewContentsTable = (TextView)  findViewById(R.id.textViewContentsTable);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnRead = (Button) findViewById(R.id.btnRead);
        btnRead.setOnClickListener(this);

        btnClear = (Button) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);

        etName = (EditText) findViewById(R.id.EditTextName);
        etSurname = (EditText) findViewById(R.id.EditTextSurname);
        etEmail = (EditText) findViewById(R.id.EditTextSurname);

        // создаем объект для создания и управления версиями БД
        dbHelper = new DBHelper(this);
    }

    @Override
    public void onClick(View v) {
        // получаем данные из полей ввода
        String name = etName.getText().toString();
        String surname = etSurname.getText().toString();
        String email = etEmail.getText().toString();

        // подключаемся к БД
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        // создаем объект для данных
        ContentValues contentValues = new ContentValues();


        switch (v.getId()) {

            case R.id.btnAdd:
                Log.d(LOG_TAG, "--- Insert in mytable: ---");
                // подготовим данные для вставки в виде пар: наименование столбца - значение

                contentValues.put(DBHelper.KEY_NAME, name);
                contentValues.put(DBHelper.KEY_SURNAME, surname);
                contentValues.put(DBHelper.KEY_EMAIL, email);

                // вставляем запись и получаем ее ID
                long rowID = database.insert(DBHelper.TABLE_CONTACTS, null, contentValues);
                Log.d(LOG_TAG, "row inserted, ID = " + rowID);
                break;

            case R.id.btnRead:
                Log.d(LOG_TAG, "--- Rows in mytable: ---");
                // делаем запрос всех данных из таблицы mytable, получаем Cursor
                Cursor cursor = database.query(DBHelper.TABLE_CONTACTS, null, null,
                        null, null, null, null);

                // ставим позицию курсора на первую строку выборки
                // если в выборке нет строк, вернется false
                if (cursor.moveToFirst()) {

                    // определяем номера столбцов по имени в выборке
                    int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                    int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
                    int surnameIndex = cursor.getColumnIndex(DBHelper.KEY_SURNAME);
                    int emailIndex = cursor.getColumnIndex(DBHelper.KEY_EMAIL);

                    do {

                        // получаем значения по номерам столбцов
                        String msg = "ID = " + cursor.getInt(idIndex) +
                                ", name = " + cursor.getString(nameIndex) +
                                ", surname = " + cursor.getString(surnameIndex) +
                                ", email = " + cursor.getString(emailIndex);
                        //пишем все в лог
                        Log.d(LOG_TAG, msg);

                        //выодим все на екран
                        textViewContentsTable.setText(msg + "\n");




                    } while (cursor.moveToNext());
                } else
                    Log.d("mLog", "0 rows");
                // закрываем подключение к Curcor
                cursor.close();
                break;

            case R.id.btnClear:

                // удаляем все записи
                database.delete(DBHelper.TABLE_CONTACTS, null, null);
                break;
        }
        // закрываем подключение к БД
        dbHelper.close();
    }
}

