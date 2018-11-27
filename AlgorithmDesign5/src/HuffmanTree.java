import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.PriorityQueue;

/**
 * Huffman树编码类
 */
public class HuffmanTree {
	private CharCounter theCounts;// 字符统计类对象
	private HuffNode root;// 根结点
	private HuffNode[] theNodes = new HuffNode[BitUtils.DIFF_BYTES + 1];// 存储HuffNode的数组
	public static final int ERROR = -3;// 错误
	public static final int INCOMPLETE_CODE = -2;// 不完全的结点编码
	public static final int END = BitUtils.DIFF_BYTES;// 字节的溢出位

	/**
	 * 实例化一个字符统计类对象
	 */
	public HuffmanTree() {
		theCounts = new CharCounter();
		root = null;
	}

	/**
	 * 可以通过CharCounter对象来创建一个huffmanTree对象
	 * 
	 * @param cc
	 *          CharCounter对象
	 */
	public HuffmanTree(CharCounter cc) {
		theCounts = cc;
		root = null;
		createTree();// 创建HuffmanTree
	}

	/**
	 * 得到要寻找的字符编码所在的树结点,如果该字符不在树上，则返回null表示出错,否则,通过父亲链逆向查找,直到到达根结点
	 * 
	 * @param ch 当前结点的下标
	 * @return   结点相对的字符编码数组
	 */
	public int[] getCode(int ch) {
		HuffNode current = theNodes[ch];

		if (current == null)
			return null;
		String v = "";// 结点的编码
		HuffNode parent = current.parent;

		while (parent != null) {
			if (parent.left == current)
				v = "0" + v;// 左结点编码
			else
				v = "1" + v;// 右结点编码
			// 向下遍历
			current = current.parent;
			parent = current.parent;
		}
		int len = v.length();
		int[] result = new int[len];// 创建一个与编码相同大小数组
		for (int i = 0; i < len; i++)
			result[i] = v.charAt(i) == '0' ? 0 : 1;
		return result;
	}

	/**
	 * 获取编码对应的字符
	 * 
	 * @param code 哈弗曼编码
	 * @return 存储在结点中的值(如果结点不是叶子结点,则返回符号INCOMPLETE)
	 */
	public int getChar(String code) {
		HuffNode leaf = root;// 获取根结点
		int len = code.length();
		// 按照编码向左或向右遍历到叶子结点
		for (int i = 0; leaf != null && i < len; i++)
			if (code.charAt(i) == '0')
				leaf = leaf.left;
			else
				leaf = leaf.right;
		// 根结点为空
		if (leaf == null)
			return ERROR;
		return leaf.value;
	}

	/**
	 * 写入编码表的方法
	 * 
	 * @param out
	 *          写入的数据流
	 * @throws IOException
	 */
	public void writeEncodingTable(DataOutputStream out) throws IOException {
		for (int i = 0; i < BitUtils.DIFF_BYTES; i++) {
			if (theCounts.getCount(i) > 0) {
				out.writeByte(i);// 将字节写入（通常是文件）
				out.writeInt(theCounts.getCount(i));// 将字节出现的次数写入（通常是文件）
			}
		}
		// 最后写入0表示编码的结束
		out.writeByte(0);
		out.writeInt(0);
	}

	/**
	 * 读取编码表的方法
	 * 
	 * @param in
	 *          数据输入流对象
	 * @throws IOException
	 */
	public void readEncodingTable(DataInputStream in) throws IOException {
		for (int i = 0; i < BitUtils.DIFF_BYTES; i++)
			theCounts.setCount(i, 0);
		byte ch;
		int num;
		for (;;) {
			ch = in.readByte();// 读到的字节
			num = in.readInt();// 字符出现的次数
			if (num == 0)// 如果读到0表示编码表的结束
				break;
			theCounts.setCount(ch, num);
		}
		createTree();// 创建HuffmanTree
	}

	/**
	 * 构造哈弗曼编码树的方法
	 */
	public void createTree() {
		// 创建一个优先队列来保存HuffNode
		PriorityQueue<HuffNode> pq = new PriorityQueue<HuffNode>();

		for (int i = 0; i < BitUtils.DIFF_BYTES; i++) {
			if (theCounts.getCount(i) > 0) {// 如果某一个字节出现过
				HuffNode newNode = new HuffNode(i, theCounts.getCount(i), null, null, null);
				theNodes[i] = newNode;
				pq.add(newNode);// 将新结点添加到队列中
			}
		}

		theNodes[END] = new HuffNode(END, 1, null, null, null);
		pq.add(theNodes[END]);
		// 当剩余的结点多于一个时
		while (pq.size() > 1) {
			// 每次取出当前最小的两个结点，PriorityQueue默认的排序是升序
			HuffNode n1 = pq.remove();// remove方法与poll方法的唯一不同之处在于:此队列为空时将抛出一个异常
			HuffNode n2 = pq.remove();
			// 将最小的两个结点链接形成新结点
			HuffNode result = new HuffNode(INCOMPLETE_CODE, n1.weight + n2.weight,
					n1, n2, null);
			n1.parent = n2.parent = result;
			// 将新结点添加到队列当中
			pq.add(result);
		}
		root = pq.element();// 根结点就是队列中的最后一个结点
	}
}