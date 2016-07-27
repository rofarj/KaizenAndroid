package info.androidhive.loginandregistration.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Calendar;

import info.androidhive.loginandregistration.R;
import info.androidhive.loginandregistration.app.AppConfig;
import info.androidhive.loginandregistration.app.AppController;
import info.androidhive.loginandregistration.helper.SQLiteHandler;
import info.androidhive.loginandregistration.helper.SessionManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Aktivita pre pridanie novej ulohy.
 */
public class NewIdea extends Activity {

    private Button btnSend;
    private EditText nameEditText, actualStateEditText1,improvedStateEditText3, advantagesEditText4, costsEditText2;
    private String name, actual_state, improved_state, advantages, costs;
    private static final String TAG = NewIdea.class.getSimpleName();
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    private Button btnTakePhotoOld;
    private Button btnTakePhotoNew;
    private ImageView iv;
    private ImageView iv2;

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bp = (Bitmap) data.getExtras().get("data");
        if(requestCode == 0) {
            iv.setImageBitmap(bp);
        }
        if(requestCode == 1) {
            iv2.setImageBitmap(bp);
        }
    }

    /**
     * Po kliknuti na tlacidlo sa do premennych ulozia string-y z layoutu a vlozia sa do databazy.
     * Nakoniec sa v intente vytvori nova aktivita (MainActivity)
     * @param savedInstanceState
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate your view
        setContentView(R.layout.activity_new_idea);

        //final MyDatabaseHelper db = new MyDatabaseHelper(this);

        //nameEditText = (EditText) findViewById(R.id.editTextTest);
        /*actualStateEditText1 = (EditText) findViewById(R.id.editText1);
        improvedStateEditText3 = (EditText) findViewById(R.id.editText3);
        advantagesEditText4 = (EditText) findViewById(R.id.editText4);
        costsEditText2 = (EditText) findViewById(R.id.editText2);
        btnSend = (Button) findViewById(R.id.button);*/
/*
        btnTakePhotoOld = (Button) findViewById(R.id.button2);
        btnTakePhotoNew = (Button) findViewById(R.id.button3);
        iv = (ImageView) findViewById(R.id.imageView);
        iv2 = (ImageView) findViewById(R.id.imageView2);*/
        //final Calendar c = Calendar.getInstance();
        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        btnTakePhotoOld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });

        btnTakePhotoNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1);
            }
        });


        /*btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                name = nameEditText.getText().toString();
                actual_state = actualStateEditText1.getText().toString();
                improved_state = improvedStateEditText3.getText().toString();
                advantages = advantagesEditText4.getText().toString();
                costs = costsEditText2.getText().toString();


                if (!name.isEmpty() && !actual_state.isEmpty() && !improved_state.isEmpty() && !advantages.isEmpty() && !costs.isEmpty()) {
                    registerIdea(name, actual_state, improved_state, advantages, costs);
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter your details!", Toast.LENGTH_LONG).show();
                }



                //db.createRecords(nazov, kategoria,datum, priorita,popis);


                Intent intent2 = new Intent(v.getContext(), User.class);
                startActivity(intent2);
            }
        });*/
    }

    /**
     * Function to store idea in MySQL database will post params(tag, name,
     * email, password) to register url
     * */
    private void registerIdea(final String name, final String actual_state, final String improved_state, final String advantages, final String costs) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Sending...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_ADD_IDEA, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                        //String uid = jObj.getString("uid");

                        JSONObject idea = jObj.getJSONObject("idea");
                        String name = idea.getString("short_name");
                        String act_state = idea.getString("actual_state");
                        String impState = idea.getString("improved_state");
                        String adv = idea.getString("advantages");
                        String cos = idea.getString("costs");

                        // Inserting row in users table
                        //db.addIdea(name, act_state, impState, adv, cos);

                        Toast.makeText(getApplicationContext(), "Idea successfully stored.", Toast.LENGTH_LONG).show();

                        // Launch login activity
                        Intent intent = new Intent(NewIdea.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                        Log.d(TAG, "REGISTRATION ERROR: " +errorMsg );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("short_name", name);
                params.put("actual_state", actual_state);
                params.put("improved_state", improved_state);
                params.put("advantages",advantages);
                params.put("costs",costs);
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}

