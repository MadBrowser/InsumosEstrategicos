package cl.colabra.cvilches.insumosestrategicos;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.raizlabs.android.dbflow.runtime.TransactionManager;
import com.raizlabs.android.dbflow.runtime.transaction.SelectListTransaction;
import com.raizlabs.android.dbflow.runtime.transaction.TransactionListenerAdapter;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.sql.queriable.ModelQueriable;

import java.util.ArrayList;
import java.util.List;

import cl.colabra.cvilches.insumosestrategicos.model.DailyPlan;
import cl.colabra.cvilches.insumosestrategicos.model.Register;
import cl.colabra.cvilches.insumosestrategicos.model.Storehouse;
import cl.colabra.cvilches.insumosestrategicos.utils.DividerItemDecoration;
import cl.colabra.cvilches.insumosestrategicos.utils.SessionManager;

public class RecordStockListActivity extends AppCompatActivity {

    public static final String EXTRA_REGISTER_ID = "cl.colabra.insumosestratégicos.REGISTER_ID";

    private SessionManager mSessionManager;

    private DailyPlan mDailyPlan;
    private List<Register> mRegisters;
    private List<Storehouse> mStorehouses = new ArrayList<>();

    private RelativeLayout vLoadingLayout;
    private RelativeLayout vNoDataLayout;
    private RecyclerView vRecyclerView;

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
        vRecyclerView = (RecyclerView) findViewById(R.id.registers_list);

        if (mStorehouses.size() == 0 || mRegisters.size() == 0) {
            getDailyPlan();
        } else {
            setUpRecyclerView();
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
                    getRegistersData();
                }
            }
        };
    }

    private void showNoElementsLayout() {
        this.vLoadingLayout.setVisibility(View.GONE);
        this.vNoDataLayout.setVisibility(View.VISIBLE);
    }

    private void getRegistersData() {
        this.mRegisters = mDailyPlan.getMyPendingRegisters();
        for (Register register : this.mRegisters) {
            this.mStorehouses.add(register.storehouseFKContainer.load());
        }
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        // Layout Manager
        LinearLayoutManager lym = new LinearLayoutManager(this);
        vRecyclerView.setLayoutManager(lym);
        // Item Decorator
        RecyclerView.ItemDecoration id = new DividerItemDecoration(this, LinearLayout.VERTICAL);
        vRecyclerView.addItemDecoration(id);
        // Fixed Size
        vRecyclerView.setHasFixedSize(true);
        // Adapter
        RegisterAdapter mAdapter = new RegisterAdapter(this, mStorehouses);
        vRecyclerView.setAdapter(mAdapter);
        // Show progress
        showProgress(false);
        // Show Toast
        Toast.makeText(RecordStockListActivity.this, R.string.message_select_register,
                Toast.LENGTH_LONG).show();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            vRecyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
            vRecyclerView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    vRecyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            vRecyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private void startDetailActivity(int position) {
        long registerId = this.mRegisters.get(position).getId();
        Intent intent = new Intent(this, RecordStockDetailActivity.class);
        intent.putExtra(EXTRA_REGISTER_ID, registerId);
        startActivity(intent);
    }

    public class RegisterAdapter extends RecyclerView.Adapter<RegisterAdapter.RegisterViewHolder> {

        private Context mContext;
        private List<Storehouse> mStorehouses;
        private TextDrawable.IBuilder mDrawableBuilder;

        public RegisterAdapter(Context mContext, List<Storehouse> mStorehouses) {
            this.mContext = mContext;
            this.mStorehouses = mStorehouses;
            this.mDrawableBuilder = TextDrawable.builder()
                    .beginConfig()
                    .fontSize(20)
                    .endConfig()
                    .round();
        }

        @Override
        public RegisterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View RegisterView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.viewholder_register, parent, false);
            return new RegisterViewHolder(RegisterView);
        }

        @Override
        public void onBindViewHolder(RegisterViewHolder viewHolder, int position) {
            Storehouse storehouse = mStorehouses.get(position);
            viewHolder.mStoreDescription.setText(storehouse.getDescription());
            viewHolder.mLastReading.setText(mContext.getString(R.string.last_reading,
                    storehouse.getLastReading()));
            viewHolder.mStockLights.setImageDrawable(mDrawableBuilder
                    .build(getStockString(storehouse), getStockLight(storehouse)));
        }

        @Override
        public int getItemCount() {
            return this.mStorehouses.size();
        }

        private String getStockString(Storehouse storehouse) {
            return String.format("%.1f", storehouse.getPercentageStock() * 100) + "%";
        }

        private int getStockLight(Storehouse storehouse) {
            switch (storehouse.getStockLight()) {
                case Storehouse.RED_LIGHTS:
                    return ContextCompat.getColor(mContext, R.color.stockLightRed);
                case Storehouse.YELLOW_LIGHTS:
                    return ContextCompat.getColor(mContext, R.color.stockLightYellow);
                case Storehouse.GREEN_LIGHTS:
                    return ContextCompat.getColor(mContext, R.color.stockLightGreen);
                default:
                    return ContextCompat.getColor(mContext, R.color.stockLightGreen);
            }
        }

        public class RegisterViewHolder extends RecyclerView.ViewHolder implements
                View.OnClickListener {

            private View mView;
            private TextView mStoreDescription;
            private TextView mLastReading;
            private ImageView mStockLights;

            public RegisterViewHolder(View v) {
                super(v);
                this.mView = v;
                this.mStoreDescription = (TextView) v.findViewById(R.id.storehouse_description);
                this.mLastReading = (TextView) v.findViewById(R.id.last_reading);
                this.mStockLights = (ImageView) v.findViewById(R.id.stock_light);
                this.mView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                startDetailActivity(getAdapterPosition());
            }
        }
    }
}
