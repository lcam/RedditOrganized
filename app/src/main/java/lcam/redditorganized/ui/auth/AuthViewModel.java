package lcam.redditorganized.ui.auth;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import lcam.redditorganized.SessionManager;
import lcam.redditorganized.models.User;
import lcam.redditorganized.network.auth.AuthApi;

public class AuthViewModel extends ViewModel {
    private static final String TAG = "AuthViewModel";

    //inject
    private final AuthApi authApi;
    private SessionManager sessionManager;

    @Inject
    public AuthViewModel(AuthApi authApi, SessionManager sessionManager) {
        this.authApi = authApi;
        this.sessionManager = sessionManager;
    }

    public void authenticateWithId(int userId){
        Log.d(TAG, "authenticateWithId: attempting to login");
        sessionManager.authenticateWithId(queryUserId(userId));
    }

    private LiveData<AuthResource<User>> queryUserId(int userId){
        //converting Flowable to LiveData obj, doing the api call
        return LiveDataReactiveStreams.fromPublisher(
                authApi.getUser(userId)

                        //instead of calling onError (error happens)
                        .onErrorReturn(new Function<Throwable, User>() {
                            @Override
                            public User apply(Throwable throwable) throws Exception {
                                User errorUser = new User();
                                errorUser.setId(-1);
                                return errorUser;
                            }
                        })

                        //wrap User object in AuthResource
                        .map(new Function<User, AuthResource<User>>() {
                            @Override
                            public AuthResource<User> apply(User user) throws Exception {
                                if(user.getId() == -1){
                                    return AuthResource.error("Could not authenticate", (User)null);
                                }
                                return AuthResource.authenticated(user); //no error
                            }
                        })

                        .subscribeOn(Schedulers.io()) //subscribe on a background thread
        );
    }

    //observe MediatorLiveData from the UI
    public LiveData<AuthResource<User>> observeAuthState(){
        return sessionManager.getAuthUser(); //observe this LiveData obj, any changes made to that obj will get updated to UI
                         //in this case, the only time it will get changed is if we successfully authenticate
    }

}
