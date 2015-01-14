package engine.module.view.imp;

import utils.elements.Button;
import utils.factory.Log;
import utils.listener.OnComplete;
import utils.listener.OnCompleteListener;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.coder5560.game.assets.Assets;
import com.coder5560.game.enums.Constants;
import com.coder5560.game.enums.GameEvent;

import engine.module.view.IController;
import engine.module.view.IViewElement;
import engine.module.view.ViewElement;
import engine.module.view.ViewName;

public class ViewTest3 extends ViewElement {
	Button	btn;

	public ViewTest3(ViewName viewParentName, IController controller,
			ViewName viewName, Rectangle bound) {
		super(viewParentName, controller, viewName, bound);
		buildComponent();
	}

	@Override
	public IViewElement buildComponent() {
		{
			btn = new Button(100, 60);
			btn.buildBackground(Assets.instance.ui.reg_ninepatch)
					.buildText("Next")
					.buildOnClick(new OnComplete() {

						@Override
						public void onComplete(Object data) {
							Log.d("Click to View Test 4");
							if (!getController().isContainView(
									ViewName.TEST_VIEW4)) {
								ViewTest4 viewTest4 = new ViewTest4(
										getViewName(), getController(),
										ViewName.TEST_VIEW4,
										Constants.GAME_BOUND);
								getController().addView(viewTest4);
							}
							getController().getView(ViewName.TEST_VIEW4).show(
									getController().getEngine(), 1f, null);
						}
					}).buidPosition(400, 200, Align.center).buidToContainer()
					.buildColor(Color.RED);
			addActor(btn);
			setBackground(new NinePatchDrawable(new NinePatch(
					Assets.instance.ui.reg_ninepatch, Color.GRAY)));
		}
		return this;
	}

	@Override
	public IViewElement show(Stage stage, float duration,
			OnCompleteListener listener) {
		super.show(stage, duration, listener);

		{
			// do some thing here
		}
		return this;
	}

	@Override
	public IViewElement hide(float duration, OnCompleteListener listener) {
		super.hide(duration, listener);
		{
			// do some thing here
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
