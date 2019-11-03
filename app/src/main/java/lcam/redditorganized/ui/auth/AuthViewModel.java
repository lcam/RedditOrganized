package lcam.redditorganized.ui.auth;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import io.reactivex.Observable;
import lcam.redditorganized.models.OAuthToken;
import lcam.redditorganized.network.auth.AuthenticateUser;


public class AuthViewModel extends ViewModel {
    private static final String TAG = "AuthViewModel";

    //inject
    private AuthenticateUser authenticateUser;

    @Inject
    public AuthViewModel(AuthenticateUser authenticateUser) {
        this.authenticateUser = authenticateUser;
    }


    public void authenticateWithCode(String code){
        Log.d(TAG, "authenticateWithId: attempting to login");
        authenticateUser.queryToken(code);
    }

    //observe Observable in SessionManager, any changes made to that obj will get updated to UI
    //in this case, the only time it will get changed is if we successfully authenticate
    public Observable<AuthResource<OAuthToken>> observeAuthState(){
        return authenticateUser.observeCachedToken();
    }

}
