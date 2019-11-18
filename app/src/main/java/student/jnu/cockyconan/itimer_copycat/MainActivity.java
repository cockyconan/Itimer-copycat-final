package student.jnu.cockyconan.itimer_copycat;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static android.icu.util.Calendar.getInstance;
import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    public ArrayList<MyTimer> getAllTimers() {
        return allTimers;
    }

    //my stuff begin
    private ArrayList<MyTimer> allTimers=new ArrayList<MyTimer>();
//my stuff end

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //新建界面的切换
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentswitch=new Intent(MainActivity.this, CreateTimer.class);
                startActivityForResult(intentswitch,666);
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
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
       // if(requestCode==666 && resultCode==RESULT_OK) {
            Toast.makeText(this, "created !", LENGTH_SHORT).show();


        int year=data.getIntExtra("createtimer_year",2019);
        int month=data.getIntExtra("createtimer_month",10);
        int day=data.getIntExtra("createtimer_day",18);
        int hour=data.getIntExtra("createtimer_hour",11);
        int min=data.getIntExtra("createtimer_min",0);

            MyTimer myTimertmp = new MyTimer("","", Uri.EMPTY) ;
            myTimertmp=(MyTimer) data.getParcelableExtra("createtimer");
            myTimertmp.reintializeCalendar();
            myTimertmp.setEndCalendar(year,month,day,hour,min);
            allTimers.add(myTimertmp);
       // }
       // else{
       //     Toast.makeText(this, "not create!", LENGTH_SHORT).show();
       // }



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
