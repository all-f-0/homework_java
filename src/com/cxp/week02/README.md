[TOC]
# 第四题
## Serial GC 串行GC
1. 使用单线程进行垃圾回收
2. 年轻代使用mark-copy算法
3. Serial GC Old 使用mark-sweep-compact
4. full gc仅清理老年代
5. 已经很少使用
6. 基于响应时间的设计

## ParNew GC
1. Serial GC的并行版本
2. 仅适用于年轻代
3. 使用mark-copy算法
4. 往往配合CMS使用
5. 基于响应时间的设计

## Parallel GC
1. 并行GC，可以充分利用多核CPU，降低STW时间
2. 年轻代使用mark-copy Parallel scavenge
3. 老年代使用mark-sweep-compact
4. full gc会清理年轻代和老年代
5. JDK8的默认GC
6. 基于吞吐量的设计

## CMS GC
1. 并发GC，用于老年代的垃圾回收
2. 使用mark-sweep算法，不进行compact，使用freelist来保证内存分配的效率
3. 清理过程
   1. Initial Mark：标记GC Roots可达的第一层对象和跨区的引用，进入STW
   2. Concurrent Mark：并发标记
   3. Concurrent Preclean：并发预清理
   4. Final remark：最终标记