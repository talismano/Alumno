<%@ page import="alumno.Registration" %>



<div class="fieldcontain ${hasErrors(bean: registrationInstance, field: 'households', 'error')} ">
	<label for="households">
		<g:message code="registration.households.label" default="Households" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${registrationInstance?.households?}" var="h">
    <li><g:link controller="household" action="show" id="${h.id}">${h?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="household" action="create" params="['registration.id': registrationInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'household.label', default: 'Household')])}</g:link>
</li>
</ul>

</div>

<div class="fieldcontain ${hasErrors(bean: registrationInstance, field: 'students', 'error')} ">
	<label for="students">
		<g:message code="registration.students.label" default="Students" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${registrationInstance?.students?}" var="s">
    <li><g:link controller="student" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="student" action="create" params="['registration.id': registrationInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'student.label', default: 'Student')])}</g:link>
</li>
</ul>

</div>

