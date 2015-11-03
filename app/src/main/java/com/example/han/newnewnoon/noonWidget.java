package com.example.han.newnewnoon;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RemoteViews;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by CHAE on 2015-10-26.
 */
public class noonWidget extends AppWidgetProvider {
    private static RemoteViews updateViews;
    public static int themaValue;

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        themaValue = 0;
    }
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        themaValue= 0;
    }
    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        themaValue =0;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for ( int i = 0; i < appWidgetIds.length; i++ ){
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
        if(intent.getAction().equals("chae.widget.left")) {
            SharedPreferences prefs = context.getSharedPreferences("NW", 0);
            SharedPreferences.Editor editor = prefs.edit();
            int value = intent.getIntExtra("T_value",0);
            switch(value){
                case 0:
                    editor.putString("thema","thema4");
                    themaValue = 3;
                    break;
                case 1:
                    editor.putString("thema","thema1");
                    themaValue = 0;
                    break;
                case 2:
                    editor.putString("thema","thema2");
                    themaValue = 1;
                    break;
                case 3:
                    editor.putString("thema","thema3");
                    themaValue = 2;
                    break;
                default :
            }
            editor.commit();
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName thiswidget = new ComponentName(context, noonWidget.class);
            int[] ids = appWidgetManager.getAppWidgetIds(thiswidget);
            onUpdate(context, appWidgetManager, ids);
        }

        if(intent.getAction().equals("chae.widget.right")) {
            SharedPreferences prefs = context.getSharedPreferences("NW", 0);
            SharedPreferences.Editor editor = prefs.edit();
            int value = intent.getIntExtra("T_value",0);
            switch(value){
                case 0:
                    editor.putString("thema","thema2");
                    themaValue = 1;
                    break;
                case 1:
                    editor.putString("thema","thema3");
                    themaValue = 2;
                    break;
                case 2:
                    editor.putString("thema","thema4");
                    themaValue = 3;
                    break;
                case 3:
                    editor.putString("thema","thema1");
                    themaValue = 0;
                    break;
                default :
            }
            editor.commit();
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName thiswidget = new ComponentName(context, noonWidget.class);
            int[] ids = appWidgetManager.getAppWidgetIds(thiswidget);
            onUpdate(context, appWidgetManager, ids);
        }

        if(intent.getAction().equals("chae.widget.click")) {
            Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR);
            int min = c.get(Calendar.MINUTE);
            Log.i("widget","widget_click_OK (hour:min) :" + hour +":" + min);

            DBHandler dbHandler = DBHandler.open(context);
            dbHandler.insert(hour + ":" + min);
        }
    }

    public static void updateAppWidget (Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        Item item;

        SharedPreferences prefs = context.getSharedPreferences("NW", 0);
        String content = prefs.getString("content", "content1");
        int layoutId = R.layout.widget_layout;
        if ("content1".equals(content)){
            layoutId = R.layout.widget_layout;
        } else if ("content2".equals(content)){
            layoutId = R.layout.widget_layout2;
        } else if ("content3".equals(content)) {
            layoutId = R.layout.widget_layout2;
        }
        String thema = prefs.getString("thema","thema1");
        updateViews = new RemoteViews(context.getPackageName(), layoutId);
        updateViews.setTextViewText(R.id.widget_tv, thema);
        item = contentValue(MainActivity.ThemaItem, thema);

        Log.i("widget", "before configure : " + MainActivity.ThemaItem.get(0).title);
        configureLayout(content, item);
        Intent left_intent = new Intent();
        Intent right_intent = new Intent();
        Intent click_intent = new Intent();
        left_intent.putExtra("T_value",themaValue);
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
        updateViews.setOnClickPendingIntent(R.id.widget_click,pendingIntent_C);
        appWidgetManager.updateAppWidget(appWidgetId, updateViews);
    }

    private static void configureLayout(String content, Item item) {
        updateViews.setTextViewText(R.id.widget_title, item.title);
        updateViews.setTextViewText(R.id.widget_cg, item.category);
        updateViews.setTextViewText(R.id.widget_address, item.address);
        try {
            URL newurl = new URL(item.imageUrl);
            Bitmap bm = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
            updateViews.setImageViewBitmap(R.id.widget_image, bm);
        } catch(IOException e) {
            updateViews.setImageViewResource(R.id.widget_image,R.drawable.plus);
        }
        //new DownloadImageTask2().execute(item.imageUrl);
        Log.i("widget", "configure:" + item.title + item.imageUrl);

    }

    private static Item contentValue(ArrayList<Item> items, String string) {
        Item item = new Item();
        switch(string) {
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

    private static class DownloadImageTask2 extends AsyncTask<String, Void, Bitmap> {

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Log.i("widget","url:"+urldisplay);
            Bitmap bm = null;
            /*
            try {
                URL url = new URL(urldisplay);
                URLConnection conn = url.openConnection();
                conn.connect();
                InputStream is = conn.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                bm = BitmapFactory.decodeStream(bis);
                bis.close();
                is.close();
            } catch (IOException e ) {
                Log.i("widget", "downloadImageTask2");
            }
*/
            try {
                URL newurl = new URL(urldisplay);
                bm = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
            } catch(IOException e){

            }
            return bm;
        }

        protected void onPostExecute(Bitmap result) {
            if(result == null ) {
                updateViews.setImageViewResource(R.id.widget_image, R.drawable.plus);;
            } else {
                updateViews.setImageViewBitmap(R.id.widget_image, result);
            }
            Log.i("widget", "image task");
        }
    }

}
