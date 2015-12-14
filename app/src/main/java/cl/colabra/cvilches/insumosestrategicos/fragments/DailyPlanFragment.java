package cl.colabra.cvilches.insumosestrategicos.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cl.colabra.cvilches.insumosestrategicos.R;
import cl.colabra.cvilches.insumosestrategicos.model.Storehouse;

public class DailyPlanFragment extends Fragment {

    // Interface listener
    private OnDailyPlanFragmentInteractionListener mListener;

    // Logic variables
    private List<Storehouse> mStorehouses;

    // View elements
    private FloatingActionButton fab;

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



        // Floating Action Button
        fab = (FloatingActionButton) layout.findViewById(R.id.fab);

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



}
