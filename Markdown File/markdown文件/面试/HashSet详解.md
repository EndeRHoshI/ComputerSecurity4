首先回答面试问题中关于HashSet的内容：
#### HashSet是如何实现的，里面的数据是怎么存储的，HashSet和HashMap有什么区别啊
* HashSet概述：实现Set接口，由哈希表（实际上是一个HashMap实例）支持。它不保证set 的迭代顺序；特别是它不保证该顺序恒久不变。此类允许使用null元素。
  * 对于HashSet而言，它是基于HashMap实现的，HashSet底层使用HashMap来保存所有元素，因此HashSet 的实现比较简单，相关HashSet的操作，基本上都是直接调用底层HashMap的相关方法来完成。
  * Java1.8源码：http://docs.oracle.com/javase/8/docs/api/
* HashSet的实现原理总结如下：
  1. 是基于HashMap实现的，默认构造函数是构建一个初始容量为16，负载因子为0.75 的HashMap。封装了一个 HashMap 对象来存储所有的集合元素，所有放入 HashSet 中的集合元素实际上由 HashMap 的 key 来保存，而 HashMap 的 value 则存储了一个 PRESENT，它是一个静态的 Object 对象。
  2. 当我们试图把某个类的对象当成 HashMap的 key，或试图将这个类的对象放入 HashSet 中保存时，重写该类的equals(Object obj)方法和 hashCode() 方法很重要，而且这两个方法的返回值必须保持一致：当该类的两个的 hashCode() 返回值相同时，它们通过 equals() 方法比较也应该返回 true。通常来说，所有参与计算 hashCode() 返回值的关键属性，都应该用于作为 equals() 比较的标准。
  3. HashSet的其他操作都是基于HashMap的。

          public class HashSet<E> extends AbstractSet<E> implements Set<E>, Cloneable, java.io.Serializable {   
            // 使用 HashMap 的 key 保存 HashSet 中所有元素  
            private transient HashMap<E,Object> map;

            // 定义一个虚拟的 Object 对象作为 HashMap 的 value   
            private static final Object PRESENT = new Object();   
            ...   
            // 初始化 HashSet，底层会初始化一个 HashMap   
            public HashSet() {   
              map = new HashMap<E,Object>();   
            }   
            // 以指定的 initialCapacity、loadFactor 创建 HashSet   
            // 其实就是以相应的参数创建 HashMap   
            public HashSet(int initialCapacity, float loadFactor) {   
              map = new HashMap<E,Object>(initialCapacity, loadFactor);   
            }   
            public HashSet(int initialCapacity){   
              map = new HashMap<E,Object>(initialCapacity);   
            }   
            HashSet(int initialCapacity, float loadFactor, boolean dummy){   
              map = new LinkedHashMap<E,Object>(initialCapacity, loadFactor);   
            }   
            // 调用 map 的 keySet 来返回所有的 key   
            public Iterator<E> iterator() {   
              return map.keySet().iterator();   
            }   
            // 调用 HashMap 的 size() 方法返回 Entry 的数量，就得到该 Set 里元素的个数  
            public int size() {   
              return map.size();   
            }   
            // 调用 HashMap 的 isEmpty() 判断该 HashSet 是否为空，  
            // 当 HashMap 为空时，对应的 HashSet 也为空  
            public boolean isEmpty() {   
              return map.isEmpty();   
            }   
            // 调用 HashMap 的 containsKey 判断是否包含指定 key   
            //HashSet 的所有元素就是通过 HashMap 的 key 来保存的  
            public boolean contains(Object o) {   
              return map.containsKey(o);   
            }   
            // 将指定元素放入 HashSet 中，也就是将该元素作为 key 放入 HashMap   
            public boolean add(E e) {   
              return map.put(e, PRESENT) == null;   
            }   
            // 调用 HashMap 的 remove 方法删除指定 Entry，也就删除了 HashSet 中对应的元素  
            public boolean remove(Object o) {   
              return map.remove(o)==PRESENT;   
            }   
            // 调用 Map 的 clear 方法清空所有 Entry，也就清空了 HashSet 中所有元素  
            public void clear() {   
              map.clear();   
            }   
            ...   
          }
* 由上面源程序可以看出，HashSet的实现其实非常简单，它只是封装了一个HashMap对象来存储所有的集合元素，所有放入HashSet中的集合元素实际上由HashMap的key来保存，而HashMap的 value则存储了一个PRESENT，它是一个静态的Object对象。
* HashSet的绝大部分方法都是通过调用HashMap的方法来实现的，因此HashSet和HashMap两个集合在实现本质上是相同的。所以更深入的内容可以继续查看HashMap的相关实现。

## 补充
HashSet是Set接口的典型实现，大多数时候使用Set集合时就是使用这个实现类。HashSet按Hash算法来存储集合的元素，因此具有很好的存取和查找性能。HashSet具有以下的特点：
1. 不能保证元素的排列顺序，顺序可能与添加顺序不同，顺序也有可能发生变化。
2. HashSet不是同步的，如果多个线程同时访问一个HashSet，假设有两个或者以上的线程同时修改了HashSet集合时，则必须通过代码来保证其同步。
3. 集合元素值可以是null。

当向HashSet集合中存入一个元素时，HashSet会调用该对象的hashCode()方法来得到该对象的hashCode值，然后根据该hashCode值决定该对象在HashSet中的存储位置。如果有两个元素通过equals()方法比较返回true，但他们的hashCode()方法返回值不相等，HashSet将会把它们存储在不同的位置，依然可以添加成功。

那么HashSet集合判断两个元素相等的标准是两个对象通过equals()方法比较相等，并且两个对象的hashCode()方法返回值也相等。(《疯狂java讲义》P281例子)

当把一个对象放入HashSet中时，如果需要重写该对象对应类的equals()方法，则也应该重写其hashCode()方法。规则是：如果两个对象通过equals()方法比较返回true，那么这两个对象的hashCode值也应该相同，

以下两种情况需注意：
* 如果两个对象通过equals()方法比较返回true但是hashCode()方法返回的值不同的话，这将导致HashSet会把这两个对象保存在Hash表的不同位置，从而使两个对象都可以添加成功，但是这样就与Set集合的规则有出入了。
* 如果两个对象通过hashCode()方法返回的hashCode值相同，但是他们通过equals()方法返回false的话就更加麻烦了，因为两个对象的hashCode值相同，HashSet将试图把他们保存在同一个位置，但是又不行（否则将只剩下一个对象了），所以实际上会在这个位置使用链式结构来保存多个对象；而HashSet访问集合元素时也是根据元素的hashCode值来快速定位的，如果HashSet中两个以上的元素具有相同的hashCode值，将会导致性能下降。

hash能保证通过一个对象快速查找到另一个对象。hash算法的价值在于速度，它可以保证查询被快速执行。当需要查询集合中某个元素时，hash算法可以直接根据该元素的值计算出该元素的存储位置，从而可以让程序快速定位该元素。表面上HashSet集合里的元素都没有索引，实际上当程序向HashSet中添加元素时，HashSet会根据该元素的hashCode值来计算它的存储位置，也就是说每个元素的hashCode值就可以决定它的存储“索引”。

为什么不直接使用数组呢，因为数组元素的索引是连续的，而且数组的长度是固定的。而HashSet就不一样了，HashSet采用每个元素的hashCode值来计算其索引，从而可以自由增加HashSet的长度，并可以根据hashCode值访问元素。所以当从HashSet中访问元素时，HashSet先计算该元素的hashCode值，然后直接到该hashCode值对应的位置去取出元素，这就是HashSet速度很快的原因。

一个HashSet示例：http://alex09.iteye.com/blog/539549

关于bucket和负载因子的说明：http://blog.csdn.net/wenyiqingnianiii/article/details/52204136

还应注意：当向HashSet中添加可变对象时，应当十分小心。如果修改HashSet集合中的对象，有可能导致该对象与集合中的其他对象相等，但是hashCode没有重新改变，从而导致HashSet无法准确访问该对象。

HashMap深度解析(一)：http://blog.csdn.net/ghsau/article/details/16843543

HashMap深度解析(二)：http://blog.csdn.net/ghsau/article/details/16890151

深入Java集合学习系列——HashMap的实现原理：http://zhangshixi.iteye.com/blog/672697
