/**
 * 异步的ajax请求
 * 
 * @param url
 * @param parms
 * @param callback
 * @returns
 */
function sendRequestGet(url,parms,callback){
	$.ajax({
	    type: "GET", // 提交请求的方式
	    cache: true, // 是否有缓存
	    url: url,
	    dataType: "json",
	    data: parms,
	    timeout:60000,
	    async: true,// 是否异步
	    success: function (data) {// 获得返回值
	    	callback(data)
	    },
	    error: function (request) {// 请求出错
	    	console.log(request)
	    	callback("ERROR")
	    }
	});
}

/**
 * 异步的ajax请求
 * 
 * @param url
 * @param parms
 * @param callback
 * @returns
 */
function sendRequest(url,parms,callback){
	$.ajax({
	    type: "POST", // 提交请求的方式
		cache: false, // 是否有缓存
	    url: url,
	    dataType: "json",
	    data: parms,
	    timeout:60000,
	    async: false,// 是否异步
	    success: function (data) {// 获得返回值
	    	callback(data)
	    },
	    error: function (request) {// 请求出错
	    	callback("ERROR")
	    }
	});
}

/**
 * 同步的ajax请求
 * 
 * @param url
 * @param parms
 * @param callback
 * @returns
 */
function sendRequestAsync(url,parms,callback){
	$.ajax({
	    type: "POST", // 提交请求的方式
	    cache: true, // 是否有缓存
	    url: url,
	    dataType: "json",
	    data: parms,
	    timeout:60000,
	    async: false,// 是否异步
	    success: function (data) {// 获得返回值
	    	callback(data)
	    },
	    error: function (request) {// 请求出错
	    	console.log(request)
	    	callback("ERROR")
	    }
	});
}

/**
 * 异步的ajax请求，对象接收
 * @param url
 * @param parms
 * @param callback
 * @returns
 */
function sendRequestBody(url,parms,callback){
	$.ajax({
		type : "POST",// 提交请求的方式
		url : url,
		contentType : "application/json",
		data : JSON.stringify(parms),
		dataType : "json",
		timeout:60000,
		async : true,// 是否异步
		success : function(data) {// 获得返回值
			callback(data)
		},
		error : function(request) {// 请求出错
			callback("ERROR")
		}
	});
}

function sendRequestBodyAsync(url,parms,callback){
	$.ajax({
		type : "POST",// 提交请求的方式
		url : url,
		contentType : "application/json",
		data : JSON.stringify(parms),
		dataType : "json",
		timeout:60000,
		async : false,// 是否异步
		success : function(data) {// 获得返回值
			callback(data)
		},
		error : function(request) {// 请求出错
			console.log(request)
			callback("ERROR")
		}
	});
}

function getUuid() {
    return 'xxxxxxxxxxxx4xxxyxxx'.replace(/[xy]/g, function (c) {
        var r = Math.random() * 16 | 0,
            v = c == 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16).toUpperCase();
    });
}
