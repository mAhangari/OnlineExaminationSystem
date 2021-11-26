$(document).ready(function () {

    let quizId_glob;
    let courseId_glob;
    let answerId_glob;
    let total_score = 0;
    let score_id_list = [];


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
            url: `/professor/professor-courses`,
            method: "GET",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (response) {
                if (response.length === 0) {
                    $('#message_text').html("There is no course!!!");
                } else {
                    display_on_off(".courses", "flex");
                    set_response_glob(response);
                    $(function () {
                        $(".courses tr:has(td)").remove();
                        $(this).next_previous_button("course");
                        table_data(response, "course");
                    });
                }
            },
            error: function (errMsg) {
                console.log(errMsg);
            }
        });
    });

    function set_response_glob(response) {
        $response_glob = response;
    }

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
                set_response_glob(response);
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
            $("#records_table_body_professor_course").html(tr);
        else if (section === "participants_info")
            $("#quiz_participants_info_tbody").html(tr);
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
            } else if (section === "all_quiz_info") {
                $tr = $('<tr>').append(
                    $('<td>').text(i + 1),
                    $('<td>').text(item.title),
                    $('<td>').text(item.description),
                    $('<td>').text(item.quizTime),
                    $('<td>').append(
                        `<button id="question_sheet_button" type='button' onclick='$(this).question_sheet_information(\"${item.id}\")'>Question Sheet</button>`
                    ),
                    $('<td>').append(
                        `<button id="examiners_information" type='button' onclick='$(this).get_participants(\"${item.id}\")'>Information</button>`
                    )
                );
            } else if (section === "participants_info") {
                console.log(item.score);
                $tr = $('<tr>').append(
                    $('<td>').text(i + 1),
                    $('<td>').text(`${item.student.firstName} ${item.student.lastName}`),
                    $('<td>').text(`${item.score}`),
                    $('<td>').append(`<button id="edit_info_button" type='button' onclick='$(this).answer_information(${JSON.stringify(item)})'>Info</button>`)
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

    // return to quizzes table if question sheet is empty
    $.fn.question_sheet_back_function = function () {
        display_on_off("#quiz_table", "flex");
        $("#all_quizzes").trigger("click")
    };

    // return to quizzes table if question sheet is empty
    $.fn.dashboard_back_function = function () {
        display_on_off(".dashboard", "flex");
        $("#dashboard").trigger("click");
    };

    // after apply score to the question
    $.fn.successful_apply_score = function () {
        $(this).question_sheet_information(quizId_glob);
    };

    // after apply score to the answer
    $.fn.successful_apply_score_toAnswer = function () {
        $(this).get_participants(quizId_glob);
    };

    // after click on question sheet in quizzes table
    $.fn.question_sheet_information = function (quizId) {
        quizId_glob = quizId;
        $.ajax({
            url: `/professor/get-question-sheet${quizId}`,
            type: "GET",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
        }).done(function (response) {
            if (response.length === 0) {
                $("#message_text").html("There is no question sheet!!!");
                display_on_off("#messages", "flex");
                setTimeout($(this).question_sheet_back_function, 1500);
            } else {
                display_on_off("#question_sheet_table", "flex");
                createQuestions(response);
            }

        }).fail(function (errMsg) {
            console.log(errMsg);
        })
    };

    // after click on information button in quizzes table
    $.fn.get_participants = function (quizId) {
        quizId_glob = quizId;
        $.ajax({
            url: `/professor/get-participants/${quizId}`,
            type: "GET",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
        }).done(function (response) {
            if (response.length === 0) {
                $("#message_text").html("There is no participants yet!!!");
                display_on_off("#messages", "flex");
                setTimeout($(this).question_sheet_back_function, 1500);
            } else {
                set_response_glob(response);
                display_on_off("#participants_table", "flex");
                $(this).next_previous_button("participants_info");
                table_data(response, "participants_info");
            }

        }).fail(function (errMsg) {
            console.log(errMsg);
        })
    };

    // after click on save in add essays
    $.fn.add_essays_back_function = function () {
        display_on_off("#edit_quiz_table", "flex");
    };

    // after click on add in question bank table
    $.fn.add_question_back_function = function () {
        display_on_off("#edit_quiz_table", "flex");
        $("#question_bank").css("display", "inline");
    };

    // after click on create in quiz info page
    $.fn.quiz_create_function = function () {
        display_on_off("#create_quiz", "flex");
    };

    // function for change row per page
    $.fn.page_data_change = function (section) {
        let tr = [];
        $.each($response_glob, function (i, item) {
            if (i >= $start && i < $end) {
                row_data_iteration(tr, i, item, section);
            }
        });
        $("#records_table_body_all_course").html(tr);
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

        $.ajax({
            url: "/professor/create-quiz",
            type: "POST",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify(data)
        }).done(function (response) {
            $("#message_text").html("Create Quiz was successful!!!")
            display_on_off("#messages", "flex");
            setTimeout($(this).quiz_back_function, 1500);

        }).fail(function (errMsg) {
            console.log(errMsg);
        });
    });

    // click on information button in course table and get information from server and display on table
    $.fn.quiz_information_course_table = function (courseId) {
        courseId_glob = courseId;
        display_on_off("#course_table_quizInformation", "flex");

        $.ajax({
            url: `/professor/course-quiz${courseId}`,
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

    // click on info button in quiz details table and create table of question and answer for set score of each answer
    $.fn.answer_information = function (answer_information) {

        answerId_glob = answer_information.id;

        display_on_off("#question_sheet_table", "flex");

        total_score = 0;
        let tr = [];
        score_id_list = [];
        let question_information = answer_information.questionSheet.questions;
        $.each(question_information, function (i, item) {
            let $tr;
            let $tr_new;
            let tr_new = [];
            let score = "";
            if (item.scores.length !== 0 && item.correctAnswer === answer_information.answer[`${item.id}`]) {
                score = item.scores[0].score;
                total_score += score;
            } else {
                score = 0;
            }
            if (!item.hasOwnProperty("options")) {
                $tr = $('<tr>').append(
                    $('<td>').html("<b style='color: #ed4956'>Q:</b>"),
                    $('<td>').text(item.question),
                    $('<td style="width: 60px">').append(
                        `<input style="width: 50px" value="0" id="input_number_${item.id}" type='number' min="0" max='${item.scores[0].score}' placeholder="Score"/>`
                    )
                );
                $tr_new = $('<tr>').append(
                    $('<td>').text(""),
                    $('<td>').html("<b style='color:#0ab089;'>Answer:</b>&nbsp&nbsp&nbsp" + answer_information.answer[`${item.id}`])
                )
                tr_new.push($tr_new[0]);
                score_id_list.push(`input_number_${item.id}`);
            } else {
                $tr = $('<tr style="width: 100%">').append(
                    $('<td>').html("<b style='color: #ed4956'>Q:</b>"),
                    $('<td>').text(item.question),
                    $('<td>').text(`Score: ${score}`)
                );

                $tr_new = $('<tr>').append(
                    $('<td>').text(""),
                    $('<td>').html("<b style='color:#0ab089;'>Answer:</b>&nbsp&nbsp&nbsp" + answer_information.answer[`${item.id}`])
                )
                tr_new.push($tr_new[0]);

            }
            tr.push($tr[0]);
            if ($tr_new !== undefined) {
                for (let i = 0; i < tr_new.length; i++) {
                    tr.push(tr_new[i]);
                }
            }
            tr.push("<tr><td></td><td><hr></td></tr>");

        });
        tr.push(
            `<tr>
                <td></td>
                <td></td>
                <td><button class="green_button" type='submit'>Apply</button></td>
            </tr>`
        );
        $("#questions_table_body").html(tr);
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

    // click on edit button in quiz table and edit information
    $.fn.edit_quiz_information = function (quizId) {
        display_on_off("#edit_quiz_table", "flex");

        $.ajax({
            url: "/professor/edit-quiz",
            type: "POST",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: quizId,
            success: function (response) {
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
                $("#message_text").html("Delete Quiz was successful!!!");
                display_on_off("#messages", "flex");
                setTimeout($(this).quiz_back_function, 1500);
            },
            error: function (errMsg) {
                console.log(errMsg);
            }
        });
    };

    // click on add quiz button in edit quiz table
    $.fn.add_question = function () {
        $("#question_source_button").css("display", "none");
        $("#add_quiz_button").css("display", "inline");
    };

    // click on question bank button in edit quiz table
    $.fn.question_bank = function () {
        $("#question_source_button").css("display", "none");
        $("#question_bank").css("display", "inline");

        $.ajax({
            url: "/professor/get-questions",
            type: "get",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
        }).done(function (response) {
            if (response.size === 0) {
                $("#message_text").html("Question Bank is empty!!!")
                display_on_off("#messages", "flex");
                setTimeout($(this).add_essays_back_function, 1500);
            } else
                createQuestionBankBody(response);

        }).fail(function (errMsg) {
            console.log(errMsg);
        })
    };

    // create table body of question bank
    function createQuestionBankBody(question_list) {
        let tr = [];
        $.each(question_list, function (i, item) {
            let $tr;
            let $tr_new;
            let tr_new = [];
            if (!item.hasOwnProperty("options")) {
                $tr = $('<tr>').append(
                    $('<td>').text("Q:"),
                    $('<td>').text(item.question),
                    $('<td style="width: 120px">').append(
                        `<button class="gray_button" type='button' onclick='$(this).edit_question_information(${JSON.stringify(item)})'>Edit</button>
                         <button class="green_button" type='button' onclick='$(this).add_question_from_questionBank(\"${item.id}\")'>Add</button>`
                    )
                );
            } else {
                $tr = $('<tr style="width: 100%">').append(
                    $('<td>').text("Q:"),
                    $('<td>').text(item.question),
                    $('<td style="width: 120px">').append(
                        `<button class="gray_button" type='button' onclick='$(this).edit_question_information(${JSON.stringify(item)})'>Edit</button>
                        <button class="green_button" type='button' onclick='$(this).add_question_from_questionBank(\"${item.id}\")'>Add</button>`
                    )
                );
                $.each(item.options, function (i, item) {
                    $tr_new = $('<tr>').append(
                        $('<td>').text(""),
                        $('<td>').html(i + 1 + ":&nbsp&nbsp&nbsp" + item)
                    )
                    tr_new.push($tr_new[0]);
                });
            }
            tr.push($tr[0]);
            if ($tr_new !== undefined) {
                for (let i = 0; i < tr_new.length; i++) {
                    tr.push(tr_new[i]);
                }
            }
            tr.push("<tr><td></td><td><hr></td></tr>");

        });
        $("#question_bank_table_body").html(tr);
    }

    // all questions for specified quiz
    function createQuestions(question_list) {
        let tr = [];
        let total_score = 0;
        $.each(question_list, function (i, item) {
            let $tr;
            let $tr_new;
            let tr_new = [];
            let score = "";
            if (item.scores.length !== 0) {
                score = item.scores[0].score;
                total_score += score;
            }
            if (!item.hasOwnProperty("options")) {
                $tr = $('<tr>').append(
                    $('<td>').text("Q:"),
                    $('<td>').text(item.question),
                    $('<td style="width: 195px">').append(
                        `<input style="width: 50px" value="${score}" id="input_number_${item.id}" type='number' placeholder="Score"/>
                         <button class="green_button" type='button' onclick='$(this).apply_score_for_question(\"${item.id}\")'>Apply</button>
                         <button class="red_button" type='button' onclick='$(this).delete_question(\"${item.id}\")'>Delete</button>`
                    )
                );
            } else {
                $tr = $('<tr style="width: 100%">').append(
                    $('<td>').text("Q:"),
                    $('<td>').text(item.question),
                    $('<td style="width: 195px">').append(
                        `<input style="width: 50px" value="${score}" id="input_number_${item.id}" type='number' placeholder="Score"/>
                        <button class="green_button" type='button' onclick='$(this).apply_score_for_question(\"${item.id}\")'>Apply</button>
                        <button class="red_button" type='button' onclick='$(this).delete_question(\"${item.id}\")'>Delete</button>`
                    )
                );
                $.each(item.options, function (i, item) {
                    $tr_new = $('<tr>').append(
                        $('<td>').text(""),
                        $('<td>').html(i + 1 + ":&nbsp&nbsp&nbsp" + item)
                    )
                    tr_new.push($tr_new[0]);
                });
            }
            tr.push($tr[0]);
            if ($tr_new !== undefined) {
                for (let i = 0; i < tr_new.length; i++) {
                    tr.push(tr_new[i]);
                }
            }
            tr.push("<tr><td></td><td><hr></td></tr>");

        });
        tr.push(
            `<tr>
                <td></td>
                <td></td>
                <td>Total Score: ${total_score}</td>
            </tr>`
        );
        $("#questions_table_body").html(tr);
    }

    // edit question information
    $.fn.edit_question_information = function (question) {
        if (!question.hasOwnProperty("options")) {
            $(this).add_essays();
            $("#add_essays_title").val(question.title);
            if (question.scores.length !== 0)
                $("#add_essays_score").val(question.scores[0].score);
            $("#add_essays_question").val(question.question);
            $("#essays_id").val(question.id);
        } else {
            $(this).add_multipleChoice();

            $("#add_multipleChoice_title").val(question.title);
            if (question.scores.length !== 0)
                $("#add_multipleChoice_score").val(question.scores[0].score);
            $("#add_multipleChoice_question").val(question.question);
            $("#multipleChoice_id").val(question.id);
            $("#multipleChoice_correct_answer").val(question.correctAnswer);
            $("#question_text").html(question.question);
            let tr = [];
            $.each(question.options, function (i, item) {
                let $tr = $('<tr>').append(
                    $('<td>').text(i + 1),
                    $('<td style="width: 500px" class="td_multipleOption">').text(item),
                    $('<td>').html(`<button type='button' onclick='$(this).removeOption(\"${item}\")'>remove</button>`)
                );
                tr.push($tr[0]);
                $("#multipleChoice_correct_answer").append(
                    `<option value="${item}">
                    ${i + 1}
                </option>`
                );
            });
            $("#question_text_option_table_body").html(tr);
            $("#multipleChoice_table").css("display", "inline");
            $("#multipleChoice_correct_answer_label").css("display", "inline");
            $("#text_of_option").css("display", "inline");
        }
    }

    // click on add from question bank table list
    $.fn.add_question_from_questionBank = function (questionId) {
        let data = [
            {questionId: questionId},
            {quizId: quizId_glob}
        ];
        $.ajax({
            url: "/professor/add-question",
            type: "POST",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify(data)
        }).done(function (response) {
            $("#message_text").html("add question was successful!!!")
            display_on_off("#messages", "flex");
            setTimeout($(this).add_question_back_function, 1500);
            clean_multipleChoice_form();
        }).fail(function (errMsg) {
            console.log(errMsg);
        })
    }

    // click on apply in question sheet table
    $.fn.apply_score_for_question = function (questionId) {
        if ($(`#input_number_${questionId}`).val() !== "") {
            let data = [
                {questionId: questionId},
                {quizId: quizId_glob},
                {score: $(`#input_number_${questionId}`).val()}
            ];
            $.ajax({
                url: "/professor/apply-question-score",
                type: "POST",
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                data: JSON.stringify(data)
            }).done(function (response) {
                $("#message_text").html("apply score was successful!!!")
                display_on_off("#messages", "flex");
                setTimeout($(this).successful_apply_score, 1500);
            }).fail(function (errMsg) {
                console.log(errMsg);
            })
        } else {
            $("#message_text").html("Enter the score first!!!")
            display_on_off("#messages", "flex");
            setTimeout($(this).successful_apply_score, 1500);
        }

    }

    // click on apply in answer question table
    $(document).on("submit", "#question_sheet_submit", function (event) {
        event.preventDefault();
        $(this).apply_score_for_answers();

        let data = {
            answerId: answerId_glob,
            score: total_score
        };

        $.ajax({
            url: "/professor/apply-answer-score",
            type: "POST",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify(data)
        }).done(function (response) {
            $("#message_text").html("apply score was successful!!!")
            display_on_off("#messages", "flex");
            setTimeout($(this).successful_apply_score_toAnswer, 1500);
        }).fail(function (errMsg) {
            console.log(errMsg);
        })
    })

    // configure total score insert in question sheet form
    $.fn.apply_score_for_answers = function () {

        for (let index = 0; index < score_id_list.length; index++) {
            total_score += parseInt($(`#${score_id_list[index]}`).val());
        }

    }

    // click on delete in question sheet table
    $.fn.delete_question = function (questionId) {
        let data = {questionId: questionId};
        $.ajax({
            url: `/professor/delete-question/${questionId}/${quizId_glob}`,
            type: "DELETE",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
        }).done(function (response) {
            $("#message_text").html("Delete question was successful!!!")
            display_on_off("#messages", "flex");
            setTimeout($(this).successful_apply_score, 1500);
        }).fail(function (errMsg) {
            console.log(errMsg);
        })

    }

    // click on add question button in edit quiz table
    $.fn.chose_source = function () {
        options = [];
        display_on_off("#edit_quiz_table", "flex");
        $("#add_quiz_button").css("display", "none");
        $("#question_source_button").css("display", "inline");
    };

    // click on add essays button after click on add quiz button
    $.fn.add_essays = function () {
        display_on_off("#add_essays", "inline");
    };

    // click on remove button in edit multiple choice question
    $.fn.removeOption = function (item) {
        $("#question_text_option_table_body tr").each(function () {
            if ($(this).find(".td_multipleOption")[0].innerText === item) {
                $(this).remove();
            }
        });

        $("#multipleChoice_correct_answer option").each(function () {
            if ($(this)[0].value === item) {
                $(this).remove();
            }
        });
    };

    // click on add multipleChoice button after click on add quiz button
    $.fn.add_multipleChoice = function () {
        display_on_off("#add_multipleChoice", "inline");
    };

    // click on add multipleChoice option button
    $.fn.add_text_of_option = function () {
        $("#question_text").html($("#add_multipleChoice_question").val());

        if ($("#text_of_option").val() !== "") {
            $("#multipleChoice_correct_answer_label").css("display", "inline");
            $("#multipleChoice_table").css("display", "inline");
            $("#question_text_option_table_body:last-child").append(
                `<tr>
                    <td>${$("#question_text_option_table_body tr").length + 1}</td>
                    <td style="width: 500px" class="td_multipleOption">
                        ${$("#text_of_option").val()}               
                    </td>
                    <td><button type='button' onclick='$(this).removeOption(\"${$("#text_of_option").val()}\")'>remove</button></td>
                </tr>`
            );
            $("#multipleChoice_correct_answer").append(
                `<option value="${$("#question_text_option_table_body tr:last-child").find(".td_multipleOption").get()[0].innerText}">
                    ${$("#question_text_option_table_body tr").length}
                </option>`
            );
        }
        $("#question_text_option_table_body tr").each(function () {
            let $testTd = $(this).find(".td_multipleOption").get();
            options.push($testTd[0].innerText);
        })
        $("#text_of_option").val("").css("display", "inline");
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

        $.ajax({
            url: "/professor/update-quiz",
            type: "POST",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify(data)
        }).done(function (response) {
            $("#message_text").html("Edit Quiz was successful!!!")
            display_on_off("#messages", "flex");
            setTimeout($(this).quiz_back_function, 1500);
        }).fail(function (errMsg) {
            console.log(errMsg);
        })
    });

    // after click on save in add essays
    $(document).on('submit', '#add_essays_form', function (event) {
        event.preventDefault();

        let data = [
            {title: $("#add_essays_title").val()},
            {question: $("#add_essays_question").val()},
            {score: $("#add_essays_score").val()},
            {quizId: quizId_glob},
            {essaysId: $("#essays_id").val()}
        ];

        $.ajax({
            url: "/professor/add-essays",
            type: "POST",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify(data)
        }).done(function (response) {
            $("#message_text").html("add question was successful!!!")
            display_on_off("#messages", "flex");
            setTimeout($(this).add_essays_back_function, 1500);
            clean_essays_form();
        }).fail(function (errMsg) {
            console.log(errMsg);
        })
    });

    // after click on save in add multiple choice
    $(document).on('submit', '#add_multipleChoice_form', function (event) {
        event.preventDefault();
        let options = [];

        $("#question_text_option_table_body tr").each(function () {
            options.push($(this).find(".td_multipleOption")[0].innerText);
        });

        let data = [
            {title: $("#add_multipleChoice_title").val()},
            {question: $("#add_multipleChoice_question").val()},
            {score: $("#add_multipleChoice_score").val()},
            {quizId: quizId_glob},
            {option: options},
            {correctAnswer: $("#multipleChoice_correct_answer").val()},
            {multipleChoiceId: $("#multipleChoice_id").val()}
        ];

        $.ajax({
            url: "/professor/add-multipleOption",
            type: "POST",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify(data)
        }).done(function (response) {
            $("#message_text").html("add question was successful!!!")
            display_on_off("#messages", "flex");
            setTimeout($(this).add_essays_back_function, 1500);
            clean_multipleChoice_form();
        }).fail(function (errMsg) {
            console.log(errMsg);
        })
    });

    // after click on submit in add professor image
    $("#add-professor-image").on('submit', function (event) {
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

    // clean multiple choice question form
    function clean_multipleChoice_form() {
        $("#add_multipleChoice_title").val("");
        $("#add_multipleChoice_score").val("");
        $("#add_multipleChoice_question").val("");
        $("#question_text_option_table_body tr").remove();
        $("#question_text").val("");
        $("#multipleChoice_correct_answer").find("option").remove();
        $("#multipleChoice_table").css("display", "none");
        $("#text_of_option").css("display", "none");
        $("#multipleChoice_correct_answer_label").css("display", "none");
    }

    // clean essays question form
    function clean_essays_form() {
        $("#add_essays_title").val("");
        $("#add_essays_score").val("");
        $("#add_essays_question").val("");
        $("#essays_id").val("");
    }

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
        if (`"${divId}"` !== "#participants_table")
            $("#participants_table").css("display", "none");
        if (`"${divId}"` !== "#question_sheet_table")
            $("#question_sheet_table").css("display", "none");
        if (`"${divId}"` !== "#previousAndNextButton")
            $("#previousAndNextButton").css("display", "none");
        if (`"${divId}"` !== "#participants_table")
            $("#participants_table").css("display", "none");

        $(`${divId}`).css("display", `${displayType}`);
        $('#course_table_quiz_info').html("");

        $("#messageID").css("display", "none");
        $("#messageID_2").css("display", "none");

    };
})