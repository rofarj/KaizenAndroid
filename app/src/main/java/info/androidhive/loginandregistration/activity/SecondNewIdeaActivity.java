package info.androidhive.loginandregistration.activity;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import info.androidhive.loginandregistration.R;

import android.view.View.OnClickListener;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import info.androidhive.loginandregistration.app.AppConfig;
import info.androidhive.loginandregistration.app.AppController;
import info.androidhive.loginandregistration.helper.SQLiteHandler;
import info.androidhive.loginandregistration.helper.SessionManager;

public class SecondNewIdeaActivity extends AppCompatActivity {

    private Button btnSend;
    private EditText typEditText, actualStateEditText1,improvedStateEditText3, advantagesEditText4, costsEditText2, receiverEditText5;
    private EditText popis2EditText2, prinosyEditText3, nakladyEditText4, nameEditText5, prioritaEditText6, nameEditText7, nameEditText8, nameEditText9, nameEditText10;
    private String typ, popis2, prinosy, naklady, foto2, priorita;
    private String riesitel, kategoria, nazov, popis, miesto, pricina, foto;
    private static final String TAG = SecondNewIdeaActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private Button mail, fromSystemButton, fotoButton;
    private Button btnTakePhotoNew;
    private ImageView iv;
    private ImageView iv2;
    int visibility, visibility2, visibility3, visibility4, visibility5, visibility6, visibility7, visibility8, visibility9, visibility10;
    private SQLiteHandler db;
    private SessionManager session;

    TextView messageText, messageText2,messageText22, messageText3, messageText4, messageText5, messageText6, messageText7;
    // Button uploadButton;
    ImageButton path2Button, path2Button2, path2Button3, path2Button4,path2Button5,path2Button6,pathButton7,path2Button8,path2Button9,path2Button10, path2Button11;
    int serverResponseCode = 0;
    ProgressDialog dialog = null;
    String fileName1;
    String fileName2;
    String creator;
    String receiver;
    ImageView image;
    ImageView image2;
    private RadioGroup radioPrioritaGroup;
    private RadioButton radioPrioritaButton;
    private String cislo_priorita;


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

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_new_idea);
        typEditText = (EditText) findViewById(R.id.editText2Test);
        popis2EditText2 = (EditText) findViewById(R.id.editText2Test2);
        prinosyEditText3 = (EditText) findViewById(R.id.editText2Test3);
        nakladyEditText4 = (EditText) findViewById(R.id.editText2Test4);
        //nameEditText5 = (EditText) findViewById(R.id.editText2Test5);
        //prioritaEditText6 = (EditText) findViewById(R.id.editText2Test6);

        fromSystemButton = (Button) findViewById(R.id.Button2);
        fotoButton = (Button) findViewById(R.id.Button3);

        Intent intent = getIntent();
        riesitel = intent.getStringExtra("riesitel");
        kategoria = intent.getStringExtra("kategoria");
        nazov = intent.getStringExtra("nazov");
        popis = intent.getStringExtra("popis");
        miesto = intent.getStringExtra("miesto");
        pricina = intent.getStringExtra("pricina");
        finalPath = intent.getStringExtra("finalPath2");
        fileName1 = intent.getStringExtra("fileName2");

        session = new SessionManager(getApplicationContext());

        receiver = "whocares?";

        db = new SQLiteHandler(getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();

        creator = user.get("email");
        Log.d(TAG, "CREATOR FROM LOCAL DATABASE " + creator);

        //nameEditText.setHint("abrakadabra");
        /*actualStateEditText1 = (EditText) findViewById(R.id.editText1);
        improvedStateEditText3 = (EditText) findViewById(R.id.editText3);
        advantagesEditText4 = (EditText) findViewById(R.id.editText4);
        costsEditText2 = (EditText) findViewById(R.id.editText2);
        receiverEditText5 = (EditText) findViewById(R.id.editText5);
        btnSend = (Button) findViewById(R.id.button);
        mail = (Button) findViewById(R.id.button2);*/
        image = (ImageView) findViewById(R.id.imageViewImproved);
        /*image2 = (ImageView) findViewById(R.id.imageView2);*/
        radioPrioritaGroup = (RadioGroup) findViewById(R.id.radioPriorita);
        //fileName1 = "";
        fileName2 = "";
        visibility = 0;
        visibility2 = 0;
        visibility3 = 0;
        visibility4 = 0;
        visibility5 = 0;
        visibility6 = 0;
        visibility7 = 0;
        visibility8 = 0;
        visibility9 = 0;
        visibility10 = 0;

        // Session manager
        /*session = new SessionManager(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());


        //uploadButton = (Button)findViewById(R.id.uploadButton);
        pathButton = (Button)findViewById(R.id.pathButton);
        pathButton2 = (Button)findViewById(R.id.pathButton2);


        //messageText2  = (TextView)findViewById(R.id.messageText2);

        messageText.setText("No photo of actual state taken");
        messageText2.setText("No photo of improved state taken");*/

        /************* Php script path ****************/
        upLoadServerUri = "http://zims.ceitgroup.eu/kaizen/android_login_api/facina.php";

        path2Button = (ImageButton)findViewById(R.id.path2Button);
        path2Button2 = (ImageButton)findViewById(R.id.path2Button2);
        path2Button3 = (ImageButton)findViewById(R.id.path2Button3);
        path2Button4 = (ImageButton)findViewById(R.id.path2Button4);
        path2Button5 = (ImageButton)findViewById(R.id.path2Button5);
        path2Button6 = (ImageButton)findViewById(R.id.path2Button6);
        path2Button8 = (ImageButton)findViewById(R.id.path2Button8);
        path2Button9 = (ImageButton)findViewById(R.id.path2Button9);
        path2Button10 = (ImageButton)findViewById(R.id.path2Button10);
        path2Button11 = (ImageButton)findViewById(R.id.path2Button11);

        messageText  = (TextView)findViewById(R.id.viewText2Test);
        messageText22  = (TextView)findViewById(R.id.viewText2Test2);
        messageText3  = (TextView)findViewById(R.id.viewText2Test3);
        messageText4  = (TextView)findViewById(R.id.viewText2Test4);
        //messageText5  = (TextView)findViewById(R.id.viewText2Test5);
        //messageText6  = (TextView)findViewById(R.id.viewText2Test6);

        typEditText.setVisibility(View.GONE);
        messageText.setVisibility(View.GONE);
        popis2EditText2.setVisibility(View.GONE);
        messageText22.setVisibility(View.GONE);
        prinosyEditText3.setVisibility(View.GONE);
        messageText3.setVisibility(View.GONE);
        nakladyEditText4.setVisibility(View.GONE);
        messageText4.setVisibility(View.GONE);
        radioPrioritaGroup.setVisibility(View.GONE);
        //nameEditText5.setVisibility(View.GONE);
        //messageText5.setVisibility(View.GONE);
        //prioritaEditText6.setVisibility(View.GONE);
        //messageText6.setVisibility(View.GONE);

        image.setVisibility(View.GONE);

        fromSystemButton.setVisibility(View.GONE);
        fotoButton.setVisibility(View.GONE);

        path2Button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                typEditText.setVisibility(View.VISIBLE);
                messageText.setVisibility(View.VISIBLE);
                popis2EditText2.setVisibility(View.GONE);
                messageText22.setVisibility(View.GONE);
                prinosyEditText3.setVisibility(View.GONE);
                messageText3.setVisibility(View.GONE);
                nakladyEditText4.setVisibility(View.GONE);
                messageText4.setVisibility(View.GONE);
                //prioritaEditText6.setVisibility(View.GONE);
                //messageText6.setVisibility(View.GONE);
                image.setVisibility(View.GONE);
                fromSystemButton.setVisibility(View.GONE);
                fotoButton.setVisibility(View.GONE);
                radioPrioritaGroup.setVisibility(View.GONE);



                /*try {

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
                }*/

            }
        });

        path2Button2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                typEditText.setVisibility(View.GONE);
                messageText.setVisibility(View.GONE);
                popis2EditText2.setVisibility(View.VISIBLE);
                messageText22.setVisibility(View.VISIBLE);
                prinosyEditText3.setVisibility(View.GONE);
                messageText3.setVisibility(View.GONE);
                nakladyEditText4.setVisibility(View.GONE);
                messageText4.setVisibility(View.GONE);
                //prioritaEditText6.setVisibility(View.GONE);
                //messageText6.setVisibility(View.GONE);
                image.setVisibility(View.GONE);
                fromSystemButton.setVisibility(View.GONE);
                fotoButton.setVisibility(View.GONE);
                radioPrioritaGroup.setVisibility(View.GONE);

                /*try {

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
                }*/

            }
        });

        path2Button3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                typEditText.setVisibility(View.GONE);
                messageText.setVisibility(View.GONE);
                popis2EditText2.setVisibility(View.GONE);
                messageText22.setVisibility(View.GONE);
                prinosyEditText3.setVisibility(View.VISIBLE);
                messageText3.setVisibility(View.VISIBLE);
                nakladyEditText4.setVisibility(View.GONE);
                messageText4.setVisibility(View.GONE);
                //prioritaEditText6.setVisibility(View.GONE);
                //messageText6 .setVisibility(View.GONE);
                image.setVisibility(View.GONE);
                fromSystemButton.setVisibility(View.GONE);
                fotoButton.setVisibility(View.GONE);
                radioPrioritaGroup.setVisibility(View.GONE);

            }
        });

        path2Button4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                typEditText.setVisibility(View.GONE);
                messageText.setVisibility(View.GONE);
                popis2EditText2.setVisibility(View.GONE);
                messageText22.setVisibility(View.GONE);
                prinosyEditText3.setVisibility(View.GONE);
                messageText3.setVisibility(View.GONE);
                nakladyEditText4.setVisibility(View.VISIBLE);
                messageText4.setVisibility(View.VISIBLE);
                //prioritaEditText6.setVisibility(View.GONE);
                //messageText6.setVisibility(View.GONE);
                image.setVisibility(View.GONE);
                fromSystemButton.setVisibility(View.GONE);
                fotoButton.setVisibility(View.GONE);
                radioPrioritaGroup.setVisibility(View.GONE);

            }
        });

        path2Button5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                typEditText.setVisibility(View.GONE);
                messageText.setVisibility(View.GONE);
                popis2EditText2.setVisibility(View.GONE);
                messageText22.setVisibility(View.GONE);
                prinosyEditText3.setVisibility(View.GONE);
                messageText3.setVisibility(View.GONE);
                nakladyEditText4.setVisibility(View.GONE);
                messageText4.setVisibility(View.GONE);
                //prioritaEditText6.setVisibility(View.GONE);
                //messageText6.setVisibility(View.GONE);
                image.setVisibility(View.VISIBLE);
                fromSystemButton.setVisibility(View.VISIBLE);
                fotoButton.setVisibility(View.VISIBLE);
                radioPrioritaGroup.setVisibility(View.GONE);
            }
        });

        path2Button6.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                typEditText.setVisibility(View.GONE);
                messageText.setVisibility(View.GONE);
                popis2EditText2.setVisibility(View.GONE);
                messageText22.setVisibility(View.GONE);
                prinosyEditText3.setVisibility(View.GONE);
                messageText3.setVisibility(View.GONE);
                nakladyEditText4.setVisibility(View.GONE);
                messageText4.setVisibility(View.GONE);
                //prioritaEditText6.setVisibility(View.VISIBLE);
                //messageText6.setVisibility(View.VISIBLE);
                image.setVisibility(View.GONE);
                fromSystemButton.setVisibility(View.GONE);
                fotoButton.setVisibility(View.GONE);
                radioPrioritaGroup.setVisibility(View.VISIBLE);
            }
        });


        path2Button8.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                session.setLogin(false);

                db.deleteUsers();

                // Launching the login activity
                Intent intent = new Intent(SecondNewIdeaActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });

        path2Button9.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(SecondNewIdeaActivity.this, User.class);
                startActivity(intent);
                finish();



            }
        });


        path2Button10.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent = new Intent(SecondNewIdeaActivity.this, FirstNewIdeaActivity.class);
                startActivity(intent);

            }
        });

        path2Button11.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                typ = typEditText.getText().toString();
                popis2 = popis2EditText2.getText().toString();
                prinosy = prinosyEditText3.getText().toString();
                naklady = nakladyEditText4.getText().toString();
                //priorita = prioritaEditText6.getText().toString();
                //costs = costsEditText2.getText().toString();
                //receiver = receiverEditText5.getText().toString();

                Log.d(TAG, "NAZOV " + nazov);
                Log.d(TAG, "POPIS " + popis);
                Log.d(TAG, "POPIS2 " + popis2);
                Log.d(TAG, "PRINOSY " + prinosy);
                Log.d(TAG, "NAKLADY " + naklady);
                Log.d(TAG, "RIESITEL " + riesitel);
                Log.d(TAG, "finalPath " + finalPath);
                Log.d(TAG, "finalPath2 " + finalPath2);
                Log.d(TAG, "PRIORITA " + priorita);
                Log.d(TAG, "KATEGORIA " + kategoria);
                Log.d(TAG, "FINALPATH " + finalPath);
                Log.d(TAG, "fileName1 " + fileName1);
                Log.d(TAG, "fileName2 " + fileName2);

                int selectedId2 = radioPrioritaGroup.getCheckedRadioButtonId();
                radioPrioritaButton = (RadioButton) findViewById(selectedId2);

                try {
                    priorita = radioPrioritaButton.getText().toString();
                    if(priorita.equals("Urgent")){
                        cislo_priorita = "1";
                    }
                    else if(priorita.equals("High")){
                        cislo_priorita = "2";
                    }
                    else if(priorita.equals("Normal")){
                        cislo_priorita = "3";
                    }
                    Log.d(TAG, "PRIORITA " + cislo_priorita);
                } catch (Exception e) {
                    priorita = "Exception";
                }


                if(!finalPath.equals("/mnt/sdcard/subor.txt")) {
                    dialog = ProgressDialog.show(SecondNewIdeaActivity.this, "", "Uploading old file...", true);
                }

                new Thread(new Runnable() {
                    public void run() {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                if(!finalPath.equals("/mnt/sdcard/subor.txt")) {
                                    messageText.setText("uploading started.....");
                                }
                            }
                        });
                        Log.d(TAG, "FINALPATH " + finalPath);
                        if(!finalPath.equals("/mnt/sdcard/subor.txt")) {
                            Log.d(TAG, "FFFIIIIINNNNNNNNAAAAAAAAAALLLLLLLLPPPPPPPPPPAAAAAAAAATTTTTTTTTTTHHHHHHHHHHH " + finalPath);
                            uploadFile(finalPath);
                        }
                        runOnUiThread(new Runnable() {
                            public void run() {
                                if(!finalPath2.equals("/mnt/sdcard/subor.txt")) {
                                    dialog = ProgressDialog.show(SecondNewIdeaActivity.this, "", "Uploading new file...", true);
                                    //messageText2.setText("uploading started.....");
                                }
                            }
                        });
                        Log.d(TAG, "FINALPATH2 " + finalPath2);
                        if(!finalPath2.equals("/mnt/sdcard/subor.txt")) {
                            uploadFile2(finalPath2);
                        }
                        if (!nazov.isEmpty() && !popis.isEmpty() && !popis2.isEmpty() && !prinosy.isEmpty() && !naklady.isEmpty()) {
                            registerIdea(nazov, popis, popis2, prinosy, naklady, creator, fileName1, fileName2, cislo_priorita, kategoria, receiver, typ, miesto, pricina);
                        } else {
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "Please enter your details!", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                }).start();




            }
        });

        fromSystemButton.setOnClickListener(new OnClickListener() {
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
                    //.setText(finalPath2);
                    uploadFileName2 = finalFile.getName();
                    uploadFilePath2 = finalFile.getParent();
                    startActivityForResult(intent,6);
                } catch (Exception e) {
                    Log.e("EXCEPTION :((", e.toString());
                }

            }
        });

        fotoButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

                startActivityForResult(chooserIntent, 7);
            }
        });

        /*uploadButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog = ProgressDialog.show(FirstNewIdeaActivity.this, "", "Uploading old file...", true);

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
                                dialog = ProgressDialog.show(FirstNewIdeaActivity.this, "", "Uploading new file...", true);
                                messageText2.setText("uploading started.....");
                            }
                        });
                        uploadFile2(finalPath2);

                    }
                }).start();
            }
        });*/

       /* btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                typ = typEditText.getText().toString();
                popis2 = popis2EditText2.getText().toString();
                prinosy = prinosyEditText3.getText().toString();
                naklady = nakladyEditText4.getText().toString();
                //costs = costsEditText2.getText().toString();
                //receiver = receiverEditText5.getText().toString();

                int selectedId2 = radioPrioritaGroup.getCheckedRadioButtonId();
                radioPrioritaButton = (RadioButton) findViewById(selectedId2);

                try {
                    priorita = radioPrioritaButton.getText().toString();
                    if(priorita.equals("Urgent")){
                        cislo_priorita = "1";
                    }
                    else if(priorita.equals("High")){
                        cislo_priorita = "2";
                    }
                    else if(priorita.equals("Normal")){
                        cislo_priorita = "3";
                    }
                    Log.d(TAG, "PRIORITA " + cislo_priorita);
                } catch (Exception e) {
                    priorita = "";
                }


                if(!finalPath.equals("/mnt/sdcard/subor.txt")) {
                    dialog = ProgressDialog.show(SecondNewIdeaActivity.this, "", "Uploading old file...", true);
                }

                new Thread(new Runnable() {
                    public void run() {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                if(!finalPath.equals("/mnt/sdcard/subor.txt")) {
                                    messageText.setText("uploading started.....");
                                }
                            }
                        });
                        Log.d(TAG, "FINALPATH " + finalPath);
                        if(!finalPath.equals("/mnt/sdcard/subor.txt")) {
                            uploadFile(foto);
                        }
                        runOnUiThread(new Runnable() {
                            public void run() {
                                if(!finalPath2.equals("/mnt/sdcard/subor.txt")) {
                                    dialog = ProgressDialog.show(SecondNewIdeaActivity.this, "", "Uploading new file...", true);
                                    messageText2.setText("uploading started.....");
                                }
                            }
                        });
                        Log.d(TAG, "FINALPATH2 " + finalPath2);
                        if(!finalPath2.equals("/mnt/sdcard/subor.txt")) {
                            uploadFile2(foto2);
                        }
                        if (!nazov.isEmpty() && !popis.isEmpty() && !popis2.isEmpty() && !prinosy.isEmpty() && !naklady.isEmpty()) {
                            registerIdea(nazov, popis, popis2, prinosy, naklady, riesitel, foto, foto2, priorita, kategoria, typ, miesto);
                        } else {
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "Please enter your details!", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                }).start();

                //Intent intent2 = new Intent(v.getContext(), User.class);
                //startActivity(intent2);


        //db.createRecords(nazov, kategoria,datum, priorita,popis);



            }
        });*/

        /*mail.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                name = nameEditText.getText().toString();
                actual_state = actualStateEditText1.getText().toString();
                improved_state = improvedStateEditText3.getText().toString();
                advantages = advantagesEditText4.getText().toString();
                costs = costsEditText2.getText().toString();
                receiver = receiverEditText5.getText().toString();

                int selectedId2 = radioPrioritaGroup.getCheckedRadioButtonId();
                radioPrioritaButton = (RadioButton) findViewById(selectedId2);

                String DEST =  Environment.getExternalStorageDirectory() + "/simpleTable.pdf";
                File file = new File(DEST);
                file.getParentFile().mkdirs();
                try {
                    //createPdf(DEST);
                    Document document = new Document();
                    PdfWriter.getInstance(document, new FileOutputStream(DEST));
                    document.open();
                    PdfPTable table = new PdfPTable(2);
                    table.addCell("CEIT KAIZEN");
                    table.addCell("");
                    table.addCell("Name od Kaizen: ");
                    table.addCell(name);
                    table.addCell("Actual state: ");
                    table.addCell(actual_state);
                    table.addCell("Picture of the actual state: ");
                    Image img = Image.getInstance(Environment.getExternalStorageDirectory() + "/" +fileName1);
                    PdfPCell cell = new PdfPCell(img, true);
                    table.addCell(cell);
                    table.addCell("Improved state: ");
                    table.addCell(improved_state);
                    table.addCell("Picture of the improved state: ");
                    Image img2 = Image.getInstance(Environment.getExternalStorageDirectory() + "/" +fileName2);
                    PdfPCell cell2 = new PdfPCell(img2, true);
                    table.addCell(cell2);
                    table.addCell("Advantages: ");
                    table.addCell(advantages);
                    table.addCell("Costs: ");
                    table.addCell(costs);
                    table.addCell("Receiver: ");
                    table.addCell(receiver);
                    table.addCell("Priority: ");
                    table.addCell(radioPrioritaButton.getText().toString());
                    document.add(table);
                    document.close();
                }
                catch(IOException e)
                {
                }
                catch(DocumentException e)
                {
                }

                Intent intent = new Intent(FirstNewIdeaActivity.this, MailActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("actual_state", actual_state);
                intent.putExtra("improved_state", improved_state);
                intent.putExtra("advantages", advantages);
                intent.putExtra("costs", costs);
                intent.putExtra("receiver", receiver);
                startActivity(intent);
            }
        });
*/
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 7 && resultCode == RESULT_OK) {
            Log.d(TAG, "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            // Get the url from data
            Uri selectedImageUri = data.getData(); 
            if (null != selectedImageUri) {
                // Get the path from the Uri
                String path = getPathFromURI(selectedImageUri);
                Log.i(TAG, "Image Path : " + path);
                finalPath2 = path;
                // Set the image in ImageView
                image.setImageURI(selectedImageUri);
            }
            fileName2 = getFileName(selectedImageUri);

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
            //foto2 = finalPath2;
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
                            Toast.makeText(SecondNewIdeaActivity.this, "File Upload Complete.",
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
                        Toast.makeText(SecondNewIdeaActivity.this, "MalformedURLException",
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
                        Toast.makeText(SecondNewIdeaActivity.this, "Got Exception : see logcat ",
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
                    //messageText2.setText("Source File not exist :"
                     //       +uploadFilePath + "" + uploadFileName);
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

                            //messageText2.setText(msg);
                            Toast.makeText(SecondNewIdeaActivity.this, "File Upload Complete.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {

                //dialog.dismiss();
                ex.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
                        messageText.setText("MalformedURLException Exception : check script url.");
                        Toast.makeText(SecondNewIdeaActivity.this, "MalformedURLException",
                                Toast.LENGTH_SHORT).show();
                    }
                });

                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {

                //dialog.dismiss();
                e.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
                        messageText.setText("Got Exception : see logcat ");
                        Toast.makeText(SecondNewIdeaActivity.this, "Got Exception : see logcat ",
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
    //nazov, popis, popis2, prinosy, naklady, creator, fileName1, fileName2, priorita, kategoria, receiver, typ, miesto, pricina
    private void registerIdea(final String name, final String actual_state, final String improved_state, final String advantages, final String costs, final String creator, final String fileName1, final String fileName2, final String priority, final String cathegory, final String receiver, final String type, final String location, final String source) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";
        int a=2;
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
                        //String rec = idea.getString("")


                        // Inserting row in users table
                        db.addIdea(name, act_state, impState, adv, cos, creator,  fileName1, fileName2);

                        Toast.makeText(getApplicationContext(), "Idea successfully stored.", Toast.LENGTH_LONG).show();

                        // Launch login activity
                        Intent intent = new Intent(SecondNewIdeaActivity.this, LoginActivity.class);
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
                params.put("priority", priority);
                params.put("receiver", receiver);
                params.put("category", kategoria);
                params.put("type", type);
                params.put("location", location);
                params.put("source", source);
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
        //outState.putCharSequence("txtBox", messageText.getText());
        //outState.putCharSequence("txtBox2", messageText2.getText());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //messageText.setText(savedInstanceState.getCharSequence("txtBox"));
        //messageText2.setText(savedInstanceState.getCharSequence("txtBox2"));
        //finalPath = savedInstanceState.getCharSequence("txtBox").toString();
        //finalPath2 = savedInstanceState.getCharSequence("txtBox2").toString();
    }


    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
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

