package anand.android.blemusicaccess.samplechatexample.thread;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;

import static anand.android.blemusicaccess.samplechatexample.BluetoothChatService.MY_UUID_INSECURE;
import static anand.android.blemusicaccess.samplechatexample.BluetoothChatService.MY_UUID_SECURE;
import static anand.android.blemusicaccess.samplechatexample.BluetoothChatService.STATE_CONNECTING;
import static anand.android.blemusicaccess.samplechatexample.BluetoothChatService.connected;
import static anand.android.blemusicaccess.samplechatexample.BluetoothChatService.mAdapter;
import static anand.android.blemusicaccess.samplechatexample.BluetoothChatService.mConnectThread;
import static anand.android.blemusicaccess.samplechatexample.BluetoothChatService.mState;

/**
 * Created by anandm on 05/03/17.
 */

/**
 * This thread runs while attempting to make an outgoing connection
 * with a device. It runs straight through; the connection either
 * succeeds or fails.
 */
public class ConnectThread extends Thread {

    private static final String TAG = ConnectThread.class.getSimpleName();
    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
    private String mSocketType;
    private BluetoothAdapter bluetoothAdapter;

    public ConnectThread(BluetoothDevice device, boolean secure) {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mmDevice = device;
        BluetoothSocket tmp = null;
        mSocketType = secure ? "Secure" : "Insecure";

        //http://stackoverflow.com/questions/13362774/android-listen-for-connections-without-uuid
        //This connection sugested to connect without uuid
        //Did tried, but no progress, need little more research
//        try {
//            Method m = mAdapter.getClass().getMethod("listenUsingRfcommOn", new Class[] { int.class });
//            bluetoothServerSocket = (BluetoothServerSocket) m.invoke(mAdapter, 1);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }





        // Get a BluetoothSocket for a connection with the
        // given BluetoothDevice
        try {
            if (secure) {
                tmp = device.createRfcommSocketToServiceRecord(
                        MY_UUID_SECURE);
            } else {
                tmp = device.createInsecureRfcommSocketToServiceRecord(
                        MY_UUID_INSECURE);
            }
        } catch (IOException e) {
            Log.e(TAG, "Socket Type: " + mSocketType + "create() failed", e);
        }
        mmSocket = tmp;
        mState = STATE_CONNECTING;
    }

    public void run() {
        Log.i(TAG, "BEGIN mConnectThread SocketType:" + mSocketType);
        setName("ConnectThread" + mSocketType);


        // Always cancel discovery because it will slow down a connection
        mAdapter.cancelDiscovery();

        // Make a connection to the BluetoothSocket
        try {
            // This is a blocking call and will only return on a
            // successful connection or an exception
            mmSocket.connect();
        } catch (IOException e) {
            // Close the socket
            try {
                mmSocket.close();
            } catch (IOException e2) {
                Log.e(TAG, "unable to close() " + mSocketType +
                        " socket during connection failure", e2);
            }

            Log.e(TAG, "Connection failed");

//            connectionFailed();
            return;
        }

        // Reset the ConnectThread because we're done
        synchronized (this) {
            mConnectThread = null;
        }

        // Start the connected thread
        connected(mmSocket, mmDevice, mSocketType);
    }

    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
            Log.e(TAG, "close() of connect " + mSocketType + " socket failed", e);
        }
    }
}
