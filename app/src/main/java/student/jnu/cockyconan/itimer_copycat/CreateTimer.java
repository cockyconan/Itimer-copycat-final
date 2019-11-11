package student.jnu.cockyconan.itimer_copycat;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Locale;

import static android.widget.Toast.LENGTH_SHORT;

@RequiresApi(api = Build.VERSION_CODES.N)
public class CreateTimer extends AppCompatActivity {
    private EditText title;
    private EditText note;
    private Button saveBtn;
    private Button cancelBtn;
    private TextView shortclicktxtDate;
    private TextView longclicktxtDate;
    private MyDialog mMyDialog;
    private DateFormat format= DateFormat.getDateTimeInstance();
    private Calendar calendar= Calendar.getInstance(Locale.CHINA);
    private View setloop_view;
    private View selectrelativetime_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_timer);
        shortclicktxtDate =(TextView)findViewById(R.id.create_timer_datetimelayout_showdate);

        saveBtn = (Button) findViewById(R.id.create_timer_save_button);
        cancelBtn =(Button) findViewById(R.id.create_timer_cancel_button);


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
                longclicktxtDate=(TextView) selectrelativetime_view.findViewById(R.id.create_timer_selectrelativedate_thedate);
                Calendar calendar=Calendar.getInstance();
                longclicktxtDate.setText(calendar.get(Calendar.YEAR)+" - " +calendar.get(Calendar.MONTH)+" - "+calendar.get(Calendar.DAY_OF_MONTH) );
                return true;//return true不响应短按事件
            }


        });
        //单点又出现不同的选择
        longtap.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
               // Toast.makeText(v.getContext(),"短按时间设置", LENGTH_SHORT).show();
                showDatePickerDialog(CreateTimer.this,  0, shortclicktxtDate, calendar);

            }});
        //-------------------------------------------------------------------------------------------------------------------------------------
        //---------------------------------------------------------------------------------------------------------------------------------------

        title= (EditText)findViewById(R.id.create_timer_edittext_title);
        note =(EditText)findViewById(R.id.create_timer_edittext_note);



    }

    public void onClick(View which_is_clicked)
    {
        switch (which_is_clicked.getId())
        {
            case R.id.create_timer_addpicture_layout: {

                Toast.makeText(this, "添加图片", LENGTH_SHORT).show();
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
                        Intent intentswitch=new Intent(CreateTimer.this, MainActivity.class);
                        startActivity(intentswitch);
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
            case R.id.create_timer_save_button:
            {
                Toast.makeText(this, "保存按钮", LENGTH_SHORT).show();
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
                    mMyDialog.dismiss();
                    break;
                }

            }
            //相对时间窗口修改基准时间点击
            case R.id.create_timer_selectrelativedate_thedate:
            {


                //因为此处相对时间选择的视图还没有渲染，所以不能获取那个文本空间，这里设置为1，是为了里面区分：
               // longclicktxtDate.setId(R.id.create_timer_selectrelativedate_thedate);
                //problem
                showDatePickerDialog(this,  3, longclicktxtDate, calendar);
                break;
            }
            //相对时间窗口几天之后选择
            case R.id.create_timer_selectrelativetime_daysafter_button:
            {
                break;
            }
            //相对时间窗口几天之前选择
            case R.id.create_timer_selectrelativetime_daysbefore_button:
            {
                break;
            }
            //相对时间窗口取消按钮，其实不必要。
            case R.id.create_timer_selectrelativetime_cancelbutton:
            {
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
                        tv.setText("You choose：#Year " + year + " #Month " + (monthOfYear + 1) + " #Day " + dayOfMonth + " ~~~");
                        final TextView txtTime=(TextView)activity.findViewById(R.id.create_timer_datetimelayout_showtime);
                        showTimePickerDialog(activity,  0, txtTime, calendar);
                    }
                }
                        // 设置初始日期
                        , calendar.get(Calendar.YEAR)
                        , calendar.get(Calendar.MONTH)
                        , calendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            }
            case R.id.create_timer_selectrelativedate_thedate:
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
    public static void showTimePickerDialog(final Activity activity, int themeResId, final TextView tv, final Calendar calendar) {
        // Calendar c = Calendar.getInstance();
        // 创建一个TimePickerDialog实例，并把它显示出来
        // 解释一哈，Activity是context的子类
        new TimePickerDialog( activity,themeResId,
                // 绑定监听器
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        tv.setText("You choose: #Hour " + hourOfDay + " #Minute " + minute  + " ~~~");

                    }
                }
                // 设置初始时间
                , calendar.get(Calendar.HOUR_OF_DAY)
                , calendar.get(Calendar.MINUTE)
                // true表示采用24小时制
                ,true).show();
    }

//--------------------------------------------------------------------------------------------------------------------------------------！！！！！！！


}
