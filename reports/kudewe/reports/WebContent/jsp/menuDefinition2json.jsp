<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<json:object>
	<json:array name="items" var="item" items="${menuDefinition}">
		<json:object>
			<json:property name="name" value="${item.name}"/>
			<json:property name="dashBoardUrl" value="${item.dashBoardUrl}"/>
			<json:array name="items" var="item" items="${item.items}">
				<json:object>
					<json:property name="name" value="${item.name}"/>
					<json:property name="dashBoardUrl" value="${item.dashBoardUrl}"/>
				</json:object>
			</json:array>
		</json:object>
	</json:array>
</json:object>