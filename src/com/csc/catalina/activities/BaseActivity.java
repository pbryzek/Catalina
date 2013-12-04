
package com.csc.catalina.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.ciety.framework.logging.Logs;
import com.ciety.framework.utils.AndroidNativeDevice;
import com.csc.catalina.CatalinaApplication;
import com.csc.catalina.R;

public abstract class BaseActivity extends SherlockFragmentActivity {

    private static final String LOG_TAG = BaseActivity.class.getName();

    protected ActionBar actionBar;

    public Handler messageHandler;

    public static enum DIALOGS {
        INFO
    }

    private static AlertDialog checkBoxDialog;
    private static AlertDialog dialog;
    private Dialog progressDialog;
    private Dialog translucentDialog;

    public static int activityCounter;

    public void setMessageHandler() {
        messageHandler = new BaseActivityMessageHandler();
    }

    public void setUpActionBar() {
        actionBar = getSupportActionBar();
        if (this instanceof HomeActivity) {
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        } else {
            actionBar.hide();
        }
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowCustomEnabled(false);

        actionBar.setLogo(null);

        View homeIcon = findViewById(
                AndroidNativeDevice.ATLEAST_HONEYCOMB ?
                        android.R.id.home : R.id.abs__home);
        ((View) homeIcon.getParent()).setVisibility(View.GONE);
        homeIcon.setVisibility(View.GONE);
    }

    /** {@inheritDoc} */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CatalinaApplication app = (CatalinaApplication) CatalinaApplication.getInstance();
        if (app != null && app.getEventBus() != null) {
            app.getEventBus().register(this);
        }
        activityCounter = activityCounter + 1;
        setMessageHandler();
        setUpActionBar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        messageHandler = null;
        activityCounter = activityCounter - 1;
        if (activityCounter == 0) {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        CatalinaApplication app = (CatalinaApplication) CatalinaApplication.getInstance();
        if (app != null && app.getEventBus() != null) {
            app.getEventBus().register(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        CatalinaApplication app = (CatalinaApplication) CatalinaApplication.getInstance();
        if (app != null && app.getEventBus() != null) {
            try {
                app.getEventBus().unregister(this);
            } catch (IllegalArgumentException e) {
                Logs.error(LOG_TAG, "EventBus not registered", e);
            } catch (NullPointerException e) {
                Logs.error(LOG_TAG, "NPE caught", e);
            }
        }
    }

    public class BaseActivityMessageHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

    public void cleanupDialogs(boolean destroy) {
        hideCheckboxDialog(destroy);
        hideProgressDialog(destroy);
        hideDialog(destroy);
        hideTranslucentDialog(destroy);
    }

    public void showInfoDialog(String message) {
        buildAlertDialog(DIALOGS.INFO, message);
    }

    protected AlertDialog.Builder buildAlertDialog(final DIALOGS dialogType) {
        return buildAlertDialog(dialogType, null);
    }

    protected void hideTranslucentDialog() {
        hideDialog(false);
    }

    protected void hideTranslucentDialog(boolean cleanup) {
        if (translucentDialog != null) {
            if (cleanup) {
                translucentDialog.dismiss();
                translucentDialog = null;
            } else if (translucentDialog.isShowing()) {
                translucentDialog.dismiss();
            }
        }
    }

    protected void hideProgressDialog() {
        hideProgressDialog(false);
    }

    protected void hideProgressDialog(boolean cleanup) {
        if (progressDialog != null) {
            if (cleanup) {
                progressDialog.dismiss();
                progressDialog = null;
            } else if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }

    protected void hideDialog() {
        hideDialog(false);
    }

    protected void hideDialog(boolean cleanup) {
        if (dialog != null) {
            if (cleanup) {
                dialog.dismiss();
                dialog = null;
            } else if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }

    protected void hideCheckboxDialog() {
        hideCheckboxDialog(false);
    }

    protected void hideCheckboxDialog(boolean cleanup) {
        if (checkBoxDialog != null) {
            if (cleanup) {
                checkBoxDialog.dismiss();
                checkBoxDialog = null;
            } else if (checkBoxDialog.isShowing()) {
                checkBoxDialog.dismiss();
            }
        }
    }

    protected AlertDialog.Builder buildAlertDialog(final DIALOGS dialogType, String message) {
        Resources res = CatalinaApplication.getInstance().getResources();
        AlertDialog.Builder builder = null;
        if (dialogType == DIALOGS.INFO) {
            String info = res.getString(R.string.info);
            String ok = res.getString(android.R.string.ok);
            builder = buildAlertDialog(ok, null, info, message, dialogType);
        }

        return builder;
    }

    protected AlertDialog.Builder buildAlertDialog(String positive, String negative, String title, String message,
            final DIALOGS dialogType) {
        return buildAlertDialog(positive, negative, null, title, message, true, dialogType);
    }

    protected AlertDialog.Builder buildAlertDialog(String positive, String negative, String neutral, String title,
            String message, boolean cancelable, final DIALOGS dialogType) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        dialogBuilder.setTitle(title)
                .setPositiveButton(positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                }).setCancelable(cancelable);
        if (negative != null) {
            dialogBuilder.setNegativeButton(negative, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int whichButton) {
                    dialog.cancel();
                }
            });
        }
        if (neutral != null) {
            dialogBuilder.setNeutralButton(neutral, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int whichButton) {
                }
            });
        }
        if (message != null) {
            dialogBuilder.setMessage(message);
        }

        createAndShowDialog(dialogBuilder);

        return dialogBuilder;
    }

    protected void createAndShowDialog(AlertDialog.Builder builder) {
        dialog = builder.create();
        dialog.show();
    }
}
