package com.technohest.java;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.technohest.constants.Settings;
import com.technohest.core.menu.RRRMain;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import sun.applet.Main;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class MainDesktop {
	public static void main (String[] args) {
		Settings settings = new Settings();
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = settings.getWidth();
		config.height = settings.getHeight();
		new LwjglApplication(new RRRMain(), config);
	}
}
