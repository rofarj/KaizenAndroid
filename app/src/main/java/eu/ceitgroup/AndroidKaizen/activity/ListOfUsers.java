package eu.ceitgroup.AndroidKaizen.activity;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import eu.ceitgroup.AndroidKaizen.R;

/**
 * Trieda reprezentujuca aktivitu pre zobrazenie zoznamu uloh. Prerobena verzia z KucharkaFragment
 */
public class ListOfUsers extends Activity  implements ListOfUsersFragment.OnNewItemAddedListener2{

    //private EditText myEditText;
    private boolean mDualPane;
    public static Activity fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_users);

        if (findViewById(R.id.detail) == null)
            mDualPane = false; //Portrait ||
        else
            mDualPane = true; //Landscape ||

        fa = this;
    }


    public void onItemSelected(UserItem newItem) {
        if(mDualPane){
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.detail,NewUserFragment.newInstance(newItem));
            transaction.commit();
        }else {
            Intent intent = new Intent(this, UserDetail.class);
            intent.putExtra("user", newItem);
            startActivity(intent);
        }
    }
}