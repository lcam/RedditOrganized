package lcam.redditorganized.base;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import lcam.redditorganized.models.OAuthToken;
import lcam.redditorganized.ui.auth.AuthActivity;
import lcam.redditorganized.ui.auth.AuthResource;

public abstract class BaseActivity extends DaggerAppCompatActivity {

    private static final String TAG = "BaseActivity";

    @Inject
    public SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subscribeObservers();
    }

    private void subscribeObservers(){
        //SessionManager obj has an auth observable, let's get it and subscribe to it
        Disposable disposable = sessionManager.getAuthTokenObservable().subscribe(
                tokenAuthResource -> observeAuthStatus(tokenAuthResource)
        );
    }

    private void observeAuthStatus(AuthResource<OAuthToken> tokenAuthResource){
        if(tokenAuthResource != null){
            switch (tokenAuthResource.status){
                case LOADING:{
                    Log.e(TAG, "observeAuthStatus: in BaseActivity LOADING");
                    break;
                }

                case AUTHENTICATED:{
                    Log.e(TAG, "observeAuthStatus: in BaseActivity AUTHENTICATED");
                    break;
                }

                case ERROR:{
                    Log.e(TAG, "observeAuthStatus: in BaseActivity ERROR");
                    break;
                }

                case NOT_AUTHENTICATED:{
                    Log.e(TAG, "observeAuthStatus: in BaseActivity NOT_AUTHENTICATED");
                    navLoginScreen(); //redirect to login screen if user gets logged out for whatever reason
                    break;
                }
            }
        }
    }

    private void navLoginScreen(){
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
        finish();
    }
}
