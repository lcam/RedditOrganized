package lcam.redditorganized.ui.main;

import android.os.Bundle;

import androidx.annotation.Nullable;

import lcam.redditorganized.BaseActivity;
import lcam.redditorganized.R;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
