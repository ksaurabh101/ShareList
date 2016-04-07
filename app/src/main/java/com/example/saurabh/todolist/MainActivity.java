package com.example.saurabh.todolist;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    EditText text;
    Button add;
    int flag=0;
    int index;
    public ArrayList<String> toDoList;
    public ArrayAdapter<String> listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        text=(EditText)findViewById(R.id.editText);
        add= (Button) findViewById(R.id.add);
        text.setVisibility(View.INVISIBLE);
        add.setVisibility(View.INVISIBLE);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text.setVisibility(View.VISIBLE);
                add.setVisibility(View.VISIBLE);
                flag=1;
            }
        });

        toDoList=new ArrayList<>();
        listAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,toDoList);
        ListView listView=(ListView)findViewById(R.id.todoListView);
        listView.setAdapter(listAdapter);

        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        ListView listView=(ListView)findViewById(R.id.todoListView);
        if(v.getId()!=R.id.todoListView){
            return;
        }
        menu.setHeaderTitle("What would you like to do");
        String[] options={"Edit","Delete","Return"};
        for(String option : options){
            menu.add(option);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info= (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        index=info.position;
        if(item.getTitle().equals("Edit")){
            text.setVisibility(View.VISIBLE);
            add.setVisibility(View.VISIBLE);
            flag=2;
            String value=toDoList.get(index);
            text.setText(value);
            add.setText("Edit");
        }
        if(item.getTitle().equals("Delete")){
            toDoList.remove(index);
            listAdapter.notifyDataSetChanged();
        }
        return  true;
    }

    @Override
    public void onBackPressed() {
        try{
            Log.i("On Back Button Pressed","Back Button Pressed");
            PrintWriter writer=new PrintWriter(openFileOutput("ToDolist.txt", Context.MODE_PRIVATE));
            for(String item: toDoList){
                writer.println(item);
            }
        }catch (Exception e){
            Toast.makeText(this,"Error : "+e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addToDo(View view) {
        if(flag==1) {
            String todo = text.getText().toString().trim();
            if (todo.isEmpty()) {
                return;
            }
            listAdapter.add(todo);
            text.setText("");
            text.setVisibility(View.INVISIBLE);
            add.setVisibility(View.INVISIBLE);
            DatabaseHelper helper=new DatabaseHelper(this);

        }
        if(flag==2){
            String todo = text.getText().toString().trim();
            if (todo.isEmpty()) {
                return;
            }
            toDoList.set(index, todo);
            listAdapter.notifyDataSetChanged();
            text.setText("");
            text.setVisibility(View.INVISIBLE);
            add.setVisibility(View.INVISIBLE);
        }
    }
}
