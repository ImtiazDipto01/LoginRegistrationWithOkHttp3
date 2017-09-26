package com.nextdot.loginregistrationwithokhttp3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sakib on 9/26/2017.
 */

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_email)
    EditText loginEmail;
    @BindView(R.id.login_password)
    EditText loginPassword;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.register_here)
    Button registerHere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.login, R.id.register_here})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login:
                break;
            case R.id.register_here:
                registerOnClick();
                break;
        }
    }

    private void registerOnClick(){
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class) ;
        startActivity(intent);
    }
}
