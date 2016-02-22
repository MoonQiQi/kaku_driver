package com.yichang.kaku.tools;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * @ClassName: GPSLocationUtil
 * @Description: 获取GPS各种信息
 * @author： 文
 * @Date： 2013-8-27 下午2:56:23
 */
public class GPSLocationUtil {
	static onGPSLocationChangedListener mCallback;

	public static Location getLocation(Context context) {
		LocationManager locationManager;
		String serviceName = Context.LOCATION_SERVICE;
		locationManager = (LocationManager) context
				.getSystemService(serviceName);
		Location location;
		// location = locationManager
		// .getLastKnownLocation(LocationManager.GPS_PROVIDER);
		// if (location == null)
		// location = locationManager
		// .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		// locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
		// 200, 10,
		// locationListener);
		// locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
		// 200, 10,
		// locationListener);
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_COARSE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		String provider = locationManager.getBestProvider(criteria, true);
		String provider1 = locationManager.getBestProvider(criteria, false);
		location = locationManager.getLastKnownLocation(provider);
		if (location == null)
			location = locationManager.getLastKnownLocation(provider1);
		locationManager.requestLocationUpdates(provider, 200, 10,
				locationListener);
		locationManager.requestLocationUpdates(provider1, 200, 10,
				locationListener);
		try {
			mCallback = (onGPSLocationChangedListener) context;
		} catch (ClassCastException e) {
			throw new ClassCastException(context.toString()
					+ " must implement onGPSLocationChangedListener");
		}
		return location;
	}

	private static LocationListener locationListener = new LocationListener() {
		public void onLocationChanged(Location location) {
			mCallback.onLocationChanged(location);
		}

		public void onProviderDisabled(String provider) {
		}

		public void onProviderEnabled(String provider) {
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	};

	public interface onGPSLocationChangedListener {
		public void onLocationChanged(Location location);
	}
}
