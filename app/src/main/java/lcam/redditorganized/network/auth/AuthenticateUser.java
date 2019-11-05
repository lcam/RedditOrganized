package lcam.redditorganized.network.auth;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import lcam.redditorganized.base.SessionManager;
import lcam.redditorganized.models.OAuthToken;
import lcam.redditorganized.ui.auth.AuthResource;

public class AuthenticateUser {

    private static final String TAG = "AuthenticateUser";

    private AuthNetworkClient authNetworkClient;
    private SessionManager sessionManager;

    @Inject
    public AuthenticateUser(AuthNetworkClient authNetworkClient, SessionManager sessionManager) {
        this.authNetworkClient = authNetworkClient;
        this.sessionManager = sessionManager;
    }

    public void queryToken(String code){

        sessionManager.authenticateWithId(
                authNetworkClient.requestToken(code)

                        .toObservable()

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

    public Observable<AuthResource<OAuthToken>> observeCachedToken(){
        return sessionManager.getAuthTokenObservable();
    }
}
