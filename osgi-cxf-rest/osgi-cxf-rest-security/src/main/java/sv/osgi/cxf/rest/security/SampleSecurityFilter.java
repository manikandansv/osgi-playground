package sv.osgi.cxf.rest.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

@Component //
(//		
		enabled = true,
		immediate = true,
		service = javax.servlet.Filter.class, property = //
		{ "org.apache.cxf.httpservice.filter=true", "servletNames=none" }//
)
public class SampleSecurityFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		System.out.println("Destroy");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("Query String: "+((HttpServletRequest) request).getQueryString());
		if ("secure".equals(((HttpServletRequest) request).getQueryString())) {
			//LOG.info("Access granted");
			chain.doFilter(request, response);
		} else {
	//		LOG.warn("Access denied");
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN);
		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("Init");
	}

}
