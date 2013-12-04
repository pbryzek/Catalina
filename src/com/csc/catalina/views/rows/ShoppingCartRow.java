
package com.csc.catalina.views.rows;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ciety.framework.corecomponents.BaseCietyFrameworkApplication;
import com.csc.catalina.CatalinaApplication;
import com.csc.catalina.R;
import com.csc.catalina.adapters.ListRowAdapter;
import com.csc.catalina.constants.CatalinaConstants;
import com.csc.catalina.guava.IntMessage;

public class ShoppingCartRow extends ListRow {

    private static final String LOG_TAG = ShoppingCartRow.class.getName();

    private final String name;

    private final String quantity;

    private final double price;

    private final double savings;

    public ShoppingCartRow(String name, String quantity, double price, double savings) {
        super(ListRowAdapter.LIST_ROW_IDS.SHOPPING_CART.ordinal());
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.savings = savings;
    }

    public String getName() {
        return name;
    }

    public String getQuantity() {
        return quantity;
    }

    public double getSavings() {
        return savings;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public View createListRowDisplay(View currentRowView, ViewGroup parent, ListRowViewHolder viewHolder,
            final int position) {
        ShoppingCartRowViewHolder viewholder;
        if (currentRowView == null) {
            currentRowView = getRowView(R.layout.row_shopping_cart, parent);
            viewholder = new ShoppingCartRowViewHolder();
            viewholder.number = (TextView) currentRowView.findViewById(R.id.number);
            viewholder.name = (TextView) currentRowView.findViewById(R.id.name);
            viewholder.quantity = (TextView) currentRowView.findViewById(R.id.quantity);
            viewholder.price = (TextView) currentRowView.findViewById(R.id.price);
            viewholder.delete = (ImageView) currentRowView.findViewById(R.id.delete);
            viewholder.nameLayout = (RelativeLayout) currentRowView.findViewById(R.id.nameLayout);

            viewholder.nameLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    BaseCietyFrameworkApplication.getInstance().getEventBus()
                            .post(new IntMessage(LOG_TAG, CatalinaConstants.LAUNCH_ITEM_ROW, position));
                }
            });

            viewholder.delete.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    BaseCietyFrameworkApplication.getInstance().getEventBus()
                            .post(new IntMessage(LOG_TAG, CatalinaConstants.DELETE_ROW, position));
                }
            });

            currentRowView.setTag(viewholder);
        } else {
            viewholder = (ShoppingCartRowViewHolder) currentRowView.getTag();
            if (viewholder.number == null) {
                viewholder.number = (TextView) currentRowView.findViewById(R.id.number);
            }
            if (viewholder.name == null) {
                viewholder.name = (TextView) currentRowView.findViewById(R.id.name);
            }
            if (viewholder.quantity == null) {
                viewholder.quantity = (TextView) currentRowView.findViewById(R.id.quantity);
            }
            if (viewholder.price == null) {
                viewholder.price = (TextView) currentRowView.findViewById(R.id.price);
            }
            if (viewholder.delete == null) {
                viewholder.delete = (ImageView) currentRowView.findViewById(R.id.delete);
            }
            if (viewholder.nameLayout == null) {
                viewholder.nameLayout = (RelativeLayout) currentRowView.findViewById(R.id.nameLayout);
            }
        }

        if (viewholder.number != null) {
            String pos = position + 1 + ".";
            viewholder.number.setText(pos);
        }

        if (viewholder.name != null) {
            viewholder.name.setText(name);
        }

        if (viewholder.quantity != null) {
            viewholder.quantity.setText(quantity);
        }

        if (viewholder.price != null) {
            String priceStr = CatalinaApplication.getInstance().getResources().getString(R.string.price_str);
            priceStr = String.format(priceStr, price);
            viewholder.price.setText(priceStr);
        }

        return currentRowView;
    }

    protected static class ShoppingCartRowViewHolder extends ListRowViewHolder {
        RelativeLayout nameLayout;

        TextView number;
        TextView name;
        TextView quantity;
        TextView price;

        ImageView delete;
    }
}
