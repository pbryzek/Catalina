
package com.csc.catalina.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.csc.catalina.views.rows.ListRow;

import java.util.ArrayList;
import java.util.List;

public class ListRowAdapter extends BaseAdapter {

    private final ArrayList<ListRow> rows = new ArrayList<ListRow>();

    // The pull to refresh bit in the listview makes the position inconsistent
    // with the rows we keep locally (off by 1).
    private final boolean pullToRefresh;

    /** Array of list view type identifiers. */
    public enum LIST_ROW_IDS {
        SHOPPING_CART,
        // This must be the last element in the enum
        COUNT
    }

    @Override
    public boolean isEnabled(int position) {
        ListRow row = rows.get(position);
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    public ListRowAdapter(boolean pullToRefresh) {
        this.pullToRefresh = pullToRefresh;
    }

    public ListRowAdapter() {
        pullToRefresh = false;
    }

    public void insert(ListRow row, int to) {
        rows.add(to, row);
        notifyDataSetChanged();
    }

    public void removeAtIndex(int i) {
        if (i < rows.size()) {
            rows.remove(i);
            notifyDataSetChanged();
        }
    }

    public void remove(ListRow row) {
        if (rows.contains(row)) {
            rows.remove(row);
        }
        notifyDataSetChanged();
    }

    /**
     * Method used to update the current adapter with the new one. This process is done iteratively so that the rows are
     * never empty in the update transition so user does not unexpectedly see the empty market screen when he should
     * not.
     * 
     * @param newAdapter
     */
    public void updateAdapterWithNewAdapter(ListRowAdapter newAdapter) {
        int newAdapterSize = newAdapter.rows.size();
        int oldAdapterSize = rows.size();

        for (int i = 0; i < newAdapterSize; i++) {
            if (oldAdapterSize > i) {
                rows.set(i, newAdapter.rows.get(i));
            } else {
                rows.add(newAdapter.rows.get(i));
            }
        }

        if (newAdapterSize == 0) {
            clearAdapter();
        } else if (newAdapterSize > 0 && newAdapterSize < oldAdapterSize) {
            for (int i = oldAdapterSize - 1; i >= newAdapterSize; i--) {
                rows.remove(i);
            }
        }
    }

    public void clearAdapter() {
        if (rows != null) {
            rows.clear();
            System.gc();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (rows.size() > 0) {
            ListRow row = rows.get(position);
            return row.getListViewType();
        }
        return 0;
    }

    public ListRow getRowByPosition(int index) {
        if (pullToRefresh) {
            if (index == 0) {
                return null;
            }
            index--;
        }
        if (rows != null && rows.size() > index) {
            return rows.get(index);
        } else {
            return null;
        }
    }

    public void addList(final List<ListRow> listviewRows) {
        for (ListRow row : listviewRows) {
            rows.add(row);
        }
        notifyDataSetChanged();
    }

    public void addItem(final ListRow row) {
        rows.add(row);
        notifyDataSetChanged();
    }

    @Override
    public Object getItem(int position) {
        if (pullToRefresh) {
            if (position == 0) {
                return null;
            }
            position--;
        }
        return rows.get(position);
    }

    @Override
    public long getItemId(int position) {
        if (pullToRefresh) {
            if (position == 0) {
                return -1;
            }
            position--;
        }
        return rows.get(position).getListViewType();
    }

    public void updateRow(ListRow row, int position) {
        if (pullToRefresh) {
            if (position == 0) {
                return;
            }
            position--;
        }
        rows.set(position, row);
    }

    @Override
    public int getViewTypeCount() {
        return LIST_ROW_IDS.COUNT.ordinal();
    }

    @Override
    public int getCount() {
        return rows.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListRow row = rows.get(position);
        if (row != null) {
            convertView = row.createListRowDisplay(convertView, parent, null, position);
        }

        return convertView;
    }
}
