package caro.valdezg.drinksmeterapp;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;

class CountDrinksService extends IntentService {
    public static final String ACTION_COUNT_COFFEE = "caro.caldezg.drinksmeterapp.widgets.click";
    public static final String ACTION_COUNT_WATER = "caro.caldezg.drinksmeterapp.widgets.click";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public CountDrinksService() {
        super("DrinksService");
    }

    @Override
    protected void onHandleIntent(@androidx.annotation.Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();

            if (ACTION_COUNT_COFFEE.equals(action)) {
                handleClick();
            }
        }
    }

    private void handleClick() {
        int clicks = getSharedPreferences("sp", MODE_PRIVATE).getInt("clicks", 0);
        clicks++;
        getSharedPreferences("sp", MODE_PRIVATE)
                .edit()
                .putInt("clicks", clicks)
                .commit();

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] widgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, DrinksWidget.class));
        for (int appWidgetId : widgetIds) {
            DrinksWidget.updateAppWidget(getApplicationContext(), appWidgetManager, appWidgetId);
        }
    }
}
