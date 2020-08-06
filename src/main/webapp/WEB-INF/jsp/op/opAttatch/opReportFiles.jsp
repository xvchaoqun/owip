<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>报送材料</h3>
</div>
<div class="modal-body">
    <div class="popTableDiv"
         data-url-page="${ctx}/op/opReportFiles?reportId=${opReport.id}">
        <table class="table table-actived table-striped table-bordered table-hover">
            <thead>
            <tr>
                <th>材料名称</th>
                <th>下载</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${opAttatchs}" var="opAttatch" varStatus="st">
                <tr>
                    <td nowrap width="300">${opAttatch.fileName}</td>
                    <td nowrap width="80" align="center">
                        <div class="hidden-sm hidden-xs action-buttons">
                            <button class="downloadBtn btn btn-xs btn-info" data-type="download"
                                    data-url="${ctx}/attach_download?path=${cm:sign(opAttatch.filePath)}&filename=${opAttatch.fileName}">
                                <i class="fa fa-download"></i> 下载</button>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div class="well well-lg center noRecord" hidden>
            <h4 class="green lighter">暂无记录</h4>
        </div>
    </div>
</div>
<script>
    var _opAttatchs = ${cm:toJSONObject(opAttatchs)};
    if (_opAttatchs.length == 0){
        $(".noRecord").show();
    } else {
        $(".noRecord").hide();
    }
    //$.fileInput($('#modalForm input[type=file]'));
    $.fileInput($("#modalForm input[type=file]"),{
        no_file:'请选择pdf或word文件',
        allowExt: ['pdf', 'doc', 'docx'],
        allowMime: ['application/pdf','application/msword',
            'application/vnd.openxmlformats-officedocument.wordprocessingml.document']
    });

    function _pop_reload(){
        pop_reload();
        $(window).triggerHandler('resize.jqGrid');
    }
    $("#submitBtn", "#modalForm").click(function () {
        $("#modalForm").submit();
        return false;
    })
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        _pop_reload();
                    }
                     $btn.button('reset');
                }
            });
        }
    });
</script>