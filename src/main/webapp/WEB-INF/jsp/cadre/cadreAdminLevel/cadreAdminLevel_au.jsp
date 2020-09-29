<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set value="${_pMap['postTimeToDay']=='true'}" var="_p_postTimeToDay"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cadreAdminLevel!=null}">编辑</c:if><c:if test="${cadreAdminLevel==null}">添加</c:if>任职级经历</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadreAdminLevel_au?cadreId=${cadre.id}" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${cadreAdminLevel.id}">
        <div class="form-group">
            <label class="col-xs-3 control-label">姓名</label>
            <div class="col-xs-6 label-text">
                ${sysUser.realname}
            </div>
        </div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>行政级别</label>
				<div class="col-xs-6">
                    <select required data-rel="select2" name="adminLevel" data-placeholder="请选择">
                        <option></option>
                        <c:import url="/metaTypes?__id=${cm:getMetaClassByCode('mc_admin_level').id}"/>
                    </select>
                    <script type="text/javascript">
                        $("#modal form select[name=adminLevel]").val(${cadreAdminLevel.adminLevel});
                    </script>
				</div>
			</div>
            <div class="form-group">
                <label class="col-xs-3 control-label">职级始任职务</label>
                <div class="col-xs-6">
                    <textarea class="form-control noEnter" name="sPost">${cadreAdminLevel.sPost}</textarea>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">职级始任日期</label>
                <div class="col-xs-6">
                    <div class="input-group">
                        <c:if test="${_p_postTimeToDay}">
                            <input class="form-control date-picker" name="_sWorkTime" type="text"
                               data-date-format="yyyy.mm.dd" value="${cm:formatDate(cadreAdminLevel.sWorkTime,'yyyy.MM.dd')}"/>
                        </c:if>
                        <c:if test="${!_p_postTimeToDay}">
                            <input class="form-control date-picker" name="_sWorkTime" type="text"
                                data-date-min-view-mode="1" data-date-format="yyyy.mm" value="${cm:formatDate(cadreAdminLevel.sWorkTime,'yyyy.MM')}"/>
                        </c:if>
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">职级结束日期</label>
                <div class="col-xs-6">
                    <div class="input-group">
                        <c:if test="${_p_postTimeToDay}">
                            <input class="form-control date-picker" name="_eWorkTime" type="text"
                               data-date-format="yyyy.mm.dd" value="${cm:formatDate(cadreAdminLevel.eWorkTime,'yyyy.MM.dd')}"/>
                        </c:if>
                        <c:if test="${!_p_postTimeToDay}">
                            <input class="form-control date-picker" name="_eWorkTime" type="text"
                                data-date-min-view-mode="1" data-date-format="yyyy.mm" value="${cm:formatDate(cadreAdminLevel.eWorkTime,'yyyy.MM')}"/>
                        </c:if>
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
                </div>
            </div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                        <textarea  class="form-control limited"  name="remark"  rows="5">${cadreAdminLevel.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${cadreAdminLevel!=null}">确定</c:if><c:if test="${cadreAdminLevel==null}">添加</c:if>"/>
</div>

<script>
    $("#modal :checkbox").bootstrapSwitch();
    $('textarea.limited').inputlimiter();

    $.register.date($('.date-picker'));

    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        _reload();
                        //SysMsg.success('操作成功。', '成功');
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>