package info.androidhive.loginandregistration.activity;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import info.androidhive.loginandregistration.R;
/**
 * Aktivita na zobrazenie podrobnosti o ulohe. Prerobena verzia z KucharkaFragment
 */
public class MyIdeaDetail extends Activity {
    public IdeaItem item;

    /**
     * Vyberie udaje z prijateho intentu a vytvori novy fragment.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea_detail);

        Intent intent = getIntent();
        item = (IdeaItem) intent.getParcelableExtra("idea");

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, NewItemFragment.newInstance(item));
        transaction.commit();
    }
}
