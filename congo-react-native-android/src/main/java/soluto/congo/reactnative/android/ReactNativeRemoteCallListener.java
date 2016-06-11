package soluto.congo.reactnative.android;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.google.gson.Gson;
import com.remote.RemoteCall;
import com.remote.RemoteCallListener;

import rx.Observable;
import rx.subjects.PublishSubject;

public final class ReactNativeRemoteCallListener extends ReactContextBaseJavaModule implements RemoteCallListener {
    private PublishSubject<RemoteCall> mRemoteCalls = PublishSubject.create();
    private String bridgeName;

    public ReactNativeRemoteCallListener(ReactApplicationContext reactContext, String bridgeName) {
        super(reactContext);
        this.bridgeName = bridgeName;
    }

    @Override
    public String getName() {
      return bridgeName;
    }

    @ReactMethod
    public void send(String serializedRemoteCall) {
        try {
            RemoteCall remoteCall = new Gson().fromJson(serializedRemoteCall, RemoteCall.class);
            mRemoteCalls.onNext(remoteCall);
        }
        catch (Exception ex) {
        }
    }

    @Override
    public Observable<RemoteCall> getRemoteCalls() {
        return mRemoteCalls;
    }
}
