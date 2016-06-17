package soluto.congo.reactnative.android;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import soluto.congo.core.ControllerHandler;
import soluto.congo.core.RemoteCallResponder;
import soluto.congo.core.Router;

public class ReactNativeAndroidBridgePackage implements ReactPackage {
    private Map<String, Object> services;
    private String moduleName;
    private String responseChannel;

    public ReactNativeAndroidBridgePackage(Map<String, Object> services, String moduleName, String responseChannel) {
        this.services = services;
        this.moduleName = moduleName;
        this.responseChannel = responseChannel;
    }

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        ReactNativeRemoteCallListener listener = new ReactNativeRemoteCallListener(reactContext, moduleName);
        RemoteCallResponder responder = new ReactNativeRemoteCallResponder(reactContext, responseChannel);
        Router router = new Router(listener, responder);

        for (String serviceName: services.keySet()) {
            router.use(new ControllerHandler(serviceName, services.get(serviceName)));
        }

        router.listen();
        List<NativeModule> modules = new ArrayList<>();
        modules.add(listener);
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
