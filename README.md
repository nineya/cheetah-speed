## cheetah-speed
性能测试的计速工具，支持单线程、并发测试，可方便的统计每个线程的最大耗时、最小耗时和分位值。



- 更详细的文档，请前往：[cspeed.nineya.com](https://cspeed.nineya.com)



### 快速开始

1. 引入Maven依赖：

```xml
<dependency>
    <groupId>com.nineya.cspeed</groupId>
    <artifactId>cheetah-speed</artifactId>
    <version>1.0.0</version>
</dependency>
```



2. `cheetah-speed`默认提供了两个计速实现，分别为`SimpleRecorder`、`MultiThreadRecorder`，其中`SimpleRecorder`是基础的计速器，并不是线程安全的，适用于单个线程的计速，`MultiThreadRecorder`是多线程场景下使用的计速器。



3. 创建一个`SimpleRecorder`计速器

```java
Recorder recorder = CSpeed.getRecorder("addRecorderName");

Recorder recorder = CSpeed.getRecorder(this.getClass());
```

`CSpeed`是一个计速器管理器，当指定名称的计速器已经存在时，`getRecorder`方法将直接返回该计速器，如果不存在，将创建一个`SimpleRecorder`过滤器返回。



3.1 `getRecorder`也可以接受一个创建完成的计速器，如果您希望在不存在计速器时能够指定计速器类型等具体信息，那么您可以：

```java
Recorder recorder = CSpeed.getRecorder(SimpleRecorder.build("addRecorder"));
```

这样当指定的计速器不存在时将使用您创建的计速器，如果您可以确定你要添加的计速器肯定不存在，或者您希望覆盖`CSpeed`中的某个计速器，那么您可以：

```java
Recorder recorder = CSpeed.addRecorder(SimpleRecorder.build("addRecorder"));
```

这样`CSpeed`中同名的计速器将被覆盖。



3.2 有时候我们创建一个计速器只需要在一小部分场景用到，并不希望将其加入`CSpeed`中进行统一管理，这时候创建计速器的步骤还可以再简单一些。

```java
// 创建一个简单计速器
Recorder recorder =  SimpleRecorder.build("addRecorder");

// 创建一个多线程的计速器
Recorder recorder =  MultiThreadRecorder.build("addRecorder");
```



4. 使用计速器进行计速非常简单。

```java
// 执行程序前开始计速
recorder.start();

// 执行需要计速的某些内容...

// 结束本次计速，输出耗时数据
recorder.end();
```

执行了上述步骤之后，在计速器中已经存储了计速的数据，我们有三种方式可以输出统计信息。

```java
// 输出统计信息
recorder.statistics();
// 输出统计信息，同时清空计速器数据
recorder.reset();
// 输出统计信息，同时停止计速器
recorder.stop();
```



5. 除了上述方法外，`CSpeed`还提供了一个更加简洁的方式实现计速功能。

```java
MultiThreadRecorder recorder = MultiThreadRecorder.build("collectTest");
recorder.collect(()->{	// 进行测试的内容
    System.out.println("执行测试！");
})
.setCount(5)	//任务数
.run()		// 执行计速
.stop();	// 停止计速器，输出计速结果
```

