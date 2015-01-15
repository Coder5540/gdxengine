package engine.module.list;

import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.coder5560.game.assets.Assets;

import engine.element.TableElement;
import engine.module.pool.PoolManager;

public class TestListView extends AbstractListView {

	public TestListView(float width, float height) {
		super(width, height);
	}

	public TestListView buildComponent(PoolManager poolManager) {
		for (int i = 0; i < 100; i++) {
			TableElement tbElement = poolManager.tableElementPool.obtain();
			tbElement.setSize(getWidth(), getHeight());
			tbElement.setBackground(new TextureRegionDrawable(
					Assets.instance.ui.reg_ninepatch));
			tbElement.setOrigin(Align.center);
			addPage(tbElement);
		}
		return this;
	}
}
