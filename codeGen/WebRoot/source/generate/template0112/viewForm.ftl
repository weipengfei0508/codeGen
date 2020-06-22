<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>${functionName}管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${r"${ctx}"}/${urlPrefix}/">${functionName}列表</a></li>
		<li class="active"><a href="${r"${ctx}"}/${urlPrefix}/form?id=${"${"+className+".id}"}">${functionName}<shiro:hasPermission name="${permissionPrefix}:edit">${r"${not empty "+className+".id?'修改':'添加'}"}</shiro:hasPermission><shiro:lacksPermission name="${permissionPrefix}:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="${className}" action="${r"${ctx}"}/${urlPrefix}/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${r"${message}"}"/>
		<#list tbmodel as column>
		<#if column.column_key!="pri">
		<div class="control-group">
			<label class="control-label">${column.column_comment}:</label>
			<div class="controls">
				<form:input path="${column.column_name}" htmlEscape="false" maxlength="${column.column_type_num1}" <#if column.column_type_jname=="String"&&column.is_nullable=="no"&&column.column_key!="pri">class="required"</#if><#if column.is_nullable=="no"&&column.column_key!="pri"&&column.column_type_jname=="Integer">class="required digits"</#if>/>
			</div>
		</div>
		</#if>
		</#list>
		<div class="form-actions">
			<shiro:hasPermission name="${permissionPrefix}:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
