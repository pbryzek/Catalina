
package com.csc.catalina.views.rows;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csc.catalina.CatalinaApplication;

public abstract class ListRow {

    protected int listViewTypeId;

    protected View headerView;

    public ListRow(int typeId) {
        listViewTypeId = typeId;
    }

    public int getListViewType() {
        return listViewTypeId;
    }

    protected View getRowView(int layoutId, ViewGroup parent) {
        return getRowView(layoutId, parent, false);
    }

    protected View getRowView(int layoutId, ViewGroup parent, boolean attachToRoot) {
        Context context = CatalinaApplication.getInstance();
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(layoutId, parent, attachToRoot);
    }

    public abstract View createListRowDisplay(View currentRowView, ViewGroup parent,
            ListRowViewHolder viewHolder, int position);

    protected static class ListRowViewHolder {
    }
}
