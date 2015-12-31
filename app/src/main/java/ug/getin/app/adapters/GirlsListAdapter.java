package ug.getin.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ug.getin.app.R;
import ug.getin.app.models.Girl;

/**
 * Created by abdussekalala on 12/31/15.
 */
public class GirlsListAdapter extends ArrayAdapter<Girl> {

    List<Girl> girls;
    Context context;

    public GirlsListAdapter(Context context, int resource,List<Girl> girls) {
        super(context, resource);
        this.girls = girls;
        this.context = context;


    }

    @Override
    public int getCount() {
        return girls.size();
    }


    @Override
    public Girl getItem(int position) {

        return girls.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.girl_list_item, null);
        }
        Girl girl = girls.get(position);

        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView dob = (TextView) convertView.findViewById(R.id.age);
        TextView marital_status = (TextView) convertView.findViewById(R.id.marital_status);
        TextView education_level = (TextView) convertView.findViewById(R.id.education_level);

        name.setText(girl.getName());

        dob.setText("Age: " + girl.getAge());
        marital_status.setText("Marital Status: " + girl.getMarital_status());
        education_level.setText("Education Level: " + girl.getEducation_level());




        return convertView;
    }
}
