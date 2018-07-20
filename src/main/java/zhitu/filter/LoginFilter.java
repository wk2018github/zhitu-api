package zhitu.filter;

import javax.servlet.*;
import java.io.IOException;
import java.util.Enumeration;

public class LoginFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        StringBuilder sb = new StringBuilder();
        Enumeration<String> paramNames = request.getParameterNames();
        while(paramNames.hasMoreElements()){
            String paramName = paramNames.nextElement();
            sb.append(paramName).append(":").append("\t").append(request.getParameter(paramName)).append("\n");
        }
        System.out.println(sb);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
