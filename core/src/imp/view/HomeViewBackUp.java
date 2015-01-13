package imp.view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import utils.elements.GalleryViewHorizontal;
import utils.factory.Factory;
import utils.factory.FontFactory.fontType;
import utils.factory.Log;
import utils.listener.IInputListener;
import utils.listener.OnClickListener;
import utils.listener.OnCompleteListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.coder5560.game.assets.Assets;
import com.coder5560.game.ui.CustomTextButton;
import com.coder5560.game.views.View;

public class HomeViewBackUp extends View {
	Table					tbScroll;
	ScrollPane				scroll;
	ArrayList<String>		lines			= new ArrayList<String>();
	ArrayList<ScrollPane>	listScroll		= new ArrayList<ScrollPane>();
	GalleryViewHorizontal	galleryViewHorizontal;
	TextField				start, end, current, directory, directoryData;
	CustomTextButton		customTextButton;
	CustomTextButton		loadData;
	Label					lbDown;
	String					strDirectory	= "/home/dinhanh/Study/English Dictionary/alphabetsounds/";
	Group					group;

	public HomeViewBackUp() {
	}

	public HomeViewBackUp buildComponent() {

		setBackground(new NinePatchDrawable(new NinePatch(
				Assets.instance.ui.reg_ninepatch, colorBg)));

		getViewController().getScreen().setGestureDetector(customListener);

		group = new Group();
		group.setSize(getWidth(), getHeight());

		start = new TextField("" + index,
				com.coder5560.game.ui.UIUtils.getTextFieldStyle(new NinePatch(
						Assets.instance.ui.reg_ninepatch),
						Assets.instance.fontFactory.getFont(30, fontType.Bold),
						Color.BLUE));
		end = new TextField("70000",
				com.coder5560.game.ui.UIUtils.getTextFieldStyle(new NinePatch(
						Assets.instance.ui.reg_ninepatch),
						Assets.instance.fontFactory.getFont(30, fontType.Bold),
						Color.BLUE));
		current = new TextField("" + index,
				com.coder5560.game.ui.UIUtils.getTextFieldStyle(new NinePatch(
						Assets.instance.ui.reg_ninepatch),
						Assets.instance.fontFactory.getFont(30, fontType.Bold),
						Color.BLUE));
		directory = new TextField(strDirectory,
				com.coder5560.game.ui.UIUtils.getTextFieldStyle(new NinePatch(
						Assets.instance.ui.reg_ninepatch),
						Assets.instance.fontFactory.getFont(30, fontType.Bold),
						Color.BLUE));
		directoryData = new TextField("data/alphabet.txt",
				com.coder5560.game.ui.UIUtils.getTextFieldStyle(new NinePatch(
						Assets.instance.ui.reg_ninepatch),
						Assets.instance.fontFactory.getFont(30, fontType.Bold),
						Color.BLUE));

		LabelStyle style = new LabelStyle();
		style.font = Assets.instance.fontFactory.getFont(20, fontType.Bold);
		lbDown = new Label("Start", style);
		lbDown.setAlignment(Align.center);
		customTextButton = new CustomTextButton(new NinePatch(
				Assets.instance.ui.reg_ninepatch4, Color.GREEN), lbDown);
		customTextButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				customTextButton.addAction(Actions.sequence(
						Actions.scaleTo(1.2f, 1.2f, .05f),
						Actions.scaleTo(1f, 1f, .1f, Interpolation.swingOut),
						Actions.run(new Runnable() {
							@Override
							public void run() {
								if (isDownload) {
									lbDown.setText("Start");
									isDownload = false;
								} else {
									lbDown.setText("Pause");
									isDownload = true;
								}
							}
						})));
			}

		});
		Label lbLoad = new Label("Load", style);
		lbLoad.setAlignment(Align.center);
		loadData = new CustomTextButton(new NinePatch(
				Assets.instance.ui.reg_ninepatch4, Color.GREEN), lbLoad);
		loadData.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				loadData.addAction(Actions.sequence(
						Actions.scaleTo(1.2f, 1.2f, .05f),
						Actions.scaleTo(1f, 1f, .1f, Interpolation.swingOut),
						Actions.run(new Runnable() {
							@Override
							public void run() {
								load();
							}
						})));
			}

		});

		customTextButton.setBounds(100, 100, 100, 60);
		loadData.setBounds(300, 100, 100, 60);
		directory.setBounds(40, 650, 400, 60);
		directoryData.setBounds(40, 550, 400, 60);
		start.setBounds(40, 450, 400, 60);
		current.setBounds(40, 350, 400, 60);
		end.setBounds(40, 250, 400, 60);
		group.addActor(start);
		group.addActor(current);
		group.addActor(end);
		group.addActor(directory);
		group.addActor(directoryData);
		group.addActor(customTextButton);
		group.addActor(loadData);
		this.addActor(group);
		load();
		return this;
	}

	void readFile() {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(directoryData.getText()));
			String line = reader.readLine();
			while (line != null) {
				lines.add(line);
				line = reader.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void load() {
		lines.clear();
		readFile();
		galleryViewHorizontal = new GalleryViewHorizontal(this, 1);
		galleryViewHorizontal.pages.setOverscroll(false, false);
		try {
			for (int i = 0; i < lines.size() / 10000 + 1; i++) {
				Table tb = galleryViewHorizontal.newPage();
				tb.setBackground(new NinePatchDrawable(new NinePatch(
						Assets.instance.ui.reg_ninepatch, colorBg)));
				tbScroll = new Table();
				tbScroll.setSize(getWidth(), getHeight());
				tbScroll.defaults().expandX().fillX().height(60).left();
				scroll = new ScrollPane(tbScroll);
				scroll.setScrollingDisabled(true, false);
				scroll.setSmoothScrolling(true);
				scroll.setForceScroll(false, true);
				scroll.setClamp(true);
				scroll.setCancelTouchFocus(false);

				listScroll.add(scroll);
				tb.add(scroll).expand().fill().top();
				for (int k = 0; k < (10000 < lines.size() ? 10000 : lines
						.size()); k++) {
					tbScroll.row();
					ItemWord itemMail = new ItemWord(i * 10000 + k, lines.get(i
							* 10000 + k), "It means to go ahead .... ",
							tbScroll.getWidth(), 60);
					tbScroll.add(itemMail);
					tbScroll.row();
					addLine(tbScroll, 2, 0, 0, 0, 0);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		group.toFront();
	}

	@Override
	public void show(OnCompleteListener listener) {
		super.show(listener);
	}

	@Override
	public void hide(OnCompleteListener listener) {
		super.hide(listener);
	}

	int		index		= 0;
	int		countTime	= 0;
	int		maxTime		= 500;
	boolean	isDownload	= false;

	@Override
	public void update(float delta) {
		if (isPanHorizontal || isPanVerticle) {
			if (isPanHorizontal) {
				for (ScrollPane scrl : listScroll) {
					getStage().unfocus(scrl);
					scrl.cancel();
				}
			} else if (isPanVerticle) {
				getStage().unfocus(galleryViewHorizontal.pages);
				galleryViewHorizontal.pages.cancel();
				galleryViewHorizontal.pages.scrollToPage();
			}
		}
		if (isDownload) {
			strDirectory = directory.getText().toString();
			int max = Integer.parseInt(end.getText());
			current.setText("" + index);
			if (index < Integer.parseInt(start.getText())) {
				index = Integer.parseInt(start.getText());
			}

			if (index > max)
				Gdx.app.exit();
			countTime++;
			if (countTime >= 200) {
				if (index < lines.size()) {
					try {
						String filePath = strDirectory + lines.get(index)
								+ ".mp3";
						File f = new File(filePath);
						if (!f.exists()) {
							Log.d(index + ". Saving File : " + lines.get(index)
									+ ".mp3");
							Factory.save(strDirectory, lines.get(index));
						} else {
							Log.d("File Already Exist");
						}
					} catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					index += 1;
				} else {
					Gdx.app.exit();
				}
				countTime = 0;
			}
		}
	}

	@Override
	public void destroyComponent() {
	}

	@Override
	public void back() {
	}

	class ItemWord extends Group {
		Image			bg;
		Image			icon;
		LabelStyle		styleTitle, styleDesciption;
		Label			lbTitle, lbShortDescription;
		Color			bgColor, wordColor, desColor;
		public int		index;
		OnClickListener	onClickListener;

		public ItemWord(final int index, final String title,
				final String description, final float width, float height) {
			this.setSize(width, height);
			this.index = index;
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
					2 * width / 3 - 10, styleDesciption.font), styleDesciption);
			lbShortDescription.setAlignment(Align.center, Align.left);
			lbShortDescription.setTouchable(Touchable.disabled);
			lbShortDescription.setWrap(true);

			bg = new Image(Assets.instance.ui.reg_ninepatch);
			bg.setColor(bgColor);

			bg.addListener(new ClickListener() {
				boolean	isTouch	= false;
				Vector2	touch	= new Vector2();

				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					touch.set(x, y);
					isTouch = true;
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					if (!isTouch || touch.isZero())
						return;
					final float px = x, py = y;
					bg.addAction(Actions.sequence(Actions.color(new Color(
							220 / 255f, 220 / 255f, 220 / 255f, 1f), 0.1f),
							Actions.run(new Runnable() {

								@Override
								public void run() {
									if (onClickListener != null)
										onClickListener.onClick(px, py);
									bg.setColor(bgColor);
									String filePath = strDirectory + title
											+ ".mp3";
									File f = new File(filePath);
									if (!f.exists()) {
										try {
											Factory.save(strDirectory, title);
										} catch (MalformedURLException e) {
											e.printStackTrace();
										} catch (IOException e) {
											e.printStackTrace();
										}
									} else {
										Log.d("File already exist");
									}

									Log.d("Click to word " + title);
								}
							})));

				}

				@Override
				public void touchDragged(InputEvent event, float x, float y,
						int pointer) {
					if (!touch.epsilonEquals(x, y, 10))
						touch.set(0, 0);
					if (isPanHorizontal || isPanVerticle)
						isTouch = false;
					super.touchDragged(event, x, y, pointer);
				}
			});

			addActor(bg);
			addActor(lbTitle);
			addActor(lbShortDescription);
			valid();
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
	}

	void addLine(Table table, float height, float padLeft, float padRight,
			float padTop, float padBottom) {
		/*
		 * Image line = new Image(Assets.instance.ui.reg_ninepatch);
		 * line.setColor(new Color(100 / 255f, 100 / 255f, 100 / 255f, 0.7f));
		 * line.setHeight(height); line.setWidth(table.getWidth());
		 * table.add(line).expandX().fillX().height(height).padTop(padTop)
		 * .padLeft(padLeft).padBottom(padBottom).padRight(padRight)
		 * .colspan(4); table.row();
		 */
	}

	private Color			colorBg			= new Color(0 / 255f, 0 / 255f,
													0 / 255f, 1f);

	ActorGestureListener	scrollListener	= new ActorGestureListener() {
												@Override
												public void pan(
														InputEvent event,
														float x, float y,
														float deltaX,
														float deltaY) {
													event.stop();
													super.pan(event, x, y,
															deltaX, deltaY);
												}

												@Override
												public void touchDown(
														InputEvent event,
														float x, float y,
														int pointer, int button) {
													super.touchDown(event, x,
															y, pointer, button);
												}
											};

	void addGallaryListener() {
		galleryViewHorizontal.pages.addListener(scrollListener);
	}

	boolean					canPan			= false;
	public static boolean	isPanHorizontal	= false;
	public static boolean	isPanVerticle	= false;
	IInputListener			customListener	= new IInputListener() {

												public boolean touchDown(
														float x, float y,
														int pointer, int button) {
													Log.d("Touch Down");
													if (!canPan) {
														canPan = true;
													}
													return false;
												};

												public boolean pan(float x,
														float y, float deltaX,
														float deltaY) {
													if (canPan) {
														if (!isPanHorizontal
																&& !isPanHorizontal
																&& Math.abs(deltaX) >= 0.5f
																&& Math.abs(deltaY) >= 0.5f) {
															if (Math.abs(deltaX) > Math
																	.abs(deltaY)) {
																isPanHorizontal = true;
																isPanVerticle = false;
															} else {
																isPanVerticle = true;
																isPanHorizontal = false;
															}
														}
													}
													if (isPanHorizontal)
														Log.d("Pan Horizontal");
													if (isPanVerticle)
														Log.d("Pan Verticle");

													return false;
												};

												public boolean panStop(float x,
														float y, int pointer,
														int button) {

													return false;
												};

												public boolean touchUp(
														int screenX,
														int screenY,
														int pointer, int button) {
													canPan = false;
													isPanHorizontal = false;
													isPanVerticle = false;
													if (isPanHorizontal
															|| isPanVerticle)
														return true;
													return false;
												};

											};
}
