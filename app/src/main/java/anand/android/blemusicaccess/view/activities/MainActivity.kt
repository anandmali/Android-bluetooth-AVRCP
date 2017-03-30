package anand.android.blemusicaccess.view.activities

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.widget.Toast

import com.polidea.rxandroidble.RxBleClient

import java.util.ArrayList

import anand.android.blemusicaccess.samplechatexample.DeviceModel
import anand.android.blemusicaccess.R
import anand.android.blemusicaccess.view.adapters.DeviceListAdapter
import butterknife.BindView
import butterknife.ButterKnife

class MainActivity : AppCompatActivity(), BluetoothAdapter.LeScanCallback {
    private var mBluetoothAdapter: BluetoothAdapter? = null
    private val mScanning: Boolean = false
    private val rxBleClient: RxBleClient? = null
    private val deviceList = ArrayList<DeviceModel>()
    private var adapter: DeviceListAdapter? = null

    @BindView(R.id.toolbar) internal var _toolbar: Toolbar? = null
    @BindView(R.id.listPairedDevices) internal var _listPairedDevices: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        adapter = DeviceListAdapter(deviceList)
        _listPairedDevices!!.layoutManager = LinearLayoutManager(this)
        _listPairedDevices!!.adapter = adapter

        // Use this check to determine whether BLE is supported on the device.  Then you can
        // selectively disable BLE-related features.
        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT).show()
        }

        //Getting bluetooth adapter
        //It is required for any further interactions using ble
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            Log.e(TAG, "device do not support ble")
        }

        //Check if ble is enabled
        //If not, open settings activity
        if (!mBluetoothAdapter!!.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        // User chose not to enable Bluetooth.
        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
            Log.e(TAG, "Not enabled")
            return
        }
        Log.e(TAG, "enabled")
        val pairedDevices = mBluetoothAdapter!!.bondedDevices
        Log.e(TAG, "Result enabled " + pairedDevices.size)

        if (pairedDevices.size > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (device in pairedDevices) {
                val deviceName = device.name
                val deviceHardwareAddress = device.address // MAC address
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


    override fun onResume() {
        super.onResume()

        if (mBluetoothAdapter!!.isDiscovering)
            mBluetoothAdapter!!.cancelDiscovery()

        mBluetoothAdapter!!.startDiscovery()

        val pairedDevices = mBluetoothAdapter!!.bondedDevices
        Log.e(TAG, "OnResume enabled " + pairedDevices.size)

        if (pairedDevices.size > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (device in pairedDevices) {
                val deviceName = device.name
                val deviceHardwareAddress = device.address // MAC address
                val deviceModel = DeviceModel()
                deviceModel.deviceName = deviceName
                deviceModel.macAddress = deviceHardwareAddress
                deviceList.add(deviceModel)
            }
            adapter!!.notifyDataSetChanged()
        }

    }

    // Create a BroadcastReceiver for ACTION_FOUND.
    private val mReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            Log.e(TAG, "Broad " + action)
            if (BluetoothDevice.ACTION_FOUND == action) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                //                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //                String deviceName = device.getName();
                //                String deviceHardwareAddress = device.getAddress(); // MAC address
                //                DeviceModel
                //                deviceList.add(deviceName);
            }
        }
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onLeScan(device: BluetoothDevice, rssi: Int, scanRecord: ByteArray) {
        Log.e(TAG, "on scan " + device.name)
    }

    companion object {

        private val TAG = MainActivity::class.java.simpleName
        private val REQUEST_ENABLE_BT = 999
    }
}
