package anand.android.blemusicaccess.samplechatexample;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by anandm on 05/03/17.
 */

public class DeviceModel implements Parcelable {

    private String deviceName;
    private String macAddress;

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.deviceName);
        dest.writeString(this.macAddress);
    }

    public DeviceModel() {
    }

    protected DeviceModel(Parcel in) {
        this.deviceName = in.readString();
        this.macAddress = in.readString();
    }

    public static final Parcelable.Creator<DeviceModel> CREATOR = new Parcelable.Creator<DeviceModel>() {
        @Override
        public DeviceModel createFromParcel(Parcel source) {
            return new DeviceModel(source);
        }

        @Override
        public DeviceModel[] newArray(int size) {
            return new DeviceModel[size];
        }
    };
}
