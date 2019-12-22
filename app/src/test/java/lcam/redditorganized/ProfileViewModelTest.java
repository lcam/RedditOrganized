package lcam.redditorganized;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.internal.stubbing.answers.ReturnsArgumentAt;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import lcam.redditorganized.network.main.MainApi;
import lcam.redditorganized.network.main.MainInterceptor;
import lcam.redditorganized.network.main.MainNetworkClient;
import lcam.redditorganized.network.main.RequestProfile;
import lcam.redditorganized.ui.main.posts.PostsViewModel;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ProfileViewModelTest {

    //some Android Architecture Components execute tasks in background but we don’t want that in our tests
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    MainApi mainApi;

    @Mock
    MainInterceptor mainInterceptor;

    @Mock
    MainNetworkClient mainNetworkClient;

    @Mock
    RequestProfile requestProfile;

    private PostsViewModel postsViewModel;

    @Mock
    Observer<NewsListViewState> observer;

    @Test
    public void testQuery()  {
        ClassToTest t  = new ClassToTest(databaseMock);
        boolean check = t.query("* from t");
        assertTrue(check);
        verify(databaseMock).query("* from t");
    }
}