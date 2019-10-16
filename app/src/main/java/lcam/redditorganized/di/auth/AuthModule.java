package lcam.redditorganized.di.auth;

import dagger.Module;
import dagger.Provides;
import lcam.redditorganized.network.auth.AuthApi;
import lcam.redditorganized.network.auth.AuthenticateUser;
import lcam.redditorganized.network.auth.NetworkClient;
import retrofit2.Retrofit;

@Module
public class AuthModule {

    //add dependencies for the Auth SubComponent
    @AuthScope
    @Provides
    static AuthApi provideAuthApi(Retrofit retrofit){
        return retrofit.create(AuthApi.class);
    }

    @AuthScope
    @Provides
    static AuthenticateUser provideAuthenticatedUser(NetworkClient client){
        return new AuthenticateUser(client);
    }

    @AuthScope
    @Provides
    static NetworkClient provideNetworkClient(AuthApi authApi){
        return new NetworkClient(authApi);
    }
}
