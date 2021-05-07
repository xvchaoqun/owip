<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div class="tabbable">
            <ul class="nav nav-tabs padding-12 tab-color-blue background-blue" id="myTab4">
                <li class="active">
                    <a data-toggle="tab" href="#cmdTab" aria-expanded="true">系统命令</a>
                </li>
                <li class="">
                    <a data-toggle="tab" href="#logTab" aria-expanded="false">实时日志</a>
                </li>
            </ul>
            <div class="tab-content">
                <div id="cmdTab" class="tab-pane active">
                    <c:if test="${!isLinux}">
                        <span class="text-danger">当前系统不是Linux操作系统，不支持以下功能。</span>
                    </c:if>
                    <table class="table table-bordered table-unhover2">
                        <tr>
                            <td style="width: 600px">
                                <form autocomplete="off" disableautocomplete id="modalForm" class="form-inline">
                                    <textarea required class="noEnter" rows="8" name="cmd"
                                              style="width: 100%"></textarea>
                                </form>
                                <button id="submitBtn" class="btn btn-primary"><i class="fa fa-flash"></i> 执行</button>
                                <button id="exportBtn" class="btn btn-warning"><i class="fa fa-download"></i> 执行并导出结果
                                </button>
                            </td>
                            <td>
                                <div class="sample">
                                    <div class="cmd">tail -n100 /opt/logs/info.$(date +%Y-%m-%d).log</div>
                                    <button class="cpBtn btn btn-xs btn-info"><i class="fa fa-copy"></i> 拷贝</button>
                                </div>
                                <div class="sample">
                                    <div class="cmd">cat /opt/logs/info.$(date +%Y-%m-%d).log |grep -C10 'ERROR'</div>
                                    <button class="cpBtn btn btn-xs btn-info"><i class="fa fa-copy"></i> 拷贝</button>
                                </div>
                                <div class="sample">
                                    <div class="cmd">cat /opt/logs/info.$(date +%Y-%m)*.log |grep -C10 'ERROR'</div>
                                    <button class="cpBtn btn btn-xs btn-info"><i class="fa fa-copy"></i> 拷贝</button>
                                </div>
                                <div class="sample">
                                    <div class="cmd">cat /opt/logs/info.$(date -d "1 day ago" +%Y-%m-%d).log |grep -C10 'ERROR'</div>
                                    <button class="cpBtn btn btn-xs btn-info"><i class="fa fa-copy"></i> 拷贝</button>
                                </div>
                                <div class="sample">
                                    <div class="label">重启Tomcat服务</div>
                                    <div class="cmd">sudo /etc/init.d/jsvc-owip restart</div>
                                    <button class="cpBtn btn btn-xs btn-danger"><i class="fa fa-copy"></i> 拷贝</button>
                                </div>
                                <div class="sample">
                                    <div class="label">查看tomcat日志</div>
                                    <div class="cmd">tail -n20 /opt/logs/catalina.out</div>
                                    <button class="cpBtn btn btn-xs btn-info"><i class="fa fa-copy"></i> 拷贝</button>
                                </div>
                            </td>
                        </tr>
                    </table>
                    <div class="log-container" style="height: 400px; overflow-y: scroll;">
                        <div></div>
                    </div>
                </div>
                <div id="logTab" class="tab-pane">
                    <div class="log-container" class="col-xs-12" style="height: 650px; overflow-y: scroll;">
                        <div></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<style>
    .sample {
        padding: 5px 0;
    }
    .sample .label {
        float: left;
    }
    .sample .cmd {
        float: left;
        padding-right: 5px;
    }
</style>
<script type="text/template" id="cmd_container_tpl">
    <div class="space-4"></div>
    {{if(lines.length>=1){}}
    <table class="table table-striped table-bordered table-condensed">
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
    $(".cpBtn").click(function () {
        $("#modalForm textarea[name=cmd]").val($.trim($(this).closest(".sample").find(".cmd").text()));
    });
    var $cmdContainer = $("#cmdTab .log-container");
    var $logContainer = $("#logTab .log-container");
    $("#exportBtn").click(function () {
        $("div", $cmdContainer).html("");
        var cmd = $("#modalForm textarea[name=cmd]").val();
        if ($.trim(cmd) == '') {
            $("#modalForm textarea[name=cmd]").focus();
            return;
        }
        $(this).download("${ctx}/system/cmd_export?cmd=" + $.base64.encode($.trim(cmd)));
        return false;
    });
    $("#submitBtn").click(function () {
        $("div", $cmdContainer).html("");

        var cmd = $("#modalForm textarea[name=cmd]").val();
        if ($.trim(cmd) == '') {
            $("#modalForm textarea[name=cmd]").focus();
            return;
        }
        $.post("${ctx}/system/cmd", {cmd: $.base64.encode($.trim(cmd))}, function (ret) {
            if (ret.msg == "success") {
                $("div", $cmdContainer).html(_.template($("#cmd_container_tpl").html().NoMultiSpace())({
                    lines: ret.lines
                }));
                $cmdContainer.scrollTop($("div", $cmdContainer).height() - $cmdContainer.height());
            }
        })
        return false;
    });

    var websocket = new WebSocket((location.href.startWith("https://")?"wss":"ws") + '://'+ location.host +'/log');
    websocket.onmessage = function (event) {
        $("div", $logContainer).append(("<span class='{0}'>" + event.data + "</span>")
            .format(event.data.indexOf('WARING')!=-1?'bg-warning':(event.data.indexOf('ERROR')!=-1?'bg-danger':'')));
       $logContainer.scrollTop($("div", $logContainer).height() - $logContainer.height());
    };
</script>