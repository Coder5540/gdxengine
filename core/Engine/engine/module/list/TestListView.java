package engine.module.list;

import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.coder5560.game.assets.Assets;

public class TestListView extends AbstractListView {

	public TestListView(float width, float height) {
		super(width, height);
	}

	public TestListView buildComponent() {
		for (int i = 0; i < 10; i++) {
			Page page = newPage();
			page.setBackground(new TextureRegionDrawable(
					Assets.instance.ui.reg_ninepatch));
			page.setOrigin(Align.center);
		}
		return this;
	}

}
