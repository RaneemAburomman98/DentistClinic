package com.example.appointmenthospital.utility;

import com.example.appointmenthospital.dto.ResultDto;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtility {
	public final Integer expDate = 300000; // 5 minutes
	public final String keySign = "DenSystem";

	public String generateToken(String username) {

		Map<String, Object> test = new HashMap<String, Object>();
		test.put("username", username);

		 Date convertedDatetime = new Date(System.currentTimeMillis() + expDate);

		return Jwts.builder().setClaims(test)
				.setExpiration(convertedDatetime)
				.signWith(SignatureAlgorithm.HS512, keySign).compact();
	}

	public ResultDto checkToken(String token) {
		ResultDto result = new ResultDto();
		try {
			Claims s = Jwts.parser().setSigningKey(keySign).parseClaimsJws(token).getBody();
			result.setStatusCode(0);
			result.setStatusDescription("Successful");
			String username = (String) s.get("username");
			result.setResult(username);
			return result;
		} catch (SignatureException ex) {
			result.setStatusCode(1);
			result.setStatusDescription("Error: Invalid JWT signature");
			return result;

		} catch (MalformedJwtException ex) {
			result.setStatusCode(1);
			result.setStatusDescription("Error: Invalid JWT toekn");
			return result;

		} catch (ExpiredJwtException ex) {
			result.setStatusCode(1);
			result.setStatusDescription("Error: Expired JWT token");
			return result;

		} catch (UnsupportedJwtException ex) {
			result.setStatusCode(1);
			result.setStatusDescription("Error: Unsupported JWT token");
			return result;

		}

		catch (IllegalArgumentException ex) {
			result.setStatusCode(1);
			result.setStatusDescription("Error: JWT String is empty or has an illegal argument");
			return result;

		} catch (Exception e) {
			result.setStatusCode(1);
			result.setStatusDescription("Error");
			return result;
		}

	}
}
