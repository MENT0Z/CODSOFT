package com.mruraza.kuuizz;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Gridadaptor extends BaseAdapter {
    private  int sets =0;
    String categoryy;
    public Gridadaptor(int sets,String categoryy) {
        this.sets = sets;
        this.categoryy = categoryy;
    }

    @Override
    public int getCount() {
        return sets;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if(convertView == null)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.set_layout_number,parent,false);
        }else {
            view =convertView;
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent.getContext(),questions.class);
                intent.putExtra("category",categoryy);
                intent.putExtra("setno",position+1);
                parent.getContext().startActivity(intent);
            }
        });
        ((TextView) view.findViewById(R.id.setsnumbers)).setText(String.valueOf(position+1));
        return view;
    }
}
