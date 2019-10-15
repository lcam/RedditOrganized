package lcam.redditorganized.network.auth;

import javax.inject.Inject;
import retrofit2.Retrofit;

public class AuthenticateUser {

    Retrofit retrofit;

    @Inject
    public AuthenticateUser(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public AuthApi getSimpleClient(){
        return retrofit.create(AuthApi.class);
    }
}
