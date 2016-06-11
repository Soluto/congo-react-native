var Observable = require('rx').Observable;
var NativeModules = require('react-native').NativeModules;
var DeviceEventEmitter = require('react-native').DeviceEventEmitter;

module.exports = function(bridgeModuleName) {
    return {
        invoke: function(remoteCall) {
            return Observable.create(function (observer)  {
                DeviceEventEmitter.addListener("onNext_" + correlationId, function(result) {observer.onNext(JSON.parse(result))});
                DeviceEventEmitter.addListener("onCompleted_" + correlationId, function() {observer.onCompleted(); removeListeners()});
                DeviceEventEmitter.addListener("onError_" + correlationId, function(error){observer.onError(JSON.parse(error)); removeListeners()});

                NativeModules[bridgeModuleName].send(JSON.stringify(bridgeMessage));

                return function() {
                    var cancelRemoteCall = Object.assing({}, remoteCall, {isCancelled: true});
                    NativeModules[bridgeModuleName].send(JSON.stringify(bridgeMessage));
                };

                function removeListeners() {
                    DeviceEventEmitter.removeAllListeners("onNext_" + correlationId);
                    DeviceEventEmitter.removeAllListeners("onCompleted_" + correlationId);
                    DeviceEventEmitter.removeAllListeners("onError_" + correlationId);
                }
            });
        }
    }
};
