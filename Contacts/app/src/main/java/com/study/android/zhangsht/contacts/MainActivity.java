package com.study.android.zhangsht.contacts;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        final List<Contactor> listItems = new LinkedList<Contactor>();
        try {
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(getResources().getAssets().open("data.txt")));
            String line;
            String[] arrs;
            bufferedReader.readLine();
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                arrs = line.split("\\s+");
                listItems.add(new Contactor(arrs[0], arrs[1], arrs[2], arrs[3], arrs[4]));
                bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        listItems.add(new Contactor("Aaron", "17715523654", "手机", "江苏苏州电信", "#BB4C3B"));
//        listItems.add(new Contactor("Elvis", "18825653224", "手机", "广东揭阳移动", "#c48d30"));
//        listItems.add(new Contactor("David", "15052116654", "手机", "江苏无锡移动", "#4469b0"));
//        listItems.add(new Contactor("Edwin", "18854211875", "手机", "山东青岛移动", "#20A17B"));
//        listItems.add(new Contactor("Frank", "13955188541", "手机", "安徽合肥移动", "#BB4C3B"));
//        listItems.add(new Contactor("Joshua", "13621574410", "手机", "江苏苏州移动", "#c48d30"));
//        listItems.add(new Contactor("Ivan", "15684122771", "手机", "山东烟台联通", "#4469b0"));
//        listItems.add(new Contactor("Mark", "17765213579", "手机", "广东珠海电信", "#20A17B"));
//        listItems.add(new Contactor("Joseph", "13315466578", "手机", "河北石家庄电信", "#BB4C3B"));
//        listItems.add(new Contactor("Phoebe", "17895466428", "手机", "山东东营移动", "#c48d30"));

        final MyAdapter myAdapter = new MyAdapter(this, listItems);
        final ListView listView = (ListView) findViewById(R.id.contacts_list);
        listView.setAdapter(myAdapter);

        final LinearLayout main_layout = (LinearLayout) findViewById(R.id.main_layout);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contactor contactor = (Contactor) listView.getItemAtPosition(position);
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, DetailActivity.class);
                intent.putExtra("name", contactor.getName());
                intent.putExtra("phoneNumber", contactor.getPhoneNumber());
                intent.putExtra("type", contactor.getType());
                intent.putExtra("homeArea", contactor.getOwnerArea());
                intent.putExtra("bgColor", contactor.getBackground());
                startActivityForResult(intent, 1);
            }

        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("删除联系人");
                Contactor delete_contactor = (Contactor) listView.getItemAtPosition(position);
                builder.setMessage("确定删除联系人" + delete_contactor.getName() + "?");

                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listItems.remove((Contactor) listView.getItemAtPosition(position));
                        myAdapter.notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();
                return true;
            }
        });
    }
}
