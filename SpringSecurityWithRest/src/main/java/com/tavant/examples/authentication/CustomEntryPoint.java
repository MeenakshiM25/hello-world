package com.tavant.examples.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class CustomEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {
		response.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
		response.addHeader("Access-Control-Expose-Headers", "true");
		response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
		response.addHeader("Access-Control-Max-Age", "3600");

		if (request.getMethod().equals("OPTIONS")) {
			System.out.println("Inside Options");
			response.addHeader("Access-Control-Allow-Origin", "*");
			response.setStatus(HttpServletResponse.SC_OK);
			return;
		}

		String originHeader = request.getHeader("Origin");
		System.out.println("Request Method" + request.getMethod());
		System.out.println("Origin Header" + originHeader);
		response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
		response.setHeader("Access-Control-Allow-Credentials", "true");

		System.out.println("Entering commence");
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
	}

}
