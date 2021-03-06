package eu.ceitgroup.AndroidKaizen.activity;

/**
 * Created by Palo on 3/20/2016.
 * Updated by JR
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;
import eu.ceitgroup.AndroidKaizen.R;

/**
 * Tato trieda vytvori zoznam uloh vo view prvku. Prerobena verzia z cvicenia (KucharkaFragment).
 */
public class IdeaMyAdapter extends ArrayAdapter<IdeaItem> {
    Context appContext;
    int resource;
    List<IdeaItem> myList;


    public IdeaMyAdapter(Context context,int resource, List<IdeaItem> items) {
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
            v = inflater.inflate(R.layout. todolist_item, null);
            viewHolder = new ViewHolder();

            viewHolder.short_name = (TextView) v.findViewById(R.id.textview_define_problem_tittle);
            viewHolder.actual_state = (TextView) v.findViewById(R.id.date);
            viewHolder.imroved_state = (TextView) v.findViewById(R.id.surname);
            v.setTag(viewHolder);
        } else
        {
            viewHolder = (ViewHolder) v.getTag();
        }


        viewHolder.short_name.setText(myList.get(position).getName());

        viewHolder.actual_state.setText(myList.get(position).getTime());

        viewHolder.imroved_state.setText(myList.get(position).getCreator());

        return v;
    }
}

