package http.v2;

public class RequestDispatcher {

	public String webPath;
	
	public RequestDispatcher(String webpath){
		this.webPath=webpath;
	}
	
	public void forward(HttpServletRequest request,HttpServletResponse response){
		request.setRequestURL(webPath);
	}
}
