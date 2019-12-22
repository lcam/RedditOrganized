package lcam.redditorganized.network.main;

import android.util.Log;

import java.util.ArrayList;
import javax.inject.Inject;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import lcam.redditorganized.models.ListData;
import lcam.redditorganized.models.SavedList;
import lcam.redditorganized.models.SavedPost;
import lcam.redditorganized.models.SavedPostAttributes;
import lcam.redditorganized.models.User;
import lcam.redditorganized.ui.main.Resource;

public class RequestProfile {

    private static final String TAG = "RequestProfile";

    private MainNetworkClient mainNetworkClient;

    @Inject
    public RequestProfile(MainNetworkClient mainNetworkClient) {
        this.mainNetworkClient = mainNetworkClient;
    }

    public Observable<Resource<User>> queryName(){
        Log.e(TAG, "queryName: At the start");

        Single<User> source = mainNetworkClient.requestName();

        return source.toObservable()
                .onErrorReturn(throwable -> defaultUser(throwable))
                .map(user -> {
                    if(user.getUsername().equals("")){
                        Log.e(TAG, "queryName: ABOUT TO RETURN ERROR UGGHHH");
                        return Resource.error("Could not retrieve name", (User) null);
                    }

                    Log.e(TAG, "queryName: SUCCESS, ABOUT TO RETURN USER VER 2");
                    return Resource.success(user); })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Resource<SavedList>> queryPosts(String username){

        Single<SavedList> source = mainNetworkClient.requestSavedList(username);

        return source.toObservable()
                .onErrorReturnItem(defaultSavedList())
                .map(savedList -> {
                    if(savedList!=null){
                        if(savedList.getListData().getSavedPostList().get(0).getSavedPostAttributes().getTitle().equals("")){
                            Log.e(TAG, "queryPosts: ABOUT TO RETURN AN ERROR");
                            return Resource.error("Something went wrong", (SavedList) null);
                        }
                    }

                    Log.e(TAG, "queryPosts: SUCCESS, ABOUT TO RETURN SAVED LIST");
                    return Resource.success(savedList); })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    private User defaultUser(Throwable throwable) {
        Log.e(TAG, "defaultUser: ERROR " + throwable.getLocalizedMessage());
        return new User("");
    }

    private SavedList defaultSavedList(){
        //SavedList -> ListData -> List<SavedPost> -> SavedPostAttributes
        SavedPostAttributes savedPostAttributes = new SavedPostAttributes("");
        SavedPost savedPost = new SavedPost(savedPostAttributes);
        ArrayList<SavedPost> savedPostsList = new ArrayList<>();
        savedPostsList.add(savedPost);
        ListData listData = new ListData(1, savedPostsList);
        SavedList savedList = new SavedList(listData);

        return savedList;
    }

}
