package com.yc.url;

import java.io.FileInputStream;
import java.io.FileOutputStream;
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
		Scanner sc=new Scanner(System.in);
		Socket server=new Socket("172.20.80.186",8888);
		System.out.println("成功连接服务器");
		InetAddress addr=server.getInetAddress();
		System.out.println("服务器端的主机地址："+addr.getHostAddress());
		System.out.println("服务器的IP地址:"+Arrays.toString(addr.getAddress()));
		
		InputStream in=server.getInputStream();
		OutputStream out=server.getOutputStream();
		
		Thread t1=new Thread(){
			public void run(){
				boolean running=true;
				while(running){
					System.out.print("我说");
					String msg=sc.nextLine();
					try {
						out.write(msg.getBytes());
						//文件：e:/a.txt
						if(msg.startsWith("文件:")){
							String filename=msg.substring("文件:".length());
							sendFile(out,filename);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		t1.start();
		
		Thread t2=new Thread(){
			public void run(){
				boolean running=true;
				while(running){
					try {
						byte[] buf=new byte[1024];
						int count=in.read(buf);
						String msg=new String(buf,0,count);
						//文件：e:/a.txt
						if(msg.startsWith("文件:")){
							String filename=msg.substring("文件:".length());
							filename=filename.substring(filename.lastIndexOf("/")+1);
							saveFile(in,filename);
						}else{
							System.out.println("服务器说："+new String(buf,0,count));
						}
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			
			}
		};
		t2.start();
		
		t1.join();
		t2.join();
		sc.close();
		server.close();
	}
	
	
	static void saveFile(InputStream in,String filename) throws IOException{
		FileOutputStream fos=new FileOutputStream("d:/"+filename);
		try{
			byte[] buf=new byte[1024];
			int count;
			while((count=in.read(buf))>0){
				fos.write(buf,0,count);
			}
		}finally{
			fos.close();
		}
	}
	
	static void sendFile(OutputStream out,String filename) throws IOException{
		FileInputStream fis=new FileInputStream(filename);
		try {
			byte[] buf=new byte[1024];
			int count;
			while((count=fis.read(buf))>0){
				out.write(buf,0,count);
			}
		} finally {
			fis.close();
		}
		System.out.println("文件发送成功:"+"d:/"+filename);
	}
}
