package lcam.redditorganized.base;

import android.util.Log;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;
import lcam.redditorganized.models.OAuthToken;
import lcam.redditorganized.ui.auth.AuthResource;

@Singleton
public class SessionManager {

    private static final String TAG = "SessionManager";

    private AuthResource<OAuthToken> authResourceCache = AuthResource.logout();

    private BehaviorSubject<AuthResource<OAuthToken>> cachedToken = BehaviorSubject.create();


    @Inject
    public SessionManager() {
    }

    public void authenticateWithId(AuthResource<OAuthToken> authResource){
        Log.e(TAG, "onNext: Use OAuthToken obj from API call");
        authResourceCache = authResource; //cache to OAuthToken object
        cachedToken.onNext(authResourceCache);
    }

    public void errorCase(Throwable e){
        Log.e(TAG, "onError: in SessionManager");
    }

    public void completeCase(){
        Log.e(TAG, "onComplete: in SessionManager");
    }

    public void subscribeCase(Disposable d){
        Log.e(TAG, "onSubscribe: Set logout state as default");
        cachedToken.onNext(AuthResource.logout());
    }

    public void logOut(){
        //cachedToken.setValue(AuthResource.logout());
        authResourceCache = AuthResource.logout();
        cachedToken.onNext(authResourceCache);
    }

    public Observable<AuthResource<OAuthToken>> getAuthTokenObservable(){
        return cachedToken; //used to observe auth changes
    }

    public OAuthToken getCachedToken(){
        return authResourceCache.data; //used to peek at cached auth obj
    }
}
