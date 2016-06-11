package soluto.congo.reactnative.android;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import com.remote.Bridge;
import com.remote.RemoteCallResponder;
import com.remote.RpcListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ReactNativeAndroidBridgePackage implements ReactPackage {

    private Map<String, Object> mServices;
    private String mBridgeName;

    public ReactNativeAndroidBridgePackage(Map<String, Object> services, String bridgeName) {
        mServices = services;
        mBridgeName = bridgeName;
    }

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        List<NativeModule> modules = new ArrayList<>();
        ReactNativeRemoteCallListener remoteCallListener = new ReactNativeRemoteCallListener(reactContext, mBridgeName);
        modules.add(remoteCallListener);

        RemoteCallResponder responder = new ReactNativeRemoteCallResponder(reactContext);
        for (String serviceName: mServices.keySet()) {
            new Bridge(mServices.get(serviceName), new RpcListener(serviceName, remoteCallListener.getRemoteCalls(), responder)).startListening();
        }

        return modules;
    }

    @Override
    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return Collections.emptyList();
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }
}
