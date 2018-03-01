package com.tavant.examples.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class CustomSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws ServletException, IOException {
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
		System.out.println("authentication successful!");
	}
}
