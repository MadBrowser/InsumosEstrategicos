package cl.colabra.cvilches.insumosestrategicos.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;

import java.util.List;

import cl.colabra.cvilches.insumosestrategicos.R;
import cl.colabra.cvilches.insumosestrategicos.model.Storehouse;

/**
 * Project: InsumosEstrategicos.
 * Created by Carlos Vilches on 12/15/15. By appointment
 * of Colabra for client 'Minera Collahuasi'
 */
public class DailyPlanAdapter extends RecyclerView.Adapter<DailyPlanAdapter.DailyPlanViewHolder> {

    private Context context;
    private List<Storehouse> storehouses;
    private TextDrawable.IBuilder mDrawableBuilder;

    public DailyPlanAdapter(Context context, List<Storehouse> storehouses) {
        this.context = context;
        this.storehouses = storehouses;
        mDrawableBuilder = TextDrawable.builder()
                .beginConfig()
                    .fontSize(25)
                .endConfig()
                .round();
    }

    @Override
    public DailyPlanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View DailyPlanView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_storehouse, parent, false);
        return new DailyPlanViewHolder(DailyPlanView);
    }

    @Override
    public void onBindViewHolder(DailyPlanViewHolder holder, int position) {
        Storehouse storehouse = storehouses.get(position);
        holder.mStoreDescription.setText(storehouse.getDescription());
        holder.mLastReading.setText(context.getString(R.string.last_reading,
                storehouse.getLastReading()));
        int stockValue = (int) storehouse.getPercentageStock() * 100;
        String stockValueStr = Integer.toString(stockValue) + "%";
        int color;

        switch (storehouse.getStockLight()) {
            case Storehouse.RED_LIGHTS:
                color = ContextCompat.getColor(context, R.color.stockLightRed);
                break;
            case Storehouse.YELLOW_LIGHTS:
                color = ContextCompat.getColor(context, R.color.stockLightYellow);
                break;
            case Storehouse.GREEN_LIGHTS:
                color = ContextCompat.getColor(context, R.color.stockLightGreen);
                break;
            default:
                color = ContextCompat.getColor(context, R.color.stockLightGreen);
                break;
        }
        holder.mStockLights.setImageDrawable(mDrawableBuilder.build(stockValueStr, color));
    }

    public int getItemCount() {
        return storehouses.size();
    }

    public class DailyPlanViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        private TextView mStoreDescription;
        private TextView mLastReading;
        private ImageView mStockLights;

        public DailyPlanViewHolder(View v) {
            super(v);
            this.mStoreDescription = (TextView) v.findViewById(R.id.storehouse_description);
            this.mLastReading = (TextView) v.findViewById(R.id.last_reading);
            this.mStockLights = (ImageView) v.findViewById(R.id.stock_light);
            mStockLights.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
