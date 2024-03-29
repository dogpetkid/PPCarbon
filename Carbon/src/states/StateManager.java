package states;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;

public class StateManager {
	
	private Map<String, State> map;
	private State currentState;
	
	public StateManager() {
		map = new HashMap<String, State>();
	}
	
	public void addState(State state) {
		map.put(state.getName().toUpperCase(), state);
		state.init();
		if(currentState == null) {
			state.enter();
			currentState = state;
		}
	}
	
	public void setState(String name) {
		State state = map.get(name.toUpperCase());
		if(state == null) {
			System.err.println("State <"+name+"> does not exist!");
			return;
		}
		currentState.exit();
		state.enter();
		currentState = state;
	}
	
	public String getCurrentStateName() {
		return currentState.getName();
	}
	
	/** Resets and initiates the room.
	 * (Same as 're-entering' the room in Game Maker.)
	 */
	public void resetCurrentState() {
		//map.remove(currentState.getName().toUpperCase());
		/*
		map.remove(currentState.getName());
		addState(currentState);
		setState(currentState.getName());
		*/
		currentState.reset();
	}
	
	public void tick() {
		currentState.tick(this);
	}
	
	public void render(Graphics2D g) {
		currentState.render(g);
	}

}
