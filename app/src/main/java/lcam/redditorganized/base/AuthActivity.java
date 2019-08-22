package lcam.redditorganized.base;

import android.os.Bundle;

import dagger.android.support.DaggerAppCompatActivity;
import lcam.redditorganized.R;

public class AuthActivity extends DaggerAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
    }
}
