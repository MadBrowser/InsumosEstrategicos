package cl.colabra.cvilches.insumosestrategicos.fragments;

import android.support.v4.app.Fragment;

public class DailyPlanFragment extends Fragment {

    /*// Interface listener
    private OnDailyPlanFragmentInteractionListener mListener;

    // Logic variables
    private List<Storehouse> mStorehouses;

    // View elements
    private FloatingActionButton mFab;
    private RecyclerView mRecyclerView;

    public DailyPlanFragment() {
        // Required empty public constructor
    }

    public static DailyPlanFragment newInstance(List<Storehouse> storehouses) {
        DailyPlanFragment fm = new DailyPlanFragment();
        fm.mStorehouses = storehouses;
        return fm;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Set-up view
        View layout = inflater.inflate(R.layout.fragment_daily_plan, container, false);

        // Set-up recycler view
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.storehouses_list);
        // Layout Manager
        LinearLayoutManager lym = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(lym);
        // Item Decorator
        RecyclerView.ItemDecoration id = new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL);
        mRecyclerView.addItemDecoration(id);
        // Fixed Size
        mRecyclerView.setHasFixedSize(true);
        // Adapter
        DailyPlanAdapter adapter = new DailyPlanAdapter(getActivity(), mStorehouses);
        mRecyclerView.setAdapter(adapter);

        // Floating Action Button
        mFab = (FloatingActionButton) layout.findViewById(R.id.fab);

        return layout;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onDailyPlanCreated(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDailyPlanFragmentInteractionListener) {
            mListener = (OnDailyPlanFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDailyPlanFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnDailyPlanFragmentInteractionListener {
        void onDailyPlanCreated(Uri uri);
    }

    public class DailyPlanAdapter extends RecyclerView.Adapter<DailyPlanAdapter.DailyPlanViewHolder> {

        private Context context;
        private List<Storehouse> storehouses;
        private TextDrawable.IBuilder mDrawableBuilder;

        public DailyPlanAdapter(Context context, List<Storehouse> storehouses) {
            this.context = context;
            this.storehouses = storehouses;
            mDrawableBuilder = TextDrawable.builder().round();
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
            String stockValue = Float.toString(storehouse.getPercentageStock());
            int color;

            switch (storehouse.getStockLight()) {
                case Storehouse.RED_LIGHTS:
                    color = R.color.stockLightRed;
                    break;
                case Storehouse.YELLOW_LIGHTS:
                    color = R.color.stockLightYellow;
                    break;
                case Storehouse.GREEN_LIGHTS:
                    color = R.color.stockLightGreen;
                    break;
                default:
                    color = R.color.stockLightGreen;
                    break;
            }
            holder.mStockLights.setImageDrawable(mDrawableBuilder.build(stockValue, color));
        }

        public int getItemCount() {
            return mStorehouses.size();
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
    }*/

}
