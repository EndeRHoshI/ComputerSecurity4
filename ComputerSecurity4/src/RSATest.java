import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Scanner;

public class RSATest {
	public static void main(String[] arg) throws IOException {
		
		Scanner mScanner = new Scanner(System.in);
		
		System.out.println("加密前创建一个cleartext.txt文件于E:\\ComputerSecurity4目录下，解密前将加密得");
		System.out.println("到的Keys.txt文件放置于E:\\ComputerSecurity4目录下，然后选择加解密操作：");
		System.out.println("选择想要进行的操作：1、加密    2、解密");
		switch (mScanner.nextInt()){
			case 1:
			  // 生成KeyPair
				RsaKeyPair keyPair = RSAGeneratorKey.generatorKey(512);
				try {
		    	/* 读入TXT文件 */  
		      String pathname = "E:\\ComputerSecurity4\\cleartext.txt";
		      File filename = new File(pathname); // 要读取以上路径的express.txt文件  
		      InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader  
		      BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言  
		      String source = "";  
		      source = br.readLine(); 
		      
		      System.out.println("加密前:" + source);
		  		// 使用公钥加密
		  		String cryptdata = RSAUtil.encrypt(source, keyPair.getPublicKey(), "UTF-8");
		  		System.out.println("加密后:" + cryptdata);
	
		      /* 写入Txt文件 */  
		      File ciphertext = new File("E:\\ComputerSecurity4\\ciphertext.txt"); // 相对路径，如果没有则要建立一个新的ciphertext.txt文件
		      File Keys = new File("E:\\ComputerSecurity4\\Keys.txt"); 
		      ciphertext.createNewFile(); // 创建新文件  
		      Keys.createNewFile();
		      BufferedWriter out = new BufferedWriter(new FileWriter(ciphertext));  
		      BufferedWriter KeyOut = new BufferedWriter(new FileWriter(Keys));  
		      KeyOut.write(keyPair.getPublicKey().getN().toString());
		      KeyOut.write('\n');
		      KeyOut.write(keyPair.getPrivateKey().getA().toString());
		      KeyOut.write('\n');
		      KeyOut.write(keyPair.getPublicKey().getB().toString());
		      KeyOut.flush();
		      KeyOut.close(); 
		      out.write(cryptdata);
		      out.flush();
		      out.close(); 
			  }catch (Exception e) {
		      e.printStackTrace();  
		    }
				break;
			case 2:
				// 使用私钥解密
				try {
					/* 读入TXT文件 */  
		      String pathname = "E:\\ComputerSecurity4\\ciphertext.txt";
		      File filename = new File(pathname); // 要读取以上路径的express.txt文件  
		      InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader  
		      BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言  
		      String cryptdata = "";  
		      cryptdata = br.readLine(); 
		      
		      pathname = "E:\\ComputerSecurity4\\Keys.txt";
		      filename = new File(pathname); // 要读取以上路径的express.txt文件  
		      reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader  
		      br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言  
		      BigInteger N = new BigInteger(br.readLine());
		      BigInteger A = new BigInteger(br.readLine());
		      
					String result = RSAUtil.decrypt(cryptdata, A, N,
							"UTF-8");
					System.out.println("解密后:" + result);
					//System.out.println(result.equals(source));
					br.close();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			break;
		}
		mScanner.close();
	}
}