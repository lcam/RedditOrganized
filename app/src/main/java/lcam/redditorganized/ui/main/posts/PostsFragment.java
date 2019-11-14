package lcam.redditorganized.ui.main.posts;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import io.reactivex.disposables.Disposable;
import lcam.redditorganized.R;
import lcam.redditorganized.models.SavedList;
import lcam.redditorganized.models.User;
import lcam.redditorganized.ui.main.Resource;
import lcam.redditorganized.util.VerticalSpacingItemDecoration;
import lcam.redditorganized.viewmodels.ViewModelProviderFactory;

public class PostsFragment extends DaggerFragment {

    private static final String TAG = "PostsFragments";

    private PostsViewModel viewModel;
    private RecyclerView recyclerView;

    @Inject
    PostsRecyclerAdapter adapter;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recycler_view); //doing this here instead of onCreateView cuz I have access to view here

        viewModel = ViewModelProviders.of(this, providerFactory).get(PostsViewModel.class);

        initRecylerView();
        subscribeObservers();
    }

    private void subscribeObservers(){
        Log.e(TAG, "subscribeObservers: at the start (Posts Fragment)");

        Disposable disposable = viewModel.getAuthenticatedUsername().subscribe(
                userResource -> subscribeObserversPosts(userResource.data),
                throwable -> Log.e(TAG, "subscribeObservers: onError " + throwable)
        );
    }

    private void subscribeObserversPosts(User user){

        Disposable disposable = viewModel.observePosts(user).subscribe(
                savedListResource -> {
                    Log.e(TAG, "subscribeObserversPosts: onNext AT POSTS FRAGMENT");
                    observePosts(savedListResource);
                },
                throwable -> Log.e(TAG, "subscribeObserversPosts: onError " + throwable)
        );
    }

    private void observePosts(Resource<SavedList> listResource){
        if(listResource != null){
            switch (listResource.status){

                case LOADING:{
                    Log.e(TAG, "onChanged: LOADING...");
                    break;
                }

                case SUCCESS:{
                    Log.e(TAG, "onChanged: Got posts!");
                    adapter.setPosts(listResource.data.getListData().getSavedPostList());
                    break;
                }

                case ERROR:{
                    Log.e(TAG, "onChanged: ERROR!" + listResource.message);
                    break;
                }
            }
        }
    }

    private void initRecylerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity())); //getActivity instead of "this" cuz we're inside a Fragment
        VerticalSpacingItemDecoration itemDecoration = new VerticalSpacingItemDecoration(15);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(adapter);
    }
}
