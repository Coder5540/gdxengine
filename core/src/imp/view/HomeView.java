package imp.view;

import java.util.ArrayList;

import utils.elements.GalleryViewHorizontal;
import utils.elements.IconFunction;
import utils.factory.Factory;
import utils.factory.FontFactory.fontType;
import utils.listener.OnClickListener;
import utils.listener.OnCompleteListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
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
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.coder5560.game.assets.Assets;
import com.coder5560.game.views.View;

public class HomeView extends View {
	Table					tbScroll;
	ScrollPane				scroll;
	ArrayList<String>		lines			= new ArrayList<String>();
	GalleryViewHorizontal	galleryViewHorizontal;

	public HomeView() {
	}

	public HomeView buildComponent() {
		setBackground(new NinePatchDrawable(new NinePatch(
				Assets.instance.ui.reg_ninepatch, Color.BLACK)));

		GalleryViewHorizontal galleryViewHorizontal = new GalleryViewHorizontal(this, 1);
		for (int i = 0; i < 10; i++) {
			IconFunction iconFunction = new IconFunction(getViewController(),
					220, 220, new Texture(
							Gdx.files.internal("Img/Add-User-icon.png")),
					"Function");
			this.add(iconFunction).pad(20);
			if (this.getChildren().size % 3 == 0 && this.getChildren().size > 0) {
				this.row();
			}
		}
		return this;
	}

	@Override
	public void show(OnCompleteListener listener) {
		super.show(listener);
	}

	@Override
	public void hide(OnCompleteListener listener) {
		super.hide(listener);
	}

	@Override
	public void update(float delta) {
	}

	@Override
	public void destroyComponent() {
	}

	@Override
	public void back() {
	}

	class ItemWord extends Group {
		private Image	bg;
		private Image	icon;
		private LabelStyle	styleTitle, styleDesciption;
		private Label		lbTitle, lbShortDescription;
		private Color		bgColor, wordColor, desColor;
		public int			index;
		OnClickListener		onClickListener;

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
							220 / 255f, 220 / 255f, 220 / 255f, 1f), 0.1f),
							Actions.run(new Runnable() {

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
}
