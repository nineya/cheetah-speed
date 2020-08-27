## 自定义记速器输出

有时候我们希望记速器能够按照我们指定个样式输出，或者希望能有其他不同的操作，希望记速器可以更加的灵活，CSpeed记速器提供了接口来设置记速时的具体操作。

1. 我们可以通过`setEveryPattern`实现自定义记速输出内容，`SpeedEvent`是包含了本次记速信息的实体，但是`SpeedEvent`中的信息是未格式化的，所以在使用之前需要将其转换成我们需要的格式，`CSpeed`提供了`StringUtil`工具类进行格式转换。
- `SpeedEvent`是不限制时间戳单位是`ms`还是`ns`的，但是`CSpeed`内置实现的记速器都是`ns`单位。
```java
MultiThreadRecorder recorder = MultiThreadRecorder.build("setEveryPatternTest");
recorder.setEveryPattern((event)->{
    System.out.println("耗时" + StringUtil.detailNs(event.getRunTime()));
}).collect(()->{
    System.out.println("执行测试！");
}).setCount(5).run().stop();
```

2. 同理可以通过`setStatisticPattern`接口实现自定义统计输出，`StatisticPattern`输出两个参数`name`是记速器名称，`list`是记录集记录的每次耗时情况的列表，`CSpeed`内置的都是`List<Long>`。
- `CSpeed`提供了`StatisticsUtil`工具类进行统计，但是在统计之前需要对数组进行排序。
```java
MultiThreadRecorder recorder = MultiThreadRecorder.build("setStatisticPatternTest");
recorder.setStatisticPattern((name, list)->{
    ((List<Long>)list).sort((a, b) -> (int) (a - b));
    System.out.println(name + "平均耗时: " + StringUtil.detailNs((long) StatisticsUtil.mean((List<Long>) list)));
}).collect(()->{
    System.out.println("执行测试！");
}).setCount(5).run().stop();
```