package lcam.redditorganized.ui.auth;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.RequestManager;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import lcam.redditorganized.R;
import lcam.redditorganized.viewmodels.ViewModelProviderFactory;

public class AuthActivity extends DaggerAppCompatActivity {

    private AuthViewModel authViewModel;

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    @Inject
    Drawable logo;

    @Inject
    RequestManager requestManager; //Glide instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        authViewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(AuthViewModel.class);

        setLogo();
    }

    private void setLogo(){
        requestManager
                .load(logo)
                .into((ImageView)findViewById(R.id.login_logo));
    }
}
