package com.gao.blueblue.ui.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gao.blueblue.R;


public class BaseFragment extends Fragment {

    /**上下文**/
    public FragmentActivity mContext;
    protected View fragmentView;
    private static final String TAG = "BaseFragment";

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.mContext = getActivity();
        Log.e(TAG, "onActivityCreated: "+this.getClass().toString() );
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: "+this.getClass().toString() );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: "+this.getClass().toString() );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(TAG, "onDestroyView: "+this.getClass().toString() );
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: "+this.getClass() );
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: "+this.getClass() );
    }
}