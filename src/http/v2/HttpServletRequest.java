package http.v2;

import java.util.HashMap;

public class HttpServletRequest {

	private String method;
	private String requestURL;
	private String protocol;
	private HashMap<String, String> headerMap = new HashMap<>();

	public HttpServletRequest(String content){
		//解析请求报文
		String [] lines=content.split("\r\n");
		for(int i=0;i<lines.length;i++){
			if(i==0){
				//解析投行 以空格为间隔符
				String[] topLines=lines[i].split("\\s");
				method=topLines[0];
				requestURL=topLines[1];
				protocol=topLines[2];
			}else{
				String[] headerLines=lines[i].split(":\\s");
				headerMap.put(headerLines[0],headerLines[1]);
			}
		}
	}

	public String getMethod() {
		return method;
	}

	/*public void setMethod(String method) {
		this.method = method;
	}*/

	public String getRequestURL() {
		return requestURL;
	}

	/*public void setRequestURL(String requestURL) {
		this.requestURL = requestURL;
	}*/

	public String getProtocol() {
		return protocol;
	}

	/*public void setProtocol(String protocol) {
		this.protocol = protocol;
	}*/

	public String getHeaderMap(String header) {
		return headerMap.get(header);
	}

	public void setRequestURL(String requestURL) {
		this.requestURL = requestURL;
		
	}

	public RequestDispatcher getRequestDispatcher(String webPath) {
		return new RequestDispatcher(webPath);
	}

	/*public void setHeaderMap(HashMap<String, String> headerMap) {
		this.headerMap = headerMap;
	}*/
	
	private Cookie[] cookies;
	public Cookie[] getCookies(){
		
		String cookieStr=headerMap.get("Cookie");
		
		if(cookieStr != null){
			String[] cookieItems=cookieStr.split(";");
			cookies=new Cookie[cookieItems.length];
			int i=0;
			for(String cookieItem: cookieItems){
				String [] cookieNameValue=cookieItem.split("=");
				String name=cookieNameValue[0];
				String value=cookieNameValue[1];
				cookies[i]=new Cookie(name, value);
				i++;
			}
		}
		
		
		return cookies;
	}
	
	
	
	
}
