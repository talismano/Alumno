<script type="text/javascript">
    var childCount = ${registrationInstance?.students.size()} + 0;

    function addStudent(){
        var clone = $("#student_clone").clone()
        var htmlId = 'studentsList['+childCount+'].';
        var studentInput = clone.find("input[id$=firstName]");

        clone.find("input[id$=id]")
                .attr('id',htmlId + 'id')
                .attr('name',htmlId + 'id');
        clone.find("input[id$=deleted]")
                .attr('id',htmlId + 'deleted')
                .attr('name',htmlId + 'deleted');
        clone.find("input[id$=new]")
                .attr('id',htmlId + 'new')
                .attr('name',htmlId + 'new')
                .attr('value', 'true');
        studentInput.attr('id',htmlId + 'firstName')
                .attr('name',htmlId + 'firstName');
        clone.find("select[id$=lastName]")
                .attr('id',htmlId + 'lastName')
                .attr('name',htmlId + 'lastName');

        clone.attr('id', 'student'+childCount);
        $("#childList").append(clone);
        clone.show();
        studentInput.focus();
        childCount++;
    }

    //bind click event on delete buttons using jquery live

    $('.del-student').live('click', function() {
        //find the parent div
        var prnt = $(this).parents(".student-div");
        //find the deleted hidden input
        var delInput = prnt.find("input[id$=deleted]");
        //check if this is still not persisted
        var newValue = prnt.find("input[id$=new]").attr('value');
        //if it is new then i can safely remove from dom
        if(newValue == 'true'){
            prnt.remove();
        }else{
            //set the deletedFlag to true
            delInput.attr('value','true');
            //hide the div
            prnt.hide();
        }

    });

</script>

<div id="childList">
    <g:each var="student" in="${registrationInstance.students}" status="i">

        <!-- Render the student template (_student.gsp) here -->
        <g:render template='student' model="['student':student,'i':i,'hidden':false]"/>
        <!-- Render the student template (_student.gsp) here -->

    </g:each>
</div>
<input type="button" value="Add Student" onclick="addStudent();" />