package student.jnu.cockyconan.itimer_copycat;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import student.jnu.cockyconan.itimer_copycat.ui.home.HomeFragment;

import static android.icu.util.Calendar.getInstance;
import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {
    public static RGBcolor aRGB;
    private AppBarConfiguration mAppBarConfiguration;
    private static FileDataSource fileDataSource;
    public static FloatingActionButton fab;
    public static RelativeLayout headlayout;
    public static FileDataSource getsaveinstance() {return fileDataSource;}
    public static ArrayList<MyTimer> getAllTimers() {
        return allTimers;
    }
    public static boolean firsttime=true;
    public static Bitmap Bytes2Bitmap(byte[] b)
    {
            if(b.length!=0){
                return BitmapFactory.decodeByteArray(b,0,b.length);

            }else
            {
                return null;
            }
    }
    //my stuff begin
    private static ArrayList<MyTimer> allTimers=new ArrayList<MyTimer>();
//my stuff end

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onDestroy() {
        fileDataSource.save(allTimers);
        //
        super.onDestroy();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fileDataSource=new FileDataSource(this);
        fileDataSource.load(allTimers);


        try {
            ObjectInputStream inputStream = new ObjectInputStream(this.openFileInput("color.txt"));
            aRGB = (RGBcolor) inputStream.readObject();
            inputStream.close();
            firsttime=true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        Window window = this.getWindow();
        window.setStatusBarColor(Color.rgb(MainActivity.aRGB.getR(),MainActivity.aRGB.getG(),MainActivity.aRGB.getB()));
        window.setNavigationBarColor(Color.rgb(MainActivity.aRGB.getR(),MainActivity.aRGB.getG(),MainActivity.aRGB.getB()));
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        //新建界面的切换
        fab = findViewById(R.id.fab);
        int[][] states = new int[2][];
        states[0] = new int[]{android.R.attr.state_pressed};
        states[1] = new int[]{android.R.attr.state_enabled};
        int[] colors;
        if(firsttime) {
             colors = new int[]{0x00e43d2b, Color.rgb(aRGB.getR(), aRGB.getG(), aRGB.getB())};
        }
        else {
             colors = new int[]{0x00e43d2b, Color.rgb(0,0,0)};
             aRGB=new RGBcolor(0,0,0);
        }
        MainActivity.fab.setBackgroundTintList(new ColorStateList(states, colors));


        toolbar.setBackgroundColor(Color.rgb(aRGB.getR(), aRGB.getG(), aRGB.getB()));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentswitch=new Intent(MainActivity.this, CreateTimer.class);
                startActivityForResult(intentswitch,666);
            }
        });
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        headlayout = (RelativeLayout) headerView.findViewById(R.id.header_main);

        if(headlayout!=null)
        headlayout.setBackgroundColor(Color.rgb(aRGB.getR(),aRGB.getG(),aRGB.getB()));

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        //NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       if(requestCode==666 && resultCode==RESULT_OK) {


           byte[] bytes=data.getByteArrayExtra("bitmap");
           Bitmap bitmap=MainActivity.Bytes2Bitmap(bytes);

        int year=data.getIntExtra("createtimer_year",2019);
        int month=data.getIntExtra("createtimer_month",10);
        int day=data.getIntExtra("createtimer_day",18);
        int hour=data.getIntExtra("createtimer_hour",11);
        int min=data.getIntExtra("createtimer_min",0);

            MyTimer myTimertmp = new MyTimer("","", Uri.EMPTY) ;
            myTimertmp=(MyTimer) data.getParcelableExtra("createtimer");
            myTimertmp.reintializeCalendar();
            myTimertmp.setEndCalendar(year,month,day,hour,min);
            myTimertmp.setPhotobitmap(bitmap);
           // myTimertmp.setPhotobitmap(bitmap);
            allTimers.add(myTimertmp);
           fileDataSource=new FileDataSource(this);

           fileDataSource.save(allTimers);
           finish();
           startActivity(getIntent());
            //更新页面！！！！！！





        }

       else {
           //finish();
           //startActivity(getIntent());

       }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
