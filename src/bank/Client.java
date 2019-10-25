package bank;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Scanner;

public class Client {

	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		new Client().start();
		
		/*Scanner sc=new Scanner(System.in);
		Socket server=new Socket("172.20.80.186",8888);
		System.out.println("成功连接服务器");
		InetAddress addr=server.getInetAddress();
		System.out.println("服务器端的主机地址："+addr.getHostAddress());
		System.out.println("服务器的IP地址:"+Arrays.toString(addr.getAddress()));
		
		OutputStream out=server.getOutputStream() ;

		DataOutputStream dos=new DataOutputStream(out);
		
		dos.writeUTF("diposit");
		
		dos.writeUTF("54168148498649684");
		dos.writeFloat(1200);
		
		dos.flush();
		System.out.println("客户暂停操作");
		Thread.sleep(20000);
		
		server.close();
		sc.close();*/
	}

	private Scanner sc=new Scanner(System.in);
	DataInputStream dis;
	DataOutputStream dos;
	public void start() throws UnknownHostException, IOException {
		Socket server=new Socket("172.20.80.186",8888);
		System.out.println("成功连接服务器");
		InetAddress addr=server.getInetAddress();
		System.out.println("服务器端的主机地址："+addr.getHostAddress());
		System.out.println("服务器的IP地址:"+Arrays.toString(addr.getAddress()));
		
		InputStream in = server.getInputStream();
		OutputStream out = server.getOutputStream();
		dis=new DataInputStream(in);
		dos=new DataOutputStream(out);
		
		boolean running=true;
		while(running){
			System.out.println("********************");
			System.out.println("        1.开户                   ");
			System.out.println("        2.存款                  ");
			System.out.println("        3.取款                   ");
			System.out.println("        4.转账                   ");
			System.out.println("        0.退出                   ");
			System.out.println("********************");
			System.out.print("请输入：");
			String command=sc.nextLine();
			switch(command){
			case "1":
				register();
				break;
			case "2":
				diposit();
				break;
			case "3":
				withdraw();
				break;
			case "4":
				transfer();
				break;
			case "0":
				System.out.println("byby");
				running=false;
			}
		}
		server.close();
		sc.close();
	}
	public void transfer() {
		// TODO Auto-generated method stub
		
	}
	public void withdraw() {
		// TODO Auto-generated method stub
		
	}
	public void diposit() throws IOException {
		System.out.println("请输入用户账户：");
		String cardno=sc.nextLine();
		System.out.println("请输入存款金额：");
		float money=sc.nextFloat();
		dos.writeUTF("diposit");
		dos.writeUTF(cardno);
		dos.writeFloat(money);
		dos.flush();
		String ret=dis.readUTF();
		System.out.println(ret);
		
	}
	public void register() throws IOException {
		System.out.println("请输入银行卡账号：");
		String cardno=sc.nextLine();
		dos.writeUTF("register");
		dos.writeUTF(cardno);
		dos.flush();
		String ret=dis.readUTF();
		System.out.println(ret);
		
	}
}
