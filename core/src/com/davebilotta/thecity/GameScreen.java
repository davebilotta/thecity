package com.davebilotta.thecity;

import com.badlogic.gdx.Screen;

public class GameScreen implements Screen {

	TheCity game;
	
	public GameScreen(TheCity game) {
		this.game = game;
	}
	
	@Override
	public void render(float delta) {
		game.update(delta);
		
		// Now do rendering here
		// renderBackground();
		// renderMenu();
		// renderCity();
		
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
