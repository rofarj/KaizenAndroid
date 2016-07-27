package info.androidhive.loginandregistration.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;

import java.util.HashMap;

import info.androidhive.loginandregistration.R;
import info.androidhive.loginandregistration.helper.SQLiteHandler;
import info.androidhive.loginandregistration.helper.SessionManager;

/**
 * Uvodna aktivita aplikacie. Prerobena verzia z KucharkaFragment
 */
public class User extends Activity implements View.OnClickListener {
    private SQLiteHandler db;
    private SessionManager session;
    private String btn;
    private String type;
    private ImageButton button;
    String creator;
    String name;
    String actual_state;
    String improved_state;
    String advantages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);



        ((ImageButton)findViewById(R.id.add_item)).setOnClickListener(this);
        ((ImageButton)findViewById(R.id.view_items)).setOnClickListener(this);
        ((ImageButton)findViewById(R.id.view_users)).setOnClickListener(this);
        //((Button)findViewById(R.id.my_ideas)).setOnClickListener(this);
        ((ImageButton)findViewById(R.id.logout)).setOnClickListener(this);
        button = (ImageButton)findViewById(R.id.view_users);


        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        HashMap<String, String> userr = db.getUserDetails();
        HashMap<String, String> idea = db.getIdeaDetails();

        String name = userr.get("name");
        String user = userr.get("email");
        //type = userr.get("user_type");
        String userID = userr.get("uid");
        String age = userr.get("age");

        //Log.d("USER ACTIVITY", "TYPE " + type);
        Log.d("USER ACTIVITY", "NAME " + name);
        Log.d("USER ACTIVITY", "USER " + user);
        Log.d("USER ACTIVITY", "ID " + userID);
        Log.d("USER ACTIVITY", "AGE " + age);

        creator = idea.get("creator");
        name = idea.get("name");
        actual_state = idea.get("actual_state");
        improved_state = idea.get("improved_state");
        advantages = idea.get("advantages");

        Log.d("USER ACTIVITY", "CREATOR   " + creator);
        Log.d("USER ACTIVITY", "name   " + name);
        Log.d("USER ACTIVITY", "actual_state   " + actual_state);
        Log.d("USER ACTIVITY", "improved_state   " + improved_state);
        Log.d("USER ACTIVITY", "advantages   " + advantages);

        //if(type.equals("0")) {
         //   button.setVisibility(View.GONE);
       //}
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.add_item:
                Intent intent = new Intent(this, FirstNewIdeaActivity.class);
                startActivity(intent);
                break;
            case R.id.view_items:
                btn = "A";
                Intent intent2 = new Intent(this, ListOfIdeas.class);
                startActivity(intent2);
                break;
            case R.id.view_users:
                Intent intent3 = new Intent(this, ListOfUsers.class);
                startActivity(intent3);
                break;
            /*case R.id.my_ideas:
                btn = "B";
                Intent intent4 = new Intent(this, ListOfMyIdeas.class);
                startActivity(intent4);
                break;*/
            case R.id.logout:
                logoutUser();
                break;
        }
    }

    public String getBtn(){return btn; }

    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(User.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Po stlaceni back button-u v tejto aktivite sa aplikacia ukonci
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}