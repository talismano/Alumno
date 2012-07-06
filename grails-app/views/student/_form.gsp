<%@ page import="alumno.Student" %>



<div class="fieldcontain ${hasErrors(bean: studentInstance, field: 'email', 'error')} ">
	<label for="email">
		<g:message code="student.email.label" default="Email" />
		
	</label>
	<g:textField name="email" value="${studentInstance?.email}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: studentInstance, field: 'firstName', 'error')} ">
	<label for="firstName">
		<g:message code="student.firstName.label" default="First Name" />
		
	</label>
	<g:textField name="firstName" value="${studentInstance?.firstName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: studentInstance, field: 'grade', 'error')} required">
	<label for="grade">
		<g:message code="student.grade.label" default="Grade" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="grade" required="" value="${fieldValue(bean: studentInstance, field: 'grade')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: studentInstance, field: 'lastName', 'error')} ">
	<label for="lastName">
		<g:message code="student.lastName.label" default="Last Name" />
		
	</label>
	<g:textField name="lastName" value="${studentInstance?.lastName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: studentInstance, field: 'phoneNumber', 'error')} ">
	<label for="phoneNumber">
		<g:message code="student.phoneNumber.label" default="Phone Number" />
		
	</label>
	<g:textField name="phoneNumber" value="${studentInstance?.phoneNumber}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: studentInstance, field: 'registration', 'error')} required">
	<label for="registration">
		<g:message code="student.registration.label" default="Registration" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="registration" name="registration.id" from="${alumno.Registration.list()}" optionKey="id" required="" value="${studentInstance?.registration?.id}" class="many-to-one"/>
</div>

