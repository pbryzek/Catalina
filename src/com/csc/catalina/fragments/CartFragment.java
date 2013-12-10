
package com.csc.catalina.fragments;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.csc.catalina.CatalinaApplication;
import com.csc.catalina.R;
import com.csc.catalina.activities.HomeActivity;
import com.csc.catalina.adapters.HomeFragmentPagerAdapter;
import com.csc.catalina.adapters.ListRowAdapter;
import com.csc.catalina.views.rows.ShoppingCartRow;

public class CartFragment extends BaseFragment implements OnClickListener {

    private static final String LOG_TAG = CartFragment.class.getName();

    private TextView savings;
    private TextView savingsAmount;
    private TextView total;
    private TextView youSaved;

    private Button offers;
    private Button shop;
    private Button readyToShopBtn;
    private Button checkOutBtn;
    private Button scanBtn;

    private RelativeLayout readyToShop;
    private RelativeLayout checkOut;
    private RelativeLayout shoppingCart;
    private RelativeLayout shoppingCartAmount;

    private ListView cartList;

    private ListRowAdapter adapter;

    private static final int SAVINGS_AMOUNT = 50;

    private double price;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.cart, container, false);
        return rootView;
    }

    public ShoppingCartRow getRow(int position) {
        ShoppingCartRow row = (ShoppingCartRow) adapter.getItem(position);
        return row;
    }

    public void deleteRow(int position) {
        adapter.removeAtIndex(position);
        adapter.notifyDataSetChanged();
        setFooter();
    }

    private void setReadyToShopVisible() {
        readyToShop.setVisibility(View.VISIBLE);
        checkOut.setVisibility(View.GONE);
        shoppingCart.setVisibility(View.GONE);

        Resources res = CatalinaApplication.getInstance().getResources();
        float titleSize = res.getDimension(R.dimen.nanoFont);
        String savingsAvail = res.getString(R.string.savings_avail);
        savingsAvail = String.format(savingsAvail, SAVINGS_AMOUNT);
        savings.setText(savingsAvail);
        savings.setTextSize(titleSize);
    }

    private void setShoppingCartVisible() {
        readyToShop.setVisibility(View.GONE);
        checkOut.setVisibility(View.GONE);
        shoppingCart.setVisibility(View.VISIBLE);

        Resources res = CatalinaApplication.getInstance().getResources();

        float titleSize = res.getDimension(R.dimen.tinyFont);
        String shoppingCartStr = res.getString(R.string.shopping_cart);
        savings.setText(shoppingCartStr);
        savings.setTextSize(titleSize);
    }

    private void setCheckoutVisible() {
        readyToShop.setVisibility(View.GONE);
        checkOut.setVisibility(View.VISIBLE);
        shoppingCart.setVisibility(View.GONE);

        Resources res = CatalinaApplication.getInstance().getResources();
        float titleSize = res.getDimension(R.dimen.tinyFont);
        String checkOutStr = res.getString(R.string.check_out);
        savings.setText(checkOutStr);
        savings.setTextSize(titleSize);
    }

    private void setupAdapter() {
        adapter = new ListRowAdapter();
        adapter.addItem(new ShoppingCartRow("Ws Dk Cho Rsp", "Br12c21z", 4.5, 10));
        adapter.addItem(new ShoppingCartRow("S&S Fat Free Milk", "128oz", 2.79, 10));
        adapter.addItem(new ShoppingCartRow("Gar Ff H&H Crm", "32oz", 2.39, 10));
        adapter.addItem(new ShoppingCartRow("Sb Crescent Rolls", "8oz", 1.44, 10));
        adapter.addItem(new ShoppingCartRow("String Cheese", "12pk", 2.59, 10));
        adapter.addItem(new ShoppingCartRow("B Park Meat Franks", "1lb", 3.99, 3.4));
        adapter.addItem(new ShoppingCartRow("Lays Bbq Potato Chips", "16oz", 2.79, 0));
        cartList.setAdapter(adapter);
    }

    private void setFooter() {
        double totalPrice = 0;
        double totalSavings = 0;

        for (int i = 0; i < adapter.getCount(); i++) {
            ShoppingCartRow row = (ShoppingCartRow) adapter.getItem(i);
            totalSavings += row.getSavings();
            totalPrice += row.getPrice();
        }
        Resources res = CatalinaApplication.getInstance().getResources();
        String tot = res.getString(R.string.total);
        String saved = res.getString(R.string.you_saved);
        String totalStr = String.format(tot, totalPrice);
        String savedStr = String.format(saved, totalSavings);
        total.setText(totalStr);
        youSaved.setText(savedStr);

        savingsAmount.setText(totalStr);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        savings = (TextView) view.findViewById(R.id.savings);
        savingsAmount = (TextView) view.findViewById(R.id.savingsAmount);
        total = (TextView) view.findViewById(R.id.total);
        youSaved = (TextView) view.findViewById(R.id.youSaved);
        cartList = (ListView) view.findViewById(R.id.cartList);

        readyToShop = (RelativeLayout) view.findViewById(R.id.ready_to_shop_layout);
        checkOut = (RelativeLayout) view.findViewById(R.id.check_out_layout);
        shoppingCart = (RelativeLayout) view.findViewById(R.id.shopping_cart_layout);
        shoppingCartAmount = (RelativeLayout) view.findViewById(R.id.shoppingCartAmount);
        shoppingCartAmount.setOnClickListener(this);

        offers = (Button) view.findViewById(R.id.offers);
        offers.setOnClickListener(this);
        shop = (Button) view.findViewById(R.id.shop);
        shop.setOnClickListener(this);
        readyToShopBtn = (Button) view.findViewById(R.id.readyToShopBtn);
        readyToShopBtn.setOnClickListener(this);
        checkOutBtn = (Button) view.findViewById(R.id.checkout);
        checkOutBtn.setOnClickListener(this);
        scanBtn = (Button) view.findViewById(R.id.scanBtn);
        scanBtn.setOnClickListener(this);

        setReadyToShopVisible();

        setupAdapter();

        setFooter();
    }

    @Override
    public void onClick(View v) {
        if (v == offers) {
            HomeActivity activity = (HomeActivity) getActivity();
            if (activity != null) {
                activity.switchTab(HomeFragmentPagerAdapter.OFFERS_POSITION);
            }
        } else if (v == readyToShopBtn) {
            setShoppingCartVisible();
        } else if (v == shop) {

        } else if (v == checkOutBtn) {
            setCheckoutVisible();
        } else if (v == scanBtn) {
            HomeActivity activity = (HomeActivity) getActivity();
            if (activity != null) {
                activity.switchTab(HomeFragmentPagerAdapter.SCAN_POSITION);
            }
        } else if (v == shoppingCartAmount) {
            setReadyToShopVisible();
        }
    }
}
