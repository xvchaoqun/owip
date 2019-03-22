<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <table class="table table-bordered table-unhover2">
            <tr>
                <td>
        <form autocomplete="off" disableautocomplete id="modalForm" class="form-inline">
            <textarea required class="noEnter" rows="2" name="cmd" style="width: 500px"></textarea>
        </form>
        <button id="submitBtn" class="btn btn-primary"><i class="fa fa-flash"></i> 执行</button>
        <button id="exportBtn" class="btn btn-warning"><i class="fa fa-download"></i> 执行并导出结果</button>
                </td>
                <td>
                    <div class="sample">
                        <div class="cmd">tail -n100 /data/logs/info.$(date +%Y-%m-%d).log</div> <button class="exeBtn btn btn-xs btn-primary"><i class="fa fa-flash"></i> 执行</button>
                    </div>
                    <div class="sample">
                        <div class="cmd">cat /data/logs/info.$(date +%Y-%m-%d).log |grep -C10 'ERROR'</div> <button class="exeBtn btn btn-xs btn-primary"><i class="fa fa-flash"></i> 执行</button>
                    </div>
                    <div class="sample">
                        <div class="cmd">cat /data/logs/info.$(date +%Y-%m)*.log |grep -C10 'ERROR'</div> <button class="exeBtn btn btn-xs btn-primary"><i class="fa fa-flash"></i> 执行</button>
                    </div>
                    <div class="sample">
                        <div class="cmd">cat /data/logs/info.$(date -d "1 day ago" +%Y-%m-%d).log |grep -C10 'ERROR'</div> <button class="exeBtn btn btn-xs btn-primary"><i class="fa fa-flash"></i> 执行</button>
                    </div>
                    <div class="sample">
                        <div class="cmd">sudo /etc/init.d/jsvc-owip restart</div> <button class="exeBtn btn btn-xs btn-primary"><i class="fa fa-flash"></i> 执行</button>
                    </div>
                    <div class="sample">
                        <div class="cmd">tail -n20 /data/logs/catalina.out</div> <button class="exeBtn btn btn-xs btn-primary"><i class="fa fa-flash"></i> 执行</button>
                    </div>
                </td>
            </tr>
        </table>
    </div>

    <div class="col-xs-12" id="result">

    </div>
</div>
<div class="footer-margin"/>
<style>
    .sample{
        padding: 5px 0;
    }
    .sample .cmd{
        float: left;
        padding-right: 5px;
    }
</style>
<script type="text/template" id="result_tpl">
    <div class="space-4"></div>
    {{if(lines.length>=1){}}
        <table class="table table-striped table-bordered table-condensed table-unhover2">
            <tbody>
            {{_.each(lines, function(line, idx){ }}
            <tr class="{{=line.indexOf('WARING')!=-1?'warning':(line.indexOf('ERROR')!=-1?'danger':'')}}">
                <td>{{=line}}</td>
            </tr>
            {{});}}
            </tbody>
        </table>
    {{}}}
</script>
<script>

    $(".exeBtn").click(function () {
        $("#modalForm textarea[name=cmd]").val($.trim($(this).closest(".sample").find(".cmd").text()));
        $("#submitBtn").click();
    });

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