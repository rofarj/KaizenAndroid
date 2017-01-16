package eu.ceitgroup.AndroidKaizen.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigInteger;

import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import eu.ceitgroup.AndroidKaizen.app.AppConfig;
import eu.ceitgroup.AndroidKaizen.app.AppController;
import eu.ceitgroup.AndroidKaizen.helper.SessionManager;
import eu.ceitgroup.AndroidKaizen.R;


import android.view.View.OnClickListener;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import eu.ceitgroup.AndroidKaizen.helper.SQLiteHandler;

import android.widget.AdapterView.OnItemSelectedListener;

public class DefineProblem extends AppCompatActivity implements OnItemSelectedListener {


    private Button btnDefineProblem1, btnDefineProblem2, btnDefineProblem3, btnDefineProblem4, btnDefineProblem5, btnDefineProblem6;
    private ImageButton btnDefineProblemLogout, btnDefineProblemHome, btnDefineProblemNext, btnDefineProblemAck;
    private EditText  edtDefineProblem2, edtDefineProblem3, edtDefineProblem4, edtDefineProblem5;
    private ImageView imgvDefineProblem;
    private Spinner spinnerDefineProblem1;
    public String proposalResponsiblePerson, proposalCategory, proposalTitle, proposalProblemDescription, proposalProblemLocation, proposalProblemSource, proposalSolutionDescription, proposalSolutionBenefits, proposalSolutionCosts, proposalType, proposalProblemAttachmentName, proposalAuthor;
    private static final String TAG = DefineProblem.class.getSimpleName();
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    private Button  btnDefineProblemFileAttachment, btnDefineProblemFotoAttachment;
    private Uri proposalUri;

    List<String> categories;
    ArrayAdapter<String> dataAdapter;


    /**********  File Path *************/
    String fileName1;


    String uploadFilePath2 = "/mnt/sdcard/";
    String uploadFileName2 = "subor.txt";
    String proposalProblemAttachmentPath = uploadFilePath2 + "" + uploadFileName2;



    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_define_problem);

        final Context context = getApplicationContext();
        final int toastDuration = Toast.LENGTH_SHORT;

        // DB for database operations
        db = new SQLiteHandler(getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();

        /** Author of proposal is defined by its email which is unique **/
        proposalAuthor = user.get("email");
        proposalCategory = "";
        /***** Session manager ******/


        session = new SessionManager(getApplicationContext());

        /** Get extra from DefineIdea Activity through Intent **/
        Intent intent = getIntent();

        proposalResponsiblePerson = intent.getStringExtra("PROPOSAL_RESPONSIBLE_PERSON");
        proposalCategory = intent.getStringExtra("PROPOSAL_CATEGORY");
        proposalTitle = intent.getStringExtra("PROPOSAL_TITLE");
        proposalProblemDescription = intent.getStringExtra("PROPOSAL_PROBLEM_DESCRIPTION");
        proposalProblemLocation = intent.getStringExtra("PROPOSAL_PROBLEM_LOCATION");
        proposalProblemSource = intent.getStringExtra("PROPOSAL_PROBLEM_SOURCE");
        proposalProblemAttachmentPath = intent.getStringExtra("PROPOSAL_PROBLEM_ATTACHMENT_PATH");
        proposalProblemAttachmentName = intent.getStringExtra("PROPOSAL_PROBLEM_ATTACHMENT_NAME");

        proposalType = intent.getStringExtra("PROPOSAL_TYPE");
        proposalSolutionDescription = intent.getStringExtra("PROPOSAL_SOLUTION_DESCRIPTION");
        proposalSolutionBenefits = intent.getStringExtra("PROPOSAL_SOLUTION_BENEFITS");
        proposalSolutionCosts = intent.getStringExtra("PROPOSAL_SOLUTION_COSTS");

        /** DEFINE PROBLEM LOGOUT **/
        btnDefineProblemLogout = (ImageButton)findViewById(R.id.button_define_problem_logout);
        //Logout from session and go to the login screen
        btnDefineProblemLogout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                session.setLogin(false);
                db.deleteUsers();

                // Launching the login activity
                Intent intent = new Intent(DefineProblem.this, LoginActivity.class);
                startActivity(intent);
                finish();


            }
        });
        /**************************************************************************************************************/


        /** DEFINE PROBLEM HOME **/
        btnDefineProblemHome = (ImageButton)findViewById(R.id.button_define_problem_home);
        btnDefineProblemHome.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DefineProblem.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        /************************************************************************************************************/


        /** DEFINE PROBLEM GO NEXT**/
        btnDefineProblemNext = (ImageButton)findViewById(R.id.button_define_problem_next);
        btnDefineProblemNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDefineIdea();
            }
        });
        /***************************************************************************************************************/

        proposalResponsiblePerson = "whoCares?";

        /** PROPOSAL CATEGORY **/
        btnDefineProblem1 = (Button)findViewById(R.id.button_define_problem_1);
        spinnerDefineProblem1 = (Spinner) findViewById(R.id.spinner_define_problem_1);
        spinnerDefineProblem1.setVisibility(View.GONE);
        categoryRequest();
        btnDefineProblem1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if(spinnerDefineProblem1.getVisibility() == View.VISIBLE){
                    spinnerDefineProblem1.setVisibility(View.GONE);
                } else {
                    spinnerDefineProblem1.setVisibility(View.VISIBLE);
                    spinnerDefineProblem1.setFocusable(true);
                    spinnerDefineProblem1.setFocusableInTouchMode(true);
                    spinnerDefineProblem1.requestFocus();
                    if (spinnerDefineProblem1!=null && proposalCategory!="") {
                        spinnerDefineProblem1.setSelection(dataAdapter.getPosition(proposalCategory));
                    }
                    Toast.makeText(context,getResources().getString(R.string.toast_button_1_1) ,toastDuration).show();
                }
            }
        });
        spinnerDefineProblem1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                    proposalCategory = spinnerDefineProblem1.getSelectedItem().toString();
                    Log.i(TAG, "spinnerDefineProblem1 : " + proposalCategory);
                }
            }
        });

        /*******************************************************************************************************************/

        /*************************************** PROPOSAL TITLE ************************************************************/
        btnDefineProblem2 = (Button)findViewById(R.id.button_define_problem_2);
        edtDefineProblem2 = (EditText) findViewById(R.id.edittext_define_problem_2); // Define Name of the problem
        edtDefineProblem2.setVisibility(View.GONE);

        btnDefineProblem2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edtDefineProblem2.getVisibility() == View.VISIBLE){
                    edtDefineProblem2.setVisibility(View.GONE);
                } else {
                    edtDefineProblem2.setVisibility(View.VISIBLE);
                    edtDefineProblem2.requestFocus();
                    if (proposalTitle !="" ) {
                        edtDefineProblem2.setText(proposalTitle);
                    }
                    Toast.makeText(context,getResources().getString(R.string.toast_button_1_2) ,toastDuration).show();
                }
            }
        });
        edtDefineProblem2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                    proposalTitle = edtDefineProblem2.getText().toString();
                    Log.i(TAG, "btnDefineProblem2 : " + proposalTitle);
                }
            }
        });

        /***************************************************************************************************************/

        /******************************* PROPOSAL DESCRIPTION PROBLEM **************************************************/
        btnDefineProblem3 = (Button)findViewById(R.id.button_define_problem_3);
        edtDefineProblem3 = (EditText) findViewById(R.id.edittext_define_problem_3); // Define Description of the problem
        edtDefineProblem3.setVisibility(View.GONE);

        btnDefineProblem3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edtDefineProblem3.getVisibility() == View.VISIBLE){
                    edtDefineProblem3.setVisibility(View.GONE);
                } else {
                    edtDefineProblem3.setVisibility(View.VISIBLE);
                    edtDefineProblem3.requestFocus();
                    if (proposalProblemDescription !="" ) {
                       edtDefineProblem3.setText(proposalProblemDescription);
                    }
                    Toast.makeText(context,getResources().getString(R.string.toast_button_1_3) ,toastDuration).show();
                }
            }
        });
        edtDefineProblem3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                    proposalProblemDescription = edtDefineProblem3.getText().toString();
                    Log.i(TAG, "btnDefineProblem3 : " + proposalProblemDescription);
                }
            }
        });

        /***********************************************************************************************************/

        /********************************** PROPOSAL PROBLEM LOCATION **********************************************/
        btnDefineProblem4 = (Button)findViewById(R.id.button_define_problem_4);
        edtDefineProblem4 = (EditText) findViewById(R.id.edittext_define_problem_4); // Define Place of the problem
        edtDefineProblem4.setVisibility(View.GONE);
        btnDefineProblem4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edtDefineProblem4.getVisibility() == View.VISIBLE){
                    edtDefineProblem4.setVisibility(View.GONE);
                } else {
                    edtDefineProblem4.setVisibility(View.VISIBLE);
                    edtDefineProblem4.requestFocus();
                    if (proposalProblemLocation !="" ) {
                        edtDefineProblem4.setText(proposalProblemLocation);
                    }
                    Toast.makeText(context,getResources().getString(R.string.toast_button_1_4) ,toastDuration).show();
                }
            }
        });

        edtDefineProblem4.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                    proposalProblemLocation = edtDefineProblem4.getText().toString();
                    Log.i(TAG, "btnDefineProblem4 : " + proposalProblemLocation);
                }
            }
        });
        /**************************************************************************************************************/


        /********************************** PROPOSAL SOURCE OF THE PROBLEM ********************************************/

        btnDefineProblem5 = (Button)findViewById(R.id.button_define_problem_5);
        edtDefineProblem5 = (EditText) findViewById(R.id.edittext_define_problem_5); // Define Reason of the problem
        edtDefineProblem5.setVisibility(View.GONE);

        btnDefineProblem5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edtDefineProblem5.getVisibility() == View.VISIBLE){
                    edtDefineProblem5.setVisibility(View.GONE);
                } else {
                    edtDefineProblem5.setVisibility(View.VISIBLE);
                    edtDefineProblem5.requestFocus();
                    if (proposalProblemSource !="" ) {
                        edtDefineProblem5.setText(proposalProblemSource);
                    }
                    Toast.makeText(context,getResources().getString(R.string.toast_button_1_5) ,toastDuration).show();
                }
            }
        });
        edtDefineProblem5.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                    proposalProblemSource = edtDefineProblem5.getText().toString();
                    Log.i(TAG, "btnDefineProblem5 : " + proposalProblemSource);
                }
            }
        });

        /****************************************************************************************************************/


        /**************************************** PROPOSAL PROBLEM ATTACHMENT *******************************************/

        btnDefineProblem6 = (Button)findViewById(R.id.button_define_problem_6);
        btnDefineProblemFileAttachment = (Button) findViewById(R.id.button_define_problem_file_attachment);
        btnDefineProblemFotoAttachment = (Button) findViewById(R.id.button_define_problem_foto_attachment);
        imgvDefineProblem = (ImageView) findViewById(R.id.imageview_define_problem);
        btnDefineProblemFileAttachment.setVisibility(View.GONE);
        btnDefineProblemFotoAttachment.setVisibility(View.GONE);
        imgvDefineProblem.setVisibility(View.GONE);

        btnDefineProblem6.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"Priloha> " + proposalProblemAttachmentName);
                if(btnDefineProblemFileAttachment.getVisibility() == View.VISIBLE){
                    btnDefineProblemFileAttachment.setVisibility(View.GONE);
                    btnDefineProblemFotoAttachment.setVisibility(View.GONE);
                    imgvDefineProblem.setVisibility(View.GONE);

                } else {
                    btnDefineProblemFileAttachment.setVisibility(View.VISIBLE);
                    btnDefineProblemFotoAttachment.setVisibility(View.VISIBLE);

                    if (uploadFileName2 != "subor.txt") {

                        imgvDefineProblem.setVisibility(View.VISIBLE);
                    }
                    Toast.makeText(context,getResources().getString(R.string.toast_button_1_6) ,toastDuration).show();
                }
            }
        });

        btnDefineProblemFileAttachment.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

                startActivityForResult(chooserIntent, 7);
            }
        });

        btnDefineProblemFotoAttachment.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    proposalProblemAttachmentName = generateFileName();
                    File file = new File(Environment.getExternalStorageDirectory(), proposalProblemAttachmentName);
                    Uri uri = Uri.fromFile(file);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    //File finalFile = new File(getRealPathFromURI(uri));
                    File finalFile = new File(uri.getPath());
                    proposalProblemAttachmentPath = finalFile.getAbsolutePath();
                    //messageText2.setText(proposalProblemAttachmentPath);
                    uploadFileName2 = finalFile.getName();
                    uploadFilePath2 = finalFile.getParent();
                    if (uploadFileName2 != "subor.txt") {
                        imgvDefineProblem.setVisibility(View.VISIBLE);
                    }
                    startActivityForResult(intent,6);
                } catch (Exception e) {
                    Log.e("EXCEPTION :((", e.toString());
                }
            }
        });

        /****************************************************************************?

        /*********************** ACK AND GO TO DEFINE IDEA **************************/
        btnDefineProblemAck = (ImageButton)findViewById(R.id.button_define_problem_ack);
        btnDefineProblemAck.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDefineIdea();
            }
        });

        /****************************************************************************/

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 7 && resultCode == RESULT_OK) {
            Log.d(TAG, "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            // Get the url from data
            Uri selectedImageUri = data.getData();  ///storage/emulated/0/DCIM/100ANDRO/DSC_1260.JPG
            proposalUri = selectedImageUri;
            if (null != selectedImageUri) {
                // Get the path from the Uri
                String path = getPathFromURI(selectedImageUri);
                Log.i(TAG, "Image Path : " + path);
                proposalProblemAttachmentPath = path;
                // Set the imgvDefineIdea in ImageView
                imgvDefineProblem.setVisibility(View.VISIBLE);
                imgvDefineProblem.setImageURI(selectedImageUri);

            }
            proposalProblemAttachmentName = getFileName(selectedImageUri);

        }


        if (requestCode == 6 && resultCode == RESULT_OK) {
            Matrix matrix = new Matrix();
            // Check if the result includes a thumbnail Bitmap
            if (data != null) {
                Log.d(TAG, "DATA NOT NULL");
                if (data.hasExtra("data"))

                {
                    Bitmap thumbnail = data.getParcelableExtra("data");
                    //imgvDefineIdea.setImageBitmap(thumbnail);
                    Log.d(TAG, "HAS EXTRA NOT NULL");


                    OutputStream outStream = null;
                    File file = new File(Environment.getExternalStorageDirectory(), "er.JPEG");
                    try {
                        outStream = new FileOutputStream(file);
                        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                        outStream.flush();
                        outStream.close();
                    } catch (Exception e) {
                    }

                    try {

                        MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                } else {
                    //imgvDefineIdea.setImageURI(outputFileUri);
                    Log.d(TAG, "HAS EXTA NULL");
                }
            }
            File imgFile = new  File(proposalProblemAttachmentPath);
            Log.d(TAG, "ABSOLUTE PATH  " + proposalProblemAttachmentPath);
            //foto = proposalProblemAttachmentPath;
            if(imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                matrix.postRotate(90);
                Bitmap resized = Bitmap.createScaledBitmap(myBitmap, 300, 300, true);
                Bitmap rotatedBitmap = Bitmap.createBitmap(resized, 0, 0, resized.getWidth(), resized.getHeight(), matrix, true);
                imgvDefineProblem.setImageBitmap(rotatedBitmap);
            }
        }

    }

    /* Get the real path from the URI */
    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    public String generateFileName()
    {
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        String output = sb.toString();
        String fileName = output;
        try {
            fileName = md5(fileName);
            fileName = fileName + ".jpg";
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return fileName;
    }
    public static String md5(String input) throws NoSuchAlgorithmException {
        String result = input;
        if(input != null) {
            MessageDigest md = MessageDigest.getInstance("MD5"); //or "SHA-1"
            md.update(input.getBytes());
            BigInteger hash = new BigInteger(1, md.digest());
            result = hash.toString(16);
            while(result.length() < 32) { //40 for SHA-1
                result = "0" + result;
            }
        }
        return result;
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        //outState.putCharSequence("txtBox", messageText.getText());
        //outState.putCharSequence("txtBox2", messageText2.getText());
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //messageText.setText(savedInstanceState.getCharSequence("txtBox"));
        //messageText2.setText(savedInstanceState.getCharSequence("txtBox2"));
       /* proposalProblemAttachmentPath = savedInstanceState.getCharSequence("txtBox").toString();
        proposalProblemAttachmentPath = savedInstanceState.getCharSequence("txtBox2").toString();*/
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinnerDefineProblem1 item
        String item = parent.getItemAtPosition(position).toString();
        //String item2 = parent.getSelectedItem().toString();

        // Showing selected spinnerDefineProblem1 item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();


    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    private void goToDefineIdea() {

        spinnerDefineProblem1.clearFocus();
        edtDefineProblem2.clearFocus();
        edtDefineProblem3.clearFocus();
        edtDefineProblem4.clearFocus();
        edtDefineProblem5.clearFocus();
        Intent intent = new Intent(DefineProblem.this, DefineIdea.class);
        intent.putExtra("PROPOSAL_RESPONSIBLE_PERSON", proposalResponsiblePerson);
        intent.putExtra("PROPOSAL_CATEGORY", proposalCategory);
        intent.putExtra("PROPOSAL_TITLE", proposalTitle);
        intent.putExtra("PROPOSAL_PROBLEM_DESCRIPTION", proposalProblemDescription);
        intent.putExtra("PROPOSAL_PROBLEM_LOCATION", proposalProblemLocation);
        intent.putExtra("PROPOSAL_PROBLEM_SOURCE", proposalProblemSource);
        intent.putExtra("PROPOSAL_PROBLEM_ATTACHMENT_PATH", proposalProblemAttachmentPath);
        intent.putExtra("PROPOSAL_PROBLEM_ATTACHMENT_NAME", proposalProblemAttachmentName);

        intent.putExtra("PROPOSAL_TYPE", proposalType);
        intent.putExtra("PROPOSAL_SOLUTION_DESCRIPTION", proposalSolutionDescription);
        intent.putExtra("PROPOSAL_SOLUTION_BENEFITS",proposalSolutionBenefits);
        intent.putExtra("PROPOSAL_SOLUTION_COSTS",proposalSolutionCosts);
        Log.d(TAG, "RIESITEL " + proposalResponsiblePerson);
        Log.d(TAG, "KATEGORIA " + proposalCategory);
        Log.d(TAG, "NAZOV " + proposalTitle);
        Log.d(TAG, "POPIS " + proposalProblemDescription);
        Log.d(TAG, "MIESTO " + proposalProblemLocation);
        Log.d(TAG, "PRICINA " + proposalProblemSource);

        Log.d(TAG, "FILENAME2 " + proposalProblemAttachmentName);
        startActivity(intent);
    }

    private void categoryRequest() {
        final String short_name;
        String actual_state;
        String improved_state;
        String advantages;
        String costs;
        String phone;
        String age;
        categories = new ArrayList<String>();


        // Spinner click listener
        spinnerDefineProblem1.setOnItemSelectedListener(this);

        // Tag used to cancel the request
        String tag_string_req = "req_register";

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.SPINNERS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // MainActivity successfully stored in MySQL
                        // Now store the user in sqlite


                        JSONObject idea = jObj.getJSONObject("categories");

                        String length2 = idea.getString("length");
                        int length = Integer.parseInt(length2);
                        //JSONObject[] ideas = new JSONObject[length];
                        Log.d("AAAAAAAAAAAA","AAAAA                  " +length2);

                        for(int j=0;j<length;j++) {
                            //JSONObject first_idea = idea.getJSONObject("'" + j + "'");
                            String id = idea.getString("'" + j + "'");
                            //int _id = Integer.parseInt(id);
                            //String short_name = first_idea.getString("short_name");


                            Log.d("AAAAAAAAAAAA","AAAAA                  " +id);


                            categories.add(id.substring(2,id.length()-2));
                        }

                        // Creating adapter for spinnerDefineProblem1
                        dataAdapter = new ArrayAdapter<String>(DefineProblem.this, android.R.layout.simple_spinner_item, categories);

                        // Drop down layout style - list view with radio button
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        // attaching data adapter to spinnerDefineProblem1
                        spinnerDefineProblem1.setAdapter(dataAdapter);

                    } else {

                        // Error occurred in registration. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        //Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                        Log.d(TAG, "REGISTRATION ERROR: " + errorMsg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                //Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                //hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                //params.put("user", user);
                //params.put("actual_state", actual_state);
                //params.put("improved_state", improved_state);
                //params.put("advantages",advantages);
                //params.put("costs",costs);
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
}
