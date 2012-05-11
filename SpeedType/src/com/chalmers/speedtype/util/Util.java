package com.chalmers.speedtype.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Display;

public class Util {

	private static Display display;
	private static Resources resources;
	private static float metersToPixelsX;
	private static float metersToPixelsY;

	public static void setConstants(DisplayMetrics metrics) {
		metersToPixelsX = metrics.xdpi / 0.0254f;
		metersToPixelsY = metrics.xdpi / 0.0254f;
	}

	public static void setDisplay(Display display) {
		Util.display = display;
	}

	public static Display getDisplay() {
		return display;
	}

	public static Resources getResources() {
		return resources;
	}

	public static void setResources(Resources resources) {
		Util.resources = resources;
	}

	public static void setMetersToPixelsX(float metersToPixelsX) {
		Util.metersToPixelsX = metersToPixelsX;
	}

	public static float getMetersToPixelsX() {
		return metersToPixelsX;
	}

	public static void setMetersToPixelsY(float metersToPixelsY) {
		Util.metersToPixelsY = metersToPixelsY;
	}

	public static float getMetersToPixelsY() {
		return metersToPixelsY;
	}

}
