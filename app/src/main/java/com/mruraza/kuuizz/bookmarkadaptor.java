package com.mruraza.kuuizz;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class bookmarkadaptor extends RecyclerView.Adapter<bookmarkadaptor.Viewholder> {
  private List<modelquestion>list;

    public bookmarkadaptor(List<modelquestion> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public bookmarkadaptor.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmark_recycler_layout,parent,false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull bookmarkadaptor.Viewholder holder, int position) {
    holder.setdata(list.get(position).getQuestion(),list.get(position).getCorrectoptn(),position);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView question,answer;
        ImageView deleteqsn;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.textViewquestioninbookmark);
            answer= itemView.findViewById(R.id.textView2answerinbookmark);
            deleteqsn = itemView.findViewById(R.id.deletebottonofbookmark);
        }
        void  setdata(String question , String answer,int position)
        {
            this.question.setText(question);
            this.answer.setText(answer);
            deleteqsn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.remove(position);
                    notifyItemRemoved(position);
                }
            });
        }
    }
}
