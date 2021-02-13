package com.example.pecs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class SignUp extends AppCompatActivity{

    EditText name , email,password,pass;
    Button Register,login;
    FirebaseAuth FAuth;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        name =findViewById(R.id.name);
        email =findViewById(R.id.email);
        password=findViewById(R.id.password);
        Register=findViewById(R.id.signup);
        pass=findViewById(R.id.password2);
         FAuth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressBar);
         login=findViewById(R.id.buttonlogin);


       login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignUp.this,MainActivity.class);
                startActivity(intent);

            }
        });



        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //extract data from form
                String Semail = email.getText().toString();
                String Sname = name.getText().toString();
                String Spassword = password.getText().toString();
                String Spass = pass.getText().toString();


                if(Sname.isEmpty()){
                    name.setError("اسم المستخدم مطلوب.");
                    return;
                }else if (Sname.length() >= 15) {
                    name.setError("اسم المستخدم طويل جدا يجب ان يكون اقل من 15 ");
                    return ;
                }
                if(Semail.isEmpty()){
                    email.setError("البريد الإلكتروني مطلوب.");
                    return;
                }else if(!Patterns.EMAIL_ADDRESS.matcher(Semail).matches()){
                    email.setError(" البريد الإلكتروني غير صحيح. ");
                    return;
                }
                if (Spassword.isEmpty()) {
                    password.setError("كلمة المرور مطلوبة.");
                    return;
                }
                if(!isValidPassword(Spassword)){
                    password.setError("يجب أن تتكون كلمة المرور من 8 أحرف على الأقل وتتضمن رقمًا وحرفًا صغيرًا وحرفًا كبيرًا وحرفًا خاصًا (مثل @، & ؛، #،؟).");
                    return;
                }



                if(Spass.isEmpty()){
                    pass.setError("أعد كتابة كلمة المرور .");
                    return;
                }
                if(!(Spassword.equals(Spass))){
                    pass.setError("كلمة المرور غير متطابقة. ");
                    return;
                }


                progressBar.setVisibility(View.VISIBLE);



                // now data is validate

                //Register user in the Fierbase

             FAuth.createUserWithEmailAndPassword(Semail,Spassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                 public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful()){

                           Toast.makeText(SignUp.this,"تم تسجيل المستخدم بنجاح " , Toast.LENGTH_SHORT).show();
                           Intent intent=new Intent(SignUp.this,HOME.class);
                           startActivity(intent);

                       }else{
                           //Toast.makeText(SignUp.this,"البريد الإلكتروني مسجل سابقا انقر على تسجيل الدخول "  +task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                         Toast.makeText(SignUp.this,"البريد الإلكتروني مسجل سابقا انقر على تسجيل الدخول "  , Toast.LENGTH_SHORT).show();
                           progressBar.setVisibility(View.GONE);
                       }

                    }

               });
           }
        });


            }
    public boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }

    }

