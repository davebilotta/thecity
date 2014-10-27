package com.davebilotta.thecity;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.davebilotta.thecity.Person.Gender;

public class GameScreen implements Screen {

	TheCity game;
	SpriteBatch batch;
	Texture red_dot,orange_dot,yellow_dot,green_dot,blue_dot,white_dot;
	
	public GameScreen(TheCity game) {
		this.game = game;
		this.batch = new SpriteBatch();
		
		red_dot = new Texture("red_dot.png");
		orange_dot = new Texture("orange_dot.png");
		yellow_dot = new Texture("yellow_dot.png");
		green_dot = new Texture("green_dot.png");
		blue_dot = new Texture("blue_dot.png");
		white_dot = new Texture("white_dot.png");
	}
	
	@Override
	public void render(float delta) {
		game.update(delta);
		
		Gdx.gl20.glClearColor(0, 0, 0, 1f);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT); // clear the buffer
		
		// Now do rendering here
		// renderBackground();
		// renderMenu();
		renderCitizens(delta);
		
	}
	
	public void renderCitizens(float delta) {
		Iterator<Person> i = this.game.city.citizens.iterator();
		Person p;
		Texture texture;
		
		batch.begin();
		while (i.hasNext()) {
			p = i.next();
			
			if (p.getGender() == Gender.MALE) texture = blue_dot;
			else {
				if (p.fertile) texture = green_dot;
				else texture = red_dot;
			}
			
			batch.draw(texture, p.position.x, p.position.y);
			
		}
		
		batch.end();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		Utils.log("gamescreen-RESIZE");
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		Utils.log("gamescreen-SHOW");
		
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		Utils.log("gamescreen-HIDE");
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		Utils.log("gamescreen-PAUSE");
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		Utils.log("gamescreen-RESUME");
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		Utils.log("gamescreen-DISPOSE");
	}

}
