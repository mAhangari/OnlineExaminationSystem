$(document).ready(function () {

    $response_glob = 0;
    $rowPerPage = 5;
    $start = 0;
    $end = $rowPerPage;

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

});