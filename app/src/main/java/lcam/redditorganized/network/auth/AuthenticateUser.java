package lcam.redditorganized.network.auth;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import lcam.redditorganized.models.OAuthToken;

public class AuthenticateUser {

    private static final String TAG = "AuthenticateUser";

    private NetworkClient networkClient;

    @Inject
    public AuthenticateUser(NetworkClient networkClient) {
        this.networkClient = networkClient;
    }

    public Observable<OAuthToken> queryToken(String code){
        return networkClient.requestToken(code)
                .toObservable()
                .subscribeOn(Schedulers.io());
    }
}
