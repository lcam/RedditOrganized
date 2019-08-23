package lcam.redditorganized.di.module;

import android.app.Application;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import lcam.redditorganized.R;

@Module
public class AppModule {
    //application level dependencies like retrofit, glide

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
