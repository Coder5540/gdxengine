package com.coder5560.game.screens;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import engine.module.screens.AbstractGameScreen;
import engine.module.screens.GameCore;
import engine.module.view.Controller;
import engine.module.view.VController;
import engine.module.view.imp.TestShape;

public class TestScreen extends AbstractGameScreen {
	Controller controller;

	public TestScreen(GameCore game) {
		super(game);
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
	}

	@Override
	public void show() {
		super.show();
		controller = new VController(parent, getEngine(), this);
		controller.buildController();
	}

	@Override
	public void update(float delta) {

	}

	ShapeRenderer shapeRenderer;

	@Override
	public void render(float delta) {
		super.render(delta);

		if (shapeRenderer == null) {
			shapeRenderer = new ShapeRenderer();
		}

//		shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
//
//		shapeRenderer.begin(ShapeType.Filled);
//		drawFilled(shapeRenderer);
//		shapeRenderer.end();
//
//		shapeRenderer.begin(ShapeType.Line);
//		drawLine(shapeRenderer);
//		shapeRenderer.end();
	}

	TestShape testShape = new TestShape();

	public void drawFilled(ShapeRenderer shapeRenderer) {
//		testShape.drawFilled(shapeRenderer);
	}

	public void drawLine(ShapeRenderer shapeRenderer) {
//		testShape.drawLine(shapeRenderer);
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.BACK || keycode == Keys.ESCAPE) {
			if (controller != null) {
				controller.onBack();
			}
		}
		return false;
	}

}
