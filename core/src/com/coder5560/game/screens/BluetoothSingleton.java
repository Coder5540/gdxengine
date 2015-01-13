package com.coder5560.game.screens;
public class BluetoothSingleton {
 
    private static volatile BluetoothSingleton instance = null;
    public iBluetooth bluetoothManager;
 
    /* METHODS */
    public static BluetoothSingleton getInstance() {
        if (instance == null) {
            synchronized (BluetoothSingleton.class) {
                if (instance == null) {
                    instance = new BluetoothSingleton();
                }
            }
        }
        return instance;
    }
 
    private BluetoothSingleton() {
 
    }
     
}