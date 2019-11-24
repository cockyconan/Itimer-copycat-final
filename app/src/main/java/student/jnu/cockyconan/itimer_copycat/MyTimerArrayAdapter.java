package student.jnu.cockyconan.itimer_copycat;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.List;

import static java.util.Calendar.YEAR;

public class MyTimerArrayAdapter extends ArrayAdapter<MyTimer> {
    private int resourceid;//保存id


    public MyTimerArrayAdapter(@NonNull Context context, int resource, @NonNull List<MyTimer> objects) {
        super(context, resource, objects);
        resourceid=resource;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater mInflater=LayoutInflater.from(this.getContext());
        View item = mInflater.inflate(this.resourceid,null);

        ImageView photo=(ImageView)item.findViewById(R.id.content_mian_listview_model_photo);
        TextView title =(TextView) item.findViewById(R.id.content_main_listview_title);
        TextView enddate= (TextView) item.findViewById(R.id.content_main_listview_enddate);
        TextView memo =(TextView) item.findViewById(R.id.content_main_listview_memo);
        final TextView emergencycolor =(TextView)item.findViewById(R.id.content_main_listview_emergencycolor);
        final TextView remaintime=(TextView) item.findViewById(R.id.content_main_listview_remaintime);

        MyTimer myTimer=this.getItem(position);

        photo.setImageBitmap(myTimer.getPhotobitmap());
        title.setText(myTimer.getTitle());
        android.icu.util.Calendar calendartmp=myTimer.getEndCalendar();
        int montmp=calendartmp.get(Calendar.MONTH)+1;
        enddate.setText(""+calendartmp.get(Calendar.YEAR)+" - "+montmp+" - " +calendartmp.get(Calendar.DAY_OF_MONTH)+"  "+calendartmp.get(Calendar.HOUR)+":"+calendartmp.get(Calendar.MINUTE));

        memo.setText(myTimer.getNote());

        CountDownTimer countDownTimer;
        countDownTimer=new CountDownTimer(myTimer.getremaintime(), 1000){
            @Override
            public void onTick(long remaintime_millissec) {
                if((remaintime_millissec/(1000*60*60))<=1) {
                    emergencycolor.setBackgroundColor(Color.rgb(255, 0, 0));
                    remaintime.setText(remaintime_millissec/(1000*60)+"mins to go");
                }
                else if((remaintime_millissec/(1000*60*60))<=24) {
                    remaintime.setText(remaintime_millissec / (1000 ) + "second to go");
                    emergencycolor.setBackgroundColor(Color.rgb(255, 80, 80));
                }
                else if((remaintime_millissec/(1000*60*60))<=168) {
                    remaintime.setText(remaintime_millissec / (1000 * 60 * 60 * 24) + "days to go");
                    emergencycolor.setBackgroundColor(Color.rgb(255, 120, 0));
                }
                else if((remaintime_millissec/(1000*60*60))<=720) {
                    remaintime.setText(remaintime_millissec / (1000 * 60 * 60 * 24) + "days to go");
                    emergencycolor.setBackgroundColor(Color.rgb(255, 235, 69));
                }
                else if((remaintime_millissec/(1000*60*60))<=4320) {
                    emergencycolor.setBackgroundColor(Color.rgb(165, 165, 165));
                    remaintime.setText(remaintime_millissec / (1000 * 60 * 60 * 24) + "days to go");
                }
                else {
                    emergencycolor.setBackgroundColor(Color.rgb(255, 255, 255));
                    remaintime.setText(remaintime_millissec / (1000 * 60 * 60 * 24) + "days to go");
                }
            }

            @Override
            public void onFinish() {

            }
        };
        countDownTimer.start();
/*
        //下面设置背景颜色。
        //!!---------------------------------------------------------------------------------------------------------------------------------------------------
        long remaintime_millissec=myTimer.getremaintime();//获取剩余时间，用以设置紧急程度颜色
        if((remaintime_millissec/(1000*60*60))<=1) {
            emergencycolor.setBackgroundColor(Color.rgb(255, 0, 0));
            remaintime.setText(remaintime_millissec/(1000*60)+"mins to go");
        }
        else if((remaintime_millissec/(1000*60*60))<=24) {
            remaintime.setText(remaintime_millissec / (1000 * 60 * 60) + "hours to go");
            emergencycolor.setBackgroundColor(Color.rgb(255, 80, 80));
        }
        else if((remaintime_millissec/(1000*60*60))<=168) {
            remaintime.setText(remaintime_millissec / (1000 * 60 * 60 * 24) + "days to go");
            emergencycolor.setBackgroundColor(Color.rgb(255, 120, 0));
        }
        else if((remaintime_millissec/(1000*60*60))<=720) {
            remaintime.setText(remaintime_millissec / (1000 * 60 * 60 * 24) + "days to go");
            emergencycolor.setBackgroundColor(Color.rgb(255, 235, 69));
        }
        else if((remaintime_millissec/(1000*60*60))<=4320) {
            emergencycolor.setBackgroundColor(Color.rgb(165, 165, 165));
            remaintime.setText(remaintime_millissec / (1000 * 60 * 60 * 24) + "days to go");
        }
        else {
            emergencycolor.setBackgroundColor(Color.rgb(255, 255, 255));
            remaintime.setText(remaintime_millissec / (1000 * 60 * 60 * 24) + "days to go");
        }
*/
        //----------------------------------------------------------------------------------------------------------------------------------------------------

        return item;
    }
}
