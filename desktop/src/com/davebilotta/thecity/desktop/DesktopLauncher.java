package com.davebilotta.thecity.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.davebilotta.thecity.TheCity;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.title = "The City";
		config.width = 1024;
		config.height = 768;
		
		new LwjglApplication(new TheCity(), config);
	}
}
