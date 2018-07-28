package com.example.ridko.warehousepda.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.ridko.warehousepda.R;
import com.example.ridko.warehousepda.activity.MainActivity;

/**
 * Created by mumu on 2018/3/31.
 */

public class HomeFragment extends Fragment {

    public static HomeFragment newInstance(){
        return  new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_layout,container,false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_info:
                ((MainActivity)getActivity()).aboutClicked();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
//可以不要
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            //Set the navigation mode to standard
            ((AppCompatActivity) getActivity()).getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);

            //Change the icon
            ((AppCompatActivity) getActivity()).getSupportActionBar().setIcon(R.drawable.about);
        }
    }
    public void onBackPressed(){}
}
