package cl.colabra.cvilches.insumosestrategicos;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;

public class RecordStock extends AppCompatActivity {

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

        // Set the loading data layout
        RelativeLayout vLoadingLayout = (RelativeLayout) findViewById(R.id.loading_layout);
        RelativeLayout vNoDataLayout = (RelativeLayout) findViewById(R.id.no_data_layout);
        RecyclerView vRegistersList = (RecyclerView) findViewById(R.id.registers_list);

    }

}
