Date.prototype.Format = function(fmt) {
	var o = {
		"M+": this.getMonth() + 1, //月份
		"d+": this.getDate(), //日
		"h+": this.getHours(), //小时
		"m+": this.getMinutes(), //分
		"s+": this.getSeconds(), //秒
		"q+": Math.floor((this.getMonth() + 3) / 3), //季度
		"S": this.getMilliseconds() //毫秒
	};
	if(/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for(var k in o)
		if(new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[
			k]).substr(("" + o[k]).length)));
	return fmt;
}

var Validation = function() {};
Validation.valiPwd = function(pwd) {
	if(pwd == null) {
		return false;
	}
	if(pwd.trim().length == 0) {
		return false;
	}
	var myreg = /^[a-zA-Z]\w{5,15}$/; //密码(以字母开头，长度在6~16之间，只能包含字母、数字和下划线)
	//var myreg2 = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[a-zA-Z0-9]{8,16}$/;//强密码(必须包含大小写字母和数字的组合，不能使用特殊字符，长度在 8-16 之间)
	return myreg.test(pwd);
}
Validation.valiStrongPwd = function(pwd) {
	if(pwd == null) {
		return false;
	}
	if(pwd.trim().length == 0) {
		return false;
	}
	//var myreg1 = /^[a-zA-Z]\w{5,15}$/; //密码(以字母开头，长度在6~16之间，只能包含字母、数字和下划线)
	var myreg = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[a-zA-Z0-9]{8,16}$/; //强密码(必须包含大小写字母和数字的组合，不能使用特殊字符，长度在 8-16 之间)
	return myreg.test(pwd);
}
Validation.valiUserName = function(ac) {
	if(ac == null) {
		return false;
	}
	var myreg = /^[a-zA-Z][a-zA-Z0-9_]{5,15}$/; //字母开头，允许6-16字节，允许字母数字下划线
	//校验用户名
	return myreg.test(ac);
}

deleteNote = function(e){
	console.log($("#id").val());
	swal({
		buttons: true,
		title: "请三思！确定删除吗？",
		text: "你将无法恢复该操作！",
		icon: "warning",
		dangerMode: true
	}).then(
		function (value) {
			if(value){
				$.ajax({
					type: 'delete',
					url: "/note/delete/" + $("#id").val(),
					success: function (data) {
						if (data.status == 200) {
							swal('删除成功');
							window.location.reload();
						} else {
							swal(data.msg)
						}
					},
					failed: function (data) {
						swal("服务器异常，请稍后重试！")
					}
				})
			}
		}

	);
}