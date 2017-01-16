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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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

public class DefineIdea extends AppCompatActivity {

    private Button btnSend;
    private EditText edtDefineIdea1, actualStateEditText1,improvedStateEditText3, advantagesEditText4, costsEditText2, receiverEditText5;
    private EditText edtDefineIdea2, edtDefineIdea3, edtDefineIdea4, nameEditText5, prioritaEditText6, nameEditText7, nameEditText8, nameEditText9, nameEditText10;
    private String proposalType, proposalSolutionDescription, proposalSolutionBenefits, proposalSolutionCosts, foto2, priorita;
    private String proposalResponsiblePerson, proposalCategory, proposalTitle, proposalProblemDescription, proposalProblemLocation, proposalProblemSource, foto;
    private static final String TAG = DefineIdea.class.getSimpleName();
    private ProgressDialog pDialog;
    private Button mail, btnDefineIdeaFileAttachment, btnDefineIdeaFotoAttachment;
    private Button btnTakePhotoNew;
    private ImageView iv;
    private ImageView iv2;
    private SQLiteHandler db;
    private SessionManager session;

    TextView messageText, messageText2,messageText22, messageText3, messageText4, messageText5, messageText6, messageText7;
    // Button uploadButton;
    ImageButton pathButton7, btnDefineIdeaLogout, btnDefineIdeaHome, btnDefineIdeaPrevious, btnDefineIdeaAck;
    Button btnDefineIdea1, btnDefineIdea2, btnDefineIdea3, btnDefineIdea4, btnDefineIdea6, btnDefineIdea5;
    Spinner spinnerDefineIdea1;
    int serverResponseCode = 0;
    ProgressDialog dialog = null;
    String proposalProblemAttachmentName;
    String proposalSolutionAttachmentName;
    String proposalAuthor;
    ImageView imgvDefineIdea;
    ImageView image2;
    private RadioGroup radioDefineIdea5;
    private RadioButton radioPrioritaButton;
    private String proposalPriority;


    String upLoadServerUri = null;

    /**********  File Path *************/
    String uploadFilePath = "/mnt/sdcard/";
    String uploadFileName = "subor.txt";
    String proposalProblemAttachmentPath = uploadFilePath + "" + uploadFileName;

    String uploadFilePath2 = "/mnt/sdcard/";
    String uploadFileName2 = "subor.txt";
    String finalPath2  = uploadFilePath2 + "" + uploadFileName2;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_define_idea);
        edtDefineIdea1 = (EditText) findViewById(R.id.edittext_define_idea_1);
        edtDefineIdea2 = (EditText) findViewById(R.id.edittext_define_idea_2);
        edtDefineIdea3 = (EditText) findViewById(R.id.edittext_define_idea_3);
        edtDefineIdea4 = (EditText) findViewById(R.id.edittext_define_idea4);


        btnDefineIdeaFileAttachment = (Button) findViewById(R.id.button_define_idea_file_attachment);
        btnDefineIdeaFotoAttachment = (Button) findViewById(R.id.button_define_idea_foto_attachment);

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


        Log.d(TAG, "RIESITEL " + proposalResponsiblePerson);
        Log.d(TAG, "KATEGORIA " + proposalCategory);
        Log.d(TAG, "NAZOV " + proposalTitle);
        Log.d(TAG, "POPIS " + proposalProblemDescription);
        Log.d(TAG, "MIESTO " + proposalProblemLocation);
        Log.d(TAG, "PRICINA " + proposalProblemSource);
        Log.d(TAG, "FOTO " + foto);
        Log.d(TAG, "FILENAME2 " + proposalProblemAttachmentName);

        session = new SessionManager(getApplicationContext());

        proposalResponsiblePerson = "whocares?";

        db = new SQLiteHandler(getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();

        proposalAuthor = user.get("email");
        Log.d(TAG, "CREATOR FROM LOCAL DATABASE " + proposalAuthor);

        imgvDefineIdea = (ImageView) findViewById(R.id.imageview_define_idea);

        radioDefineIdea5 = (RadioGroup) findViewById(R.id.radio_define_idea_5);

        proposalSolutionAttachmentName = "";

        // TOAST parameters for this activity -- Toast is informative message box
        final Context context = getApplicationContext();
        final int toastDuration = Toast.LENGTH_SHORT;



        /************* Php script path ****************/
        upLoadServerUri = "http://zims.ceitgroup.eu/kaizen/android_login_api/facina.php";

        btnDefineIdea1 = (Button) findViewById(R.id.button_define_idea_1);
        btnDefineIdea2 = (Button)findViewById(R.id.button_define_idea_2);
        btnDefineIdea3 = (Button)findViewById(R.id.button_define_idea_3);
        btnDefineIdea4 = (Button)findViewById(R.id.button_define_idea_4);
        btnDefineIdea5 = (Button)findViewById(R.id.button_define_idea_5);
        btnDefineIdea6 = (Button)findViewById(R.id.button_define_idea_6);
        btnDefineIdeaLogout = (ImageButton)findViewById(R.id.button_define_idea_logout);
        btnDefineIdeaHome = (ImageButton)findViewById(R.id.button_define_idea_home);
        btnDefineIdeaPrevious = (ImageButton)findViewById(R.id.button_define_idea_previous);
        btnDefineIdeaAck = (ImageButton)findViewById(R.id.button_define_idea_ack);


        edtDefineIdea1.setVisibility(View.GONE);
        edtDefineIdea2.setVisibility(View.GONE);
        edtDefineIdea3.setVisibility(View.GONE);
        edtDefineIdea4.setVisibility(View.GONE);
        radioDefineIdea5.setVisibility(View.GONE);
        imgvDefineIdea.setVisibility(View.GONE);
        btnDefineIdeaFileAttachment.setVisibility(View.GONE);
        btnDefineIdeaFotoAttachment.setVisibility(View.GONE);



        btnDefineIdea1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtDefineIdea1.getVisibility() == View.VISIBLE){
                    edtDefineIdea1.setVisibility(View.GONE);
                } else {
                    edtDefineIdea1.setVisibility(View.VISIBLE);
                    edtDefineIdea1.requestFocus();
                    if (proposalType !="" ) {
                        edtDefineIdea1.setText(proposalType);
                    }
                    Toast.makeText(context,getResources().getString(R.string.toast_button_2_1) ,toastDuration).show();
                }

            }
        });

        btnDefineIdea2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtDefineIdea2.getVisibility() == View.VISIBLE){
                    edtDefineIdea2.setVisibility(View.GONE);
                } else {
                    edtDefineIdea2.setVisibility(View.VISIBLE);
                    edtDefineIdea2.requestFocus();
                    if (proposalSolutionDescription !="" ) {
                        edtDefineIdea2.setText(proposalSolutionDescription);
                    }
                    Toast.makeText(context,getResources().getString(R.string.toast_button_2_2) ,toastDuration).show();
                }

            }
        });

        btnDefineIdea3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtDefineIdea3.getVisibility() == View.VISIBLE){
                    edtDefineIdea3.setVisibility(View.GONE);
                } else {
                    edtDefineIdea3.setVisibility(View.VISIBLE);
                    edtDefineIdea3.requestFocus();
                    if (proposalSolutionBenefits !="" ) {
                        edtDefineIdea3.setText(proposalSolutionBenefits);
                    }
                    Toast.makeText(context,getResources().getString(R.string.toast_button_2_3) ,toastDuration).show();
                }

            }
        });

        btnDefineIdea4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtDefineIdea4.getVisibility() == View.VISIBLE){
                    edtDefineIdea4.setVisibility(View.GONE);
                } else {
                    edtDefineIdea4.setVisibility(View.VISIBLE);
                    edtDefineIdea4.requestFocus();
                    if (proposalSolutionCosts !="" ) {
                        edtDefineIdea4.setText(proposalSolutionCosts);
                    }
                    Toast.makeText(context,getResources().getString(R.string.toast_button_2_4) ,toastDuration).show();
                }

            }
        });

        btnDefineIdea5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radioDefineIdea5.getVisibility() == View.VISIBLE){
                    radioDefineIdea5.setVisibility(View.GONE);
                } else {
                    radioDefineIdea5.setVisibility(View.VISIBLE);
                    radioDefineIdea5.setFocusable(true);
                    radioDefineIdea5.setFocusableInTouchMode(true);
                    radioDefineIdea5.requestFocus();
                    Toast.makeText(context,getResources().getString(R.string.toast_button_2_6) ,toastDuration).show();
                }

            }
        });

        btnDefineIdea6.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnDefineIdeaFileAttachment.getVisibility() == View.VISIBLE){
                    imgvDefineIdea.setVisibility(View.GONE);
                    btnDefineIdeaFileAttachment.setVisibility(View.GONE);
                    btnDefineIdeaFotoAttachment.setVisibility(View.GONE);
                } else {
                    if (uploadFileName2 != null) {
                        imgvDefineIdea.setVisibility(View.VISIBLE);
                    }
                    btnDefineIdeaFileAttachment.setVisibility(View.VISIBLE);
                    btnDefineIdeaFotoAttachment.setVisibility(View.VISIBLE);
                }
                Toast.makeText(context,getResources().getString(R.string.toast_button_2_5) ,toastDuration).show();
            }
        });

        btnDefineIdeaLogout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                session.setLogin(false);
                db.deleteUsers();
                // Launching the login activity
                Intent intent = new Intent(DefineIdea.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnDefineIdeaHome.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DefineIdea.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnDefineIdeaPrevious.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(DefineIdea.this, DefineProblem.class);
                startActivity(intent);*/
                goToDefineProblem();
            }
        });

        btnDefineIdeaAck.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                proposalType = edtDefineIdea1.getText().toString();
                proposalSolutionDescription = edtDefineIdea2.getText().toString();
                proposalSolutionBenefits = edtDefineIdea3.getText().toString();
                proposalSolutionCosts = edtDefineIdea4.getText().toString();

                Log.d(TAG, "NAZOV " + proposalTitle);
                Log.d(TAG, "POPIS " + proposalProblemDescription);
                Log.d(TAG, "POPIS2 " + proposalSolutionDescription);
                Log.d(TAG, "PRINOSY " + proposalSolutionBenefits);
                Log.d(TAG, "NAKLADY " + proposalSolutionCosts);
                Log.d(TAG, "RIESITEL " + proposalResponsiblePerson);
                Log.d(TAG, "proposalProblemAttachmentPath " + proposalProblemAttachmentPath);
                Log.d(TAG, "proposalProblemAttachmentPath " + finalPath2);
                Log.d(TAG, "PRIORITA " + priorita);
                Log.d(TAG, "KATEGORIA " + proposalCategory);
                Log.d(TAG, "FINALPATH " + proposalProblemAttachmentPath);
                Log.d(TAG, "proposalProblemAttachmentName " + proposalProblemAttachmentName);
                Log.d(TAG, "proposalProblemAttachmentName " + proposalSolutionAttachmentName);

                int selectedId2 = radioDefineIdea5.getCheckedRadioButtonId();
                radioPrioritaButton = (RadioButton) findViewById(selectedId2);

                try {
                    priorita = radioPrioritaButton.getText().toString();
                    if(priorita.equals("Urgent")){
                        proposalPriority = "1";
                    }
                    else if(priorita.equals("High")){
                        proposalPriority = "2";
                    }
                    else if(priorita.equals("Normal")){
                        proposalPriority = "3";
                    }
                    Log.d(TAG, "PRIORITA " + proposalPriority);
                } catch (Exception e) {
                    priorita = "Exception";
                }


                if(!proposalProblemAttachmentPath.equals("/mnt/sdcard/subor.txt")) {
                    dialog = ProgressDialog.show(DefineIdea.this, "", "Uploading my old file...", true);
                }

                new Thread(new Runnable() {
                    public void run() {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                if(!proposalProblemAttachmentPath.equals("/mnt/sdcard/subor.txt")) {
                                    //messageText.setText("uploading started....."); //Message text doesnt exist
                                }
                            }
                        });
                        Log.d(TAG, "FINALPATH " + proposalProblemAttachmentPath);
                        if(!proposalProblemAttachmentPath.equals("/mnt/sdcard/subor.txt")) {
                            Log.d(TAG, "FFFIIIIINNNNNNNNAAAAAAAAAALLLLLLLLPPPPPPPPPPAAAAAAAAATTTTTTTTTTTHHHHHHHHHHH " + proposalProblemAttachmentPath);
                            uploadFile(proposalProblemAttachmentPath);
                        }
                        runOnUiThread(new Runnable() {
                            public void run() {
                                if(!finalPath2.equals("/mnt/sdcard/subor.txt")) {
                                    dialog = ProgressDialog.show(DefineIdea.this, "", "Uploading new file...", true);
                                    //messageText2.setText("uploading started.....");
                                }
                            }
                        });
                        Log.d(TAG, "FINALPATH2 " + finalPath2);
                        if(!finalPath2.equals("/mnt/sdcard/subor.txt")) {
                            uploadFile2(finalPath2);
                        }
                        if (!proposalTitle.isEmpty() && !proposalProblemDescription.isEmpty() && !proposalSolutionDescription.isEmpty() && !proposalSolutionBenefits.isEmpty() && !proposalSolutionCosts.isEmpty()) {
                            registerIdea(proposalTitle, proposalProblemDescription, proposalSolutionDescription, proposalSolutionBenefits, proposalSolutionCosts, proposalAuthor, proposalProblemAttachmentName, proposalSolutionAttachmentName, proposalPriority, proposalCategory, proposalResponsiblePerson, proposalType, proposalProblemLocation, proposalProblemSource);
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

        btnDefineIdeaFotoAttachment.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    proposalSolutionAttachmentName = generateFileName();
                    File file = new File(Environment.getExternalStorageDirectory(), proposalSolutionAttachmentName);
                    Uri uri = Uri.fromFile(file);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    //File finalFile = new File(getRealPathFromURI(uri));
                    File finalFile = new File(uri.getPath());
                    finalPath2 = finalFile.getAbsolutePath();
                    //.setText(proposalProblemAttachmentPath);
                    uploadFileName2 = finalFile.getName();
                    uploadFilePath2 = finalFile.getParent();
                    if (uploadFileName2 != null) {
                        imgvDefineIdea.setVisibility(View.VISIBLE);
                    }
                    startActivityForResult(intent,6);
                } catch (Exception e) {
                    Log.e("EXCEPTION :((", e.toString());
                }

            }
        });

        btnDefineIdeaFileAttachment.setOnClickListener(new OnClickListener() {
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

                dialog = ProgressDialog.show(DefineProblem.this, "", "Uploading old file...", true);

                new Thread(new Runnable() {
                    public void run() {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                messageText.setText("uploading started.....");
                            }
                        });

                        uploadFile(proposalProblemAttachmentPath);
                        runOnUiThread(new Runnable() {
                            public void run() {
                                dialog = ProgressDialog.show(DefineProblem.this, "", "Uploading new file...", true);
                                messageText2.setText("uploading started.....");
                            }
                        });
                        uploadFile2(proposalProblemAttachmentPath);

                    }
                }).start();
            }
        });*/

       /* btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                proposalType = edtDefineIdea1.getText().toString();
                proposalSolutionDescription = edtDefineIdea2.getText().toString();
                proposalSolutionBenefits = edtDefineIdea3.getText().toString();
                proposalSolutionCosts = edtDefineIdea4.getText().toString();
                //costs = costsEditText2.getText().toString();
                //proposalResponsiblePerson = receiverEditText5.getText().toString();

                int selectedId2 = radioDefineIdea5.getCheckedRadioButtonId();
                radioPrioritaButton = (RadioButton) findViewById(selectedId2);

                try {
                    priorita = radioPrioritaButton.getText().toString();
                    if(priorita.equals("Urgent")){
                        proposalPriority = "1";
                    }
                    else if(priorita.equals("High")){
                        proposalPriority = "2";
                    }
                    else if(priorita.equals("Normal")){
                        proposalPriority = "3";
                    }
                    Log.d(TAG, "PRIORITA " + proposalPriority);
                } catch (Exception e) {
                    priorita = "";
                }


                if(!proposalProblemAttachmentPath.equals("/mnt/sdcard/subor.txt")) {
                    dialog = ProgressDialog.show(DefineIdea.this, "", "Uploading old file...", true);
                }

                new Thread(new Runnable() {
                    public void run() {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                if(!proposalProblemAttachmentPath.equals("/mnt/sdcard/subor.txt")) {
                                    messageText.setText("uploading started.....");
                                }
                            }
                        });
                        Log.d(TAG, "FINALPATH " + proposalProblemAttachmentPath);
                        if(!proposalProblemAttachmentPath.equals("/mnt/sdcard/subor.txt")) {
                            uploadFile(foto);
                        }
                        runOnUiThread(new Runnable() {
                            public void run() {
                                if(!proposalProblemAttachmentPath.equals("/mnt/sdcard/subor.txt")) {
                                    dialog = ProgressDialog.show(DefineIdea.this, "", "Uploading new file...", true);
                                    messageText2.setText("uploading started.....");
                                }
                            }
                        });
                        Log.d(TAG, "FINALPATH2 " + proposalProblemAttachmentPath);
                        if(!proposalProblemAttachmentPath.equals("/mnt/sdcard/subor.txt")) {
                            uploadFile2(foto2);
                        }
                        if (!proposalTitle.isEmpty() && !proposalProblemDescription.isEmpty() && !proposalSolutionDescription.isEmpty() && !proposalSolutionBenefits.isEmpty() && !proposalSolutionCosts.isEmpty()) {
                            registerIdea(proposalTitle, proposalProblemDescription, proposalSolutionDescription, proposalSolutionBenefits, proposalSolutionCosts, proposalResponsiblePerson, foto, foto2, priorita, proposalCategory, proposalType, proposalProblemLocation);
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


        //db.createRecords(proposalTitle, proposalCategory,datum, priorita,proposalProblemDescription);



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
                proposalResponsiblePerson = receiverEditText5.getText().toString();

                int selectedId2 = radioDefineIdea5.getCheckedRadioButtonId();
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
                    Image img = Image.getInstance(Environment.getExternalStorageDirectory() + "/" +proposalProblemAttachmentName);
                    PdfPCell cell = new PdfPCell(img, true);
                    table.addCell(cell);
                    table.addCell("Improved state: ");
                    table.addCell(improved_state);
                    table.addCell("Picture of the improved state: ");
                    Image img2 = Image.getInstance(Environment.getExternalStorageDirectory() + "/" +proposalProblemAttachmentName);
                    PdfPCell cell2 = new PdfPCell(img2, true);
                    table.addCell(cell2);
                    table.addCell("Advantages: ");
                    table.addCell(advantages);
                    table.addCell("Costs: ");
                    table.addCell(costs);
                    table.addCell("Receiver: ");
                    table.addCell(proposalResponsiblePerson);
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

                Intent intent = new Intent(DefineProblem.this, MailActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("actual_state", actual_state);
                intent.putExtra("improved_state", improved_state);
                intent.putExtra("advantages", advantages);
                intent.putExtra("costs", costs);
                intent.putExtra("proposalResponsiblePerson", proposalResponsiblePerson);
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
                // Set the imgvDefineIdea in ImageView
                imgvDefineIdea.setImageURI(selectedImageUri);
            }
            proposalSolutionAttachmentName = getFileName(selectedImageUri);

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
            File imgFile = new  File(finalPath2);
            Log.d(TAG, "ABSOLUTE PATH  " + finalPath2);
            //foto2 = proposalProblemAttachmentPath;
            if(imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                matrix.postRotate(90);
                Bitmap resized = Bitmap.createScaledBitmap(myBitmap, 300, 300, true);
                Bitmap rotatedBitmap = Bitmap.createBitmap(resized, 0, 0, resized.getWidth(), resized.getHeight(), matrix, true);
                imgvDefineIdea.setImageBitmap(rotatedBitmap);
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
                   /* messageText.setText("Source File not exist :"
                            +uploadFilePath + "" + uploadFileName);*/
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

//                            messageText.setText(msg);
                            Toast.makeText(DefineIdea.this, "File Upload Complete.",
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
//                        messageText.setText("MalformedURLException Exception : check script url.");
                        Toast.makeText(DefineIdea.this, "MalformedURLException",
                                Toast.LENGTH_SHORT).show();
                    }
                });

                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {

                dialog.dismiss();
                e.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
//                        messageText.setText("Got Exception : see logcat ");
                        Toast.makeText(DefineIdea.this, "Got Exception : see logcat ",
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
                            Toast.makeText(DefineIdea.this, "File Upload Complete.",
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
//                        messageText.setText("MalformedURLException Exception : check script url.");
                        Toast.makeText(DefineIdea.this, "MalformedURLException",
                                Toast.LENGTH_SHORT).show();
                    }
                });

                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {

                //dialog.dismiss();
                e.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
//                        messageText.setText("Got Exception : see logcat ");
                        Toast.makeText(DefineIdea.this, "Got Exception : see logcat ",
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
    //proposalTitle, proposalProblemDescription, proposalSolutionDescription, proposalSolutionBenefits, proposalSolutionCosts, proposalAuthor, proposalProblemAttachmentName, proposalProblemAttachmentName, priorita, proposalCategory, proposalResponsiblePerson, proposalType, proposalProblemLocation, proposalProblemSource

    private void goToDefineProblem() {

        proposalResponsiblePerson = "whoCares?";


        proposalType = edtDefineIdea1.getText().toString();
        proposalSolutionDescription = edtDefineIdea2.getText().toString();
        proposalSolutionBenefits = edtDefineIdea3.getText().toString();
        proposalSolutionCosts = edtDefineIdea4.getText().toString();
        proposalType = edtDefineIdea1.getText().toString();

        //foto;
        if(edtDefineIdea1.getText().toString()!=null)
        {
            proposalType = edtDefineIdea1.getText().toString();
        }
        else
        {
            proposalType =  "";
        }

        if(edtDefineIdea2.getText().toString()!=null) {
            proposalSolutionDescription = edtDefineIdea2.getText().toString();
        }
        else
        {
            proposalSolutionDescription =  "";
        }

        if(edtDefineIdea3.getText().toString()!=null)
        {
            proposalSolutionBenefits = edtDefineIdea2.getText().toString();
        }
        else
        {
            proposalSolutionBenefits =  "";
        }

        if(edtDefineIdea4.getText().toString()!=null)
        {
            proposalSolutionCosts = edtDefineIdea4.getText().toString();
        }
        else
        {
            proposalSolutionCosts =  "";
        }


        //String item3 = spinnerDefineProblem1.getSelectedItem().toString();

        //Log.d(TAG, "SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS " + item3);

        Log.d(TAG, "RIESITEL " + proposalResponsiblePerson);
        Log.d(TAG, "KATEGORIA " + proposalCategory);
        Log.d(TAG, "NAZOV " + proposalTitle);
        Log.d(TAG, "POPIS " + proposalProblemDescription);
        Log.d(TAG, "MIESTO " + proposalProblemLocation);
        Log.d(TAG, "PRICINA " + proposalProblemSource);
        Log.d(TAG, "FOTO " + foto);
        Log.d(TAG, "FILENAME2 " + proposalProblemAttachmentName);


        Intent intent = new Intent(DefineIdea.this, DefineProblem.class);
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

        startActivity(intent);
    }

    private void registerIdea(final String name, final String actual_state, final String improved_state, final String advantages, final String costs, final String creator, final String fileName1, final String fileName2, final String priority, final String cathegory, final String receiver, final String type, final String location, final String source) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";
        int a=2;
        //pDialog.setMessage("Sending...");
        //showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_DATA, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "new Register Response: " + response.toString());
                //hideDialog();

                try {

                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {


                        // MainActivity successfully stored in MySQL
                        // Now store the user in sqlite
                        //String uid = jObj.getString("uid");

                        JSONObject idea = jObj.getJSONObject("idea");
                        String name = idea.getString("short_name");
                        String act_state = idea.getString("actual_state");
                        String impState = idea.getString("improved_state");
                        String adv = idea.getString("advantages");
                        String cos = idea.getString("costs");
                        String creator = idea.getString("proposalAuthor");
                        String fileName1 = idea.getString("file_before");
                        String fileName2 = idea.getString("file_after");
                        //String rec = idea.getString("")


                        // Inserting row in users table
                        db.addIdea(name, act_state, impState, adv, cos, creator,  fileName1, fileName2);

                        //Toast.makeText(getApplicationContext(), "Idea successfully stored.", Toast.LENGTH_LONG).show();

                        // Launch login activity
                        Intent intent = new Intent(DefineIdea.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                        Log.d(TAG, "registration REGISTRATION ERROR: " +errorMsg );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Toast.makeText(getApplicationContext(), "Idea successfully stored.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(DefineIdea.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "new Registration Error: " + error.getMessage());
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
                params.put("proposalAuthor", creator);
                params.put("file_before",fileName1);
                params.put("file_after", fileName2);
                params.put("priority", priority);
                params.put("receiver", receiver);
                params.put("category", proposalCategory);
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
        proposalProblemAttachmentPath = savedInstanceState.getCharSequence("txtBox").toString();
        finalPath2 = savedInstanceState.getCharSequence("txtBox2").toString();
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

