package student.jnu.cockyconan.itimer_copycat;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Locale;

import static android.icu.util.Calendar.getInstance;

@RequiresApi(api = Build.VERSION_CODES.N)
public class CompleteInfo extends AppCompatActivity {

    public static final int EDIT_APPLY_CODE = 159;
    private static final int EDIT_CHANGE_CODE =197 ;
    private static Calendar completeinfo_calendar=getInstance(Locale.CHINA);

    private int switchremaintimeshow=0;

    private int position;

    private String title;
    private String memo;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int min;
    private int loop;
    private Bitmap bitmapbg;

    private TextView titletxt;
    private  TextView memotxt;
    private  TextView endtimetxt;
    private TextView remaintimetxt;
    private ImageView imageViewbg;

    private  CountDownTimer completeinfo_timer;


    private boolean isitchange=false;//判断是否用edit界面对其做出修改



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_info);
        titletxt=(TextView)findViewById(R.id.complete_info_Title);
        memotxt=(TextView)findViewById(R.id.complete_info_mymemo);
        endtimetxt=(TextView)findViewById(R.id.complete_info_endtime);
        remaintimetxt=(TextView)findViewById(R.id.complete_info_remain_time);
        imageViewbg=(ImageView)findViewById(R.id.complete_info_bg);

        Intent intenttmp=getIntent();
        position=intenttmp.getIntExtra("position",0);
        title=intenttmp.getStringExtra("title");
        memo =intenttmp.getStringExtra("memo");
        year=intenttmp.getIntExtra("year",0);
        month=intenttmp.getIntExtra("month",0);
        day=intenttmp.getIntExtra("day",0);
        hour =intenttmp.getIntExtra("hour",0);
        min=intenttmp.getIntExtra("minute",0);
        loop=intenttmp.getIntExtra("loop",0);
        byte[] bytes=intenttmp.getByteArrayExtra("photobitmap");
        bitmapbg=MainActivity.Bytes2Bitmap(bytes);

        //Button backbtn=(Button)findViewById(R.id.complete_info_backtomain_button);
       // Button tothetopbtn=(Button)findViewById(R.id.complete_info_tothemaintop_button);
        //Button showbtn=(Button)findViewById(R.id.complete_info_show_button);
       // Button deletebtn=(Button)findViewById(R.id.complete_info_delete_button);
       // Button






        completeinfo_calendar.set(year,month,day,hour,min,0);//这里的零必须设置

        titletxt.setText(title);
        memotxt.setText(memo);
        int monthtmp=completeinfo_calendar.get(Calendar.MONTH)+1;
        String dayofweek= null;
        switch (completeinfo_calendar.get(Calendar.DAY_OF_WEEK))
        {
            case 1:dayofweek="Sunday";break;
            case 2:dayofweek="Monday";break;
            case 3:dayofweek="Tuesday";break;
            case 4:dayofweek="Wednesday";break;
            case 5:dayofweek="Thursday";break;
            case 6:dayofweek="Friday";break;
            case 7:dayofweek="Saturday";break;
            default:
        }
        if(loop==0)
        endtimetxt.setText(completeinfo_calendar.get(Calendar.YEAR)+"-"+monthtmp+"-"+completeinfo_calendar.get(Calendar.DAY_OF_MONTH)+" "+
                completeinfo_calendar.get(Calendar.HOUR_OF_DAY)+":"+completeinfo_calendar.get(Calendar.MINUTE)+" "+dayofweek+"\n  "+"  ------ No Loop ------");
        else
            endtimetxt.setText(completeinfo_calendar.get(Calendar.YEAR)+"-"+monthtmp+"-"+completeinfo_calendar.get(Calendar.DAY_OF_MONTH)+" "+
                    completeinfo_calendar.get(Calendar.HOUR_OF_DAY)+":"+completeinfo_calendar.get(Calendar.MINUTE)+" "+dayofweek+"\n  "+"------ Every "+loop+" Days ------");
        imageViewbg.setImageBitmap(bitmapbg);


         completeinfo_timer=new CountDownTimer(getremaintime(),1000) {
            @Override
            public void onTick(long remaintime_millissec) {
                long days=(long)remaintime_millissec/(1000*60*60*24);
                long hours=(long)(remaintime_millissec%(1000*60*60*24))/(1000*60*60);
                long mins=(long)(remaintime_millissec%(1000*60*60))/(1000*60);
                long second=(long)(remaintime_millissec%(1000*60)/1000);
                if(switchremaintimeshow==0) {//改变剩余时间显示格式
                    remaintimetxt.setText(days+" Days "+hours+" Hours "+mins+" Mins "+second+" Seconds ");
                }
                else if(switchremaintimeshow==1)
                {
                    hours+=days*24;
                    remaintimetxt.setText(hours+" Hours "+mins+" Mins "+second+" Seconds ");
                }
                else if(switchremaintimeshow==2)
                {
                    mins=mins+days*24*60+hours*60;
                    remaintimetxt.setText(mins+" Mins "+second+" Seconds ");
                }
                else if(switchremaintimeshow==3)
                {
                    second=second+mins*60+days*24*60*60+hours*60*60;
                    remaintimetxt.setText(second+" Seconds ");
                }
            }


            @Override
            public void onFinish() {
                if(loop!=0)
                {
                    ArrayList<MyTimer> myTimerstmp=MainActivity.getAllTimers();

                    myTimerstmp.get(position).getEndCalendar().add(Calendar.DATE,loop);
                    CountDownTimer tobenewtimer= new CountDownTimer(5000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            if(millisUntilFinished/1000>2)
                                remaintimetxt.setText(millisUntilFinished/1000-2+" seconds to start the loop!!!");
                            else
                            {
                                remaintimetxt.setText("Starting the loop!");
                            }
                        }

                        @Override
                        public void onFinish() {
                            finish();
                        }
                    };
                    tobenewtimer.start();
                }
                else {
                    remaintimetxt.setText("Timer's Up");
                }
            }
        };
        completeinfo_timer.start();//某些场合记得要删除它哟










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
                break;
            }
            case R.id.complete_info_tothemaintop_button:
            {

            }
            case R.id.complete_info_show_button:
            {

            }
            case R.id.complete_info_delete_button:
            {
                AlertDialog.Builder builder  = new AlertDialog.Builder(this);
                builder.setTitle("Are you sure ?" ) ;
                builder.setIcon(R.drawable.skull);
                builder.setMessage("this can not be undone !" ) ;
                builder.setPositiveButton("just do it !" ,  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Intent intentswitch=new Intent(CreateTimer.this, MainActivity.class);
                        //startActivity(intentswitch);
                        Intent deleteintent=new Intent();
                        deleteintent.putExtra("deleteposition",position);
                        setResult(101,deleteintent);
                        CompleteInfo.this.finish();
                    }
                });
                builder.setNegativeButton("misclicked !",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(CompleteInfo.this,"that was close !",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
                break;

            }
            case R.id.complete_info_remain_time://模仿点击剩余时间会改变计时方式
            {
                if (switchremaintimeshow == 0)
                    switchremaintimeshow = 1;
                else if (switchremaintimeshow == 1) {
                    switchremaintimeshow = 2;
                }
                else if (switchremaintimeshow == 2) {
                    switchremaintimeshow = 3;
                }
                else if (switchremaintimeshow == 3)
                {
                    switchremaintimeshow = 0;
                }
                break;
            }
            case R.id.complete_info_edit_button:
            {
                Intent intentedit=new Intent(CompleteInfo.this,EditTimer.class);

                intentedit.putExtra("Title",title);
                intentedit.putExtra("Memo",memo);
                intentedit.putExtra("Year",year);
                intentedit.putExtra("Month",month);
                intentedit.putExtra("Day",day);
                intentedit.putExtra("Hour",hour);
                intentedit.putExtra("loop",loop);
                intentedit.putExtra("Minute",min);
                ByteArrayOutputStream output=new ByteArrayOutputStream();
                bitmapbg=Bitmap.createScaledBitmap(bitmapbg,415,620,true);
                bitmapbg.compress(Bitmap.CompressFormat.PNG,100,output);
                byte[] bytepic=output.toByteArray();
                intentedit.putExtra("bitmapbyte",bytepic);
                startActivityForResult(intentedit, EDIT_APPLY_CODE);//////////////////////////////////////
                break;
            }

        }
    }
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode)
        {
            case EDIT_APPLY_CODE:
                if(resultCode==EDIT_CHANGE_CODE){
                    year=data.getIntExtra("yearchange",0);
                    month=data.getIntExtra("monthchange",0);
                    day=data.getIntExtra("daychange",0);
                    hour=data.getIntExtra("hourchange",0);
                    min=data.getIntExtra("minutechange",0);
                    loop=data.getIntExtra("loopchange",0);
                    title=data.getStringExtra("titlechange");
                    memo=data.getStringExtra("memochange");
                    byte[] bytepic=data.getByteArrayExtra("bitmapbytechange");
                    bitmapbg=MainActivity.Bytes2Bitmap(bytepic);


                    completeinfo_timer.cancel();

                    completeinfo_calendar.set(year,month,day,hour,min,0);
                    titletxt.setText(title);
                    memotxt.setText(memo);
                    int monthtmp=completeinfo_calendar.get(Calendar.MONTH)+1;
                    String dayofweek= null;
                    switch (completeinfo_calendar.get(Calendar.DAY_OF_WEEK))
                    {
                        case 1:dayofweek="Sunday";break;
                        case 2:dayofweek="Monday";break;
                        case 3:dayofweek="Tuesday";break;
                        case 4:dayofweek="Wednesday";break;
                        case 5:dayofweek="Thursday";break;
                        case 6:dayofweek="Friday";break;
                        case 7:dayofweek="Saturday";break;
                        default:
                    }
                    if(loop==0)
                        endtimetxt.setText(completeinfo_calendar.get(Calendar.YEAR)+"-"+monthtmp+"-"+completeinfo_calendar.get(Calendar.DAY_OF_MONTH)+" "+
                                completeinfo_calendar.get(Calendar.HOUR_OF_DAY)+":"+completeinfo_calendar.get(Calendar.MINUTE)+" "+dayofweek+"\n  "+"  ------ No Loop ------");
                    else
                        endtimetxt.setText(completeinfo_calendar.get(Calendar.YEAR)+"-"+monthtmp+"-"+completeinfo_calendar.get(Calendar.DAY_OF_MONTH)+" "+
                                completeinfo_calendar.get(Calendar.HOUR_OF_DAY)+":"+completeinfo_calendar.get(Calendar.MINUTE)+" "+dayofweek+"\n  "+"------ Every "+loop+" Days ------");
                    imageViewbg.setImageBitmap(bitmapbg);

                    ArrayList<MyTimer> myTimerstmp=MainActivity.getAllTimers();
                    myTimerstmp.get(position).setPhotobitmap(bitmapbg);

                    myTimerstmp.get(position).setEndCalendar(year,month,day,hour,min);
                    myTimerstmp.get(position).setLoop(loop);
                    myTimerstmp.get(position).setTitle(title);
                    myTimerstmp.get(position).setNote(memo);









                    FileDataSource fileDataSource=new FileDataSource(this);
                    fileDataSource.save(myTimerstmp);

























                    completeinfo_timer= new CountDownTimer(getremaintime(),1000) {
                        @Override
                        public void onTick(long remaintime_millissec) {
                            long days=(long)remaintime_millissec/(1000*60*60*24);
                            long hours=(long)(remaintime_millissec%(1000*60*60*24))/(1000*60*60);
                            long mins=(long)(remaintime_millissec%(1000*60*60))/(1000*60);
                            long second=(long)(remaintime_millissec%(1000*60)/1000);
                            if(switchremaintimeshow==0) {//改变剩余时间显示格式
                                remaintimetxt.setText(days+" Days "+hours+" Hours "+mins+" Mins "+second+" Seconds ");
                            }
                            else if(switchremaintimeshow==1)
                            {
                                hours+=days*24;
                                remaintimetxt.setText(hours+" Hours "+mins+" Mins "+second+" Seconds ");
                            }
                            else if(switchremaintimeshow==2)
                            {
                                mins=mins+days*24*60+hours*60;
                                remaintimetxt.setText(mins+" Mins "+second+" Seconds ");
                            }
                            else if(switchremaintimeshow==3)
                            {
                                second=second+mins*60+days*24*60*60+hours*60*60;
                                remaintimetxt.setText(second+" Seconds ");
                            }
                        }


                        @Override
                        public void onFinish() {
                            if(loop!=0)
                            {
                                ArrayList<MyTimer> myTimerstmp=MainActivity.getAllTimers();

                                myTimerstmp.get(position).getEndCalendar().add(Calendar.DATE,loop);
                                CountDownTimer tobenewtimer= new CountDownTimer(5000, 1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        if(millisUntilFinished/1000>2)
                                        remaintimetxt.setText(millisUntilFinished/1000-2+" seconds to start the loop!!!");
                                        else
                                        {
                                            remaintimetxt.setText("Starting the loop!");
                                        }
                                    }

                                    @Override
                                    public void onFinish() {
                                        finish();

                                    }
                                };
                                tobenewtimer.start();
                            }
                            else {
                                remaintimetxt.setText("Timer's Up");
                            }

                        }

                    };
                    completeinfo_timer.start();



                    break;
                }


        }
    }
}
