package utils.listener;

import com.coder5560.game.enums.NetworkState;

public interface INetworkManager {
	
	public boolean isNetworkEnable();

	public NetworkState getNetworkState();

	public void setNetworkState(NetworkState networkState);
	
	
}
