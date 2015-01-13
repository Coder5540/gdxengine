package imp.view;

import utils.elements.Img;
import utils.factory.StringSystem;
import utils.listener.IInputListener;
import utils.listener.OnClickListener;
import utils.listener.OnComplete;
import utils.listener.OnCompleteListener;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.coder5560.game.assets.Assets;
import com.coder5560.game.enums.Constants;
import com.coder5560.game.enums.ViewState;
import com.coder5560.game.ui.ListMenu;
import com.coder5560.game.ui.Loading;
import com.coder5560.game.views.TraceView;
import com.coder5560.game.views.View;

public class MainMenu extends View {
	private Image	tranBg;
	Table			content;
	public int		lastSelect			= 0;
	public ListMenu	menu;
	private boolean	ignoreUpdateMove	= true;

	public MainMenu buildComponent() {
		Color colorBg = new Color(100 / 255f, 100 / 255f, 100 / 255f, 1f);
		getViewController().getScreen().setGestureDetector(customListener);
		tranBg = new Image(new NinePatch(Assets.instance.ui.reg_ninepatch,
				new Color(00, 00, 00, .4f)));
		tranBg.setSize(Constants.WIDTH_SCREEN, Constants.HEIGHT_SCREEN
				- Constants.HEIGHT_ACTIONBAR);
		tranBg.setVisible(true);
		tranBg.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				hide(null);
			}
		});

		content = new Table();
		content.setSize(Constants.WIDTH_MAINMENU, Constants.HEIGHT_SCREEN);

		content.setPosition(-content.getWidth(), 0);
		content.setBackground(new NinePatchDrawable(new NinePatch(
				Assets.instance.ui.reg_ninepatch, colorBg)));
		addActor(tranBg);
		addActor(content);
		buildContent(content);
		return this;

	}

	void buildContent(Table content) {
		menu = new ListMenu(getViewController(), new Table(), new Rectangle(0,
				0, content.getWidth(), content.getHeight()));
		content.addActor(menu);

		menu.setOnPageViewClicked(onPageViewClicked);
		menu.setOnListViewTestClicked(onListViewTestClicked);
		menu.setOnBoardGameClicked(onBoardGameClicked);
		menu.setOnAvatarClicked(onAvatarClicked);
		menu.setOnSpellingClicked(onSpellingClicked);
		menu.setOnCrossWordClicked(onCrossWordGame);
		menu.setOnDictionaryClicked(onDictionaryClicked);
	}

	Img getLine(Color color, float height) {
		Img img = new Img(Assets.instance.ui.reg_ninepatch);
		img.setColor(color);
		return img;
	}

	@Override
	public void show(final OnCompleteListener listener) {
		super.show(listener);
		setIgnoreUpdateMove(false);
		tranBg.setTouchable(Touchable.disabled);
		content.addAction(Actions.sequence(
				Actions.moveTo(0, 0, 0.5f, Interpolation.pow5Out),
				Actions.run(new Runnable() {
					@Override
					public void run() {
						if (listener != null)
							listener.onComplete(null);
						setIgnoreUpdateMove(true);
						tranBg.setTouchable(Touchable.enabled);
						setViewState(ViewState.SHOW);
					}
				})));
	}

	@Override
	public void hide(final OnCompleteListener listener) {
		super.hide(listener);
		setIgnoreUpdateMove(false);
		content.addAction(Actions.sequence(Actions.moveTo(-content.getWidth(),
				0, 0.5f, Interpolation.pow5Out), Actions.run(new Runnable() {
			@Override
			public void run() {
				if (listener != null)
					listener.onComplete(null);
				tranBg.setTouchable(Touchable.disabled);
				setIgnoreUpdateMove(true);
				setViewState(ViewState.HIDE);

			}
		})));
	}

	Actor	bar, currentView;

	@Override
	public void act(float delta) {
		super.act(delta);
		if (ignoreUpdateMove)
			return;
		if (tranBg.isVisible()) {
			float alpha = (content.getWidth() + content.getX())
					/ content.getWidth();
			if (content.getX() == 0)
				alpha = 1;
			tranBg.setColor(tranBg.getColor().r, tranBg.getColor().g,
					tranBg.getColor().b, alpha);
		}
		if (getViewController() != null) {
			if (bar == null)
				bar = (Actor) (getViewController()
						.getView(StringSystem.VIEW_ACTION_BAR));
			if (currentView == null)
				currentView = (Actor) (getViewController()
						.getView(TraceView.instance.getLastView()));

			if (bar != null)
				bar.setPosition(content.getX() + content.getWidth(), bar.getY());
			if (currentView != null)
				currentView.setPosition(content.getX() + content.getWidth(),
						currentView.getY());
		}
	}

	@Override
	public void destroyComponent() {
	}

	@Override
	public void back() {
		hide(null);
	}

	boolean					canPan					= false;
	IInputListener			customListener			= new IInputListener() {
														public boolean touchDown(
																float x,
																float y,
																int pointer,
																int button) {
															if (content.getX() == -content
																	.getWidth()
																	&& x < 10
																	&& !canPan) {
																canPan = true;
																tranBg.setVisible(true);
															}
															if (content.getX() == 0) {
																canPan = true;
															}
															return false;
														};

														public boolean pan(
																float x,
																float y,
																float deltaX,
																float deltaY) {
															if (canPan) {
																setIgnoreUpdateMove(false);
																tranBg.setVisible(true);
																content.setPosition(
																		MathUtils
																				.clamp(content
																						.getX()
																						+ deltaX,
																						-content.getWidth(),
																						0),
																		content.getY());
																float alpha = (content
																		.getWidth() - content
																		.getX())
																		/ content
																				.getWidth();
																if (content
																		.getX() == 0)
																	alpha = 1;
																tranBg.setColor(
																		tranBg.getColor().r,
																		tranBg.getColor().g,
																		tranBg.getColor().b,
																		alpha);
																return true;
															}
															return false;
														};

														public boolean panStop(
																float x,
																float y,
																int pointer,
																int button) {
															if (canPan) {
																float position = content
																		.getX()
																		+ content
																				.getWidth();
																if (position <= content
																		.getWidth() / 2)
																	hide(null);
																if (position > content
																		.getWidth() / 2)
																	show(null);
																canPan = false;
																return true;
															}
															return false;
														};

													};

	OnClickListener			onPageViewClicked		= new OnClickListener() {

														@Override
														public void onClick(
																float x, float y) {
															Loading.ins
																	.show((Group) getViewController()
																			.getCurrentView());
															getViewController()
																	.getView(
																			StringSystem.VIEW_MAIN_MENU)
																	.hide(new OnComplete() {

																		@Override
																		public void onComplete(
																				Object object) {
																			Loading.ins
																					.hide();
																			PageView pageView = new PageView();
																			pageView.build(
																					getStage(),
																					getViewController(),
																					"pageview",
																					new Rectangle(
																							0,
																							0,
																							Constants.WIDTH_SCREEN,
																							Constants.HEIGHT_SCREEN
																									- Constants.HEIGHT_ACTIONBAR));
																			pageView.buildComponent();
																			pageView.show(null);

																		}
																	});
														}
													};

	OnClickListener			onBoardGameClicked		= new OnClickListener() {

														@Override
														public void onClick(
																float x, float y) {
															Loading.ins
																	.show((Group) getViewController()
																			.getCurrentView());
															getViewController()
																	.getView(
																			StringSystem.VIEW_MAIN_MENU)
																	.hide(new OnComplete() {
																		@Override
																		public void onComplete(
																				Object object) {
																			Loading.ins
																					.hide();
																		}
																	});

														}
													};

	OnClickListener			onListViewTestClicked	= new OnClickListener() {

														@Override
														public void onClick(
																float x, float y) {
															Loading.ins
																	.show((Group) getViewController()
																			.getCurrentView());
															getViewController()
																	.getView(
																			StringSystem.VIEW_MAIN_MENU)
																	.hide(new OnComplete() {

																		@Override
																		public void onComplete(
																				Object object) {
																			Loading.ins
																					.hide();
																			ListViewTest listViewTest = new ListViewTest();
																			listViewTest
																					.build(getStage(),
																							getViewController(),
																							"listviewtest",
																							new Rectangle(
																									0,
																									0,
																									Constants.WIDTH_SCREEN,
																									Constants.HEIGHT_SCREEN));
																			listViewTest
																					.buildComponent();
																			listViewTest
																					.show(null);

																		}
																	});
														}
													};

	OnClickListener			onAvatarClicked			= new OnClickListener() {

														@Override
														public void onClick(
																float x, float y) {
															getViewController()
																	.getView(
																			StringSystem.VIEW_MAIN_MENU)
																	.hide(new OnComplete() {

																		@Override
																		public void onComplete(
																				Object object) {
																			
																		}
																	});
														}
													};

	OnClickListener			onSpellingClicked		= new OnClickListener() {

														@Override
														public void onClick(
																float x, float y) {
															getViewController()
																	.getView(
																			StringSystem.VIEW_MAIN_MENU)
																	.hide(new OnComplete() {

																		@Override
																		public void onComplete(
																				Object object) {
																		}
																	});
														}
													};

	public OnClickListener	onCrossWordGame			= new OnClickListener() {

														@Override
														public void onClick(
																float x, float y) {
															getViewController()
																	.getView(
																			StringSystem.VIEW_MAIN_MENU)
																	.hide(new OnComplete() {

																		@Override
																		public void onComplete(
																				Object object) {
																		}
																	});
														}
													};
	public OnClickListener	onDictionaryClicked		= new OnClickListener() {

														@Override
														public void onClick(
																float x, float y) {
															getViewController()
																	.getView(
																			StringSystem.VIEW_MAIN_MENU)
																	.hide(new OnComplete() {

																		@Override
																		public void onComplete(
																				Object object) {
																		}
																	});
														}
													};

	public boolean isIgnoreUpdateMove() {
		return ignoreUpdateMove;
	}

	public void setIgnoreUpdateMove(boolean ignoreUpdateMove) {
		this.ignoreUpdateMove = ignoreUpdateMove;
	}
	
	
	
	
	
	

}
