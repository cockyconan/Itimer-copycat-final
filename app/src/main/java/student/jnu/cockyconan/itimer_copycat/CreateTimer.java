package student.jnu.cockyconan.itimer_copycat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CreateTimer extends AppCompatActivity {
    private EditText title;
    private EditText note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_timer);


        title= (EditText)findViewById(R.id.create_timer_edittext_title);
        note =(EditText)findViewById(R.id.create_timer_edittext_note);



    }
    public void onClick(View which_is_clicked)
    {
        switch (which_is_clicked.getId())
        {
            case R.id.create_timer_addpicture_layout:
                Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                break;
                default: Toast.makeText(this, "登录failed", Toast.LENGTH_SHORT).show();
        }
    }
}
