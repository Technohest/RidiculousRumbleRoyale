package com.technohest.java;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.technohest.constants.Settings;
import com.technohest.core.menu.RRRMain;

public class MainDesktop {
	public static void main (String[] args) {
		Settings settings = new Settings();
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = settings.getWidth();
		config.height = settings.getHeight();
		new LwjglApplication(new RRRMain(), config);
	}
}
