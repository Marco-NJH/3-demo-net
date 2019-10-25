package http.v2;

import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.CookieHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class HttpServletResponse {

	//web解析器
	private static WebXmlParser webXmlParser=new WebXmlParser("web.xml");
	private HttpServletRequest request;
	private OutputStream out;
	
	//状态码字段
	private int status=200;
	//状态的描述信息
	private String message="OK";
	
	private HashMap<String, String> headerMap=new HashMap<>();
	
	public HttpServletResponse(HttpServletRequest request,OutputStream out){
		super();
		this.request=request;
		this.out=out;
	}
	
	//提交方法
	public void commit() throws IOException{
		String suffix=request.getRequestURL().substring(request.getRequestURL().lastIndexOf(".")+1);
		
		if(headerMap.containsKey("Content-Type")==false){
			String contentType = webXmlParser.getContentType(suffix);
			//设置响应类型
			setContentType(contentType);
		}
		
		String responseStr="HTTP/1.1"+"status"+"message"+"\r\n";
		for(Entry<String,String> entry: headerMap.entrySet()){
			responseStr+=entry.getKey()+":"+entry.getValue()+"\r\n";
		}
		
		//写入cookie数据
		for(Cookie cookie: cookies){
			responseStr+=cookie+"\r\n";
		}
				responseStr+="\r\n";//CRLF空行
				
		
		out.write(responseStr.getBytes());
		
		
		//响应重定向不需要写body
		if(status<300 || status>399){
			if(caw.toString().isEmpty()){
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
				byte[] buf=new byte[1024];
				int count;
				while((count=fis.read(buf))>0){
					out.write(buf, 0, count);
				}
				fis.close();
			}else{
				out.write(caw.toString().getBytes());
			}
			
		}
		
		
	}
	
	public void setStatus(int status, String message) {

		this.status = status;

		this.message = message;

	}



	/**

	 * 响应重定向

	 * @param webPath

	 */

	public void sendRedirect(String webPath) {

		/**

		 * 响应结果码： 

		 * 	1xx 接受请求，继续处理

		 * 	2xx	正常响应 200

		 * 	3xx	响应重定向 301 302

		 * 	4xx 浏览器端错误 404  405

		 * 	5xx 服务器端错误

		 */

		this.setStatus(301, "Redirect");

		this.addHeader("Location", webPath);

		

	}

	

	public void addHeader(String key, String value){

		this.headerMap.put(key, value);

	}

	

	public void setContentType(String contentType){

		this.headerMap.put("Content-Type", contentType);

	}
	
	/**

	 * 如何定义   PrintWriter ，在commit 要考虑  和 文件输出的配合问题

	 * @return

	 */

	

	CharArrayWriter caw = new CharArrayWriter();

	PrintWriter pw = new PrintWriter(caw);

	public PrintWriter getWriter() {

		return pw;

	}
	
	private List<Cookie> cookies=new ArrayList<Cookie>();
	
	public void addCookie(Cookie cookie){
		cookies.add(cookie);
	}


}
