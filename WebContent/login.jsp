<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>高博起重</title>
<link href="css/header.css" rel="stylesheet" type="text/css" />
<link href="css/main.css" rel="stylesheet" type="text/css" />
</head>
<body style="font: 15px Verdana, Arial, Helvetica,"宋体",sans-serif">
	<div id="header-wrap">
		<div id="header" class="cbody">
			<div id="language">
				<table>
					<tr>
						<td width="173"><h1>
								<a href="http://www.gorbel.com.cn/"><img
									src="http://www.gorbel.com.cn/theme/skins/2009/images/logo.png"
									alt="起重机/起重设备" /></a>
							</h1></td>
						<td width="440"><div align="center">
								<font size="+2" color="#CC6600"><b>休假&加班管理</b></font>
							</div></td>
						<td width="173">Welcome <s:property
								value="#session.user.displayName" />
						</td>
					</tr>
				</table>
			</div>
			<div class="headyyright"></div>
		</div>
	</div>
	<section class="container">
	<div class="login">
		<h1>系统登录</h1>
		<form method="post" action="login.action">
			<p>
				<label>用戶名</label><input type="text" name="quser.usrName" value="" id='login'
					placeholder="Username">
			</p>
			<p>
				<label>密  码</label><input type="password" name="quser.usrPassword" value=""
					placeholder="Password">
			</p>
			<!--<p class="remember_me">
				<label> <input type="checkbox" name="remember_me"
					id="remember_me"> Remember me on this computer
				</label>
			</p>  -->
			<p class="submit">
				<input type="submit" name="commit" value="登录(Sign in)">	
			</p>
		</form><s:actionmessage />
	</div>
	</section>
	<!-- <div align="center"	>
		<div style="height: 200px;"></div>
		<div class="easyui-panel" title="" style="width: 300px;">
			<form action="login" method="post">
				<table>
					<tr>
						<td>用户名:</td>
						<td><input name="quser.usrName" /></td>
					</tr>
					<tr>
						<td>密    码:</td>
						<td><input type="password" name="quser.usrPassword" /></td>
					</tr>
					<tr>
						<td colspan="2"><input type="submit" value="登陆"></input></td>
					</tr>
				</table>
				<s:actionmessage />
			</form>
		</div><div style="height: 200px"></div>
	</div> -->
	<%@include file="../footer.jsp"%>