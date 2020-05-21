

function drOnline_eva() {
    $('.changePasswd').attr("disabled", "disabled");
    $('.changePasswd').attr("hidden", "hidden");

    $('.notice').attr("disabled", "disabled");
    $('.notice').attr("hidden", "hidden");

    $('.eva').removeAttr("disabled");
    $('.eva').removeAttr("hidden");
}

function drOnline_notice() {
    $('.changePasswd').attr("disabled", "disabled");
    $('.changePasswd').attr("hidden", "hidden");

    $('.notice').removeAttr("disabled");
    $('.notice').removeAttr("hidden");

    $('.eva').attr("disabled", "disabled");
    $('.eva').attr("hidden", "hidden");
}

function drOnline_changePasswd() {
    $('.changePasswd').removeAttr("disabled");
    $('.changePasswd').removeAttr("hidden");

    $('.notice').attr("disabled", "disabled");
    $('.notice').attr("hidden", "hidden");

    $('.eva').attr("disabled", "disabled");
    $('.eva').attr("hidden", "hidden");
}

$("#evaluateForm #agree").on("click",function () {
    if ($(this).is(":checked"))
        $("#evaluateForm #enterBtn").css({"cssText": "background-color:#419641!important;border-color:#3e8f3e"});
    else
        $("#evaluateForm #enterBtn").removeAttr("style");
})

//统计是否需要另选他人
$("#survey input[type=radio]").on("click", function () {
    //console.log("111")
    var postId = $(this).attr("postId");
    $.each(postViews, function (i, item) {
        if (postId == item.id){
            var totalCount = item.competitiveNum;
            var count = 0;
            $("table input[postId="+postId+"]:checked").each(function () {
                if($(this).val() == 1) {
                    count++;
                }
            })
            if (count == totalCount) {
                $("#survey input[name=candidateCode][postId=" + postId + "]").next().attr("disabled", true);
                $("#survey input[name=candidateCode][postId=" + postId + "]").parent().css({"background-color": "#eee"})
            } else {
                $("#survey input[name=candidateCode][postId=" + postId + "]").next().attr("disabled", false);
                $("#survey input[name=candidateCode][postId=" + postId + "]").parent().css({"background-color": "white"})
            }
        }
    })
})
