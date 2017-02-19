package development.android.androidfirebasetutorial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class TravelExpenseActivity extends AppCompatActivity {

    private EditText editTextDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_expense);

        editTextDate = (EditText) findViewById(R.id.editTextDate);

    }
}
