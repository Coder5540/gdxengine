package com.coder5560.game.ui;

import java.util.ArrayList;

import utils.factory.FontFactory.fontType;
import utils.listener.OnClickListener;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.coder5560.game.assets.Assets;
import com.coder5560.game.views.IViewController;

public class ListMenu extends ScrollPane {

	Table					table;
	private IViewController	controller;
	ArrayList<ItemMenu>		listItem	= new ArrayList<ListMenu.ItemMenu>();
	LabelStyle				lbStyle;

	public ListMenu(IViewController controllerView, final Table table,
			Rectangle bound) {
		super(table);
		this.controller = controllerView;
		this.table = table;
		setBounds(bound.x, bound.y, bound.width, bound.height);
		table.top();
		lbStyle = new LabelStyle(Assets.instance.fontFactory.getFont(20,
				fontType.Bold), Color.WHITE);
		Group user = new Group();
		createAvatar(user);
		table.add(user).row();
		addLine(table, 2);

		ItemMenu itemEnglishBackground = new ItemMenu(
				Assets.instance.ui.getRegUsermanagement(),
				"ENGLISH BACKGROUND", getWidth(), 50);
		createItemEnglishBackground(itemEnglishBackground);
		addItem(itemEnglishBackground, 0);

		

		addLine(table, 2);
		ItemMenu itemGameCenter = new ItemMenu(new IconMail(45, 45),
				"GAME CENTER", getWidth(), 50);
		createItemGameCenter(itemGameCenter);
		addItem(itemGameCenter, 1);
		
		addLine(table, 2);
		ItemMenu itemEnglishSkill = new ItemMenu(new IconMail(45, 45),
				"ENGLISH SKILLS", getWidth(), 50);
		createItemEnglishSkills(itemEnglishSkill);
		addItem(itemEnglishSkill, 2);
	}

	private void createAvatar(final Group user) {
		user.setSize(getWidth(), 200);
		final Image bgFocus = new Image(new NinePatch(
				Assets.instance.ui.reg_ninepatch, Color.GRAY));
		bgFocus.setSize(user.getWidth(), user.getHeight());
		bgFocus.getColor().a = 0;
		Image avatar = new Image(Assets.instance.ui.getAvatar());
		avatar.setSize(120, 120);
		avatar.setPosition(20, user.getHeight() / 2 - avatar.getHeight() / 2);
		Label lbName = new Label("User", new LabelStyle(
				Assets.instance.fontFactory.getFont(20, fontType.Light),
				Color.WHITE));
		lbName.setPosition(avatar.getX() + avatar.getWidth() + 5,
				user.getHeight() / 2 - lbName.getHeight() / 2);
		user.addActor(bgFocus);
		user.addActor(avatar);
		user.addActor(lbName);
		user.setOrigin(Align.center);
		user.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				user.addAction(Actions.sequence(
						Actions.scaleTo(1.2f, 1.2f, .1f),
						Actions.scaleTo(1f, 1f, .2f, Interpolation.swingOut),
						Actions.run(new Runnable() {
							@Override
							public void run() {
								if (onAvatarClicked != null) {
									onAvatarClicked.onClick(0, 0);
									user.addAction(Actions.sequence(
											Actions.touchable(Touchable.disabled),
											Actions.delay(
													.1f,
													Actions.touchable(Touchable.enabled))));
								}
							}
						})));
			}
		});
	}

	private void createItemEnglishBackground(ItemMenu itemManager) {

		LabelButton userActive = new LabelButton("PageView Test", lbStyle,
				getWidth(), 45);
		userActive.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(float x, float y) {
				if (onPageViewClicked != null)
					onPageViewClicked.onClick(x, y);
			}
		});
		LabelButton userUnactive = new LabelButton("Board Game Test", lbStyle,
				getWidth(), 45);
		userUnactive.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(float x, float y) {
				if (onBoardGameClicked != null)
					onBoardGameClicked.onClick(x, y);
			}
		});
		LabelButton userBlock = new LabelButton("List View Test", lbStyle,
				getWidth(), 45);
		userBlock.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(float x, float y) {
				if (onListViewTestClicked != null)
					onListViewTestClicked.onClick(x, y);
			}
		});
		itemManager.addComponent(userActive);
		itemManager.addComponent(userUnactive);
		itemManager.addComponent(userBlock);

	}

	private void createItemEnglishSkills(ItemMenu itemSkills) {
		LabelButton speaking = new LabelButton("Speaking", lbStyle, getWidth(),
				45);
		speaking.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(float x, float y) {
			}
		});

		LabelButton listening = new LabelButton("Listening", lbStyle,
				getWidth(), 45);
		listening.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(float x, float y) {

			}
		});

		LabelButton reading = new LabelButton("Reading", lbStyle, getWidth(),
				45);
		reading.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(float x, float y) {

			}
		});

		LabelButton writing = new LabelButton("Writing", lbStyle, getWidth(),
				45);
		writing.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(float x, float y) {

			}
		});

		itemSkills.addComponent(speaking);
		itemSkills.addComponent(listening);
		itemSkills.addComponent(reading);
		itemSkills.addComponent(writing);
	}

	private void createItemGameCenter(ItemMenu itemGameCenter) {
		LabelButton crossWordGame = new LabelButton("CrossWord Game", lbStyle,
				getWidth(), 45);
		crossWordGame.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(float x, float y) {
				if (onCrossWordClicked != null) {
					onCrossWordClicked.onClick(x, y);
				}
			}
		});

		LabelButton whichWord = new LabelButton("Spelling Rush", lbStyle,
				getWidth(), 45);
		whichWord.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(float x, float y) {
				if (onSpellingClicked != null) {
					onSpellingClicked.onClick(x, y);
				}
			}
		});

		LabelButton bubbleWord = new LabelButton("Bubble Words", lbStyle,
				getWidth(), 45);
		bubbleWord.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(float x, float y) {

			}
		});

		LabelButton dictionary = new LabelButton("Dictionary", lbStyle,
				getWidth(), 45);
		dictionary.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(float x, float y) {
				if (onDictionaryClicked != null)
					onDictionaryClicked.onClick(x, y);
			}
		});

		itemGameCenter.addComponent(dictionary);
		itemGameCenter.addComponent(crossWordGame);
		itemGameCenter.addComponent(whichWord);
		itemGameCenter.addComponent(bubbleWord);
		
	}

	void addLine(Table table, float height) {
		Image line = new Image(Assets.instance.ui.reg_ninepatch);
		line.setColor(new Color(0, 220 / 255f, 0, 1f));
		line.setHeight(height);
		line.setWidth(table.getWidth());
		table.add(line).expandX().fillX().height(height).padTop(10);
		table.row();
	}

	void addItem(ItemMenu item, int permisionItem) {
		if (true) {
			table.add(item).top().row();
			listItem.add(item);
		}
	}

	void setHeightForCell(Actor a, float height) {
		table.getCell(a).height(height);
		table.invalidate();
		layout();
	}

	class ItemMenu extends Group {
		Group					item;
		Image					bg;
		Image					bgFocus;
		Image					btnExpand;
		ArrayList<LabelButton>	subButton;
		boolean					isValidate;
		boolean					isExpand;
		float					maxheight;
		float					minheight;
		float					currentheight;
		Vector2					touchPoint;

		// Image bg_content;

		public ItemMenu(TextureRegion region, String title, float width,
				float height) {
			touchPoint = new Vector2();
			subButton = new ArrayList<LabelButton>();
			setSize(width, height);
			item = new Group();
			item.setSize(width, height);
			bg = new Image(new NinePatch(Assets.instance.ui.reg_ninepatch, 1,
					1, 1, 1));
			bg.setColor(new Color(250 / 255f, 250 / 255f, 250 / 255f, 1f));
			bg.setSize(item.getWidth(), item.getHeight());

			bgFocus = new Image(new NinePatch(Assets.instance.ui.reg_ninepatch));
			bgFocus.setColor(new Color(220 / 255f, 220 / 255f, 220 / 255f, 0));
			bgFocus.setSize(item.getWidth(), item.getHeight());

			Label lbtitle = new Label(title, new LabelStyle(
					Assets.instance.fontFactory.getFont(15, fontType.Medium),
					new Color(0, 191 / 255f, 1, 1)));
			btnExpand = new Image(Assets.instance.getRegion("down"));
			btnExpand.setSize(15, 10);
			btnExpand
					.setColor(new Color(130 / 255f, 130 / 255f, 130 / 255f, 1));
			btnExpand.setOrigin(btnExpand.getWidth() / 2,
					btnExpand.getHeight() / 2);

			Image icon = new Image(region);
			icon.setSize(45, 45);

			icon.setPosition(7,
					bg.getY() + bg.getHeight() / 2 - icon.getHeight() / 2);
			lbtitle.setPosition(icon.getX() + icon.getWidth() + 5, bg.getY()
					+ bg.getHeight() / 2 - lbtitle.getHeight() / 2);
			btnExpand.setPosition(
					item.getX() + item.getWidth() - btnExpand.getWidth() - 10,
					bg.getY() + bg.getHeight() / 2 - btnExpand.getHeight() / 2);
			item.addActor(bg);
			// item.addActor(bg_content);
			item.addActor(bgFocus);
			item.addActor(icon);
			item.addActor(lbtitle);
			item.addActor(btnExpand);

			maxheight = height;
			minheight = height;
			currentheight = height;

			item.addListener(new ClickListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					touchPoint.set(x, y);
					bgFocus.getColor().a = 1;
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					bgFocus.getColor().a = 0;
					if (touchPoint.epsilonEquals(x, y, 20)) {
						if (!isExpand) {
							setHeight(maxheight);
							showSubButton();
							setHeightForCell(ItemMenu.this, maxheight);
							btnExpand.addAction(Actions.rotateTo(180, 0.2f,
									Interpolation.exp10));
							isExpand = true;
						} else {
							setHeight(minheight);
							hideSubButton();
							isExpand = false;
						}
					}
					super.touchUp(event, x, y, pointer, button);
				}

				@Override
				public void touchDragged(InputEvent event, float x, float y,
						int pointer) {
					if (!touchPoint.epsilonEquals(x, y, 40)) {
						bgFocus.getColor().a = 0f;
						touchPoint.set(0, 0);
					}
					super.touchDragged(event, x, y, pointer);
				}
			});
		}

		public ItemMenu(IconMail iconMail, String title, float width, int height) {

			touchPoint = new Vector2();
			subButton = new ArrayList<LabelButton>();
			setSize(width, height);
			item = new Group();
			item.setSize(width, height);
			bg = new Image(new NinePatch(Assets.instance.ui.reg_ninepatch, 1,
					1, 1, 1));
			bg.setColor(new Color(250 / 255f, 250 / 255f, 250 / 255f, 1f));
			bg.setSize(item.getWidth(), item.getHeight());

			bgFocus = new Image(new NinePatch(Assets.instance.ui.reg_ninepatch));
			bgFocus.setColor(new Color(220 / 255f, 220 / 255f, 220 / 255f, 0));
			bgFocus.setSize(item.getWidth(), item.getHeight());

			Label lbtitle = new Label(title, new LabelStyle(
					Assets.instance.fontFactory.getFont(15, fontType.Medium),
					new Color(0, 191 / 255f, 1, 1)));
			btnExpand = new Image(Assets.instance.getRegion("down"));
			btnExpand.setSize(15, 10);
			btnExpand
					.setColor(new Color(130 / 255f, 130 / 255f, 130 / 255f, 1));
			btnExpand.setOrigin(btnExpand.getWidth() / 2,
					btnExpand.getHeight() / 2);

			iconMail.setPosition(7,
					bg.getY() + bg.getHeight() / 2 - iconMail.getHeight() / 2);
			lbtitle.setPosition(iconMail.getX() + iconMail.getWidth() + 5,
					bg.getY() + bg.getHeight() / 2 - lbtitle.getHeight() / 2);
			btnExpand.setPosition(
					item.getX() + item.getWidth() - btnExpand.getWidth() - 10,
					bg.getY() + bg.getHeight() / 2 - btnExpand.getHeight() / 2);
			item.addActor(bg);
			// item.addActor(bg_content);
			item.addActor(bgFocus);
			item.addActor(iconMail);
			item.addActor(lbtitle);
			item.addActor(btnExpand);

			maxheight = height;
			minheight = height;
			currentheight = height;

			item.addListener(new ClickListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					touchPoint.set(x, y);
					bgFocus.getColor().a = 1;
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					bgFocus.getColor().a = 0;
					if (touchPoint.epsilonEquals(x, y, 20)) {
						if (!isExpand) {
							setHeight(maxheight);
							showSubButton();
							setHeightForCell(ItemMenu.this, maxheight);
							btnExpand.addAction(Actions.rotateTo(180, 0.2f,
									Interpolation.exp10));
							isExpand = true;
						} else {
							setHeight(minheight);
							hideSubButton();
							isExpand = false;
						}
					}
					super.touchUp(event, x, y, pointer, button);
				}

				@Override
				public void touchDragged(InputEvent event, float x, float y,
						int pointer) {
					if (!touchPoint.epsilonEquals(x, y, 40)) {
						bgFocus.getColor().a = 0f;
						touchPoint.set(0, 0);
					}
					super.touchDragged(event, x, y, pointer);
				}
			});

		}

		public void addComponent(LabelButton subButton) {
			subButton.setVisible(false);
			subButton.setPosition(item.getX(), item.getY());
			this.subButton.add(subButton);
			isValidate = false;
			this.subButton.get(this.subButton.size() - 1).setTouchable(
					Touchable.disabled);
			maxheight += subButton.getHeight();
		}

		public void validate() {
			item.setPosition(getX(), getY());
			for (int i = subButton.size() - 1; i >= 0; i--) {
				addActor(subButton.get(i));
				subButton.get(i).setPosition(getX(), getY());
			}
			addActor(item);
		}

		@Override
		public void act(float delta) {
			if (!isValidate) {
				clear();
				validate();
				isValidate = true;
			}
			super.act(delta);
		}

		public void showSubButton() {
			item.setY(getHeight() - item.getHeight());
			float x = item.getX();
			float y = item.getY();
			item.clearActions();
			for (int i = 0; i < subButton.size(); i++) {
				subButton.get(i).clearActions();
				subButton.get(i).setVisible(true);
				subButton.get(i).setPosition(x, y);
				subButton.get(i).addAction(
						Actions.moveTo(x, y - subButton.get(i).getHeight(),
								0.3f, Interpolation.exp5Out));
				y -= subButton.get(i).getHeight();
				subButton.get(i).setTouchable(Touchable.enabled);
			}
		}

		public void hideSubButton() {
			for (int i = 0; i < subButton.size(); i++) {
				subButton.get(i).addAction(
						Actions.sequence(Actions.moveTo(item.getX(),
								item.getY(), 0.25f, Interpolation.exp10In),
								Actions.visible(false)));
				subButton.get(i).setTouchable(Touchable.disabled);
			}

			item.addAction(Actions.sequence(Actions.delay(0.25f),
					Actions.run(new Runnable() {
						@Override
						public void run() {
							item.setY(getHeight() - item.getHeight());
							setHeightForCell(ItemMenu.this, minheight);
							for (int i = 0; i < subButton.size(); i++) {
								subButton.get(i).setPosition(item.getX(),
										item.getY());
							}
							btnExpand.addAction(Actions.rotateTo(0, 0.2f,
									Interpolation.exp10));
						}
					})));
		}
	}

	public class LabelButton extends Group {

		public static final int	LEFT	= 0;
		public static final int	CENTER	= 1;

		private Image			bg;
		public Image			bgFocus;
		Label					title;
		Vector2					touchPoint;
		int						align;
		private OnClickListener	onClickListener;

		public LabelButton(String title, LabelStyle style, float width,
				float height, int align) {
			this.align = align;
			init(title, style, width, height);
		}

		public LabelButton(String title, LabelStyle style, float width,
				float height) {
			this.align = LEFT;
			init(title, style, width, height);

			addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					touchPoint.set(x, y);
					bgFocus.getColor().a = 1;
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					bgFocus.getColor().a = 0;
					if (touchPoint.epsilonEquals(x, y, 20)) {
						if (onClickListener != null)
							onClickListener.onClick(x, y);
						for (ItemMenu item : listItem) {
							for (LabelButton label : item.subButton) {
								label.bgFocus.getColor().a = 0;
							}
						}
					}
					super.touchUp(event, x, y, pointer, button);
				}

				@Override
				public void touchDragged(InputEvent event, float x, float y,
						int pointer) {
					if (!touchPoint.epsilonEquals(x, y, 40)) {
						bgFocus.getColor().a = 0;
						touchPoint.set(0, 0);
					}
					super.touchDragged(event, x, y, pointer);
				}
			});
		}

		public void init(String title, LabelStyle style, float width,
				float height) {
			touchPoint = new Vector2();
			setSize(width, height);
			bg = new Image(new NinePatch(new NinePatch(
					Assets.instance.ui.reg_ninepatch, 4, 4, 4, 4), new Color(
					100 / 255f, 100 / 255f, 100 / 255f, 1)));
			bg.setSize(width, height);
			bgFocus = new Image(new NinePatch(new NinePatch(
					Assets.instance.ui.reg_ninepatch, 4, 4, 4, 4), new Color(
					220 / 255f, 220 / 255f, 220 / 255f, 1)));
			bgFocus.getColor().a = 0;
			bgFocus.setSize(width - 20, height);

			this.title = new Label(title, style);
			if (align == LEFT) {
				this.title.setPosition(30, bg.getY() + bg.getHeight() / 2
						- this.title.getHeight() / 2);
			} else {
				this.title.setPosition(getWidth() / 2 - this.title.getWidth()
						/ 2,
						bg.getY() + bg.getHeight() / 2 - this.title.getHeight()
								/ 2);
			}
			addActor(bg);
			bgFocus.setX(10);
			addActor(bgFocus);
			addActor(this.title);
		}

		public void setTitle(String title) {
			this.title.setText(title);
			if (align == LEFT) {
				this.title.setPosition(30, bg.getY() + bg.getHeight() / 2
						- this.title.getHeight() / 2);
			} else {
				this.title.setPosition(
						getWidth() / 2 - this.title.getTextBounds().width / 2,
						getHeight() / 2 - this.title.getHeight() / 2);
			}
		}

		public OnClickListener getOnClickListener() {
			return onClickListener;
		}

		public void setOnClickListener(OnClickListener onClickListener) {
			this.onClickListener = onClickListener;
		}
	}

	class IconMail extends Group {
		private Image	icon;
		private Image	bgNotify;
		private Label	notify;
		LabelStyle		lbNotify;
		private int		unRead	= 0;

		public IconMail(float width, float height) {
			super();
			this.setSize(width, height);
			create();
			addActor(icon);
			addActor(bgNotify);
			addActor(notify);
			setNotify(4);
		}

		void create() {
			lbNotify = new LabelStyle();
			lbNotify.font = Assets.instance.fontFactory.getFont(15,
					fontType.Bold);
			notify = new Label("" + unRead, lbNotify);
			notify.setVisible(true);
			icon = new Image(Assets.instance.ui.getRegionMail());
			icon.setOrigin(Align.center);
			icon.setColor(new Color(0, 220 / 255f, 0f, 1f));
			bgNotify = new Image(Assets.instance.ui.getCircle());
			bgNotify.setOrigin(Align.center);
			bgNotify.setColor(new Color(255 / 255f, 0f, 0f, 1f));
			notify.setAlignment(Align.center);
			valid();
		}

		public void valid() {
			float w = getWidth();
			float h = getHeight();
			icon.setSize(w, h);
			bgNotify.setSize(w / 2, h / 2);
			bgNotify.setPosition(w - bgNotify.getWidth(),
					h - bgNotify.getHeight());
			notify.setPosition(bgNotify.getX() + bgNotify.getWidth() / 2,
					bgNotify.getY() + bgNotify.getHeight() / 2, Align.center);
		}

		public void setNotify(int number) {
			notify.setText("" + number);
			this.unRead = number;
			if (number == 0) {
				notify.setVisible(false);
				bgNotify.setVisible(false);
			}
			valid();
		}

	}

	public IViewController getController() {
		return controller;
	}

	public void setOnListViewTestClicked(OnClickListener onListViewTestClicked) {
		this.onListViewTestClicked = onListViewTestClicked;
	}

	public void setOnBoardGameClicked(OnClickListener onBoardGameClicked) {
		this.onBoardGameClicked = onBoardGameClicked;
	}

	public void setOnPageViewClicked(OnClickListener onPageViewClicked) {
		this.onPageViewClicked = onPageViewClicked;
	}

	public void setController(IViewController controller) {
		this.controller = controller;
	}

	public void setOnAvatarClicked(OnClickListener onAvatarClicked) {
		this.onAvatarClicked = onAvatarClicked;
	}

	public void setOnSpellingClicked(OnClickListener onSpellingClicked) {
		this.onSpellingClicked = onSpellingClicked;
	}

	public void setOnCrossWordClicked(OnClickListener onCrossWordClicked) {
		this.onCrossWordClicked = onCrossWordClicked;
	}

	public void setOnDictionaryClicked(OnClickListener onDictionaryClicked) {
		this.onDictionaryClicked = onDictionaryClicked;
	}




	private OnClickListener	onListViewTestClicked;
	private OnClickListener	onBoardGameClicked;
	private OnClickListener	onPageViewClicked;
	private OnClickListener	onAvatarClicked;
	private OnClickListener	onSpellingClicked;
	private OnClickListener	onCrossWordClicked;
	private OnClickListener	onDictionaryClicked;
}
