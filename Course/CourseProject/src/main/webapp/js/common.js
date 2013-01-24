/*获取学院信息
 * 
 */
function fetchColleges(){
	 $.ajax({
	        type:"post",
	        url: "fetchData.do?fetchCollege&filter=false",
	        data:{
	            "id":0
	        },
	        error: function(){
	            alert("获取学院列表失败,请尝试重新打开页面或者与管理员联系");
	        },
	        success: function(data){
	            var obj=null;
	            try{
	                obj=$.parseJSON(data);
	            }catch(e){
	                alert("请求数据有错，请与管理员联系");
	                return;
	            }
	            if(obj.length>0){
	            	source="学院:<select name=\"col\" id=\"col\" style=\"width:170px;\" onchange=\"fetchMajor();\"><option value=0>请选择学院</option>";
		            for(i=0;i<obj.length;i++){
		                source=source+"<option value='"+obj[i].collegeId+"'>"+obj[i].collegeName+"</option>";
		            }
	            }else{
	            	var val="";
	            	if($("#col").val()!=0){
	            		val="还没有添加学院";
	            	}else{
	            		val="请选择学院"
	            	}
	            	source="学院:<select name=\"col\" id=\"col\" style=\"width:170px;\" onchange=\"fetchMajor();\"><option value=0>"+val+"</option>";
	            }
	            source=source+"</select><span style=\"color:red;font-size: 20px;font-weight: bold;margin-top:10px\">*</span>";
	            $("#colloegeDiv").html(source);
	        }
	    });
}


/**根据学院号码获取专业信息
 *
 */
function fetchMajor(){
    id=$("#col").val();
    $.ajax({
        type:"post",
        url: "fetchData.do?fetchMajor&filter=false",
        data:{
            "id":id
        },
        error: function(){
            alert("获取专业列表失败,请与管理员联系");
        },
        success: function(data){
            var obj=null;
            try{
                obj=$.parseJSON(data);
            }catch(e){
                alert("请求数据有错，请与管理员联系");
                return;
            }
            if(obj.length>0){
            	source="专业:<select name=\"majorDiv\" id=\"majorDiv\" style=\"width:170px;\" onchange=\"fetchClasses();\"><option value=0>请选择专业</option>";
	            for(i=0;i<obj.length;i++){
	                source=source+"<option value='"+obj[i].majorId+"'>"+obj[i].majorName+"</option>";
	            }
            }else{
            	var val="";
            	if($("#col").val()!=0){
            		val="该学院没有专业";
            	}else{
            		val="请选择专业"
            	}
            	source="专业:<select name=\"majorDiv\" id=\"majorDiv\" style=\"width:170px;\" onchange=\"fetchClasses();\"><option value=0>"+val+"</option>";
            }
            source=source+"</select><span style=\"color:red;font-size: 20px;font-weight: bold;margin-top:10px\">*</span>";
            $("#majordiv").html(source);
        }
    });
}

/**根据专业获取班级信息
*
*/
function fetchClasses(){
    id=$("#majorDiv").val();
    $.ajax({
        type:"post",
        url: "fetchData.do?fetchClasses&filter=false",
        data:{
            "id":id
        },
        error: function(){
            alert("获取班级列表失败,请与管理员联系");
        },
        success: function(data){
            var obj=null;
            try{
                obj=$.parseJSON(data);
            }catch(e){
                alert("请求数据有错，请与管理员联系");
                return;
            }
            if(obj.length>0){
            	source="班级:<select name=\"className\" id=\"className\" style=\"width:170px;\"><option value=0>请选择班级</option>";
	            for(i=0;i<obj.length;i++){
	                source=source+"<option value='"+obj[i].classId+"'>"+obj[i].className+"</option>";
	            }
            }else{
            	var val="";
            	if($("#majordiv").val()!=0){
            		val="该专业还没有划分班级";
            	}else{
            		val="请选择专业班级"
            	}
            	source="班级:<select name=\"className\" id=\"className\" style=\"width:170px;\"><option value=0>"+val+"</option>";
            }
            source=source+"</select><span style=\"color:red;font-size: 20px;font-weight: bold;margin-top:10px\">*</span>";
            $("#className").html(source);
        }
    });
}
/**显示新闻
* 
*/
function viewNews(id){
 $.facebox.close();
 $("<a href='news.do?c=view&id="+id+"'>查看</a>").facebox({
     type:'ajax'
 }).click(); 
}

/**显示选题详细信息
 * 
 */
function viewCourse(id){
    $.facebox.close();
    $("<a href='course.do?c=view&id="+id+"'>查看</a>").facebox({
        type:'ajax'
    }).click(); 
}

function isTeacherNameExist(){
	teaName=$("#teacherName").val();
	if(teaName!=null && teaName!=""){
	    $.ajax({
	        type:"post",
	        url: "user.do?isTeacherExist",
	        data:{
	            "userName":teaName
	        },
	        error: function(){
	            alert("查询教师失败,请与管理员联系");
	        },
	        success: function(data){
	        	var obj=null;
	            try{
	                obj=$.parseJSON(data);
	            }catch(e){
	                alert("请求数据有错，请与管理员联系");
	                return;
	            }
	        	if(obj!=null&&obj.length==1){
	        		if(obj[0].id != null && obj[0].id!=""){
	        			$("#teacherId").val(obj[0].id);
	        		}
	        		$("#isTeacherExistMessage").html(obj[0].message);
	        	}else{
	        		alert("请求数据有错，请与管理员联系");
	        	}
	        }
	    });
	}
}


/**显示教师
* 
*/
function viewTea(id){
 $.facebox.close();
 $("<a href='user.do?c=viewTea&id="+id+"'>查看</a>").facebox({
     type:'ajax'
 }).click(); 
}

/**显示学生
* 
*/
function viewStu(id){
 $.facebox.close();
 $("<a href='user.do?c=viewStu&id="+id+"'>查看</a>").facebox({
     type:'ajax'
 }).click(); 
}


//选择课题
function selectOption(id){
	$("#img"+id).html('<img alt="Approve" src="facebox/loading.gif">');
    $.ajax({
       type:"post",
       url: "selection.do?c=select",
       data:{
           "id":id
       },
       error: function(){
           error("选入失败 服务器连接失败",3000);
       },
       success: function(data){
           var obj=null;
           try{
               obj=$.parseJSON(data);
           }catch(e){
               alert(e);
               return;
           }	 
           
           if(obj.statu==10){
               $("#img"+id).html('<a href="javascript:void(0)" style="color:red;font-size:20px" onclick="deSelectOption('+id+')">退选</a>');
               //success(obj.msg,3000);
           }else if(obj.statu==3){
               //error(obj.msg,3000);
               $("#img"+id).html("");
           }else{
        	   $("#img"+id).html('<input type="button" value="选入" name="selectItem"  onclick="selectOption('+id+')" />');
        	   //error(obj.msg,3000);
           }
           alert(obj.msg);
       }
   });
}

//退选课题
function deSelectOption(id){
	$("#img"+id).html('<img alt="Approve" src="facebox/loading.gif">');
    $.ajax({
       type:"post",
       url: "selection.do?c=deSelect",
       data:{
           "id":id
       },
       error: function(){
           error("退选失败 服务器连接失败",3000);
       },
       success: function(data){
           var obj=null;
           try{
               obj=$.parseJSON(data);
           }catch(e){
               alert(e);
               return;
           }	 
           if(obj.statu==11){
        	   $("#img"+id).html('<input type="button" value="选入" name="selectItem"  onclick="selectOption('+id+')" />');
               //success(obj.msg,3000);
           }else{
        	   $("#img"+id).html('<a href="javascript:void(0)" style="color:red;font-size:20px" onclick="deSelectOption('+id+')">退选</a>');
        	   //error(obj.msg,3000);
           }
           alert(obj.msg);
       }
   });
}



var t;
/**
 * 显示成功消息
 * 
 */
function success(text, time) {
	clearTimeout(t);
	text = "<img src='img/icons/icon_success.png' alt='Success' />"+text;
	$("#noticesuccesscontent").html(text);
	$("#noticeerror").hide();
	$("#noticesuccess").show();
	t = setTimeout(function() {
		$("#noticesuccess").fadeOut(300);
	}, time);
}
/**
 * 显示错误消息
 * 
 */
function error(text, time) {
	clearTimeout(t);
	text = "<img src='img/icons/icon_error.png' alt='Error' />"+text;
	$("#noticeerrorcontent").html(text);
	$("#noticesuccess").hide();
	$("#noticeerror").show();
	t = setTimeout(function() {
		$("#noticeerror").fadeOut(300);
	}, time);
}



/**初始化时间选择器
	 * 
	 */
function initTimePicker(){

    $('.datetime').datepicker({
        dateFormat: 'yy-mm-dd',	
        monthNames: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],
        currentText: '现在'	,
        closeText: '完成',
        prevText: '上月',
        nextText: '下月',
        timeText: '时间',
        hourText: '小时',
        minuteText: '分钟'
    });

}


function buildCourseSourceAndTypeData(){
	var courseArray=$("[name='courseId[]']");
	var courseData="";
	for(var i=0;i<courseArray.length;i++){
		var courseId=courseArray[i].value;
		var courseSourceId=$("#courseSource"+courseId).val();
		var courseTypeId=$("#courseType"+courseId).val();
		oneCourseData=courseId+"_"+courseSourceId+"_"+courseTypeId;
		if(i>0){
			courseData+=",";
		}
		courseData+=oneCourseData;
	}
	$.ajax({
		type : "post",
		dataType : "json",
		url : "course.do?c=UpdateCourseSourseAndType",
		data : {//send the datas
			"courseAllData":courseData
		},
		error : function() {
			//return fail 
			alert("服务器异常，请联系管理员！");
		},
		success : function(data) {
			var jsonArray=eval(data);
			alert(jsonArray[0].result);
			window.location.href = jsonArray[1].url;
		}
	});
}




///**正则匹配是否满足
// */
//function matchIt(s,regxp)
//{
//    var patrn=new RegExp(regxp);
//    if (patrn.exec(s)!=s) return false
//    return true
//} 
///**根据value初始化选择某opition
// */
//function initopition(opitionobj,value){
//    opitionobj.find("option").each(function(){
//        if(this.value==value){
//            $(this).attr("selected","selected");
//        }else{
//            
//    }
//                
//    });
///**显示题库
// * 
// */
///**查看选入情况
// *
// */
//function viewSelection(id){
//    $.facebox.close();
//    $("<a href='selection.do?c=view&id="+id+"'>查看</a>").facebox({
//        type:'ajax'
//    }).click(); 
//}
///**显示权限设置
// * 
// */
//function setPermission(id,string){
//    $.facebox.close();
//    $("<a href='permission.do?c=show&id="+id+"&string="+string+"'>查看</a>").facebox({
//        type:'ajax'
//    }).click(); 
//}
//
//function in_array(key,array){
//    for(i=0;i<array.length;i++){
//        if(array[i]!=""&&key!=""&&array[i]==key){
//            return true;
//        }
//    }
//    return false;
//}
//

///**初始化设置
// * 
// */
//$(document).ready(function() {
//    initTimePicker();
//});

///**保存选题
// * 
// */
//function saveSel(id){
//    $("#savesel"+id).html('<img alt="Approve" src="facebox/loading.gif">');
//    max=$("#saveseltext"+id).val();
//    $.ajax({
//        type:"post",
//        url: "selection.do?c=savesel",
//        data:{
//            "id":id,
//            "max":max
//        },
//        error: function(){
//            error("保存失败",3000);
//        },
//        success: function(data){
//            var obj=null;
//            try{
//                obj=$.parseJSON(data);
//            }catch(e){
//                alert(e);
//                return;
//            }	 
//            if(obj.statu==0){
//                error(obj.msg,3000);
//            }else{
//                success(obj.msg,3000);
//                left=$("#saveleft"+id).html(obj.left);
//            }
//            $("#savesel"+id).html('<input type="button" onclick="saveSel('+id+');" class="btn" value="保存" />');
//            
//        }
//    });
//}