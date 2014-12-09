package com.davebilotta.thecity;

import java.util.Iterator;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.davebilotta.thecity.Person.Gender;

public class ScreenRenderer {

	TheCity game;
	SpriteBatch batch;
	GameScreen screen;
	ShapeRenderer sr;
	
	//screenWidth = Gdx.graphics.getWidth();
	//screenHeight = Gdx.graphics.getHeight();

	//renderWidth = 16; // 1024px / 64
	//renderHeight = 12; // 768px / 64
	//tileSize = 64;
	//itemSize = 32;

	public ScreenRenderer(TheCity game, GameScreen screen, SpriteBatch batch) {
		this.game = game;
		this.screen = screen;
		this.batch = batch;
		
		this.sr = new ShapeRenderer();
	}
	
	public void render(float delta) {
		batch.begin();
		renderCitizens(delta);
		renderBuildings(delta);
		
		renderUIComponents();
		batch.end();

	}
	
	public void renderCitizens(float delta) {
		Person p;
		Texture texture;

		Iterator<Person> i = this.game.city.citizens.iterator();
		// TODO: Only render those whose tileX and tileY are within the viewing
		// window
		while (i.hasNext()) {
			p = i.next();

			// figure out if in view
			if (inView(p)) {
				
			if (p.isSelected()) {
				texture = Assets.white_dot;
			}
			else {
				if (p.isPaused()) { 
					texture = Assets.yellow_dot;
				}
				else {
				if (p.getGender() == Gender.MALE)
					texture = Assets.blue_dot;
				else {
					if (p.fertile)
						texture = Assets.green_dot;
					else
						texture = Assets.red_dot;
				}
				}
			}

				Vector2 screenPos = worldToScreen(p.getPosition());
				// TODO: need to factor item's size when rendering
				batch.draw(texture,screenPos.x,screenPos.y);
				
				if (p.isSelected()) {
					renderHealthBar(p,screenPos);
				}
				
			}
		} // end while

		
	}
	
	public void renderHealthBar (GameObject p, Vector2 pos) {
		// display at
		int healthSize = 4;
		int health = p.getHealth();
		// render max of 8
		//int h = Math.floor((double) (((health/100) * itemSize) / healthSize));
		int h = (int) ((double) (health/100.0f) * this.screen.itemSize) / healthSize;
		
		//Utils.log("drawing " + h + " bars");
		
		for (int i = 0; i < h; i++) {
			batch.draw(Assets.health,(pos.x + (healthSize * i)),pos.y + this.screen.itemSize + 5);
		}
	}
	
	public void renderBuildings(float delta) {
		GameObject b;
		Texture texture;

		Iterator<GameObject> i = this.game.city.buildings.iterator();
		// TODO: Only render those whose tileX and tileY are within the viewing
		// window
		while (i.hasNext()) {
			b = i.next();
			
			Vector2 screenPos = worldToScreen(b.getPosition());
			
			if (inView(b)) {
				batch.draw(b.getTexture(),screenPos.x,screenPos.y);
			}
		} // end while
	} // end renderBuildings class


	
	public void renderUIComponents() {
		// render city information
		// Update population and age (top bar)
		this.screen.populationText.setText(this.game.city.getPopulationText());
		this.screen.ageText.setText(this.game.city.getAgeText());
		
		if (this.screen.bottomBarVisible) {
			sr.begin(ShapeType.Filled);
			sr.setColor(Color.OLIVE);
		
		// Render bottom bar - height = 2 tiles high (64px)
		sr.rect(0, 0, this.screen.screenWidth, (this.screen.tileSize * 2));
		sr.end();
		}
		
		// build button 
		//batch.draw(Assets.ui[0],20,20);
		
	}
	
	public boolean inView(GameObject item) {
		// position = position in world
		Vector2 position = item.getPosition();

		if (((position.x + this.screen.itemSize/2)>= this.screen.worldX) && (position.x <= (this.screen.screenWidth + this.screen.worldX))
		&& ((position.y + this.screen.itemSize/2)>= this.screen.worldY) && (position.y <= (this.screen.screenHeight + this.screen.worldY)) ) {
			
			return true;
		} else {

			return false;
		}
	}
	
	public Vector2 worldToScreen(Vector2 position) {
		return new Vector2(((position.x - this.screen.worldX)),
				(( (position.y - this.screen.worldY))) );
	}
	
	public Vector2 screenToWorld(float screenX, float screenY) {
		return new Vector2((screenX + this.screen.worldX ),((this.screen.screenHeight - screenY + this.screen.worldY)));
	}


	
}
