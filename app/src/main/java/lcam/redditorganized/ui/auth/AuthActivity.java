package lcam.redditorganized.ui.auth;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.RequestManager;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import lcam.redditorganized.R;
import lcam.redditorganized.models.OAuthToken;
import lcam.redditorganized.ui.main.MainActivity;
import lcam.redditorganized.util.Constants;
import lcam.redditorganized.viewmodels.ViewModelProviderFactory;

public class AuthActivity extends DaggerAppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AuthActivity";

    private AuthViewModel authViewModel;

    private ProgressBar progressBar;

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    @Inject
    Drawable logo;

    @Inject
    RequestManager requestManager; //Glide instance


    @Override
    protected void onResume() {
        super.onResume();

        if(getIntent()!=null && getIntent().getAction()!=null && getIntent().getAction().equals(Intent.ACTION_VIEW)) {
            Uri uri = getIntent().getData();
            if(uri.getQueryParameter("error") != null) {
                String error = uri.getQueryParameter("error");
                Log.e(TAG, "An error has occurred : " + error);
            } else {
                String state = uri.getQueryParameter("state");
                if(state.equals(Constants.STATE)) {
                    String code = uri.getQueryParameter("code");
                    //authViewModel.getAccessToken(code);
                    authViewModel.authenticateWithCode(code);
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        progressBar = findViewById(R.id.progress_bar);

        findViewById(R.id.login_button).setOnClickListener(this);

        authViewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(AuthViewModel.class);

        setLogo();

        subscribeObservers();
    }

    private void subscribeObservers(){
        authViewModel.observeAuthState().subscribe(new Observer<AuthResource<OAuthToken>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(AuthResource<OAuthToken> tokenAuthResource) {
                Log.e(TAG, "onNext: DID I COME HERE?");
                observeAuthStatus(tokenAuthResource);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void observeAuthStatus(AuthResource<OAuthToken> tokenAuthResource){
        if(tokenAuthResource != null){
            switch (tokenAuthResource.status){
                case LOADING:{
                    Log.e(TAG, "observeAuthStatus: LOADING");
                    showProgressBar(true);
                    break;
                }

                case AUTHENTICATED:{
                    Log.d(TAG, "observeAuthStatus: AUTHENTICATED: " + tokenAuthResource.data.getAccessToken());
                    showProgressBar(false);
                    onLoginSuccess();
                    break;
                }

                case ERROR:{
                    Log.e(TAG, "observeAuthStatus: ERROR");
                    showProgressBar(false);
                    Toast.makeText(AuthActivity.this, tokenAuthResource.message
                            + "\nWRONG LOGIN", Toast.LENGTH_SHORT).show();
                    break;
                }

                case NOT_AUTHENTICATED:{
                    Log.e(TAG, "observeAuthStatus: NOT AUTHENTICATED");
                    showProgressBar(false);
                    break;
                }
            }
        }
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
        switch (view.getId()) {

            case R.id.login_button:
                attemptLogin();
                break;
        }
    }

    public void attemptLogin() {
        String url = String.format(Constants.AUTH_URL, Constants.CLIENT_ID, Constants.STATE, Constants.REDIRECT_URI);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

        Log.d(TAG, "URL: " + url);

        startActivity(intent);
    }
}
