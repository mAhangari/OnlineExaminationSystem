$(document).ready(function() {
    $(".sidebar").after("<hr color='whitesmoke'>");
    $("#profile").after("<hr color='whitesmoke'>");
    $("#profile").css("text-align", "center");

    $("#dashboard").click(function(){
        // $(".users").css("visibility", "none");
        $(".users").css("display", "none");
        $(".dashboard").css("display", "block");
    })

    $("#users").click(function(){
        $(".dashboard").css("display", "none");
        $(".users").css("display", "flex");
    })

    $('#select-all-input').click(function() {
        if (this.checked) {
            $(':checkbox').prop('checked', true);
        } else {
            $(':checkbox').prop('checked', false);
        }
    });

    $('#submitRegistration').click(function(){
        let person;
        if($("#userType").val() === "PROFESSOR") {

            person = {
                firstName: $("#firstname").val(),
                lastName: $("#lastnameID").val(),
                username: $("#usernameID").val(),
                password: $("#passID").val(),
                nationalCode: $("#nationalCodeID").val(),
                userType: $("#userType").val(),
                personnelId: $("#userID").val()
            };
        }
        else {
            person = {
                firstName: $("#firstname").val(),
                lastName: $("#lastnameID").val(),
                username: $("#usernameID").val(),
                password: $("#passID").val(),
                nationalCode: $("#nationalCodeID").val(),
                userType: $("#userType").val(),
                studentId: $("#userID").val()
            };
        }


        $.ajax({
            type: "POST",
            url: "/user/add-user",
            data: JSON.stringify(person),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function(response){
                $('#messageID').html(response.responseText);
                console.log(response);
                },
            error: function(errMsg) {
                $('#messageID').html(errMsg.responseText);
                console.log(errMsg);
            }
        });
    });


})