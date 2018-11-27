import java.io.IOException;
import java.io.InputStream;

/**
 * 字符统计类,获取输入流（通常是文件）中所含的字符数 8位字节认为是ASCII字符
 */
public class CharCounter {
	// 字节的下标表示字节的种类，对应的值表示出现的次数
	private int theCounts[] = new int[BitUtils.DIFF_BYTES];// 字节的种类总共有256种

	/**
	 * 默认的无参的构造方法
	 */
	public CharCounter() {

	}

	/**
	 * 封装了基本的InputStream,读取数据并进行字符的频次统计
	 * 
	 * @param input
	 *          InputStream对象
	 * @throws IOException
	 */
	public CharCounter(InputStream input) throws IOException {
		int ch;// 读到的字节
		// 一直读到文件的末尾，每一种byte出现了多少次
		while ((ch = input.read()) != -1) {
			theCounts[ch]++;
		}
	}

	/**
	 * 获取该字符统计数组的某一个字符出现的次数
	 * 
	 * @param ch
	 *          数组下标
	 * @return 该下标位置字符出现的次数
	 */
	public int getCount(int ch) {
		return theCounts[ch & 0xff];
	}

	/**
	 * 设置某一个字符出现的次数
	 * 
	 * @param ch
	 *          数组下标
	 * @param count
	 *          字符出现次数
	 */
	public void setCount(int ch, int count) {
		theCounts[ch & 0xff] = count;
	}
	
	public void print(){
		int index = 0;
		while(index < BitUtils.DIFF_BYTES){
			if(theCounts[index] != 0){
				System.out.println((char)index + " " + theCounts[index]);
			}
			index ++;
		}
	}
}