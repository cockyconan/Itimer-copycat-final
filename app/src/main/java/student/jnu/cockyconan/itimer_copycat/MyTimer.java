package student.jnu.cockyconan.itimer_copycat;

import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;

import androidx.annotation.RequiresApi;

import java.util.Locale;
import java.util.Objects;
import java.util.Timer;

public class MyTimer {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public MyTimer(String title, String note, Uri photoUri, long stopTime, CountDownTimer countdown) {
        Title = title;
        Note = note;
        PhotoUri = Uri.parse(photoUri.toString());
        StopTime = stopTime;
        this.countdown = countdown;
        this.endCalendar = Calendar.getInstance(Locale.CHINA);

        loop=0;//初始为零
    }


    private String Title;   //标题
    private String Note;    //备忘
    private int loop;
    private Uri PhotoUri;   //移动端照片存储路径,而不是直接存储图片


    //借用google为我们实现的countdowntimer，
    // mstoptimeinfuture
    private long StopTime;  //每次退出程序时，保存一下结束时间，方便下次立马访问
    //不确定需不需要

    private CountDownTimer countdown;

    private android.icu.util.Calendar endCalendar; //为了方便我们记录介绍的日期而已作用不大的。

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public Uri getPhotoUri() {
        return PhotoUri;
    }

    public void setPhotoUri(Uri photoUri) {
        PhotoUri = photoUri;
    }

    public long getStopTime() {
        return StopTime;
    }

    public void setStopTime(long stopTime) {
        StopTime = stopTime;
    }

    public CountDownTimer getCountdown() {
        return countdown;
    }

    public void setCountdown(CountDownTimer countdown) {
        this.countdown = countdown;
    }

    public android.icu.util.Calendar getEndCalendar() {
        return endCalendar;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setEndCalendar(int year,int month ,int day,int hour,int min) {
        this.endCalendar.set(year, month , day, hour, min,0);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public long getremaintime()
    {
        return endCalendar.getTimeInMillis()-28800000l-Calendar.getInstance(Locale.CHINA).getTimeInMillis();
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyTimer myTimer = (MyTimer) o;
        return StopTime == myTimer.StopTime &&
                Objects.equals(Title, myTimer.Title) &&
                Objects.equals(Note, myTimer.Note) &&
                Objects.equals(PhotoUri, myTimer.PhotoUri) &&
                Objects.equals(countdown, myTimer.countdown) &&
                Objects.equals(endCalendar, myTimer.endCalendar);
    }

    public int getLoop() {
        return loop;
    }

    public void setLoop(int loop) {
        this.loop = loop;
    }




    /*
    private int Endtime_Year;
    private int Endtime_Month;
    private int Endtime_DayOfMonth;
    private int Endtime_Hour;
    private int Endtime_Minute;
    private int Endtime_Second;
*/


}
