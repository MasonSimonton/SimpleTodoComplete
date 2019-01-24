package com.example.simpletodo;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.*;
import android.widget.*;
import android.view.*;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readItems();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        itemList = (ListView) findViewById(R.id.itemList);
        itemList.setAdapter(itemsAdapter);

        //items.add("first item");
        //items.add("Second item");

    }
    public void onAddBtn(View v) {
        EditText itemName = (EditText) findViewById(R.id.itemName);
        String text = itemName.getText().toString();
        itemsAdapter.add(text);
        itemName.setText("");
        writeItems();
        Toast.makeText(getApplicationContext(), "item added to list", Toast.LENGTH_SHORT).show();

    }

    private void setupListViewListener(){
        Log.i("MainActivity", "Setting up listener on list view");
        itemList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id){
                Log.i("MainActivity", "item will be removed from list: " + position);
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
    }

    private File getDataFile(){
        return new File(getFilesDir(), "todo.txt");
    }
    private void readItems() {
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("mainActivity", "Error reading file", e);
            items = new ArrayList<>();
        }
    }
        private void writeItems(){
            try {
                FileUtils.writeLines(getDataFile(), items);
            }
            catch(IOException e){
                Log.e("MainActivity", "Error writing file", e);
            }
        }
    }
}
