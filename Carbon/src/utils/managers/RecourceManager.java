package utils.managers;

public abstract class RecourceManager {
	
	protected int count = 1;
	
	public void addReference() {
		count++;
	}
	
	public boolean removeReference() {
		if(count>1) count--;
		return count==0;
	}

}
