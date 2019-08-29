package lcam.redditorganized.di.module;

import androidx.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import lcam.redditorganized.viewmodels.ViewModelProviderFactory;

@Module
public abstract class ViewModelFactoryModule {

    @Binds //provide instance of ViewModelProvider.Factory, providing a dependency for ViewModelFactory
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory viewModelProviderFactory);

//    @Provides NOTE: Same thing as above, but that one is more efficient, can be used when method body is just return the object
//    static ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory factory){
//        return factory;
//    }


}
