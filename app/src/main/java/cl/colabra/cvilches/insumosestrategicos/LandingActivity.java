package cl.colabra.cvilches.insumosestrategicos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import cl.colabra.cvilches.insumosestrategicos.utils.SessionManager;

public class LandingActivity extends AppCompatActivity {

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RelativeLayout vDailyPlanLayout = (RelativeLayout) findViewById(R.id.daily_plan);
        RelativeLayout vStockRegisterLayout = (RelativeLayout) findViewById(R.id.stock_register);
        RelativeLayout vSyncLayout = (RelativeLayout) findViewById(R.id.sync);

        vDailyPlanLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDailyPlanActivity();
            }
        });

        vStockRegisterLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegisterListActivity();
            }
        });

        vSyncLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDailyPlanActivity();
            }
        });

        sessionManager = new SessionManager(this);
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

    private void startDailyPlanActivity() {
        Intent intent = new Intent(this, DailyPlanActivity.class);
        startActivity(intent);
    }

    private void startRegisterListActivity() {
        /*Intent intent = new Intent(this, RecordListActivity.class);
        startActivity(intent);*/
    }
}
