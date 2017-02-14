package edu.ksu.cs.santos.benign.sendgreetings;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class ITestService extends Service {
    public ITestService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new ITestInterface.Stub() {
            @Override
            public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

            }

            @Override
            public String exec(String cmd) throws RemoteException {
                return cmd;
            }
        };
    }
}
