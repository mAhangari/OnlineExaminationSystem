$(document).ready(function () {

    let response_glob;
    let rowPerPage = 2;
    let start = 0;
    let end = rowPerPage;
    let userInfo_glob;
    let selected_prof;
    let selected_students = [];

    $(".sidebar").after("<hr color='whitesmoke'>");
    $("#profile").after("<hr color='whitesmoke'>").css("text-align", "center");

    // click on dashboard
    $("#dashboard").click(function () {
        display_on_off(".dashboard", "block");
    });

    // click on new_users
    $("#new_users").click(function () {
        display_on_off(".users", "flex");

        start = 0;
        end = rowPerPage;
        $.ajax({
            url: "/admin/new-users",
            method: "GET",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (response) {
                console.log(response);
                if (response.length === 0) {
                    display_on_off("#messages", "flex");
                    $('#message_text').html("There are no new users!!!");
                } else {

                    response_glob = response;
                    $(function () {
                        $(".users tr:has(td)").remove();
                        table_data(response);
                    });
                }
            },
            error: function (errMsg) {
                console.log(errMsg);
            }
        });
    });

    // click on all-user
    $("#all_users").click(function () {
        display_on_off(".all_users", "flex");
    });

    // click on course
    $("#course").click(function () {
        display_on_off(".course", "flex");
        $("#add_new_course").css("display", "flex");


        $.ajax({
            url: "/admin/all_courses",
            method: "GET",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (response) {
                console.log(response);
                $(function () {
                    $(".course tr:has(td)").remove();
                    course_table_data(response, "course");
                });
            },
            error: function (errMsg) {
                console.log(errMsg);
            }
        });

    });

    // click on accept button
    $('#accept').click(function () {
        $('#records_table_body tr').each(function () {
            if (($(this).find('input[name="checkmarkInput"]').is(":checked"))) {
                let username = $(this).find('td').eq(3).text();

                $.ajax({
                    url: "/admin/user-state",
                    method: "POST",
                    contentType: "application/json; charset=utf-8",
                    dataType: "json",
                    data: JSON.stringify({username: username, active: "accept"}),
                    success: function (response) {
                        console.log(response);
                        response_glob = response;
                        $(function () {
                            $(".users tr:has(td)").remove();
                            table_data(response);
                        });
                    },
                    error: function (errMsg) {
                        console.log(errMsg);
                    }
                });
            }
        });
    });

    // click on reject button
    $('#reject').click(function () {
        $('#records_table_body tr').each(function () {
            if (($(this).find('input[name="checkmarkInput"]').is(":checked"))) {
                let username = $(this).find('td').eq(3).text();

                $.ajax({
                    url: "/admin/user-state",
                    method: "POST",
                    contentType: "application/json; charset=utf-8",
                    dataType: "json",
                    data: JSON.stringify({username: username, active: "deny"}),
                    success: function (response) {
                        console.log(response);
                        response_glob = response;
                        $(function () {
                            $(".users tr:has(td)").remove();
                            table_data(response);
                        });
                    },
                    error: function (errMsg) {
                        console.log(errMsg);
                    }
                });
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
            $("#records_table_body_all_course").html(tr);
        else
            $("#records_table_body").html(tr);
    }

    // course table data iteration
    function course_table_data(response, section) {
        let tr = [];
        $.each(response, function (i, item) {
            row_data_iteration(tr, i, item, section);
        });
        if (section === "user_info")
            $("#course_table_user_info").html(tr);
        else
            $("#records_table_body_all_course").html(tr);
    }

    // click on next button
    $("#next").click(function () {
        console.log(Object.keys(response_glob).length);
        if (end < Object.keys(response_glob).length) {
            start = start + rowPerPage;
            end = end + rowPerPage;
        }
        page_date_change();
    });

    // click on next_all_users button
    $("#next_all_users").click(function () {
        console.log(Object.keys(response_glob).length);
        if (end < Object.keys(response_glob).length) {
            start = start + rowPerPage;
            end = end + rowPerPage;
        }
        all_user_page_date_change();
    });

    // click on previous button
    $("#previous").click(function () {
        console.log(response_glob);
        if (start >= rowPerPage) {
            start = start - rowPerPage;
            end = end - rowPerPage;
        }
        page_date_change();
    });

    // click on previous_all_users button
    $("#previous_all_users").click(function () {
        console.log(response_glob);
        if (start >= rowPerPage) {
            start = start - rowPerPage;
            end = end - rowPerPage;
        }
        all_user_page_date_change();
    });

    // function for change row per page
    function page_date_change() {
        let tr = [];
        $.each(response_glob, function (i, item) {
            if (i >= start && i < end) {
                row_data_iteration(tr, i, item);
            }
        });
        $("#records_table_body").html(tr);
    }

    // function for change row per page in all_users
    function all_user_page_date_change() {
        let tr = [];
        $.each(response_glob, function (i, item) {
            if (i >= start && i < end) {
                all_users_row_data_iteration(tr, i, item);
            }
        });
        $("#records_table_body_all_users").html(tr);
    }

    // iterate among table row data
    function row_data_iteration(tr, i, item, section) {
        let $tr;
        let lastName;
        if (item != null) {
            if (item.professor != null) {
                lastName = item.professor.lastName;
            } else lastName = '';
            if (section === "course") {
                $tr = $('<tr>').append(
                    $('<td>').text(i + 1),
                    $('<td>').text(item.title),
                    $('<td>').text(item.courseId),
                    $('<td>').text(item.startDate),
                    $('<td>').text(item.endDate),
                    $('<td>').text(lastName),
                    $('<td>').append(
                        `<button type='button' onclick='$(this).user_information_course_table(\"${item.courseId}\")'>Info</button>`
                    ),
                    $('<td>').append(
                        `<button type='button' onclick='$(this).edit_course_information(\"${item.courseId}\")'>Edit</button>`
                    )
                );
            } else if (section === "user_info") {
                $tr = $('<tr>').append(
                    $('<td>').text(i + 1),
                    $('<td>').text(item.firstName),
                    $('<td>').text(item.lastName),
                    $('<td>').text(item.username),
                    $('<td>').text(item.userType),
                    $('<td>').text(item.nationalCode)
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

    // iterate among table row data in all_users
    function all_users_row_data_iteration(tr, i, item) {
        let $tr = $('<tr>').append(
            $('<td>').text(i + 1),
            $('<td>').text(item.firstName),
            $('<td>').text(item.lastName),
            $('<td>').text(item.username),
            $('<td>').text(item.userType),
            $('<td>').text(item.nationalCode),
            $('<td>').append(
                `<button type='button' onclick='$(this).user_information(\"${item.username}\")'>Info</button>`
            )
        );
        tr.push($tr[0]);
    }

    // mark or unmark all rows after click
    $('.select-all-input').click(function () {
        if (this.checked) {
            $(':checkbox').prop('checked', true);
        } else {
            $(':checkbox').prop('checked', false);
        }
    });

    // after click on submit button this function will register user.
    $(document).on('submit', '#submitRegistration', function (event) {
        event.preventDefault();

        if (checkRepeatPass()) {
            let person;
            if ($("#userType").val() === "PROFESSOR") {

                person = {
                    firstName: $("#firstname").val(),
                    lastName: $("#lastnameID").val(),
                    username: $("#usernameID").val(),
                    password: $("#passID").val(),
                    nationalCode: $("#nationalCodeID").val(),
                    userType: $("#userType").val(),
                    personnelId: $("#userID").val()
                };
            } else {
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
                success: function (response) {
                    $('#messageID').html(response.responseText);
                    console.log(response);
                },
                error: function (errMsg) {
                    if (errMsg.responseText === 'Registration was successful!!! wait for confirmation.')
                        $('#messageID').css("color", "#0ab089").html(errMsg.responseText);
                    else
                        $('#messageID').css("color", "#ed4956").html(errMsg.responseText);
                    console.log(errMsg);
                }
            });
        }
    });

    // check password repeat is correct
    function checkRepeatPass() {
        $('#messageID').css("color", "#ed4956")
        let password = $('#passID').val();
        let repeat_pass = $('#pass-repeatID').val();

        if (password !== repeat_pass) {
            $("#messageID").html("Password doesn't match");
            return false;
        } else {
            return true;
        }
    }

    // This function attaches a CSRF token to each Ajax request
    $(function () {
        let token = $("meta[name='_csrf']").attr("content");
        let header = $("meta[name='_csrf_header']").attr("content");
        $(document).ajaxSend(function (e, xhr, options) {
            xhr.setRequestHeader(header, token);
        });
    });

    // click on search button in all_users page
    $(document).on('submit', '#get_user_by_filed', function (event) {
        event.preventDefault();
        $('#all_users_table').css('display', 'flex');
        $('#all_users_next_prev').css('display', 'flex');

        let data = [
            {userType: $("#user_type_search").val()},
            {firstName: $("#first_name").val()},
            {lastName: $("#last_name").val()},
            {username: $("#username").val()}
        ];

        console.log(JSON.stringify(data));

        $.ajax({
            method: "POST",
            url: "/admin/all-users",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (response) {
                console.log(response);
                if (response.length === 0) {
                    display_on_off("#messages", "flex");
                    $('#message_text').html("User Not exists!!!");
                } else {
                    $(function () {
                        response_glob = response;
                        all_user_response(response);
                    });
                }
            },
            error: function (errMsg) {
                console.log(errMsg);
            }
        });
    });

    // after click search button and get response data this function will process data
    function all_user_response(response) {
        start = 0;
        end = rowPerPage;

        $(".all_users tr:has(td)").remove();
        let tr = [];
        $.each(response, function (i, item) {
            if (i < rowPerPage) {
                let $tr = $('<tr>').append(
                    $('<td>').text(i + 1),
                    $('<td>').text(item.firstName),
                    $('<td>').text(item.lastName),
                    $('<td>').text(item.username),
                    $('<td>').text(item.userType),
                    $('<td>').text(item.nationalCode),
                    $('<td>').append(
                        `<button type='button' onclick='$(this).user_information(\"${item.username}\");'>Info</button>`
                    )
                );
                tr.push($tr[0]);
            }
        });
        $("#records_table_body_all_users").html(tr);
    }

    // show all professor after fetching
    function all_professor_response(response) {

        $("#edit_course_table_professor tr:has(td)").remove();
        let tr = [];
        $.each(response, function (i, item) {
            let $tr = $('<tr>').append(
                $('<td>').text(i + 1),
                $('<td>').text(item.firstName),
                $('<td>').text(item.lastName),
                $('<td>').text(item.username),
                $('<td>').text(item.userType),
                $('<td>').append(
                    `<button type='button' onclick='$(this).select_professor(\"${item.username}\");'>Select</button>`
                )
            );
            tr.push($tr[0]);
        });
        $("#edit_course_table_professor").html(tr);
    }

    // show all professor after fetching
    function all_students_response(response) {

        $("#course_table_body_select_students tr:has(td)").remove();
        let tr = [];
        $.each(response, function (i, item) {
            let $tr = $('<tr>').append(
                $('<td>').text(i + 1),
                $('<td>').text(item.firstName),
                $('<td>').text(item.lastName),
                $('<td>').text(item.username),
                $('<td>').text(item.userType),
                $('<td>').append(`
                                        <label class="container">
                                            <input name="checkmarkInput" type="checkbox">
                                            <span class="checkmark"></span>
                                        </label>
                                    `)
            );
            tr.push($tr[0]);
        });
        $("#course_table_body_select_students").html(tr);
    }

    // click on information button in all_user table and get information from server and display on table
    $.fn.user_information = function (username) {
        console.log(username);
        display_on_off("#user_information_table_edit", "flex");

        $.ajax({
            url: "/admin/user-info",
            method: "POST",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: username,
            success: function (response) {
                console.log(response);
                userInfo_glob = response;
                $('#user_info_first_name').html(response.firstName);
                $('#user_info_last_name').html(response.lastName);
                $('#user_info_username').html(response.username);
                $('#user_info_user_type').html(response.userType);
                $('#user_info_national_code').html(response.nationalCode);

                if (response.hasOwnProperty("studentId")) {
                    $('#user_info_user_id').html(response.studentId);
                } else {
                    $('#user_info_user_id').html(response.personnelId);
                }
            },
            error: function (errMsg) {
                console.log(errMsg);
            }
        });
    };

    // click on information button in course table and get information from server and display on table
    $.fn.user_information_course_table = function (courseId) {
        console.log(courseId);
        display_on_off("#course_table_userInformation", "flex");

        $.ajax({
            url: "/admin/course-user-info",
            method: "POST",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: courseId,
            success: function (response) {
                console.log(response);
                course_table_data(response, "user_info");
            },
            error: function (errMsg) {
                console.log(errMsg);
            }
        });
    };

    // click on edit button in course table and edit information
    $.fn.edit_course_information = function (courseId) {
        selected_students = [];
        selected_prof = null;
        console.log(courseId);
        display_on_off("#edit_course_table", "flex");

        $.ajax({
            url: "/admin/find_course",
            method: "POST",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: courseId,
            success: function (response) {
                console.log(response);
                $('#course_edit_title').val(response.title);
                $('#course_edit_courseId').val(response.courseId);
                $('#course_edit_start_date').val(response.startDate);
                $('#course_edit_end_date').val(response.endDate);
            },
            error: function (errMsg) {
                console.log(errMsg);
            }
        });
    };

    // after click on save button in page user information
    $.fn.user_info_save_function = function () {
        $("#user_info_button_change").css("display", "inline");
        $("#user_info_button_save").css("display", "none");

        let person = [
            {firstName: $("#firstnameID").val()},
            {lastName: $("#lastnameID").val()},
            {nationalCode: $("#nationalCodeID").val()},
            {userType: $("#userType").val()},
            {userId: $("#userID").val()},
            {username: userInfo_glob.username}
        ];

        $.ajax({
            url: "/admin/apply-user-change",
            type: "POST",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify(person)
        }).done(function (response) {
            console.log(response);
            $('#user_info_first_name').html($("#firstnameID").val());
            $('#user_info_last_name').html($("#lastnameID").val());
            $('#user_info_user_type').html($("#userType").val());
            $('#user_info_national_code').html($("#nationalCodeID").val());
            $('#user_info_user_id').html($("#userID").val());
        }).fail(function (errMsg) {
            console.log(errMsg);
        })
    };

    // after click on cancel button in page user information
    $.fn.user_info_cancel_function = function () {
        display_on_off(".all_users", "flex");
        $("#user_info_button_change").css("display", "inline");
        $("#user_info_button_save").css("display", "none");
        $("#search_user_by_field").trigger("click");
    };

    // after click on professor select button
    $.fn.select_professor = function (username) {
        $("#edit_course_select_professor").css("display", "none");
        selected_prof = username;
        console.log(selected_prof);
    };

    // after click on save students button
    $.fn.select_students = function () {
        selected_students = [];
        $("#edit_course_select_students").css("display", "none");

        $('#course_table_body_select_students tr').each(function () {
            if (($(this).find('input[name="checkmarkInput"]').is(":checked"))) {
                let username = $(this).find('td').eq(3).text();
                selected_students
                    .push({username: $(this).find('td').eq(3).text()});
            }
        });
        console.log(selected_students);
    };

    // after click on change button in page user information
    $.fn.user_info_change_function = function () {
        $("#user_info_button_change").css("display", "none");
        $("#user_info_button_save").css("display", "inline");

        $('#user_info_first_name').html(
            `<input id="firstnameID" type="text" name="firstName" value="${userInfo_glob.firstName}" required/>`
        );
        $('#user_info_last_name').html(
            `<input id="lastnameID" type="text" name="lastName" value="${userInfo_glob.lastName}" required/>`
        );

        $('#user_info_user_type').html(
            `<select id="userType">
                    <option value="PROFESSOR">PROFESSOR</option>
                    <option value="STUDENT">STUDENT</option>
                  </select>`
        );

        $('#user_info_national_code').html(
            `<input id="nationalCodeID" type="text" name="nationalCode" value="${userInfo_glob.nationalCode}" required/>`
        );

        if (userInfo_glob.hasOwnProperty("studentId")) {
            $('#user_info_user_id').html(
                `<input id="userID" type="text" name="studentId" value="${userInfo_glob.studentId}" required/>`
            );
        } else {
            $('#user_info_user_id').html(
                `<input id="userID" type="text" name="personnelId" value="${userInfo_glob.personnelId}" required/>`
            );
        }
    };

    // after click on add_course button this function create new page
    $.fn.add_course_function = function () {
        $('.course').css('display', 'none');
        $('#messageID').html('');
        $('#course_info_title').html('');
        $('#course_info_courseId').html('');
        $('#course_info_end_date').html('');
        $('#add_new_course_table').css('display', 'flex');
    };

    // after click on save in course creation page
    $(document).on('submit', '#submit_course_info_data', function (event) {
        event.preventDefault();

        let data = [
            {title: $("#course_info_title").val()},
            {courseId: $("#course_info_courseId").val()},
            {startDate: $("#course_info_start_date").val()},
            {endDate: $("#course_info_end_date").val()}
        ];

        $.ajax({
            url: "/admin/add_course",
            type: "POST",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify(data)
        }).done(function (response) {
            console.log(response);
            if (response.status === "success")
                $('#messageID').html(`<p style="color: #0f958a">Adding new Course was Successful!!!</p>`);
            else
                $('#messageID').html('<p style="color: #ed4956">Field is already exists!!!</p>');
        }).fail(function (errMsg) {
            console.log(errMsg);
        })
    });

    // after click on save in course edit page
    $(document).on('submit', '#submit_course_change_info_data', function (event) {
        event.preventDefault();

        let data = [
            {title: $("#course_edit_title").val()},
            {courseId: $("#course_edit_courseId").val()},
            {startDate: $("#course_edit_start_date").val()},
            {endDate: $("#course_edit_end_date").val()},
            {professor: selected_prof},
            {students: selected_students}
        ];
        console.log(data);

        $.ajax({
            url: "/admin/update-course",
            type: "POST",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify(data)
        }).done(function (response) {
            console.log(response);
            $('#messageID_2').html(`<p style="color: #0f958a">Adding new information was Successful!!!</p>`);
            $(this).course_info_cancel_function();

        }).fail(function (errMsg) {
            console.log(errMsg);
        })
    });

    // after click on cancel in course creation page
    $.fn.course_info_cancel_function = function () {
        display_on_off(".course", "flex");
        $("#course").trigger("click");
    };

    // after click on select professor
    $.fn.course_edit_select_professor = function () {
        $("#edit_course_select_professor").css("display", "flex");
        $('#edit_course_select_students').css('display', 'none');

        $.ajax({
            url: "/admin/get-professors",
            method: "GET",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (response) {
                console.log(response);
                $(function () {
                    all_professor_response(response);
                });
            },
            error: function (errMsg) {
                console.log(errMsg);
            }
        });
    };

    // after click on select students
    $.fn.course_edit_select_students = function () {
        $("#edit_course_select_professor").css("display", "none");
        $('#edit_course_select_students').css('display', 'flex');

        $.ajax({
            url: "/admin/get-students",
            method: "GET",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (response) {
                console.log(response);
                $(function () {
                    all_students_response(response);
                });
            },
            error: function (errMsg) {
                console.log(errMsg);
            }
        });
    };

    // after click on course edit button delete
    $.fn.course_edit_button_delete = function () {

        $.ajax({
            url: "/admin/delete-course",
            method: "POST",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: $('#course_edit_courseId').val(),
            success: function (response) {
                console.log(response);
                $('#messageID_2').html("Deleted Course successfully completed");
                $(this).course_info_cancel_function();
            },
            error: function (errMsg) {
                console.log(errMsg);
            }
        });
    };

    function display_on_off(divId, displayType) {
        if (`"${divId}"` !== "#add_new_course_table")
            $("#add_new_course_table").css("display", "none");
        if (`"${divId}"` !== ".dashboard")
            $(".dashboard").css("display", "none");
        if (`"${divId}"` !== ".users")
            $(".users").css("display", "none");
        if (`"${divId}"` !== ".all_users")
            $(".all_users").css("display", "none");
        if (`"${divId}"` !== "#user_information_table_edit")
            $("#user_information_table_edit").css("display", "none");
        if (`"${divId}"` !== "#add_new_course_table")
            $("#add_new_course_table").css("display", "none");
        if (`"${divId}"` !== ".course")
            $(".course").css("display", "none");
        if (`"${divId}"` !== "#edit_course_table")
            $("#edit_course_table").css("display", "none");
        if (`"${divId}"` !== "#edit_course_select_students")
            $('#edit_course_select_students').css('display', 'none');
        if (`"${divId}"` !== "#edit_course_select_professor")
            $("#edit_course_select_professor").css("display", "none");
        if (`"${divId}"` !== "#course_table_userInformation")
            $("#course_table_userInformation").css("display", "none");
        if (`"${divId}"` !== "#messages")
            $("#messages").css("display", "none");
        if (`"${divId}"` !== "#add_new_course")
            $("#add_new_course").css("display", "none");
        if (`"${divId}"` !== "#edit_course_table")
            $("#edit_course_table").css("display", "none");
        if (`"${divId}"` !== "#course_table_userInformation")
            $("#course_table_userInformation").css("display", "none");
        $(`${divId}`).css("display", `${displayType}`);
        $('#course_table_user_info').html("");

        $("#messageID").css("display", "none");
        $("#messageID_2").css("display", "none");

    };

});