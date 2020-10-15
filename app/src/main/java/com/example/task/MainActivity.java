package com.example.task;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.request.RequestOptions;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.security.PrivateKey;
import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
       private LoginButton login_button;
       private TextView p_email,p_name;
       private CircleImageView p_pic;
       private CallbackManager callbackManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login_button=findViewById(R.id.login_button);
        p_email=findViewById(R.id.p_email);
        p_name=findViewById(R.id.p_name);
        p_pic=findViewById(R.id.p_pic);
        callbackManager = CallbackManager.Factory.create();
        login_button.setReadPermissions(Arrays.asList("email","public_profile "));


        login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        callbackManager.onActivityResult(requestCode,resultCode,data);

        super.onActivityResult(requestCode, resultCode, data);
    }

 AccessTokenTracker accessTokenTracker=new AccessTokenTracker() {
     @Override
     protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
if (currentAccessToken==null){
p_name.setText(" ");
p_email.setText(" ");
    Toast.makeText(MainActivity.this, "Ivalid User", Toast.LENGTH_SHORT).show();

}
else {
    loadUser(currentAccessToken);

}
     }
 };
    private void loadUser(AccessToken newacesstoken)

    {

        GraphRequest request=    GraphRequest.newMeRequest(newacesstoken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
try {
String first_name =object.getString("first_name ");
    String email=object.getString("email");
    String last_name =object.getString("last_name");
    String id =object.getString("id");


  p_email.setText(email);
  p_name.setText(first_name);
    RequestOptions requestOptions=new RequestOptions();
    requestOptions.dontAnimate();


}catch (JSONException e){
    e.printStackTrace();

}
            }
        });
        Bundle paramerter=new Bundle();
        paramerter.putString("fields","first_name ,last_name,email ,id");
        request.setParameters(paramerter );
        request.executeAsync();



    }
}