package com.example.j_lds.firebasetest;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private EditText et_fn, et_ln, et_pwd, et_cpwd, et_birth;
    private String fn, ln, pwd, cpwd, birth;
    private Button btn_save;
    private User user;
    private long maxID = 0;

    private DatabaseReference mdatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this, "Firebase connection done", Toast.LENGTH_SHORT).show();

        et_fn = (EditText)findViewById(R.id.editText_firstName);
        et_ln = (EditText)findViewById(R.id.editText_lastName);
        et_pwd = (EditText)findViewById(R.id.editText_password);
        et_cpwd = (EditText)findViewById(R.id.editText_confirm_password);
        et_birth = (EditText)findViewById(R.id.editText_birthday);

        btn_save = (Button) findViewById(R.id.button_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test();
            }
        });

        mdatabaseReference = FirebaseDatabase.getInstance("https://test-jl010.firebaseio.com/").getReference().child("user");
        mdatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    maxID = (dataSnapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void test(){
        fn = et_fn.getText().toString().trim();
        ln = et_ln.getText().toString().trim();
        pwd = et_pwd.getText().toString().trim();
        cpwd = et_cpwd.getText().toString().trim();
        birth = et_birth.getText().toString().trim();

        if (pwd.equals(cpwd) && !pwd.equals("") && !cpwd.equals("")){
            user = new User();
            user.setFirstName(fn);
            user.setLastName(ln);
            user.setPassword(pwd);
            user.setBirth(birth);

            mdatabaseReference.child(String.valueOf(maxID+1)).setValue(user);
//            mdatabaseReference.push().setValue(user);
            Toast.makeText(this, "Information is saved !!!", Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(this, "The password id not the same", Toast.LENGTH_SHORT).show();
        }

    }
}
