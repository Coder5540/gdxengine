package engine.module.view.imp;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.coder5560.game.enums.Constants;

public class TestShape {

	public float width = Constants.WIDTH_SCREEN;
	public float height = Constants.HEIGHT_SCREEN;
	public int rows = 3;
	public int cols = 5;
	public float pading = 10;

	public void drawFilled(ShapeRenderer shapeRenderer) {
		for (int i = 0; i < 15; i++) {
			int r = i % rows;
			float w = width / (cols + 1);

			int c = i % cols;
			float h = height / (rows + 1);
			shapeRenderer.circle((c + 1) * w, h * (r + 1), 10);
		}
	}

	public void drawLine(ShapeRenderer shapeRenderer) {

	}

}
