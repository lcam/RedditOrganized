package lcam.redditorganized.base;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import lcam.redditorganized.models.OAuthToken;
import lcam.redditorganized.ui.auth.AuthResource;

@Singleton
public class SessionManager {

    private static final String TAG = "SessionManager";

    //using MediatorLiveData instead of just a basic obj (User user) cuz I want this obj to be observable
    private MediatorLiveData<AuthResource<OAuthToken>> cachedToken = new MediatorLiveData<>();

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

    public void authenticateWithId(Flowable<AuthResource<OAuthToken>> token){
        final LiveData<AuthResource<OAuthToken>> source = LiveDataReactiveStreams.fromPublisher(token);
        //get source LiveData obj set to  authUser LiveData obj --> by using MediatorLiveData
        if(cachedToken != null){
            cachedToken.setValue(AuthResource.loading((OAuthToken) null));
            cachedToken.addSource(source, new Observer<AuthResource<OAuthToken>>() {
                @Override
                public void onChanged(AuthResource<OAuthToken> userAuthResource) {
                    cachedToken.setValue(userAuthResource);
                    cachedToken.removeSource(source);
                }
            });
        }
    }

    public void logOut(){
        cachedToken.setValue(AuthResource.<OAuthToken>logout());
    }

    public LiveData<AuthResource<OAuthToken>> getAuthUser(){
        return cachedToken;
    }
}
