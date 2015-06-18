package ua.pp.mmr.mymedicalrecord;

/**
 * Created by mitin on 18.06.15. MTaxi
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DiseaseListAdapter extends ArrayAdapter<Disease> {
    private final Context context;
    private final List<Disease> disease;

    public DiseaseListAdapter(Context context, List<Disease> names) {
        super(context, R.layout.disease_list_item, names);
        this.context = context;
        this.disease = names;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.disease_list_item, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.diseases_list_name);
        textView.setText(disease.get(position).getName());
        textView = (TextView) rowView.findViewById(R.id.diseases_list_annotation);
        textView.setText(disease.get(position).getAnnotation());
        textView = (TextView) rowView.findViewById(R.id.diseases_list_id);
        textView.setText(String.valueOf(disease.get(position).getId()));
        // Изменение иконки для Windows и iPhone
        return rowView;
    }
}