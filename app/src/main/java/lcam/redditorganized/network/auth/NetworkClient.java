package lcam.redditorganized.network.auth;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public class NetworkClient {

    public NetworkClient() {
    }

    public Request networkImplementation(String userAgent, String authorization, String accessTokenUrl, String postData){
        Request request = new Request.Builder()
                .addHeader("User-Agent", userAgent)
                .addHeader("Authorization", authorization)
                .url(accessTokenUrl)
                .post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"),
                        postData))
                .build();

        return request;
    }
}
