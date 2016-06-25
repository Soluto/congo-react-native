#Congo React Native - Android

### Installation
In your build.gradle file add:
```
repositories {
   ...
    maven {
        url "https://dl.bintray.com/soluto/soluto-jars"
    }
    ...

}
...
dependencies {
    ...
    compile 'soluto:congo-core-java:0.0.4'
    compile 'soluto:congo-react-native-android:0.0.2'
    ...
}
```
### Usage
In your React Native app ```MainActivity``` add:
```java
public class MainActivity extends ReactActivity {
    ...
    ...
    @Override
    protected List<ReactPackage> getPackages() {
        Map<String, Object> services = new HashMap<>();
        services.put("myService", new MyService());
        return Arrays.asList(
                ...
                new ReactNativeAndroidBridgePackage(services, "<REQUEST_CHANNEL_NAME>", "<RESPONSE_CHANNEL_NAME>")
                ...
        );
    }
}
```
Notice  the ```REQUEST_CHANNEL_NAME``` and the ```RESPONSE_CHANNEL_NAME```which will be used by the invoker in [congo-react-native-js](https://github.com/Soluto/congo-react-native/tree/master/congo-react-native-js)

### Example
See real app example in: https://github.com/Soluto/congo-examples/tree/master/reactNativeApp
