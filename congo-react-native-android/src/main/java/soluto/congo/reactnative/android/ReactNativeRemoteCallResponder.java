package soluto.congo.reactnative.android;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.google.gson.Gson;
import com.remote.BridgeErrorMessage;
import com.remote.RemoteCall;
import com.remote.RemoteCallResponder;

public class ReactNativeRemoteCallResponder implements RemoteCallResponder {
    private ReactContext mReactContext;

    public ReactNativeRemoteCallResponder(ReactApplicationContext reactContext) {
        mReactContext = reactContext;
    }

    public void onNext(RemoteCall remoteCall, Object result) {

        mReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit("onNext_" + remoteCall.correlationId, new Gson().toJson(result));
    }

    public void onCompleted(RemoteCall remoteCall) {
        mReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit("onCompleted_" + remoteCall.correlationId, null);
    }

    public void onError(RemoteCall remoteCall, Throwable exception) {
        mReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit("onError_" + remoteCall.correlationId,  new Gson().toJson(new BridgeErrorMessage(exception.getMessage())));
    }
}
