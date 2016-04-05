package info.androidhive.loginandregistration.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);



        ((Button)findViewById(R.id.add_item)).setOnClickListener(this);
        ((Button)findViewById(R.id.view_items)).setOnClickListener(this);
        ((Button)findViewById(R.id.view_users)).setOnClickListener(this);
        ((Button)findViewById(R.id.my_ideas)).setOnClickListener(this);
        ((Button)findViewById(R.id.logout)).setOnClickListener(this);
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.add_item:
                Intent intent = new Intent(this, Facina.class);
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
            case R.id.my_ideas:
                btn = "B";
                Intent intent4 = new Intent(this, ListOfMyIdeas.class);
                startActivity(intent4);
                break;
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