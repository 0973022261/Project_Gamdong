package com.example.inhm.gamdong;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {


    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    Button eventBtn;

    float value = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        eventBtn = (Button) findViewById(R.id.eventBtn);
        RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.buttonId);
        Animation ani = AnimationUtils.loadAnimation(this,R.anim.translate);
        eventBtn.startAnimation(ani);
        relativeLayout.startAnimation(ani);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null) {
            mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        eventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Context context = MainActivity.this;
                AppWidgetManager widgetManager = AppWidgetManager.getInstance(context);
                MyWidget.updateWidget(context,widgetManager,mAppWidgetId);
                Intent resultValue = new Intent();
                resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,mAppWidgetId);
                setResult(RESULT_OK, resultValue);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        value++;
        eventBtn.setX(value);
        eventBtn.setY(value);
    }

}


