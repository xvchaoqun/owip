<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<form action="${ctx}/system/properties" method="post" autocomplete="off" disableautocomplete id="modalForm" class="form-horizontal">
    <textarea class="span5" rows="25" id="unicode_content" style="width: 100%"></textarea>
    <textarea name="content" style="display: none;">${content}</textarea>
</form>
<div class="modal-footer center">
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-edit"></i> 修改</button>
    <button id="originalBtn" class="btn btn-info"><i class="fa fa-file-code-o"></i> 显示源文件</button>
    <button id="backBtn" class="btn btn-success" style="display: none"><i class="fa fa-reply"></i> 返回编辑</button>
</div>
<script>

    $("#originalBtn").click(function () {
        $("#unicode_content").val($("#modalForm textarea[name=content]").val());
        $("#submitBtn, #originalBtn").hide();
        $("#backBtn").show();
    });
    $("#backBtn").click(function () {
        ascii2native();
        $("#submitBtn, #originalBtn").show();
        $("#backBtn").hide();
    });

    $("#submitBtn").click(function () {
        $("#modalForm").submit();
        return false;
    });
    $("#modalForm").validate({
        submitHandler: function (form) {
            native2ascii();
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret == "success") {
                        toastr.success('操作成功。', '成功');
                    }
                }, error: function (ret) {

                    SysMsg.success("系统异常，请稍后再试。");
                }
            });
        }
    });

    ////////////////////////////////////////////////////////////////////////////////
    //
    //  Copyright (c) 2008 http://www.native2ascii.com.  All rights reserved.
    //	Author:Hans He
    //	Date:2008-12-16
    //
    ////////////////////////////////////////////////////////////////////////////////
    function native2ascii() {
        var character = $("#unicode_content").val().split("");
        var ascii = "";
        for (var i = 0; i < character.length; i++) {
            var code = Number(character[i].charCodeAt(0));
            if (/*!document.getElementById("ignoreLetter").checked||*/code > 127) {
                var charAscii = code.toString(16);
                charAscii = new String("0000").substring(charAscii.length, 4) + charAscii;
                ascii += "\\u" + charAscii;
            }
            else {
                ascii += character[i];
            }
        }
        $("#modalForm textarea[name=content]").val(ascii)
    }
    function ascii2native() {
        var character = $("#modalForm textarea[name=content]").val().split("\\u");
        var native1 = character[0];
        for (var i = 1; i < character.length; i++) {
            var code = character[i];
            native1 += String.fromCharCode(parseInt("0x" + code.substring(0, 4)));
            if (code.length > 4) {
                native1 += code.substring(4, code.length);
            }
        }
        $("#unicode_content").val(native1);
    }
    ascii2native();
</script>
