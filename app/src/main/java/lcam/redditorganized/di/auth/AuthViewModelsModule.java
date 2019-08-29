package lcam.redditorganized.di.auth;

import androidx.lifecycle.ViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import lcam.redditorganized.di.ViewModelKey;
import lcam.redditorganized.ui.auth.AuthViewModel;

@Module
public abstract class AuthViewModelsModule {
    //mapping view model into multibinding
    //similar to ViewModelFactoryModule, but this time I am providing dependency to AuthViewModel itself
    @Binds
    @IntoMap //mapping this dependency to particular key --> multi-binding
    @ViewModelKey(AuthViewModel.class)
    public abstract ViewModel bindAuthViewModel(AuthViewModel authViewModel);
}
