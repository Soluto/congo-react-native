var Observable = require('rx').Observable;
var NativeModules = require('react-native').NativeModules;
var DeviceEventEmitter = require('react-native').DeviceEventEmitter;

module.exports = function(bridgeModuleName) {
    return {
        invoke: function(remoteCall) {
            return Observable.create(function (observer)  {
                DeviceEventEmitter.addListener("onNext_" + remoteCall.correlationId, function(result) {observer.onNext(JSON.parse(result))});
                DeviceEventEmitter.addListener("onCompleted_" + remoteCall.correlationId, function() {observer.onCompleted(); removeListeners()});
                DeviceEventEmitter.addListener("onError_" + remoteCall.correlationId, function(error){observer.onError(JSON.parse(error)); removeListeners()});

                NativeModules[bridgeModuleName].send(JSON.stringify(remoteCall));

                return function() {
                    var cancelRemoteCall = Object.assign({}, remoteCall, {isCancelled: true});
                    NativeModules[bridgeModuleName].send(JSON.stringify(remoteCall));
                };

                function removeListeners() {
                    DeviceEventEmitter.removeAllListeners("onNext_" + remoteCall.correlationId);
                    DeviceEventEmitter.removeAllListeners("onCompleted_" + remoteCall.correlationId);
                    DeviceEventEmitter.removeAllListeners("onError_" + remoteCall.correlationId);
                }
            });
        }
    }
};
