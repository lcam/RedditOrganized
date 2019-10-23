package lcam.redditorganized.di.application;

import android.app.Application;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import lcam.redditorganized.R;
import lcam.redditorganized.base.SessionManager;
import lcam.redditorganized.network.auth.AuthInterceptor;
import lcam.redditorganized.network.main.MainInterceptor;
import lcam.redditorganized.util.Constants;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {
    //application level dependencies like retrofit, glide

    @Singleton
    @Provides
    @Named("authRetrofit")
    static Retrofit provideAuthRetrofitInstance(@Named("authOkHttpClient") OkHttpClient client){
        return new Retrofit.Builder()
                .baseUrl(Constants.REDDIT_BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    @Named("mainRetrofit")
    static Retrofit provideMainRetrofitInstance(@Named("mainOkHttpClient") OkHttpClient client){
        return new Retrofit.Builder()
                .baseUrl(Constants.REDDIT_API_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    @Named("authOkHttpClient")
    static OkHttpClient provideOkHttpInstance(AuthInterceptor authInterceptor){
        OkHttpClient client = new OkHttpClient().newBuilder()
                .addInterceptor(authInterceptor)
                .build();

        return client;
    }

    @Singleton
    @Provides
    @Named("mainOkHttpClient")
    static OkHttpClient provideMainOkHttpInstance(MainInterceptor mainInterceptor){
        OkHttpClient client = new OkHttpClient().newBuilder()
                .addInterceptor(mainInterceptor)
                .build();

        return client;
    }

    @Singleton
    @Provides
    static AuthInterceptor provideAuthInterceptor(){
        return new AuthInterceptor();
    }

    @Singleton
    @Provides
    static MainInterceptor provideMainInterceptor(SessionManager sessionManager){
        return new MainInterceptor(sessionManager);
    }

    @Singleton
    @Provides
    static RequestOptions provideRequestOptions(){
        return RequestOptions
                .placeholderOf(R.drawable.logo)
                .error(R.drawable.logo);
    }

    @Singleton
    @Provides
    static RequestManager provideGlideInstance(Application application, RequestOptions requestOptions){
        return Glide.with(application)
                .setDefaultRequestOptions(requestOptions);
    }

    @Singleton
    @Provides //application obj is available to me because it was bound when AppComponent is created
    static Drawable provideAppDrawable(Application application){
        return ContextCompat.getDrawable(application, R.drawable.logo);
    }
}
