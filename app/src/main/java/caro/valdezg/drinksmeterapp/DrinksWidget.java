package caro.valdezg.drinksmeterapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

import static android.content.Context.MODE_PRIVATE;
import static caro.valdezg.drinksmeterapp.CountDrinksService.ACTION_COUNT_WATER;

/**
 * Implementation of App Widget functionality.
 */
public class DrinksWidget extends AppWidgetProvider {

    private static int count = 0;
    private static SharedPreferences sharedPreferences;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);

        // TODO: do this for every kind of drink.
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.drinks_widget);

        views.setTextViewText(R.id.number_of_water_glasses_widget_text_view,
                sharedPreferences.getString(BaseConfiguration.SharedPreferences.NUMBER_OF_WATER_GLASSES, ""));

        views.setTextViewText(R.id.number_of_coffee_cups_widget_text_view,
                sharedPreferences.getString(BaseConfiguration.SharedPreferences.NUMBER_OF_COFFEE_CUPS, ""));

        views.setOnClickPendingIntent(R.id.coffee_cup_widget_image_view, getPendingIntent(context, 1));

        views.setOnClickPendingIntent(R.id.water_glasses_widget_image_view, getPendingIntent(context, 1));

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    static private PendingIntent getPendingIntent(Context context, int value) {
        //1
        Intent intent = new Intent(context, MainActivity.class);
        //2
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        //3
        intent.putExtra("drink", value);
        //4
        return PendingIntent.getActivity(context, value, intent, 0);
    }
}

