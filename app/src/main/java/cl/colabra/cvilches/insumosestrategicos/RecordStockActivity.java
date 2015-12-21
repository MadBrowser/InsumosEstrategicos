package cl.colabra.cvilches.insumosestrategicos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import java.util.List;

import cl.colabra.cvilches.insumosestrategicos.model.Register;
import cl.colabra.cvilches.insumosestrategicos.model.Storehouse;
import cl.colabra.cvilches.insumosestrategicos.utils.SessionManager;

public class RecordStockActivity extends AppCompatActivity {

    private SessionManager sessionManager;

    private List<Register> registerList;
    private List<Storehouse> storehouseList;

    private RelativeLayout vLoadingLayout;
    private RelativeLayout vNoDataLayout;
    private RecyclerView vRegistersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_stock);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set the loading data layout
        vLoadingLayout = (RelativeLayout) findViewById(R.id.loading_layout);
        vNoDataLayout = (RelativeLayout) findViewById(R.id.no_data_layout);
        vRegistersList = (RecyclerView) findViewById(R.id.registers_list);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        sessionManager = new SessionManager(this);

        if (sessionManager.isLoggedIn()) {
            if (registerList.size() == 0 || storehouseList.size() == 0) {

            } else {

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


}
