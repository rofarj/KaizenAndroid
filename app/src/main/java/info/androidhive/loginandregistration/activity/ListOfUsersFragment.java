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

/**
 * Trieda reprezentujuca fragment v aktivite ListOfIdeas. Prerobena verzia z KucharkaFragment.
 */
public class ListOfUsersFragment extends ListFragment {
    public OnNewItemAddedListener2 onNewItemAddedListener;
    private SQLiteHandler db;
    List<UserItem> userItems;
    private static final String TAG = ListOfUsersFragment.class.getSimpleName();

    /**
     * ziska instancie IdeaItem z databazy a nastavi ich do ListAdaptera
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //final MyDatabaseHelper db = new MyDatabaseHelper(getActivity());
        //todoItems = db.getAllItems();
        db = new SQLiteHandler(getActivity());
        //todoItems = db.getAllItems();
        userItems = new ArrayList<UserItem>();

        dataRequest();
    }


    private void dataRequest() {
        final String short_name;
        final String actual_state;
        final String improved_state;
        final String advantages;
        final String costs;
        //final String phone;
        //final String age;

        // Tag used to cancel the request
        String tag_string_req = "req_register";

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_USERS, new Response.Listener<String>() {
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

                        JSONObject user = jObj.getJSONObject("user");

                        for(int j=0;j<length;j++) {
                            JSONObject first_user = user.getJSONObject("'" + j + "'");
                            //String id = first_user.getString("id");
                            //int _id = Integer.parseInt(id);
                            String name = first_user.getString("name");
                            String surname = first_user.getString("surname");
                            String email = first_user.getString("user_email");
                            String company = first_user.getString("company");
                            String phone = first_user.getString("phone");
                            String date = first_user.getString("date");

                            UserItem useritem = new UserItem();

                            Log.d("AAAAAAAAAAAA","AAAAA                  " +phone);
                            Log.d("AAAAAAAAAAAA","AAAAA                  " +date);
 /*                         Log.d("AAAAAAAAAAAA","AAAAA                  " +impState);
                            Log.d("AAAAAAAAAAAA","AAAAA                  " +adv);
                            Log.d("AAAAAAAAAAAA","AAAAA                  " +cos);
*/
                            //useritem.set_id(_id);
                            useritem.setName(name);
                            useritem.setSurname(surname);
                            useritem.setUserName(email);
                            useritem.setCompany(company);
                            useritem.setPhone(phone);
                            useritem.setDate(date);

                            userItems.add(useritem);
                        }
                        //Log.d("AAAAAAAAAAAA","AAAAA                  " +phone);
                        //Log.d("AAAAAAAAAAAA","AAAAA                  " +date);
                       /* String d =
                        Log.d("AAAAAAAAAAAA","AAAAA                  " +short_name);
                        Log.d("AAAAAAAAAAAA","AAAAA                  " +act_state);
                        Log.d("AAAAAAAAAAAA","AAAAA                  " +impState);*/


                        setListAdapter(new UserAdapter(getActivity(), 0, userItems));
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

                /*@Override
                protected Map<String, String> getParams() {
                    // Posting params to register url
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("short_name", short_name);
                    params.put("actual_state", actual_state);
                    params.put("improved_state", improved_state);
                    params.put("advantages",advantages);
                    params.put("costs",costs);
                    return params;
                }*/

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
            onNewItemAddedListener = (OnNewItemAddedListener2) activity;
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
            onNewItemAddedListener.onItemSelected(userItems.get(position));
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
    public interface OnNewItemAddedListener2 {
        // TODO: Update argument type and name
        public void onItemSelected(UserItem recept);
    }

}