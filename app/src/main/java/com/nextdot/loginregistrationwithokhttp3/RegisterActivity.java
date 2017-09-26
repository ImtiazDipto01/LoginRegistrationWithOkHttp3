package com.nextdot.loginregistrationwithokhttp3;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by sakib on 9/26/2017.
 */

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.reg_name)
    EditText regName;
    @BindView(R.id.reg_email)
    EditText regEmail;
    @BindView(R.id.reg_pass)
    EditText regPass;
    @BindView(R.id.register)
    Button register;
    @BindView(R.id.login_here)
    Button loginHere;

    String email_string, pass_string, name_string ;
    String url = "http://programmerimtiaz.000webhostapp.com/register.php" ;
    ProgressDialog progressDialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.register, R.id.login_here})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.register:
                completeRegistration();
                break;
            case R.id.login_here:
                break;
        }
    }

    private void completeRegistration(){

        email_string = regEmail.getText().toString() ;
        pass_string = regPass.getText().toString() ;
        name_string = regName.getText().toString() ;

        OkHttpClient register_client = new OkHttpClient() ;
        RequestBody postBody = new FormBody.Builder()
                .add("name", name_string)
                .add("email", email_string)
                .add("password", pass_string)
                .build() ;

        Request request = new Request.Builder().url(url).post(postBody).build() ;
        setProgressDialouge();
        register_client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("++CONNECTION++", "ashe nai !!") ;
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                progressDialog.dismiss();
                Log.d("++CONNECTION++", "asche!!");
                String json_string = response.body().string() ;

                try {
                    JSONObject jsonObject = new JSONObject(json_string.substring(json_string.indexOf("{"), json_string.lastIndexOf("}") + 1)) ;
                    JSONArray server_res_array = jsonObject.getJSONArray("server_response") ;
                    JSONObject main_obj = server_res_array.getJSONObject(0) ;
                    final String code_string = main_obj.getString("code") ;
                    final String message_string = main_obj.getString("message") ;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(code_string.equals("reg_true")){
                                Toast.makeText(RegisterActivity.this, message_string, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setProgressDialouge(){

        progressDialog = new ProgressDialog(RegisterActivity.this) ;
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Connecting to server...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
}
