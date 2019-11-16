package student.jnu.cockyconan.itimer_copycat;

import android.content.Context;
import android.graphics.Color;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Calendar;
import java.util.List;

import static java.util.Calendar.YEAR;

public class MyTimerArrayAdapter extends ArrayAdapter<MyTimer> {
    private int resourceid;//保存id


    public MyTimerArrayAdapter(@NonNull Context context, int resource, @NonNull List<MyTimer> objects) {
        super(context, resource, objects);
        resourceid=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater mInflater=LayoutInflater.from(this.getContext());
        View item = mInflater.inflate(this.resourceid,null);

        ImageView photo=(ImageView)item.findViewById(R.id.content_mian_listview_model_photo);
        TextView title =(TextView) item.findViewById(R.id.content_main_listview_title);
        TextView enddate= (TextView) item.findViewById(R.id.content_main_listview_enddate);
        TextView memo =(TextView) item.findViewById(R.id.content_main_listview_memo);
        TextView emergencycolor =(TextView)item.findViewById(R.id.content_main_listview_emergencycolor);

        MyTimer myTimer=this.getItem(position);

        photo.setImageURI(myTimer.getPhotoUri());
        title.setText(myTimer.getTitle());
        Calendar calendartmp=myTimer.getEndCalendar();
        enddate.setText("End Date： "+calendartmp.get(Calendar.YEAR)+" - "+calendartmp.get(Calendar.MONTH)+" - " +calendartmp.get(Calendar.DAY_OF_MONTH));
        memo.setText(myTimer.getNote());


        //下面设置背景颜色。
        //!!---------------------------------------------------------------------------------------------------------------------------------------------------
        long remaintime_millissec=myTimer.getStopTime()- SystemClock.elapsedRealtime();//计算当前时间与结束时间相差多少，用以设置紧急程度颜色
        if((remaintime_millissec/(1000*60*60))<=1)
            emergencycolor.setBackgroundColor(Color.rgb(255, 0, 0));
        else if((remaintime_millissec/(1000*60*60))<=24)
            emergencycolor.setBackgroundColor(Color.rgb(255, 80, 80));
        else if((remaintime_millissec/(1000*60*60))<=168)
            emergencycolor.setBackgroundColor(Color.rgb(255, 120, 0));
        else if((remaintime_millissec/(1000*60*60))<=720)
            emergencycolor.setBackgroundColor(Color.rgb(255, 235, 69));
        else if((remaintime_millissec/(1000*60*60))<=4320)
            emergencycolor.setBackgroundColor(Color.rgb(165, 165, 165));
        else
            emergencycolor.setBackgroundColor(Color.rgb(255, 255, 255));


        //----------------------------------------------------------------------------------------------------------------------------------------------------

        return item;
    }
}
