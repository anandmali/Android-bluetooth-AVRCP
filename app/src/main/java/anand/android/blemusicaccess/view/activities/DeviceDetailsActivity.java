package anand.android.blemusicaccess.view.activities;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import anand.android.blemusicaccess.samplechatexample.BluetoothChatService;
import anand.android.blemusicaccess.utils.Constants;
import anand.android.blemusicaccess.samplechatexample.DeviceModel;
import anand.android.blemusicaccess.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by anandm on 05/03/17.
 */

public class DeviceDetailsActivity extends AppCompatActivity {

    private DeviceModel deviceModel;
    private BluetoothChatService bluetoothChatService;

    /**
     * Local Bluetooth adapter
     */
    private BluetoothAdapter mBluetoothAdapter = null;


    @BindView(R.id.txtDeviceName) TextView txtDeviceName;
    @BindView(R.id.txtMacAddress) TextView txtMacAddress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_details);
        ButterKnife.bind(this);

        // Get local Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        deviceModel = intent.getParcelableExtra(Constants.INTENT_DEVICE_DETAILS);

        txtDeviceName.setText(deviceModel.getDeviceName());
        txtMacAddress.setText(deviceModel.getMacAddress());

    }

    @OnClick(R.id.btnConnect)
    public void connectDevice(Button button) {

        // Get the BluetoothDevice object
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(deviceModel.getMacAddress());

        bluetoothChatService = new BluetoothChatService();
        bluetoothChatService.connect(device, true);

    }
}
