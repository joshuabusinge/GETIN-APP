package ug.getin.app.ui;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.app.DatePickerDialog.OnDateSetListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import ug.getin.app.MainActivity;
import ug.getin.app.R;
import ug.getin.app.models.Girl;
import ug.getin.app.service.ApiClient;
import ug.getin.app.service.ApiService;

public class MappingActivity extends AppCompatActivity implements OnClickListener, Callback<Girl> {
    Context context = this;
    Calendar calendar = Calendar.getInstance();


    EditText nameField;

    EditText dateField;

    DatePickerDialog.OnDateSetListener date;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormatter;

    Spinner maritalStatusSpinner;
    Spinner educationLevelSpinner;
    Button submitBtn;

    RestAdapter restAdapter;
    ApiService service;

    ProgressDialog progressDialog;

    String[] marital_status_choices = {
            "Single",
            "Married",
            "Divorced",
            "Widowed"
    };

    String[] education_levels = {
            "None",
            "Primary",
            "Secondary",
            "Tertiary",
    };

    String name, dob, marital_status, education_level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapping);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });




        restAdapter = new ApiClient().getRestAdapter();
        service = restAdapter.create(ApiService.class);

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");

        nameField = (EditText) findViewById(R.id.nameField);
        dateField = (EditText) findViewById(R.id.dob);
        maritalStatusSpinner = (Spinner) findViewById(R.id.marital_status);
        educationLevelSpinner = (Spinner) findViewById(R.id.education_level);
        submitBtn = (Button) findViewById(R.id.submitBtn);


        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

                dateField.setText(dateFormatter.format(calendar.getTime()));
            }

        };

        maritalStatusSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, marital_status_choices));
        educationLevelSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, education_levels));

        dateField.setOnClickListener(this);
        submitBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if(view == dateField) {
            Log.e("ER", "dateField cliecke");

            new DatePickerDialog(MappingActivity.this, date, calendar
                    .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show();

        }
        else if(view == submitBtn) {
            name = nameField.getText().toString();
            dob = dateField.getText().toString();
            marital_status = ((TextView) maritalStatusSpinner.getSelectedView()).getText().toString();
            education_level = ((TextView) educationLevelSpinner.getSelectedView()).getText().toString();

            Girl girl = new Girl();
            girl.setName(name);
            girl.setDate_of_birth(dob);
            girl.setMarital_status(marital_status);
            girl.setEducation_level(education_level);

            progressDialog.show();
            service.registerGirl(girl, MappingActivity.this);
        }
    }


    @Override
    public void success(Girl girl, Response response) {
        progressDialog.dismiss();
        startActivity(new Intent(context, MainActivity.class));
    }

    @Override
    public void failure(RetrofitError error) {
        progressDialog.dismiss();

    }

}
