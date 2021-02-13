package com.example.pecs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {
 Button button,button1,button3;
EditText mEmail,mPass;
ProgressBar progressBar;
FirebaseAuth FAuth;
CheckBox Remember;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       button=(Button)findViewById(R.id.signup);
        mEmail=findViewById(R.id.Email);
        mPass=findViewById(R.id.pass);
        button1=findViewById(R.id.login);
        FAuth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressBar);
        button3=findViewById(R.id.buttonpass);
        Remember=findViewById(R.id.Remember);



       button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,SignUp.class);
                startActivity(intent);
            }
        });



        SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
        String checkbox= preferences.getString("remember","");
        if(checkbox.equals("true")){
            Intent intent=new Intent(MainActivity.this,HOME.class);
            startActivity(intent);
        }else if(checkbox.equals("false")){
            //Toast.makeText(MainActivity.this, "الرجاء تسجيل الدخول " , Toast.LENGTH_SHORT).show();
        }
           //login button .
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=mEmail.getText().toString().trim();
                String pass=mPass.getText().toString().trim();

                if(email.isEmpty()){
                    mEmail.setError( "البريد الإلكتروني مطلوب.");
                    return;
                }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    mEmail.setError(" البريد الإلكتروني غير صحيح. ");
                    return;
                }
                if (pass.isEmpty()) {
                    mPass.setError("كلمة المرور مطلوبة .");
                    return;
                }
                if(!isValidPassword(pass)){
                   mPass.setError("يجب أن تتكون كلمة المرور من 8 أحرف على الأقل وتتضمن رقمًا وحرفًا صغيرًا وحرفًا كبيرًا وحرفًا خاصًا (مثل @، & ؛، #،؟).");
                    return;
                }


                progressBar.setVisibility(View.VISIBLE);

                //authentication the user



                FAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this,"تم تسجيل الدخول بنجاح " , Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(MainActivity.this,HOME.class);
                            startActivity(intent);


                        }else{

                           // Toast.makeText(MainActivity.this,"كلمة المرور غير صحيحة او المستخدم غير مسجل "  +task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            Toast.makeText(MainActivity.this,"كلمة المرور غير صحيحة او البريد الإلكتروني غير صحيح " , Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });
            }

        });

        Remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton CompoundButton, boolean b){
                if(CompoundButton.isChecked()){
                    SharedPreferences preferences= getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember","true");
                    editor.apply();
                   // Toast.makeText(MainActivity.this,"Checked",Toast.LENGTH_SHORT).show();
                }else if(!CompoundButton.isChecked()){
                    SharedPreferences preferences= getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember","false");
                    editor.apply();
                   // Toast.makeText(MainActivity.this,"Not Checked",Toast.LENGTH_SHORT).show();
                }
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText resetMail=new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("إعادة تعيين كلمة المرور؟");
                passwordResetDialog.setMessage("أدخل البريد الإلكتروني الخاص بك لإستقبال رابط إعادة تعيين كلمة المرور. ");
                passwordResetDialog.setView(resetMail);
                passwordResetDialog.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                   // extract email and send reset link

                        String mail=resetMail.getText().toString();
                        FAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                             Toast.makeText(MainActivity.this,"رابط إعادة تعين كلمة المرور لقد تم إرساله على البريد الإلكتروني ",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this,  "خطأ في إرسال رابط إعادة تعين كلمة المرور على البريد الإلكتروني " + e.getMessage() ,Toast.LENGTH_SHORT ).show();
                            }
                        });
                    }
                });
                passwordResetDialog.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //CLOSE The dialog
                    }
                });
                passwordResetDialog.create().show();


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