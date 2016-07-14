package mapjson.com.mapapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity {
    private TextView event, title, coordinates;
    private static String events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        event = (TextView) findViewById(R.id.event_id);
        title =(TextView) findViewById(R.id.title_id);
        coordinates = (TextView) findViewById(R.id.coord_loc_id);

        Bundle bundle = getIntent().getExtras();
        String event_type = bundle.getString("event");
        if(event_type.equals("ex"))
            events = "Exclusive";
        else if (event_type.equals("pe"))
            events = "People";
        else if (event_type.equals("ev"))
            events = "Everyone";

        event.setText("Event: " +events);
        title.setText(bundle.getString("title").toString());
        coordinates.setText(bundle.getString("coordinates").toString());

    }
}
