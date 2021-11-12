$(document).ready(function () {

    let response_glob;
    let rowPerPage = 3;
    let start = 0;
    let end = rowPerPage;
    let quizId_glob;
    let courseId_glob;
    let userInfo_glob;
    let selected_prof;
    let selected_students = [];


    $(".sidebar").after("<hr style='border-color: rgba(245,245,245,0.27)'>");
    $("#profile").after("<hr style='border-color: rgba(245,245,245,0.27)'>").css("textAlign", "center");

    // This function attaches a CSRF token to each Ajax request
    $(function () {
        let token = $("meta[name='_csrf']").attr("content");
        let header = $("meta[name='_csrf_header']").attr("content");
        $(document).ajaxSend(function (e, xhr, options) {
            xhr.setRequestHeader(header, token);
        });
    });

    // click on dashboard
    $("#dashboard").click(function () {
        display_on_off(".dashboard", "block");
    });

    // click on Courses
    $("#all_course").click(function () {
        display_on_off("#messages", "flex");
        start = 0;
        end = rowPerPage;
        $.ajax({
            url: `/professor/professor-courses`,
            method: "GET",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (response) {
                console.log(response);
                if (response.length === 0) {
                    $('#message_text').html("There are no course!!!");
                } else {
                    display_on_off(".courses", "flex");
                    response_glob = response;
                    $(function () {
                        $(".courses tr:has(td)").remove();
                        table_data(response, "course");
                    });
                }
            },
            error: function (errMsg) {
                console.log(errMsg);
            }
        });
    });

    // table data iteration
    function table_data(response, section) {
        let tr = [];
        $.each(response, function (i, item) {
            if (i < rowPerPage) {
                row_data_iteration(tr, i, item, section);
            }
        });
        if (section === "course")
            $("#records_table_body_professor_course").html(tr);
        else
            $("#records_table_body").html(tr);
    }

    // iterate among table row data
    function row_data_iteration(tr, i, item, section) {
        let $tr;
        if (item != null) {
            if (section === "course") {
                $tr = $('<tr>').append(
                    $('<td>').text(i + 1),
                    $('<td>').text(item.title),
                    $('<td>').text(item.courseId),
                    $('<td>').text(item.startDate),
                    $('<td>').text(item.endDate),
                    $('<td>').append(
                        `<button id="edit_info_button" type='button' onclick='$(this).quiz_information_course_table(\"${item.courseId}\")'>Info</button>`
                    )
                );
            } else if (section === "quiz_info") {
                $tr = $('<tr>').append(
                    $('<td>').text(i + 1),
                    $('<td>').text(item.title),
                    $('<td>').text(item.description),
                    $('<td>').text(item.quizTime),
                    $('<td>').append(
                        `<button id="edit_info_button" type='button' onclick='$(this).edit_quiz_information(\"${item.id}\")'>Edit</button>`
                    ),
                    $('<td>').append(
                        `<button id="delete_info_button" type='button' onclick='$(this).delete_quiz(\"${item.id}\")'>Delete</button>`
                    )
                );
            } else {
                $tr = $('<tr>').append(
                    $('<td>').text(i + 1),
                    $('<td>').text(item.firstName),
                    $('<td>').text(item.lastName),
                    $('<td>').text(item.username),
                    $('<td>').text(item.userType),
                    $('<td>').text(item.nationalCode),
                    $('<td>').append(`
                                            <label class="container">
                                                <input name="checkmarkInput" type="checkbox">
                                                <span class="checkmark"></span>
                                            </label>
                                        `)
                );
            }
            tr.push($tr[0]);
        }
    }

    // after click on back in quiz info page
    $.fn.quiz_back_function = function () {
        display_on_off(".courses", "flex");
        $("#all_course").trigger("click");
    };

    // after click on create in quiz info page
    $.fn.quiz_create_function = function () {
        display_on_off("#create_quiz", "flex");
    };

    // click on next button
    $("#next").click(function () {
        console.log(Object.keys(response_glob).length);
        if (end < Object.keys(response_glob).length) {
            start = start + rowPerPage;
            end = end + rowPerPage;
        }
        page_data_change();
    });

    // click on previous button
    $("#previous").click(function () {
        console.log(response_glob);
        if (start >= rowPerPage) {
            start = start - rowPerPage;
            end = end - rowPerPage;
        }
        page_data_change();
    });

    // function for change row per page
    function page_data_change() {
        let tr = [];
        $.each(response_glob, function (i, item) {
            if (i >= start && i < end) {
                row_data_iteration(tr, i, item, "course");
            }
        });
        $("#records_table_body_professor_course").html(tr);
    }

    // after click on submit in create quiz
    $(document).on('submit', '#submit_new_quiz', function (event) {
        event.preventDefault();

        let data = [
            {title: $("#quiz_title_id").val()},
            {description: $("#quiz_description_id").val()},
            {quizTime: $("#quiz_time_id").val()},
            {courseId: courseId_glob}
        ];
        console.log(data);

        $.ajax({
            url: "/professor/create-quiz",
            type: "POST",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify(data)
        }).done(function (response) {
            console.log(response);
            $("message_text").html("Create Quiz was successful!!!")
            display_on_off("#messages", "flex");

        }).fail(function (errMsg) {
            console.log(errMsg);
            $("message_text").html("Create Quiz was successful!!!")
            display_on_off("#messages", "flex");
        })
    });

    // click on information button in course table and get information from server and display on table
    $.fn.quiz_information_course_table = function (courseId) {
        courseId_glob = courseId;
        console.log(courseId);
        display_on_off("#course_table_quizInformation", "flex");

        $.ajax({
            url: `/professor/course-quiz${courseId}`,
            method: "GET",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (response) {
                console.log(response);
                course_table_data(response, "quiz_info");
            },
            error: function (errMsg) {
                console.log(errMsg);
            }
        });
    };

    // course table data iteration
    function course_table_data(response, section) {
        let tr = [];
        $.each(response, function (i, item) {
            row_data_iteration(tr, i, item, section);
        });
        if (section === "quiz_info")
            $("#course_table_quiz_info").html(tr);
        else
            $("#records_table_body_all_course").html(tr);
    }

    // click on edit button in quiz table and edit information
    $.fn.edit_quiz_information = function (quizId) {
        console.log(quizId);
        display_on_off("#edit_quiz_table", "flex");

        $.ajax({
            url: "/professor/edit-quiz",
            type: "POST",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: quizId,
            success: function (response) {
                console.log(response);
                quizId_glob = response.id;
                $('#quiz_edit_title').val(response.title);
                $('#quiz_edit_description').val(response.description);
                $('#quiz_edit_time').val(response.quizTime);
            },
            error: function (errMsg) {
                console.log(errMsg);
            }
        });
    };

    // click on delete button in quiz table and delete selected quiz
    $.fn.delete_quiz = function (quizId) {

        $.ajax({
            url: `/professor/delete-quiz`,
            method: "POST",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: quizId,
            success: function (response) {
                console.log(response);
                $("message_text").html("Delete Quiz was successful!!!")
                display_on_off("#messages", "flex");
            },
            error: function (errMsg) {
                console.log(errMsg);
                $("message_text").html("Delete Quiz wasn't successful!!!")
                display_on_off("#messages", "flex");
                for (let i = 0; i < 100000; i)
                    i++;
                $(this).quiz_information_course_table(courseId_glob);
            }
        });
    };

    // after click on save in course edit page
    $(document).on('submit', '#submit_quiz_change_info_data', function (event) {
        event.preventDefault();

        let data = [
            {title: $("#quiz_edit_title").val()},
            {description: $("#quiz_edit_description").val()},
            {quizTime: $("#quiz_edit_time").val()},
            {quizId: quizId_glob}
        ];
        console.log(data);

        $.ajax({
            url: "/professor/update-quiz",
            type: "POST",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify(data)
        }).done(function (response) {
            console.log(response);
            $("#message_text").html("Edit Quiz was successful!!!")
            display_on_off("#messages", "flex");
            // $(this).quiz_back_function();
        }).fail(function (errMsg) {
            console.log(errMsg);
        })
    });

    // change display view
    function display_on_off(divId, displayType) {
        if (`"${divId}"` !== "#add_new_course_table")
            $("#add_new_course_table").css("display", "none");
        if (`"${divId}"` !== ".dashboard")
            $(".dashboard").css("display", "none");
        if (`"${divId}"` !== ".courses")
            $(".courses").css("display", "none");
        if (`"${divId}"` !== ".all_users")
            $(".all_users").css("display", "none");
        if (`"${divId}"` !== "#user_information_table_edit")
            $("#user_information_table_edit").css("display", "none");
        if (`"${divId}"` !== "#add_new_course_table")
            $("#add_new_course_table").css("display", "none");
        if (`"${divId}"` !== ".course")
            $(".course").css("display", "none");
        if (`"${divId}"` !== "#edit_quiz_table")
            $("#edit_quiz_table").css("display", "none");
        if (`"${divId}"` !== "#edit_course_select_students")
            $('#edit_course_select_students').css('display', 'none');
        if (`"${divId}"` !== "#edit_course_select_professor")
            $("#edit_course_select_professor").css("display", "none");
        if (`"${divId}"` !== "#course_table_quizInformation")
            $("#course_table_quizInformation").css("display", "none");
        if (`"${divId}"` !== "#messages")
            $("#messages").css("display", "none");
        if (`"${divId}"` !== "#add_new_course")
            $("#add_new_course").css("display", "none");
        if (`"${divId}"` !== "#create_quiz")
            $("#create_quiz").css("display", "none");
        $(`${divId}`).css("display", `${displayType}`);
        $('#course_table_quiz_info').html("");

        $("#messageID").css("display", "none");
        $("#messageID_2").css("display", "none");

    };
})