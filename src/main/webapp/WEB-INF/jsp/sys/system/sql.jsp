<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <table class="table table-bordered table-unhover2">
            <tr>
                <td>
        <form action="${ctx}/system/sql" method="post" id="modalForm" class="form-inline">
            <textarea required rows="2" name="sql" style="width: 500px"></textarea>
        </form>
        <button id="submitBtn" class="btn btn-primary"><i class="fa fa-edit"></i> 执行</button>
                </td>
                <td>

                </td>
            </tr>
        </table>
    </div>

    <div class="col-xs-12" id="result">

    </div>
</div>
<div class="footer-margin"/>
<script type="text/template" id="result_tpl">
    <div class="space-4"></div>
    {{if(lines.length>=1){}}
        <table class="table table-bordered table-condensed table-unhover2">
            <tbody>
            {{_.each(lines, function(line, idx){ }}
            <tr>
                <td>{{=line}}</td>
            </tr>
            {{});}}
            </tbody>
        </table>
    {{}}}
</script>
<script>
    $("#submitBtn").click(function () {
        $("#result").html("");
        $("#modalForm").submit();
        return false;
    });
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.msg == "success") {
                        $("#result").html(_.template($("#result_tpl").html().NoMultiSpace())({
                            lines: ret.lines
                        }));
                    }
                }, error: function (ret) {
                    bootbox.alert("系统异常，请稍后再试。");
                }
            });
        }
    });
</script>