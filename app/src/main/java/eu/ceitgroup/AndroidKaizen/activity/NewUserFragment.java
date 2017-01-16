package eu.ceitgroup.AndroidKaizen.activity;

/**
 * Created by Palo on 3/20/2016.
 */
import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import eu.ceitgroup.AndroidKaizen.app.AppConfig;
import eu.ceitgroup.AndroidKaizen.R;
import eu.ceitgroup.AndroidKaizen.helper.SQLiteHandler;

/**
 * Trieda reprezentujuca fragment v aktivite ListOfIdeas. Prerobena verzia z KucharkaFragment.
 */
public class NewUserFragment extends Fragment {
    private SQLiteHandler db;
    private Button btn;
    UserItem item;
    ImageView img,img2;
    Bitmap bitmap, bitmap2;
    ProgressDialog pDialog;
    String file_before, file_before_without_dot, file_after, file_after_without_dot;
    private static final String TAG = NewUserFragment.class.getSimpleName();
    String user;

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
        ((TextView) view.findViewById(R.id.priezvisko)).setText(item.getSurname());
        ((TextView) view.findViewById(R.id.telephone)).setText(item.getPhone());
        ((TextView) view.findViewById(R.id.comp)).setText(item.getCompany());
        //((TextView) view.findViewById(R.id.reg)).setText(item.getDate());
        //String date = item.getDate();
        //String phone = item.getPhone();

        //Log.d("QQQQQQQQQQ", "QQQQQQQQQQQ " + date);
        //Log.d("QQQQQQQQQQ", "QQQQQQQQQQQ " + phone);

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

        user = item.getUserName();

        /*btn = (Button) view.findViewById(R.id.vymazat);

        btn.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       //final MyDatabaseHelper db = new MyDatabaseHelper(getActivity());
                                       //db.deleteTitle(item.getTask());

                                       dataRequest(user);*/
/*
                                       if (v.findViewById(R.id.detail) == null) {
                                           ListOfIdeas.fa.finish();
                                           getActivity().finish();
                                       } else {
                                           getActivity().finish();
                                       }
*/
                                       //Intent intent = new Intent(v.getContext(), ListOfIdeas.class);
                                       //startActivity(intent);
                                   //}
                               //}
        //);

        return view;
    }

    private void dataRequest(final String id) {
        final String short_name;
        final String actual_state;
        final String improved_state;
        final String advantages;
        final String costs;
        //final String phone;
        //final String age;

        // Tag used to cancel the request
        String tag_string_req = "req_register";

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.DELETE_USER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // MainActivity successfully stored in MySQL
                        // Now store the user in sqlite
                        String length2 = jObj.getString("length");
                        int length = Integer.parseInt(length2);

                        JSONObject[] ideas = new JSONObject[length];

                        JSONObject idea = jObj.getJSONObject("idea");

                        //for(int j=0;j<length;j++) {
                        //JSONObject first_idea = idea.getJSONObject("error");
                        /*String id = first_idea.getString("id");
                        int _id = Integer.parseInt(id);
                        String short_name = first_idea.getString("short_name");
                        String act_state = first_idea.getString("actual_state");
                        String impState = first_idea.getString("improved_state");
                        String adv = first_idea.getString("advantages");
                        String cos = first_idea.getString("costs");
                        String time = first_idea.getString("time");
                        String proposalAuthor = first_idea.getString("proposalAuthor");
                        String file_before = first_idea.getString("file_before");
                        String file_after = first_idea.getString("file_after");
                        String priority = first_idea.getString("priority");
                        String proposalResponsiblePerson = first_idea.getString("proposalResponsiblePerson");*/


                        //IdeaItem ideaitem = new IdeaItem();
/*
                            Log.d("AAAAAAAAAAAA","AAAAA                  " +short_name);
                            Log.d("AAAAAAAAAAAA","AAAAA                  " +act_state);
                            Log.d("AAAAAAAAAAAA","AAAAA                  " +impState);
                            Log.d("AAAAAAAAAAAA","AAAAA                  " +adv);
                            Log.d("AAAAAAAAAAAA","AAAAA                  " +cos);

                            ideaitem.set_id(_id);
                            ideaitem.setName(short_name);
                            ideaitem.setActState(act_state);
                            ideaitem.setImpState(impState);
                            ideaitem.setAdv(adv);
                            ideaitem.setCosts(cos);
                            ideaitem.setTime(time);
                            ideaitem.setCreator(proposalAuthor);
                            ideaitem.setFileBefore(file_before);
                            ideaitem.setFileAfter(file_after);
                            ideaitem.setPriority(priority);
                            ideaitem.setReceiver(proposalResponsiblePerson);

                            ideaItems.add(ideaitem);*/
                        //}
                       /* String d =
                        Log.d("AAAAAAAAAAAA","AAAAA                  " +short_name);
                        Log.d("AAAAAAAAAAAA","AAAAA                  " +act_state);
                        Log.d("AAAAAAAAAAAA","AAAAA                  " +impState);*/


                        //setListAdapter(new IdeaAdapter(getActivity(), 0, ideaItems));
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        //Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                        Log.d(TAG, "REGISTRATION ERROR: " + errorMsg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                //Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                //hideDialog();
            }
        }) {

                @Override
                protected Map<String, String> getParams() {
                    // Posting params to register url
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("email", id);
                    /*params.put("actual_state", actual_state);
                    params.put("improved_state", improved_state);
                    params.put("advantages",advantages);
                    params.put("costs",costs);*/
                    return params;
                }

        };
    }
}