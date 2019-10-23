package lcam.redditorganized.ui.main.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import lcam.redditorganized.R;
import lcam.redditorganized.models.OAuthToken;
import lcam.redditorganized.models.User;
import lcam.redditorganized.ui.auth.AuthResource;
import lcam.redditorganized.ui.main.Resource;
import lcam.redditorganized.viewmodels.ViewModelProviderFactory;

public class ProfileFragment extends DaggerFragment {

    private static final String TAG = "ProfileFragment";

    private ProfileViewModel viewModel;
    private TextView username;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Toast.makeText(getActivity(), "Profile Fragment", Toast.LENGTH_SHORT).show();

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated: ProfileFragment was created");

        username = view.findViewById(R.id.username);

        // ProfileFragment was created
        viewModel = ViewModelProviders.of(this, providerFactory).get(ProfileViewModel.class);

        subscribeObservers();
    }

    private void subscribeObservers(){
        //removeObservers() and getViewLifecycleOwner() is specifically for fragments cuz they have their own lifecyle
        //fragments just do what the Android system wants them to do, so we gotta make sure to clean up the observers
        viewModel.getAuthenticatedUsername().removeObservers(getViewLifecycleOwner());
        viewModel.getAuthenticatedUsername().observe(getViewLifecycleOwner(), new Observer<Resource<User>>() {
            @Override
            public void onChanged(Resource<User> userResource) {

                if(userResource != null){
                    switch (userResource.status){

                        case SUCCESS:{
                            setUserDetails(userResource.data);
                            break;
                        }

                        case ERROR:{
                            setErrorDetails(userResource.message);
                            break;
                        }
                    }
                }

            }
        });
    }

    private void setErrorDetails(String message) {
        username.setText("error");
    }

    private void setUserDetails(User data) {
        username.setText(data.getUsername());
    }
}
