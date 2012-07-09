
<%@ page import="alumno.Household" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'household.label', default: 'Household')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-household" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-household" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="address" title="${message(code: 'household.address.label', default: 'Address')}" />
					
						<g:sortableColumn property="city" title="${message(code: 'household.city.label', default: 'City')}" />
					
						<g:sortableColumn property="state" title="${message(code: 'household.state.label', default: 'State')}" />
					
						<g:sortableColumn property="zip" title="${message(code: 'household.zip.label', default: 'Zip')}" />
					
						<g:sortableColumn property="phoneNumber" title="${message(code: 'household.phoneNumber.label', default: 'Phone Number')}" />
					
						<th><g:message code="household.registration.label" default="Registration" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${householdInstanceList}" status="i" var="householdInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${householdInstance.id}">${fieldValue(bean: householdInstance, field: "address")}</g:link></td>
					
						<td>${fieldValue(bean: householdInstance, field: "city")}</td>
					
						<td>${fieldValue(bean: householdInstance, field: "state")}</td>
					
						<td>${fieldValue(bean: householdInstance, field: "zip")}</td>
					
						<td>${fieldValue(bean: householdInstance, field: "phoneNumber")}</td>
					
						<td>${fieldValue(bean: householdInstance, field: "registration")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${householdInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
