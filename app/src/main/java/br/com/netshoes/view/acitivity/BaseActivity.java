package br.com.netshoes.view.acitivity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.ButterKnife;

/**
 * Created by Caramelo on 14/01/2016.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private Toolbar toolbar;

    abstract int inflateLayout();
    abstract void init(Bundle savedInstanceState);

    FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout for this activity
        setContentView(inflateLayout());

        ButterKnife.bind(this);

        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                for (Fragment fragment : mFragmentManager.getFragments()) {
                    if (fragment != null) {
                        if (fragment.isVisible())
                            fragment.onResume();
                    }
                }
            }
        });

        init(savedInstanceState);
    }

    public void applyToolBarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    public void applyHomeButton(boolean enabled) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(enabled);
        }
    }

    public void attachFragment(@NonNull Fragment fragment, Bundle args, int container) {
        fragment.setArguments(args);
        if (mFragmentManager != null) {
            mFragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .add(container, fragment)
                    .commit();
        }
    }
    public void attachFragment(@NonNull Fragment fragment, int container) {
        attachFragment(fragment, getIntent().getExtras(), container);
    }

    @Override
    public void onBackPressed() {
        if (mFragmentManager != null) {
            if (mFragmentManager.getBackStackEntryCount() > 1) {
                mFragmentManager.popBackStack();
            } else {
                //super.onBackPressed();
            }
        }
    }
}
