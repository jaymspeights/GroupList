package com.jaymspeights.jay.grouplist;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity implements WebsocketHandler{

    EditText username;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        WebsocketController.getInstance().setHandler(this).connect();

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
    }

    public void login(View view) {
        WebsocketController.getInstance().login(username.getText().toString(), password.getText().toString());
    }

    public void create(View view) {
        WebsocketController.getInstance().create(username.getText().toString(), password.getText().toString());
    }

    @Override
    public void all_lists(String[] lists) {
        Intent intent = new Intent(this, Home.class);
        if (lists != null)
            intent.putExtra("lists", lists);
        startActivity(intent);
    }

    @Override
    public void error(String message) {
        final Context context = this;
        final String res = message;
        this.runOnUiThread(new Runnable () {
            @Override
            public void run() {
                Toast.makeText(context, res, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void list_update(String[] changes) {
    }
}
