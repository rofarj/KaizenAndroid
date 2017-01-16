package eu.ceitgroup.AndroidKaizen.activity;

        import android.app.Activity;
        import android.app.FragmentTransaction;
        import android.content.Intent;
        import android.os.Bundle;

        import eu.ceitgroup.AndroidKaizen.R;
/**
 * Aktivita na zobrazenie podrobnosti o ulohe. Prerobena verzia z KucharkaFragment
 */
public class UserDetail extends Activity {
    public UserItem item;

    /**
     * Vyberie udaje z prijateho intentu a vytvori novy fragment.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        Intent intent = getIntent();
        item = (UserItem) intent.getParcelableExtra("user");

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container2, NewUserFragment.newInstance(item));
        transaction.commit();
    }
}
