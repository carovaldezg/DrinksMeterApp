package caro.valdezg.drinksmeterapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static caro.valdezg.drinksmeterapp.BaseConfiguration.SharedPreferences.NUMBER_OF_COFFEE_CUPS;

public class MainActivity extends AppCompatActivity {

    private static final String COFFEE = "coffee";
    private static final String WATER = "water";

    @BindView(R.id.number_of_coffee_cups_text_view)
    TextView mNumberOfCoffeeCups;
    @BindView(R.id.number_of_water_glasses_text_view)
    TextView mNumberOfWaterGlasses;
    @BindView(R.id.water_glass_image_view)
    ImageView mWaterGlassIcon;
    @BindView(R.id.coffee_cup_image_view)
    ImageView mCoffeeCupIcon;

    SharedPreferences sharepreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        loadValues();
    }

    private void loadValues() {
        sharepreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        if (sharepreferences != null) {
            String coffee = sharepreferences.getString(BaseConfiguration.SharedPreferences.NUMBER_OF_COFFEE_CUPS, "");
            String water = sharepreferences.getString(BaseConfiguration.SharedPreferences.NUMBER_OF_WATER_GLASSES, "");
            if (coffee.equals("")) {
                mNumberOfCoffeeCups.setText("0");
            } else mNumberOfCoffeeCups.setText(coffee);
            if (water.equals("")) mNumberOfWaterGlasses.setText("0");
            else mNumberOfWaterGlasses.setText(water);
            updateSharedPreferences(Integer.valueOf(coffee), COFFEE);
            updateSharedPreferences(Integer.valueOf(water), WATER);
        }
    }

    @OnClick(R.id.water_glass_image_view)
    public void onClickOnWaterGlass() {
        int count = Integer.valueOf(mNumberOfWaterGlasses.getText().toString()) + 1;
        updateSharedPreferences(count, WATER);
        mNumberOfWaterGlasses.setText(String.valueOf(count));
    }

    @OnClick(R.id.coffee_cup_image_view)
    public void onClickOnCoffeeCups() {
        int  count = Integer.valueOf(mNumberOfCoffeeCups.getText().toString()) + 1;
        updateSharedPreferences(count, COFFEE);
        mNumberOfCoffeeCups.setText(String.valueOf(count));
    }

    private void updateSharedPreferences(int count, String drink) {
        sharepreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharepreferences.edit();
        if (drink.equals(COFFEE)) {
            editor.putString(NUMBER_OF_COFFEE_CUPS,
                    String.valueOf(count));
        } else
            editor.putString(BaseConfiguration.SharedPreferences.NUMBER_OF_WATER_GLASSES,
                    String.valueOf(count));
        editor.apply();

        Intent intent = new Intent(this, DrinksWidget.class);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        int ids[] = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(
                new ComponentName(getApplication(), DrinksWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
        sendBroadcast(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
