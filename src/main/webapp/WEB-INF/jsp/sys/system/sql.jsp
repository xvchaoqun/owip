<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <form method="post" autocomplete="off" disableautocomplete id="modalForm" class="form-horizontal">
            <span class="help-block">注：执行多条语句用分号分隔</span>
            <textarea class="form-control canEnter" required rows="10" name="sql"></textarea>
            <div class="clearfix form-actions">
            <button id="submitBtn"  class="btn btn-info" type="button">
                <i class="ace-icon fa fa-check bigger-110"></i>
                执行
            </button>
            <button id="exportBtn" class="btn btn-success" type="button">
                <i class="ace-icon fa fa-file-excel-o bigger-110"></i>
                导出
            </button>
            <button class="btn btn-default" type="reset">
                <i class="ace-icon fa fa-undo bigger-110"></i>
                重置
            </button>
        </div>
        </form>
    </div>
    <div class="col-xs-12" id="result">

    </div>
</div>
<script type="text/template" id="result_tpl">
    <div class="space-4"></div>
    <pre>
        {{_.each(lines, function(line, idx){ }}
        {{=line}}<br/>
        {{});}}
    </pre>
</script>
<script>
    $("#submitBtn").click(function () {
        $("#result").html("");

        var sql = $("#modalForm textarea[name=sql]").val();
        if($.trim(sql)==''){
            $("#modalForm textarea[name=sql]").focus();
            return;
        }
        $.post("${ctx}/system/sql",{sql:$.base64.encode($.trim(sql))},function(ret){
            if (ret.msg == "success") {
                $("#result").html(_.template($("#result_tpl").html().NoMultiSpace())({
                    lines: ret.lines
                }));
            }
        })

        return false;
    });

    $("#exportBtn").click(function () {
        $("#result").html("");

        var sql = $("#modalForm textarea[name=sql]").val();
        if($.trim(sql)==''){
            $("#modalForm textarea[name=sql]").focus();
            return;
        }
        var $btn = $("#exportBtn").button('loading');
        $.post("${ctx}/system/sql_export",{sql:$.base64.encode($.trim(sql))},function(ret){
            $("#result").html(_.template($("#result_tpl").html().NoMultiSpace())({
                lines: ret.lines
            }));
            if (ret.ret) {
                var url = ("${ctx}/attach_download?path={0}").format(ret.filePath)
                $btn.download(url);
            }else{
                $btn.button('reset');
            }
        });
        return false;
    });

</script>