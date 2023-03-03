package com.surya.api;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.AbstractFileResolvingResource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.surya.exception.CustomerException;


@Component
public class ApiFilter extends OncePerRequestFilter{
	
	@Autowired
	ObjectMapper obj;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// lets add one response header and test 
		response.setHeader("testt_header", "here is my header.suryakanta acharya");
		// now sure gonna work
		
		// here is the catch
		
		// lol
		// working perfectly
		// will put some validation to test
//		can't validate any object value.
//		only headers can be validated
//		let's do by adding some random header
		try {
			validateTestHeader(request);
			filterChain.doFilter(request, response);

		} catch (CustomerException e) {
			// TODO Auto-generated catch block
			// = new ObjectMapper();
			//let's autowire this
			
			response.getWriter().write(obj.writeValueAsString(e.getMessage()));
			//response.setStatus(500);
			// everything seems fine but why this is coming
		}
	}

	private void validateTestHeader(HttpServletRequest request) throws CustomerException {
		// TODO Auto-generated method stub
		
		String test = request.getHeader("test");
		
		// seems something is still fishy
		// some glitch ?!!
		// lets use stringutils
		// copied dependency from mvn repo
		// worked this time
		if(!StringUtils.equals(test, "surya")) {
			throw new CustomerException("invalid header");
		}
		//let's debug
		// not yet working
		// implemented on this
		//showing perfectly in console
		//time to set in response and put it in postman
		
		
		// worked !
		// =! is for babies not for professionals
		// felling sleepy bye 
		
		
	}

}
