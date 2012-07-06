
<%@ page import="alumno.Parent" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'parent.label', default: 'Parent')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-parent" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-parent" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="email" title="${message(code: 'parent.email.label', default: 'Email')}" />
					
						<g:sortableColumn property="firstName" title="${message(code: 'parent.firstName.label', default: 'First Name')}" />
					
						<g:sortableColumn property="grade" title="${message(code: 'parent.grade.label', default: 'Grade')}" />
					
						<th><g:message code="parent.household.label" default="Household" /></th>
					
						<g:sortableColumn property="lastName" title="${message(code: 'parent.lastName.label', default: 'Last Name')}" />
					
						<g:sortableColumn property="phoneNumber" title="${message(code: 'parent.phoneNumber.label', default: 'Phone Number')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${parentInstanceList}" status="i" var="parentInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${parentInstance.id}">${fieldValue(bean: parentInstance, field: "email")}</g:link></td>
					
						<td>${fieldValue(bean: parentInstance, field: "firstName")}</td>
					
						<td>${fieldValue(bean: parentInstance, field: "grade")}</td>
					
						<td>${fieldValue(bean: parentInstance, field: "household")}</td>
					
						<td>${fieldValue(bean: parentInstance, field: "lastName")}</td>
					
						<td>${fieldValue(bean: parentInstance, field: "phoneNumber")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${parentInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
