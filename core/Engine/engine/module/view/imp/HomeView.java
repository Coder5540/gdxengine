package engine.module.view.imp;

import org.omg.PortableServer.POA;

import utils.listener.OnCompleteListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.coder5560.game.enums.Constants;

import engine.element.iml.ClickItemEvent;
import engine.element.iml.HomeItem;
import engine.element.iml.HomeItem.HomeItemName;
import engine.element.iml.HomeItemData;
import engine.module.view.IController;
import engine.module.view.IViewElement;
import engine.module.view.ViewElement;
import engine.module.view.ViewName;

public class HomeView extends ViewElement {

	public int NUM_COLS = 5;
	public int NUM_ROWS = 3;
	public float pading = 20f;
	public float iconW = 100;
	public float iconH = 100;

	Array<HomeItemData> itemDatas = new Array<HomeItemData>();

	public HomeView(ViewName viewParentName, IController controller,
			ViewName viewName, Rectangle bound) {
		super(viewParentName, controller, viewName, bound);
		buildComponent();
	}

	public void calculateSize() {
		iconW = (getWidth() - (NUM_COLS + 1) * pading) / NUM_COLS;
		iconH = (getHeight() - (NUM_ROWS + 1) * pading) / NUM_ROWS;
	}

	public IViewElement buildComponent() {
//		calculateSize();
		HomeItemData defaultData = new HomeItemData(HomeItemName.ITEM_DEFAULT,
				iconW, iconW, new Texture(
						Gdx.files.internal("Img/Add-User-icon.png")),
				"Function", homeItemClick);
		for (int i = 0; i < 5; i++) {
			HomeItem homeItem = new HomeItem(defaultData);
			homeItem.setPosition(getPositionItem(i).x, getPositionItem(i).y,
					Align.center);
			this.addActor(homeItem);
		}
		return this;
	}

	public void rebuildUI() {
		this.clearChildren();

	}

	public Vector2 getPositionItem(int index) {
		int col = index % NUM_COLS;
		int row = index % NUM_ROWS;
		float w = Constants.WIDTH_SCREEN / (NUM_COLS + 1);
		float h = Constants.HEIGHT_SCREEN / (NUM_ROWS + 1);
		return new Vector2(w * (col + 1), h * (row + 1));

	}

	public HomeView addHomeItem(HomeItemData itemData) {
		if (isContainIcon(itemData.itemName, itemData.title)) {
			return this;
		}
		itemDatas.add(itemData);
		rebuildUI();
		return this;
	}

	public boolean isContainIcon(HomeItemName itemName, String homeItemTitle) {
		if (itemName == HomeItemName.ITEM_DEFAULT)
			return false;
		for (HomeItemData itemData : itemDatas) {
			if (itemData.itemName == itemName
					&& homeItemTitle.equalsIgnoreCase(itemData.title)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public IViewElement show(Stage stage, float duration,
			OnCompleteListener listener) {
		super.show(stage, duration, listener);
		this.setTouchable(Touchable.enabled);
		return this;
	}

	@Override
	public IViewElement hide(float duration, OnCompleteListener listener) {
		super.hide(duration, listener);
		return this;
	}

	ClickItemEvent homeItemClick = new ClickItemEvent() {

		@Override
		public void broadcastEvent(HomeItemName itemName) {

		}
	};

}
