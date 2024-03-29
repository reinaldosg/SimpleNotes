package com.example.hawk.mydb;

import android.view.View;

public class CustomOnClickListener implements View.OnClickListener {

    public int position;
    public OnItemClickCallback onItemClickCallback;

    public CustomOnClickListener(int position, OnItemClickCallback onItemClickCallback) {
        this.position = position;
        this.onItemClickCallback = onItemClickCallback;
    }


    @Override
    public void onClick(View view) {
        onItemClickCallback.onItemClicked(view,position);
    }

    public interface OnItemClickCallback{
        void onItemClicked(View view, int position);
    }


}
