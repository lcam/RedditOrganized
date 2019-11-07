package lcam.redditorganized.ui.main.posts;

import android.util.Log;
import androidx.lifecycle.ViewModel;
import javax.inject.Inject;

import io.reactivex.Observable;
import lcam.redditorganized.models.SavedList;
import lcam.redditorganized.models.User;
import lcam.redditorganized.network.main.RequestProfile;
import lcam.redditorganized.ui.main.Resource;

public class PostsViewModel extends ViewModel {

    private static final String TAG = "PostsViewModel";

    //inject
    private final RequestProfile requestProfile;

    @Inject
    public PostsViewModel(RequestProfile requestProfile) {
        this.requestProfile = requestProfile;
        Log.d(TAG, "PostsViewModel: viewmodel is working");
    }

    public Observable<Resource<User>> getAuthenticatedUsername(){

        return requestProfile.queryName();
    }

    public io.reactivex.Observable<Resource<SavedList>> observePosts(User user){

        return requestProfile.queryPosts(user.getUsername());
    }
}
