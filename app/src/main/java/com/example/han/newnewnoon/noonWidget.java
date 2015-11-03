package com.example.han.newnewnoon;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by CHAE on 2015-10-26.
 */
public class noonWidget extends AppWidgetProvider {
    private static RemoteViews updateViews;
    public static int themaValue;
    private static DisplayImageOptions displayOptions;

    static {
        displayOptions = DisplayImageOptions.createSimple();
    }


    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        themaValue = 0;
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        themaValue = 0;
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        themaValue = 0;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        UILApplication.initImageLoader(context);
        for (int i = 0; i < appWidgetIds.length; i++) {
            int widgetId = appWidgetIds[i];
            updateAppWidget(context, appWidgetManager, widgetId);
        }

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equals("chae.widget.update")) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName thiswidget = new ComponentName(context, noonWidget.class);
            int[] ids = appWidgetManager.getAppWidgetIds(thiswidget);
            onUpdate(context, appWidgetManager, ids);
        }
        if (intent.getAction().equals("chae.widget.left")) {
            SharedPreferences prefs = context.getSharedPreferences("NW", 0);
            SharedPreferences.Editor editor = prefs.edit();
            int value = intent.getIntExtra("T_value", 0);
            switch (value) {
                case 0:
                    editor.putString("thema", "thema4");
                    themaValue = 3;
                    break;
                case 1:
                    editor.putString("thema", "thema1");
                    themaValue = 0;
                    break;
                case 2:
                    editor.putString("thema", "thema2");
                    themaValue = 1;
                    break;
                case 3:
                    editor.putString("thema", "thema3");
                    themaValue = 2;
                    break;
                default:
            }
            editor.commit();
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName thiswidget = new ComponentName(context, noonWidget.class);
            int[] ids = appWidgetManager.getAppWidgetIds(thiswidget);
            onUpdate(context, appWidgetManager, ids);
        }

        if (intent.getAction().equals("chae.widget.right")) {
            SharedPreferences prefs = context.getSharedPreferences("NW", 0);
            SharedPreferences.Editor editor = prefs.edit();
            int value = intent.getIntExtra("T_value", 0);
            switch (value) {
                case 0:
                    editor.putString("thema", "thema2");
                    themaValue = 1;
                    break;
                case 1:
                    editor.putString("thema", "thema3");
                    themaValue = 2;
                    break;
                case 2:
                    editor.putString("thema", "thema4");
                    themaValue = 3;
                    break;
                case 3:
                    editor.putString("thema", "thema1");
                    themaValue = 0;
                    break;
                default:
            }
            editor.commit();
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName thiswidget = new ComponentName(context, noonWidget.class);
            int[] ids = appWidgetManager.getAppWidgetIds(thiswidget);
            onUpdate(context, appWidgetManager, ids);
        }

        if (intent.getAction().equals("chae.widget.click")) {
            Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR);
            int min = c.get(Calendar.MINUTE);
            Log.i("widget", "widget_click_OK (hour:min) :" + hour + ":" + min);

            DBHandler dbHandler = DBHandler.open(context);
            dbHandler.insert(hour + ":" + min);
        }
    }

    static void updateAppWidget(Context context, final AppWidgetManager appWidgetManager,final int appWidgetId) {

        Item item;

        SharedPreferences prefs = context.getSharedPreferences("NW", 0);
        String content = prefs.getString("content", "content1");
        int layoutId = R.layout.widget_layout;
        if ("content1".equals(content)) {
            layoutId = R.layout.widget_layout;
        } else if ("content2".equals(content)) {
            layoutId = R.layout.widget_layout2;
        } else if ("content3".equals(content)) {
            layoutId = R.layout.widget_layout2;
        }
        String thema = prefs.getString("thema", "thema1");
        updateViews = new RemoteViews(context.getPackageName(), layoutId);
        updateViews.setTextViewText(R.id.widget_tv, thema);
        item = contentValue(MainActivity.ThemaItem, thema);

        Log.i("widget", "before configure : " + MainActivity.ThemaItem.get(0).title);
        configureLayout(content, item);
        Intent left_intent = new Intent();
        Intent right_intent = new Intent();
        Intent click_intent = new Intent();
        left_intent.putExtra("T_value", themaValue);
        right_intent.putExtra("T_value", themaValue);
        left_intent.setAction("chae.widget.left");
        right_intent.setAction("chae.widget.right");
        //click_intent.putExtra("cur_Time", Calendar.getInstance().get(Calendar.HOUR));
        click_intent.setAction("chae.widget.click");
        PendingIntent pendingIntent_L = PendingIntent.getBroadcast(context, 0, left_intent, PendingIntent.FLAG_CANCEL_CURRENT);
        PendingIntent pendingIntent_R = PendingIntent.getBroadcast(context, 0, right_intent, PendingIntent.FLAG_CANCEL_CURRENT);
        PendingIntent pendingIntent_C = PendingIntent.getBroadcast(context, 0, click_intent, PendingIntent.FLAG_CANCEL_CURRENT);
        updateViews.setOnClickPendingIntent(R.id.left_button, pendingIntent_L);
        updateViews.setOnClickPendingIntent(R.id.right_button, pendingIntent_R);
        updateViews.setOnClickPendingIntent(R.id.widget_click, pendingIntent_C);

        ImageSize minImazeSize = new ImageSize(120,400);
        ImageLoader.getInstance().loadImage(item.imageUrl, minImazeSize,displayOptions, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                updateViews.setImageViewBitmap(R.id.widget_image,loadedImage);
                appWidgetManager.updateAppWidget(appWidgetId, updateViews);
            }
        });

    }

    private static void configureLayout(String content, Item item) {
        updateViews.setTextViewText(R.id.widget_title, item.title);
        updateViews.setTextViewText(R.id.widget_cg, item.category);
        updateViews.setTextViewText(R.id.widget_address, item.address);
        Log.i("widget", "configure:" + item.title + item.imageUrl);

    }

    private static Item contentValue(ArrayList<Item> items, String string) {
        Item item = new Item();
        switch (string) {
            case "thema1":
                item = items.get(0);
                break;
            case "thema2":
                item = items.get(1);
                break;
            case "thema3":
                item = items.get(2);
                break;
            case "thema4":
                item = items.get(3);
                break;
            default:
        }
        return item;
    }

}