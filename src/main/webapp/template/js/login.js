$(function(){
    // 点击登录
    $(".submit").on("click",function(){
        var userName = $(".username").val();
        var password = $(".password").val();
        var code = $(".captcha").val();
        if(userName == ""){
            new $.zui.Messager("用户名为空",{
                placement:'center',
                type:'warning',
                time:1500,
                icon:"warning-sign"
            }).show()
            return false
        }
        if(password == ""){
            new $.zui.Messager("密码为空",{
                placement:'center',
                type:'warning',
                time:1500,
                icon:"warning-sign"
            }).show()
            return false
        }
        if(code == ""){
            new $.zui.Messager("验证码为空",{
                placement:'center',
                type:'warning',
                time:1500,
                icon:"warning-sign"
            }).show()
            return false
        }
        var loginUrl = "/questionnaire/login/checkuser";
        var parms ={
            'username':userName,
            'password':password,
            'randCode' :code
        }
        sendRequest(loginUrl,parms,function(res){
            if(res == "ERROR"){
                new $.zui.Messager("请求失败",{
                    placement:'center',
                    type:'danger',
                    time:1500,
                    icon:"exclamation-sign"
                }).show()
                document.getElementById("randCodeImage").src="randCodeImage?"+Math.random();
            }
            else if(res.isSuccess){
            	window.location= "/questionnaire/login/index"
            	 new $.zui.Messager(res.msg,{
                     placement:'center',
                     type:'success',
                     time:1500,
                     icon:"check"
                 }).show()
            }else{
            	 new $.zui.Messager(res.msg,{
                     placement:'center',
                     type:'danger',
                     time:1500,
                     icon:"exclamation-sign"
                 }).show()
                 document.getElementById("randCodeImage").src="randCodeImage?"+Math.random();
            }
        })
    });
     //点击回车触发登录按钮
     document.onkeydown = function(e){
    	if(!e) e = window.event;
    	if((e.keyCode || e.which)==13){
    		$(".submit").click()
    	}
         //阻止ctrl+s保存页面
         if(e.ctrlKey && e.which == 83){
             return false
         }
    }
     // 点击切换图形验证码
     $("#randCodeImage").on("click",function(){
    	 document.getElementById("randCodeImage").src="randCodeImage?"+Math.random();
     })
     
})
