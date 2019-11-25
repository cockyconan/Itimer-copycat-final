package student.jnu.cockyconan.itimer_copycat.ui.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import student.jnu.cockyconan.itimer_copycat.CompleteInfo;
import student.jnu.cockyconan.itimer_copycat.MainActivity;
import student.jnu.cockyconan.itimer_copycat.MyTimer;
import student.jnu.cockyconan.itimer_copycat.MyTimerArrayAdapter;
import student.jnu.cockyconan.itimer_copycat.R;

public class HomeFragment extends Fragment {

    private MyTimerArrayAdapter myTimerArrayAdapter;

    public MyTimerArrayAdapter getAdapter(){return myTimerArrayAdapter;}

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

          View root = inflater.inflate(R.layout.fragment_home, container, false);


        MainActivity mainActivity=(MainActivity)getActivity();
        final ArrayList<MyTimer> AllTimers= mainActivity.getAllTimers();
       /*
        //直接数要在后面加上l表示是long类型
        AllTimers.add(new MyTimer("titletest","notetest", Uri.EMPTY,5530082000l));
        AllTimers.add(new MyTimer("titletest","notetest", Uri.EMPTY,55930082000l));
        AllTimers.add(new MyTimer("titletest","notetest", Uri.EMPTY,999999999));
        AllTimers.add(new MyTimer("titletest","notetest", Uri.EMPTY, SystemClock.elapsedRealtime()+600000));
        AllTimers.add(new MyTimer("titletest","notetest", Uri.EMPTY,5000));
        */
//myTimerArrayAdapter.notifyDataSetChanged();

        myTimerArrayAdapter=new MyTimerArrayAdapter(this.getContext(),R.layout.fragment_home_listview_model,AllTimers);


        ListView ListViewTimer=(ListView)root.findViewById(R.id.content_main_listview);


        ListViewTimer.setAdapter(myTimerArrayAdapter);

        ListViewTimer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent complete_info_intent=new Intent(getActivity(), CompleteInfo.class);
                complete_info_intent.putExtra("position",position);
                complete_info_intent.putExtra("title",AllTimers.get(position).getTitle());
                complete_info_intent.putExtra("memo",AllTimers.get(position).getNote());
                complete_info_intent.putExtra("loop",AllTimers.get(position).getLoop());
                complete_info_intent.putExtra("year",AllTimers.get(position).getEndCalendar().get(Calendar.YEAR));
                complete_info_intent.putExtra("month",AllTimers.get(position).getEndCalendar().get(Calendar.MONTH));
                complete_info_intent.putExtra("day",AllTimers.get(position).getEndCalendar().get(Calendar.DAY_OF_MONTH));
                int hourtmp=AllTimers.get(position).getEndCalendar().get(Calendar.HOUR_OF_DAY);
                complete_info_intent.putExtra("hour",hourtmp);
                complete_info_intent.putExtra("minute",AllTimers.get(position).getEndCalendar().get(Calendar.MINUTE));
                //不知道可不可以直接传输bitmap
                ByteArrayOutputStream output=new ByteArrayOutputStream();
                Bitmap bm= Bitmap.createScaledBitmap(AllTimers.get(position).getPhotobitmap(),425,620,true);
                bm.compress(Bitmap.CompressFormat.PNG,100,output);
                byte[] bytepic = new byte[0];
                bytepic=output.toByteArray();

                complete_info_intent.putExtra("photobitmap",bytepic);

                //先尝试一下


                startActivityForResult(complete_info_intent,111);

            }
        });

        return root;
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode)
        {
            case 111:
                if(resultCode==101){
                    int position=data.getIntExtra("position",0);//这里肯定在这个程序中只是接收，因为editmainactivity已经完成操作了

                    ArrayList<MyTimer> AllTimers= MainActivity.getAllTimers();
                    AllTimers.remove(position);
                    myTimerArrayAdapter.notifyDataSetChanged();


                    break;
                }


        }
    }


}