package student.jnu.cockyconan.itimer_copycat;

import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;

import androidx.annotation.RequiresApi;

import java.util.Calendar;
import java.util.Objects;
import java.util.Timer;

class MyTimer {

    public MyTimer(String title, String note, Uri photoUri, long stopTime, CountDownTimer countdown, Calendar endCalendar) {
        Title = title;
        Note = note;
        PhotoUri = photoUri;
        StopTime = stopTime;
        this.countdown = countdown;
        this.endCalendar = endCalendar;
    }


    private String Title;   //标题
    private String Note;    //备忘
    private Uri PhotoUri;   //移动端照片存储路径,而不是直接存储图片


    //借用google为我们实现的countdowntimer，
    // mstoptimeinfuture
    private long StopTime;  //每次退出程序时，保存一下结束时间，方便下次立马访问
    //不确定需不需要

    private CountDownTimer countdown;

    private Calendar endCalendar; //为了方便我们记录介绍的日期而已作用不大的。

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

    public Calendar getEndCalendar() {
        return endCalendar;
    }

    public void setEndCalendar(Calendar endCalendar) {
        this.endCalendar = endCalendar;
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


    /*
    private int Endtime_Year;
    private int Endtime_Month;
    private int Endtime_DayOfMonth;
    private int Endtime_Hour;
    private int Endtime_Minute;
    private int Endtime_Second;
*/


}
