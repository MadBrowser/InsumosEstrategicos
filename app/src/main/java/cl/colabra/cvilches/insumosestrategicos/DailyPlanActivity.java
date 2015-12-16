package cl.colabra.cvilches.insumosestrategicos;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.raizlabs.android.dbflow.runtime.TransactionManager;
import com.raizlabs.android.dbflow.runtime.transaction.SelectListTransaction;
import com.raizlabs.android.dbflow.runtime.transaction.TransactionListenerAdapter;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.sql.queriable.ModelQueriable;

import java.util.ArrayList;
import java.util.List;

import cl.colabra.cvilches.insumosestrategicos.adapters.DailyPlanAdapter;
import cl.colabra.cvilches.insumosestrategicos.model.Storehouse;
import cl.colabra.cvilches.insumosestrategicos.utils.DividerItemDecoration;
import cl.colabra.cvilches.insumosestrategicos.utils.SessionManager;

public class DailyPlanActivity extends AppCompatActivity {

    private List<Storehouse> mStorehouseList = new ArrayList<>();
    private RelativeLayout vLoadingLayout;
    private RecyclerView vStorehousesList;
    private FloatingActionButton fab;

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_plan);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        vLoadingLayout = (RelativeLayout) findViewById(R.id.loading_layout);
        vStorehousesList = (RecyclerView) findViewById(R.id.storehouses_list);

        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sessionManager = new SessionManager(this);

        if (sessionManager.isLoggedIn()) {
            if (mStorehouseList.size() == 0) {
                getStoreListFromDB();
            } else {
                setUpRecyclerView();
            }
        } else {
            sessionManager.logoutUser();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                sessionManager.logoutUser();
                startLoginActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void getStoreListFromDB() {
        ModelQueriable<Storehouse> query = new Select().from(Storehouse.class);
        TransactionManager.getInstance().addTransaction(new SelectListTransaction<>(
                query, getStoresTransactionListenerAdapter()
        ));
    }

    private TransactionListenerAdapter<List<Storehouse>> getStoresTransactionListenerAdapter() {
        return new TransactionListenerAdapter<List<Storehouse>>() {
            @Override
            public void onResultReceived(List<Storehouse> storehouses) {
                mStorehouseList = storehouses;
                setUpRecyclerView();
            }
        };
    }

    private void setUpRecyclerView() {
        // Layout Manager
        LinearLayoutManager lym = new LinearLayoutManager(this);
        vStorehousesList.setLayoutManager(lym);
        // Item Decorator
        RecyclerView.ItemDecoration id = new DividerItemDecoration(this, LinearLayout.VERTICAL);
        vStorehousesList.addItemDecoration(id);
        // Fixed Size
        vStorehousesList.setHasFixedSize(true);
        // Adapter
        DailyPlanAdapter adapter = new DailyPlanAdapter(this, mStorehouseList);
        vStorehousesList.setAdapter(adapter);
        showProgress(false);
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            vStorehousesList.setVisibility(show ? View.GONE : View.VISIBLE);
            vStorehousesList.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    vStorehousesList.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            fab.setVisibility(show ? View.GONE : View.VISIBLE);
            fab.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    fab.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            vLoadingLayout.setVisibility(show ? View.VISIBLE : View.GONE);
            vLoadingLayout.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    vLoadingLayout.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            vLoadingLayout.setVisibility(show ? View.VISIBLE : View.GONE);
            vStorehousesList.setVisibility(show ? View.GONE : View.VISIBLE);
            fab.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
