package com.davebilotta.thecity;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class Assets {

	public static ArrayList<Texture> textures;
	public static ArrayList<Sprite> sprites;
	
	public static Texture grass, water, road, red_dot, orange_dot, yellow_dot,
			green_dot, blue_dot, white_dot,health;

	
	public static Texture low_income_housing;
	public static TextureRegion[] ui = new TextureRegion[9];
	
	public static Sprite buildButton;
	
	public static LabelStyle menuLabelStyle;
	public static BitmapFont buttonFont, messageFont, menuFont;
	public static LabelStyle buttonLabelStyle;
	
	public static void load() {
		loadTextures();
		loadFonts();
	}

	public static void loadTextures() {
		textures = new ArrayList<Texture>();

		red_dot = createTexture("red_dot.png");
		orange_dot = createTexture("orange_dot.png");
		yellow_dot = createTexture("yellow_dot.png");
		green_dot = createTexture("green_dot.png");
		blue_dot = createTexture("blue_dot.png");
		white_dot = createTexture("white_dot.png");

		grass = createTexture("tiles/rpgTile019.png");
		water = createTexture("tiles/rpgTile029.png");
		road = createTexture("tiles/rpgTile024.png");

		low_income_housing = createTexture("lih.png");
		
		health = createTexture("health.png");

		// Texture regions 
		//Texture uiTemp = createTexture("uipack_rpg_sheet.png");
		
		ui[0] = new TextureRegion(new Texture("ui/bar1.png"));
		ui[1] = new TextureRegion(new Texture("ui/bar2.png"));
		ui[2] = new TextureRegion(new Texture("ui/bar3.png"));
		ui[3] = new TextureRegion(new Texture("ui/bar4.png"));
		ui[4] = new TextureRegion(new Texture("ui/buttonBeige.png"));
		ui[5] = new TextureRegion(new Texture("ui/buttonBeigePressed.png"));
		ui[6] = new TextureRegion(new Texture("ui/buttonBrown.png"));
		ui[7] = new TextureRegion(new Texture("ui/buttonBrownPressed.png"));
		ui[8] = new TextureRegion(new Texture("ui/bar7.png"));
	}

	
	@SuppressWarnings("deprecation")
	public static void loadFonts() {

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
				Gdx.files.internal("fonts/Bevan.ttf"));
		menuFont = generator.generateFont(20);

		menuLabelStyle = new LabelStyle();
		menuLabelStyle.font = menuFont;
		menuLabelStyle.fontColor = Color.WHITE;

		buttonFont = generator.generateFont(10);
		messageFont = generator.generateFont(16);
		generator.dispose();
	}

	/*
	 * Dispose of all textures in textures ArrayList
	 */
	public static void dispose() {
		Iterator<Texture> iter = textures.iterator();
		while (iter.hasNext()) {
			iter.next().dispose();
		}
	}

	/*
	 * Store textures in ArrayList so they can be easily disposed of later
	 */
	public static Texture createTexture(String path) {
		// Store

		Texture text = new Texture(path);
		textures.add(text);

		return text;
	}


}
