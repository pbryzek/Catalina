
package com.csc.catalina.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.ciety.framework.logging.Logs;
import com.ciety.framework.utils.AndroidNativeDevice;
import com.ciety.framework.utils.CietyFrameworkUtils;
import com.csc.catalina.CatalinaApplication;
import com.csc.catalina.R;
import com.csc.catalina.adapters.HomeFragmentPagerAdapter;
import com.csc.catalina.adapters.HomeFragmentPagerAdapter.TabContainer;
import com.csc.catalina.constants.CatalinaConstants;
import com.csc.catalina.constants.CatalinaMessageIds;
import com.csc.catalina.fragments.CartFragment;
import com.csc.catalina.guava.IntMessage;
import com.csc.catalina.utils.CatalinaMessageSender;
import com.csc.catalina.utils.Utils;
import com.csc.catalina.views.rows.ShoppingCartRow;
import com.google.common.eventbus.Subscribe;

import java.util.ArrayList;

public class HomeActivity extends BaseActivity {

    private static final String LOG_TAG = HomeActivity.class.getName();

    private HomeFragmentPagerAdapter pagerAdapter;

    public static boolean hasFinishedDisplayedSplash;

    public static boolean hasDisplayedSplash;

    private Dialog splashScreenDialog;

    private static final int SPLASH_TIME = 1000;

    private final ArrayList<com.actionbarsherlock.app.ActionBar.Tab> tabs = new ArrayList<com.actionbarsherlock.app.ActionBar.Tab>();

    private ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showSplashScreen();
        setContentView(R.layout.activity_home);

        setPagerAdapter();

        setUpViewPager();

        setupTabs();
    }

    private void setUpViewPager() {

        ViewPager.SimpleOnPageChangeListener pageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                actionBar.setSelectedNavigationItem(position);
            }
        };
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(4);
        if (this instanceof HomeActivity) {
            viewPager.setOnPageChangeListener(pageChangeListener);
        }
        viewPager.setAdapter(pagerAdapter);
    }

    public void setPagerAdapter() {
        pagerAdapter = new HomeFragmentPagerAdapter(getSupportFragmentManager());
    }

    public void switchTab(int tabPosition) {
        viewPager.setCurrentItem(tabPosition);
    }

    private void setupTabs() {
        /** Defining tab listener */
        TabListener tabListener = new TabListener() {

            @Override
            public void onTabSelected(com.actionbarsherlock.app.ActionBar.Tab tab,
                    android.support.v4.app.FragmentTransaction ft) {
                int tabPosition = tab.getPosition();
                switchTab(tabPosition);
            }

            @Override
            public void onTabUnselected(com.actionbarsherlock.app.ActionBar.Tab tab,
                    android.support.v4.app.FragmentTransaction ft) {
            }

            @Override
            public void onTabReselected(com.actionbarsherlock.app.ActionBar.Tab tab,
                    android.support.v4.app.FragmentTransaction ft) {
            }
        };

        int numTabs = HomeFragmentPagerAdapter.tabTitleMap.size();
        int tabWidth = AndroidNativeDevice.getDeviceWidth(this) / numTabs;

        for (int i = 0; i < numTabs; i++) {
            TabContainer tabContainer = HomeFragmentPagerAdapter.tabTitleMap.get(i);

            String name = tabContainer.name;
            Drawable icon = tabContainer.icon;

            RelativeLayout tabLayout = (RelativeLayout) getLayoutInflater().inflate(R.layout.tab_layout, null);

            TextView tabName = (TextView) tabLayout.findViewById(R.id.tabName);
            tabName.setText(name);

            ImageView tabIcon = (ImageView) tabLayout.findViewById(R.id.tabIcon);
            tabIcon.setImageDrawable(icon);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(tabWidth,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            tabLayout.setLayoutParams(params);

            Tab tab = actionBar.newTab()
                    .setTabListener(tabListener);
            tab.setCustomView(tabLayout);

            tabs.add(tab);
            actionBar.addTab(tab);
        }
    }

    @Override
    public void setMessageHandler() {
        messageHandler = new HomeActivityMessageHandler();
    }

    public class HomeActivityMessageHandler extends BaseActivityMessageHandler {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == CatalinaMessageIds.CATALINA_MESSAGE_IDS.LAUNCH_ITEM.ordinal()) {
                int position = msg.arg1;
                CartFragment cartFrag = getCartFrag();
                ShoppingCartRow row = cartFrag.getRow(position);
                if (row != null) {
                    String name = row.getName();
                    String quantity = row.getQuantity();
                    double price = row.getPrice();
                    double savings = row.getSavings();

                    launchItemActivity(name, quantity, price, savings);
                }
            } else if (msg.what == CatalinaMessageIds.CATALINA_MESSAGE_IDS.DELETE_ROW.ordinal()) {
                int position = msg.arg1;
                CartFragment cartFrag = getCartFrag();
                cartFrag.deleteRow(position);
            } else {
                super.handleMessage(msg);
            }
        }
    }

    protected CartFragment getCartFrag() {
        return (CartFragment) getSupportFragmentManager()
                .findFragmentByTag(getFragmentTag(HomeFragmentPagerAdapter.CART_POSITION));
    }

    protected String getFragmentTag(int id) {
        return "android:switcher:" + viewPager.getId() + ":" + id;
    }

    protected void showSplashScreen() {
        if (hasDisplayedSplash) {
            if (!CietyFrameworkUtils.isOnline(CatalinaApplication.getInstance())) {
                String infoMsg = CatalinaApplication.getInstance().getResources()
                        .getString(R.string.bad_connection);
                showInfoDialog(infoMsg);
            }
        } else {
            try {
                hasDisplayedSplash = true;

                if (splashScreenDialog == null) {
                    splashScreenDialog = new Dialog(this, R.style.SplashScreen);
                }
                splashScreenDialog.setContentView(R.layout.splash_screen);
                splashScreenDialog.setCancelable(false);
                splashScreenDialog.show();

                // Set Runnable to remove splash screen just in case
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hasFinishedDisplayedSplash = true;
                        removeSplashScreen();
                    }
                }, SPLASH_TIME);
            } catch (NullPointerException e) {
                Logs.error(LOG_TAG, "Splash npe", e);
                splashScreenDialog = null;
            } catch (RuntimeException e) {
                Logs.error(LOG_TAG, "Splash runtime", e);
                splashScreenDialog = null;
            }
        }
    }

    /**
     * Removes the Dialog that displays the splash screen
     */
    protected void removeSplashScreen() {
        if (splashScreenDialog != null) {
            splashScreenDialog.dismiss();
            splashScreenDialog = null;
        }
        if (!Utils.isOnline(this)) {
            String infoMsg = CatalinaApplication.getInstance().getResources()
                    .getString(R.string.bad_connection);
            showInfoDialog(infoMsg);
        }
    }

    public void launchItemActivity(String name, String quantity, double price, double savings) {
        Intent i = new Intent(getApplicationContext(), ItemActivity.class);
        i.putExtra(CatalinaConstants.INTENT_NAME, name);
        i.putExtra(CatalinaConstants.INTENT_QUANTITY, quantity);
        i.putExtra(CatalinaConstants.INTENT_PRICE, price);
        i.putExtra(CatalinaConstants.INTENT_SAVINGS, savings);
        startActivity(i);
    }

    @Subscribe
    public void deleteRow(IntMessage deleteRowMsg) {
        if (deleteRowMsg != null) {
            String uniqueId = deleteRowMsg.getUniqueId();
            if (uniqueId.equals(CatalinaConstants.DELETE_ROW)) {
                CatalinaMessageSender.sendMessage(CatalinaMessageIds.CATALINA_MESSAGE_IDS.DELETE_ROW.ordinal(),
                        deleteRowMsg.getData(), messageHandler);
            } else if (uniqueId.equals(CatalinaConstants.LAUNCH_ITEM_ROW)) {
                CatalinaMessageSender.sendMessage(CatalinaMessageIds.CATALINA_MESSAGE_IDS.LAUNCH_ITEM.ordinal(),
                        deleteRowMsg.getData(), messageHandler);
            }

        }
    }

}
