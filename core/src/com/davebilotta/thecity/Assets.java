package com.davebilotta.thecity;

import com.badlogic.gdx.graphics.Texture;

public class Assets {

	public static Texture grass;
	public static Texture water;
	public static Texture road;
	
	public static void load () {
		grass = new Texture("tiles/rpgTile019.png");
		water = new Texture("tiles/rpgTile029.png");
		road = new Texture("tiles/rpgTile024.png");
	}
}
