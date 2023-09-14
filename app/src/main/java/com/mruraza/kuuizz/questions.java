package com.mruraza.kuuizz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.reflect.TypeToken;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class questions extends AppCompatActivity {

        TextView question , question_number_count;
        ImageView bookmark,share;
        Button next,previous;
        int count =0;
        LinearLayout optioncontainer;
         List<modelquestion>list;
         private  int position =0;
         int score =0;
         int setno;
         String category;
         int correctans;
         int matchedquestionposition;
         private static final String FILE_NAME = "KUIZZ";
    private static final String KEY_NAME = "QUESTIONS";
         SharedPreferences preferences;
         SharedPreferences.Editor editor;
         Gson gson;
         List<modelquestion>bookmarlist;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);

        Dialog loadingdialog;
        loadingdialog = new Dialog(this);
        loadingdialog.setContentView(R.layout.progresslayout);
        loadingdialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.rrroundd_only_for_btn));
        loadingdialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingdialog.setCancelable(false);


        setContentView(R.layout.activity_questions);
        Toolbar toolbar = findViewById(R.id.toolbar_qsn);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        preferences = getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
        gson = new Gson();
        getBookmarks();



        question = findViewById(R.id.textviewforqsn);
        question_number_count = findViewById(R.id.question_numberrr);
        bookmark = findViewById(R.id.bookmakkk);
        share = findViewById(R.id.shareeee);
        next = findViewById(R.id.nextbtn);
        previous = findViewById(R.id.prevoiusbtn);
        optioncontainer = findViewById(R.id.optioncontainer);
        list = new ArrayList<>() ;

        setno = getIntent().getIntExtra("setno",1);
        category = getIntent().getStringExtra("category");


        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(matchmodel())
                {
                    bookmarlist.remove(matchedquestionposition);
                    bookmark.setImageDrawable(getDrawable(R.drawable.baseline_bookmark_border_24));
                }else {
                    bookmarlist.add(list.get(position));
                    bookmark.setImageDrawable(getDrawable(R.drawable.baseline_bookmarks));
                }
            }
        });

        loadingdialog.show();
        ref.child("sets").child(category).child("questions").orderByChild("setno").equalTo(setno).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    list.add(dataSnapshot.getValue(modelquestion.class));
                }
                if(list.size()>0){
                    playAnim(question,0,list.get(position).getQuestion());

                    for(int i=0;i<4;i++)
                    {
                        optioncontainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // checkanswer(((Button)v).getText().toString());
                                checkanswer((Button) v);
                            }
                        });


                    }
                    next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            next.setEnabled(false);
                            enableopth(true);
                            position++;
                            if(position >= list.size()-1)
                            {
                                next.setText("Submit");
                            }
                            if(position==list.size())
                            {
                                //showing score
                               // Toast.makeText(questions.this, "you reached to the final of the question", Toast.LENGTH_SHORT).show();
                                loadingdialog.show();
                                Intent intent = new Intent(questions.this,score_activity.class);
                                intent.putExtra("correctans",correctans);
                                intent.putExtra("totalqsn",list.size());
                                startActivity(intent);
                               finish();
                                return;
                            }

                            count = 0;
                            playAnim(question, 0, list.get(position).getQuestion());
                            previous.setEnabled(true);

                        }


                    });
                    previous.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(position>=1) {
                                previous.setEnabled(false);
                                enableopth(true);
                                position--;
                                count = 0;
                                playAnim(question, 0, list.get(position).getQuestion());

                            }else {
                                Toast.makeText(questions.this, "Already in the first question!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    share.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String bodyy = list.get(position).getQuestion() + "\n\n" +
                                    list.get(position).getOption1() + "\n" +
                                    list.get(position).getOption2() + "\n" +
                                    list.get(position).getOption3() + "\n" +
                                    list.get(position).getOption4() ;

                            Intent shareintent = new Intent(Intent.ACTION_SEND);
                            shareintent.setType("plain/text");
                            shareintent.putExtra(Intent.EXTRA_SUBJECT,"  SAKNU HUNXCA VANYA YO QSN SOLVE HANNU");
                            shareintent.putExtra(Intent.EXTRA_TEXT,bodyy);
                            startActivity(Intent.createChooser(shareintent,"K bata Share Hanne?"));

                        }
                    });

                }else {
                    finish();
                    Toast.makeText(questions.this, "no questions", Toast.LENGTH_SHORT).show();
                }
                loadingdialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(questions.this,error.getMessage(), Toast.LENGTH_SHORT).show();
                loadingdialog.dismiss();
                finish();
            }
        });
        question_number_count.setText(position+1 + " / " +list.size());

    }

    @Override
    protected void onPause() {
        super.onPause();
        storebookmarks();
    }

    private  void playAnim(View view , int  value, String data){
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100)
                .setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(@NonNull Animator animation) {
                        if(value==0 && count<4){
                            String option="";
                            if(count==0){
                                option = list.get(position).getOption1();
                            } else if (count==1) {
                                option = list.get(position).getOption2();
                            }
                            else if (count==2) {
                                option = list.get(position).getOption3();
                            }
                            else if (count==3) {
                                option = list.get(position).getOption4();
                            }
                            playAnim(optioncontainer.getChildAt(count),0, option);
                            count++;
                        }
                    }

                    @Override
                    public void onAnimationEnd(@NonNull Animator animation) {
                        //data change

                        if(value==0){
                            try{
                                ((TextView)view).setText(data);
                                question_number_count.setText(position+1 + " / " +list.size());
                                if(matchmodel())
                                {

                                    bookmark.setImageDrawable(getDrawable(R.drawable.baseline_bookmarks));
                                }else {

                                    bookmark.setImageDrawable(getDrawable(R.drawable.baseline_bookmark_border_24));
                                }

                            }catch (ClassCastException ex)
                            {
                                ((Button) view).setText(data);
                            }
                            view.setTag(data);
                            playAnim(view,1,data);
                        }
                    }

                    @Override
                    public void onAnimationCancel(@NonNull Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(@NonNull Animator animation) {

                    }
                });

    }
    private  void checkanswer(Button selectedopth){
  enableopth(false);
  next.setEnabled(true);
  if(selectedopth.getText().toString().equals(list.get(position).getCorrectoptn())){

        selectedopth.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50")));
        score++;
        correctans++;
  }else {
      selectedopth.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff0000")));
        Button correctopth =(Button) optioncontainer.findViewWithTag(list.get(position).getCorrectoptn());
        correctopth.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50")));
  }
    }
    private  void enableopth(boolean enablee)
    {
        for(int i=0;i<4;i++)
        {
            optioncontainer.getChildAt(i).setEnabled(enablee);
            if(enablee)
            {
               optioncontainer.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#989898")));
              //  optioncontainer.getChildAt(i).setBackground();
            }
        }
    }

    void getBookmarks()
    {
        String json = preferences.getString(KEY_NAME,"");
        Type type = new TypeToken<List<modelquestion>>(){}.getType();
        bookmarlist = gson.fromJson(json,type);
        if(bookmarlist == null)
        {
            bookmarlist = new ArrayList<>();
        }
    }

    boolean matchmodel(){
        boolean matched = false;
        int i =0;
        for(modelquestion model : bookmarlist)
        {
            if(model.getQuestion().equals(list.get(position).getQuestion())&&
            model.getCorrectoptn().equals(list.get(position).getCorrectoptn())&&
            model.getSetno() == list.get(position).getSetno())
            {
                matched = true;
                matchedquestionposition = i;
            }
            i++;
        }
        return matched;
    }

void storebookmarks(){

        String json = gson.toJson(bookmarlist);
        editor.putString(KEY_NAME,json);
        editor.commit();
}
}