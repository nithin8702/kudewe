<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c-rt" %>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<json:object>
	<json:array name="data">
		<c-rt:forEach var="position" items="${view.data.axes[1].positions}">
			<json:object>
				<c-rt:forEach var="member" items="${position}">
					<c:set var="ident" value=""/>
					<c-rt:forEach begin="1" end="${member.depth}">
						 <c:if test='${lookType == "ext.grid"}'>
        					<c:set var="ident" value="${ident}&nbsp;&nbsp;&nbsp;"/>
    					</c:if>
					</c-rt:forEach>
					<json:property name="${member.dimension.caption}" value="${ident}${member.caption}" trim="false" escapeXml="false"/>
				</c-rt:forEach>
				
				<c-rt:forEach var="position" items="${view.data.axes[0].positions}">
					<c-rt:forEach var="member" items="${position}">
						<json:property name="${member.caption}" value="${viewAdapter.cellValueBigDecimal}"/>
						<c:set var="col" value="${viewAdapter.col}"/>
					</c-rt:forEach>
				</c-rt:forEach>
	    	</json:object>
	    	<c:set var="row" value="${viewAdapter.row}"/>
		</c-rt:forEach>
  	</json:array>
</json:object>