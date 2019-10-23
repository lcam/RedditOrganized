package lcam.redditorganized.network.main;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;

import java.util.ArrayList;

import javax.inject.Inject;

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


    public LiveData<Resource<User>> queryName(){
        //converting Flowable to LiveData obj, doing the api call
        return LiveDataReactiveStreams.fromPublisher(
                mainNetworkClient.requestName()

                        //instead of calling onError (error happens)
                        .onErrorReturn(new Function<Throwable, User>() {
                            @Override
                            public User apply(Throwable throwable) throws Exception {
                                User errorUser = new User("");
                                return errorUser;
                            }
                        })

                        //wrap User object in AuthResource
                        .map(new Function<User, Resource<User>>() {
                            @Override
                            public Resource<User> apply(User user) throws Exception {
                                if(user.getUsername().equals("")){
                                    return Resource.error("Could not retrieve name", (User) null);
                                }
                                return Resource.success(user); //no error
                            }
                        })

                        .subscribeOn(Schedulers.io()) //subscribe on a background thread
        );
    }

    public LiveData<Resource<SavedList>> queryPosts(String username){
        return LiveDataReactiveStreams.fromPublisher(
                    mainNetworkClient.requestSavedList(username)

                        .onErrorReturn(new Function<Throwable, SavedList>() {
                            @Override
                            public SavedList apply(Throwable throwable) throws Exception {
                                Log.e(TAG, "apply: ", throwable);

                                //SavedList -> ListData -> List<SavedPost> -> SavedPostAttributes
                                SavedPostAttributes savedPostAttributes = new SavedPostAttributes("");
                                SavedPost savedPost = new SavedPost(savedPostAttributes);
                                ArrayList<SavedPost> savedPostsList = new ArrayList<>();
                                savedPostsList.add(savedPost);
                                ListData listData = new ListData(1, savedPostsList);
                                SavedList savedList = new SavedList(listData);

                                return savedList;
                            }
                        })

                        .map(new Function<SavedList, Resource<SavedList>>() {
                            @Override
                            public Resource<SavedList> apply(SavedList savedList) throws Exception {

                                if(savedList!=null){
                                    if(savedList.getListData().getSavedPostList().get(0).getSavedPostAttributes().getTitle().equals("")){
                                        return Resource.error("Something went wrong", null);
                                    }
                                }

                                return Resource.success(savedList);
                            }
                        })

                        .subscribeOn(Schedulers.io())
        );

    }

}
