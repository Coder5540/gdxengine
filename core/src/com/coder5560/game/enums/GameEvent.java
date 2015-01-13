package com.coder5560.game.enums;

public interface GameEvent {
	public enum Event {
		RUNNING, GAME_OVER, GAME_COMPLETE, PAUSE
	}

	public void broadcastEvent(Event event, Object message);

}
