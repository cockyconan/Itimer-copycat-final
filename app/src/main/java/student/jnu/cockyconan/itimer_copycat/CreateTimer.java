package student.jnu.cockyconan.itimer_copycat;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.method.NumberKeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.text.DateFormat;
import java.time.Year;
import java.util.Locale;

import student.jnu.cockyconan.itimer_copycat.ui.home.HomeFragment;

import static android.widget.Toast.LENGTH_SHORT;

@RequiresApi(api = Build.VERSION_CODES.N)
public class CreateTimer extends AppCompatActivity {
    private EditText title;
    private EditText note;
    private Button saveBtn;

    private TextView shortclicktxtDate;
    private TextView longclicktxtDate;
    public static MyDialog mMyDialog;
    private DateFormat format= DateFormat.getDateTimeInstance();
    private Calendar calendar= Calendar.getInstance(Locale.CHINA);
    public static Calendar RELATIVEcalendar= Calendar.getInstance(Locale.CHINA);//用于保存相对时间计算器中作为基准的日期
    public static final int PICK_PHOTO = 102;
    private View setloop_view;
    private View selectrelativetime_view;

    private static int yeartmp;
    private static int monthtmp;
    private static int daytmp;
    private static int hourtmp;
    private static int mintmp;
    private static boolean settimealready=false;
    private boolean setloopalready=false;
    private boolean setphotoalready=false;
    private static MyTimer returnTimer=new MyTimer("","",Uri.EMPTY);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_timer);

        settimealready=false;//重置


        shortclicktxtDate =(TextView)findViewById(R.id.create_timer_datetimelayout_showdate);

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
                    returnTimer.setTitle(titletxt.getText().toString());

                    if(TextUtils.isEmpty(notetxt.getText()))
                    {
                        returnTimer.setNote("good good study, day day up~~~");
                        if(settimealready==true)
                        {
                            Bitmap bm=null;
                            ContentResolver resolver=getContentResolver();
                            byte[] bytepic = new byte[0];
                            try{
                                Uri originURI=returnTimer.getPhotoUri();
                                bm=MediaStore.Images.Media.getBitmap(resolver,originURI);
                                ByteArrayOutputStream output=new ByteArrayOutputStream();
                                bm=Bitmap.createScaledBitmap(bm,415,415,true);
                                bm.compress(Bitmap.CompressFormat.PNG,100,output);
                                bytepic=output.toByteArray();
                                if(bytepic.length!=0)
                                    Toast.makeText(getApplicationContext(),"uri to bitmap success !", LENGTH_SHORT).show();
                            }catch(IOException e){
                                
                            }
                            Intent intentfinishadd=new Intent();


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

                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "please set time", LENGTH_SHORT).show();

                        }
                    }
                    else
                        {
                            String tmp=notetxt.getText().toString();
                        returnTimer.setNote(tmp.toString());
                        if(settimealready==true)
                        {

                            Bitmap bm=null;
                            ContentResolver resolver=getContentResolver();
                            byte[] bytepic = new byte[0];
                            try{
                                Uri originURI=returnTimer.getPhotoUri();
                                bm=MediaStore.Images.Media.getBitmap(resolver,originURI);
                                ByteArrayOutputStream output=new ByteArrayOutputStream();
                                bm=Bitmap.createScaledBitmap(bm,415,415,true);
                                bm.compress(Bitmap.CompressFormat.PNG,100,output);
                                bytepic=output.toByteArray();
                                if(bytepic.length!=0)
                                    Toast.makeText(getApplicationContext(),"uri to bitmap success !", LENGTH_SHORT).show();
                            }catch(IOException e){

                            }
                            Intent intentfinishadd=new Intent();


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

                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "please set time", LENGTH_SHORT).show();

                        }
                    }
                }



                //if(setloopalready==true)          no need!!!!!!!!!!!!!!!!!!!!!!
                //{
                //returnTimer.setLoop();
                //}

            }
        });



        //时间选择长按短按区分：
        //！！！！！---------------------------------------------------------------------------------------------------------------------------
        //-------------------------------------------------------------------------------------------------------------------------------------
        //实现长按出现相对时间选择
        View longtap=(View)findViewById(R.id.create_timer_date_time_layout);
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

                    showDatePickerDialog(CreateTimer.this, 0, shortclicktxtDate, calendar);

            }});
        //-------------------------------------------------------------------------------------------------------------------------------------
        //---------------------------------------------------------------------------------------------------------------------------------------

        title= (EditText)findViewById(R.id.create_timer_edittext_title);
        note =(EditText)findViewById(R.id.create_timer_edittext_note);



    }
    //!!!!-----------------------------------------------------------------------------------------------------------------
//!!!!获取图片-----------------------------------------------------------------------------------------------------------------
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == PICK_PHOTO) {
            // 从相册返回的数据
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                ImageView photo=findViewById(R.id.create_timer_picture);

                //设置photouri，photouri应该在初始时设置为null
                returnTimer.setPhotoUri(uri);

                photo.setImageURI(uri);

            }
        }
    }




//-----------------------------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------------------------
    public void onClick(View which_is_clicked)
    {
        switch (which_is_clicked.getId())
        {
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
            /*case R.id.create_timer_date_time_layout:
            {
                Toast.makeText(this, "设置时间", LENGTH_SHORT).show();
                break;
            }*/
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
            case R.id.create_timer_cancel_button:
            {
                AlertDialog.Builder builder  = new AlertDialog.Builder(this);
                builder.setTitle("Are you sure ?" ) ;
                builder.setIcon(R.drawable.skull);
                builder.setMessage("any written info will not be saved !" ) ;
                builder.setPositiveButton("just do it !" ,  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Intent intentswitch=new Intent(CreateTimer.this, MainActivity.class);
                        //startActivity(intentswitch);
                        CreateTimer.this.finish();
                    }
                });
                builder.setNegativeButton("misclicked !",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(CreateTimer.this,"that was close !",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();

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
                    Toast.makeText(this, "the value of the cycle can't be empty !", LENGTH_SHORT).show();
                    break;
                }
                else {
                    txt.setText("You choose cycle time : every "+edtxt.getText()+" days~~~");
                    returnTimer.setLoop(Integer.parseInt(edtxt.getText().toString()));
                    mMyDialog.dismiss();
                    break;
                }

            }
            //相对时间窗口修改基准时间点击
            case R.id.create_timer_selectrelativedate_therelativedate:
            {

                //因为此处相对时间选择的视图还没有渲染，所以不能获取那个文本空间，这里设置为1，是为了里面区分：

                showDatePickerDialog(this,  3, longclicktxtDate, calendar);
                break;
            }
            //相对时间窗口几天之后选择
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
                    yeartmp=RELATIVEcalendar.get(Calendar.YEAR);
                    monthtmp=RELATIVEcalendar.get(Calendar.MONTH);
                    daytmp =RELATIVEcalendar.get(Calendar.DAY_OF_MONTH);
                    final TextView txtTime = (TextView) findViewById(R.id.create_timer_datetimelayout_showtime);
                    showTimePickerDialog(this, 3, txtTime, RELATIVEcalendar);

                }
                break;
            }
            //相对时间窗口几天之前选择
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
                    yeartmp=RELATIVEcalendar.get(Calendar.YEAR);
                    monthtmp=RELATIVEcalendar.get(Calendar.MONTH);
                    daytmp =RELATIVEcalendar.get(Calendar.DAY_OF_MONTH);

                    final TextView txtTime = (TextView) findViewById(R.id.create_timer_datetimelayout_showtime);
                    showTimePickerDialog(this, 3, txtTime, RELATIVEcalendar);

                }
                break;
            }
            //相对时间窗口取消按钮，其实不必要。
            case R.id.create_timer_selectrelativetime_cancelbutton:
            {
                mMyDialog.dismiss();
                break;
            }

                default: Toast.makeText(this, "please click on valid button ！", LENGTH_SHORT).show();
        }
    }




    //日期时间选择：
    //！！！！！！！！！！--------------------------------------------------------------------------------------------------------------------
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void showDatePickerDialog(final Activity activity, int themeResId, final TextView tv, final Calendar calendar) {
        switch(tv.getId())//用于区分不同的点击事件，准确来说是长按与短按的区分
        {
            case R.id.create_timer_datetimelayout_showdate:
            {
                // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
                new DatePickerDialog(activity, themeResId, new DatePickerDialog.OnDateSetListener() {
                    // 绑定监听器(How the parent is notified that the date is set.)
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // 此处得到选择的时间，可以进行你想要的操作
                        yeartmp=year;
                        monthtmp=monthOfYear;
                        daytmp =dayOfMonth;


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
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {


                        hourtmp=hourOfDay;
                        mintmp=minute;
                        returnTimer.setEndCalendar(yeartmp,monthtmp,daytmp,hourtmp ,mintmp);//设置calendar

                        tv.setText("TIME:  " + hourOfDay + " : " + minute  + " ~~~");

                        settimealready=true;//因为在静态成员函数中调用，所以需要在判断后再修改回来。
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
