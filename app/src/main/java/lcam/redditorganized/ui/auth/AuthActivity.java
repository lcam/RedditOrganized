package lcam.redditorganized.ui.auth;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.RequestManager;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import lcam.redditorganized.R;
import lcam.redditorganized.models.User;
import lcam.redditorganized.ui.main.MainActivity;
import lcam.redditorganized.viewmodels.ViewModelProviderFactory;

public class AuthActivity extends DaggerAppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AuthActivity";

    private AuthViewModel authViewModel;

    private EditText userId;
    private ProgressBar progressBar;

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    @Inject
    Drawable logo;

    @Inject
    RequestManager requestManager; //Glide instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        userId = findViewById(R.id.user_id_input);
        progressBar = findViewById(R.id.progress_bar);

        findViewById(R.id.login_button).setOnClickListener(this);

        authViewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(AuthViewModel.class);

        setLogo();

        subscribeObservers();
    }

    private void subscribeObservers(){ //start observing the LiveData
        authViewModel.observeAuthState().observe(this, new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {
                if(userAuthResource != null){
                    switch (userAuthResource.status){

                        case LOADING:{
                            showProgressBar(true);
                            break;
                        }

                        case AUTHENTICATED:{
                            showProgressBar(false);
                            Log.d(TAG, "onChanged: LOGIN_SUCCESS: " + userAuthResource.data.getEmail());
                            onLoginSuccess();
                            break;
                        }

                        case ERROR:{
                            showProgressBar(false);
                            Toast.makeText(AuthActivity.this, userAuthResource.message
                                    + "\nWrong login!!!", Toast.LENGTH_SHORT).show();
                            break;
                        }

                        case NOT_AUTHENTICATED:{
                            showProgressBar(false);
                            break;
                        }
                    }
                }
            }
        });
    }

    //redirect to Main screen upon successful login
    private void onLoginSuccess(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void showProgressBar(boolean isVisible){
        if(isVisible){
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void setLogo(){
        requestManager
                .load(logo)
                .into((ImageView)findViewById(R.id.login_logo));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.login_button:{

                attemptLogin();
                break;
            }
        }
    }

    private void attemptLogin() {
        if(TextUtils.isEmpty(userId.getText().toString())){ //check if user id is null
            return;
        }
        //attempt to login
        authViewModel.authenticateWithId(Integer.parseInt(userId.getText().toString()));
    }
}
