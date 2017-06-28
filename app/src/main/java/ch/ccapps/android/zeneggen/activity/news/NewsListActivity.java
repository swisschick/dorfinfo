package ch.ccapps.android.zeneggen.activity.news;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import ch.ccapps.android.zeneggen.R;
import ch.ccapps.android.zeneggen.activity.ActionBarActivity;
import ch.ccapps.android.zeneggen.activity.ActionTabBarActivity;

public class NewsListActivity extends ActionBarActivity {

    private static final String TAG = NewsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarContentView(R.layout.activity_news_list);
        setTitle(R.string.title_news);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
