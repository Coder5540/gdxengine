package engine.module.view.imp;

import utils.listener.OnCompleteListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Array;

import engine.element.iml.ClickItemEvent;
import engine.element.iml.HomeItem;
import engine.element.iml.HomeItem.HomeItemName;
import engine.element.iml.HomeItemData;
import engine.module.list.ListView;
import engine.module.list.Page;
import engine.module.view.IController;
import engine.module.view.IViewElement;
import engine.module.view.ViewElement;
import engine.module.view.ViewName;

public class HomeView extends ViewElement {

	public int NUM_COLS = 4;
	public int NUM_ROWS = 3;
	public float pading = 40f;
	public float iconW;
	public float iconH;
	public float iconSize;
	int align = Align.topLeft;
	public ListView listView;
	public Page currentPage;
	Array<HomeItemData> itemDatas = new Array<HomeItemData>();

	public HomeView(ViewName viewParentName, IController controller,
			ViewName viewName, Rectangle bound) {
		super(viewParentName, controller, viewName, bound);
		listView = new ListView(getWidth(), getHeight());
		buildComponent();
	}

	public void calculateSize() {
		iconW = (getWidth() - (NUM_COLS + 1) * pading) / (NUM_COLS);
		iconH = (getHeight() - (NUM_ROWS + 1) * pading) / (NUM_ROWS);
		iconSize = (iconW < iconH) ? iconW : iconH;
	}

	Texture texture = new Texture(Gdx.files.internal("Img/Add-User-icon.png"));

	public IViewElement buildComponent() {
		calculateSize();
		for (int i = 0; i < 10000; i++) {
			addHomeItem(new HomeItemData(HomeItemName.ITEM_DEFAULT, iconSize,
					iconSize, texture, "App " + i, homeItemClick));
		}
		return this;
	}

	public void rebuildUI() {
		this.clearChildren();
	}

	public Vector2 getPositionItem(int index) {
		int col = index % NUM_COLS;
		int row = index / NUM_COLS;
		float w = getWidth() / (NUM_COLS);
		float h = getHeight() / (NUM_ROWS);
		if (align == Align.bottomLeft) {
			return new Vector2(w * (col + .5f), h * (row + .5f));
		} else if (align == Align.bottomRight) {
			return new Vector2(w * ((NUM_COLS - col - 1) + .5f), h
					* (row + .5f));
		} else if (align == Align.topLeft) {
			return new Vector2(w * (col + .5f), h
					* ((NUM_ROWS - row - 1) + .5f));
		} else if (align == Align.topRight) {
			return new Vector2(w * ((NUM_COLS - col - 1) + .5f), h
					* ((NUM_ROWS - row - 1) + .5f));
		}
		return new Vector2(w * (col + .5f), h * (row + .5f));
	}

	public HomeView addHomeItem(HomeItemData itemData) {
		if (isContainIcon(itemData.itemName, itemData.title)) {
			return this;
		}
		HomeItem item = new HomeItem(itemData);
		item.setPosition(getPositionItem(itemDatas.size).x,
				getPositionItem(itemDatas.size).y, Align.center);
		itemDatas.add(itemData);
		this.addActor(item);
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

	@Override
	public int getId() {
		return 0;
	}

}
