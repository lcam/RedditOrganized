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

    private AuthResource<OAuthToken> authResourceToken = AuthResource.logout();

//    private Observable<AuthResource<OAuthToken>> cachedToken = Observable.just(authResourceToken)
//            .subscribeOn(Schedulers.io());

    private BehaviorSubject<AuthResource<OAuthToken>> cachedToken = BehaviorSubject.create();


    @Inject
    public SessionManager() {
    }

//    public void authenticateWithId(final LiveData<AuthResource<OAuthToken>> source){
//        //get source LiveData obj set to  authUser LiveData obj --> by using MediatorLiveData
//        if(cachedToken != null){
//            cachedToken.setValue(AuthResource.loading((OAuthToken) null));
//            cachedToken.addSource(source, new Observer<AuthResource<OAuthToken>>() {
//                @Override
//                public void onChanged(AuthResource<OAuthToken> userAuthResource) {
//                    cachedToken.setValue(userAuthResource);
//                    cachedToken.removeSource(source);
//                }
//            });
//        }
//    }

    public void authenticateWithId(Observable<AuthResource<OAuthToken>> token){
        token.subscribe(new Observer<AuthResource<OAuthToken>>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, "onSubscribe: Set logout state as default");
                cachedToken.onNext(AuthResource.logout());
            }

            @Override
            public void onNext(AuthResource<OAuthToken> tokenAuthResource) {
                Log.e(TAG, "onNext: Use OAuthToken obj from API call");
                authResourceToken = tokenAuthResource; //cache to OAuthToken object

                cachedToken.onNext(authResourceToken);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: in SessionManager");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete: in SessionManager");
            }
        });
    }

    public void logOut(){
        //cachedToken.setValue(AuthResource.logout());
        authResourceToken = AuthResource.logout();
        cachedToken.onNext(authResourceToken);
    }

    public Observable<AuthResource<OAuthToken>> getAuthTokenObservable(){
        return cachedToken; //used to observe auth changes
    }

    public OAuthToken getCachedToken(){
        return authResourceToken.data; //used to peek at cached auth obj
    }
}
