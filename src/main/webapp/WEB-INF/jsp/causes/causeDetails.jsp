<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="cause">

	<h2>Causes Info
	</h2>
	<br />
	<h3 style="color: #ff5f5f;">Name</h3>
	<h4>
		<c:out value="${cause.name}" />
	</h4>
	<br />
	<h3 style="color: #ff5f5f;">Description</h3>
	<h4>
		<c:out value="${cause.description}" />
	</h4>
	<br />
	<h3 style="color: #ff5f5f;">Budget Target</h3>
	<h4>
		<c:out value="${cause.budgetTarget}" />
	</h4>
	<br />
	<h3 style="color: #ff5f5f;">Organization</h3>
	<h4>
		<c:out value="${cause.organization}" />
	</h4>
	<br>
	 <spring:url value="/causes/{causeId}/donations/new" var="danationUrl">
                        <spring:param name="causeId" value="${cause.id}"/>
                    </spring:url>
                    <a class="btn btn-default" href="${fn:escapeXml(danationUrl)}"><c:out value="Donate"/></a>
	<br><br>
	<br>
	<h2>Donations</h2>
	<br />
	<c:choose>
	<c:when test="${fn:length(donations) >0}">
	<table id="dontaionsTable" class="table table-striped">
		<thead>
			<tr>
				<th>Name</th>
				<th>Date</th>
				<th>Amount</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${donations}" var="donation">
				<tr>
					<td><c:out value="${donation.client}" /></td>
					<td><c:out value="${donation.date}" /></td>
					<td><c:out value="${donation.amount}" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</c:when>
	<c:otherwise>
	<h1>No donations yet</h1>
	</c:otherwise>
	</c:choose>
	</petclinic:layout>