package info.androidhive.loginandregistration.activity;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Stack;

import info.androidhive.loginandregistration.R;

public class MailActivity extends AppCompatActivity {

    private Button mail;
    private String name, actual_state, improved_state, advantages, costs, receiver;
    private EditText textTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);


        mail = (Button) findViewById(R.id.buttonMail);
        textTo = (EditText) findViewById(R.id.editTextt);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        actual_state = intent.getStringExtra("actual_state");
        improved_state = intent.getStringExtra("improved_state");
        advantages = intent.getStringExtra("advantages");
        costs = intent.getStringExtra("costs");
        receiver = intent.getStringExtra("receiver");


        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String to = textTo.getText().toString();

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("application/pdf");

                i.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:/sdcard/simpleTable.pdf"));
                //i.putExtra(Intent.EXTRA_STREAM, "zsskCD.pdf");
                i.putExtra(Intent.EXTRA_EMAIL, new String[] { to });
                i.putExtra(Intent.EXTRA_SUBJECT, "Kaizen Idea");
                i.putExtra(Intent.EXTRA_TEXT, "Attached is a Kaizen Idea");
                //i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(Intent.createChooser(i, "E-mail"));
                //startActivity(createEmailOnlyChooserIntent(i, "E-mail"));
            }
        });

    }

    public Intent createEmailOnlyChooserIntent(Intent source,
                                               CharSequence chooserTitle) {
        Stack<Intent> intents = new Stack<Intent>();
        Intent i = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto",
                "info@domain.com", null));
        List<ResolveInfo> activities = getPackageManager()
                .queryIntentActivities(i, 0);

        for(ResolveInfo ri : activities) {
            Intent target = new Intent(source);
            target.setPackage(ri.activityInfo.packageName);
            intents.add(target);
        }

        if(!intents.isEmpty()) {
            Intent chooserIntent = Intent.createChooser(intents.remove(0),
                    chooserTitle);
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
                    intents.toArray(new Parcelable[intents.size()]));

            return chooserIntent;
        } else {
            return Intent.createChooser(source, chooserTitle);
        }
    }

}
