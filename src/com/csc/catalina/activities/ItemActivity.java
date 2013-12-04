
package com.csc.catalina.activities;

import android.os.Bundle;
import android.widget.TextView;

import com.csc.catalina.CatalinaApplication;
import com.csc.catalina.R;
import com.csc.catalina.constants.CatalinaConstants;

public class ItemActivity extends BaseActivity {

    private TextView nameTv;
    private TextView quantityTv;
    private TextView priceTv;
    private TextView savingsTv;

    private String name;
    private String quantity;

    private double price;
    private double savings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_page);

        Bundle extras = getIntent().getExtras();
        name = extras.getString(CatalinaConstants.INTENT_NAME);
        quantity = extras.getString(CatalinaConstants.INTENT_QUANTITY);
        price = extras.getDouble(CatalinaConstants.INTENT_PRICE);
        savings = extras.getDouble(CatalinaConstants.INTENT_SAVINGS);

        nameTv = (TextView) findViewById(R.id.name);
        nameTv.setText(name);
        quantityTv = (TextView) findViewById(R.id.quantity);
        quantityTv.setText(quantity);

        String priceStr = CatalinaApplication.getInstance().getResources().getString(R.string.price);
        String savingsStr = CatalinaApplication.getInstance().getResources().getString(R.string.savings);
        priceStr = String.format(priceStr, price);
        savingsStr = String.format(savingsStr, savings);

        priceTv = (TextView) findViewById(R.id.price);
        priceTv.setText(priceStr);
        savingsTv = (TextView) findViewById(R.id.savings);
        savingsTv.setText(savingsStr);

    }

}
