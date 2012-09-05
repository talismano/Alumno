<div>
    <table>
        <tbody>
        <tr>
            <td>
                <label for="students"><g:message code="registration.students.label" default="Students List" /></label>
            </td>
            <td>
                <!-- Render the students template (_students.gsp) here -->
                <g:render template="students" model="['registrationInstance':registrationInstance]" />
                <!-- Render the students template (_students.gsp) here -->
            </td>
        </tr>
        </tbody>
    </table>
</div>