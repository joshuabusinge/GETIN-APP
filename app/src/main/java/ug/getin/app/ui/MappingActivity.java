package ug.getin.app.ui;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
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

public class MappingActivity extends AppCompatActivity implements OnClickListener, LocationListener, Callback<Girl> {
    Context context = this;
    LocationManager locationManager;
    LocationListener locationListener;

    TextView txtLat;
    String lat;
    String provider;
    double latitude, longitude;
    boolean gps_enabled, network_enabled;
    Calendar calendar = Calendar.getInstance();


    EditText nameField, number_of_children_field, villageField, parishField, subcountyField;

    EditText dateField, emergency_contact_field, contact_number_field;

    DatePickerDialog.OnDateSetListener date;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormatter;

    Spinner maritalStatusSpinner, has_received_anc_field;
    Spinner educationLevelSpinner, languageSpinner, contactTypeSpinner;
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

    String[] language_options = {
            "English",
            "Luganda",

    };

    String[] contact_types = {
            "Parent",
            "Guardian",
            "Spouse",
            "Sibling",

    };

    String[] has_received_anc_options = {
            "Yes",
            "No",
    };




    String name, dob, marital_status, education_level, number_of_children, village, parish, subcounty;
    String prefered_language, emergency_contact, contact_type, contact_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapping);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);



        restAdapter = new ApiClient().getRestAdapter();
        service = restAdapter.create(ApiService.class);

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");

        nameField = (EditText) findViewById(R.id.nameField);
        dateField = (EditText) findViewById(R.id.dob);
        maritalStatusSpinner = (Spinner) findViewById(R.id.marital_status);
        educationLevelSpinner = (Spinner) findViewById(R.id.education_level);
        has_received_anc_field = (Spinner) findViewById(R.id.has_gone_for_anc);
        emergency_contact_field = (EditText) findViewById(R.id.emergency_contact);
        contactTypeSpinner = (Spinner) findViewById(R.id.contact_type);
        contact_number_field = (EditText) findViewById(R.id.contact_number);
        languageSpinner = (Spinner) findViewById(R.id.prefered_language);
        number_of_children_field = (EditText) findViewById(R.id.number_of_children);
        villageField = (EditText) findViewById(R.id.village);
        parishField = (EditText) findViewById(R.id.parish);
        subcountyField = (EditText) findViewById(R.id.subcounty);


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
        languageSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, language_options));
        contactTypeSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, contact_types));
        has_received_anc_field.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, has_received_anc_options));

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
            emergency_contact = emergency_contact_field.getText().toString();
            contact_type = (( TextView ) contactTypeSpinner.getSelectedView()).getText().toString();
            contact_number = contact_number_field.getText().toString();
            number_of_children = number_of_children_field.getText().toString();
            prefered_language = ((TextView) languageSpinner.getSelectedView()).getText().toString();
            village = villageField.getText().toString();
            parish = parishField.getText().toString();
            subcounty = subcountyField.getText().toString();

            Girl girl = new Girl();
            girl.setName(name);
            girl.setDate_of_birth(dob);
            girl.setMarital_status(marital_status);
            girl.setEducation_level(education_level);
            girl.setEmergency_contact(emergency_contact);
            girl.setContact_type(contact_type);
            girl.setContact_number(contact_number);
            girl.setNumber_of_children(number_of_children);
            girl.setPrefered_language(prefered_language);
            girl.setVillage(village);
            girl.setParish(parish);
            girl.setSubcounty(subcounty);

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

    @Override
    public void onLocationChanged(Location location) {
//        Log.e("LOCATION", "Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Log.d("Latitude","status");
    }

    @Override
    public void onProviderEnabled(String s) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onProviderDisabled(String s) {
        Log.d("Latitude","disable");
    }
}
