package info.androidhive.loginandregistration.activity;

/**
 * Created by Palo on 3/20/2016.
 */
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.InputStream;
import java.net.URL;

import info.androidhive.loginandregistration.R;
/**
 * Trieda reprezentujuca fragment v aktivite ListOfIdeas. Prerobena verzia z KucharkaFragment.
 */
public class NewUserFragment extends Fragment {

    private Button btn;
    UserItem item;
    ImageView img,img2;
    Bitmap bitmap, bitmap2;
    ProgressDialog pDialog;
    String file_before, file_before_without_dot, file_after, file_after_without_dot;

    public static NewUserFragment newInstance(UserItem user) {
        NewUserFragment fragment = new NewUserFragment();
        Bundle args = new Bundle();
        args.putParcelable("user", (android.os.Parcelable) user);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * prazdny konstruktor
     */
    public NewUserFragment() {}

    /**
     * Nacitanie dat z item (IdeaItem)
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            item = (UserItem) getArguments().getParcelable("user");
        }
    }

    /**
     * Vytvorenie view prvku a ukončenie aktivit, ktore by sa ulozili do pozadia.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_in_list_fragment2, container, false);
        ((TextView) view.findViewById(R.id.nazovUlohy)).setText(item.getName());
        ((TextView) view.findViewById(R.id.kategoriaUlohy)).setText(item.getSurname());
        ((TextView) view.findViewById(R.id.datumUlohy)).setText(item.getUserName());
        ((TextView) view.findViewById(R.id.prioritaUlohy)).setText(item.getCompany());

        /*if (!item.getfileBefore().equals("") && !item.getFileAfter().equals("")) {
            file_before = item.getfileBefore();
            file_before_without_dot = file_before.substring(1);
            file_after = item.getFileAfter();
            file_after_without_dot = file_after.substring(1);

            Log.d("AAAAAA", "AAAAAA           " + file_after_without_dot);
            Log.d("BBBBBB", "BBBBBB           " + file_before_without_dot);
            img = (ImageView) view.findViewById(R.id.img);
            img2 = (ImageView) view.findViewById(R.id.img2);
            // new LoadImage().execute("http://zims.ceitgroup.eu/kaizen" + file_before_without_dot);
            Glide.with(this).load("http://zims.ceitgroup.eu/kaizen" + file_before_without_dot).into(img);
            Glide.with(this).load("http://zims.ceitgroup.eu/kaizen" + file_after_without_dot).into(img2);
            // new LoadImage2().execute("http://zims.ceitgroup.eu/kaizen" + file_after_without_dot);
        }*/
        //btn = (Button) view.findViewById(R.id.vymazat);

        /*btn.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       //final MyDatabaseHelper db = new MyDatabaseHelper(getActivity());
                                       //db.deleteTitle(item.getTask());

                                       if (v.findViewById(R.id.detail) == null) {
                                           ListOfIdeas.fa.finish();
                                           getActivity().finish();
                                       }
                                       else{
                                           getActivity().finish();
                                       }

                                       Intent intent = new Intent(v.getContext(), ListOfIdeas.class);
                                       startActivity(intent);
                                   }
                               }
        );*/

        return view;
    }
    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //pDialog = new ProgressDialog(getContext());
            //pDialog.setMessage("Loading Image ....");
            //pDialog.show();

        }
        protected Bitmap doInBackground(String... args) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image) {

            if(image != null){
                img.setImageBitmap(image);
                //pDialog.dismiss();

            }else{

                //pDialog.dismiss();
                Toast.makeText(getContext(), "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();

            }
        }
    }

    private class LoadImage2 extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //pDialog = new ProgressDialog(getContext());
            //pDialog.setMessage("Loading Image ....");
            //pDialog.show();

        }
        protected Bitmap doInBackground(String... args) {
            try {
                bitmap2 = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap2;
        }

        protected void onPostExecute(Bitmap image) {

            if(image != null){
                img2.setImageBitmap(image);
                //pDialog.dismiss();

            }else{

                //pDialog.dismiss();
                Toast.makeText(getContext(), "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();

            }
        }
    }

}