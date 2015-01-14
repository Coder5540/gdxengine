package engine.module.view.imp;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.coder5560.game.enums.Constants;

public class TestShape {

	public Rectangle	bound;
	public float		width;
	public float		height;
	public int			rows	= 3;
	public int			cols	= 5;
	public float		pading	= 4;

	Array<Vector2>		points	= new Array<Vector2>();
	public float		elementSize;

	public TestShape() {
		super();
		bound = new Rectangle(Constants.WIDTH_SCREEN / 4,
				Constants.HEIGHT_SCREEN / 4, Constants.WIDTH_SCREEN / 2,
				Constants.HEIGHT_SCREEN / 2);
		width = bound.width;
		height = bound.height;
		calculateVariable();
	}

	public void calculateVariable() {
		points.clear();
		for (int i = 0; i < 15; i++) {
			int r = i % rows;
			float w = width / (cols);

			int c = i % cols;
			float h = height / (rows);
			Vector2 point = new Vector2((c + .5f) * w, h * (r + .5f));
			points.add(point.add(bound.x, bound.y));
		}

		float minW = (width - (cols + 1) * pading) / cols;
		float minH = (height - (rows + 1) * pading) / rows;
		elementSize = (minW < minH) ? minW : minH;
	}

	public void drawFilled(ShapeRenderer shapeRenderer) {
		for (Vector2 point : points) {
			shapeRenderer.circle(point.x, point.y, 10);
		}
	}

	public void drawLine(ShapeRenderer shapeRenderer) {
		for (Vector2 point : points) {
			shapeRenderer.rect(point.x - elementSize / 2, point.y - elementSize
					/ 2, elementSize, elementSize);
		}
	}

}
