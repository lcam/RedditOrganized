package lcam.redditorganized.ui.main.profile;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import lcam.redditorganized.models.User;
import lcam.redditorganized.network.main.RequestProfile;
import lcam.redditorganized.ui.main.Resource;

public class ProfileViewModel extends ViewModel {

    private static final String TAG = "ProfileViewModel";

    private final RequestProfile requestProfile;

    private MediatorLiveData<Resource<User>> user;


    @Inject
    public ProfileViewModel(RequestProfile requestProfile) {
        Log.d(TAG, "ProfileViewModel: viewmodel is ready");
        this.requestProfile = requestProfile;
    }

    public LiveData<Resource<User>> getAuthenticatedUsername(){
        if(user == null){
            user = new MediatorLiveData<>();
            user.setValue(Resource.loading((User) null));

            final LiveData<Resource<User>> source = requestProfile.queryName();

            user.addSource(source, new Observer<Resource<User>>() {
                @Override
                public void onChanged(Resource<User> userResource) {
                    user.setValue(userResource);
                    user.removeSource(source);
                }
            });
        }

        return user;
    }

}
