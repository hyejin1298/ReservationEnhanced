package sg.edu.rp.c346.reservation;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {


      EditText name;
      EditText mobile;
      EditText group;
      CheckBox cbSmoke;
      Button btnReset;
      Button btnReserve;
      EditText etDate;
      EditText etTime;

    @Override
    protected void onPause() {
        super.onPause();

        String Rname = name.getText().toString();
        String telephone = mobile.getText().toString();
        String size = group.getText().toString();
        String day = etDate.getText().toString();
        String time = etTime.getText().toString();
        boolean chbox = cbSmoke.isChecked();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEdit = prefs.edit();

        // Step 1c:
        prefEdit.putString("Name", Rname);
        prefEdit.putString("telephone", telephone);
        prefEdit.putString("size", size);
        prefEdit.putString("day", day);
        prefEdit.putString("time", time);
        prefEdit.putBoolean("chbox", chbox);


        prefEdit.commit();


    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        String Rname = prefs.getString("Name","");
        String telephone = prefs.getString("telephone","");
        String size = prefs.getString("size","");
        String day = prefs.getString("day","");
        String time = prefs.getString("time","");
        boolean chbox = prefs.getBoolean("chbox",false);

        name.setText(Rname);
        mobile.setText(telephone);
        group.setText(size);
        etDate.setText(day);
        etTime.setText(time);
        cbSmoke.setChecked(chbox);


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



            name = findViewById(R.id.editTextName);
        mobile = findViewById(R.id.editTextMobile);
        group = findViewById(R.id.editTextGroup);
        cbSmoke = findViewById(R.id.checkBoxSmoke);
        btnReset = findViewById(R.id.buttonReset);
        btnReserve = findViewById(R.id.buttonReserve);
        etDate = findViewById(R.id.editTextDay);
        etTime = findViewById(R.id.editTextTime);


        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog.OnTimeSetListener myTimeListner = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        etTime.setText("Time: " + hourOfDay + ":" + minute);
                    }
                };


                //create the time picker dialog
                Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                TimePickerDialog myTimeDialog = new TimePickerDialog(MainActivity.this , myTimeListner , hour , minute , true);

                myTimeDialog.show();
            }
        });

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        etDate.setText("Date: " + dayOfMonth + "/" + (monthOfYear+1) + "/" + year);
                    }
                };

                //create the date picker dialog

                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog myDateDialog = new DatePickerDialog(MainActivity.this,myDateListener,year,month,day);

                myDateDialog.show();
            }
        });


        btnReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if (name.getText().toString().trim().length() != 0 && mobile.getText().toString().trim().length() != 0 && group.getText().toString().trim().length() != 0 ) {
                        String nameRes = name.getText().toString();
                        String mobileRes = mobile.getText().toString();
                        String groupRes = group.getText().toString();
                        if(cbSmoke.isChecked()){

                            String cbSmoke = "Yes";
                        }
                        else{
                            String cbSmoke = "No";
                        }


                        AlertDialog.Builder myBuilder = new AlertDialog.Builder(MainActivity.this);
                        myBuilder.setTitle("Confirm Your Order!");
                        myBuilder.setMessage("New Reservation\n" + "Name: " + nameRes + "\n" + "Smoking: " + cbSmoke.getText() + "\n" + "Size: " + groupRes + "\n" + "Date: " + etDate.getText() + "\n" + "Time: " + etTime.getText());
                        myBuilder.setCancelable(false);
                        myBuilder.setPositiveButton("Confirm" , null);

                        myBuilder.setNeutralButton("Cancel" , null);

                        AlertDialog myDialog =  myBuilder.create();
                        myDialog.show();





                    }

                    else{
                        Toast.makeText(MainActivity.this, "Fill in all text boxes ", Toast.LENGTH_LONG).show();

                    }

                    btnReset.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            name.setText(null);
                            mobile.setText(null);
                            group.setText(null);
                            etTime.setText(null);
                            etDate.setText(null);
                            cbSmoke.setChecked(false);
                        }
                    });




            }
        });




    }
}
