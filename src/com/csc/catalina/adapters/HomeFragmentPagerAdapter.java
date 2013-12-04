
package com.csc.catalina.adapters;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.csc.catalina.CatalinaApplication;
import com.csc.catalina.R;
import com.csc.catalina.fragments.CartFragment;
import com.csc.catalina.fragments.HelpFragment;
import com.csc.catalina.fragments.OffersFragment;
import com.csc.catalina.fragments.ScanFragment;
import com.csc.catalina.fragments.SettingsFragment;

import java.util.HashMap;

public class HomeFragmentPagerAdapter extends FragmentPagerAdapter {

    public static final int OFFERS_POSITION = 0;
    public static final int SCAN_POSITION = 1;
    public static final int CART_POSITION = 2;
    public static final int SETTINGS_POSITION = 3;
    public static final int HELP_POSITION = 4;

    public static final HashMap<Integer, TabContainer> tabTitleMap = new HashMap<Integer, TabContainer>();

    public static class TabContainer {
        public String name;
        public Drawable icon;

        public TabContainer(String name, Drawable icon) {
            this.name = name;
            this.icon = icon;
        }
    }

    static {
        Resources res = CatalinaApplication.getInstance().getResources();
        Drawable offerImg = res.getDrawable(R.drawable.coupon);
        Drawable profileImg = res.getDrawable(R.drawable.profile);
        Drawable scanImg = res.getDrawable(R.drawable.scan);
        Drawable cartImg = res.getDrawable(R.drawable.shopping_cart_small);
        Drawable settingsImg = res.getDrawable(R.drawable.settings);

        String offersStr = res.getString(R.string.offers);
        String helpStr = res.getString(R.string.help);
        String scanStr = res.getString(R.string.scan);
        String cartStr = res.getString(R.string.cart);
        String settingsStr = res.getString(R.string.settings);

        TabContainer offers = new TabContainer(offersStr, offerImg);
        TabContainer help = new TabContainer(helpStr, profileImg);
        TabContainer scan = new TabContainer(scanStr, scanImg);
        TabContainer cart = new TabContainer(cartStr, cartImg);
        TabContainer settings = new TabContainer(settingsStr, settingsImg);

        tabTitleMap.put(OFFERS_POSITION, offers);
        tabTitleMap.put(HELP_POSITION, help);
        tabTitleMap.put(SCAN_POSITION, scan);
        tabTitleMap.put(CART_POSITION, cart);
        tabTitleMap.put(SETTINGS_POSITION, settings);
    }

    public HomeFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /** This method will be invoked when a page is requested to create */
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case OFFERS_POSITION:
                OffersFragment offersFrag = new OffersFragment();
                return offersFrag;
            case HELP_POSITION:
                HelpFragment profileFrag = new HelpFragment();
                return profileFrag;
            case SCAN_POSITION:
                ScanFragment scanFrag = new ScanFragment();
                return scanFrag;
            case CART_POSITION:
                CartFragment cartFrag = new CartFragment();
                return cartFrag;
            case SETTINGS_POSITION:
                SettingsFragment settingsFrag = new SettingsFragment();
                return settingsFrag;
        }

        return null;
    }

    /** Returns the number of pages */
    @Override
    public int getCount() {
        return tabTitleMap.size();
    }

}
