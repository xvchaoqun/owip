<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <table class="table table-bordered table-unhover2">
            <tr>
                <td>
        <form id="modalForm" class="form-inline">
            <textarea required class="noEnter" rows="2" name="cmd" style="width: 500px"></textarea>
        </form>
        <button id="submitBtn" class="btn btn-primary"><i class="fa fa-edit"></i> 执行</button>
        <button id="exportBtn" class="btn btn-warning"><i class="fa fa-download"></i> 执行并导出结果</button>
                </td>
                <td>
                    <div>
                        tail -n100 /data/logs/info.$(date +%Y-%m-%d).log
                    </div>
                    <div>
                        cat /data/logs/info.$(date +%Y-%m-%d).log |grep -C10 'ERROR'
                    </div>
                    <div>
                        cat /data/logs/info.$(date +%Y-%m)*.log |grep -C10 'ERROR'
                    </div>
                    <div>
                        cat /data/logs/info.$(date -d "1 day ago" +%Y-%m-%d).log |grep -C10 'ERROR'
                    </div>
                    <div>
                        /etc/init.d/jsvc-owip restart
                    </div>
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
    $("#exportBtn").click(function () {
        $("#result").html("");
        var cmd = $("#modalForm textarea[name=cmd]").val();
        if($.trim(cmd)==''){
            $("#modalForm textarea[name=cmd]").focus();
            return;
        }
        $(this).download("${ctx}/system/cmd_export?cmd=" + new Base64().encode($.trim(cmd)));
        return false;
    });

    $("#submitBtn").click(function () {
        $("#result").html("");

        var cmd = $("#modalForm textarea[name=cmd]").val();
        if($.trim(cmd)==''){
            $("#modalForm textarea[name=cmd]").focus();
            return;
        }
        $.post("${ctx}/system/cmd",{cmd:new Base64().encode($.trim(cmd))},function(ret){
            if (ret.msg == "success") {
                $("#result").html(_.template($("#result_tpl").html().NoMultiSpace())({
                    lines: ret.lines
                }));
            }
        })

        return false;
    });
</script>