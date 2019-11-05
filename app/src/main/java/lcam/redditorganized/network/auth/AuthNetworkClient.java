package lcam.redditorganized.network.auth;

import javax.inject.Inject;

import io.reactivex.Single;
import lcam.redditorganized.models.OAuthToken;
import lcam.redditorganized.util.Constants;

public class AuthNetworkClient {

    private final AuthApi authApi;

    @Inject
    public AuthNetworkClient(AuthApi authApi) {
        this.authApi = authApi;
    }

    Single<OAuthToken> requestToken(String code){
        return authApi.requestTokenForm(
                code,
                Constants.CLIENT_ID,
                Constants.REDIRECT_URI,
                Constants.GRANT_TYPE_AUTHORIZATION_CODE
        );
    }
}
