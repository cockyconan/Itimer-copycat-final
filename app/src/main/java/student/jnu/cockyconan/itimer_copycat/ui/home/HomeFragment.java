package student.jnu.cockyconan.itimer_copycat.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
        ArrayList<MyTimer> AllTimers= mainActivity.getAllTimers();
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

        return root;
    }


}