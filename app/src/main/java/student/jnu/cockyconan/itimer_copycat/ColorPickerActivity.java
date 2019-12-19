package student.jnu.cockyconan.itimer_copycat;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_SHORT;

public class ColorPickerActivity extends AppCompatActivity {
    private LinearLayout ll;
    private TextView tv;
    private int returnR;
    private int returnG;
    private int returnB;
    private boolean ifset=false;
    private ColorPickerView colorPickerView;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_color_picker);
        ll = (LinearLayout) findViewById(R.id.ll_color);
        tv = (TextView) findViewById(R.id.tv_info);
        Window window = this.getWindow();
        window.setStatusBarColor(Color.rgb(MainActivity.aRGB.getR(),MainActivity.aRGB.getG(),MainActivity.aRGB.getB()));
        window.setNavigationBarColor(Color.rgb(MainActivity.aRGB.getR(),MainActivity.aRGB.getG(),MainActivity.aRGB.getB()));
        colorPickerView = new ColorPickerView(this);
        ll.addView(colorPickerView);
        colorPickerView.setOnColorBackListener(new ColorPickerView.OnColorBackListener() {
            @Override
            public void onColorBack(int a, int r, int g, int b) {
                tv.setText("R：" + r + "\nG：" + g + "\nB：" + b + "\n" + colorPickerView.getStrColor());
                returnR=r;
                returnG=g;
                returnB=b;
                ifset=true;
                tv.setTextColor(Color.argb(a, r, g, b));
            }
        });


    }
    public void onClick(View which_is_clicked)
    {
        switch (which_is_clicked.getId()) {

            case R.id.setcolor_OK:{
                if(ifset) {
                    Intent intent = new Intent();
                    intent.putExtra("returnR", returnR);
                    intent.putExtra("returnG", returnG);
                    intent.putExtra("returnB", returnB);
                    //setResult();
                    setResult(666,intent);
                    ColorPickerActivity.this.finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "You must choose a new color !", LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.setcolor_CANCEL: {
                Intent intent = new Intent();
                setResult(444,intent);
                ColorPickerActivity.this.finish();

                break;
            }
        }



    }

}
