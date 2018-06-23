package com.example.ridko.warehousepda.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.example.ridko.warehousepda.R;
import com.example.ridko.warehousepda.adapter.DrawerListAdapter;
import com.example.ridko.warehousepda.application.App;
import com.example.ridko.warehousepda.been.DrawerListContent;
import com.example.ridko.warehousepda.fragment.AboutFragment;
import com.example.ridko.warehousepda.fragment.CheckClothFragment;
import com.example.ridko.warehousepda.fragment.CutClothFragment;
import com.example.ridko.warehousepda.fragment.DeviceManagmentFragment;
import com.example.ridko.warehousepda.fragment.HomeFragment;
import com.example.ridko.warehousepda.fragment.SpecialStorageFragment;
import com.example.ridko.warehousepda.fragment.StockRemovalFragment;
import com.example.ridko.warehousepda.fragment.StockUpFragment;
import com.example.ridko.warehousepda.fragment.UnBindAndBindFragment;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //Tag to identify the currently displayed fragment
    protected static final String TAG_CONTENT_FRAGMENT = "ContentFragment";
    @Bind(R.id.content_frame)
    FrameLayout mFrame;
    @Bind(R.id.left_drawer)
    ListView mDrawerList;
    //导航布局
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerlayout;

    //     导航数组
    private String[] mOptionTitles;
    //    ActionBar样式
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mTitle = mDrawerTitle = getTitle();
        mOptionTitles = getResources().getStringArray(R.array.options_array);
//        mDrawerlayout.setDrawerShadow(R.mipmap.drawer_shadow, GravityCompat.START);
        mDrawerList.setAdapter(new DrawerListAdapter(this, R.layout.item_drawer_list, DrawerListContent.ITEMS));
        mDrawerList.setOnItemClickListener(new DrawerItemListener());
        //激活ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerlayout,
                R.mipmap.ic_drawer,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        ) {
            /**
             * 当一个抽屉完全关闭的时候调用此方法
             */
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                int drawableRsourceId = getActionBarIcon();
                if (drawableRsourceId != -1)
                    getSupportActionBar().setIcon(drawableRsourceId);
                getSupportActionBar().setTitle(mTitle);
                supportInvalidateOptionsMenu();
           /*     getActionBar().setTitle(mTitle);
                invalidateOptionsMenu();*/
            }

            /**
             * 当一个抽屉被完全打开的时候被调用
             */
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                drawerView.setClickable(true);
                getSupportActionBar().setTitle(mDrawerTitle);
                getSupportActionBar().setIcon(R.drawable.emoji9);
                supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerlayout.setDrawerListener(mActionBarDrawerToggle);
//        if(savedInstanceState!=null)
        selectItem(0);

    }

    /* Called whenever we call invalidateOptionsMenu() */
    boolean drawerOpen = false;

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        drawerOpen = mDrawerlayout.isDrawerOpen(mDrawerList);
//        不止为何导致actionbar变大
       /* if (drawerOpen){
//            当抽屉打开时，隐藏键盘
            InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            if (getCurrentFocus()!=null)
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
            if (getSupportActionBar()!=null){
            //隐藏标签，如果它们是可见的。
                getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            }
        }else{
            if (getSupportActionBar()!=null){
                if (getSupportFragmentManager()!=null&&getSupportFragmentManager().findFragmentByTag(TAG_CONTENT_FRAGMENT)!=null)
                    getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
            }
        }*/

        return super.onPrepareOptionsMenu(menu);
    }

    protected Menu menu;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggle
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mActionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG_CONTENT_FRAGMENT);
        if (fragment != null && (fragment instanceof AboutFragment || fragment instanceof HomeFragment || fragment instanceof
                CheckClothFragment || fragment instanceof CutClothFragment || fragment instanceof DeviceManagmentFragment
                || fragment instanceof SpecialStorageFragment || fragment instanceof StockUpFragment || fragment instanceof StockRemovalFragment
        )) {

            //update the selected item in the drawer and the title
            mDrawerList.setItemChecked(0, true);
            setTitle(mOptionTitles[0]);
            //We are handling back pressed for saving pre-filters settings. Notify the appropriate fragment.
            //{@link BaseReceiverActivity # onBackPressed should be called by the fragment when the processing is done}
            //super.onBackPressed();

            if (fragment != null && fragment instanceof HomeFragment) {
                ((HomeFragment) fragment).onBackPressed();
            } else {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mActionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        // Handle action buttons
//        右上角actionBar监听
      /*  switch (item.getItemId()) {
            case R.id.action_info:
                aboutClicked();
                return true;
            default:
                return super.onOptionsItemSelected(item);
          }*/
        return false;
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        App.activityResumed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        App.activityPaused();
    }

    //特殊入库
    public void specialClicked(View view) {
        selectItem(1);
    }

    //    解绑与绑定
    public void unBindOrBindClicked(View view) {
        selectItem(2);
    }

    //    剪布
    public void cuttingClothClicked(View view) {
        selectItem(3);
    }

    //    查布
    public void checkingClothClicked(View view) {
        selectItem(4);
    }

    //    备货
    public void stockUpClicked(View view) {
        selectItem(5);
    }

    //    出库
    public void stockRemovalClicked(View view) {
        selectItem(6);
    }

    //    设备管理
    public void deviceManagmentClicked(View view) {
        selectItem(7);
    }

    //    关于
    public void aboutClicked(View view) {
        selectItem(8);
    }

    public void aboutClicked() {
        selectItem(8);
    }

    //点击以改变当前UI状态
    private void selectItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = HomeFragment.newInstance();
                break;
            case 1:
                fragment = SpecialStorageFragment.newInstance();
                break;
            case 2:
                fragment = UnBindAndBindFragment.newInstance();
                break;
            case 3:
                fragment = CutClothFragment.newInstance();
                break;
            case 4:
                fragment = CheckClothFragment.newInstance();
                break;
            case 5:
                fragment = StockUpFragment.newInstance();
                break;
            case 6:
                fragment = StockRemovalFragment.newInstance();
                break;
            case 7:
                fragment = DeviceManagmentFragment.newInstance();
                break;
            case 8:
                fragment = AboutFragment.newInstance();
                break;
            default:
                break;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (position == 0) {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment, TAG_CONTENT_FRAGMENT).commit();
        } else {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment, TAG_CONTENT_FRAGMENT).addToBackStack(null).commit();
        }
        mDrawerList.setItemChecked(position, true);
        setTitle(mOptionTitles[position]);
        mDrawerlayout.closeDrawer(mDrawerList);
    }

    //获取当前显示的动作栏Action图标的方法
    private int getActionBarIcon() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG_CONTENT_FRAGMENT);
        if (fragment instanceof HomeFragment)
            return R.drawable.emoji0;
        else if (fragment instanceof SpecialStorageFragment)
            return R.drawable.emoji1;
        else if (fragment instanceof UnBindAndBindFragment)
            return R.drawable.emoji2;
        else if (fragment instanceof CutClothFragment)
            return R.drawable.emoji3;
        else if (fragment instanceof CheckClothFragment)
            return R.drawable.emoji4;
        else if (fragment instanceof StockUpFragment)
            return R.drawable.emoji5;
        else if (fragment instanceof DeviceManagmentFragment)
            return R.drawable.emoji6;
        else if (fragment instanceof StockRemovalFragment)
            return R.drawable.emoji7;
        else if (fragment instanceof AboutFragment)
            return R.drawable.emoji8;
        else
            return R.drawable.emoji9;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() != R.id.list_layout && drawerOpen) {
            mDrawerlayout.closeDrawers();
        }
    }

    private class DrawerItemListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if (i > -1 && i < mOptionTitles.length)
                selectItem(i);
        }
    }
}
