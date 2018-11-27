/**
 * 哈弗曼结点类
 */
public class HuffNode implements Comparable<HuffNode> {
	public int value;// 结点数据
	public int weight;// 权重
	HuffNode left, right;// 左右孩子结点
	HuffNode parent;// 父亲结点

	/**
	 * 初始化结点的数据，权重，左右孩子结点与父亲结点
	 * @param v       数据
	 * @param w       权重
	 * @param lchild  左孩子结点
	 * @param rchild  右孩子结点
	 * @param pt      父亲结点
	 */
	HuffNode(int v, int w, HuffNode lchild, HuffNode rchild, HuffNode pt) {
		value = v;
		weight = w;
		left = lchild;
		right = rchild;
		parent = pt;
	}

	/**
	 * 比较两个结点的权重
	 */
	public int compareTo(HuffNode rhs) {
		return weight - rhs.weight;
	}
}