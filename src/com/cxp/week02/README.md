[TOC]
# 第四题
## Serial GC 串行GC
1. 使用单线程进行垃圾回收，执行效率相对较低
2. 年轻代使用mark-copy算法
3. Serial GC Old 使用mark-sweep-compact
4. full gc仅清理老年代
5. 已经很少使用
6. 基于响应时间的设计

## ParNew GC
1. Serial GC的并行版本
2. 仅适用于年轻代
3. 使用mark-copy算法
4. 在CMS中作为Young GC
5. 基于响应时间的设计

## Parallel GC
1. 并行GC，可以充分利用多核CPU，降低STW时间
2. 年轻代使用mark-copy Parallel scavenge
3. 老年代使用mark-sweep-compact
4. full gc会清理年轻代和老年代
5. JDK8的默认GC
6. 基于吞吐量的设计
7. 同时对young及old区进行GC

## CMS GC
1. 并发GC，用于老年代的垃圾回收
2. 使用mark-sweep算法，不进行compact，使用freelist来保证内存分配的效率
3. 清理过程
   1. Initial Mark：标记GC Roots可达的第一层对象和跨区的引用，进入STW
   2. Concurrent Mark：并发标记
   3. Concurrent Preclean：并发预清理
      1. 可取消的并发预清理
   4. Final remark：最终标记
   5. Concurrent Sweep：并发清理
   6. Concurrent Reset：并发重置
4. 回收时有可能发生Young GC

## G1
1. Garbage-first gc
2. 混合模式
3. G1垃圾回收可能退化为串行化

## 总结
1. 堆内存配置太小，对象容易分配失败，导致频繁GC，GC后如果仍然无法放下对象就会产生OOM，内存更富裕，GC次数就会减少，每次清理的内存更多，GC停顿时间更长
2. 自适应的晋升不一定15次才晋升
3. 如果不配置默认堆内存，会先触发full gc，之后才会扩展内存，触发更多的full gc
4. 对象
   1. 对象头
      1. 标记字（1个机器字）
      2. class指针
      3. 数组长度（数组独有）
   2. 对象体
      1. 内部空白
      2. 实例数据
   3. 外部对齐
5. 对齐
   1. 内存大小需要为机器字的整数倍
6. 尽量使用原生类型，避免对象头的占用
7. 多维数组
   1. 对象数组的嵌套
   2. 可以用一维数组表示
8. String额外的24字节开销
9. 内存泄露
   1. 本来应该释放的对象无法释放

# 第六题
[代码文件](./OkhttpDemo.java)