$(document).ready(function () {

    let quizId_glob;
    let courseId_glob;
    let clear_time_glob;
    let tr = [];
    $answers = [];

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
        display_on_off(".dashboard", "flex");
    });

    // click on fa-user-edit
    $("#fa-user-edit-click").click(function () {
        $("#professor-image").css('display', 'none');
        $("#add-professor-image").css('display', 'flex');
    });

    // click on Courses
    $("#all_course").click(function () {
        display_on_off("#messages", "flex");
        $start = 0;
        $end = $rowPerPage;
        $.ajax({
            url: `/student/student-courses`,
            method: "GET",
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (response) {
            if (response.length === 0) {
                $('#message_text').html("There is no course!!!");
            } else {
                display_on_off(".courses", "flex");
                $response_glob = response;
                $(function () {
                    $(".courses tr:has(td)").remove();
                    $(this).next_previous_button();
                    table_data(response, "course");
                });
            }
        }).fail(function (errMsg) {
            console.log(errMsg);
        });
    });

    // click on Quizzes
    $("#all_quizzes").click(function () {
        display_on_off("#messages", "flex");
        $.ajax({
            url: `/professor/all-quizzes`,
            method: "GET",
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (response) {
            if (response.length === 0) {
                $('#message_text').html("There is no quiz!!!");
            } else {
                display_on_off("#quiz_table", "flex");
                $response_glob = response;
                $(function () {
                    course_table_data(response, "all_quiz_info");
                });
            }
        }).fail(function (errMsg) {
            console.log(errMsg);
        });
    });

    // table data iteration
    function table_data(response, section) {
        let tr = [];
        $.each(response, function (i, item) {
            if (i < $rowPerPage) {
                row_data_iteration(tr, i, item, section);
            }
        });
        if (section === "course")
            $("#records_table_body_student_course").html(tr);
        else
            $("#records_table_body").html(tr);
    }

    // iterate among table row data
    function row_data_iteration(tr, i, item, section) {
        let $tr;
        let lastName = "";
        if (item != null) {
            if (section === "course") {
                if (item.professor.hasOwnProperty("lastName")) {
                    lastName = item.professor["lastName"];
                }
                $tr = $('<tr>').append(
                    $('<td>').text(i + 1),
                    $('<td>').text(item.title),
                    $('<td>').text(item.courseId),
                    $('<td>').text(item.startDate),
                    $('<td>').text(item.endDate),
                    $('<td>').text(lastName),
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
                        `<button id="edit_info_button" type='button' onclick='$(this).start_quiz(\"${item.id}\")'>start</button>`
                    )
                );
            } else if (section === "all_quiz_info") {
                $tr = $('<tr>').append(
                    $('<td>').text(i + 1),
                    $('<td>').text(item.title),
                    $('<td>').text(item.description),
                    $('<td>').text(item.quizTime),
                    $('<td>').append(
                        `<button id="question_sheet_button" type='button' onclick='$(this).question_sheet_information(\"${item.id}\")'>Question Sheet</button>`
                    )
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

    // return to quizzes table if question sheet is empty
    $.fn.question_sheet_back_function = function () {
        display_on_off("#quiz_table", "flex");
        $("#all_quizzes").trigger("click")
    };

    // return to dashboard
    $.fn.dashboard_back_function = function () {
        display_on_off(".dashboard", "flex");
        location.reload();
    };

    // after apply score to the question
    $.fn.successful_apply_score = function () {
        $(this).question_sheet_information(quizId_glob);
    };

    // after click on save in add essays
    $.fn.add_essays_back_function = function () {
        display_on_off("#quiz-section", "flex");
    };

    // after click on add in question bank table
    $.fn.add_question_back_function = function () {
        display_on_off("#quiz-section", "flex");
        $("#question_bank").css("display", "inline");
    };

    // after click on create in quiz info page
    $.fn.quiz_create_function = function () {
        display_on_off("#create_quiz", "flex");
    };

    // function for change row per page
    $.fn.page_data_change = function () {
        let tr = [];
        $.each($response_glob, function (i, item) {
            if (i >= $start && i < $end) {
                row_data_iteration(tr, i, item, "course");
            }
        });
        $("#records_table_body_all_course").html(tr);
    }

    // click on information button in course table and get information from server and display on table
    $.fn.quiz_information_course_table = function (courseId) {
        courseId_glob = courseId;
        display_on_off("#course_table_quizInformation", "flex");

        $.ajax({
            url: `/student/course-quiz${courseId}`,
            method: "GET",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (response) {
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
        else if (section === "all_quiz_info")
            $("#quiz_info_tbody").html(tr);
        else
            $("#records_table_body_all_course").html(tr);
    }

    // click on start button in quiz table
    $.fn.start_quiz = function (quizId) {
        quizId_glob = quizId;

        $.ajax({
            url: `/student/start-quiz/${quizId}`,
            type: "GET",
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (response) {
            console.log(response);
            display_on_off("#quiz-section", "flex");
            $(this).timer_function(response.quizTimeLeft);
            $(this).createQuestions(response.questions);

        }).fail(function (errMsg) {
            $("#message_text").html(errMsg.responseText)
            display_on_off("#messages", "flex");
            setTimeout($(this).quiz_back_function, 1500);
        });
    };

    // after quiz time is finished this function will be call from inside the timer_function method
    $.fn.end_of_quiz = function () {

        let answers = [{quizId: quizId_glob}];

        for (let index = 0; index < $answers.length; index++) {
            console.log($answers[index]);
            answers.push($answers[index]);
        }

        $.ajax({
            url: "/student/set-answer",
            method: "POST",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify(answers)
        }).done(function (response) {
            clearInterval(clear_time_glob);
            $("#message_text").html("Answer saved successfully!!!");
            display_on_off("#messages", "flex");
            setTimeout($(this).quiz_back_function, 1500);

        }).fail(function (errMsg) {
            console.log(errMsg);
        });
    }

    // create question table
    $.fn.createQuestions = function (question_list) {
        $("#question_sheet_table").css("display", "block");
        $(this).iteration_input(question_list, tr);
        $(this).create_answer();
    };

    $.fn.create_answer = function () {
        $.each(tr, function (i, item) {
            if (item.hasOwnProperty("option")) {
                if ($(`#question_option_${$answers[i].questionId}`).find('td').eq(1).text() !== "") {
                    $("#questions_table_body tr").each(function () {
                        if (($(this).find('input[name="checkmarkInput"]').is(":checked"))) {
                            let option_val = $(this).find('td').eq(1).text();
                            let result = option_val.match(/(?<=\d:\s\s\s).+/);
                            $answers[i].answer = result[0];
                        }
                    });
                }

            } else if (item.hasOwnProperty("context")) {
                if ($(`#question_context_${$answers[i].questionId}`).val()) {

                    let answer_val = $(`#question_context_${$answers[i].questionId}`).val();
                    $answers[i].answer = answer_val;
                }
            }
        })
    }

    // timer function
    $.fn.timer_function = function (time) {

        let minutes = time;
        let time_limit = ((minutes) * 1000);
        let countdown = document.getElementById("tiles"); // get tag element

        let clear_time = setInterval(function () {
            minutes--;
            $('.timer').text($(this).get_elapsed_time_string(minutes));
            if (minutes === 0) {
                clearInterval(clear_time);
                $(this).end_of_quiz();
            }
        }, 1000);

        clear_time_glob = clear_time;

        $.fn.get_elapsed_time_string = function (total_seconds) {

            function pretty_time_string(num) {
                return (num < 10 ? "0" : "") + num;
            }

            if ((total_seconds * 1000) < (time_limit / 2))
                $('#tiles').removeClass('color-full').addClass('color-half');

            if ((total_seconds * 1000) < (time_limit / 4))
                $('#tiles').removeClass('color-half').addClass('color-empty');

            let hours = Math.floor(total_seconds / 3600);
            total_seconds = total_seconds % 3600;

            let minutes = Math.floor(total_seconds / 60);
            total_seconds = total_seconds % 60;

            let seconds = Math.floor(total_seconds);

            // Pad the minutes and seconds with leading zeros, if required
            hours = pretty_time_string(hours);
            minutes = pretty_time_string(minutes);
            seconds = pretty_time_string(seconds);

            // format countdown string + set tag value
            countdown.innerHTML = "<span>" + hours + ":</span><span>" + minutes + ":</span><span>" + seconds + "</span>";
        }
    }

    // after click on save in quiz answer section
    $(document).on('submit', '#submit_quiz_info_data', function (event) {
        event.preventDefault();

        for (let i = 0; i < $answers.length; i++) {
            if (!$answers[i].hasOwnProperty("answer")) {
                $answers[i].answer = "";
            }
        }
        $(this).end_of_quiz();
    });

    // after click on submit in add professor image
    $("#add-student-image").on('submit', function (event) {
        event.preventDefault();
        let formData = new FormData(this);

        $.ajax({
            url: "/image/upload-profile-picture",
            type: "POST",
            enctype: "multipart/form-data",
            contentType: false,
            processData: false,
            dataType: "json",
            data: formData,
            cache: false
        }).done(function (response) {
            $("#message_text").html("upload image was successful!!!")
            display_on_off("#messages", "flex");
            setTimeout($(this).dashboard_back_function, 1500);
        }).fail(function (errMsg) {
            $("#message_text").html(errMsg.responseJSON.message)
            display_on_off("#messages", "flex");
            setTimeout($(this).dashboard_back_function, 1500);

        })
    });

    // after load dashboard
    $(function () {

        $.ajax({
            url: "/image/get-profile-picture",
            type: "GET",
            contentType: "application/octet-stream",
            processData: false,

        }).done(function (response) {
            $('#professor-image').attr('src', `data:image/jpeg;base64, ${response}`);
        }).fail(function (errMsg) {
            console.log(errMsg);
        })
    });

    // change display view
    function display_on_off(divId, displayType) {
        if (`"${divId}"` !== ".dashboard")
            $(".dashboard").css("display", "none");
        if (`"${divId}"` !== ".courses")
            $(".courses").css("display", "none");
        if (`"${divId}"` !== ".all_users")
            $(".all_users").css("display", "none");
        if (`"${divId}"` !== ".course")
            $(".course").css("display", "none");
        if (`"${divId}"` !== "#quiz-section")
            $("#quiz-section").css("display", "none");
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
        if (`"${divId}"` !== "#add_quiz_button")
            $("#add_quiz_button").css("display", "none");
        if (`"${divId}"` !== "#question_source_button")
            $("#question_source_button").css("display", "none");
        if (`"${divId}"` !== "#add_essays")
            $("#add_essays").css("display", "none");
        if (`"${divId}"` !== "#add_multipleChoice")
            $("#add_multipleChoice").css("display", "none");
        if (`"${divId}"` !== "#question_bank")
            $("#question_bank").css("display", "none");
        if (`"${divId}"` !== "#quiz_table")
            $("#quiz_table").css("display", "none");
        if (`"${divId}"` !== "#question_sheet_table")
            $("#question_sheet_table").css("display", "none");
        if (`"${divId}"` !== "#previousAndNextButton")
            $("#previousAndNextButton").css("display", "none");

        $(`${divId}`).css("display", `${displayType}`);
        $('#course_table_quiz_info').html("");

        $("#messageID").css("display", "none");
        $("#messageID_2").css("display", "none");

    };
})