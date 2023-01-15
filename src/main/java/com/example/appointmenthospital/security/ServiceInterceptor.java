package com.example.appointmenthospital.security;

import com.example.appointmenthospital.dto.ResultDto;
import com.example.appointmenthospital.utility.TokenUtility;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Component
public class ServiceInterceptor implements HandlerInterceptor {

	@Autowired
	private TokenUtility tokenUtility;
	@Autowired
	private ObjectMapper mapper;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("In Interceptor");
		String token = request.getHeader("token");
		ResultDto result = new ResultDto();
		Map<String, Object> resultMap = new HashMap<>();
		if (token == null || token.isEmpty()) {
			result.setStatusCode(1);
			result.setStatusDescription("Error");
			resultMap.put("token", "token cannot be null or empty,please put the token in header");
			result.setResult(resultMap);
			String finalResult = mapper.writeValueAsString(result);
			response.setStatus(401);
			response.setContentType("application/json");

			try (PrintWriter writer = response.getWriter()) {
				writer.write(finalResult);
			}
			return false;
		} else {
			ResultDto resultToken = tokenUtility.checkToken(token);
			if (resultToken.getStatusCode() == 0) {
				return true;
			} else {
				String finalResult = mapper.writeValueAsString(resultToken);
				response.setStatus(401);
				response.setContentType("application/json");

				try (PrintWriter writer = response.getWriter()) {
					writer.write(finalResult);
				}
				return false;
			}

		}

	}

}
