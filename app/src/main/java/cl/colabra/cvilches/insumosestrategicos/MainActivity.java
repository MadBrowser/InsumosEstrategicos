package cl.colabra.cvilches.insumosestrategicos;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.games.GamesMetadata;
import com.raizlabs.android.dbflow.runtime.TransactionManager;
import com.raizlabs.android.dbflow.runtime.transaction.SelectListTransaction;
import com.raizlabs.android.dbflow.runtime.transaction.TransactionListenerAdapter;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.sql.queriable.ModelQueriable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cl.colabra.cvilches.insumosestrategicos.fragments.DailyPlanFragment;
import cl.colabra.cvilches.insumosestrategicos.model.Storehouse;
import cl.colabra.cvilches.insumosestrategicos.utils.SessionManager;

public class MainActivity extends AppCompatActivity {

    /**
     * The Session Manager class
     *//*
    private SessionManager mSessionManager;

    // private List<Storehouse> mStorehouseList = new ArrayList<>();

    // Application fragments
    private DailyPlanFragment mDailyPlanFragment;

    private static final String TAG = "SGIE_Main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSessionManager = new SessionManager(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),
                MainActivity.this);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        getStoreListFromDB();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        *//*if (id == R.id.action_settings) {
            return true;
        } else*//*
        if (id == R.id.action_logout) {
            mSessionManager.logoutUser();
            startLoginActivity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void getStoreListFromDB() {
        ModelQueriable<Storehouse> query = new Select().from(Storehouse.class);
        TransactionManager.getInstance().addTransaction(new SelectListTransaction<>(
                query, getStoresTransactionListenerAdapter()
        ));
    }

    private TransactionListenerAdapter<List<Storehouse>> getStoresTransactionListenerAdapter() {
        return new TransactionListenerAdapter<List<Storehouse>>() {
            @Override
            public void onResultReceived(List<Storehouse> storehouses) {
                mStorehouseList = storehouses;
            }
        };
    }

    @Override
    public void onDailyPlanCreated(Uri uri) {

    }

    *//**
     * A placeholder fragment containing a simple view.
     *//*
    public static class PlaceholderFragment extends Fragment {
        *//**
         * The fragment argument representing the section number for this
         * fragment.
         *//*
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        *//**
         * Returns a new instance of this fragment for the given section
         * number.
         *//*
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    *//**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     *//*
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        // Page Count
        private static final int PAGE_COUNT = 3;

        private Context context;

        *//**
         * Tab Icons Array
         *//*
        private int imageResId[] = new int[] {
                R.drawable.ic_daily_plan,
                R.drawable.ic_register,
                R.drawable.ic_sync
        };

        *//**
         * Tab Titles
         *//*
        private String tabNames[] = new String[] {
                "Plan Diario",
                "Registro de Stock",
                "Sync Registros"
        };

        public SectionsPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    if (mDailyPlanFragment == null) {
                        return DailyPlanFragment.newInstance(mStorehouseList);
                    } else {
                        return mDailyPlanFragment;
                    }
                case 1:
                    return PlaceholderFragment.newInstance(position + 1);
                case 2:
                    return PlaceholderFragment.newInstance(position + 1);
                default:
                    return PlaceholderFragment.newInstance(position + 1);
            }
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            Drawable image = context.getResources().getDrawable(imageResId[position]);
            image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
            // Replace blank spaces with image icon
            SpannableString sb = new SpannableString("   " + tabNames[position]);
            ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
            sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return sb;
        }

    }*/
}
