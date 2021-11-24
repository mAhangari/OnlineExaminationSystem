$(document).ready(function () {

    $response_glob = 0;
    $rowPerPage = 5;
    $start = 0;
    $end = $rowPerPage;
    let counter = 0;

    let circle_id = 0;
    // click on next button
    $.fn.next_func = function () {
        let res_length = $response_glob.length;
        if ($end < res_length) {
            $start = $start + $rowPerPage;
            $end = $end + $rowPerPage;

            if (circle_id === 0 && (res_length / $rowPerPage) <= 2) {
                $(`#circle_id_${circle_id}`).css("backgroundColor", "white");
                $(`#circle_id_${circle_id + 1}`).css("backgroundColor", "gray");
                circle_id++;
            } else if (circle_id === 0 && (res_length / $rowPerPage) <= 3 && (res_length / $rowPerPage) > 2) {
                $(`#circle_id_${circle_id}`).css("backgroundColor", "white").addClass("middle_circle").removeClass("circle");
                $(`#circle_id_${circle_id + 1}`).addClass("circle").removeClass("middle_circle").css("backgroundColor", "gray");
                $(`#circle_id_${circle_id + 2}`).css("backgroundColor", "white").addClass("middle_circle").removeClass("little_circle");
                circle_id++;
            } else if (circle_id === 0 && (res_length / $rowPerPage) > 3 && (res_length / $rowPerPage) <= 4) {
                $(`#circle_id_${circle_id}`).css("backgroundColor", "white").addClass("middle_circle").removeClass("circle");
                $(`#circle_id_${circle_id + 1}`).addClass("circle").removeClass("middle_circle").css("backgroundColor", "gray");
                $(`#circle_id_${circle_id + 2}`).css("backgroundColor", "white").addClass("middle_circle").removeClass("little_circle");
                $(`#circle_id_${circle_id + 3}`).removeClass("display_none");
                circle_id++;
            } else if (circle_id === 0 && (res_length / $rowPerPage) > 4) {
                $(`#circle_id_${circle_id}`).css("backgroundColor", "white").addClass("middle_circle").removeClass("circle");
                $(`#circle_id_${circle_id + 1}`).addClass("circle").removeClass("middle_circle").css("backgroundColor", "gray");
                $(`#circle_id_${circle_id + 2}`).css("backgroundColor", "white").addClass("middle_circle").removeClass("little_circle");
                $(`#circle_id_${circle_id + 3}`).removeClass("display_none");
                circle_id++;
            } else if (circle_id === 1 && (res_length / $rowPerPage) <= 3 && (res_length / $rowPerPage) > 2) {
                $(`#circle_id_${circle_id}`).css("backgroundColor", "white").addClass("middle_circle").removeClass("circle");
                $(`#circle_id_${circle_id - 1}`).addClass("little_circle").removeClass("middle_circle");
                $(`#circle_id_${circle_id + 1}`).addClass("circle").removeClass("middle_circle").css("backgroundColor", "gray");
                circle_id++;
            } else if (circle_id === 1 && (res_length / $rowPerPage) > 3 && (res_length / $rowPerPage) <= 4) {
                $(`#circle_id_${circle_id}`).css("backgroundColor", "white").addClass("middle_circle").removeClass("circle");
                $(`#circle_id_${circle_id - 1}`).addClass("little_circle").removeClass("middle_circle");
                $(`#circle_id_${circle_id + 1}`).addClass("circle").removeClass("middle_circle").css("backgroundColor", "gray");
                $(`#circle_id_${circle_id + 2}`).addClass("middle_circle").removeClass("little_circle");
                circle_id++;
            } else if (circle_id === 1 && (res_length / $rowPerPage) > 4) {
                $(`#circle_id_${circle_id}`).css("backgroundColor", "white").addClass("middle_circle").removeClass("circle");
                $(`#circle_id_${circle_id - 1}`).addClass("little_circle").removeClass("middle_circle");
                $(`#circle_id_${circle_id + 1}`).addClass("circle").removeClass("middle_circle").css("backgroundColor", "gray");
                $(`#circle_id_${circle_id + 2}`).addClass("middle_circle").removeClass("little_circle");
                $(`#circle_id_${circle_id + 3}`).removeClass("display_none");
                circle_id++;
            } else if (circle_id === 2 && (res_length / $rowPerPage) > 3 && (res_length / $rowPerPage) <= 4) {
                $(`#circle_id_${circle_id}`).css("backgroundColor", "white").addClass("middle_circle").removeClass("circle");
                $(`#circle_id_${circle_id - 1}`).addClass("little_circle").removeClass("middle_circle");
                $(`#circle_id_${circle_id + 1}`).addClass("circle").removeClass("middle_circle").css("backgroundColor", "gray");
                $(`#circle_id_${circle_id - 2}`).addClass("display_none");
                circle_id++;
            } else if (circle_id === 2 && (res_length / $rowPerPage) > 4 && ($end + $rowPerPage) >= res_length) {
                $(`#circle_id_${circle_id}`).css("backgroundColor", "white").addClass("middle_circle").removeClass("circle");
                $(`#circle_id_${circle_id - 1}`).addClass("little_circle").removeClass("middle_circle");
                $(`#circle_id_${circle_id - 2}`).addClass("display_none");
                $(`#circle_id_${circle_id + 1}`).addClass("circle").removeClass("middle_circle").css("backgroundColor", "gray");
                $(`#circle_id_${circle_id + 2}`).addClass("middle_circle").removeClass("little_circle");
                circle_id++;
            } else if (circle_id === 3 && (res_length / $rowPerPage) > 4 && $end >= res_length) {
                $(`#circle_id_${circle_id}`).css("backgroundColor", "white").addClass("middle_circle").removeClass("circle");
                $(`#circle_id_${circle_id - 1}`).addClass("little_circle").removeClass("middle_circle");
                $(`#circle_id_${circle_id - 2}`).addClass("display_none");
                $(`#circle_id_${circle_id + 1}`).addClass("circle").removeClass("middle_circle").css("backgroundColor", "gray");
                circle_id++;
            }

        }
        $(this).page_data_change();
    };

    // click on previous button
    $.fn.previous_func = function () {
        let res_length = $response_glob.length;
        if ($start >= $rowPerPage) {
            $start = $start - $rowPerPage;
            $end = $end - $rowPerPage;

            if (circle_id === 1 && (res_length / $rowPerPage) <= 2) {
                $(`#circle_id_${circle_id}`).css("backgroundColor", "white");
                $(`#circle_id_${circle_id - 1}`).css("backgroundColor", "gray");
                circle_id--;
            } else if (circle_id === 2 && (res_length / $rowPerPage) <= 3 && (res_length / $rowPerPage) > 2) {
                $(`#circle_id_${circle_id}`).css("backgroundColor", "white").addClass("middle_circle").removeClass("circle");
                $(`#circle_id_${circle_id - 1}`).addClass("circle").removeClass("middle_circle").css("backgroundColor", "gray");
                $(`#circle_id_${circle_id - 2}`).css("backgroundColor", "white").addClass("middle_circle").removeClass("little_circle");
                circle_id--;
            } else if (circle_id === 3 && (res_length / $rowPerPage) > 3 && (res_length / $rowPerPage) <= 4) {
                $(`#circle_id_${circle_id}`).css("backgroundColor", "white").addClass("middle_circle").removeClass("circle");
                $(`#circle_id_${circle_id - 1}`).addClass("circle").removeClass("middle_circle").css("backgroundColor", "gray");
                $(`#circle_id_${circle_id - 2}`).css("backgroundColor", "white").addClass("middle_circle").removeClass("little_circle");
                $(`#circle_id_${circle_id - 3}`).removeClass("display_none");
                circle_id--;
            } else if (circle_id === 4 && (res_length / $rowPerPage) > 4) {
                $(`#circle_id_${circle_id}`).css("backgroundColor", "white").addClass("middle_circle").removeClass("circle");
                $(`#circle_id_${circle_id - 1}`).addClass("circle").removeClass("middle_circle").css("backgroundColor", "gray");
                $(`#circle_id_${circle_id - 2}`).css("backgroundColor", "white").addClass("middle_circle").removeClass("little_circle");
                $(`#circle_id_${circle_id - 3}`).removeClass("display_none");
                circle_id--;
            } else if (circle_id === 1 && (res_length / $rowPerPage) <= 3 && (res_length / $rowPerPage) > 2) {
                $(`#circle_id_${circle_id}`).css("backgroundColor", "white").addClass("middle_circle").removeClass("circle");
                $(`#circle_id_${circle_id + 1}`).addClass("little_circle").removeClass("middle_circle");
                $(`#circle_id_${circle_id - 1}`).addClass("circle").removeClass("middle_circle").css("backgroundColor", "gray");
                circle_id--;
            } else if (circle_id === 2 && (res_length / $rowPerPage) > 3 && (res_length / $rowPerPage) <= 4) {
                $(`#circle_id_${circle_id}`).css("backgroundColor", "white").addClass("middle_circle").removeClass("circle");
                $(`#circle_id_${circle_id + 1}`).addClass("little_circle").removeClass("middle_circle");
                $(`#circle_id_${circle_id - 1}`).addClass("circle").removeClass("middle_circle").css("backgroundColor", "gray");
                $(`#circle_id_${circle_id - 2}`).addClass("middle_circle").removeClass("little_circle");
                circle_id--;
            } else if (circle_id === 3 && (res_length / $rowPerPage) > 4) {
                $(`#circle_id_${circle_id}`).css("backgroundColor", "white").addClass("middle_circle").removeClass("circle");
                $(`#circle_id_${circle_id + 1}`).addClass("little_circle").removeClass("middle_circle");
                $(`#circle_id_${circle_id - 1}`).addClass("circle").removeClass("middle_circle").css("backgroundColor", "gray");
                $(`#circle_id_${circle_id - 2}`).addClass("middle_circle").removeClass("little_circle");
                $(`#circle_id_${circle_id - 3}`).removeClass("display_none");
                circle_id--;
            } else if (circle_id === 1 && (res_length / $rowPerPage) > 3 && (res_length / $rowPerPage) <= 4) {
                $(`#circle_id_${circle_id}`).css("backgroundColor", "white").addClass("middle_circle").removeClass("circle");
                $(`#circle_id_${circle_id + 1}`).addClass("little_circle").removeClass("middle_circle");
                $(`#circle_id_${circle_id - 1}`).addClass("circle").removeClass("middle_circle").css("backgroundColor", "gray");
                $(`#circle_id_${circle_id + 2}`).addClass("display_none");
                circle_id--;
            } else if (circle_id === 2 && (res_length / $rowPerPage) > 4 && ($start - $rowPerPage) < $rowPerPage) {
                $(`#circle_id_${circle_id}`).css("backgroundColor", "white").addClass("middle_circle").removeClass("circle");
                $(`#circle_id_${circle_id + 1}`).addClass("little_circle").removeClass("middle_circle");
                $(`#circle_id_${circle_id + 2}`).addClass("display_none");
                $(`#circle_id_${circle_id - 1}`).addClass("circle").removeClass("middle_circle").css("backgroundColor", "gray");
                $(`#circle_id_${circle_id - 2}`).addClass("middle_circle").removeClass("little_circle");
                circle_id--;
            } else if (circle_id === 1 && (res_length / $rowPerPage) > 4 && $start < $rowPerPage) {
                $(`#circle_id_${circle_id}`).css("backgroundColor", "white").addClass("middle_circle").removeClass("circle");
                $(`#circle_id_${circle_id + 1}`).addClass("little_circle").removeClass("middle_circle");
                $(`#circle_id_${circle_id + 2}`).addClass("display_none");
                $(`#circle_id_${circle_id - 1}`).addClass("circle").removeClass("middle_circle").css("backgroundColor", "gray");
                circle_id--;
            }

        }
        $(this).page_data_change();
    };

    // function for next and previous button
    $.fn.next_previous_button = function () {
        let res_length = $response_glob.length;
        if ($end < res_length) {
            $("#previousAndNextButton").css("display", "flex");
            let tr = [];
            tr.push($(`<button id="previous" class="previousAndNextButton" type="button" onclick="$(this).previous_func()">PREVIOUS</button>`)[0]);
            let i = 0;

            if ((res_length / $rowPerPage) <= 2) {
                tr.push($(`<span id="circle_id_${i++}" class='middle_circle'>`)[0]);
                tr.push($(`<span id="circle_id_${i++}" class='middle_circle'>`)[0]);
            } else if ((res_length / $rowPerPage) > 2) {
                tr.push($(`<span id="circle_id_${i++}" class='circle'>`)[0]);
                tr.push($(`<span id="circle_id_${i++}" class='middle_circle'>`)[0]);
                tr.push($(`<span id="circle_id_${i++}" class='little_circle'>`)[0]);
            }

            if ((res_length / $rowPerPage) > 3 && (res_length / $rowPerPage) <= 4) {
                tr.push($(`<span id="circle_id_${i++}" class='little_circle display_none'>`)[0]);
            } else if ((res_length / $rowPerPage) > 4) {
                tr.push($(`<span id="circle_id_${i++}" class='little_circle display_none'>`)[0]);
                tr.push($(`<span id="circle_id_${i++}" class='little_circle display_none'>`)[0]);
            }

            tr.push($(`<button id="next" class="previousAndNextButton" type="button" onclick='$(this).next_func()'>NEXT</button>`)[0]);
            $("#previousAndNextButton").html(tr);
            $("#circle_id_0").css("backgroundColor", "gray");
        }
    }

    // function for iterate questions
    $.fn.iteration_input = function (input_list, tr) {
        tr.length = 0;
        $answers.length = 0;

        $.each(input_list, function (i, item) {

            let $tr_new;
            let tr_new = [];
            let data = {};

            if (!item.hasOwnProperty("options")) {
                $answers.push({questionId: item.id});
                data.question = $('<tr>').append(
                    $('<td>').text("Q:"),
                    $('<td>').text(item.question)
                );

                data.context = $('<tr>').append(
                    $('<td>').text(""),
                    $('<td>').append(`
                        <textarea cols="70" id="question_context_${item.id}" name="question_context" rows="5"></textarea>
                    `)
                );
                tr.push(data);
            } else {
                $answers.push({questionId: item.id});
                data.question = $(`<tr id="question_option_${item.id}" style="width: 100%">`).append(
                    $('<td>').text("Q:"),
                    $('<td>').text(item.question)
                );
                $.each(item.options, function (i, items) {
                    $tr_new = $(`<tr>`).append(
                        $('<td>').append(`
                            <label class="container">
                            <input name="checkmarkInput" type="radio">
                            <span class="checkmark"></span>
                            </label>
                        `),
                        $('<td>').html(i + 1 + ":&nbsp&nbsp&nbsp" + items)
                    )
                    tr_new.push($tr_new[0]);
                });
                data.option = tr_new;
                tr.push(data);
            }
        });
        $response_glob = tr;
        $(this).create_questions();
    }

    $.fn.create_questions = function () {
        counter = 1;
        $(this).previous_question();
    }

    $.fn.make_question_table = function (data) {
        let tr = [];
        if (data.hasOwnProperty("option")) {
            tr.push(data.question[0]);
            tr.push("<tr><td></td><td><hr></td></tr>");
            for (let i = 0; i < data.option.length; i++) {
                tr.push(data.option[i])
            }
            $("#questions_table_body").html(tr);
        } else {
            tr.push(data.question[0]);
            tr.push("<tr><td></td><td><hr></td></tr>");
            tr.push(data.context[0]);

            $("#questions_table_body").html(tr);
        }
    }

    // go to next question
    $.fn.next_question = function () {
        if (counter < ($response_glob.length - 1)) {
            $(this).create_answer();
            counter++;
            $(this).make_question_table($response_glob[counter]);
        } else if (counter === ($response_glob.length - 1)) {
            $("#question_next_button").css("display", "none");
            $("#question_save_button").css("display", "inline");
            $(this).create_answer();
        }
    }

    // go to previous question
    $.fn.previous_question = function () {
        if (counter > 0) {
            $(this).create_answer();
            counter--;
            $(this).make_question_table($response_glob[counter]);
        }
        $("#question_next_button").css("display", "inline");
        $("#question_save_button").css("display", "none");
    }

});