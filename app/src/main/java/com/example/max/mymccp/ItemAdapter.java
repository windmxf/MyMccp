package com.example.max.mymccp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by maxiaofeng on 16/3/31.
 */
public class ItemAdapter extends ArrayAdapter {

    public ItemAdapter(Context context, int resource, int textViewResourceId, List objects) {
        super(context, resource, textViewResourceId, objects);
    }

}
