package student.jnu.cockyconan.itimer_copycat;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;

import static android.widget.Toast.LENGTH_SHORT;
import static student.jnu.cockyconan.itimer_copycat.CreateTimer.showDatePickerDialog;

@RequiresApi(api = Build.VERSION_CODES.N)
public class EditTimer extends AppCompatActivity {

    private static final int PICK_PHOTO = 111;
    private static int year;
    private int month;
    private int day;
    private static int hour;
    private static int minute;
    private String title;
    private String memo;
    private int loopday;
    private Button saveBtn;
    private byte[] returnbitmapbyte;


    private Calendar calendar= Calendar.getInstance(Locale.CHINA);
    public static Calendar RELATIVEcalendar= Calendar.getInstance(Locale.CHINA);//用于保存相对时间计算器中作为基准的日期
    private Uri uritmp;
    public static MyDialog mMyDialog;
    private View setloop_view;
    private View selectrelativetime_view;
    private TextView shortclicktxtDate;
    private TextView longclicktxtDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_timer);
        View longtap=(View)findViewById(R.id.create_timer_date_time_layout);

        Intent recieveintent=getIntent();
        year =recieveintent.getIntExtra("Year",0);
        month =recieveintent.getIntExtra("Month",0);
        day=recieveintent.getIntExtra("Day",0);
        hour=recieveintent.getIntExtra("Hour",0);
        minute=recieveintent.getIntExtra("Minute",0);
        title=recieveintent.getStringExtra("Title");
        memo=recieveintent.getStringExtra("Memo");
        loopday=recieveintent.getIntExtra("loop",0);
        returnbitmapbyte=recieveintent.getByteArrayExtra("bitmapbyte");

        Bitmap bitmapbg=MainActivity.Bytes2Bitmap(returnbitmapbyte);



        TextView titleedittxt=(TextView) findViewById(R.id.create_timer_edittext_title);
        titleedittxt.setText(title);
        TextView memoedittxt=(TextView) findViewById(R.id.create_timer_edittext_note);
        memoedittxt.setText(memo);
        TextView datetxt=(TextView) findViewById(R.id.create_timer_datetimelayout_showdate);
        int monthshow=month+1;
        datetxt.setText(year+" - "+monthshow+" - "+day+"~~~");
        TextView timetxt=findViewById(R.id.create_timer_datetimelayout_showtime);
        timetxt.setText(hour+" : "+minute+"~~~");
        ImageView photo=findViewById(R.id.create_timer_picture);
        photo.setImageBitmap(bitmapbg);
        TextView looptxt=findViewById(R.id.create_timer_repeat_show);
        looptxt.setText("You set cycle time : every "+loopday+" days~~~");



        //////////////////////////////////////////////
        //////////////////////////////////////////////!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //loopday=;























        shortclicktxtDate =(TextView)findViewById(R.id.create_timer_datetimelayout_showdate);
        longtap.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                selectrelativetime_view = getLayoutInflater().inflate(R.layout.create_timer_relative_time_selector, null);
                mMyDialog  = new MyDialog( view.getContext(), 0, 0, selectrelativetime_view, R.style.DialogTheme);
                mMyDialog.setCancelable(true);
                mMyDialog.show();
                longclicktxtDate=(TextView) selectrelativetime_view.findViewById(R.id.create_timer_selectrelativedate_therelativedate);
                Calendar calendar=Calendar.getInstance();
                int month=calendar.get(Calendar.MONTH)+1;
                longclicktxtDate.setText(calendar.get(Calendar.YEAR)+" - " +month+" - "+calendar.get(Calendar.DAY_OF_MONTH) );
                return true;//return true不响应短按事件
            }


        });
        //单点又出现不同的选择
        longtap.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                // Toast.makeText(v.getContext(),"短按时间设置", LENGTH_SHORT).show();

                showDatePickerDialog(EditTimer.this, 0, shortclicktxtDate, calendar);

            }});
        saveBtn = (Button) findViewById(R.id.create_timer_save_button);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText titletxt=(EditText) findViewById(R.id.create_timer_edittext_title);
                EditText notetxt=(EditText) findViewById(R.id.create_timer_edittext_note);

                if(TextUtils.isEmpty(titletxt.getText()))
                {
                    Toast.makeText(getApplicationContext(), "the title can't be empty !", LENGTH_SHORT).show();

                }
                else{
                    title=titletxt.getText().toString();



                        memo=notetxt.getText().toString();

                            Bitmap bm=null;
                            ContentResolver resolver=getContentResolver();

                            try{
                                Uri originURI=uritmp;
                                bm=MediaStore.Images.Media.getBitmap(resolver,originURI);
                                ByteArrayOutputStream output=new ByteArrayOutputStream();
                                bm=Bitmap.createScaledBitmap(bm,415,415,true);
                                bm.compress(Bitmap.CompressFormat.PNG,100,output);
                                returnbitmapbyte=output.toByteArray();
                                //重新设置图片传回的字节串
                            }catch(IOException e){

                            }
                            Intent intentfinishadd=new Intent();

/*
                            //intentfinishadd.putExtra("CreateTimer", returnTimer);
                            intentfinishadd.putExtra("bitmap",bytepic);
                            intentfinishadd.putExtra("createtimer",returnTimer);
                            intentfinishadd.putExtra("createtimer_year",returnTimer.getEndCalendar().get(Calendar.YEAR));
                            intentfinishadd.putExtra("createtimer_month",returnTimer.getEndCalendar().get(Calendar.MONTH));
                            intentfinishadd.putExtra("createtimer_day",returnTimer.getEndCalendar().get(Calendar.DAY_OF_MONTH));
                            intentfinishadd.putExtra("createtimer_hour",returnTimer.getEndCalendar().get(Calendar.HOUR));
                            intentfinishadd.putExtra("createtimer_min",returnTimer.getEndCalendar().get(Calendar.MINUTE));
                            setResult(RESULT_OK,intentfinishadd);
                            CreateTimer.this.finish();
*/


                }








                //需要补充返回的东西
















                //if(setloopalready==true)          no need!!!!!!!!!!!!!!!!!!!!!!
                //{
                //returnTimer.setLoop();
                //}

            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == PICK_PHOTO) {
            // 从相册返回的数据
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                ImageView photo=findViewById(R.id.create_timer_picture);

                //设置photouri，photouri应该在初始时设置为null
                uritmp=uri;

                photo.setImageURI(uri);

            }
        }
    }

    public void onClick(View which_is_clicked) {
        switch (which_is_clicked.getId()) {
            case R.id.create_timer_addpicture_layout: {
                if (
                        ContextCompat.checkSelfPermission(this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
                } else {
                    //打开相册
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    //Intent.ACTION_GET_CONTENT = "android.intent.action.GET_CONTENT"
                    intent.setType("image/*");
                    startActivityForResult(intent, PICK_PHOTO); // 打开相册
                }
                break;
            }
            case R.id.create_timer_cancel_button:
            {
                EditTimer.this.finish();

                break;
            }
            case R.id.create_timer_setloopdialog_buttoncancel:
            {
                mMyDialog.dismiss();
                break;
            }
            case R.id.create_timer_setloopdialog_buttonok://设置周期的确定按钮
            {
                //必须渲染来获取dialog中的控件


                EditText edtxt=(EditText)setloop_view.findViewById(R.id.create_timer_setloop_value);
                TextView txt=(TextView) findViewById(R.id.create_timer_repeat_show);


                if(TextUtils.isEmpty(edtxt.getText()))
                {
                    loopday=0;
                    txt.setText("You choose cycle time : no loop~~~");
                    mMyDialog.dismiss();
                    break;
                }
                else {
                    txt.setText("You choose cycle time : every "+edtxt.getText()+" days~~~");
                    loopday=Integer.parseInt(edtxt.getText().toString());
                    mMyDialog.dismiss();
                    break;
                }

            }
            case R.id.create_timer_repeat_layout:
            {

                setloop_view = getLayoutInflater().inflate(R.layout.create_timer_setloop_dialog, null);
                mMyDialog = new MyDialog(this, 0, 0, setloop_view, R.style.DialogTheme);
                mMyDialog.setCancelable(true);
                mMyDialog.show();
                //https://blog.csdn.net/qq_34882418/article/details/81085608
                //https://blog.csdn.net/sakurakider/article/details/80735400
                break;
            }
            case R.id.create_timer_selectrelativedate_therelativedate:
            {

                //因为此处相对时间选择的视图还没有渲染，所以不能获取那个文本空间，这里设置为1，是为了里面区分：

                showDatePickerDialog(this,  3, longclicktxtDate, calendar);
                break;
            }
            case R.id.create_timer_selectrelativetime_daysafter_button:
            {
                EditText edtxtafter=(EditText)selectrelativetime_view.findViewById(R.id.create_timer_relativetimeselector_daysafter);

                if(TextUtils.isEmpty(edtxtafter.getText()))
                {
                    Toast.makeText(this, "the value of the days after can't be empty !", LENGTH_SHORT).show();
                    break;
                }
                else {
                    int adddays=Integer.parseInt(edtxtafter.getText().toString());
                    RELATIVEcalendar.add(Calendar.DATE,adddays);//作添加相对日子
                    TextView date_show=(TextView)findViewById(R.id.create_timer_datetimelayout_showdate);
                    int monthtEmp=RELATIVEcalendar.get(Calendar.MONTH)+1;
                    date_show.setText("DATE:  " + RELATIVEcalendar.get(Calendar.YEAR) + " - " + monthtEmp + " - " + RELATIVEcalendar.get(Calendar.DAY_OF_MONTH) + " ~~~");
                    //RELATIVEcalendar= Calendar.getInstance(Locale.CHINA);//static的修复
                    year=RELATIVEcalendar.get(Calendar.YEAR);
                    month=RELATIVEcalendar.get(Calendar.MONTH);
                    day =RELATIVEcalendar.get(Calendar.DAY_OF_MONTH);
                    final TextView txtTime = (TextView) findViewById(R.id.create_timer_datetimelayout_showtime);
                    showTimePickerDialog(this, 3, txtTime, RELATIVEcalendar);

                }
                break;
            }
            case R.id.create_timer_selectrelativetime_daysbefore_button:
            {
                EditText edtxtafter=(EditText)selectrelativetime_view.findViewById(R.id.create_timer_RTS_daysbefore);

                if(TextUtils.isEmpty(edtxtafter.getText()))
                {
                    Toast.makeText(this, "the value of the days before can't be empty !", LENGTH_SHORT).show();
                    break;
                }
                else {
                    int adddays=-Integer.parseInt(edtxtafter.getText().toString());
                    RELATIVEcalendar.add(Calendar.DATE,adddays);//作添加相对日子
                    TextView date_show=(TextView)findViewById(R.id.create_timer_datetimelayout_showdate);
                    int monthtEmp=RELATIVEcalendar.get(Calendar.MONTH)+1;
                    date_show.setText("DATE:  " + RELATIVEcalendar.get(Calendar.YEAR) + " - " + monthtEmp + " - " + RELATIVEcalendar.get(Calendar.DAY_OF_MONTH) + " ~~~");
                    year=RELATIVEcalendar.get(Calendar.YEAR);
                    month=RELATIVEcalendar.get(Calendar.MONTH);
                    day =RELATIVEcalendar.get(Calendar.DAY_OF_MONTH);

                    final TextView txtTime = (TextView) findViewById(R.id.create_timer_datetimelayout_showtime);
                    showTimePickerDialog(this, 3, txtTime, RELATIVEcalendar);

                }
                break;
            }
            case R.id.create_timer_selectrelativetime_cancelbutton:
            {
                mMyDialog.dismiss();
                break;
            }

            default: Toast.makeText(this, "please click on valid button ！", LENGTH_SHORT).show();

        }
    }




































    @RequiresApi(api = Build.VERSION_CODES.N)
    public void showDatePickerDialog(final Activity activity, int themeResId, final TextView tv, final Calendar calendar) {
        switch(tv.getId())//用于区分不同的点击事件，准确来说是长按与短按的区分
        {
            case R.id.create_timer_datetimelayout_showdate:
            {
                // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
                new DatePickerDialog(activity, themeResId, new DatePickerDialog.OnDateSetListener() {
                    // 绑定监听器(How the parent is notified that the date is set.)
                    @Override
                    public void onDateSet(DatePicker view, int yeartmp, int monthOfYear, int dayOfMonth) {
                        // 此处得到选择的时间，可以进行你想要的操作
                        year=yeartmp;
                        month=monthOfYear;
                        day =dayOfMonth;


                        tv.setText("DATE:  " + year + " - " + (monthOfYear + 1) + " - " + dayOfMonth + " ~~~");

                        final TextView txt = (TextView) activity.findViewById(R.id.create_timer_datetimelayout_showtime);

                        showTimePickerDialog(activity, 0, txt, calendar);


                    }
                }
                        // 设置初始日期
                        , calendar.get(Calendar.YEAR)
                        , calendar.get(Calendar.MONTH)
                        , calendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            }
            case R.id.create_timer_selectrelativedate_therelativedate:
            {

                new DatePickerDialog(activity, themeResId, new DatePickerDialog.OnDateSetListener() {
                    // 绑定监听器(How the parent is notified that the date is set.)
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // 此处得到选择的时间，可以进行你想要的操作

                        tv.setText(  ""+year + " - " + (monthOfYear + 1) + " - " + dayOfMonth + "");


                    }
                }
                        // 设置初始日期
                        , calendar.get(Calendar.YEAR)
                        , calendar.get(Calendar.MONTH)
                        , calendar.get(Calendar.DAY_OF_MONTH)).show();
                break;

            }

        }


    }

    /**
     * 时间选择
     * @param activity
     * @param themeResId
     * @param tv
     * @param calendar
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void showTimePickerDialog(final Activity activity, final int themeResId, final TextView tv, final Calendar calendar) {
        // Calendar c = Calendar.getInstance();
        // 创建一个TimePickerDialog实例，并把它显示出来
        // 解释一哈，Activity是context的子类

        new TimePickerDialog( activity,themeResId,
                // 绑定监听器
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minutetmp) {


                        hour=hourOfDay;
                        minute=minutetmp;


                        tv.setText("TIME:  " + hourOfDay + " : " + minute  + " ~~~");


                        RELATIVEcalendar= Calendar.getInstance(Locale.CHINA);//static的修复
                        if(themeResId==3)
                            mMyDialog.dismiss();
                    }
                }
                // 设置初始时间
                , calendar.get(Calendar.ZONE_OFFSET)
                , calendar.get(Calendar.ZONE_OFFSET)
                // true表示采用24小时制
                ,true).show();
        if(themeResId==3)
            mMyDialog.dismiss();//修复bug，否则会设置时间时点击取消，static变量将不会重置！！！！！

        RELATIVEcalendar= Calendar.getInstance(Locale.CHINA);//static的修复

    }

//--------------------------------------------------------------------------------------------------------------------------------------！！！！！！！


}



