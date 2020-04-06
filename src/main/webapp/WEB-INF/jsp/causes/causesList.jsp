<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="announcements">
    <h2>Causes</h2>

    <table id="CausesTable" class="table table-striped">
        <thead>
        <tr>
            <th>Name</th>
            <th>Budget Achieved</th>
            <th>Budget Target</th>
            <th>Options</th>
            <th>Donations</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${causes}" var="cause">
            <tr>
                <td>
                    <c:out value="${cause.name}"/>
                </td>
                <td>
                   <c:out value="${cause.getTotalAmount()}"/>
                </td>
				<td>
                    <c:out value="${cause.budgetTarget}"/>
                </td>     
                <td>
                    <spring:url value="/causes/{causeId}" var="CauseUrl">
                        <spring:param name="causeId" value="${cause.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(CauseUrl)}">Show Details</a>
                </td>    
                 <td>    
                 	         
                    <spring:url value="/causes/{causeId}/donations/new" var="danationUrl">
                        <spring:param name="causeId" value="${cause.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(danationUrl)}"><c:out value="Donate"/></a>
                </td>       
            </tr>
        </c:forEach>
        </tbody>
    </table>
 
    
	    <spring:url value="/causes/new" var="addUrl">
	    </spring:url>
	    <a href="${fn:escapeXml(addUrl)}" class="btn btn-default">Add New Cause</a>

</petclinic:layout>