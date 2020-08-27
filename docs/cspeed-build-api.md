## 创建CSpeed记速器

1. `cheetah-speed`默认提供了两个计速实现，分别为`SimpleRecorder`、`MultiThreadRecorder`，其中`SimpleRecorder`是基础的计速器，并不是线程安全的，适用于单个线程的计速，`MultiThreadRecorder`是多线程场景下使用的计速器。



2. 创建一个`SimpleRecorder`计速器

```java
Recorder recorder = CSpeed.getRecorder("addRecorderName");

Recorder recorder = CSpeed.getRecorder(this.getClass());
```

`CSpeed`负责管理记速器，当指定名称的计速器已经存在时，`getRecorder`方法将直接返回该计速器，如果不存在，将创建一个`SimpleRecorder`过滤器返回。



3. `getRecorder`也可以接受一个创建完成的计速器，如果您希望在不存在计速器时能够指定计速器类型等具体信息，那么您可以：

```java
Recorder recorder = CSpeed.getRecorder(SimpleRecorder.build("addRecorder"));
```

这样当指定的计速器不存在时将使用您创建的计速器，如果您可以确定你要添加的计速器肯定不存在，或者您希望覆盖`CSpeed`中的某个计速器，那么您可以：

```java
Recorder recorder = CSpeed.addRecorder(SimpleRecorder.build("addRecorder"));
```

这样`CSpeed`中同名的计速器将被覆盖。



4. 有时候我们创建一个计速器只需要在一小部分场景用到，并不希望将其加入`CSpeed`中进行统一管理，这时候创建计速器的步骤还可以再简单一些。

```java
// 创建一个简单计速器
Recorder recorder =  SimpleRecorder.build("addRecorder");

// 创建一个多线程的计速器
Recorder recorder =  MultiThreadRecorder.build("addRecorder");
```
