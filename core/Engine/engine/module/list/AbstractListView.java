package engine.module.list;

import utils.factory.Factory;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Array;
import com.coder5560.game.enums.Direct;

import engine.element.GroupElement;
import engine.element.TableElement;
import engine.module.updatehandler.IUpdateHandler;

public abstract class AbstractListView extends GroupElement implements
		PanStopListener {

	public Array<TableElement> pages;
	public int currentSelected = 0;
	public PanListView pan;
	public boolean looping = false;

	public AbstractListView(float width, float height) {
		super();
		this.setSize(width, height);
		pages = new Array<TableElement>();
		pan = new PanZoom(new Rectangle(0, 0, width, height), this);
		rebuildComponent();
		buildListener();
		this.setCullingArea(new Rectangle(getX(), getY(), getWidth(),
				getHeight()));
	}

	public AbstractListView addPage(TableElement page) {
		pages.add(page);
		rebuildComponent();
		return this;
	}

	public Page newPage() {
		Page page = new Page(pages.size, getWidth(), getHeight());
		pages.add(page);
		rebuildComponent();
		return page;
	}

	public int getPageSize() {
		if (pages == null)
			throw new NullPointerException("Pages in Abstract listview is null");
		return pages.size;
	}

	public void buildListener() {
		this.addListener(new ActorGestureListener() {
			boolean isPanning = false;
			boolean isPanVertical = false;

			@Override
			public void pan(InputEvent event, float x, float y, float deltaX,
					float deltaY) {

				// == if there is no page, no need to catch Pan event ====
				if (getPageSize() <= 1)
					return;

				// ====== Detect event when user pan vertical ========
				if (!isPanVertical
						&& (Math.abs(deltaX) >= 5 || Math.abs(deltaY) >= 5)) {
					if (Math.abs(deltaY) > Math.abs(deltaX))
						isPanVertical = true;
				}
				/*
				 * When user pan vertical, just remove listener from the child
				 * has typed of scroll
				 */
				if (isPanVertical) {
					for (Actor actor : pages.get(currentSelected).getChildren()) {
						if (actor instanceof ScrollPane) {

						}
					}
					return;
				}
				// ========= Detect even pan horizontal =================
				// ========= Cancel children listener =================
				if (!isPanning && !isPanVertical && Math.abs(deltaX) >= 5) {
					isPanning = true;
					pages.get(currentSelected).setTouchable(Touchable.disabled);
					for (Actor actor : pages.get(currentSelected).getChildren()) {
						if (actor instanceof ScrollPane) {
							((ScrollPane) actor).cancel();
						}
					}
				}

				// =========== Process pan horizontal =========
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
				// == if there is no page, no need to catch Pan event ====
				if (getPageSize() <= 1)
					return;

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
				// == if there is no page, no need to process update ====
				if (getPageSize() <= 1)
					return;
				// === Process update from current, previous and next page ====
				pan.onUpdate(delta);
			}
		};

		registerUpdateHandler(update);
	}

	public void onPan(float x, float y, float deltaX, float deltaY) {
		if (pan == null)
			return;
		if (getPageSize() <= 1)
			return;
		if (getCurrentPage() != null && getCurrentPage().isTouchable())
			getCurrentPage().setTouchable(Touchable.disabled);
		pan.onPan(x, y, deltaX, deltaY);

	}

	public void onPanStop(float x, float y) {
		if (pan == null)
			return;
		if (getPageSize() == 0)
			return;
		pan.onStop(x, y);
	}

	public void onFling(float velocityX, float velocityY) {
		if (pan == null)
			return;
		if (getPageSize() <= 1)
			return;
		pan.onFling(velocityX, velocityY);
	}

	@Override
	public void onStop(Direct direct) {
		if (getPageSize() <= 1)
			return;
		if (direct == Direct.LEFT) {
			currentSelected = Factory.getNext(currentSelected, 0,
					pages.size - 1, looping);
		}
		if (direct == Direct.RIGHT) {
			currentSelected = Factory.getPrevious(currentSelected, 0,
					pages.size - 1, looping);
		}
		// add component
		rebuildComponent();
	}

	public void rebuildComponent() {
		// ====== remove all the page is not current page========
		for (Actor actor : getChildren()) {
			if (actor instanceof Page) {
				if (((TableElement) actor).getId() != pages.get(currentSelected)
						.getId()) {
					actor.remove();
				}
			}
		}
		{
			TableElement current = getCurrentPage();
			TableElement next = getNextPage(looping);
			TableElement previous = getPreviousPage(looping);

			if (current != null)
				addActor(current);
			// ========= add next page ==============
			if (next != null)
				addActor(next);
			// ========= add previous page ==============
			if (previous != null)
				addActor(previous);
			// == bring current page into the surface of it parent =========
			if (current != null) {
				current.toFront();
				current.setTouchable(Touchable.enabled);
			}
			// ========= update data for pan controller ===============
			pan.setData(current, next, previous);
		}
	}

	public TableElement getCurrentPage() {
		if (pages != null && getPageSize() > 0) {
			return pages.get(currentSelected);
		}
		return null;
	}

	public TableElement getNextPage(boolean loop) {
		// ========= only have next page when the size of pages at least 2 ===
		if (getPageSize() >= 2) {
			if (!loop) {
				if (currentSelected < getPageSize() - 1)
					return pages.get(currentSelected + 1);
				else
					return null;
			} else {
				return pages.get(Factory.getNext(currentSelected, 0,
						pages.size - 1));
			}
		}
		return null;
	}

	public TableElement getPreviousPage(boolean loop) {
		// ========= only have next page when the size of pages at least 2 ===
		if (getPageSize() >= 2) {
			if (!loop) {
				if (currentSelected > 0)
					return pages.get(currentSelected - 1);
				else
					return null;
			} else {
				return pages.get(Factory.getPrevious(currentSelected, 0,
						pages.size - 1));
			}
		}
		return null;
	}

}
