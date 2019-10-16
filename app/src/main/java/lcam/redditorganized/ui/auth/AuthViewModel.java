package lcam.redditorganized.ui.auth;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import lcam.redditorganized.base.SessionManager;
import lcam.redditorganized.models.OAuthToken;
import lcam.redditorganized.models.User;
import lcam.redditorganized.network.auth.AuthApi;
import lcam.redditorganized.network.auth.AuthenticateUser;
import lcam.redditorganized.util.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AuthViewModel extends ViewModel {
    private static final String TAG = "AuthViewModel";

    //inject
    private final AuthApi authApi;
    private SessionManager sessionManager;
    private AuthenticateUser authenticateUser;

    @Inject
    public AuthViewModel(AuthApi authApi, SessionManager sessionManager, AuthenticateUser authenticateUser) {
        this.authApi = authApi;
        this.sessionManager = sessionManager;
        this.authenticateUser = authenticateUser;
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


    public void getAccessToken(String code) {
        Log.d(TAG, "Inside getAccessToken!!!");

        //AuthApi authApi = authenticateUser.getSimpleClient();

        Call<OAuthToken> getRequestTokenFormCall = authApi.requestTokenForm(
                code,
                Constants.CLIENT_ID,
                Constants.REDIRECT_URI,
                Constants.GRANT_TYPE_AUTHORIZATION_CODE
        );

        getRequestTokenFormCall.enqueue(new Callback<OAuthToken>() {
            @Override
            public void onResponse(Call<OAuthToken> call, Response<OAuthToken> response) {
                Log.e(TAG, "===============SUCCESS==========================");
                OAuthToken oAuthToken = response.body();
                Log.d(TAG, "access token: " + oAuthToken.getAccessToken());
                Log.d(TAG, "refresh token: " + oAuthToken.getRefreshToken());

            }
            @Override
            public void onFailure(Call<OAuthToken> call, Throwable t) {
                Log.e(TAG, "===============FAILURE==========================");
                Log.e(TAG, "The call getRequestTokenFormCall failed", t);
            }
        });

    }

}
