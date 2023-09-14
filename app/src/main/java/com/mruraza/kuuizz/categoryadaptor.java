package com.mruraza.kuuizz;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class categoryadaptor extends RecyclerView.Adapter<categoryadaptor.ViewHolder> {
    public categoryadaptor(List<category_model> category_modelList) {
        this.category_modelList = category_modelList;
    }

    List<category_model> category_modelList;
    @NonNull
    @Override
    public categoryadaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_recycler,parent,false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull categoryadaptor.ViewHolder holder, int position) {
        holder.setdata(category_modelList.get(position).getUrl(),category_modelList.get(position).getName(),category_modelList.get(position).getSets());


    }

    @Override
    public int getItemCount() {
        return category_modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView profile;
        TextView title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profile = itemView.findViewById(R.id.profile_image);
            title = itemView.findViewById(R.id.topic);

        }
        private void setdata(String url,String title,int sets)
        {
            Glide.with(itemView.getContext()).load(url).into(profile);
            this.title.setText(title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(),question_sets.class);
                    intent.putExtra("title",title);
                    intent.putExtra("sets",sets);
                    itemView.getContext().startActivity(intent);
                }
            });
        }

    }
}
