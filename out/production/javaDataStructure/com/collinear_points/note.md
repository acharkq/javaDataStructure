# 作业说明

这份作业并非满分作业
作业要求为运行时间~O(n^2*logn)
我写的是~O(n^3)
解决方案有思路，去掉

```JAVA
for (int k = 0; k < i; k++)
    if (points[i].slopeTo(points[k]) == points[i.slopeTo(points[j])) {
        exit = true;
        break;
    }
```

以及

```JAVA
    if (exit)
        continue;
```

几行代码，退出最外层循环后，重新对linesegment进行排序，注意此处排序需要现将linesegment extend为compatible类，写两个排序方式，在排序中过滤掉被包含的linesegment，最后再作为linesegment返回

## 为什么没直接实现

- 懒
- 这个实现起来其实还是有几点需要好好想一想的
- 这个方法其实有些麻烦的，应该会有更好的实现的方法，但网上一直没找到比我的代码更好的解决方案，唉:-(
- 确实最近比较忙
- 这个代码毕竟还是得了99分的