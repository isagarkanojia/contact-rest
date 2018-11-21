package com.contact.api.util;

import org.apache.commons.codec.binary.Base64;
import org.springframework.util.StringUtils;

import com.unboundid.util.json.JSONException;
import com.unboundid.util.json.JSONObject;

public class AuthUtils {

	public static Long getUserIdFromToken(String authHeader) {
		String jwtToken = "";
		if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer:")) {
			jwtToken = authHeader.substring(7, authHeader.length());
		}

		String[] split_string = jwtToken.split("\\.");
		String base64EncodedBody = split_string[1];
		Base64 base64Url = new Base64(true);
		String body = new String(base64Url.decode(base64EncodedBody));
		JSONObject object = null;
		try {
			object = new JSONObject(body);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return Long.parseLong(object.getField("sub").toString().replace("\"", ""));
	}

}
