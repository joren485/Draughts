package com.example.marco.customadaptertest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

/**
 * @author Marco Hernandez
 * Een  simpele arrayadapter for Name o
 */
public class NameAdapter extends ArrayAdapter<Name> {

    public NameAdapter(Context context, List<Name> objects) {
        super(context, 0, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Name name = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_name, parent, false);
        }

        TextView tvFirstName = (TextView) convertView.findViewById(R.id.tvFirstName);
        TextView tvSurname   = (TextView) convertView.findViewById(R.id.tvSurname);

        tvFirstName.setText(String.format("%s%s", "First name: ",name.firstName));
        tvSurname.setText(String.format("%s%s", "Surname: ",name.surname));

        return convertView;
    }
}
