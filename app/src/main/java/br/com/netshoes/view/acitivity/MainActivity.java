package br.com.netshoes.view.acitivity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import br.com.netshoes.R;
import br.com.netshoes.view.fragment.ProductListFragment;
import butterknife.Bind;

public class MainActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    int inflateLayout() {
        return R.layout.activity_main;
    }

    @Override
    void init(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);

        applyToolBarTitle(this.getString(R.string.app_name));

        attachFragment(new ProductListFragment(), R.id.frame_container);
    }
}