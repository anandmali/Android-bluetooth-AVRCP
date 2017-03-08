package anand.android.blemusicaccess.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import anand.android.blemusicaccess.utils.Constants;
import anand.android.blemusicaccess.samplechatexample.DeviceModel;
import anand.android.blemusicaccess.R;
import anand.android.blemusicaccess.view.activities.DeviceDetailsActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by anandm on 05/03/17.
 */

public class DeviceListAdapter extends RecyclerView.Adapter<DeviceListAdapter.ViewHolder> {

    private ArrayList<DeviceModel> deviceList = new ArrayList<>();
    private Context context;

    public DeviceListAdapter(ArrayList<DeviceModel> deviceList) {
        this.deviceList = deviceList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_devie_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final DeviceModel deviceModel = deviceList.get(position);
        holder.txtDeviceName.setText(deviceModel.getDeviceName());
        holder.txtMacAddress.setText(deviceModel.getMacAddress());
        holder.layoutItemHolder.setTag(deviceModel.getMacAddress());
        holder.layoutItemHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = (String) v.getTag();
                Log.e("Device adapter ", s+"");
                Intent intent = new Intent(context, DeviceDetailsActivity.class);
                intent.putExtra(Constants.INTENT_DEVICE_DETAILS, deviceModel);
                context.startActivity(intent);
//                BluetoothChatService bluetoothChatService = new BluetoothChatService(context);
            }
        });
    }

    @Override
    public int getItemCount() {
        return deviceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtDeviceName) TextView txtDeviceName;
        @BindView(R.id.txtMacAddress) TextView txtMacAddress;
        @BindView(R.id.layoutItemHolder) LinearLayout layoutItemHolder;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
