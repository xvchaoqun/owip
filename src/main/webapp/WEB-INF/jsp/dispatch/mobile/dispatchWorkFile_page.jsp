<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content">
            <div class="row">
                <form class="form-inline">
                    <div class="form-group">
                        <div class="col-xs-10">
                              <input id="searchinput" class="form-control" type="text" name="fileName" placeholder="请输入文件名">
                              <span id="searchclear" class="fa fa-times-circle-o"></span>
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
<style>
    #searchinput {
    /*width: 200px;*/
}
#searchclear {
    position: absolute;
    right: 20px;
    top: 0;
    bottom: 0;
    height: 14px;
    margin: auto;
    font-size: 14px;
    cursor: pointer;
    color: #ccc;
}
</style>
<script type="text/template" id="result_tpl">
    <div class="col-xs-12">
        <table class="table table-bordered table-condensed table-unhover2 table-center">
            <thead>
            <tr>
                <th>文件名</th>
                <th>类型</th>
            </tr>
            </thead>
            <tbody>
            {{_.each(files, function(f, idx){ }}
            <tr>
                <td style="text-align: left;">
                    {{if(f.pdfFilePath.length>0){}}
                    <a href="javascript:void(0)" class="popPdfView" data-direction="bottom"
                            data-url="${ctx}/m/dispatchWorkFile_preview?path={{=f.pdfFilePath}}">
                        <span style="color:{{=f.status?'':'red'}}">{{=f.fileName}}</span></a>
                    {{}else{}}
                    <span style="color:{{=f.status?'':'red'}}">{{=f.fileName}}</span>
                    {{}}}
                </td>
                <td nowrap>{{=_cMap.DISPATCH_WORK_FILE_TYPE_MAP[f.type]}}</td>
            </tr>
            {{});}}
            </tbody>
        </table>
    </div>
</script>
<script>
    $("#searchclear").click(function(){
        $("#searchinput").val('');
    });

    var dispatchWorkFiles = ${cm:toJSONArray(dispatchWorkFiles)}
    if(dispatchWorkFiles.length>0) {
        $("#result").html(_.template($("#result_tpl").html())({files: dispatchWorkFiles}));
    }
    $(" #search").click(function(){
        $("#result").html('')
        var fileName = $("input[name=fileName]").val();
        if($.trim(fileName)==''){
         location.reload();
         return;
        }
        $.post("${ctx}/m/dispatchWorkFile_search",{fileName:fileName},function(ret){
            if(ret.success){

                $("#result").html(_.template($("#result_tpl").html())({files: ret.dispatchWorkFiles}));
            }
        });
    })
</script>