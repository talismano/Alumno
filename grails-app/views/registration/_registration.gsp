<div class="dialog">
    <table>
        <tbody>
        <tr class="prop">
            <td valign="top" class="name">
                <label for="students"><g:message code="contact.phones.label" default="Students List" /></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: registrationInstance, field: 'students', 'errors')}">

                <!-- Render the students template (_students.gsp) here -->
                <g:render template="students" model="['registrationInstance':registrationInstance]" />
                <!-- Render the students template (_students.gsp) here -->

            </td>
        </tr>
        </tbody>
    </table>
</div>