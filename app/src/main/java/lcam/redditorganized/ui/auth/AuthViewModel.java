package lcam.redditorganized.ui.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import lcam.redditorganized.models.User;
import lcam.redditorganized.network.auth.AuthApi;

public class AuthViewModel extends ViewModel {

    private final AuthApi authApi;

    private MediatorLiveData<User> authUser = new MediatorLiveData<>();

    @Inject
    public AuthViewModel(AuthApi authApi) {
        this.authApi = authApi;
    }

    public void authenticateWithId(int userId){
        //converting Flowable to LiveData obj, doing the api call
        final LiveData<User> source = LiveDataReactiveStreams.fromPublisher(
                authApi.getUser(userId)
                .subscribeOn(Schedulers.io()) //subscribe on a background thread
        );

        //get source LiveData obj set to  authUser LiveData obj --> by using MediatorLiveData
        authUser.addSource(source, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                authUser.setValue(user);
                authUser.removeSource(source); //remove cuz we no longer need to listen to it
            }
        });
    }

    //observe MediatorLiveData from the UI
    public LiveData<User> observeUser(){
        return authUser; //observe this LiveData obj, any changes made to that obj will get updated to UI
                         //in this case, the only time it will get changed is if we successfully authenticate
    }

}
