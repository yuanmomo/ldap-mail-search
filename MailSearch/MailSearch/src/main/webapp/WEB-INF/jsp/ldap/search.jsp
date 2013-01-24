<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cienet Mail Search System</title>
<!-- Le styles -->
<link href="css/bootstrap.css" rel="stylesheet">
<link href="css/bootstrap-responsive.css" rel="stylesheet">
<link href="css/docs.css" rel="stylesheet">
<link href="js/external/google-code-prettify/prettify.css"
	rel="stylesheet">

<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
		  <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
	<![endif]-->
</head>
<body onload="isNewUser()" data-spy="scroll" data-target=".subnav" data-offset="50" style="overFlow-x:hidden;overFlow-y:scroll;">
	<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">
				<a class="btn btn-navbar" data-toggle="collapse"
					data-target=".nav-collapse"> <span class="icon-bar"></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span>
				</a>
				<div class="nav-collapse">
					<ul class="nav">
						<li class=""><a href="search.do">Home</a></li>
						<li class=""><a data-toggle="modal" href="javascript:void(0)"
							onclick="popLoginDialog()" id="loginTag">Sign In</a></li>
						<li class=""><a href="javascript:void(0)"
							onclick="alertAuthorInfo()">Contact Me</a></li>
					</ul>
				</div>
				<a class="brand" href="search.do">Comverse</a>
			</div>
		</div>
	</div>
	<div class="container-fluid">
		<div class="row-fluid">
			<header class="jumbotron subhead" id="overview">
				<div class="span1"></div>
				<div class="span10">
					<div class="well subnav" style="height: 15px; padding: 10px">
						<strong class="alert-heading">Info:</strong> &nbsp;The last update
						time of database is&nbsp;&nbsp; <strong><c:out
								value="${lastUpdateTime}"></c:out>&nbsp;&nbsp;&nbsp;&nbsp;</strong>
						Update users:&nbsp;&nbsp;<strong><c:out
								value="${updateCount}"></c:out></strong>&nbsp;&nbsp;&nbsp;&nbsp; Add new
						users:&nbsp;&nbsp;<strong><c:out value="${insertCount}"></c:out></strong>&nbsp;&nbsp;&nbsp;&nbsp;
						Delete users:&nbsp;&nbsp;<strong><c:out
								value="${deleteCount}"></c:out></strong>&nbsp;&nbsp;&nbsp;&nbsp;
					</div>
				</div>
				<div class="span1"></div>
			</header>
			<div class="span3">
				<div id="messageDialog" class="modal hide fade in"
					style="display: none;overFlow:hidden;">
					<div class="modal-header">
						<a class="close" data-dismiss="modal">×</a>
						<h3>Info:</h3>
					</div>
					<div class="modal-body">
						<h4 id="errorContent"></h4>
						<input type="hidden" value="" id="loginError"></input>
					</div>
					<div class="modal-footer">
						<a href="#" data-dismiss="modal" class="btn btn-primary">OK</a>
					</div>
				</div>

				<div id="signoutDialog" class="modal hide fade in"
					style="display: none; overFlow:hidden;">
					<div class="modal-header">
						<a class="close" data-dismiss="modal">×</a>
						<h3>Sign Out:</h3>
					</div>
					<div class="modal-body">
						<h4 id="messageContent"></h4>
					</div>
					<div class="modal-footer">
						<a href="javascript:void(0)" class="btn" data-dismiss="modal">NO</a>
						<a href="javascript:void(0)" data-dismiss="modal"
							class="btn btn-primary" onclick="signout()">YES</a>
					</div>
				</div>
				<div id="loginDialog" class="modal hide fade" style="display: none;overFlow:hidden;">
					<div class="modal-header">
						<a class="close" data-dismiss="modal">×</a>
						<h3>User Login:</h3>
					</div>
					<div class="modal-body">
						<h4>Please enter your CIENET account:</h4>
						<table>
							<tr>
								<td>Name:</td>
								<td>&nbsp;&nbsp;&nbsp;&nbsp; <input id="username"
									name="username" type="text" placeholder=""></td>
							</tr>
							<tr>
								<td>Password:</td>
								<td>&nbsp;&nbsp;&nbsp;&nbsp; <input id="password"
									name="password" type="password" placeholder=""></td>
							</tr>
							<tr>
								<td>Remember:</td>
								<td>&nbsp;&nbsp;&nbsp;&nbsp; <input id="remember"
									name="remember" type="checkbox" checked="checked"></td>
							</tr>
						</table>
					</div>
					<div class="modal-footer">
						<a href="#" class="btn" data-dismiss="modal">Close</a> <a
							href="javascript:void(0)" onclick="login()"
							class="btn btn-primary">Login</a>
					</div>
				</div>
				<form class="well form-search" action=""
					onsubmit="return searchByName();" method="post">
					<h4>Please enter the key words:</h4>
					<br /> <span class="help-block">Enter keyword and click
						search: </span> <input id="keyWords" name="keyWords" type="text"
						placeholder="">
					<button onclick="searchByName()" type="button" class="btn"
						style="padding: 4px 5px 4px 5px">Search</button>
					<span class="help-block">&nbsp;</span> <span class="help-block">Support
						four regular symbols: ^ $ * .</span> <span class="help-block">Tips:</span>
					<span class="help-block">&nbsp;&nbsp;&nbsp;&nbsp; ^ :
						Matches beginning of line.</span> <span class="help-block">&nbsp;&nbsp;&nbsp;&nbsp;
						$ : Matches end of line..</span> <span class="help-block">&nbsp;&nbsp;&nbsp;&nbsp;
						* : Matches 0 or more occurrences </span> <span class="help-block">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						of preceding expression.</span> <span class="help-block">&nbsp;&nbsp;&nbsp;&nbsp;
						. : Matches any single character.</span>
				</form>
				<div id="userinfoDIV" style="display: none;">
					<form class="well form-search" action="" onsubmit="return false;"
						method="post">
						<table>
							<tr>
								<td colspan="2">Name:</td>
							</tr>
							<tr>
								<td colspan="2"><input disabled="disabled" id="name"
									name="name" type="text" placeholder="" /></td>
							</tr>
							<tr>
								<td colspan="2">&nbsp;</td>
							</tr>
							<tr>
								<td colspan="2">Cellphone:</td>
							</tr>
							<tr>
								<td><input disabled="disabled" id="cellphone"
									name="cellphone" type="text" placeholder="" /> <input
									id="cellphoneBackup" name="cellphoneBackup" type="hidden"
									placeholder="" /></td>
								<td>
									<button id="optionCellphoneButton" onclick="editOrSaveOption()"
										type="button" class="btn">Edit</button>
								</td>
							</tr>
						</table>
					</form>
				</div>
			</div>
			<div class="span8" style="background-color: white;">
				<table id="personListTable"
					class="table table-striped table-bordered">
					<thead>
						<tr>
							<th width="10%">No.</th>
							<th width="15%">Name</th>
							<th width="20%">Cell Phone</th>
							<th width="20%">Mail</th>
							<th width="20%">OU</th>
							<th width="15%">Description</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<!-- Le javascript================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="js/external/jquery.js" type="text/javascript"></script>
	<script src="js/external/application.js" type="text/javascript"></script>
	<script src="js/external/bootstrap-alert.js" type="text/javascript"></script>
	<script src="js/external/bootstrap-modal.js" type="text/javascript"></script>
	<script src="js/external/jquery.cookie.js" type="text/javascript"></script>
	<script src="js/external/bootstrap-transition.js"
		type="text/javascript"></script>
	<script src="js/common.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			//get user info form session
			var name = '<c:out value="${user.loginName}"></c:out>';
			var phone = '<c:out value="${user.cellPhone}"></c:out>';
			if (name != '') {
				$("#name").val(name);
				$("#cellphone").val(phone);
				$("#cellphoneBackup").val(phone);
				$("#userinfoDIV").css("display", "block");
				$("#loginTag").html("Sign out");
			} else {
				$("#userinfoDIV").css("display", "none");
			}
		});
	</script>
</body>
</html>