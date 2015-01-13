package engine.module.list;

import utils.factory.Factory;
import utils.factory.FontFactory.fontType;
import utils.factory.Log;
import utils.listener.OnClickListener;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.coder5560.game.assets.Assets;

import engine.element.GroupElement;

public class ScrollView extends GroupElement {
	public Array<String>	datas				= new Array<String>();
	public Array<IItemView>	items				= new Array<IItemView>();

	int						currentStartIndex	= 0;
	int						currentEndIndex		= 0;
	float					scrollPosition		= 0;

	private float			flingTime			= 0.1f;
	private float			flingTimer			= 0;
	private float			amountY				= 0;
	private float			velocityY			= 0;
	Table					root;

	public ScrollView(float width, float height) {
		super();
		this.setSize(width, height);
		scrollPosition = height;
		root = new Table();
		root.setSize(getWidth(), getHeight());
		root.setTouchable(Touchable.enabled);
		root.setClip(true);
		for (int i = 0; i < 2000; i++) {
			datas.add("Element " + i);
		}

		for (int index = 0; index < datas.size; index++) {
			ItemView itemView = new ItemView(index, datas.get(index),
					"Description : " + index, width, 40 + MathUtils.random(10,
							40));
			itemView.recreateData();
			items.add(itemView);
		}
		buildListener();
		addActor(root);
		validateElement();
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
			validateElement();
		}

		for (int i = 0; i < items.size; i++) {
			IItemView item = items.get(i);
			if (item.getPositionY() > 3 * getHeight() / 2) {
				currentStartIndex = i;
				((ItemView) item).remove();
				// if (item.hasParents()) {
				// item.removeFromParent();
				// item.resetData();
				// }
			} else {
				// if (!item.hasParents()) {
				// item.recreateData();
				// item.addToParent(root);
				// }
				root.addActor(((ItemView) item));

			}
			if (item.getPositionY() < -1.5 * getHeight()) {
				currentEndIndex = i;
				((ItemView) item).remove();

				// if (item.hasParents()) {
				// item.removeFromParent();
				// item.resetData();
				// }
			}
		}
		Log.d("Scroll Size : " + root.getChildren().size);
	}

	public void buildListener() {

		root.addListener(new ActorGestureListener() {
			@Override
			public void touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				flingTimer = 0;
			}

			@Override
			public void pan(InputEvent event, float x, float y, float deltaX,
					float deltaY) {
				if (getTotalChildrenHeight() > getHeight()) {
					scrollPosition += deltaY;
					validateElement();
				}
			}

			@Override
			public void fling(InputEvent event, float velocityX,
					float velocityY, int button) {
				ScrollView.this.velocityY = velocityY;
				if (getTotalChildrenHeight() > getHeight()) {
					flingTimer = flingTime;
				}
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
			IItemView itemView = items.get(index);
			currentPosition += itemView.getDimesion().y;
			itemView.setPositionY(scrollPosition - currentPosition);
		}
	}

	private float getTotalChildrenHeight() {
		float totalHeight = 0;
		for (int index = 0; index < items.size; index++) {
			ItemView itemView = (ItemView) items.get(index);
			totalHeight += itemView.getHeight();
		}
		return totalHeight;
	}

	public void onPan(float x, float y, float deltaX, float deltaY) {

	}

	public void onFling(float velocityX, float velocityY) {

	}

	public void onStop(float x, float y) {
	}

}

class ItemView extends GroupElement implements IItemView {
	private Image	bg;
	private Image	icon;
	private LabelStyle	styleTitle, styleDesciption;
	private Label		lbTitle, lbShortDescription;
	private Color		bgColor, wordColor, desColor;
	private String		title, description;
	public int			index;

	OnClickListener		onClickListener;

	public ItemView(final int index, final String title,
			final String description, final float width, float height) {
		this.setSize(width, height);
		this.index = index;
		this.title = title;
		this.description = description;
	}

	private void createColor() {
		bgColor = new Color(10 / 255f, 10 / 255f, 10 / 255f, 0.95f);
		wordColor = new Color(255 / 255f, 255 / 255f, 255 / 255f, 0.95f);
		desColor = new Color(245 / 255f, 245 / 255f, 245 / 255f, 0.95f);
	}

	public void valid() {
		float w = this.getWidth();
		float h = this.getHeight();
		bg.setSize(w - 10, h - 2);
		bg.setPosition(5, 1);
		lbShortDescription.setWidth(2 * w / 3);
		lbTitle.setPosition(10, h - lbTitle.getHeight());
		lbShortDescription.setPosition(10, lbTitle.getHeight() - 20);
	}

	@Override
	public Vector2 getDimesion() {
		return new Vector2(getWidth(), getHeight());
	}

	@Override
	public IItemView setPositionX(float x) {
		this.setX(x);
		return this;
	}

	@Override
	public IItemView setPositionY(float y) {
		this.setY(y);
		return this;
	}

	@Override
	public IItemView recreateData() {
		createColor();
		styleTitle = new LabelStyle();
		styleTitle.font = Assets.instance.fontFactory.getFont(22,
				fontType.Medium);
		styleTitle.fontColor = wordColor;

		styleDesciption = new LabelStyle();
		styleDesciption.font = Assets.instance.fontFactory.getFont(18,
				fontType.Regular);
		styleDesciption.fontColor = desColor;

		lbTitle = new Label(title, styleTitle);
		lbTitle.setAlignment(Align.center, Align.left);
		lbTitle.setTouchable(Touchable.disabled);

		lbShortDescription = new Label(Factory.getSubString(description,
				2 * getWidth() / 3 - 10, styleDesciption.font), styleDesciption);
		lbShortDescription.setAlignment(Align.center, Align.left);
		lbShortDescription.setTouchable(Touchable.disabled);
		lbShortDescription.setWrap(true);

		bg = new Image(Assets.instance.ui.reg_ninepatch);
		bg.setColor(bgColor);

		bg.addListener(new ClickListener() {
			Vector2	touch	= new Vector2();

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				touch.set(x, y);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				if (!touch.epsilonEquals(x, y, 20))
					return;
				final float px = x, py = y;
				bg.addAction(Actions.sequence(Actions.color(new Color(
						220 / 255f, 220 / 255f, 220 / 255f, 1f), 0.1f), Actions
						.run(new Runnable() {

							@Override
							public void run() {

								if (onClickListener != null)
									onClickListener.onClick(px, py);
								bg.setColor(bgColor);
							}
						})));

			}

			@Override
			public void touchDragged(InputEvent event, float x, float y,
					int pointer) {
				if (!touch.epsilonEquals(x, y, 10))
					touch.set(0, 0);
				super.touchDragged(event, x, y, pointer);
			}
		});

		addActor(bg);
		addActor(lbTitle);
		addActor(lbShortDescription);
		valid();
		return this;
	}

	@Override
	public IItemView resetData() {
		if (!hasParent())
			return this;
		this.clearChildren();
		this.clearActions();
		this.clearListeners();
		bg = null;
		lbShortDescription = null;
		lbTitle = null;
		styleDesciption = null;
		styleTitle = null;
		return this;
	}

	@Override
	public IItemView removeFromParent() {
		remove();
		return this;
	}

	@Override
	public float getPositionX() {
		return getX();
	}

	@Override
	public float getPositionY() {
		return getY();
	}

	public boolean hasParents() {
		return super.hasParent();
	}

	@Override
	public IItemView addToParent(Group group) {
		group.addActor(this);
		return this;
	}

}

interface IItemView {

	public Vector2 getDimesion();

	public IItemView setPositionX(float x);

	public IItemView setPositionY(float y);

	public float getPositionX();

	public float getPositionY();

	public IItemView recreateData();

	public IItemView resetData();

	public IItemView removeFromParent();

	public IItemView addToParent(Group group);

	public boolean hasParents();

}
