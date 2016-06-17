package soluto.congo.reactnative.android;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.google.gson.Gson;

import rx.Completable;
import rx.functions.Action0;
import soluto.congo.core.RemoteCallResponder;
import soluto.congo.core.RemoteCallResult;

public class ReactNativeRemoteCallResponder implements RemoteCallResponder {
    private ReactContext reactContext;
    private String responseChannel;

    public ReactNativeRemoteCallResponder(ReactApplicationContext reactContext, String responseChannel) {
        this.reactContext = reactContext;
        this.responseChannel = responseChannel;
    }

    @Override
    public Completable respond(final RemoteCallResult remoteCallResult) {
        return Completable.fromAction(new Action0() {
            @Override
            public void call() {
                reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                        .emit(responseChannel, new Gson().toJson(remoteCallResult));
            }
        });
    }
}
