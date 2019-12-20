package student.jnu.cockyconan.itimer_copycat;


import android.content.Context;
import android.graphics.Bitmap;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by chen on 2019/10/14.
 */

public class FileDataSource {
    private Context context;
    public FileDataSource(Context context) {
        this.context = context;
    }



    private ArrayList< saveTimerdatas> AllTimerDatas= new ArrayList<saveTimerdatas>();
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void save(ArrayList<MyTimer> alltimers)
    {

        try {


            ObjectOutputStream outputStream = new ObjectOutputStream(context.openFileOutput("serializablefinal.txt", Context.MODE_PRIVATE));//private是删除以前的，重新写,append是追加
            AllTimerDatas.clear();
            for(int i=0;i<alltimers.size();i++)
            {
                saveTimerdatas AsaveTimerdatas=new saveTimerdatas(null, null, 0, 0,0 ,0, 0, 0,null);
                AsaveTimerdatas.year=alltimers.get(i).getEndCalendar().get(Calendar.YEAR);
                AsaveTimerdatas.month=alltimers.get(i).getEndCalendar().get(Calendar.MONTH);
                AsaveTimerdatas.day=alltimers.get(i).getEndCalendar().get(Calendar.DAY_OF_MONTH);
                AsaveTimerdatas.hour=alltimers.get(i).getEndCalendar().get(Calendar.HOUR_OF_DAY);
                AsaveTimerdatas.min=alltimers.get(i).getEndCalendar().get(Calendar.MINUTE);
                AsaveTimerdatas.title=alltimers.get(i).getTitle();
                AsaveTimerdatas.memo=alltimers.get(i).getNote();
                AsaveTimerdatas.loop=alltimers.get(i).getLoop();
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                Bitmap bm;
                bm = Bitmap.createScaledBitmap(alltimers.get(i).getPhotobitmap(), 415, 415, true);
                bm.compress(Bitmap.CompressFormat.PNG, 100, output);
                AsaveTimerdatas.photobitmapbyte = output.toByteArray();
                AllTimerDatas.add(AsaveTimerdatas);
            }
            outputStream.writeObject(AllTimerDatas);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }//其实是语法上有瑕疵，但是这里不深纠，直接catch
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void load(ArrayList<MyTimer> Alltimers) {

        try {


            ObjectInputStream inputStream = new ObjectInputStream(context.openFileInput("serializablefinal.txt"));
           Alltimers.clear();
            AllTimerDatas = (ArrayList<saveTimerdatas>) inputStream.readObject();

            inputStream.close();
            for(int i=0;i<AllTimerDatas.size();i++) {
                MyTimer timertmp=new MyTimer("","", Uri.EMPTY);//
                timertmp.setNote(AllTimerDatas.get(i).memo);
                timertmp.setTitle(AllTimerDatas.get(i).title);
                timertmp.setLoop(AllTimerDatas.get(i).loop);
                timertmp.setEndCalendar(AllTimerDatas.get(i).year,AllTimerDatas.get(i).month,AllTimerDatas.get(i).day,AllTimerDatas.get(i).hour,AllTimerDatas.get(i).min
                        );
                timertmp.setPhotobitmap(MainActivity.Bytes2Bitmap(AllTimerDatas.get(i).photobitmapbyte));
                Alltimers.add(timertmp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
