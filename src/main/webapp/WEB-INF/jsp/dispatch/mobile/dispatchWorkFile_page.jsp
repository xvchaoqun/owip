<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content">
            <div class="row">
                <form class="form-inline">
                    <div class="form-group">
                        <div class="col-xs-10">
                            <input class="form-control" type="text" name="fileName" placeholder="请输入文件名">
                        </div>
                        <div class="col-xs-2" style="padding-left: 0">
                            <input type="button" id="search" class="btn btn-sm btn-primary" value="查询"/>
                        </div>
                    </div>
                </form>
                <br/>  <br/>
                <div class="row" id="result">

                </div>
            </div>
        </div>
        <div id="body-content-view"<%-- style="overflow-y: hidden"--%>>
        </div>
    </div>
</div>
<script type="text/template" id="result_tpl">
    <div class="col-xs-12">
        <table class="table table-bordered table-condensed table-unhover2 table-center">
            <thead>
            <tr>
                <th>文件名</th>
                <th>类型</th>
                <th style="width: 60px;">状态</th>
            </tr>
            </thead>
            <tbody>
            {{_.each(files, function(f, idx){ }}
            <tr>
                <td style="text-align: left">
                    {{if(f.pdfFilePath.length>0){}}
                    <%--<a href="javascript:void(0)" data-url="${ctx}/swf/preview?type=url&path={{=f.pdfFilePath}}&filename={{=f.fileName}}"
                       title="PDF文件预览" class="openUrl">{{=f.fileName}}</a>--%>
                    <a href="javascript:void(0)" class="popPdfView" data-direction="bottom"
                            data-url="${ctx}/m/dispatchWorkFile_preview?path={{=f.pdfFilePath}}">{{=f.fileName}}</a>
                    {{}else{}}
                    {{=f.fileName}}
                    {{}}}
                </td>
                <td nowrap>{{=_cMap.DISPATCH_WORK_FILE_TYPE_MAP[f.type]}}</td>
                <td>{{=f.status?'有效':'失效'}}</td>
            </tr>
            {{});}}
            </tbody>
        </table>
    </div>
</script>
<script>
    $(" #search").click(function(){
        $("#result").html('')
        var fileName = $("input[name=fileName]").val();
        if($.trim(fileName)=='') return;
        $.post("${ctx}/m/dispatchWorkFile_search",{fileName:fileName},function(ret){
            if(ret.success){

                $("#result").html(_.template($("#result_tpl").html())({files: ret.dispatchWorkFiles}));
            }
        });
    })
</script>