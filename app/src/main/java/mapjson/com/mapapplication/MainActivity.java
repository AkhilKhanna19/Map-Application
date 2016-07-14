package mapjson.com.mapapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class MainActivity extends AppCompatActivity {
    private CheckBox exclusive, normal, people;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        exclusive = (CheckBox) findViewById(R.id.exclusive_event);
        people = (CheckBox) findViewById(R.id.people_id);
        normal = (CheckBox) findViewById(R.id.normal_id);




    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.exclusive_event:
                if (checked) {
                    Intent intent = getIntent();
                    intent.putExtra("key","exclusive");
                    setResult(Activity.RESULT_OK,intent);
                    finish();


                }
                break;
            case R.id.all_events_id:
                if (checked) {
                    Intent intent = getIntent();
                    intent.putExtra("key", "def");
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
                break;

            case R.id.normal_id:
                if (checked) {
                    Intent intent = getIntent();
                    intent.putExtra("key", "normal");
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
                break;
            case R.id.people_id:
                if (checked) {
                    Intent intent = getIntent();
                    intent.putExtra("key", "people");
                    setResult(Activity.RESULT_OK, intent);
                    finish();

                }
                break;

        }
    }
}