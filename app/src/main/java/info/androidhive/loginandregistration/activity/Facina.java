package info.androidhive.loginandregistration.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import info.androidhive.loginandregistration.R;

import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import info.androidhive.loginandregistration.R;
import info.androidhive.loginandregistration.app.AppConfig;
import info.androidhive.loginandregistration.app.AppController;
import info.androidhive.loginandregistration.helper.SQLiteHandler;
import info.androidhive.loginandregistration.helper.SessionManager;

public class Facina extends AppCompatActivity {

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

    TextView messageText;
    TextView messageText2;
   // Button uploadButton;
    Button pathButton;
    Button pathButton2;
    int serverResponseCode = 0;
    ProgressDialog dialog = null;
    String fileName1;
    String fileName2;
    String creator;
    ImageView image;
    ImageView image2;


    String upLoadServerUri = null;

    /**********  File Path *************/
    String uploadFilePath = "/mnt/sdcard/";
    String uploadFileName = "subor.txt";
    String finalPath  = uploadFilePath + "" + uploadFileName;

    String uploadFilePath2 = "/mnt/sdcard/";
    String uploadFileName2 = "subor.txt";
    String finalPath2  = uploadFilePath2 + "" + uploadFileName2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        db = new SQLiteHandler(getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();

        creator = user.get("email");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facina);
        nameEditText = (EditText) findViewById(R.id.editText);
        actualStateEditText1 = (EditText) findViewById(R.id.editText1);
        improvedStateEditText3 = (EditText) findViewById(R.id.editText3);
        advantagesEditText4 = (EditText) findViewById(R.id.editText4);
        costsEditText2 = (EditText) findViewById(R.id.editText2);
        btnSend = (Button) findViewById(R.id.button);
        image = (ImageView) findViewById(R.id.imageView);
        image2 = (ImageView) findViewById(R.id.imageView2);



        // Session manager
        session = new SessionManager(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());


        //uploadButton = (Button)findViewById(R.id.uploadButton);
        pathButton = (Button)findViewById(R.id.pathButton);
        pathButton2 = (Button)findViewById(R.id.pathButton2);

        messageText  = (TextView)findViewById(R.id.messageText);
        messageText2  = (TextView)findViewById(R.id.messageText2);

        messageText.setText("No photo of actual state taken");
        messageText2.setText("No photo of improved state taken");

        /************* Php script path ****************/
        upLoadServerUri = "http://zims.ceitgroup.eu/kaizen/android_login_api/facina.php";


        pathButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    fileName1 = generateFileName();
                    File file = new File(Environment.getExternalStorageDirectory(), fileName1);
                    Uri uri = Uri.fromFile(file);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    //File finalFile = new File(getRealPathFromURI(uri));
                    File finalFile = new File(uri.getPath());
                    finalPath = finalFile.getAbsolutePath();
                    messageText.setText(finalPath);
                    uploadFileName = finalFile.getName();
                    uploadFilePath = finalFile.getParent();
                    startActivityForResult(intent, 7);
                } catch (Exception e) {
                    Log.e("EXCEPTION :((", e.toString());
                }

            }
        });

        pathButton2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    fileName2 = generateFileName();
                    File file = new File(Environment.getExternalStorageDirectory(), fileName2);
                    Uri uri = Uri.fromFile(file);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    //File finalFile = new File(getRealPathFromURI(uri));
                    File finalFile = new File(uri.getPath());
                    finalPath2 = finalFile.getAbsolutePath();
                    messageText2.setText(finalPath2);
                    uploadFileName2 = finalFile.getName();
                    uploadFilePath2 = finalFile.getParent();
                    startActivityForResult(intent,6);
                } catch (Exception e) {
                    Log.e("EXCEPTION :((", e.toString());
                }

            }
        });


        /*uploadButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog = ProgressDialog.show(Facina.this, "", "Uploading old file...", true);

                new Thread(new Runnable() {
                    public void run() {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                messageText.setText("uploading started.....");
                            }
                        });

                        uploadFile(finalPath);
                        runOnUiThread(new Runnable() {
                            public void run() {
                                dialog = ProgressDialog.show(Facina.this, "", "Uploading new file...", true);
                                messageText2.setText("uploading started.....");
                            }
                        });
                        uploadFile2(finalPath2);

                    }
                }).start();
            }
        });*/

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* Edit texty */
                name = nameEditText.getText().toString();
                actual_state = actualStateEditText1.getText().toString();
                improved_state = improvedStateEditText3.getText().toString();
                advantages = advantagesEditText4.getText().toString();
                costs = costsEditText2.getText().toString();

                dialog = ProgressDialog.show(Facina.this, "", "Uploading old file...", true);

                new Thread(new Runnable() {
                    public void run() {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                messageText.setText("uploading started.....");
                            }
                        });

                        uploadFile(finalPath);
                        runOnUiThread(new Runnable() {
                            public void run() {
                                dialog = ProgressDialog.show(Facina.this, "", "Uploading new file...", true);
                                messageText2.setText("uploading started.....");
                            }
                        });
                        uploadFile2(finalPath2);
                        if (!name.isEmpty() && !actual_state.isEmpty() && !improved_state.isEmpty() && !advantages.isEmpty() && !costs.isEmpty()) {
                            registerIdea(name, actual_state, improved_state, advantages, costs, creator, fileName1,fileName2);
                        } else {
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "Please enter your details!", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                }).start();

                //Intent intent2 = new Intent(v.getContext(), MainActivity.class);
                //startActivity(intent2);

                /* ulozenie zaznamu do databazy */
                //db.createRecords(nazov, kategoria,datum, priorita,popis);



            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 7 && resultCode == RESULT_OK) {

            Matrix matrix = new Matrix();
            // Check if the result includes a thumbnail Bitmap
            if (data != null) {
                if (data.hasExtra("data"))

                {
                    Bitmap thumbnail = data.getParcelableExtra("data");
                    if(thumbnail == null){
                        Log.d(TAG, "NULL");
                    }
                    else {
                        Log.d(TAG, "NOT NULL");
                    }
                    //image.setImageBitmap(photo);

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
                    //image.setImageURI(outputFileUri);

                }
            }
            File imgFile2 = new  File(finalPath);
            Log.d(TAG, "ABSOLUTE PATH  " + finalPath);
            if(imgFile2.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile2.getAbsolutePath());
                matrix.postRotate(90);
                Bitmap resized = Bitmap.createScaledBitmap(myBitmap, 300, 300, true);
                Bitmap rotatedBitmap = Bitmap.createBitmap(resized, 0, 0, resized.getWidth(), resized.getHeight(), matrix, true);
                image2.setImageBitmap(rotatedBitmap);
            }

        }

        if (requestCode == 6 && resultCode == RESULT_OK) {
            Matrix matrix = new Matrix();
            // Check if the result includes a thumbnail Bitmap
            if (data != null) {
                Log.d(TAG, "DATA NOT NULL");
                if (data.hasExtra("data"))

                {
                    Bitmap thumbnail = data.getParcelableExtra("data");
                    //image.setImageBitmap(thumbnail);
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
                    //image.setImageURI(outputFileUri);
                    Log.d(TAG, "HAS EXTA NULL");
                }
            }
            File imgFile = new  File(finalPath2);
            Log.d(TAG, "ABSOLUTE PATH  " + finalPath2);
            if(imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                matrix.postRotate(90);
                Bitmap resized = Bitmap.createScaledBitmap(myBitmap, 300, 300, true);
                Bitmap rotatedBitmap = Bitmap.createBitmap(resized, 0, 0, resized.getWidth(), resized.getHeight(), matrix, true);
                image.setImageBitmap(rotatedBitmap);
            }
        }

    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    public int uploadFile(String sourceFileUri) {


        String fileName = sourceFileUri;

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1024 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);

        if (!sourceFile.isFile()) {

            dialog.dismiss();

            Log.e("uploadFile", "Source File not exist :"
                    +uploadFilePath + "" + uploadFileName);

            runOnUiThread(new Runnable() {
                public void run() {
                    messageText.setText("Source File not exist :"
                            +uploadFilePath + "" + uploadFileName);
                }
            });

            return 0;

        }
        else
        {
            try {

                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(upLoadServerUri);

                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file7", fileName);

                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file7\";filename=\""
                        + fileName + "\"" + lineEnd);

                dos.writeBytes(lineEnd);

                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.i("uploadFile", "HTTP Response is : "
                        + serverResponseMessage + ": " + serverResponseCode);

                if(serverResponseCode == 200){

                    runOnUiThread(new Runnable() {
                        public void run() {

                            String msg = "File Upload Completed.\n\n";

                            messageText.setText(msg);
                            Toast.makeText(Facina.this, "File Upload Complete.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {

                dialog.dismiss();
                ex.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
                        messageText.setText("MalformedURLException Exception : check script url.");
                        Toast.makeText(Facina.this, "MalformedURLException",
                                Toast.LENGTH_SHORT).show();
                    }
                });

                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {

                dialog.dismiss();
                e.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
                        messageText.setText("Got Exception : see logcat ");
                        Toast.makeText(Facina.this, "Got Exception : see logcat ",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                Log.e("server Exception", "Exception : "
                        + e.getMessage(), e);
            }
            dialog.dismiss();
            return serverResponseCode;

        } // End else block
    }
    public int uploadFile2(String sourceFileUri) {


        String fileName = sourceFileUri;

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1024 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);

        if (!sourceFile.isFile()) {

            dialog.dismiss();

            Log.e("uploadFile", "Source File not exist :"
                    +uploadFilePath + "" + uploadFileName);

            runOnUiThread(new Runnable() {
                public void run() {
                    messageText2.setText("Source File not exist :"
                            +uploadFilePath + "" + uploadFileName);
                }
            });

            return 0;

        }
        else
        {
            try {

                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(upLoadServerUri);

                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file6", fileName);

                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file6\";filename=\""
                        + fileName + "\"" + lineEnd);

                dos.writeBytes(lineEnd);

                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.i("uploadFile", "HTTP Response is : "
                        + serverResponseMessage + ": " + serverResponseCode);

                if(serverResponseCode == 200){

                    runOnUiThread(new Runnable() {
                        public void run() {

                            String msg = "File Upload Completed.\n\n";

                            messageText2.setText(msg);
                            Toast.makeText(Facina.this, "File Upload Complete.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {

                dialog.dismiss();
                ex.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
                        messageText.setText("MalformedURLException Exception : check script url.");
                        Toast.makeText(Facina.this, "MalformedURLException",
                                Toast.LENGTH_SHORT).show();
                    }
                });

                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {

                dialog.dismiss();
                e.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
                        messageText.setText("Got Exception : see logcat ");
                        Toast.makeText(Facina.this, "Got Exception : see logcat ",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                Log.e("server Exception", "Exception : "
                        + e.getMessage(), e);
            }
            dialog.dismiss();
            return serverResponseCode;

        } // End else block
    }


    private void registerIdea(final String name, final String actual_state, final String improved_state, final String advantages, final String costs, final String creator, final String fileName1, final String fileName2) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        //pDialog.setMessage("Sending...");
        //showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_DATA, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                //hideDialog();

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
                        String creator = idea.getString("creator");
                        String fileName1 = idea.getString("file_before");
                        String fileName2 = idea.getString("file_after");


                        // Inserting row in users table
                        db.addIdea(name, act_state, impState, adv, cos, creator,  fileName1, fileName2);

                        Toast.makeText(getApplicationContext(), "Idea successfully stored.", Toast.LENGTH_LONG).show();

                        // Launch login activity
                        Intent intent = new Intent(Facina.this, LoginActivity.class);
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
                //hideDialog();
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
                params.put("creator", creator);
                params.put("file_before",fileName1);
                params.put("file_after", fileName2);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
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
        outState.putCharSequence("txtBox", messageText.getText());
        outState.putCharSequence("txtBox2", messageText2.getText());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        messageText.setText(savedInstanceState.getCharSequence("txtBox"));
        messageText2.setText(savedInstanceState.getCharSequence("txtBox2"));
        finalPath = savedInstanceState.getCharSequence("txtBox").toString();
        finalPath2 = savedInstanceState.getCharSequence("txtBox2").toString();
    }

/*
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }*/
}
