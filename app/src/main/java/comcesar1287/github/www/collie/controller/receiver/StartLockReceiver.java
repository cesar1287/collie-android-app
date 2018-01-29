package comcesar1287.github.www.collie.controller.receiver;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StartLockReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        DevicePolicyManager mDPM =
                (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);

        if(mDPM != null) {
            mDPM.lockNow();
        }
    }
}
