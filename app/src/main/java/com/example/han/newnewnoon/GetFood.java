package com.example.han.newnewnoon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;

import java.util.List;

/**
 * Created by han on 2015-09-14.
 */
public class GetFood extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        int radius = 1000; // 중심 좌표부터의 반경거리. 특정 지역을 중심으로 검색하려고 할 경우 사용. meter 단위 (0 ~ 10000)
        int page = 1;

        if(intent.getAction().equals("ACTION.GET.ONE"))
        {
            try {
                Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                final double latitude, longitude;

                latitude = location.getLatitude();
                longitude = location.getLongitude();

                Searcher searcher1 = new Searcher();
                searcher1.searchKeyword(context, "편의점", latitude, longitude, radius, page, 2, "63795804fcf1477cdd7226501d8ba39b", new OnFinishSearchListener() {
                    @Override
                    public void onSuccess(List<Item> itemList) {

                        MainActivity.ThemaItem.add(0, itemList.get(0));

                    }

                    @Override
                    public void onFail() {
                    }
                });
                Searcher searcher2 = new Searcher();
                searcher2.searchKeyword(context, "편의점", latitude, longitude, radius, page, 2, "63795804fcf1477cdd7226501d8ba39b", new OnFinishSearchListener() {
                    @Override
                    public void onSuccess(List<Item> itemList) {
                        MainActivity.ThemaItem.add(1, itemList.get(0));
                    }

                    @Override
                    public void onFail() {
                    }
                });
                Searcher searcher3 = new Searcher();
                searcher3.searchCategory(context, "FD6", latitude, longitude, radius, page, 2, "63795804fcf1477cdd7226501d8ba39b", new OnFinishSearchListener() {
                    @Override
                    public void onSuccess(List<Item> itemList) {
                        MainActivity.ThemaItem.add(2, itemList.get(0));
                    }

                    @Override
                    public void onFail() {
                    }
                });
                Searcher searcher4 = new Searcher();
                searcher4.searchCategory(context, "FD6", latitude, longitude, radius, 1, (int) (Math.random() * 3), "63795804fcf1477cdd7226501d8ba39b", new OnFinishSearchListener() {
                    @Override
                    public void onSuccess(List<Item> itemList) {
                        MainActivity.ThemaItem.add(3, itemList.get((int) (Math.random() * 15)));
                    }

                    @Override
                    public void onFail() {
                    }
                });

            } catch (Exception e) {
            }
            registerAlarm rA = new registerAlarm(context);
            rA.registerAM(intent.getAction(),"1");


        }
        if(intent.getAction().equals("ACTION.GET.TWO")){

            try {
                Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                final double latitude, longitude;

                latitude = location.getLatitude();
                longitude = location.getLongitude();

                Searcher searcher1 = new Searcher();
                searcher1.searchKeyword(context, "편의점", latitude, longitude, radius, page, 2, "63795804fcf1477cdd7226501d8ba39b", new OnFinishSearchListener() {
                    @Override
                    public void onSuccess(List<Item> itemList) {

                        MainActivity.ThemaItem.add(0,itemList.get(0));

                    }

                    @Override
                    public void onFail() {
                    }
                });
                Searcher searcher2 = new Searcher();
                searcher2.searchKeyword(context, "편의점", latitude, longitude, radius, page, 2, "63795804fcf1477cdd7226501d8ba39b", new OnFinishSearchListener() {
                    @Override
                    public void onSuccess(List<Item> itemList) {
                        MainActivity.ThemaItem.add(1,itemList.get(0));
                    }

                    @Override
                    public void onFail() {
                    }
                });
                Searcher searcher3 = new Searcher();
                searcher3.searchCategory(context, "FD6", latitude, longitude, radius, page, 2, "63795804fcf1477cdd7226501d8ba39b", new OnFinishSearchListener() {
                    @Override
                    public void onSuccess(List<Item> itemList) {
                        MainActivity.ThemaItem.add(2, itemList.get(0));
                    }

                    @Override
                    public void onFail() {
                    }
                });
                Searcher searcher4 = new Searcher();
                searcher4.searchCategory(context, "FD6", latitude, longitude, radius, 1, (int) (Math.random() * 3), "63795804fcf1477cdd7226501d8ba39b", new OnFinishSearchListener() {
                    @Override
                    public void onSuccess(List<Item> itemList) {
                        MainActivity.ThemaItem.add(3,itemList.get((int) (Math.random() * 15)));
                    }

                    @Override
                    public void onFail() {
                    }
                });
                registerAlarm rA = new registerAlarm(context);
                rA.registerAM(intent.getAction(),"1");


            } catch (Exception e) {
            }

        }
        if(intent.getAction().equals("ACTION.GET.THREE")){
            try {

                Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                final double latitude, longitude;

                latitude = location.getLatitude();
                longitude = location.getLongitude();

                Searcher searcher1 = new Searcher();
                searcher1.searchKeyword(context, "편의점", latitude, longitude, radius, page, 2, "63795804fcf1477cdd7226501d8ba39b", new OnFinishSearchListener() {
                    @Override
                    public void onSuccess(List<Item> itemList) {

                        MainActivity.ThemaItem.add(0,itemList.get(0));

                    }

                    @Override
                    public void onFail() {
                    }
                });
                Searcher searcher2 = new Searcher();
                searcher2.searchKeyword(context, "편의점", latitude, longitude, radius, page, 2, "63795804fcf1477cdd7226501d8ba39b", new OnFinishSearchListener() {
                    @Override
                    public void onSuccess(List<Item> itemList) {
                        MainActivity.ThemaItem.add(1,itemList.get(0));
                    }

                    @Override
                    public void onFail() {
                    }
                });
                Searcher searcher3 = new Searcher();
                searcher3.searchCategory(context, "FD6", latitude, longitude, radius, page, 2, "63795804fcf1477cdd7226501d8ba39b", new OnFinishSearchListener() {
                    @Override
                    public void onSuccess(List<Item> itemList) {
                        MainActivity.ThemaItem.add(2, itemList.get(0));
                    }

                    @Override
                    public void onFail() {
                    }
                });
                Searcher searcher4 = new Searcher();
                searcher4.searchCategory(context, "FD6", latitude, longitude, radius, 1, (int) (Math.random() * 3), "63795804fcf1477cdd7226501d8ba39b", new OnFinishSearchListener() {
                    @Override
                    public void onSuccess(List<Item> itemList) {
                        MainActivity.ThemaItem.add(3,itemList.get((int) (Math.random() * 15)));
                    }

                    @Override
                    public void onFail() {
                    }
                });
                registerAlarm rA = new registerAlarm(context);
                rA.registerAM(intent.getAction(),"2");


            } catch (Exception e) {
            }

        }
        if(intent.getAction().equals("ACTION.GET.FOUR")){

            try {
                Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                final double latitude, longitude;

                latitude = location.getLatitude();
                longitude = location.getLongitude();

                Searcher searcher1 = new Searcher();
                searcher1.searchKeyword(context, "편의점", latitude, longitude, radius, page, 2, "63795804fcf1477cdd7226501d8ba39b", new OnFinishSearchListener() {
                    @Override
                    public void onSuccess(List<Item> itemList) {

                        MainActivity.ThemaItem.add(0,itemList.get(0));

                    }

                    @Override
                    public void onFail() {
                    }
                });
                Searcher searcher2 = new Searcher();
                searcher2.searchKeyword(context, "편의점", latitude, longitude, radius, page, 2, "63795804fcf1477cdd7226501d8ba39b", new OnFinishSearchListener() {
                    @Override
                    public void onSuccess(List<Item> itemList) {
                        MainActivity.ThemaItem.add(1,itemList.get(0));
                    }

                    @Override
                    public void onFail() {
                    }
                });
                Searcher searcher3 = new Searcher();
                searcher3.searchCategory(context, "FD6", latitude, longitude, radius, page, 2, "63795804fcf1477cdd7226501d8ba39b", new OnFinishSearchListener() {
                    @Override
                    public void onSuccess(List<Item> itemList) {
                        MainActivity.ThemaItem.add(2,itemList.get(0));
                    }

                    @Override
                    public void onFail() {
                    }
                });
                Searcher searcher4 = new Searcher();
                searcher4.searchCategory(context, "FD6", latitude, longitude, radius, 1, (int) (Math.random() * 3), "63795804fcf1477cdd7226501d8ba39b", new OnFinishSearchListener() {
                    @Override
                    public void onSuccess(List<Item> itemList) {
                        MainActivity.ThemaItem.add(3,itemList.get((int) (Math.random() * 15)));
                    }

                    @Override
                    public void onFail() {
                    }
                });
                registerAlarm rA = new registerAlarm(context);
                rA.registerAM(intent.getAction(),"3");


            } catch (Exception e) {
            }

        }
        if(intent.getAction().equals("ACTION.GET.FIVE")){

            try {
                Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                final double latitude, longitude;

                latitude = location.getLatitude();
                longitude = location.getLongitude();

                Searcher searcher1 = new Searcher();
                searcher1.searchKeyword(context, "편의점", latitude, longitude, radius, page, 2, "63795804fcf1477cdd7226501d8ba39b", new OnFinishSearchListener() {
                    @Override
                    public void onSuccess(List<Item> itemList) {

                        MainActivity.ThemaItem.add(0,itemList.get(0));

                    }

                    @Override
                    public void onFail() {
                    }
                });
                Searcher searcher2 = new Searcher();
                searcher2.searchKeyword(context, "편의점", latitude, longitude, radius, page, 2, "63795804fcf1477cdd7226501d8ba39b", new OnFinishSearchListener() {
                    @Override
                    public void onSuccess(List<Item> itemList) {
                        MainActivity.ThemaItem.add(1,itemList.get(0));
                    }

                    @Override
                    public void onFail() {
                    }
                });
                Searcher searcher3 = new Searcher();
                searcher3.searchCategory(context, "FD6", latitude, longitude, radius, page, 2, "63795804fcf1477cdd7226501d8ba39b", new OnFinishSearchListener() {
                    @Override
                    public void onSuccess(List<Item> itemList) {
                        MainActivity.ThemaItem.add(2,itemList.get(0));
                    }

                    @Override
                    public void onFail() {
                    }
                });
                Searcher searcher4 = new Searcher();
                searcher4.searchCategory(context, "FD6", latitude, longitude, radius, 1, (int) (Math.random() * 3), "63795804fcf1477cdd7226501d8ba39b", new OnFinishSearchListener() {
                    @Override
                    public void onSuccess(List<Item> itemList) {
                        MainActivity.ThemaItem.add(3,itemList.get((int) (Math.random() * 15)));
                    }

                    @Override
                    public void onFail() {
                    }
                });
                registerAlarm rA = new registerAlarm(context);
                rA.registerAM(intent.getAction(),"4");


            } catch (Exception e) {
            }

        }
        if(intent.getAction().equals("SIX")){

            try {
                Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                final double latitude, longitude;

                latitude = location.getLatitude();
                longitude = location.getLongitude();

                Searcher searcher1 = new Searcher();
                searcher1.searchKeyword(context, "편의점", latitude, longitude, radius, page, 2, "63795804fcf1477cdd7226501d8ba39b", new OnFinishSearchListener() {
                    @Override
                    public void onSuccess(List<Item> itemList) {

                        MainActivity.ThemaItem.add(0,itemList.get(0));

                    }

                    @Override
                    public void onFail() {
                    }
                });
                Searcher searcher2 = new Searcher();
                searcher2.searchKeyword(context, "편의점", latitude, longitude, radius, page, 2, "63795804fcf1477cdd7226501d8ba39b", new OnFinishSearchListener() {
                    @Override
                    public void onSuccess(List<Item> itemList) {
                        MainActivity.ThemaItem.add(1,itemList.get(0));
                    }

                    @Override
                    public void onFail() {
                    }
                });
                Searcher searcher3 = new Searcher();
                searcher3.searchCategory(context, "FD6", latitude, longitude, radius, page, 2, "63795804fcf1477cdd7226501d8ba39b", new OnFinishSearchListener() {
                    @Override
                    public void onSuccess(List<Item> itemList) {
                        MainActivity.ThemaItem.add(2,itemList.get(0));
                    }

                    @Override
                    public void onFail() {
                    }
                });
                Searcher searcher4 = new Searcher();
                searcher4.searchCategory(context, "FD6", latitude, longitude, radius, 1, (int) (Math.random() * 3), "63795804fcf1477cdd7226501d8ba39b", new OnFinishSearchListener() {
                    @Override
                    public void onSuccess(List<Item> itemList) {
                        MainActivity.ThemaItem.add(3,itemList.get((int) (Math.random() * 15)));
                    }

                    @Override
                    public void onFail() {
                    }
                });
                registerAlarm rA = new registerAlarm(context);
                rA.registerAM(intent.getAction(),"5");


            } catch (Exception e) {
            }

        }
        if(intent.getAction().equals("ACTION.GET.SEVEN")){

            try {
                Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                final double latitude, longitude;

                latitude = location.getLatitude();
                longitude = location.getLongitude();

                Searcher searcher1 = new Searcher();
                searcher1.searchKeyword(context, "편의점", latitude, longitude, radius, page, 2, "63795804fcf1477cdd7226501d8ba39b", new OnFinishSearchListener() {
                    @Override
                    public void onSuccess(List<Item> itemList) {

                        MainActivity.ThemaItem.add(0,itemList.get(0));

                    }

                    @Override
                    public void onFail() {
                    }
                });
                Searcher searcher2 = new Searcher();
                searcher2.searchKeyword(context, "편의점", latitude, longitude, radius, page, 2, "63795804fcf1477cdd7226501d8ba39b", new OnFinishSearchListener() {
                    @Override
                    public void onSuccess(List<Item> itemList) {
                        MainActivity.ThemaItem.add(1,itemList.get(0));
                    }

                    @Override
                    public void onFail() {
                    }
                });
                Searcher searcher3 = new Searcher();
                searcher3.searchCategory(context, "FD6", latitude, longitude, radius, page, 2, "63795804fcf1477cdd7226501d8ba39b", new OnFinishSearchListener() {
                    @Override
                    public void onSuccess(List<Item> itemList) {
                        MainActivity.ThemaItem.add(2,itemList.get(0));
                    }

                    @Override
                    public void onFail() {
                    }
                });
                Searcher searcher4 = new Searcher();
                searcher4.searchCategory(context, "FD6", latitude, longitude, radius, 1, (int) (Math.random() * 3), "63795804fcf1477cdd7226501d8ba39b", new OnFinishSearchListener() {
                    @Override
                    public void onSuccess(List<Item> itemList) {
                        MainActivity.ThemaItem.add(3,itemList.get((int) (Math.random() * 15)));
                    }

                    @Override
                    public void onFail() {
                    }
                });
                registerAlarm rA = new registerAlarm(context);
                rA.registerAM(intent.getAction(),"6");


            } catch (Exception e) {
            }

        }

    }
}
