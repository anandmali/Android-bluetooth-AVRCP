package anand.android.blemusicaccess.samplechatexample

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by anandm on 05/03/17.
 */

class DeviceModel : Parcelable {

    var deviceName: String? = null
    var macAddress: String? = null

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(this.deviceName)
        dest.writeString(this.macAddress)
    }

    constructor() {}

    protected constructor(`in`: Parcel) {
        this.deviceName = `in`.readString()
        this.macAddress = `in`.readString()
    }

    companion object {

        val CREATOR: Parcelable.Creator<DeviceModel> = object : Parcelable.Creator<DeviceModel> {
            override fun createFromParcel(source: Parcel): DeviceModel {
                return DeviceModel(source)
            }

            override fun newArray(size: Int): Array<DeviceModel> {
                return arrayOfNulls(size)
            }
        }
    }
}
