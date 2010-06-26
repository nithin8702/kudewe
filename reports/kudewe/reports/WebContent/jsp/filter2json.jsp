<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<json:object>
  <json:array name="data" var="item" items="${filter.items}">
    <json:object>
      <json:property name="id" value="${item.value}"/>
      <json:property name="name" value="${item.description}"/>
    </json:object>
  </json:array>
</json:object>