package soluto.congo.reactnative.android;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.google.gson.Gson;
import rx.Observable;
import rx.subjects.PublishSubject;
import soluto.congo.core.RemoteCall;
import soluto.congo.core.RemoteCallListener;

public final class ReactNativeRemoteCallListener extends ReactContextBaseJavaModule implements RemoteCallListener {
    private PublishSubject<RemoteCall> remoteCalls = PublishSubject.create();
    private String moduleName;

    public ReactNativeRemoteCallListener(ReactApplicationContext reactContext, String moduleName) {
        super(reactContext);
        this.moduleName = moduleName;
    }

    @Override
    public String getName() {
      return moduleName;
    }

    @ReactMethod
    public void send(String serializedRemoteCall) {
        try {
            RemoteCall remoteCall = new Gson().fromJson(serializedRemoteCall, RemoteCall.class);
            remoteCalls.onNext(remoteCall);
        }
        catch (Exception ex) {
        }
    }

    @Override
    public Observable<RemoteCall> getRemoteCalls() {
        return remoteCalls;
    }
}
