package me.lianzhao.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void navigation(View view) throws ClassNotFoundException {
        if (!(view instanceof Button)) {
            return;
        }

        Button button = (Button) view;
        String buttonText = button.getText().toString();
        String activityClassName = "me.lianzhao.myapplication." + buttonText + "Activity";
        Class<?> activityClass = Class.forName(activityClassName);
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }
}
