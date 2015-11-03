package com.example.han.newnewnoon;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.io.InputStream;
import java.util.ArrayList;


public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mPlanetTitles;
    private String[] tabs = {"Thema1", "Thema2", "Thema3", "Thema4"};
    ActionBar actionbar;
    MapView mapView;

    //UI
    TextView nameTv = null;
    TextView telTv = null;
    TextView cateTv = null;
    TextView addrTv = null;
    ImageView foodImg = null;
    //Data
    public static ArrayList<Item> ThemaItem = new ArrayList<Item>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        make_dummy();
        Intent a = new Intent(this, GpsService.class);
        startService(a);

        //초기화&알람
        SharedInit SI = new SharedInit(getApplicationContext());
        registerAlarm rA = new registerAlarm(getApplicationContext());
        if(!SI.getSharedTrue("isCreate")){
            SI.Init();
            rA.registerInit();
        }
        rA.testAM("ACTION.GET.ONE",10,41);

        //바인딩
        actionbar = getActionBar();
        nameTv = (TextView) findViewById(R.id.nameView);
        telTv = (TextView) findViewById(R.id.telView);
        cateTv = (TextView) findViewById(R.id.cateView);
        addrTv = (TextView) findViewById(R.id.addrView);
        foodImg = (ImageView) findViewById(R.id.cookImage);


        // MapView
        mapView = new MapView(this);
        mapView.setDaumMapApiKey("5c27579fc750d1e54ced797676373643");
        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);


        //Drawer
        mTitle = mDrawerTitle = getTitle();
        mPlanetTitles = getResources().getStringArray(R.array.planets_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mPlanetTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }

        // tab
        actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionbar.setHomeButtonEnabled(false);

        for (String tab_name : tabs) {
            actionbar.addTab(actionbar.newTab().setText(tab_name)
                    .setTabListener(this));
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch (item.getItemId()) {
            case R.id.action_websearch:
                // create intent to perform web search for this planet
                Intent intent = new Intent(MainActivity.this, optionActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */



    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }
    private void selectItem(int position) {

    }
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        MapPOIItem marker;
        Item in1;
        int position = tab.getPosition();
        int size = ThemaItem.size();
        switch (position) {
            case 0:
                if(size>=1) {
                    Toast.makeText(getApplicationContext(), "" + size, Toast.LENGTH_SHORT).show();
                    in1 = ThemaItem.get(0);
                    marker = new MapPOIItem();
                    nameTv.setText("" + in1.title);
                    telTv.setText("" + in1.phone);
                    cateTv.setText("" + in1.category);
                    addrTv.setText("" + in1.address);
                    new DownloadImageTask().execute(in1.imageUrl);
                    marker.setItemName("Default Marker");
                    marker.setTag(0);
                    marker.setMapPoint(MapPoint.mapPointWithGeoCoord(in1.latitude, in1.longitude));
                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                    mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(in1.latitude, in1.longitude), true);
                    mapView.addPOIItem(marker);
                }
                break;
            case 1:
                if(size >=2) {
                    Toast.makeText(getApplicationContext(), "" + size, Toast.LENGTH_SHORT).show();
                    in1 = ThemaItem.get(1);
                    marker = new MapPOIItem();
                    nameTv.setText("" + in1.title);
                    telTv.setText("" + in1.phone);
                    cateTv.setText("" + in1.category);
                    addrTv.setText("" + in1.address);
                    new DownloadImageTask().execute(in1.imageUrl);

                    marker.setItemName("Default Marker");
                    marker.setTag(0);
                    marker.setMapPoint(MapPoint.mapPointWithGeoCoord(in1.latitude, in1.longitude));
                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                    mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(in1.latitude, in1.longitude), true);
                    mapView.addPOIItem(marker);
                }
                break;
            case 2:
                if(size >=3) {
                    Toast.makeText(getApplicationContext(), "" + size, Toast.LENGTH_SHORT).show();
                    in1 = ThemaItem.get(2);
                    marker = new MapPOIItem();
                    nameTv.setText("" + in1.title);
                    telTv.setText("" + in1.phone);
                    cateTv.setText("" + in1.category);
                    addrTv.setText("" + in1.address);
                    new DownloadImageTask().execute(in1.imageUrl);

                    marker.setItemName("Default Marker");
                    marker.setTag(0);
                    marker.setMapPoint(MapPoint.mapPointWithGeoCoord(in1.latitude, in1.longitude));
                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                    mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(in1.latitude, in1.longitude), true);
                    mapView.addPOIItem(marker);
                }
                break;
            case 3:
                if(size >=4) {
                    Toast.makeText(getApplicationContext(), "" + size, Toast.LENGTH_SHORT).show();
                    in1 = ThemaItem.get(3);
                    marker = new MapPOIItem();
                    nameTv.setText("" + in1.title);
                    telTv.setText("" + in1.phone);
                    cateTv.setText("" + in1.category);
                    addrTv.setText("" + in1.address);
                    new DownloadImageTask().execute(in1.imageUrl);

                    marker.setItemName("Default Marker");
                    marker.setTag(0);
                    marker.setMapPoint(MapPoint.mapPointWithGeoCoord(in1.latitude, in1.longitude));
                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                    mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(in1.latitude, in1.longitude), true);
                    mapView.addPOIItem(marker);
                }
                break;
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {


    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            foodImg.setImageBitmap(result);
        }
    }

    public void widgetClicked(View v) {
        Context context = this.getApplicationContext();
        Intent update = new Intent();
        update.setAction("chae.widget.update");
        context.sendBroadcast(update);
    }

    public void make_dummy() {
        ArrayList<Item> wg;
        wg = new ArrayList<Item>();
        for (int i=1; i<5;i++) {
            Item item = new Item();
            item.title = "(X)title"+i;
            item.category = "(X)category"+i;
            item.address = "(X)address"+i;
            item.imageUrl = "http://cfile72.uf.daum.net/image/2125083751B7EEB80E8ECC";
            wg.add(item);
        }
        ThemaItem = wg;
    }
}
