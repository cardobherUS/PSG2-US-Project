
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="owners">

    <h2>Owner Information</h2>


    <table class="table table-striped">
        <tr>
            <th>Name</th>
            <td><b><c:out value="${owner.firstName} ${owner.lastName}"/></b></td>
        </tr>
        <tr>
        <th>Name</th>
            <td><c:out value="${owner.dni}"/></td>
        </tr>
        <tr>
            <th>Address</th>
            <td><c:out value="${owner.address}"/></td>
        </tr>
        <tr>
            <th>City</th>
            <td><c:out value="${owner.city}"/></td>
        </tr>
        <tr>
            <th>Telephone</th>
            <td><c:out value="${owner.telephone}"/></td>
        </tr>
    </table>

    <spring:url value="{ownerId}/edit" var="editUrl">
        <spring:param name="ownerId" value="${owner.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Edit Owner</a>
    
    <spring:url value="{ownerId}/delete" var="deleteUrl">
        <spring:param name="ownerId" value="${owner.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(deleteUrl)}" class="btn btn-default">Delete Owner</a>

    <spring:url value="{ownerId}/pets/new" var="addUrl">
        <spring:param name="ownerId" value="${owner.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(addUrl)}" class="btn btn-default">Add New Pet</a>

    <br/>
    <br/>
    <br/>
    <h2>Pets, Visits and Hotel Books</h2>

    <table class="table table-striped">
        <c:forEach var="pet" items="${owner.pets}">

            <tr>
                <td valign="top">
                    <dl class="dl-horizontal">
                        <dt>Name</dt>
                        <dd><c:out value="${pet.name}"/></dd>
                        <dt>Birth Date</dt>
                        <dd><petclinic:localDate date="${pet.birthDate}" pattern="yyyy-MM-dd"/></dd>
                        <dt>Type</dt>
                        <dd><c:out value="${pet.type.name}"/></dd>
                    </dl>
                </td>
                <td valign="top">
                    <table class="table-condensed">
                        <thead>
                        <tr align="left">
                        	<th>Visits:</th>
                            <th>Date</th>
                            <th>Description</th>
                        </tr>
                        </thead>
                        <c:forEach var="visit" items="${pet.visits}">
                            <tr>
                            	<td></td>
                                <td><petclinic:localDate date="${visit.date}" pattern="yyyy-MM-dd"/></td>
                                <td><c:out value="${visit.description}"/></td>
                                <td>
                                <spring:url value="/owners/{ownerId}/pets/{petId}/visits/{visitId}/delete" var="visitDeleteUrl">
                                    <spring:param name="visitId" value="${visit.id}"/>
                                    <spring:param name="petId" value="${visit.pet.id}"/>
                                    <spring:param name="ownerId" value="${visit.pet.owner.id}"/>
                                </spring:url>
                                <a href="${fn:escapeXml(visitDeleteUrl)}">Delete visit</a>
                            </td>
                            </tr>
                        </c:forEach>
                        <thead>
                        <tr align="right">
                        	<th>Hotel Books:</th>
                            <th>Start Date</th>
                            <th>Finish Date</th>
                        </tr>
                        </thead>
                        <c:forEach var="hotel" items="${pet.hotels}">
                            <tr>
                            	<td></td>
                                <td><petclinic:localDate date="${hotel.startDate}" pattern="yyyy-MM-dd"/></td>
                                <td><petclinic:localDate date="${hotel.finishDate}" pattern="yyyy-MM-dd"/></td>
                            	<td>
                                <spring:url value="/owners/{ownerId}/pets/{petId}/hotels/{hotelId}/delete" var="hotelDeleteUrl">
                                    <spring:param name="hotelId" value="${hotel.id}"/>
                                    <spring:param name="petId" value="${hotel.pet.id}"/>
                                    <spring:param name="ownerId" value="${hotel.pet.owner.id}"/>
                                </spring:url>
                                <a href="${fn:escapeXml(hotelDeleteUrl)}">Delete hotel book</a>
                            </td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td>
                                <spring:url value="/owners/{ownerId}/pets/{petId}/edit" var="petUrl">
                                    <spring:param name="ownerId" value="${owner.id}"/>
                                    <spring:param name="petId" value="${pet.id}"/>
                                </spring:url>
                                <a href="${fn:escapeXml(petUrl)}">Edit Pet</a>
                            </td>
                            <td>
                                <spring:url value="/owners/{ownerId}/pets/{petId}/delete" var="deletePetUrl">
                                    <spring:param name="ownerId" value="${owner.id}"/>
                                    <spring:param name="petId" value="${pet.id}"/>
                                </spring:url>
                                <a href="${fn:escapeXml(deletePetUrl)}">Delete Pet</a>
                            </td>
                            <td>
                                <spring:url value="/owners/{ownerId}/pets/{petId}/visits/new" var="visitUrl">
                                    <spring:param name="ownerId" value="${owner.id}"/>
                                    <spring:param name="petId" value="${pet.id}"/>
                                </spring:url>
                                <a href="${fn:escapeXml(visitUrl)}">Add Visit</a>
                             </td>
                             <td>
                                <spring:url value="/owners/{ownerId}/pets/{petId}/hotels/new" var="hotelUrl">
                                    <spring:param name="ownerId" value="${owner.id}"/>
                                    <spring:param name="petId" value="${pet.id}"/>
                                </spring:url>
                                <a href="${fn:escapeXml(hotelUrl)}">Add Hotel Book</a>
                             </td>
                        </tr>
                    </table>
                </td>
            </tr>

        </c:forEach>
    </table>

</petclinic:layout>
