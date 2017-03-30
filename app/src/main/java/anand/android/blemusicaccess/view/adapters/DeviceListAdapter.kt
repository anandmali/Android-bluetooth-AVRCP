package anand.android.blemusicaccess.view.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

import java.util.ArrayList

import anand.android.blemusicaccess.utils.Constants
import anand.android.blemusicaccess.samplechatexample.DeviceModel
import anand.android.blemusicaccess.R
import anand.android.blemusicaccess.view.activities.DeviceDetailsActivity
import butterknife.BindView
import butterknife.ButterKnife

/**
 * Created by anandm on 05/03/17.
 */

class DeviceListAdapter(deviceList: ArrayList<DeviceModel>) : RecyclerView.Adapter<DeviceListAdapter.ViewHolder>() {

    private val deviceList = ArrayList<DeviceModel>()
    private var context: Context? = null

//    init {
//        this.deviceList = deviceList
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.list_item_devie_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val deviceModel = deviceList[position]
        holder.txtDeviceName!!.text = deviceModel.deviceName
        holder.txtMacAddress!!.text = deviceModel.macAddress
        holder.layoutItemHolder!!.tag = deviceModel.macAddress
        holder.layoutItemHolder!!.setOnClickListener { v ->
            val s = v.tag as String
            Log.e("Device adapter ", s + "")
            val intent = Intent(context, DeviceDetailsActivity::class.java)
            intent.putExtra(Constants.INTENT_DEVICE_DETAILS, deviceModel)
            context!!.startActivity(intent)
            //                BluetoothChatService bluetoothChatService = new BluetoothChatService(context);
        }
    }

    override fun getItemCount(): Int {
        return deviceList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.txtDeviceName) internal var txtDeviceName: TextView? = null
        @BindView(R.id.txtMacAddress) internal var txtMacAddress: TextView? = null
        @BindView(R.id.layoutItemHolder) internal var layoutItemHolder: LinearLayout? = null

        init {
            ButterKnife.bind(this, itemView)
        }
    }
}
