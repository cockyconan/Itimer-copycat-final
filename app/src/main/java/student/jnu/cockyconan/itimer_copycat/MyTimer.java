package student.jnu.cockyconan.itimer_copycat;

import android.graphics.Bitmap;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.Locale;
import java.util.Objects;
import java.util.Timer;

public class MyTimer implements Parcelable {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public MyTimer(String title, String note, Uri photoUri) {
        Title = title;
        Note = note;
        PhotoUri = Uri.parse(photoUri.toString());

        endCalendar = Calendar.getInstance(Locale.CHINA);

        loop=0;//初始为零
    }

    private Bitmap photobitmap;
    private String Title;   //标题
    private String Note;    //备忘
    private int loop;
    private Uri PhotoUri;   //移动端照片存储路径,而不是直接存储图片


    //借用google为我们实现的countdowntimer，
    // mstoptimeinfuture




    private android.icu.util.Calendar endCalendar; //为了方便我们记录介绍的日期而已作用不大的。

    protected MyTimer(Parcel in) {
        Title = in.readString();
        Note = in.readString();
        loop = in.readInt();
        PhotoUri = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Creator<MyTimer> CREATOR = new Creator<MyTimer>() {
        @Override
        public MyTimer createFromParcel(Parcel in) {
            return new MyTimer(in);
        }

        @Override
        public MyTimer[] newArray(int size) {
            return new MyTimer[size];
        }
    };

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





    public android.icu.util.Calendar getEndCalendar() {
        return endCalendar;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setEndCalendar(int year,int month ,int day,int hour,int min) {
        reintializeCalendar();
        this.endCalendar.set(year, month , day, hour, min,0);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void reintializeCalendar()
    {
        endCalendar= Calendar.getInstance(Locale.CHINA);
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
        return
                Objects.equals(Title, myTimer.Title) &&
                Objects.equals(Note, myTimer.Note) &&
                Objects.equals(PhotoUri, myTimer.PhotoUri) &&
                Objects.equals(endCalendar, myTimer.endCalendar);
    }

    public int getLoop() {
        return loop;
    }

    public void setLoop(int loop) {
        this.loop = loop;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Title);
        dest.writeString(Note);
        dest.writeInt(loop);
        dest.writeParcelable(PhotoUri, flags);
    }

    public Bitmap getPhotobitmap() {
        return photobitmap;
    }

    public void setPhotobitmap(Bitmap photobitmap) {
        this.photobitmap = photobitmap;
    }







}
