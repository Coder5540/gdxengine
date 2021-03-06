package imp.view;

import utils.listener.OnCompleteListener;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.coder5560.game.views.IViewController;
import com.coder5560.game.views.View;

import engine.module.list.ListView;

public class PageView extends View {
	ListView listView;

	@Override
	public void build(Stage stage, IViewController viewController,
			String viewName, Rectangle bound) {
		super.build(stage, viewController, viewName, bound);
	}

	public void buildComponent() {
		listView = new ListView(getWidth() / 2, getHeight() / 2);
		listView.debugAll();
		listView.setPosition(getWidth() / 2, getHeight() / 2, Align.center);
		addActor(listView);
	}

	@Override
	public void show(OnCompleteListener listener) {
		super.show(listener);
	}

	@Override
	public void hide(OnCompleteListener listener) {
		super.hide(listener);
	}

	@Override
	public void destroyComponent() {
		super.destroyComponent();
	}

	@Override
	public void back() {
		super.back();
	}

}
