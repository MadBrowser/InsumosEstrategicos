package cl.colabra.cvilches.insumosestrategicos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.raizlabs.android.dbflow.runtime.TransactionManager;
import com.raizlabs.android.dbflow.runtime.transaction.SelectListTransaction;
import com.raizlabs.android.dbflow.runtime.transaction.TransactionListenerAdapter;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.sql.queriable.ModelQueriable;

import java.util.List;

import cl.colabra.cvilches.insumosestrategicos.model.DailyPlan;
import cl.colabra.cvilches.insumosestrategicos.utils.SessionManager;

public class RecordStockActivity extends AppCompatActivity {

    private SessionManager mSessionManager;

    private DailyPlan mDailyPlan;

    private RelativeLayout vLoadingLayout;
    private RelativeLayout vNoDataLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_stock);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mSessionManager = new SessionManager(this);

        vLoadingLayout = (RelativeLayout) findViewById(R.id.loading_layout);
        vNoDataLayout = (RelativeLayout) findViewById(R.id.no_data_layout);

        getDailyPlan();
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
                mSessionManager.logoutUser();
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

    private void getDailyPlan() {
        ModelQueriable<DailyPlan> query = new Select().from(DailyPlan.class)
                .orderBy(false, "createdAt");
        TransactionManager.getInstance().addTransaction(new SelectListTransaction<>(query,
                getDailyPlanTransactionListenerAdapter()));
    }

    private TransactionListenerAdapter<List<DailyPlan>> getDailyPlanTransactionListenerAdapter() {
        return new TransactionListenerAdapter<List<DailyPlan>>() {
            @Override
            public void onResultReceived(List<DailyPlan> dailyPlans) {
                if (dailyPlans.size() == 0) {
                    showNoElementsLayout();
                } else {
                    mDailyPlan = dailyPlans.get(0);
                }
            }
        };
    }

    private void showNoElementsLayout() {
        this.vLoadingLayout.setVisibility(View.GONE);
        this.vNoDataLayout.setVisibility(View.VISIBLE);
    }
}
