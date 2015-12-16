package cl.colabra.cvilches.insumosestrategicos.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
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

    // Selection attributes
    private SparseBooleanArray selectedItems;
    private boolean selectionModeEnabled;
    private OnStoreHouseSelected mListener;

    public DailyPlanAdapter(Context context, List<Storehouse> storehouses, OnStoreHouseSelected
            listener) {
        this.context = context;
        this.storehouses = storehouses;
        mDrawableBuilder = TextDrawable.builder()
                .beginConfig()
                    .fontSize(20)
                .endConfig()
                .round();
        this.selectedItems = new SparseBooleanArray();
        this.selectionModeEnabled = false;
        this.mListener = listener;
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

        if (selectionModeEnabled && selectedItems.get(position, false)) {
            updateCheckState(holder, storehouse, true);
        } else {
            updateCheckState(holder, storehouse, false);
        }
    }

    public int getItemCount() {
        return storehouses.size();
    }

    private void toggleSelection(DailyPlanViewHolder viewHolder, int position) {
        Storehouse selectedStorehouse = this.storehouses.get(position);
        if (selectedItems.get(position, false)) { // Item was selected
            // Remove it from selected items list
            selectedItems.delete(position);
            // Update view holder's state
            updateCheckState(viewHolder, selectedStorehouse, false);
        } else { // Item wasn't selected
            // Add item to the selected items list
            selectedItems.put(position, true);
            // Update view holder's state
            updateCheckState(viewHolder, selectedStorehouse, true);
        }
        // Tell the activity that the selection has changed
        mListener.selectionChanged(this.selectedItems.size());
    }

    private void updateCheckState(DailyPlanViewHolder viewHolder, Storehouse storehouse,
                                  Boolean checked) {
        if (checked) {
            viewHolder.mStockLights.setImageDrawable(
                    mDrawableBuilder.build(
                            " ", ContextCompat.getColor(
                                    context, R.color.selectedIconBackgroundColor)));
            viewHolder.mView.setBackgroundColor(
                    ContextCompat.getColor(context, R.color.selectedItemBackgroundColor));
            viewHolder.mCheckIcon.setVisibility(View.VISIBLE);
        } else {
            viewHolder.mStockLights.setImageDrawable(mDrawableBuilder
                    .build(getStockString(storehouse), getStockLight(storehouse)));
            viewHolder.mView.setBackgroundColor(
                    ContextCompat.getColor(context, R.color.backgroundColor));
            viewHolder.mCheckIcon.setVisibility(View.GONE);
        }
    }

    private String getStockString(Storehouse storehouse) {
        return String.format("%.1f", storehouse.getPercentageStock() * 100) + "%";
    }

    private int getStockLight(Storehouse storehouse) {
        switch (storehouse.getStockLight()) {
            case Storehouse.RED_LIGHTS:
                return ContextCompat.getColor(context, R.color.stockLightRed);
            case Storehouse.YELLOW_LIGHTS:
                return ContextCompat.getColor(context, R.color.stockLightYellow);
            case Storehouse.GREEN_LIGHTS:
                return ContextCompat.getColor(context, R.color.stockLightGreen);
            default:
                return ContextCompat.getColor(context, R.color.stockLightGreen);
        }
    }

    public void setSelectionMode(Boolean value) {
        this.selectionModeEnabled = value;
    }

    public void clearSelection() {
        for (int i = 0; i < selectedItems.size(); i++) {
            notifyItemChanged(i);
        }
        this.selectedItems.clear();
    }

    public interface OnStoreHouseSelected {
        void selectionChanged(int selectedNumber);
    }

    public class DailyPlanViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        private View mView;
        private TextView mStoreDescription;
        private TextView mLastReading;
        private ImageView mStockLights;
        private ImageView mCheckIcon;

        public DailyPlanViewHolder(View v) {
            super(v);
            this.mView = v;
            this.mStoreDescription = (TextView) v.findViewById(R.id.storehouse_description);
            this.mLastReading = (TextView) v.findViewById(R.id.last_reading);
            this.mStockLights = (ImageView) v.findViewById(R.id.stock_light);
            this.mCheckIcon = (ImageView) v.findViewById(R.id.check_icon);
            mStockLights.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (selectionModeEnabled) {
                toggleSelection(this, getAdapterPosition());
            }
        }
    }
}
