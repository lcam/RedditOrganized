package lcam.redditorganized.network.main;

import java.io.IOException;

import javax.inject.Inject;

import lcam.redditorganized.base.SessionManager;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class MainInterceptor implements Interceptor {

    private final SessionManager sessionManager;

    @Inject
    public MainInterceptor(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        String accessToken = sessionManager.getAuthUser().getValue().data.getAccessToken();

        Request request = chain.request()
                .newBuilder()
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();
        return chain.proceed(request);
    }
}
