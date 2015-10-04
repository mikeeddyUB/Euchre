package euchre.game.utilities;

public class Suite {
	public SuiteName name;
	public SuiteColor color;
	public boolean trump;
	
	public Suite(SuiteName name){
		this.name = name;
		this.color = name.getColor();
		this.trump = false;
	}
	
	public Suite(SuiteName name, boolean trump){
		this.name = name;
		this.color = name.getColor();
		this.trump = trump;
	}
	
	public SuiteName getName() {
		return name;
	}

	public void setName(SuiteName name) {
		this.name = name;
	}
	
	public boolean equals(Object o){
		return (o instanceof Suite && ((Suite)o).getName() == this.getName());
	}

	public SuiteColor getColor() {
		return color;
	}

	public void setColor(SuiteColor color) {
		this.color = color;
	}

	public boolean isTrump() {
		return trump;
	}

	public void setTrump(boolean trump) {
		this.trump = trump;
	}

	public enum SuiteName{
		SPADE(SuiteColor.BLACK),CLUB(SuiteColor.BLACK),HEART(SuiteColor.RED),DIAMOND(SuiteColor.RED);
		
		private SuiteColor color;
		
		SuiteName(SuiteColor color){
			this.color = color;
		}
		public SuiteColor getColor(){
			return this.color;
		}
	}
	
	enum SuiteColor{
		BLACK,RED;
	}
}
