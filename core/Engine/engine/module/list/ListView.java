package engine.module.list;

import java.util.ArrayList;

import utils.factory.Factory;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.coder5560.game.assets.Assets;
import com.coder5560.game.enums.Direct;

import engine.element.GroupElement;
import engine.module.updatehandler.IUpdateHandler;

public class ListView extends GroupElement implements OnStopListener {

	private ArrayList<Page>	pages;
	private int				currentSelected	= 0;
	private ListViewPan		pan;

	public ListView(float width, float height) {
		super();
		this.setSize(width, height);
		pages = new ArrayList<Page>();
		for (int i = 0; i < 100; i++) {
			Page page = new Page(i, width, height);
			page.setBackground(new TextureRegionDrawable(
					Assets.instance.ui.reg_ninepatch4));
			page.setOrigin(Align.center);
			pages.add(page);
		}
		pan = new PanFade(new Rectangle(0, 0, width, height), this);
		// pan = new PanZoomOut(new Rectangle(0, 0, width, height), this);
		addActor(pages.get(currentSelected));
		addComponent();
		buildListener();
		this.setCullingArea(new Rectangle(getX(), getY(), getWidth(),
				getHeight()));

		Page page = pages.get(4);
		page.row();
		Table tb = new Table();
		tb.setSize(page.getWidth(), page.getHeight());
		ScrollPane scr = new ScrollPane(tb);
		scr.setScrollingDisabled(true, false);
		scr.setCancelTouchFocus(false);
		for (int i = 0; i < 10; i++) {
			final Page pg = new Page(i, width, height / 5);
			pg.setBackground(new TextureRegionDrawable(
					Assets.instance.ui.reg_ninepatch2));
			pg.setTouchable(Touchable.enabled);
			pg.setOrigin(Align.center);
			pg.setTransform(true);
			pg.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y) {
					pg.clearActions();
					pg.addAction(Actions.sequence(Actions.scaleTo(1.2f, 1.2f, .05f),
							Actions.scaleTo(1f, 1f, .1f, Interpolation.swingOut),
							Actions.run(new Runnable() {
								@Override
								public void run() {
								}
							})));
				}
			});
			tb.add(pg).pad(5).row();
		}
		page.add(scr).expand().fill();
		
		
		
		Page page2 = pages.get(6);
		page2.row();
		ScrollView scrollView = new ScrollView(page2.getWidth(), page2.getHeight());
		page2.addActor(scrollView);

	}

	public void buildListener() {
		this.addListener(new ActorGestureListener() {
			boolean	isPanning		= false;
			boolean	isPanVertical	= false;
			@Override
			public void pan(InputEvent event, float x, float y, float deltaX,
					float deltaY) {
				if (!isPanVertical
						&& (Math.abs(deltaX) >= 5 || Math.abs(deltaY) >= 5)) {
					if (Math.abs(deltaY) > Math.abs(deltaX))
						isPanVertical = true;
				}

				if (isPanVertical){
					for (Actor actor : pages.get(currentSelected).getChildren()) {
						if (!(actor instanceof ScrollPane)) {
							
						}
					}
					return;
				}
					

				if (!isPanning && !isPanVertical && Math.abs(deltaX) >= 5) {
					isPanning = true;
					pages.get(currentSelected).setTouchable(Touchable.disabled);
					for (Actor actor : pages.get(currentSelected).getChildren()) {
						if (actor instanceof ScrollPane) {
							((ScrollPane) actor).cancel();
						}
					}
				}
				if (isPanning) {
					onPan(x, y, deltaX, 0);
				}
			}

			@Override
			public void fling(InputEvent event, float velocityX,
					float velocityY, int button) {
			}

			@Override
			public void touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				if (!isPanVertical) {
					isPanning = false;
					pages.get(currentSelected).setTouchable(Touchable.enabled);
					onPanStop(x, y);
				} else {
					isPanVertical = false;
				}
			}
		});

		IUpdateHandler update = new IUpdateHandler() {

			@Override
			public void reset() {

			}

			@Override
			public void onUpdate(float delta) {
				if (pan == null)
					return;
				pan.onUpdate(delta);
			}
		};

		registerUpdateHandler(update);
	}

	public void onPan(float x, float y, float deltaX, float deltaY) {
		if (pan == null)
			return;
		pan.onPan(x, y, deltaX, deltaY);
	}

	public void onPanStop(float x, float y) {
		if (pan == null)
			return;
		pan.onStop(x, y);
	}

	public void onFling(float velocityX, float velocityY) {
		if (pan == null)
			return;
		pan.onFling(velocityX, velocityY);
	}

	@Override
	public void onStop(Direct direct) {
		if (direct == Direct.LEFT) {
			currentSelected = Factory.getNext(currentSelected, 0,
					pages.size() - 1);
		}
		if (direct == Direct.RIGHT) {
			currentSelected = Factory.getPrevious(currentSelected, 0,
					pages.size() - 1);
		}
		// add component
		addComponent();
	}

	public void addComponent() {
		for (Actor actor : getChildren()) {
			if (actor instanceof Page) {
				if (((Page) actor).getId() != pages.get(currentSelected)
						.getId()) {
					actor.remove();
				}
			}
		}
		addActor(pages
				.get(Factory.getNext(currentSelected, 0, pages.size() - 1)));
		addActor(pages.get(Factory.getPrevious(currentSelected, 0,
				pages.size() - 1)));
		pages.get(currentSelected).toFront();
		pages.get(currentSelected).setTouchable(Touchable.enabled);
		pan.setData(pages.get(currentSelected), pages.get(Factory.getNext(
				currentSelected, 0, pages.size() - 1)), pages.get(Factory
				.getPrevious(currentSelected, 0, pages.size() - 1)));
	}

}

abstract class ListViewPan {
	public Page				current, next, previous;
	public Rectangle		bound;
	public OnStopListener	onStopListener;

	public ListViewPan(Rectangle bound, OnStopListener onStopListener) {
		super();
		this.bound = bound;
		this.onStopListener = onStopListener;
	}

	public void setData(Page current, Page next, Page previous) {
		this.current = current;
		this.next = next;
		this.previous = previous;
	}

	public abstract void onUpdate(float deltaTime);

	public abstract void onPan(float x, float y, float deltaX, float deltaY);

	public abstract void onStop(float x, float y);

	public abstract void onFling(float velocityX, float velocityY);

}

class PanNormal extends ListViewPan {

	public PanNormal(Rectangle bound, OnStopListener onStopListener) {
		super(bound, onStopListener);
	}

	@Override
	public void onUpdate(float delta) {
		onUpdateOther(delta);
	}

	private void onUpdateOther(float delta) {
		next.setPosition(current.getX() + current.getWidth(), current.getY());
		previous.setPosition(current.getX() - previous.getWidth(),
				current.getY());
		next.setTouchable(Touchable.disabled);
		previous.setTouchable(Touchable.disabled);
	}

	@Override
	public void onPan(float x, float y, float deltaX, float deltaY) {
		current.setPosition(current.getX() + deltaX, current.getY() + deltaY);
	}

	@Override
	public void onStop(float x, float y) {
		current.clearActions();
		if (current.getX(Align.center) > bound.x + 2 * bound.width / 3) {
			current.addAction(Actions.sequence(
					Actions.moveTo(bound.x + bound.width, bound.y, .23f),
					Actions.run(new Runnable() {
						@Override
						public void run() {
							onStopListener.onStop(Direct.RIGHT);
						}
					})));
		} else if (current.getX(Align.center) < bound.x + bound.width / 3) {
			current.addAction(Actions.sequence(
					Actions.moveTo(bound.x - bound.width, bound.y, .25f),
					Actions.run(new Runnable() {
						@Override
						public void run() {
							onStopListener.onStop(Direct.LEFT);
						}
					})));
		} else {
			current.addAction(Actions.sequence(Actions.moveTo(bound.x, bound.y,
					.3f)));
		}
	}

	@Override
	public void onFling(float velocityX, float velocityY) {

	}
}

class PanFade extends ListViewPan {
	public PanFade(Rectangle bound, OnStopListener onStopListener) {
		super(bound, onStopListener);
	}

	@Override
	public void onUpdate(float delta) {
		onUpdateOther(delta);
	}

	private void onUpdateOther(float delta) {
		next.setPosition(bound.x, bound.y);
		previous.setPosition(bound.x, bound.y);
		current.setVisible(true);
		float percent = Math.abs(current.getX(Align.center)
				- (bound.getX() + bound.getWidth() / 2))
				/ (bound.width);
		percent = MathUtils.clamp(percent, 0, 1);
		current.getColor().a = 1 - percent;

		if (current.getX(Align.center) > bound.x + bound.width / 2) {
			next.setVisible(false);
			next.getColor().a = 1f;
			previous.setVisible(true);
			previous.getColor().a = percent;
			previous.setZIndex(current.getZIndex() - 1);
		} else if (current.getX(Align.center) < bound.x + bound.width / 2) {
			next.getColor().a = percent;
			next.setVisible(true);
			previous.setVisible(false);
			previous.getColor().a = 1f;
			next.setZIndex(current.getZIndex() - 1);
		}

		next.setTouchable(Touchable.disabled);
		previous.setTouchable(Touchable.disabled);
	}

	@Override
	public void onPan(float x, float y, float deltaX, float deltaY) {
		current.setPosition(current.getX() + deltaX, current.getY() + deltaY);
	}

	@Override
	public void onStop(float x, float y) {
		current.clearActions();
		if (current.getX(Align.center) > bound.x + 2 * bound.width / 3) {
			current.addAction(Actions.sequence(
					Actions.moveTo(bound.x + bound.width, bound.y, .25f),
					Actions.run(new Runnable() {
						@Override
						public void run() {
							onStopListener.onStop(Direct.RIGHT);
						}
					})));
		} else if (current.getX(Align.center) < bound.x + bound.width / 3) {
			current.addAction(Actions.sequence(
					Actions.moveTo(bound.x - bound.width, bound.y, .25f),
					Actions.run(new Runnable() {
						@Override
						public void run() {
							onStopListener.onStop(Direct.LEFT);
						}
					})));
		} else {
			current.addAction(Actions.sequence(Actions.moveTo(bound.x, bound.y,
					.3f)));
		}
	}

	@Override
	public void onFling(float velocityX, float velocityY) {

	}
}

class PanZoomOut extends ListViewPan {
	float	maxScale	= 1.2f;

	public PanZoomOut(Rectangle bound, OnStopListener onStopListener) {
		super(bound, onStopListener);
	}

	@Override
	public void onUpdate(float delta) {
		onUpdateOther(delta);
	}

	private void onUpdateOther(float delta) {
		next.setPosition(current.getX() + current.getWidth(), current.getY());
		previous.setPosition(current.getX() - previous.getWidth(),
				current.getY());

		float percent = Math.abs(current.getX(Align.center)
				- (bound.x + bound.width / 2))
				/ (bound.width / 2);
		percent = MathUtils.clamp(percent, 0, 1f);

		next.setScale(1 - (1 - percent) * (maxScale - 1));
		previous.setScale(1 - (1 - percent) * (maxScale - 1));
		current.setScale(1 - percent * (maxScale - 1));

		next.setTouchable(Touchable.disabled);
		previous.setTouchable(Touchable.disabled);
	}

	@Override
	public void onPan(float x, float y, float deltaX, float deltaY) {
		current.setPosition(current.getX() + deltaX, current.getY() + deltaY);
	}

	@Override
	public void onStop(float x, float y) {
		current.clearActions();
		if (current.getX(Align.center) > bound.x + 2 * bound.width / 3) {
			current.addAction(Actions.sequence(Actions.moveTo(bound.x
					+ bound.width, bound.y, .25f, Interpolation.linear),
					Actions.run(new Runnable() {
						@Override
						public void run() {
							onStopListener.onStop(Direct.RIGHT);
						}
					})));
		} else if (current.getX(Align.center) < bound.x + bound.width / 3) {
			current.addAction(Actions.sequence(Actions.moveTo(bound.x
					- bound.width, bound.y, .25f, Interpolation.linear),
					Actions.run(new Runnable() {
						@Override
						public void run() {
							onStopListener.onStop(Direct.LEFT);
						}
					})));
		} else {
			current.addAction(Actions.sequence(Actions.moveTo(bound.x, bound.y,
					.3f, Interpolation.linear)));
		}
	}

	@Override
	public void onFling(float velocityX, float velocityY) {

	}
}

interface OnStopListener {
	public void onStop(Direct direct);
}