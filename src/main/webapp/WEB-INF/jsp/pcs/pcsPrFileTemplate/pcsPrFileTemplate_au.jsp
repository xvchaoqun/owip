<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="widget-box">
    <div class="widget-header">
        <h4 class="smaller">
            ${record!=null?"修改":"添加"}大会材料
        </h4>
    </div>
    <div class="widget-body">
        <div class="widget-main">
            <form class="form-horizontal" action="${ctx}/pcsPrFileTemplate_au" id="modalForm" method="post"
                  enctype="multipart/form-data">
                <div class="row">
                    <input type="hidden" name="id" value="${record.id}">
                    <div class="form-group">
                        <label class="col-xs-3 control-label">材料名称</label>

                        <div class="col-xs-6">
                            <input required class="form-control" type="text" name="name"
                                   value="${record.appointCount}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-3 control-label">上传材料</label>

                        <div class="col-xs-6">
                            <input class="form-control" type="file" name="_file"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-3 control-label">备注</label>
                        <div class="col-xs-6">
                            <textarea class="form-control limited" name="remark">${record.remark}</textarea>
                        </div>
                    </div>
                </div>
                <div class="clearfix form-actions center">
                    <button class="btn btn-info btn-sm" type="submit">
                        <i class="ace-icon fa fa-check "></i>
                        ${record!=null?"修改":"添加"}
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    $('textarea.limited').inputlimiter();
    $.fileInput($('#modalForm input[type=file]'));

    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $.hashchange();
                    }
                }
            });
        }
    });
</script>