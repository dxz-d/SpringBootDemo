var E = window.wangEditor;
var editor = new E('#editor')
editor.customConfig.uploadImgServer = '/image/upload';
editor.customConfig.pasteFilterStyle = false;
editor.customConfig.uploadImgMaxSize = 3 * 1024 * 1024;
editor.customConfig.uploadImgMaxLength = 5;
editor.customConfig.menus = [
	'head', // 标题
	'bold', // 粗体
	'fontSize', // 字号
	'fontName', // 字体
	'italic', // 斜体
	'underline', // 下划线
	'strikeThrough', // 删除线
	'foreColor', // 文字颜色
	'backColor', // 背景颜色
	'link', // 插入链接
	'list', // 列表
	'justify', // 对齐方式
	//'quote', // 引用
	//'emoticon', // 表情
	//'image', // 插入图片
	'table', // 表格
	//'video', // 插入视频
	//'code', // 插入代码
	'undo', // 撤销
	'redo' // 重复
];

editor.customConfig.customAlert = function(info) {
	// info 是需要提示的内容
	swal(info);
}
//editor.customConfig.uploadFileName = 'files';
//editor.customConfig.uploadImgTimeout = 60000;
editor.create();
editor.txt.html($("#tmpcontent").text());
$("#tmpcontent").remove();

function postNote(e){
	e.preventDefault();
	if($("#title").val().trim().length<1){
		swal("标题不能为空");
		return;
	}
	$.ajax({
		type:"post",
		url:"/note/insert",
		async:false,
		data:{
			'title':$("#title").val(),
			'content':editor.txt.html()
		},
		success:function(data){
			if(data.status==200&&data.data!=null&&data.data.id!=null){
				swal("保存成功!");
				setTimeout(() => {
					//window.location.href="http://localhost:8081/note/"+data.data.id+".html";
					var item ="<a class='list-group-item' href='javascript:ShowTap('/note/"+data.data.id+"')'>"+data.data.title+"</a>";
					$("#newTap").after(item);
					$("#note").load("/note/"+data.data.id);
				}, 1000);
			}else{
				swal("上传失败了-- "+data.msg);
			}
		},
		error:function(data){
			swal("服务器异常！上传失败了！");
		}
	});
}
function updateNote(e){
	e.preventDefault();
	$.ajax({
		type:"post",
		url:"/note/update",
		async:false,
		data:{
			'id':$("#id").val(),
			'userId':$("#userId").val(),
			'title':$("#title").val(),
			'content':editor.txt.html()
		},
		success:function(data){
			if(data.status==200){
				swal("更新成功");
				$("#note").load("/note/"+$("#id").val());
			}else{
				swal(data.msg);
			}
		},
		error:function(data){
			swal("服务器异常！更新失败了！");
		}
	});
}