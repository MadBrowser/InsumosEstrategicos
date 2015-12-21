package cl.colabra.cvilches.insumosestrategicos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.raizlabs.android.dbflow.sql.builder.Condition;
import com.raizlabs.android.dbflow.sql.language.Select;

import cl.colabra.cvilches.insumosestrategicos.model.Register;
import cl.colabra.cvilches.insumosestrategicos.model.Storehouse;

public class RecordStockDetailActivity extends AppCompatActivity {

    private Register mRegister;
    private Storehouse mStorehouse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_stock_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        long registerId = intent.getLongExtra(RecordStockListActivity.EXTRA_REGISTER_ID, 0);

        getDataFromDB(registerId);
        fillFormData();
    }

    private void getDataFromDB(long registerId) {
        this.mRegister = new Select()
                .from(Register.class)
                .where(Condition.column("id").is(registerId))
                .querySingle();
        this.mStorehouse = this.mRegister.storehouseFKContainer.load();
    }

    private void fillFormData() {

    }
}