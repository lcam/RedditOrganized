package lcam.redditorganized.ui.main.posts;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
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
        viewModel.getAuthenticatedUsername().removeObservers(getViewLifecycleOwner()); //make sure to remove observers
        viewModel.getAuthenticatedUsername().observe(getViewLifecycleOwner(), new Observer<Resource<User>>() {
            @Override
            public void onChanged(Resource<User> userResource) {
                if(userResource != null){
                    switch (userResource.status){

                        case SUCCESS:{
                            Log.d(TAG, "onChanged: Got username!");
                            subscribeObserversPosts(userResource.data);
                            break;
                        }

                        case ERROR:{
                            Log.d(TAG, "onChanged: ERROR!" + userResource.message);
                            break;
                        }
                    }
                }
            }
        });
    }

    private void subscribeObserversPosts(User user){
        viewModel.observePosts(user).removeObservers(getViewLifecycleOwner()); //make sure to remove observers
        viewModel.observePosts(user).observe(getViewLifecycleOwner(), new Observer<Resource<SavedList>>() {
            @Override
            public void onChanged(Resource<SavedList> listResource) {
                if(listResource != null){
                    switch (listResource.status){

                        case LOADING:{
                            Log.d(TAG, "onChanged: LOADING...");
                            break;
                        }

                        case SUCCESS:{
                            Log.d(TAG, "onChanged: Got posts!");
                            adapter.setPosts(listResource.data.getListData().getSavedPostList());
                            break;
                        }

                        case ERROR:{
                            Log.d(TAG, "onChanged: ERROR!" + listResource.message);
                            break;
                        }
                    }
                }
            }
        });
    }

    private void initRecylerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity())); //getActivity instead of "this" cuz we're inside a Fragment
        VerticalSpacingItemDecoration itemDecoration = new VerticalSpacingItemDecoration(15);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(adapter);
    }
}
