package ug.getin.app.ui;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import ug.getin.app.MainActivity;
import ug.getin.app.R;
import ug.getin.app.models.Girl;
import ug.getin.app.service.ApiClient;
import ug.getin.app.service.ApiService;

public class MappingActivity extends AppCompatActivity implements Callback<Girl> {
    Context context = this;

    EditText nameField;
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
        submitBtn = (Button) findViewById(R.id.submitBtn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Girl girl = new Girl();
                girl.setName(nameField.getText().toString());
                girl.setDate_of_birth("2000-01-01");
                girl.setMarital_status("Single");
                girl.setEducation_level("Primary");

                progressDialog.show();
                service.registerGirl(girl, MappingActivity.this);
            }
        });
    }

    @Override
    public void success(Girl girl, Response response) {
        progressDialog.dismiss();
        startActivity(new Intent(context, MainActivity.class));
    }

    @Override
    public void failure(RetrofitError error) {

    }
}
