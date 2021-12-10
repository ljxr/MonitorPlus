package com.enjoyor.common.utils;

import java.util.List;

import org.apache.shiro.session.Session;

import com.enjoyor.common.constant.AuthConst;
import com.enjoyor.modules.sys.shiro.ShiroUtils;

public class RegionUtils {

	public static List<String> region() {
		Session session = ShiroUtils.getSession();
		String token = (String) session.getAttribute(AuthConst.TOKEN);
		List<String> list = SessionStorage.INSTANCE.get3(token);

		return list;
	}

}
