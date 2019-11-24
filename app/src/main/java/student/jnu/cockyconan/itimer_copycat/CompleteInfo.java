package student.jnu.cockyconan.itimer_copycat;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import static android.icu.util.Calendar.getInstance;

@RequiresApi(api = Build.VERSION_CODES.N)
public class CompleteInfo extends AppCompatActivity {

    private Calendar completeinfo_calendar=getInstance(Locale.CHINA);

    private int switchremaintimeshow=0;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_info);

        Intent intenttmp=getIntent();
        int position=intenttmp.getIntExtra("position",0);
        String title=intenttmp.getStringExtra("title");
        String memo=intenttmp.getStringExtra("memo");
        int year=intenttmp.getIntExtra("year",0);
        int month=intenttmp.getIntExtra("month",0);
        int day=intenttmp.getIntExtra("day",0);
        int hour =intenttmp.getIntExtra("hour",0);
        int min=intenttmp.getIntExtra("min",0);
        Bitmap photo=intenttmp.getParcelableExtra("photobitmap");

        //Button backbtn=(Button)findViewById(R.id.complete_info_backtomain_button);
       // Button tothetopbtn=(Button)findViewById(R.id.complete_info_tothemaintop_button);
        //Button showbtn=(Button)findViewById(R.id.complete_info_show_button);
       // Button deletebtn=(Button)findViewById(R.id.complete_info_delete_button);
       // Button
        TextView titletxt=(TextView)findViewById(R.id.complete_info_Title);
        TextView memotxt=(TextView)findViewById(R.id.complete_info_mymemo);
        TextView endtimetxt=(TextView)findViewById(R.id.complete_info_endtime);
        final TextView remaintimetxt=(TextView)findViewById(R.id.complete_info_remain_time);
        ImageView imageViewbg=(ImageView)findViewById(R.id.complete_info_bg);

        completeinfo_calendar.set(year,month,day,hour,min);

        titletxt.setText(title);
        memotxt.setText(memo);
        endtimetxt.setText(completeinfo_calendar.get(Calendar.YEAR)+"-"+completeinfo_calendar.get(Calendar.MONTH)+1+"-"+completeinfo_calendar.get(Calendar.DAY_OF_MONTH)+" "+
                completeinfo_calendar.get(Calendar.HOUR)+":"+completeinfo_calendar.get(Calendar.MINUTE)+" "+completeinfo_calendar.get(Calendar.DAY_OF_WEEK));
        imageViewbg.setBackgroundColor(Color.rgb(165, 165, 165));

        CountDownTimer completeinfo_timer;
         completeinfo_timer=new CountDownTimer(getremaintime(),1000) {
            @Override
            public void onTick(long remaintime_millissec) {
                int days=(int)remaintime_millissec/(1000*60*60*24);
                int hours=(int)(remaintime_millissec%(1000*60*60*24))/(1000*60*60);
                int mins=(int)(remaintime_millissec%(1000*60*60))/(1000*60);
                int second=(int)(remaintime_millissec%(1000*60))/1000;
                if(switchremaintimeshow==0) {
                    remaintimetxt.setText(days+" Days "+hours+" Hours "+mins+" Mins "+second+" Seconds ");
                }
                else
                {
                    remaintimetxt.setText( "hahaha");
                }
            }


            @Override
            public void onFinish() {

            }
        };
        completeinfo_timer.start();









    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private long getremaintime()
    {
        return completeinfo_calendar.getTimeInMillis()-28800000l-Calendar.getInstance(Locale.CHINA).getTimeInMillis();
    }
    public void onClick(View which_is_clicked)
    {
        switch (which_is_clicked.getId())
        {
            case R.id.complete_info_backtomain_button:
            {
                Intent backtomain=new Intent(this,MainActivity.class);
                startActivity(backtomain);
                this.finish();
            }
            case R.id.complete_info_tothemaintop_button:
            {

            }
            case R.id.complete_info_show_button:
            {

            }
            case R.id.complete_info_delete_button:
            {

            }
            case R.id.complete_info_remain_time://模仿点击剩余时间会改变计时方式
            {
                if(switchremaintimeshow==0)
                    switchremaintimeshow=1;
                else
                {
                    switchremaintimeshow=0;
                }
            }

        }
    }
}
