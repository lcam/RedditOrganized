package lcam.redditorganized.network.auth;

import android.util.Base64;

import java.io.IOException;

import lcam.redditorganized.util.Constants;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class SupportInterceptor implements Interceptor {

    public SupportInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        String authString = Constants.CLIENT_ID + ":";
        String encodedAuthString = Base64.encodeToString(authString.getBytes(),
                Base64.NO_WRAP);

        Request request = chain.request()
                .newBuilder()
                .addHeader("User-Agent", "Sample App")
                .addHeader("Authorization", "Basic " + encodedAuthString)
                .build();
        return chain.proceed(request);
    }
}
