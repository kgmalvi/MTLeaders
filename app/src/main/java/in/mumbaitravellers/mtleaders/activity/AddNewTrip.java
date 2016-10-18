package in.mumbaitravellers.mtleaders.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import in.mumbaitravellers.mtleaders.R;

public class AddNewTrip extends AppCompatActivity {

    private String[] arrayDivisionHead;
    private int division;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_trip);

       // final Spinner divisionSpinner = (Spinner) findViewById(R.id.spinner_Division);
        final EditText edDivisionHead = (EditText) findViewById(R.id.edTxt_DivisionHead);

        this.arrayDivisionHead = new String[]{
                "Long Tours", "Biking", "Himalayan Expeditions", "Adventure", "Wildlife Tours",
                "Photography", "Offbeat Tours", "Camping", "Trekking", "Cycling", "Backpacking",
                "Corporate"
        };

        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, arrayDivisionHead);
        divisionSpinner.setAdapter(adapter);

        divisionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                division = position;
                //division = (divisionSpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        /*
        "Saurabh Thakekar", "Jogi Prajapati", "Nilesh Haldankar", "Vaibhav Khaire",
         "Kedar Kalamkar", "Karishma Joshi", "Sameer Sawant", "Harsh Joshi"
         */
    }
}
