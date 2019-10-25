package http.v2;

import java.io.PrintWriter;

public class CookieServlet extends HttpServlet{

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		PrintWriter pw=response.getWriter();
		pw.write("<h1>测试cookie</h1>");
		
		Cookie cookie=new Cookie("username","zhangsan");
		response.addCookie(cookie);
		
		cookie=new Cookie("level", "1000");
		cookie.setMaxAge(60*60);
		response.addCookie(cookie);
		
		cookie=new Cookie("page", "1");
		cookie.setPath("/page");
		response.addCookie(cookie);
		
		cookie=new Cookie("user", "test");
		cookie.setPath("/user");
		response.addCookie(cookie);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		
	}

	
	
}
