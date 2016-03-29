package info.androidhive.loginandregistration.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import info.androidhive.loginandregistration.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Trieda reprezentujuca aktivitu pre zobrazenie zoznamu uloh. Prerobena verzia z KucharkaFragment
 */
public class ListOfIdeas extends Activity  implements ListOfIdeasFragment.OnNewItemAddedListener{

    //private EditText myEditText;
    private boolean mDualPane;
    public static Activity fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_ideas);

        if (findViewById(R.id.detail) == null)
            mDualPane = false; //Portrait ||
        else
            mDualPane = true; //Landscape ||

        fa = this;
    }


    public void onItemSelected(IdeaItem newItem) {
        if(mDualPane){
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.detail,NewItemFragment.newInstance(newItem));
            transaction.commit();
        }else {
            Intent intent = new Intent(this, IdeaDetail.class);
            intent.putExtra("uloha", newItem);
            startActivity(intent);
        }
    }
}