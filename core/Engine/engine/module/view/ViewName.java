package engine.module.view;

public enum ViewName {

	DEFAULT("DEFAULT"),

	HOME_VIEW("HOME_VIEW"),

	TEST_VIEW("TEST_VIEW"),

	TEST_VIEW2("TEST_VIEW2"),

	TEST_VIEW3("TEST_VIEW3"),

	TEST_VIEW4("TEST_VIEW4"),
	
	HOME_VIEW2("HOME_VIEW2"),

	;

	String	name	= "";

	private ViewName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
