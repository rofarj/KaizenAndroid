package info.androidhive.loginandregistration.activity;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.androidhive.loginandregistration.app.AppConfig;
import info.androidhive.loginandregistration.app.AppController;
import info.androidhive.loginandregistration.helper.SQLiteHandler;
import info.androidhive.loginandregistration.helper.SessionManager;

/**
 * Trieda reprezentujuca fragment v aktivite ListOfIdeas. Prerobena verzia z KucharkaFragment.
 */
public class ListOfMyIdeasFragment extends ListFragment {
    public OnNewItemAddedListener3 onNewItemAddedListener;
    private SQLiteHandler db;
    List<IdeaItem> ideaItems;
    private static final String TAG = ListOfIdeasFragment.class.getSimpleName();
    private SessionManager session;

    /**
     * ziska instancie IdeaItem z databazy a nastavi ich do ListAdaptera
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //todoItems = db.getAllItems();
        db = new SQLiteHandler(getActivity());
        //todoItems = db.getAllItems();
        ideaItems = new ArrayList<IdeaItem>();

        HashMap<String, String> userr = db.getUserDetails();

        String name = userr.get("name");
        String user = userr.get("email");
        String userType = userr.get("user_type");

        //String user = "rapac.pavol@centrum.sk";
        Log.d("AAAAAAAAAAAA","AAAAA                  " +user);
        dataRequest(user);

    }


    private void dataRequest(final String user) {
        final String short_name;
        String actual_state;
        String improved_state;
        String advantages;
        String costs;
        String phone;
        String age;

        // Tag used to cancel the request
        String tag_string_req = "req_register";

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_MY_IDEAS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                        String length2 = jObj.getString("length");
                        int length = Integer.parseInt(length2);

                        JSONObject[] ideas = new JSONObject[length];

                        JSONObject idea = jObj.getJSONObject("idea");

                        for(int j=0;j<length;j++) {
                            JSONObject first_idea = idea.getJSONObject("'" + j + "'");
                            String id = first_idea.getString("id");
                            int _id = Integer.parseInt(id);
                            String short_name = first_idea.getString("short_name");
                            String act_state = first_idea.getString("actual_state");
                            String impState = first_idea.getString("improved_state");
                            String adv = first_idea.getString("advantages");
                            String cos = first_idea.getString("costs");
                            String time = first_idea.getString("time");
                            String creator = first_idea.getString("creator");
                            String file_before = first_idea.getString("file_before");
                            String file_after = first_idea.getString("file_after");
                            String priority = first_idea.getString("priority");
                            String receiver = first_idea.getString("receiver");

                            IdeaItem ideaitem = new IdeaItem();

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
                            ideaitem.setCreator(creator);
                            ideaitem.setFileBefore(file_before);
                            ideaitem.setFileAfter(file_after);
                            ideaitem.setPriority(priority);
                            ideaitem.setReceiver(receiver);

                            ideaItems.add(ideaitem);
                        }
                       /* String d =
                        Log.d("AAAAAAAAAAAA","AAAAA                  " +short_name);
                        Log.d("AAAAAAAAAAAA","AAAAA                  " +act_state);
                        Log.d("AAAAAAAAAAAA","AAAAA                  " +impState);*/


                        setListAdapter(new IdeaAdapter(getActivity(), 0, ideaItems));
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
                    params.put("user", user);
                    //params.put("actual_state", actual_state);
                    //params.put("improved_state", improved_state);
                    //params.put("advantages",advantages);
                    //params.put("costs",costs);
                    return params;
                }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    /**
     * Otestovanie ci bolo implementovane rozhranie OnNewItemAddedListener
     *
     * @param activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            onNewItemAddedListener = (OnNewItemAddedListener3) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnNewItemAddedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onNewItemAddedListener = null;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);


        if (null != onNewItemAddedListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            onNewItemAddedListener.onItemSelected(ideaItems.get(position));
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnNewItemAddedListener3 {
        // TODO: Update argument type and name
        public void onItemSelected(IdeaItem recept);
    }

}