function searchByName() {
	var keyWords = $("#keyWords").val();
	if (keyWords == "") {
		showMessage("messageDialog", "errorContent",
				"Key words cannot be null.");
		return false;
	}
	$.ajax({
		type : "post",
		url : "search.do?option=searchByName",
		data : {
			"keyWords" : keyWords
		},
		error : function() {
			showMessage("messageDialog", "errorContent", "Server error");
		},
		success : function(data) {
			var result=handleReturnMessage(data);
			if(result=='error' ||result=='success' ||result=='message' ){
				return;
			}
			personArray=result;
			if(personArray!=null && personArray.length > 0){
				var table = $("#personListTable");
				table.children("tbody").html("");
				for ( var i = 0; i < personArray.length; i++) {
					var row = $("<tr></tr>");
					var td1 = $("<td></td>");
					var td2 = $("<td></td>");
					var td3 = $("<td></td>");
					var td4 = $("<td></td>");
					var td5 = $("<td></td>");
					var td6 = $("<td></td>");
					td1.append(i + 1);
					td2.append(personArray[i].name);
					td3.append(personArray[i].cellPhone);
					td4.append(personArray[i].mail);
					td5.append(personArray[i].ou);
					td6.append(personArray[i].description);
					row.append(td1);
					row.append(td2);
					row.append(td3);
					row.append(td4);
					row.append(td5);
					row.append(td6);
					table.append(row);
				}
			}
		}
	});
	return false;
}
function popLoginDialog(){
	var name=$("#name").val();
	if(name!=''){
		showMessage("signoutDialog", "messageContent","Are you sure to Sign out?");
		return;
	}else{
		$('#loginDialog').modal('show');
	}
}

function login() {
	// not login
	$("#loginError").val("Logining");
	
	var username = $("#username").val();
	var password = $("#password").val();
	if (username == "" || $.trim(username) == "") {
		showMessage("messageDialog", "errorContent",
				"User name cannot be null.");
		return false;
	}
	if (password == "" || $.trim(password) == "") {
		showMessage("messageDialog", "errorContent",
				"Pssword cannot be null.");
		return false;
	}
	// Datas are OK, send to server
	saveUserInfo();
	$.ajax({
		type : "post",
		url : "user.do?option=login",
		data : {
			"username" : username,
			"password" : password
		},
		error : function() {
			showMessage("messageDialog", "errorContent", "Server error");
		},
		success : function(data) {
			var result=handleReturnMessage(data);
			if(result=='error' ||result=='success' ||result=='message' ){
				return;
			}
			if(result!=null && result.length > 0){
				// refresh user's info(cell phone number) in seach.jsp
				// alert("login success!!");
				$('#loginDialog').modal('hide');
				var name = result[0].username;
				var phone = result[0].cellphone;
				$("#name").val(name);
				$("#cellphone").val(phone);
				$("#cellphoneBackup").val(phone);
				$("#userinfoDIV").css("display","block");
				$("#loginError").val("");
				
				// change Sign in into Sign out
				$("#loginTag").html("Sign out");
			}
		}
	});
}

function signout(){
	$.ajax({
		type : "post",
		url : "user.do?option=logout",
		data : {},
		error : function() {
			showMessage("messageDialog", "errorContent", "Server error");
		},
		success : function(data) {
			var result=handleReturnMessage(data);
			if(result=='success'){
				$('#signoutDialog').modal('hide');
				$("#cellphone").val("");
				$("#cellphoneBackup").val("");
				$("#name").val("");
				$("#loginTag").html("Sign In");
				$("#userinfoDIV").css("display","none");
			}
		}
	});
}


// Remember User
// initial if user has choose save username and password
$(document).ready(function() {
	if ($.cookie("remember") == "true") {
		$("#remember").attr("checked", true);
		$("#username").val($.cookie("username"));
		$("#password").val($.cookie("password"));
	}
});
// Save user info if select remember username and password
function saveUserInfo() {
	if ($("#remember").attr("checked") == "checked") {
		var username = $("#username").val();
		var password = $("#password").val();
		$.cookie("remember", "true", {
			expires : 365
		}); // save a cookie for 365 days validity
		$.cookie("username", username, {
			expires : 365
		}); // save a cookie for 365 days validity
		$.cookie("password", password, {
			expires : 365
		}); // save a cookie for 365 days validity
	} else {
		$.cookie("remember", "false", {
			expires : -1
		});
		$.cookie("username", '', {
			expires : -1
		});
		$.cookie("password", '', {
			expires : -1
		});
	}
}

function alertAuthorInfo() {
	showMessage("messageDialog", "errorContent",
			"My mail is yuanhongbin@cienet.com.cn");
	return false;
}

function showMessage(messageDiv, messageTagId, message) {
	//if login dialog is block, then hide it
	$('#loginDialog').modal('hide');
	
	var errorTag = $("#" + messageTagId);
	errorTag.text(message);
	$('#' + messageDiv).modal({
		backdrop : true,
		keyboard : true,
		show : true
	});
//	// make this messageDialog at the front of the brower
//	$('#'+ messageDiv).css("z-index", "99999");
}

$('#messageDialog').on('hidden', function(){
	var loginError=$("#loginError").val();
	if(loginError=="Logining"){
		$('#loginDialog').modal('show');
	}
});



function editOrSaveOption(){
	var button=$("#optionCellphoneButton");
	var buttonValue=button.html();
	if(buttonValue=='Edit'){
		$("#cellphone").attr("disabled",false);
		button.html('Save');
	}else if(buttonValue=='Save'){
		saveCellphone();
	}
}
function saveCellphone(){
	var cellphoneInput=$("#cellphone");
	var cellphone=cellphoneInput.val();
	var cellphoneBackup=$("#cellphoneBackup").val();
	if(cellphone==''){
		showMessage("messageDialog", "errorContent","Cellphone number cannot be null.");
		cellphoneInput.val(cellphoneBackup);
		return;
	}
//	var reg1 =/^0{0,1}(13[4-9]|147|15[0-2]|15[7-9]|18[278])[0-9]{8}$/;
//	var reg2 =/^0{0,1}(133|153|180|189)[0-9]{8}$/;
//	var reg3 =/^0{0,1}(13[0-2]|15[56]|18[56])[0-9]{8}$/;
//    if(!reg1.test(cellphone) || !reg2.test(cellphone) || !reg3.test(cellphone) )
//    {
//    	showMessage("messageDialog", "errorContent","Cellphone number should be 11 digtals.");
//    	cellphoneInput.val(cellphoneBackup);
//        return;
//    }
    if(cellphoneBackup==cellphone){
    	showMessage("messageDialog", "errorContent","Cellphone number doesn't change.");
    	$("#optionCellphoneButton").html('Edit');
    	cellphoneInput.attr("disabled",true);
    	return;
    }
    // cellphone number change, send to the server
    $.ajax({
		type : "post",
		url : "user.do?option=saveCellphone",
		data : {
			"cellphone" : cellphone
		},
		error : function() {
			showMessage("messageDialog", "errorContent", "Server error");
		},
		success : function(data) {
			var result=handleReturnMessage(data);
			if(result=='success'){
				cellphoneInput.attr("disabled",true);
				$("#cellphoneBackup").val(cellphone);
				$("#optionCellphoneButton").html('Edit');
			}
		}
	});
}


function handleReturnMessage(msg){
	try {
		var jsonArray = eval(msg);
		if(jsonArray!=null && jsonArray.length>0){
			if(jsonArray[0].error!=null &&jsonArray[0].error!=''){
				showMessage("messageDialog", "errorContent",
						jsonArray[0].error);
				return "error";
			}else if(jsonArray[0].success!=null &&jsonArray[0].success!=''){
				showMessage("messageDialog", "errorContent",
						jsonArray[0].success);
				return "success";
			}else if(jsonArray[0].message!=null && jsonArray[0].message!=''){
				showMessage("messageDialog", "errorContent",
						jsonArray[0].message);
				return "message";
			}
			return jsonArray;
		}else{
			showMessage("messageDialog", "errorContent",
			"No data return from server.");
		}
	} catch (e) {
		showMessage("messageDialog", "errorContent",
		"Bad json datas returned from server.");
		return null;
	}
}

function isNewUser(){
	
}