<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cadrePaper!=null}">编辑</c:if><c:if test="${cadrePaper==null}">添加</c:if>发表论文情况</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadrePaper_au?toApply=${param.toApply}&cadreId=${cadre.id}" id="modalForm" method="post" enctype="multipart/form-data">
        <input type="hidden" name="_isUpdate" value="${param._isUpdate}">
        <input type="hidden" name="applyId" value="${param.applyId}">
        <input type="hidden" name="id" value="${cadrePaper.id}">
        <div class="form-group">
            <label class="col-xs-3 control-label">姓名</label>
            <div class="col-xs-6 label-text">
                ${sysUser.realname}
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">发表日期</label>
            <div class="col-xs-6">
                <div class="input-group" style="width: 150px">
                    <input required class="form-control date-picker" name="_pubTime" type="text"
                           data-date-format="yyyy-mm-dd" value="${cm:formatDate(cadrePaper.pubTime,'yyyy-MM-dd')}" />
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>
			<div class="form-group">
				<label class="col-xs-3 control-label">论文</label>
				<div class="col-xs-6 uploader">
                    <input ${cadrePaper==null?'required':''} class="form-control" type="file" name="_file" />
				</div>
			</div>
        <div class="form-group">
            <label class="col-xs-3 control-label">备注</label>
            <div class="col-xs-6">
                <textarea class="form-control" name="remark">${cadrePaper.remark}</textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${cadrePaper!=null}">确定</c:if><c:if test="${cadrePaper==null}">添加</c:if>"/>
</div>

<script>
    register_date($('.date-picker'));
    $.fileInput($('#modalForm input[type=file]'),{
        no_file:'请上传pdf文件',
        allowExt: ['pdf']
    })

    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal("hide");
                        <c:if test="${param.toApply!=1}">
                        $("#jqGrid_cadrePaper").trigger("reloadGrid");
                        </c:if>
                        <c:if test="${param.toApply==1}">
                        <c:if test="${param._isUpdate==1}">
                        $("#item-content").load("${ctx}/modifyCadrePaper_detail?applyId=${param.applyId}&_="+new Date().getTime())
                        </c:if>
                        <c:if test="${param._isUpdate!=1}">
                        $.hashchange('cls=1&module=${MODIFY_TABLE_APPLY_MODULE_CADRE_PAPER}');
                        </c:if>
                        </c:if>
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>