
package com.csc.catalina;

import com.ciety.framework.corecomponents.BaseCietyFrameworkApplication;

public class CatalinaApplication extends BaseCietyFrameworkApplication {

    private static final String LOG_TAG = CatalinaApplication.class.getName();

    @Override
    public void onCreate() {
        setInstance(this);

        super.onCreate();
    }
}
