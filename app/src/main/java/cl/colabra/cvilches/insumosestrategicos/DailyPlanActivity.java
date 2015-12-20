package cl.colabra.cvilches.insumosestrategicos;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.raizlabs.android.dbflow.runtime.TransactionManager;
import com.raizlabs.android.dbflow.runtime.transaction.SelectListTransaction;
import com.raizlabs.android.dbflow.runtime.transaction.TransactionListenerAdapter;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.sql.queriable.ModelQueriable;
import com.raizlabs.android.dbflow.structure.container.ForeignKeyContainer;

import java.util.ArrayList;
import java.util.List;

import cl.colabra.cvilches.insumosestrategicos.adapters.DailyPlanAdapter;
import cl.colabra.cvilches.insumosestrategicos.model.DailyPlan;
import cl.colabra.cvilches.insumosestrategicos.model.Register;
import cl.colabra.cvilches.insumosestrategicos.model.Storehouse;
import cl.colabra.cvilches.insumosestrategicos.utils.DividerItemDecoration;
import cl.colabra.cvilches.insumosestrategicos.utils.SessionManager;

public class DailyPlanActivity extends AppCompatActivity implements
        View.OnClickListener,
        DailyPlanAdapter.OnStoreHouseSelected,
        ActionMode.Callback {

    private List<Storehouse> mStorehouseList = new ArrayList<>();

    private RelativeLayout vLoadingLayout;
    private FloatingActionButton fab;

    private RecyclerView vStorehousesList;
    private DailyPlanAdapter mAdapter;

    private SessionManager sessionManager;

    private ActionMode mActionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_plan);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

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
        mAdapter = new DailyPlanAdapter(this, mStorehouseList, this);
        vStorehousesList.setAdapter(mAdapter);
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

    private void setActionModeTitle(String title) {
        this.mActionMode.setTitle(title);
    }

    @Override
    public void onClick(View v) {
        if (mActionMode == null) {
            mAdapter.setSelectionMode(true);
            this.mActionMode = startActionMode(this);
            setActionModeTitle(getString(R.string.message_select_storehouses));
        } else {
            createDailyPlan();
            mActionMode.finish();
        }
        updateFabState();
    }

    private void updateFabState() {
        if (mActionMode != null) {
            fab.setBackgroundTintList(ColorStateList.valueOf(
                    ContextCompat.getColor(this, R.color.colorPrimary)));
            fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_check));
        } else {
            fab.setBackgroundTintList(ColorStateList.valueOf(
                    ContextCompat.getColor(this, R.color.colorAccent)));
            fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_daily_plan));
        }
    }

    private void createDailyPlan() {
        List<Storehouse> storehouses = mAdapter.getSelectedStorehouses();
        if (storehouses.size() == 0) { // If there aren't selected storehouses, show alert
            Toast.makeText(DailyPlanActivity.this,
                    R.string.message_select_at_least_one_storehouse,
                    Toast.LENGTH_LONG).show();
        } else {
            DailyPlan dailyPlan = new DailyPlan();
            dailyPlan.save();
            List<Register> registers = new ArrayList<>();
            for (Storehouse storehouse : storehouses) {
                // Create the register
                Register register = new Register();
                register.save();
                register.associateDailyPlan(dailyPlan);
                register.associateStorehouse(storehouse);
                register.save();
                registers.add(register);
                // Associate the register with the corresponding storehouse
                storehouse.registerList.add(register);
                storehouse.save();
            }
            dailyPlan.registerList = registers;
            dailyPlan.save();
            Toast.makeText(DailyPlanActivity.this,
                    R.string.message_daily_plan_created,
                    Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.menu_daily_plan_cab, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mode.finish();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        mAdapter.setSelectionMode(false);
        mAdapter.clearSelection();
        mActionMode = null;
        updateFabState();
    }

    @Override
    public void selectionChanged(int selectedNumber) {
        setActionModeTitle(getString(R.string.message_selected_count, selectedNumber));
    }
}
