package http;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Processer {

	public void process(Socket socket){
		InputStream in;
		OutputStream out;
		try {
			in=socket.getInputStream();
			out=socket.getOutputStream();
			byte[] buf=new byte[1024];
			int count;
			count=in.read(buf);
			String content=new String(buf,0,count);
			System.out.println(content);
			//解析请求报文
			HttpServletRequest request=parseRequest(content);
			
			String suffix=request.getRequestURL().substring(request.getRequestURL().lastIndexOf(".")+1);
			String contentType;
			
			switch(suffix){
			case "js":
				contentType="application/x-javascript";
				break;
			case "css":
				contentType="text/css";
				break;
			case "jpg":
				contentType="image/jpeg";
				break;
			case "bmp":
				contentType="image/bmp";
				break;
			case "gif":
				contentType="image/gif";
				break;
			case "png":
				contentType="image/png";
				break;
				default:
					contentType="text/html";
			}
			
			
			
			String responseStr="HTTP/1.1 200 OK\r\n";
			responseStr+="Content-Type: "+contentType+"\r\n";
					responseStr+="\r\n";//CRLF空行
					//responseStr+="<h1>hello world</h1>";
			
			out.write(responseStr.getBytes());
			String rootPath="E:/Tomcat/webapps/photo";
			String filePath=request.getRequestURL();
			//判断访问文件是否存在
			String diskPath=rootPath + filePath;
			if(new File(diskPath).exists()==false){
				diskPath=rootPath+"/404.html";
			}
			FileInputStream fis=new FileInputStream(diskPath);
			
			//根据请求的路径返回对应的文件html
			//向浏览器发送报文
			while((count=fis.read(buf))>0){
				out.write(buf, 0, count);
			}
			fis.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public HttpServletRequest parseRequest(String content){
		HttpServletRequest request=new HttpServletRequest(content);
		return request;
	}

}
