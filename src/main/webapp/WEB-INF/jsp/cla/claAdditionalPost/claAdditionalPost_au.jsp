<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${claAdditionalPost!=null}">编辑</c:if><c:if
            test="${claAdditionalPost==null}">添加</c:if>兼审单位</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cla/claAdditionalPost_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${claAdditionalPost.id}">
        <div class="form-group">
            <label class="col-xs-3 control-label">选择干部</label>
            <div class="col-xs-6">
                <select data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects?type=1" data-width="272"
                        name="cadreId" data-placeholder="请选择">
                    <option value="${cadre.id}">${cadre.realname}-${cadre.code}</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>兼审单位</label>
            <div class="col-xs-8">
                <select required class="form-control" name="unitId" data-rel="select2"
                        data-width="272"
                        data-placeholder="请选择兼审单位">
                    <option></option>
                    <c:forEach items="${unitMap}" var="unit">
                        <option value="${unit.key}">${unit.value.name}</option>
                    </c:forEach>
                </select>
                <script>
                    $("#modalForm select[name=unitId]").val('${claAdditionalPost.unitId}');
                </script>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>兼审单位职务属性</label>

            <div class="col-xs-6">
                <select required data-rel="select2" name="postId"
                        data-width="272" data-placeholder="请选择职务属性">
                    <option></option>
                    <jsp:include page="/metaTypes?__code=mc_post"/>
                </select>
                <script>
                    $("#modalForm select[name=postId]").val('${claAdditionalPost.postId}');
                </script>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">备注</label>
            <div class="col-xs-6">
                <textarea class="form-control limited" name="remark" rows="5">${claAdditionalPost.remark}</textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary"
           value="<c:if test="${claAdditionalPost!=null}">确定</c:if><c:if test="${claAdditionalPost==null}">添加</c:if>"/>
</div>
<script>
    jgrid_left = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollLeft();
    jgrid_top = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollTop();
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    $('[data-rel="select2"]').select2();
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $.register.date($('.date-picker'));
</script>