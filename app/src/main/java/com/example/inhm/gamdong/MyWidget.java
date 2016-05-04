package com.example.inhm.gamdong;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;

/**
 * Created by inhm on 2016-05-02.
 */
public class MyWidget extends AppWidgetProvider {


    public static ComponentName mService = null;
    public static boolean endServiceFlag = false;
    public static int curCount = 0;
    MainActivity mainActivity = new MainActivity();


    public void onUpdate(Context context, AppWidgetManager appWidgetManager,int[] appWidgetIds) {

        String name1 = "인형민";
        String name2 = "최예희";

        boolean check = false;

        super.onUpdate(context, appWidgetManager, appWidgetIds);
        int appId;
        for(int i=0;i<appWidgetIds.length;i++){
            appId=appWidgetIds[i];

            //인텐트와 액티비티를 연결한다.
//            Intent intent=new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://m.daum.net"));

            Intent intent = new Intent(context,MainActivity.class);
            PendingIntent pe= PendingIntent.getActivity(context, 0, intent, 0);

            //버튼이 눌린후 실행할 액티비티를 인텐트에 결합한다.
            RemoteViews views =  new RemoteViews(context.getPackageName(),R.layout.activity_main);

            views.setOnClickPendingIntent(R.id.eventBtn, pe);

//            appWidgetManager.updateAppWidget(appId, views);
        }
    }


    static void updateWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        endServiceFlag = false;
        mService = context.startService(new Intent(context, UpdateService.class));

    }

    /*
	 * 위젯의 remoteView 갱신 시간은 1.6이상부터 기본 30분으로 지정되어있음.
	 * 따라서 30분 이하의 갱신을 원할 경우에는 Service를 상속받은 내부 클래스를 선언하여 시간 조절.
	 */
    public static class UpdateService extends Service implements Runnable {


        private Handler mHandler;
        private static final int TIMER_PERIOD = 1 * 1000;

        private long preTime;
        private long curTime;


        @Override
        public void onCreate(){
            mHandler = new Handler();
        }

        @Override
        public void onStart(Intent intent, int startId){
            preTime = System.currentTimeMillis();// - DAY_TIME;

            mHandler.postDelayed(this, 1000);
        }

        @Override
        public IBinder onBind(Intent intent) {
            // TODO Auto-generated method stub
            return null;
        }

        /*
         * 위젯 화면 업데이트를 주기적으로 하기위해 run 함수 호출.
         */
        @Override
        public void run() {

			/*
			 * 글 설정 부분. (위젯에 포함될 데이터 갱신 부분)
			 * 현재시간을 측정 후, 이전시간과 하루 이상 차이나면 갱신됨.
			 */
            curTime = System.currentTimeMillis();
            Log.d("***********", "*******************************************");
            Log.d("curTime]",""+curTime);
            Log.d("PreTime]",""+preTime);

            //여기서는 버튼의 글자를 갱신한다.
            RemoteViews views = new RemoteViews(this.getPackageName(), R.layout.activity_main);


			/*
			 * 지난 업데이트로 부터 시간이 약 1초가  지나면 버튼의 숫자를 늘린다.
			 */
            long CUR_PERIOD = curTime - preTime;

            if( CUR_PERIOD > 1000 ){
                views.setTextViewText(R.id.text, ""+Integer.toString(curCount));

                //Animation ani = AnimationUtils.loadAnimation(this,R.anim.translate);
                //mainActivity.eventBtn.startAnimation(ani);

                views.setTextViewText(R.id.eventBtn, ""+Integer.toString(curCount));
                preTime = curTime;
                curCount++;

            }

            //위젯이 업데이트 되었음을 알림.
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            ComponentName testWidge = new ComponentName(this, MyWidget.class);
            appWidgetManager.updateAppWidget(testWidge, views);

            if(endServiceFlag){
                return;
            }else{
                mHandler.postDelayed(this, TIMER_PERIOD);
            }
        } //run end!!!

    }// class end

}
