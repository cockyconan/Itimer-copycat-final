package student.jnu.cockyconan.itimer_copycat;

import android.content.Context;

import java.io.Serializable;



    public class saveTimerdatas implements Serializable {
        String title;
        String memo;
        int year;
        int month;
        int day;
        int hour;
        int min;
        int loop;
        byte[] photobitmapbyte;




        public saveTimerdatas(String title, String memo, int year, int month, int day, int hour, int min, int loop, byte[] photobitmapbyte) {
            this.title = new String();
            this.memo = new String();
            this.year = year;
            this.month = month;
            this.day = day;
            this.hour = hour;
            this.min = min;
            this.loop = loop;
            this.photobitmapbyte = new byte[0];
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public int getHour() {
            return hour;
        }

        public void setHour(int hour) {
            this.hour = hour;
        }

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }

        public int getLoop() {
            return loop;
        }

        public void setLoop(int loop) {
            this.loop = loop;
        }

        public byte[] getPhotobitmapbyte() {
            return photobitmapbyte;
        }

        public void setPhotobitmapbyte(byte[] photobitmapbyte) {
            this.photobitmapbyte = photobitmapbyte;
        }
    }