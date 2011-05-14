<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c-rt" %>
<json:object>
  <json:object name="look">
    <c-rt:forEach var="lookObject" items="${dashBoardDefinition.lookDefinition.properties}">
      <json:property name="${lookObject.name}" value="${lookObject.value}"/>
    </c-rt:forEach>
  </json:object>
  <json:array name="filters" var="filter" items="${dashBoardDefinition.filters}">
    <json:object>
      <json:property name="name" value="${filter.name}"/>
      <json:property name="url" value="${filter.url}"/>
      <json:property name="label" value="${filter.label}"/>
      <json:array name="dependencies" items="${filter.dataSourceDefinition.dependencies}"/>
      <json:array name="dependants" items="${filter.dependants}"/>
    </json:object>
  </json:array>
  <json:array name="views" var="view" items="${dashBoardDefinition.views}">
    <json:object>
      <json:property name="name" value="${view.name}"/>
      <json:property name="url" value="${view.url}"/>
      <json:array name="dependencies" var="dependency" items="${view.dataSourceDefinition.dependencies}"/>
      <c:set var="look" value="${lookDefinitionIterator.nextLook}"/>
      
      <json:object name="look">
      	<c-rt:forEach var="lookObject" items="${look.simpleProperties}">
      		<json:property name="${lookObject.name}" value="${lookObject.value}"/>
      	</c-rt:forEach>
      	
      	<c-rt:forEach var="lookObject" items="${look.complexProperties}">
      		<json:object name="${lookObject.name}">
	      		<c-rt:forEach var="lookObjectProperty" items="${lookObject.simpleProperties}">
		      		<json:property name="${lookObjectProperty.name}" value="${lookObjectProperty.value}"/>
		      	</c-rt:forEach>
      		</json:object>
      	</c-rt:forEach>
      	
      	<c-rt:forEach var="lookSimpleArray" items="${look.simpleArrayProperties}">
      		<json:array name="${lookSimpleArray.name}" var="lookSimpleArrayItem" items="${lookSimpleArray.value}">
      			<json:property value="${lookSimpleArrayItem}"/>
      		</json:array>
      	</c-rt:forEach>
      	
      	<c-rt:forEach var="lookComplexArray" items="${look.complexArrayProperties}">
      		<json:array name="${lookComplexArray.name}">
      			<c-rt:forEach var="look" items="${lookComplexArray.value}">
      				<json:object>
	      				<c-rt:forEach var="lookObject" items="${look.simpleProperties}">
      						<json:property name="${lookObject.name}" value="${lookObject.value}"/>
      					</c-rt:forEach>
      					
      					<c-rt:forEach var="lookObject" items="${look.complexProperties}">
				      		<json:object name="${lookObject.name}">
					      		<c-rt:forEach var="lookObjectProperty" items="${lookObject.simpleProperties}">
						      		<json:property name="${lookObjectProperty.name}" value="${lookObjectProperty.value}"/>
						      	</c-rt:forEach>
				      		</json:object>
				      	</c-rt:forEach>
				      	
      					<c-rt:forEach var="lookSimpleArray" items="${look.simpleArrayProperties}">
				      		<json:array name="${lookSimpleArray.name}" var="lookSimpleArrayItem" items="${lookSimpleArray.value}">
				      			<json:property value="${lookSimpleArrayItem}"/>
				      		</json:array>
				      	</c-rt:forEach>
      					
	      			</json:object>
      			</c-rt:forEach>
      		</json:array>
      	</c-rt:forEach>
      	
      </json:object>
    </json:object>
  </json:array>
</json:object>