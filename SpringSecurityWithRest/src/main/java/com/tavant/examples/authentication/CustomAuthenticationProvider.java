package com.tavant.examples.authentication;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String userName = authentication.getName();
		String password = authentication.getCredentials().toString();
		System.out.println("Username " + userName);
		if (authorizedUser(userName, password)) {
			List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
			grantedAuths.add(new GrantedAuthority() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public String getAuthority() {
					// TODO Auto-generated method stub
					return "AUTH_USER";
				}
			});
			Authentication auth = new UsernamePasswordAuthenticationToken(userName, password, grantedAuths);
			System.out.println(auth.getAuthorities());
			return auth;
		} else {
			throw new AuthenticationCredentialsNotFoundException("Invalid Credentials!");
		}
	}

	private boolean authorizedUser(String userName, String password) {
		System.out.println("username is :" + userName + " and password is " + password);
		if ("Meenakshi".equals(userName) && "Meenakshi".equals(password))
			return true;
		return false;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
}