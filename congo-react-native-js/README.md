#Congo React Native - JavaScript

### Installation
```npm install congo-react-native-js --save```

### Usage
####Invoker
First, create the invoker:
```javascript
var CongoReactNativeInvoker = require("congo-react-native-js").CongoReactNativeInvoker;
var reactNativeInvoker = new CongoReactNativeInvoker("<REQUEST_CHANNEL_NAME>", "<RESPONSE_CHANNEL_NAME>");
```
Then, use it directly by invoking remote call:
```javascript
var remoteCall = {
    correlationId: "34c99d2e-5d61-48a7-812f-59dcb6628433",
    service: "someService",
    method: "someMethod"
};

reactNativeInvoker.invoke(remoteCall)
    .doOnNext(function(item) {
        console.log(item);
    })
    .doOnCompleted(function() {
        console.log("completed");
    })
    .doOnError(function(error) {
        console.log(error);
    })
    .subscribe();
```
or use [Congo Proxy](https://github.com/Soluto/congo-proxy)

### Example
See real app example in: https://github.com/Soluto/congo-examples/tree/master/reactNativeApp


