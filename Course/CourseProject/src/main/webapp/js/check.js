function checkFun() {
	$("#errorMsg").html("");
	if (!$("#studentNumber").val().match("\\d{12,13}")
			|| $("#studentNumber").val() == "") {
		$("#errorMsg").html("请输入正确的学号");
		return false;
	}
	if ($("#studentName").val() == "") {
		$("#errorMsg").html("请输入你的姓名");
		return false;
	}
	if ($("#password1").val() == "") {
		$("#errorMsg").html("请输入一个密码");
		return false;
	}
	if ($("#password1").val() != $("#password2").val()) {
		$("#errorMsg").html("两次输入密码不一致");
		return false;
	}
	if (!$("#email").val().match(".*?@.*")) {
		$("#errorMsg").html("请输入您的email");
		return false;
	}
	if ($("#phone").val() == "") {
		$("#errorMsg").html("请输入您的电话号码");
		return false;
	}
	if ($("#col").val() == "0") {
		$("#errorMsg").html("请选择您的学院");
		return false;
	}
	if ($("#majorDiv").val() == "0") {
		$("#errorMsg").html("请选择您的专业");
		return false;
	}
	if ($("#classDiv").val() == "0") {
		$("#errorMsg").html("请选择您的班级");
		return false;
	}
	return true;
}
function checkLogin() {
	$("#errorMsg").html("");
	if ($("#loginName").val() == "") {
		$("#errorMsg").html("请输入用户名");
		return false;
	}
	if ($("#loginPass").val() == "") {
		$("#errorMsg").html("请输入密码");
		return false;
	}
	return true;
}

//发布公告检查
function checktrunk() {
	if ($("#title").val() == "") {
		alert("请输入公告标题");
		return false;
	}
	if ($("#context").val() == "") {
		alert("请输入公告内容");
		return false;
	}
	return true;
}

//发布课题检查
function checkCourse() {
	if ($("#title").val() == "") {
		alert("请输入课题标题");
		return false;
	}
	if ($("#desp").val() == "") {
		alert("请输入课题内容");
		return false;
	}
	if ($("#teacherName").val() == "") {
		alert("请输入指导教师姓名");
		return false;
	}
	if ($("#isTeacherExistMessage").html()== "对不起，该教师用户不存在") {
		alert("请输入正确的老师名字");
		return false;
	}
	return true;
}


//课题导入，检查文件
function checkuserimport(){
	if($("#file").val() == "") {
		alert("请选择上传的文件");
		return false;
	}
	return true;
}

function checkFileSave(){
	if($("#file").val() == "") {
		alert("请选择上传的文件");
		return false;
	}
}

function checkTeaSave() {
	if ($("#userName").val() == "") {
		alert("请输入您的姓名");
		return false;
	}
	if (!$("#email").val().match(".*?@.*")) {
		alert("请输入您正确的email");
		return false;
	}
	if ($("#phone").val() == "") {
		alert("请输入您的电话号码");
		return false;
	}
	return true;
}


function checkStuSave() {
	if (!$("#stuNumber").val().match("\\d{12,13}")
			|| $("#stuNumber").val() == "") {
		alert("请输入正确的学号");
		return false;
	}
	if ($("#userName").val() == "") {
		alert("请输入你的姓名");
		return false;
	}
	if (!$("#email").val().match(".*?@.*")) {
		alert("请输入您的email");
		return false;
	}
	if ($("#phone").val() == "") {
		alert("请输入您的电话号码");
		return false;
	}
	if ($("#stuClass").val() == "0") {
		alert("请选择您的班级");
		return false;
	}
	return true;
}
 function  checkTeaImport(){
	 if($("#password").val()==""){
		 alert("请输入教师的初始密码");
		 return false;
	 }
	 if($("#file").val()==""){
		 alert("请选择Excel文件");
		 return false;
	 }
	 return true;
 }
 
 function checkbulk(){
    if($("#bulkaction").val()==0){
       alert("请选择操作"); 
       return false;
    }
    return true;
}

 
 function checkMajorSave() {
	if (!$("#majorNumber").val().match("\\d{8}")
			|| $("#majorNumber").val() == "") {
		alert("请输入正确的专业号");
		return false;
	}
	if ($("#majorName").val() == "") {
		alert("请输入专业名称");
		return false;
	}
	return true;
}
 
 function checkClassSave() {
	if ($("#classNumber").val() == "") {
		alert("请输入班级编号");
		return false;
	}
	if ($("#className").val() == "") {
		alert("请输入班级名称");
		return false;
	}
	if ($("#majorId").val() == 0) {
		alert("请选择所在专业");
		return false;
	}
	return true;
}
 
 
 function checkPositionSave() {
	if ($("#positionName").val() == "") {
		alert("请输入职称名称");
		return false;
	}
	if ($("#defaultStuCount").val() == "") {
		alert("请输入默认带生人数");
		return false;
	}
	return true;
}
 
 function checkTimeSave(){
		if ($("#timeStart").val() == "") {
		alert("请输入开始时间");
		return false;
	}
	if ($("#timeEnd").val() == "") {
		alert("请输入截止时间");
		return false;
	}
	return true;
}