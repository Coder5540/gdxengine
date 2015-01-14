package engine.module.list;

import java.util.ArrayList;

import utils.factory.Factory;

import com.badlogic.gdx.math.Interpolation;
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

public class ListView extends GroupElement implements PanStopListener {

	public ArrayList<Page> pages;
	private int currentSelected = 0;
	private PanListView pan;

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
			pg.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					pg.clearActions();
					pg.addAction(Actions.sequence(Actions.scaleTo(1.2f, 1.2f,
							.05f), Actions.scaleTo(1f, 1f, .1f,
							Interpolation.swingOut), Actions
							.run(new Runnable() {
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
		ScrollView scrollView = new ScrollView(page2.getWidth(),
				page2.getHeight());
		page2.addActor(scrollView);

	}

	public void buildListener() {
		this.addListener(new ActorGestureListener() {
			boolean isPanning = false;
			boolean isPanVertical = false;
			@Override
			public void pan(InputEvent event, float x, float y, float deltaX,
					float deltaY) {
				if (!isPanVertical
						&& (Math.abs(deltaX) >= 5 || Math.abs(deltaY) >= 5)) {
					if (Math.abs(deltaY) > Math.abs(deltaX))
						isPanVertical = true;
				}

				if (isPanVertical) {
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

