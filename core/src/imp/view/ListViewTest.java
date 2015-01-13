package imp.view;

import utils.listener.OnCompleteListener;
import utils.listener.OnSelectListener;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Array;
import com.coder5560.game.assets.Assets;
import com.coder5560.game.enums.Constants;
import com.coder5560.game.views.IViewController;
import com.coder5560.game.views.View;

import engine.module.list.AbstractDataItem;
import engine.module.list.imp.DataItemFood;
import engine.module.list.imp.ListFoodVertical;

public class ListViewTest extends View {
	// ScrollView scrollView;
	ListFoodVertical	listVertical;

	@Override
	public void build(Stage stage, IViewController viewController,
			String viewName, Rectangle bound) {
		super.build(stage, viewController, viewName, bound);
	}


	// public void buildComponent() {
	// setBackground(new NinePatchDrawable(new NinePatch(
	// Assets.instance.ui.reg_ninepatch, Color.BLACK)));
	// scrollView = new ScrollView(getWidth() / 2, getHeight() / 2);
	// scrollView.setPosition(getWidth() / 2, getHeight() / 2, Align.center);
	// addActor(scrollView);
	// }
	public void buildComponent() {
		setBackground(new NinePatchDrawable(new NinePatch(
				Assets.instance.ui.reg_ninepatch, Color.BLACK)));

		OnSelectListener onSelectListener = new OnSelectListener() {

			@Override
			public void onSelect(int i, Object data) {

			}
		};

		{
			listVertical = new ListFoodVertical(getWidth() / 2, getHeight() / 2);
			listVertical.setPosition(getWidth() / 2, getHeight() / 2,
					Align.center);
			Array<AbstractDataItem> datas = new Array<AbstractDataItem>();
			for (int i = 0; i < 500000; i++) {
				datas.add(new DataItemFood(i, "Element " + i, "Description : "
						+ i, listVertical.getWidth(), 60 + i % 20,
						onSelectListener));
			}
			listVertical.createPool();
			listVertical.createList(datas, Constants.HEIGHT_SCREEN / 2);
			listVertical.validateElement();
			addActor(listVertical);
		}

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
