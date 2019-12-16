package student.jnu.cockyconan.itimer_copycat.ui.gallery;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import student.jnu.cockyconan.itimer_copycat.ColorPickerActivity;
import student.jnu.cockyconan.itimer_copycat.CompleteInfo;
import student.jnu.cockyconan.itimer_copycat.FileDataSource;
import student.jnu.cockyconan.itimer_copycat.MainActivity;
import student.jnu.cockyconan.itimer_copycat.MyTimer;
import student.jnu.cockyconan.itimer_copycat.R;
import student.jnu.cockyconan.itimer_copycat.RGBcolor;
import student.jnu.cockyconan.itimer_copycat.saveTimerdatas;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;

    private RGBcolor aRGB=new RGBcolor(0,0,0);
    private Button changecolorbtn;
    private ImageView current_color_show;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        current_color_show=root.findViewById(R.id.current_color_show);
        changecolorbtn=root.findViewById(R.id.changecolorbtn);
        changecolorbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changecolorintent=new Intent(getActivity(), ColorPickerActivity.class);
                startActivityForResult(changecolorintent,678);
            }
        });


        try {
            ObjectInputStream inputStream = new ObjectInputStream(getActivity().openFileInput("color.txt"));
            aRGB = (RGBcolor) inputStream.readObject();
            inputStream.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(aRGB!=null)
        current_color_show.setBackgroundColor(Color.rgb(aRGB.getR(),aRGB.getG(),aRGB.getB()));





        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode)
        {
            case 678:
                if(resultCode==666){
                    aRGB.setR(data.getIntExtra("returnR",0));
                    aRGB.setG(data.getIntExtra("returnG",0));
                    aRGB.setB(data.getIntExtra("returnB",0));
                    current_color_show.setBackgroundColor(Color.rgb(aRGB.getR(),aRGB.getG(),aRGB.getB()));
                    try {
                        ObjectOutputStream outputStream = new ObjectOutputStream(getActivity().openFileOutput("color.txt", Context.MODE_PRIVATE));
                        outputStream.writeObject(aRGB);
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }//保存设置的颜色
                    MainActivity.getsaveinstance().save(MainActivity.getAllTimers());
                    int[][] states = new int[2][];
                    states[0] = new int[]{android.R.attr.state_pressed};
                    states[1] = new int[]{android.R.attr.state_enabled};
                    int[] colors = new int[]{0x00e43d2b,Color.rgb(aRGB.getR(),aRGB.getG(),aRGB.getB()) };
                   MainActivity.fab.setBackgroundTintList(new ColorStateList(states, colors));


                    MainActivity.headlayout.setBackgroundColor(Color.rgb(aRGB.getR(),aRGB.getG(),aRGB.getB()));
                    MainActivity.aRGB.setR(aRGB.getR());
                    MainActivity.aRGB.setG(aRGB.getG());
                    MainActivity.aRGB.setB(aRGB.getB());
                    Window window = getActivity().getWindow();
                    window.setStatusBarColor(Color.rgb(MainActivity.aRGB.getR(),MainActivity.aRGB.getG(),MainActivity.aRGB.getB()));
                    window.setNavigationBarColor(Color.rgb(MainActivity.aRGB.getR(),MainActivity.aRGB.getG(),MainActivity.aRGB.getB()));
                    Toolbar toolbar = getActivity().findViewById(R.id.toolbar);

                    toolbar.setBackgroundColor(Color.rgb(aRGB.getR(), aRGB.getG(), aRGB.getB()));
                    break;
                }


        }
    }
}