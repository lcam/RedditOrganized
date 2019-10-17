package lcam.redditorganized.ui.auth;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import lcam.redditorganized.models.OAuthToken;
import lcam.redditorganized.network.auth.AuthApi;
import lcam.redditorganized.network.auth.AuthenticateUser;


public class AuthViewModel extends ViewModel {
    private static final String TAG = "AuthViewModel";

    //inject
    private final AuthApi authApi;
    private AuthenticateUser authenticateUser;

    @Inject
    public AuthViewModel(AuthApi authApi, AuthenticateUser authenticateUser) {
        this.authApi = authApi;
        this.authenticateUser = authenticateUser;
    }

    public void getAccessToken(String code) {
        Log.d(TAG, "Inside getAccessToken!!!");

        authenticateUser.queryToken(code)
                .subscribe(new Observer<OAuthToken>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(OAuthToken oAuthToken) {
                        Log.d(TAG, "onNext access token: " + oAuthToken.getAccessToken());
                        Log.d(TAG, "onNext refresh token: " + oAuthToken.getRefreshToken());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

}
