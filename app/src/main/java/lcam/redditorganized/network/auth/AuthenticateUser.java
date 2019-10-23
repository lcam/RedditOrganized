package lcam.redditorganized.network.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import lcam.redditorganized.models.OAuthToken;
import lcam.redditorganized.ui.auth.AuthResource;

public class AuthenticateUser {

    private static final String TAG = "AuthenticateUser";

    private AuthNetworkClient authNetworkClient;

    @Inject
    public AuthenticateUser(AuthNetworkClient authNetworkClient) {
        this.authNetworkClient = authNetworkClient;
    }

    public Observable<OAuthToken> queryToken(String code){
        return authNetworkClient.requestToken(code)
                .toObservable()
                .subscribeOn(Schedulers.io());
    }

    public LiveData<AuthResource<OAuthToken>> queryToken2(String code){
        //converting Flowable to LiveData obj, doing the api call
        return LiveDataReactiveStreams.fromPublisher(
                authNetworkClient.requestToken(code)

                //instead of calling onError (error happens)
                .onErrorReturn(new Function<Throwable, OAuthToken>() {
                    @Override
                    public OAuthToken apply(Throwable throwable) throws Exception {
                        OAuthToken errorToken = new OAuthToken("","");
                        errorToken.setAccessToken("");
                        return errorToken;
                    }
                })

                //wrap User object in AuthResource
                .map(new Function<OAuthToken, AuthResource<OAuthToken>>() {
                    @Override
                    public AuthResource<OAuthToken> apply(OAuthToken oAuthToken) throws Exception {
                        if(oAuthToken.getAccessToken().equals("")){
                            return AuthResource.error("Could not authenticate", (OAuthToken) null);
                        }
                        return AuthResource.authenticated(oAuthToken); //no error
                    }
                })

                .subscribeOn(Schedulers.io()) //subscribe on a background thread
        );
    }
}
