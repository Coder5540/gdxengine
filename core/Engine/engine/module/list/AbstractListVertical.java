package engine.module.list;

import utils.factory.Factory;
import utils.listener.OnSelectListener;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

import engine.element.GroupElement;
import engine.module.list.imp.DataItemFood;
import engine.module.list.imp.ItemListFood;

public abstract class AbstractListVertical extends GroupElement {

	public Table					root;
	public Pool<AbstractItem>		pool;

	public Array<AbstractDataItem>	datas			= new Array<AbstractDataItem>();
	public Array<AbstractItem>		items			= new Array<AbstractItem>();

	public float					scrollPosition	= 0;
	public float					flingTime		= 0.1f;
	public float					flingTimer		= 0;
	public float					amountY			= 0;
	public float					velocityY		= 0;

	/* how much expand of the scrollView */
	public float					expandViewportY;
	public int						indexOverscrollUp;
	public int						indexOverscrollDown;
	public boolean					overscrollDown	= false;
	public boolean					overscrollUp	= false;

	public AbstractListVertical(float width, float height) {
		super();
		{
			this.setSize(width, height);
			scrollPosition = height;
			root = new Table();
			root.setSize(getWidth(), getHeight());
			root.setTouchable(Touchable.enabled);
			root.setClip(true);
			expandViewportY = height / 2;
		}
		buildListener();
		addActor(root);
	}

	/* Return start index and last index of the list */
	public Vector2 createList(Array<AbstractDataItem> listData, float height) {
		Vector2 indexes = new Vector2();
		float totalHeight = 0;
		int index = 0;
		while (totalHeight < (height + expandViewportY)
				&& indexes.y <= listData.size) {
			AbstractDataItem data = listData.get(index++);
			totalHeight += data.height;
			AbstractItem item = pool.obtain();
			item.recreateData(data);
			items.add(item);
			if (!item.hasParent() || item.getParent() != root) {
				root.addActor(item);
			}
		}
		return indexes;
	}

	public void createPool() {
		pool = new Pool<AbstractItem>(10, 20) {
			@Override
			protected AbstractItem newObject() {
				return new ItemListFood(new DataItemFood(-1, "", "",
						getWidth(), 60, onSelectListener));
			}
		};
	}

	@Override
	public void act(float deltatime) {
		update(deltatime);
		super.act(deltatime);
	}

	public void update(float delta) {
		float alpha = flingTimer / flingTime;
		amountY = velocityY * alpha * delta;
		flingTimer -= delta * 0.09f;
		if (flingTimer <= 0) {
			velocityY = 0;
		} else {
			scrollPosition += amountY;
			validateElement(amountY);
		}

		while (items.first().getY() >= getHeight() + expandViewportY) {
			AbstractItem item = items.first();
			item.remove();
			pool.free(item);
			items.removeIndex(0);
		}
		while (items.first().getY() < (getHeight() + expandViewportY)) {
			AbstractItem item = pool.obtain();
			item.recreateData(datas.get(Factory.getPrevious(items.first()
					.getIndex(), 0, datas.size - 1)));
			item.setY(items.first().getY(Align.top));
			items.insert(0, item);
			root.addActor(item);
		}

		while (items.peek().getY() <= -(getHeight() + expandViewportY)) {
			AbstractItem item = items.peek();
			item.remove();
			pool.free(item);
			items.removeValue(item, false);
		}

		while (items.peek().getY() > -(getHeight() + expandViewportY)) {
			AbstractItem item = pool.obtain();
			item.recreateData(datas.get(Factory.getNext(
					items.peek().getIndex(), 0, datas.size - 1)));
			item.setY(items.peek().getY(Align.bottom) - item.getHeight());
			items.add(item);
			root.addActor(item);
		}
	}
	
	public boolean available(){
		if(items == null || items.size <=0 ) return false;
		
		return true;
	}
	
	
	

	private void buildListener() {
		root.addListener(new ActorGestureListener() {
			@Override
			public void touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				flingTimer = 0;
				velocityY = 0;
			}

			@Override
			public void pan(InputEvent event, float x, float y, float deltaX,
					float deltaY) {
				scrollPosition += deltaY;
				validateElement(deltaY);
			}

			@Override
			public void fling(InputEvent event, float velocityX,
					float velocityY, int button) {
				AbstractListVertical.this.velocityY = velocityY;
				flingTimer = flingTime;
			}
		});
	}

	public void validateElement() {
		if (scrollPosition <= getHeight()) {
			scrollPosition = getHeight();
			flingTimer = 0;
		} else if (getTotalChildrenHeight() - scrollPosition <= 0) {
			scrollPosition = getTotalChildrenHeight();
		}
		float currentPosition = 0;
		for (int index = 0; index < items.size; index++) {
			AbstractItem itemView = items.get(index);
			currentPosition += itemView.getHeight();
			itemView.setY(scrollPosition - currentPosition);
		}
	}

	// call when the scroll type is in type of pool
	private void validateElement(float amount) {
		for (int index = 0; index < items.size; index++) {
			AbstractItem itemView = items.get(index);
			itemView.setY(itemView.getY() + amount);
		}
	}

	private float getTotalChildrenHeight() {
		float totalHeight = 0;
		for (int index = 0; index < items.size; index++) {
			ItemListFood itemView = (ItemListFood) items.get(index);
			totalHeight += itemView.getHeight();
		}
		return totalHeight;
	}

	/*
	 * Over scroll up at the specific element in the data store. this feature is
	 * just available in the vertical orientation.
	 */
	public void setOverScrollUp(boolean overScrollUp, int IndexOverscrollUp) {

	}

	/*
	 * Over scroll down at the specific element in the data store. this feature
	 * is just available in the vertical orientation.
	 */
	public void setOverScrollDown(boolean overScrollDown,
			int IndexOverscrollDown) {

	}

	OnSelectListener	onSelectListener	= new OnSelectListener() {

												@Override
												public void onSelect(int i,
														Object data) {

												}
											};

}
