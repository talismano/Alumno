<div id="student${i}" class="student-div" <g:if test="${hidden}">style="display:none;"</g:if>>
    <g:hiddenField name='studentsList[${i}].id' value='${student?.id}'/>
    <g:hiddenField name='studentsList[${i}].deleted' value='false'/>
    <g:hiddenField name='studentsList[${i}].new' value="${student?.id == null?'true':'false'}"/>

    <f:with bean="student">
        <f:field property="firstName"/>
        <f:field property="lastName"/>
        <f:field property="grade"/>
        <f:field property="phoneNumber"/>
        <f:field property="email"/>
    </f:with>

    <span class="del-student">
        <img src="${resource(dir:'images/skin', file:'icon_delete.png')}"
             style="vertical-align:middle;"/>
    </span>
</div>