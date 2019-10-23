package lcam.redditorganized.ui.main.posts;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import lcam.redditorganized.models.SavedList;
import lcam.redditorganized.models.User;
import lcam.redditorganized.network.main.RequestProfile;
import lcam.redditorganized.ui.main.Resource;

public class PostsViewModel extends ViewModel {

    private static final String TAG = "PostsViewModel";

    //inject
    private final RequestProfile requestProfile;

    private MediatorLiveData<Resource<User>> user;
    private MediatorLiveData<Resource<SavedList>> posts;

    @Inject
    public PostsViewModel(RequestProfile requestProfile) {
        this.requestProfile = requestProfile;
        Log.d(TAG, "PostsViewModel: viewmodel is working");
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

    public LiveData<Resource<SavedList>> observePosts(User user){

        if(posts == null){

            posts = new MediatorLiveData<>();
            posts.setValue(Resource.loading((SavedList)null)); //tell UI we're loading and show progress bar
            
            final LiveData<Resource<SavedList>> source = requestProfile.queryPosts(user.getUsername());

            posts.addSource(source, new Observer<Resource<SavedList>>() {
                @Override
                public void onChanged(Resource<SavedList> listResource) {
                    posts.setValue(listResource);
                    posts.removeSource(source);
                }
            });
        }
        return posts;
    }
}
