package lcam.redditorganized.network.auth;

import javax.inject.Inject;

import io.reactivex.Flowable;
import lcam.redditorganized.models.OAuthToken;
import lcam.redditorganized.util.Constants;

public class NetworkClient {

    private final AuthApi authApi;

    @Inject
    public NetworkClient(AuthApi authApi) {
        this.authApi = authApi;
    }

    Flowable<OAuthToken> requestToken(String code){
        return authApi.requestTokenForm(
                code,
                Constants.CLIENT_ID,
                Constants.REDIRECT_URI,
                Constants.GRANT_TYPE_AUTHORIZATION_CODE
        );
    }
}
