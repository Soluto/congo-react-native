var Observable = require('rx').Observable;
var NativeModules = require('react-native').NativeModules;
var DeviceEventEmitter = require('react-native').DeviceEventEmitter;

module.exports = function(moduleName, responseChannel) {
    return {
        invoke: function(remoteCall) {
            return Observable.create(function (observer)  {
                console.log(responseChannel)
                DeviceEventEmitter.addListener(responseChannel, function(message) {
                    var remoteCallResult = JSON.parse(message);
                    if (remoteCallResult.correlationId !== remoteCall.correlationId) return;

                    if (remoteCallResult.notification.kind === "OnNext") {
                        observer.onNext(remoteCallResult.notification.value);
                    }
                    if (remoteCallResult.notification.kind === "OnCompleted") {
                        observer.onCompleted();
                    }
                    if (remoteCallResult.notification.kind === "OnError") {
                        observer.onError(remoteCallResult.notification.error);
                    }
                });

                NativeModules[moduleName].send(JSON.stringify(remoteCall));

                return function() {
                    var cancelRemoteCall = Object.assign({}, remoteCall, {isCancelled: true});
                    NativeModules[moduleName].send(JSON.stringify(remoteCall));
                };
            });
        }
    }
};
