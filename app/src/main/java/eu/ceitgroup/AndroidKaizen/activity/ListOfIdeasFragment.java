package eu.ceitgroup.AndroidKaizen.activity;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import eu.ceitgroup.AndroidKaizen.app.AppConfig;
import eu.ceitgroup.AndroidKaizen.app.AppController;
import eu.ceitgroup.AndroidKaizen.helper.SQLiteHandler;

/**
 * Trieda reprezentujuca fragment v aktivite ListOfIdeas. Prerobena verzia z KucharkaFragment.
 */
public class ListOfIdeasFragment extends ListFragment {
    public OnNewItemAddedListener onNewItemAddedListener;
    private SQLiteHandler db;
    List<IdeaItem> ideaItems;
    private static final String TAG = ListOfIdeasFragment.class.getSimpleName();
    //MainActivity user_button;
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
        ideaItems = new ArrayList<IdeaItem>();
        HashMap<String, String> userr = db.getUserDetails();
//        MainActivity activity = (MainActivity)getActivity();
//        String data_from_User_activity = activity.getBtn();

        String name = userr.get("name");
        String userEmail = userr.get("email");
        String userType = userr.get("user_type");
        String userID = userr.get("id");
        Log.d(TAG, "LIST FRAGMENT USER_TYPE " + userType);
        Log.d(TAG, "LIST FRAGMENT USER_ID " + userID);
        dataRequest(userEmail, userType);
    }


    private void dataRequest(final String userEmail, final String userType  ) {
        final String short_name;
        final String actual_state;
        final String improved_state;
        final String advantages;
        final String costs;
        //final String phone;
        //final String age;

        // Tag used to cancel the request
        String tag_string_req = "req_register";

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_VIEW, new Response.Listener<String>() {
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
                        for(int j=0;j<length;++j) {
                            JSONObject first_idea = idea.getJSONObject("'" + (length-j-1) + "'");
                            String id = first_idea.getString("id");
                            int _id = Integer.parseInt(id);
                            String short_name = first_idea.getString("short_name");
                            String act_state = first_idea.getString("actual_state");
                            String impState = first_idea.getString("improved_state");
                            String adv = first_idea.getString("advantages");
                            String cos = first_idea.getString("costs");
                            String time = first_idea.getString("time");
                            String creator = first_idea.getString("proposalAuthor");
                            String file_before = first_idea.getString("file_before");
                            String file_after = first_idea.getString("file_after");
                            String priority = first_idea.getString("priority");
                            String receiver = first_idea.getString("receiver");

                            Log.d("List Of ideas - ","length                " +length);
                            Log.d("List Of ideas - ","length2                " +length2);
                            Log.d("List Of ideas - ","idea order                " +j);
                            Log.d("List Of ideas - ","id                  " +id);
                            Log.d("List Of ideas - ","name                  " +short_name);
                            Log.d("List Of ideas - A","author                  " +creator);
                            IdeaItem ideaitem = new IdeaItem();

                            Log.d(TAG, "Fragment view by  IdeaAuthor: "+ creator);
                            Log.d(TAG, "Fragment view by  Logged MainActivity: "+ userEmail);
                            Log.d(TAG, "Fragment view by  Logged UserType: "+ userType);



                            //if ( userEmail.equals(proposalAuthor)) {
                                Log.d(TAG, "Fragment view  Comparison OK: " + (userEmail == creator));
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
                           // }
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
            onNewItemAddedListener = (OnNewItemAddedListener) activity;
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
    public interface OnNewItemAddedListener {
        // TODO: Update argument type and name
        public void onItemSelected(IdeaItem recept);
    }

}