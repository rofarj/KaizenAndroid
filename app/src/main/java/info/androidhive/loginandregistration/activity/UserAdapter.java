package info.androidhive.loginandregistration.activity;

/**
 * Created by Palo on 3/20/2016.
 */

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;
import info.androidhive.loginandregistration.R;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Tato trieda vytvori zoznam uloh vo view prvku. Prerobena verzia z cvicenia (KucharkaFragment).
 */
public class UserAdapter extends ArrayAdapter<UserItem> {
    Context appContext;
    int resource;
    List<UserItem> myList;


    public UserAdapter(Context context,int resource, List<UserItem> items) {
        super(context, resource, items);
        this.resource = resource;
        this.appContext = context;
        myList = items;
    }

    /**
     * Trieda na uchovavavie instancii.
     */
    private class ViewHolder {
        TextView short_name;
        TextView actual_state;
        TextView imroved_state;
    }

    /**
     * Vytvorenie view prvku. Okrem nazvov nastavi aj farbu.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        ViewHolder viewHolder;

        if (v == null)
        {
            LayoutInflater inflater = (LayoutInflater) appContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.user_item, null);
            viewHolder = new ViewHolder();

            viewHolder.short_name = (TextView) v.findViewById(R.id.name);
            viewHolder.actual_state = (TextView) v.findViewById(R.id.surname);
            viewHolder.imroved_state = (TextView) v.findViewById(R.id.date);
            v.setTag(viewHolder);
        } else
        {
            viewHolder = (ViewHolder) v.getTag();
        }


        viewHolder.short_name.setText(myList.get(position).getName());

        viewHolder.actual_state.setText(myList.get(position).getSurname());

        viewHolder.imroved_state.setText(myList.get(position).getCompany());


        return v;
    }
}

