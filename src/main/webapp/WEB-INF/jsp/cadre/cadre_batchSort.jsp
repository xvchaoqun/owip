<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>批量排序(${CADRE_STATUS_MAP.get(status)})</h3>
</div>
<div class="modal-body">
    <h4>第一步： 按当前排序导出Excel：
        <button class="downloadBtn btn btn-warning btn-sm"
                data-url="${ctx}/cadre_data?export=4&status=${status}"><i class="fa fa-download"></i> 导出
        </button>
    </h4>
    <hr/>
    <h4 style="width: 280px;float: left">
        第二步：导入更新排序后的Excel：
    </h4>
    <div style="width: 250px;padding-top: 3px;float: left">
        <form class="form-inline" autocomplete="off" disableautocomplete id="modalForm"
              enctype="multipart/form-data" action="${ctx}/cadre_batchSort" method="post">
            <input type="hidden" name="status" value="${status}"/>
            <input type="file" name="xlsx" required extension="xlsx"/>
        </form>
    </div>
    <div class="clearfix"/>
    <hr/>
    <div class="text-danger">
        使用说明：
        <ul>
            <li>导入的文件请严格按照第一步导出的数据（请不要删除或新增干部）</li>
            <li>此操作仅更新排序，不影响其他字段</li>
        </ul>
    </div>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" type="button" class="btn btn-primary"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"> 确定
    </button>
</div>

<script>
    $.fileInput($('#modalForm input[type=file]'))

    $("#submitBtn").click(function () {
        $("#modalForm").submit();
        return false;
    });
    $("#modalForm").validate({

        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                dataType: "json",
                success: function (ret) {
                    if (ret.success) {
                        $("#modal").modal('hide');
                        SysMsg.success('批量排序操作成功', '成功', function () {
                            $("#jqGrid").trigger("reloadGrid");
                        });
                    }
                    $btn.button('reset');
                }
            });
        }
    });

</script>