package lcam.redditorganized.di.module;

import android.app.Application;

import com.bumptech.glide.request.RequestOptions;

import dagger.Module;
import dagger.Provides;
import lcam.redditorganized.R;

@Module
public class AppModule {
    //application level dependencies like retrofit

//    @Provides //application obj is available to me because it was bound when AppComponent is created
//    static boolean getApp(Application application){
//        return application == null;
//    }
    @Provides
    static RequestOptions provideRequestOptions(){
        return RequestOptions
                .placeholderOf(R.drawable.white_background)
                .error(R.drawable.white_background);
    }
}
