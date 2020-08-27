## 使用记速器进行记速

1. 使用计速器进行计速非常简单。

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



2. 除了上述方法外，`CSpeed`还提供了一个更加简洁的方式实现计速功能。

```java
MultiThreadRecorder recorder = MultiThreadRecorder.build("collectTest");
recorder.collect(()->{	// 进行测试的内容
    System.out.println("执行测试！");
})
.setCount(5)	//任务数
.run()		// 执行计速
.stop();	// 停止计速器，输出计速结果
```

