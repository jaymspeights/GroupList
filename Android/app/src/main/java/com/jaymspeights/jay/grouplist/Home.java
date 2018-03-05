package com.jaymspeights.jay.grouplist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    View editing = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        String[] data;
        if (getIntent().hasExtra("lists"))
             data = getIntent().getStringArrayExtra("lists");
        else
            data = new String[0];
        RecyclerView rview = findViewById(R.id.list_view);
        rview.setLayoutManager(new LinearLayoutManager(this));
        ListAdapter adapter = new ListAdapter(data);
        rview.setAdapter(adapter);
    }

    public void addItem(View view) {

    }

    public void edit(View view) {
        if (editing != null) {
            if (view != editing) {
                LinearLayout layout = (LinearLayout) editing;
                TextView tv = (TextView)layout.getChildAt(0);
                EditText et = (EditText)layout.getChildAt(1);
                tv.setText(et.getText());
                et.setVisibility(View.GONE);
                tv.setVisibility(View.VISIBLE);
                editing = null;
                return;
            } else {
                return;
            }
        }
        LinearLayout layout = (LinearLayout) view;
        TextView tv = (TextView)layout.getChildAt(0);
        EditText et = (EditText)layout.getChildAt(1);
        tv.setVisibility(View.GONE);
        et.setText(tv.getText());
        et.setVisibility(View.VISIBLE);
        editing = view;
    }
}
