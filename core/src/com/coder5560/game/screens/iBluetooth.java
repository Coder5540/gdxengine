package com.coder5560.game.screens;

public interface iBluetooth {
	public static final int		MESSAGE_STATE_CHANGE	= 1;
	public static final int		MESSAGE_READ			= 2;
	public static final int		MESSAGE_WRITE			= 3;
	public static final int		MESSAGE_DEVICE_NAME		= 4;
	public static final int		MESSAGE_TOAST			= 5;
	public static final String	DEVICE_NAME				= "device_name";
	public static final String	TOAST					= "toast";

	public void enableBluetooth();

	public void enableDiscoveribility();

	public void discoverDevices();

	public void stopDiscovering();

	public boolean startServer();

	public void connectToServer();

	public String getTest();

	public void sendMessage(String message);

	public String getMessage();

	public boolean isConnected();

	public boolean canConnect();

	public void switchToNextDevice();

	public void switchToPrevDevice();

	public String getDevice();

	public void stop();

	public boolean isFirst();

	public boolean isLast();

	public boolean isDiscovering();
}