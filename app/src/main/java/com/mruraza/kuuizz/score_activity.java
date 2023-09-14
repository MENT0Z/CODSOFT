package com.mruraza.kuuizz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidplot.pie.PieChart;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;

public class score_activity extends AppCompatActivity {
    PieChart pieChart;
    Segment s1,s2;
    float correctans,incorrect,totalll;
    TextView scoreee , commentsss;
   Button btn,btn_share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        scoreee = findViewById(R.id.scorecountt);
        commentsss = findViewById(R.id.commentsonres);
        btn = findViewById(R.id.donebtn);
        btn_share = findViewById(R.id.shareinevaluationpage);





        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    finish();
            }
        });
        correctans = getIntent().getIntExtra("correctans",0);
        totalll = getIntent().getIntExtra("totalqsn",1);
        incorrect = totalll-correctans;

        float passpercentage = 50 ;

        float percentagee = (correctans/totalll)*100;
        String statusss ;

        if(passpercentage<=percentagee)
        {
            scoreee.setTextColor(Color.parseColor("#09EF12"));
            scoreee.setText(" "+(int)correctans + " / " + (int)totalll+"\nPassed");
            //Toast.makeText(this, "Congrats\nyou got passed by "+percentagee+"%", Toast.LENGTH_SHORT).show();
            commentsss.setText("     Congrats\nyou got "+percentagee+"%");
            statusss = "Passed";

        }else{
            scoreee.setTextColor(Color.parseColor("#FF0000"));
            scoreee.setText(" "+(int)correctans + " / " + (int)totalll+"\nFailed");
          //  Toast.makeText(this, "you got "+percentagee+"% \n don't give up", Toast.LENGTH_SHORT).show();
            commentsss.setText(" you got "+percentagee+"% \n don't give up");
            statusss = "Failed";

        }
        pieChart = findViewById(R.id.piee);
        s1 = new Segment("",correctans);
        s2 = new Segment("",incorrect);

        SegmentFormatter sf1 = new SegmentFormatter(Color.GREEN);
        SegmentFormatter sf2 = new SegmentFormatter(Color.RED);
        pieChart.addSegment(s1,sf1);
        pieChart.addSegment(s2,sf2);


        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bodyy = "\nStatus = " + statusss + "\n\nScore = " + (int)correctans + " out of "
                        +(int)totalll + "\nPercentage = " + percentagee;

                Intent shareintent = new Intent(Intent.ACTION_SEND);
                shareintent.setType("plain/text");
                shareintent.putExtra(Intent.EXTRA_SUBJECT,"*****View My Score******");
                shareintent.putExtra(Intent.EXTRA_TEXT,bodyy);
                startActivity(Intent.createChooser(shareintent,"Share Via"));
            }
        });
    }
}

//
//        btn_share.setOnClickListener(new View.OnClickListener() {
//@Override
//public void onClick(View v) {
//        String bodyy = "\nStatus = " + statusss + "\n\nScore = " + (int)correctans + " out of"
//        +(int)totalll + "\nPercentage = " + percentagee;
//
//        Intent shareintent = new Intent(Intent.ACTION_SEND);
//        shareintent.setType("plain/text");
//        shareintent.putExtra(Intent.EXTRA_SUBJECT,"*****View My Score******");
//        shareintent.putExtra(Intent.EXTRA_TEXT,bodyy);
//        }
//        });

  /*share.setOnClickListener(new View.OnClickListener() {
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
        });*/