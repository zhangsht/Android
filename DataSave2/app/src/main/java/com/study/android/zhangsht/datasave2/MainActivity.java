package com.study.android.zhangsht.datasave2;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private Button addItem;
    private TextView phoneNumber;
    private MyDB myDB;
    private SimpleCursorAdapter sca;

    public void updateListView() {
        Cursor cursors = myDB.getAll();
        sca.swapCursor(cursors);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:
                updateListView();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sca.swapCursor(null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new MyDB(this);

        Cursor listItems = myDB.getAll();
        sca = new SimpleCursorAdapter(getApplicationContext(), R.layout.item,
                listItems, new String[] {"name", "birth", "gift"},
                new int[]{R.id.name, R.id.birthDay, R.id.gift}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        listView = (ListView) findViewById(R.id.info_list);
        listView.setAdapter(sca);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LayoutInflater factory = LayoutInflater.from(MainActivity.this);
                View views = factory.inflate(R.layout.dialoglayout, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setView(views);

                TextView name = (TextView)views.findViewById(R.id.d_nameText);
                TextView phone = (TextView)views.findViewById(R.id.d_phoneNumber);
                final EditText birth = (EditText)views.findViewById(R.id.d_birthText);
                final EditText gift = (EditText)views.findViewById(R.id.d_giftText);

                Cursor cursor = (Cursor)sca.getItem(position);
                final String nametext = cursor.getString(cursor.getColumnIndex("name"));
                name.setText(nametext);
                final String birthtext = cursor.getString(cursor.getColumnIndex("birth"));
                birth.setText(birthtext);
                final String gifttext = cursor.getString(cursor.getColumnIndex("gift"));
                gift.setText(gifttext);

                builder.setTitle("o(*￣▽￣*)o");
                builder.setNegativeButton("放弃修改", null);
                builder.setPositiveButton("保存修改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String birthText = birth.getText().toString();
                        String giftText = gift.getText().toString();
                        myDB.updateDB(nametext, birthText, giftText);
                        updateListView();
                    }
                });

                ContentResolver cr = getContentResolver();
                Cursor cursor1 = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
                        "DISPLAY_NAME = '" + nametext + "'", null, null);

                String phoneNumber = "";
                if (cursor1.moveToFirst()) {
                    String contactId =
                            cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts._ID));

                    Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                    while (phones.moveToNext()) {
                        phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)) + " ";
                    }
                    phones.close();
                }
                cursor1.close();

                if (phoneNumber.isEmpty()) {
                    phoneNumber = "无";
                }
                phone.setText(phoneNumber);

                builder.create().show();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor)sca.getItem(position);
                final String name = cursor.getString(cursor.getColumnIndex("name"));
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("是否删除");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myDB.deleteDB(name);
                        updateListView();
                    }
                });

                builder.setNegativeButton("取消", null);
                builder.create().show();
                return true;
            }
        });

        addItem = (Button)findViewById(R.id.add_button);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
    }
}
