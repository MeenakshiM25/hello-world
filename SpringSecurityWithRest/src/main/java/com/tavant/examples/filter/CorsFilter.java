package com.tavant.examples.filter;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
@Component
public class CorsFilter implements   Filter {

	 //below properties need to be moved to property file
	 private static final String  ACCESS_CONTROL_ALLOW_ORIGIN = "*";
	 private static final String  ACCESS_CONTROL_ALLOW_METHODS = "GET,POST,PUT,DELETE";
	 private static final String  ACCESS_CONTROL_ALLOW_HEADERS = "Origin, X-Requested-With, Content-Type, Accept, Authorization";
	 private static final String  ACCESS_CONTROL_MAX_AGE = "3600";
	 String[] allowOrigins = {"http://run.plnkr.co","http://localhost:4200","http://localhost:8094","https://secure-development.53.com","https://secure-qa.53.com","https://secure-staging.53.com","https://www.53.com","https://secure.53.com"};  
	 
	

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void doFilter(ServletRequest req, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse resp = (HttpServletResponse) response;
		 resp.addHeader("Access-Control-Allow-Methods",ACCESS_CONTROL_ALLOW_METHODS);
		    resp.addHeader("Access-Control-Allow-Headers",ACCESS_CONTROL_ALLOW_HEADERS);
		    resp.addHeader("Access-Control-Max-Age", ACCESS_CONTROL_MAX_AGE);
			resp.addHeader("Access-Control-Expose-Headers","true");
	       String originHeader = request.getHeader("Origin");
		    System.out.println("Request Method"+ request.getMethod());
		    System.out.println("Origin Header"+originHeader);
	     
		    // Just ACCEPT and REPLY OK if OPTIONS
		    if (request.getMethod().equals("OPTIONS") ) {
		    	  System.out.println("Inside Options");
		    	  resp.addHeader("Access-Control-Allow-Origin",ACCESS_CONTROL_ALLOW_ORIGIN);
		        resp.setStatus(HttpServletResponse.SC_OK);
		        return ;
		    }
		    
	       if(null != originHeader && !originHeader.equalsIgnoreCase("null")){
		        for(String origin : allowOrigins){
		            if(originHeader.endsWith(origin))
		            	resp.setHeader("Access-Control-Allow-Origin", originHeader);
		            break;
		        }
	       }else{
	       	resp.setHeader("Access-Control-Allow-Origin", ACCESS_CONTROL_ALLOW_ORIGIN);
	       }
		filterChain.doFilter(request, resp);
	}




	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
}
