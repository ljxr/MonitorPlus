/**
 * 生成UUID
 * @param len 长度
 * @param radix 位数
 * @returns
 */
function uuid(len, radix) {
	var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
	var uuid = [], i;
	radix = radix || chars.length;
	if (len) {
		// Compact form
		for (i = 0; i < len; i++)
			uuid[i] = chars[0 | Math.random() * radix];
	} else {
		// rfc4122, version 4 form
		var r;
		// rfc4122 requires these characters
		uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
		uuid[14] = '4';
		// Fill in random data. At i==19 set the high bits of clock sequence as
		// per rfc4122, sec. 4.1.5
		for (i = 0; i < 36; i++) {
			if (!uuid[i]) {
				r = 0 | Math.random() * 16;
				uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
			}
		}
	}
	return uuid.join('');
}
/**
 * 获取请求地址参数
 * @param name
 * @returns
 */
function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}

/**
 * 自定义提示框
 * @param value 内容
 * @param callback 回调方法
 * @param type 类型，修改图标使用
 */
function jAlert(value, callback, type) {
	/* "error"、"info"、"question"、"warning" */
	type = type || "info";
	$.messager.alert({
		ok : "确定",
		cancel : "取消",
		title : "提示",
		msg : value,
		icon : type,
		fn : function() {
			if (typeof callback == "function") {
				callback.call(this);
			}
		}
	});
}

/**
 * 自定义询问框
 * @param value 内容
 * @param callback 确定回调
 * @param cancellation 关闭回调
 */
function jConfirm(value, callback, cancellation) {
	$.messager.confirm({
		ok : "确定",
		cancel : "取消",
		title : "询问",
		msg : value,
		fn : function(a) {
			if (typeof callback == "function" && a) {
				callback.call(this);
			} else if (typeof cancellation == "function" && !a) {
				cancellation.call(this);
			}
		}
	});
}

/**
 * Ajax请求
 * @param url 地址
 * @param data 参数
 * @param successcall 成功方法
 * @param errorcall 错误方法
 * @param completecall 完成方法
 * @returns
 */
function jAjax(url, data, successcall, errorcall, completecall, async, config) {
	data = data || {};
	config = config || {};
	data.cachetime = new Date().getTime();
	return $.ajax($.extend({}, config, {
		async : typeof async == undefined ? true : async,
		type : "post",
		url : url,
		contentType : 'application/x-www-form-urlencoded; charset=utf-8',
		data : data,
		dataType : "json",
		complete : function() {
			if (typeof completecall == 'function')
				completecall();
		},
		success : function(data) {
			if (typeof successcall == 'function')
				successcall(data);
		},
		error : function(xhr, status, errMsg) {
			if (typeof errorcall == 'function')
				errorcall(xhr, status, errMsg);
		}
	}));
}

/**
 * Ajax跨域请求
 * @param url
 * @param data
 * @param success
 * @param error
 * @param complete
 * @param verify
 */
function jPAjax(url, data, success, error, complete, verify) {
	data = data || {};
	if (verify) {
		data.crossdomain = true;
		data.token = ssoclientfilterToken;
		data.systemKey = ssoclientfilterSystemKey;
	}
	$.ajax({
		type : "GET",
		url : url,
		contentType : 'application/x-www-form-urlencoded; charset=utf-8',
		dataType : "jsonp",
		data : {
			data : JSON.stringify(data)
		},
		jsonp : "callback",
		success : function(data) {
			if (typeof success === "function")
				success(data);
		},
		error : function(xhr, status, message) {
			if (typeof error === "function")
				error(xhr, status, message);
		},
		complete : function() {
			if (typeof complete === "function")
				complete();
		}
	});
}
/**
 * 时间格式化
 * @param fmt
 * @returns
 */
Date.prototype.format = function(fmt) {
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours() % 12 == 0 ? 12 : this.getHours() % 12, // 小时
		"H+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()
	// 毫秒
	};
	var week = {
		"0" : "/u65e5",
		"1" : "/u4e00",
		"2" : "/u4e8c",
		"3" : "/u4e09",
		"4" : "/u56db",
		"5" : "/u4e94",
		"6" : "/u516d"
	};
	if (/(y+)/.test(fmt)) {
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	}
	if (/(E+)/.test(fmt)) {
		fmt = fmt.replace(RegExp.$1, ((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "/u661f/u671f" : "/u5468") : "") + week[this.getDay() + ""]);
	}
	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(fmt)) {
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
		}
	}
	return fmt;
};
/**
 * 字符串去空格
 */
String.prototype.trim = function() {
	return this.replace(/(^\s*)|(\s*$)/g, "");
};
/**
 * 字符串去左空格
 */
String.prototype.ltrim = function() {
	return this.replace(/(^\s*)/g, "");
};
/**
 * 字符串去右空格
 */
String.prototype.rtrim = function() {
	return this.replace(/(\s*$)/g, "");
};

/**
 * 封装ajax请求。<br />
 * 支持跨域和非跨域请求，非跨域时请求json数据，跨域时用jsonp形式调用。<br />
 * 示例：<br />
 * CallWebMethod("/test.html");<br />
 * CallWebMethod("/test.html",{data:JSON.stringify(obj)} );<br />
 * CallWebMethod("/test.html",function successCallback(){} );<br />
 * 
 * @param {String} url ajax请求地址
 * @param {Object} data ajax请求参数（可空缺,空缺时ajaxSettings应同时空缺）
 * @param {Object} ajaxSettings ajax设置（可空缺）
 * @param {Function} successCallback ajax请求结果json.appCode=="0"时回调
 * @param {Function} errorCallback ajax请求结果json.appCode!="0"时回调
 * @param {Function} complete ajax请求完成时回调
 * @param {Function} error ajax请求错误时回调
 * @returns {jqXHR}
 * @throws "url empty."
 */
function CallWebMethod(url, data, ajaxSettings, successCallback, errorCallback, complete, error) {
	// 参数处理,空缺参数处理
	if (typeof url != 'string') {
		error = complete;
		complete = errorCallback;
		errorCallback = successCallback;
		successCallback = ajaxSettings;
		ajaxSettings = data;
		data = url;
		url = undefined;
	}
	if (typeof data == 'function') {
		error = errorCallback;
		complete = successCallback;
		errorCallback = ajaxSettings;
		successCallback = data;
		ajaxSettings = {};
		data = {}
	}
	if (typeof ajaxSettings == 'function') {
		error = complete;
		complete = errorCallback;
		errorCallback = successCallback;
		successCallback = ajaxSettings;
		ajaxSettings = {}
	}
	var thisargs = arguments;

	data = data || {};
	ajaxSettings = ajaxSettings || {};
	url = url || ajaxSettings.url;

	if (!url)
		throw "url empty.";

	// 判断是否跨域
	var a = document.createElement('a');
	a.href = url;
	var isCrossDomain = !(window.location.protocol == a.protocol && window.location.host == a.host && window.location.port == a.port);

	// data.cachetime=new Date().getTime();
	if (typeof PreCallWebMethod == 'function')
		PreCallWebMethod.apply(window, thisargs);
	/*
	 * url=url+(data?((url.indexOf("?")>=0?"&":"?")+$.param(data)):"");
	 * data=null;
	 */
	if (typeof console != "undefined")
		console.log("CallWebMethod" + (isCrossDomain ? "(跨域)" : "") + "：" + url + ($.isEmptyObject(data) ? "" : (url.indexOf("?") >= 0 ? "&" : "?") + $.param(data)));

	ajaxSettings = $.extend({
		type : (isCrossDomain ? "GET" : "POST"),
		url : url,
		contentType : 'application/x-www-form-urlencoded; charset=utf-8',
		data : data,
		dataType : (isCrossDomain ? "jsonp" : "json"),
		success : function(json) {
			if (json.appCode == "0") {
				if (typeof successCallback == 'function')
					successCallback.apply(this, arguments);
			} else {
				if (typeof errorCallback == 'function')
					errorCallback.apply(this, arguments);
				if (typeof console != "undefined")
					console.log("CallWebMethod 请求错误：" + url + ":" + json.dataBuffer);
			}
		},
		complete : function() {
			if (typeof complete == 'function')
				complete.apply(this, arguments);
			if (typeof PostCallWebMethod == 'function')
				PostCallWebMethod.apply(window, cwmargs);
		},
		error : function(xhr, status, errMsg) {
			if (typeof error == 'function') {
				error.apply(this, arguments);
				return;
			}
			var error = "系统异常！";
			try {
				error = eval(xhr.responseText).Message;
			} catch (ex) {
			}
			if (typeof console != "undefined")
				console.log("CallWebMethod error：" + url + ":" + error);
			// jAlert(error);
		}
	}, ajaxSettings);

	return $.ajax(ajaxSettings);
}

// 绑定Select
function BindSelect(id, url, data, value, name, callback, withFirst, firstValue, firstName, titleValue) {
	value = value || "dicCode";
	name = name || "dicName";
	titleValue = titleValue || name;
	withFirst = withFirst || false;
	firstValue = firstValue || "";
	firstName = firstName || "-----------请选择----------";
	jAjax(url, data, function(data) {
		$("#" + id).empty();
		if (withFirst && withFirst == true) {
			$("#" + id).append("<option title='" + firstName + "' value='" + firstValue + "' selected='selected'>" + firstName + "</option>");
		}
		if (data) {
			for ( var key in data) {
				$("#" + id).append("<option title='" + data[key][titleValue] + "' value='" + data[key][value] + "'>" + data[key][name] + "</option>");
			}
		}
		if (callback) {
			callback(data);
		}
	});
};

var Base64 = {
	_keyStr : "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",
	encode : function(input) {
		var output = "";
		var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
		var i = 0;
		input = Base64.utf8_encode(input);
		while (i < input.length) {
			chr1 = input.charCodeAt(i++);
			chr2 = input.charCodeAt(i++);
			chr3 = input.charCodeAt(i++);
			enc1 = chr1 >> 2;
			enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
			enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
			enc4 = chr3 & 63;
			if (isNaN(chr2)) {
				enc3 = enc4 = 64;
			} else if (isNaN(chr3)) {
				enc4 = 64;
			}
			output = output + Base64._keyStr.charAt(enc1) + Base64._keyStr.charAt(enc2) + Base64._keyStr.charAt(enc3) + Base64._keyStr.charAt(enc4);
		}
		return output;
	},
	decode : function(input) {
		var output = "";
		var chr1, chr2, chr3;
		var enc1, enc2, enc3, enc4;
		var i = 0;
		input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");
		while (i < input.length) {
			enc1 = Base64._keyStr.indexOf(input.charAt(i++));
			enc2 = Base64._keyStr.indexOf(input.charAt(i++));
			enc3 = Base64._keyStr.indexOf(input.charAt(i++));
			enc4 = Base64._keyStr.indexOf(input.charAt(i++));
			chr1 = (enc1 << 2) | (enc2 >> 4);
			chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
			chr3 = ((enc3 & 3) << 6) | enc4;
			output = output + String.fromCharCode(chr1);
			if (enc3 != 64) {
				output = output + String.fromCharCode(chr2);
			}
			if (enc4 != 64) {
				output = output + String.fromCharCode(chr3);
			}
		}
		output = Base64.utf8_decode(output);
		return output;
	},
	utf8_encode : function(string) {
		string = string.replace(/\r\n/g, "\n");
		var utftext = "";
		for (var n = 0; n < string.length; n++) {
			var c = string.charCodeAt(n);
			if (c < 128) {
				utftext += String.fromCharCode(c);
			} else if ((c > 127) && (c < 2048)) {
				utftext += String.fromCharCode((c >> 6) | 192);
				utftext += String.fromCharCode((c & 63) | 128);
			} else {
				utftext += String.fromCharCode((c >> 12) | 224);
				utftext += String.fromCharCode(((c >> 6) & 63) | 128);
				utftext += String.fromCharCode((c & 63) | 128);
			}

		}
		return utftext;
	},
	utf8_decode : function(utftext) {
		var string = "";
		var i = 0;
		var c = c1 = c2 = 0;
		while (i < utftext.length) {
			c = utftext.charCodeAt(i);
			if (c < 128) {
				string += String.fromCharCode(c);
				i++;
			} else if ((c > 191) && (c < 224)) {
				c2 = utftext.charCodeAt(i + 1);
				string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
				i += 2;
			} else {
				c2 = utftext.charCodeAt(i + 1);
				c3 = utftext.charCodeAt(i + 2);
				string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
				i += 3;
			}
		}
		return string;
	}
};