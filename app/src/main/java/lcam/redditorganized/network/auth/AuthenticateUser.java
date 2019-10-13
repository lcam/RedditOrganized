package lcam.redditorganized.network.auth;

import android.util.Base64;

import javax.inject.Inject;

import lcam.redditorganized.util.Constants;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class AuthenticateUser {

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

        Request request = new Request.Builder()
                .addHeader("User-Agent", "Sample App")
                .addHeader("Authorization", "Basic " + encodedAuthString)
                .url(Constants.ACCESS_TOKEN_URL)
                .post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"),
                        "grant_type=authorization_code&code=" + code +
                                "&redirect_uri=" + Constants.REDIRECT_URI))
                .build();

        return request;
    }
}
