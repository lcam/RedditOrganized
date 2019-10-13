package lcam.redditorganized.network.auth;

import android.util.Base64;

import javax.inject.Inject;

import lcam.redditorganized.util.Constants;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class AuthenticateUser {

    NetworkClient networkClient = new NetworkClient();

    OkHttpClient client;

    @Inject
    public AuthenticateUser(OkHttpClient client) {
        this.client = client;
    }

    public OkHttpClient buildClient(){
        return client;
    }

    public Request buildRequest(String code){
        String authString = Constants.CLIENT_ID + ":";
        String encodedAuthString = Base64.encodeToString(authString.getBytes(),
                Base64.NO_WRAP);

        Request request = networkClient.networkImplementation(
                "Sample App",
                "Basic " + encodedAuthString,
                Constants.ACCESS_TOKEN_URL,
                "grant_type=authorization_code&code=" + code +
                                "&redirect_uri=" + Constants.REDIRECT_URI);

        return request;
    }
}
