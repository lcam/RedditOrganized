package lcam.redditorganized.network.auth;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
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
        Single<OAuthToken> source = authNetworkClient.requestToken(code);

        //converting Single to Observable to use onSubscribe()
        Disposable authDisposable = source.toObservable()
                .onErrorReturnItem(new OAuthToken("", ""))
                .map(token -> {
                    if(token.getAccessToken().equals("")){
                        return AuthResource.error("Could not authenticate", null);
                    }
                    return AuthResource.authenticated(token); })
                .subscribeOn(Schedulers.io())
                .subscribe(
                        authResource -> sessionManager.authenticateWithId((AuthResource<OAuthToken>) authResource),
                        throwable -> sessionManager.errorCase(throwable),
                        () -> sessionManager.completeCase(),
                        disposable -> sessionManager.subscribeCase(disposable));
    }

    public Observable<AuthResource<OAuthToken>> observeCachedToken(){
        return sessionManager.getAuthTokenObservable();
    }
}
