package lcam.redditorganized.ui.main.profile;

import android.util.Log;
import androidx.lifecycle.ViewModel;
import javax.inject.Inject;

import io.reactivex.Observable;
import lcam.redditorganized.models.User;
import lcam.redditorganized.network.main.RequestProfile;
import lcam.redditorganized.ui.main.Resource;

public class ProfileViewModel extends ViewModel {

    private static final String TAG = "ProfileViewModel";

    private final RequestProfile requestProfile;

    @Inject
    public ProfileViewModel(RequestProfile requestProfile) {
        Log.d(TAG, "ProfileViewModel: viewmodel is ready");
        this.requestProfile = requestProfile;
    }

    public Observable<Resource<User>> getAuthenticatedUsername(){
        return requestProfile.queryName();
    }

}
