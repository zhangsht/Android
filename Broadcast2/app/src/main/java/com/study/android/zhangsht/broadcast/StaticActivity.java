package com.study.android.zhangsht.broadcast;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhangsht on 2016/10/21.
 */

public class StaticActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.static_layout);

        final List<Fruit> listItems = new LinkedList<Fruit>();
        listItems.add(new Fruit(R.mipmap.apple, "Apple"));
        listItems.add(new Fruit(R.mipmap.banana, "Banana"));
        listItems.add(new Fruit(R.mipmap.cherry, "Cherry"));
        listItems.add(new Fruit(R.mipmap.coco, "Coco"));
        listItems.add(new Fruit(R.mipmap.kiwi, "Kiwi"));
        listItems.add(new Fruit(R.mipmap.orange, "Orange"));
        listItems.add(new Fruit(R.mipmap.pear, "Pear"));
        listItems.add(new Fruit(R.mipmap.strawberry, "Strawberry"));
        listItems.add(new Fruit(R.mipmap.watermelon, "Watermelon"));

        final FruitAdapter fruitAdapter = new FruitAdapter(this, listItems);
        final ListView listView = (ListView) findViewById(R.id.fruit_list);
        listView.setAdapter(fruitAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fruit fruit = (Fruit) listView.getItemAtPosition(position);
                Intent intent = new Intent("staticReceiver");
                intent.setClass(StaticActivity.this, StaticReceiver.class);
                intent.putExtra("logo", fruit.getLogo());
                intent.putExtra("kind", fruit.getKind());
                sendBroadcast(intent);
            }

        });
    }
}
