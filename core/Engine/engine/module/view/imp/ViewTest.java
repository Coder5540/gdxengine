package engine.module.view.imp;

import utils.elements.Button;
import utils.factory.Log;
import utils.listener.OnComplete;
import utils.listener.OnCompleteListener;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.coder5560.game.assets.Assets;
import com.coder5560.game.enums.Constants;
import com.coder5560.game.enums.GameEvent;

import engine.module.view.IController;
import engine.module.view.IViewElement;
import engine.module.view.ViewElement;
import engine.module.view.ViewName;

public class ViewTest extends ViewElement {
	public ViewTest(ViewName viewParentName, IController controller,
			ViewName viewName, Rectangle bound) {
		super(viewParentName, controller, viewName, bound);
		buildComponent();
	}
	public IViewElement buildComponent() {
		{
			setBackground(new NinePatchDrawable(new NinePatch(
					Assets.instance.ui.reg_ninepatch, new Color(
							MathUtils.random(0, 255) / 255f, MathUtils.random(
									0, 255) / 255f,
							MathUtils.random(0, 255) / 255f, 1f))));

			for (int k = 0; k < 6; k++) {
				for (int i = 0; i < 6; i++) {
					Button btn = new Button(100, 60);
					btn.buildBackground(Assets.instance.ui.reg_ninepatch)
							.buildText("Btn " + (k * 6 + i))
							.buildOnClick(new OnComplete() {
								@Override
								public void onComplete(Object data) {
									Log.d("Click to View Test");
									if (!getController().isContainView(
											ViewName.TEST_VIEW2)) {
										ViewTest2 viewTest2 = new ViewTest2(
												getViewName(),
												getController(),
												ViewName.TEST_VIEW2,
												Constants.GAME_BOUND);
										getController().addView(viewTest2);
									}
									getController()
											.getView(ViewName.TEST_VIEW2)
											.show(getController().getEngine(),
													1f, null);
								}
							})
							.buidPosition(100 * i + (i - 1) * 6 + 100,
									60 * k + (k - 1) * 5 + 60, Align.center)
							.buidToContainer()
							.buildColor(
									new Color(MathUtils.random(0, 255) / 255f,
											MathUtils.random(0, 255) / 255f,
											MathUtils.random(0, 255) / 255f, 1f));
					addActor(btn);
				}
			}
		}
		return this;
	}

	@Override
	public IViewElement show(Stage stage, float duration,
			OnCompleteListener listener) {
		super.show(stage, duration, listener);
		setTouchable(Touchable.enabled);
		{
			// do some thing here
		}
		return this;
	}

	@Override
	public IViewElement hide(float duration, OnCompleteListener listener) {
		super.hide(duration, listener);
		{

		}
		return this;
	}

	@Override
	public void back() {
		super.back();

	}

	@Override
	public IViewElement onGameEvent(GameEvent gameEvent) {
		{
			// do some thing here
		}
		return this;
	}
	@Override
	public int getId() {
		return 0;
	}

	
}
