package lcam.redditorganized.ui.auth;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
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

//    public void authenticateWithCode(String code){
//        Log.d(TAG, "authenticateWithId: attempting to login");
//        sessionManager.authenticateWithId(authenticateUser.queryToken2(code));
//    }

    public void authenticateWithCode(String code){
        Log.d(TAG, "authenticateWithId: attempting to login");
        authenticateUser.queryToken2(code);
    }



    //observe MediatorLiveData from the UI
//    public LiveData<AuthResource<OAuthToken>> observeAuthState(){
//        return sessionManager.getAuthUser(); //observe this LiveData obj, any changes made to that obj will get updated to UI
//        //in this case, the only time it will get changed is if we successfully authenticate
//    }

    public LiveData<AuthResource<OAuthToken>> observeAuthState(){
        return authenticateUser.observeCachedToken();
    }


//    public void getAccessToken(String code) {
//        Log.d(TAG, "Inside getAccessToken!!!");
//
//        authenticateUser.queryToken(code)
//                .subscribe(new Observer<AuthResource<OAuthToken>>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(AuthResource<OAuthToken> tokenAuthResource) {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
//    }

}
