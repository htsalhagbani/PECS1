package com.example.pecs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HOME extends AppCompatActivity {
Button logout,library,advice,p1,p2,p3,p4,p5,p6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h_o_m_e);
        logout=findViewById(R.id.logout);
        library=findViewById(R.id.library);
        advice=findViewById(R.id.advice);
        p1=findViewById(R.id.phase1);
        p2=findViewById(R.id.phase2);
        p3=findViewById(R.id.phase3);
        p4=findViewById(R.id.phase4);
        p5=findViewById(R.id.phase5);
        p6=findViewById(R.id.phase6);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences= getSharedPreferences("checkbox",MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("remember","false");
                editor.apply();
                   finish();
            }
        });
        library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HOME.this,Library.class);
                startActivity(intent);
            }
        });

       advice.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent=new Intent(HOME.this,help.class);
               startActivity(intent);
           }
       });
p1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(HOME.this,phase1.class);
        startActivity(intent);
    }
});
p2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(HOME.this,activity_phase2.class);
        startActivity(intent);
    }
});

p3.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(HOME.this,activity_phase3.class);
        startActivity(intent);
    }
});

p4.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(HOME.this,activity_phase4.class);
        startActivity(intent);
    }
});
p5.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(HOME.this,activity_phase5.class);
        startActivity(intent);
    }
});

p6.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(HOME.this,activity_phase6.class);
        startActivity(intent);
    }
});
    }
}