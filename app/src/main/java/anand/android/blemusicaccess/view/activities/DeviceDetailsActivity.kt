package anand.android.blemusicaccess.view.activities

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView

import anand.android.blemusicaccess.samplechatexample.BluetoothChatService
import anand.android.blemusicaccess.utils.Constants
import anand.android.blemusicaccess.samplechatexample.DeviceModel
import anand.android.blemusicaccess.R
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick

/**
 * Created by anandm on 05/03/17.
 */

class DeviceDetailsActivity : AppCompatActivity() {

    private var deviceModel: DeviceModel? = null
    private var bluetoothChatService: BluetoothChatService? = null

    /**
     * Local Bluetooth adapter
     */
    private var mBluetoothAdapter: BluetoothAdapter? = null


    @BindView(R.id.txtDeviceName) internal var txtDeviceName: TextView? = null
    @BindView(R.id.txtMacAddress) internal var txtMacAddress: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_details)
        ButterKnife.bind(this)

        // Get local Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

    }

    override fun onResume() {
        super.onResume()

        val intent = intent
        deviceModel = intent.getParcelableExtra<DeviceModel>(Constants.INTENT_DEVICE_DETAILS)

        txtDeviceName!!.text = deviceModel!!.deviceName
        txtMacAddress!!.text = deviceModel!!.macAddress
        txtDeviceName!!.setTextColor(resources.getColor(R.color.colorPrimaryDark))

    }

    @OnClick(R.id.btnConnect)
    fun connectDevice(button: Button) {

        // Get the BluetoothDevice object
        val device = mBluetoothAdapter!!.getRemoteDevice(deviceModel!!.macAddress)

        bluetoothChatService = BluetoothChatService()
        bluetoothChatService!!.connect(device, true)

    }
}
