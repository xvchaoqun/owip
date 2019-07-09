<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cadreBook!=null}">编辑</c:if><c:if test="${cadreBook==null}">添加</c:if>出版著作情况</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadreBook_au?toApply=${param.toApply}&cadreId=${cadre.id}" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="_isUpdate" value="${param._isUpdate}">
        <input type="hidden" name="applyId" value="${param.applyId}">
        <input type="hidden" name="id" value="${cadreBook.id}">
        <div class="form-group">
            <label class="col-xs-3 control-label">姓名</label>
            <div class="col-xs-6 label-text">
                ${sysUser.realname}
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>出版日期</label>
            <div class="col-xs-6">
                <div class="input-group" style="width: 120px">
                    <input required class="form-control date-picker" name="_pubTime" type="text"
                           data-date-format="yyyy-mm-dd" value="${cm:formatDate(cadreBook.pubTime,'yyyy-MM-dd')}" />
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>著作名称</label>
            <div class="col-xs-6">
                <input required class="form-control" type="text" name="name" value="${cadreBook.name}">
                <span class="help-block">注：不要加书名号。</span>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>类型</label>
            <div class="col-xs-6">
                <select required data-rel="select2" name="type"
                        data-placeholder="请选择" data-width="162">
                    <option></option>
                    <c:forEach items="<%=CadreConstants.CADRE_BOOK_TYPE_MAP%>" var="type">
                        <option value="${type.key}">${type.value}</option>
                    </c:forEach>
                </select>
                <script type="text/javascript">
                    $("#modal form select[name=type]").val(${cadreBook.type});
                </script>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>出版社</label>
            <div class="col-xs-6">
                <input required class="form-control" type="text" name="publisher" value="${cadreBook.publisher}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">备注</label>
            <div class="col-xs-6">
                <textarea class="form-control" name="remark">${cadreBook.remark}</textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${cadreBook!=null}">确定</c:if><c:if test="${cadreBook==null}">添加</c:if>"/>
</div>

<script>
    $.register.date($('.date-picker'));
    $.fileInput($('#modalForm input[type=file]'))

    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal("hide");
                        <c:if test="${param.toApply!=1}">
                        $("#jqGrid_cadreBook").trigger("reloadGrid");
                        </c:if>
                        <c:if test="${param.toApply==1}">
                        <c:if test="${param._isUpdate==1}">
                        $("#body-content-view").load("${ctx}/modifyTableApply_detail?module=${param.module}&opType=${param.opType}&applyId=${param.applyId}&_="+new Date().getTime())
                        </c:if>
                        <c:if test="${param._isUpdate!=1}">
                        $.hashchange('cls=1&module=${param.module}');
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