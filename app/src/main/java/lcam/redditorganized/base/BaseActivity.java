package lcam.redditorganized.base;

import android.content.Intent;
import android.os.Bundle;

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
//        sessionManager.getAuthUser().observe(this, new Observer<AuthResource<OAuthToken>>() {
//            @Override
//            public void onChanged(AuthResource<OAuthToken> userAuthResource) {
//                if(userAuthResource != null){
//                    switch (userAuthResource.status){
//
//                        case LOADING:{
//                            break;
//                        }
//                        case AUTHENTICATED:{
//                            break;
//                        }
//                        case ERROR:{
//                            break;
//                        }
//                        case NOT_AUTHENTICATED:{
//                            break;
//                        }
//                    }
//                }
//            }
//        });

        //SessionManager obj has an auth observable, let's get it and subscribe to it
        sessionManager.getAuthTokenObservable().subscribe(new Observer<AuthResource<OAuthToken>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(AuthResource<OAuthToken> tokenAuthResource) {
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
                    break;
                }

                case AUTHENTICATED:{
                    break;
                }

                case ERROR:{
                    break;
                }

                case NOT_AUTHENTICATED:{
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
