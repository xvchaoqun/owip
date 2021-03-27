<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cadreEva!=null}">编辑</c:if><c:if test="${cadreEva==null}">添加</c:if>年度考核记录</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadreEva_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${cadreEva.id}">
        <c:if test="${cadreEva==null && param._auth=='1'}">
            <input type="hidden" name="cadreId" value="${param.cadreId}">
        </c:if>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>年份</label>
				<div class="col-xs-6">
                    <input required autocomplete="off" disableautocomplete style="width: 80px"
                               class="form-control date-picker" placeholder="请选择" name="year"
                               type="text"
                               data-date-format="yyyy" data-date-min-view-mode="2" value="${cadreEva.year}"/>
				</div>
			</div>
            <div class="form-group">
                <c:set var="cadre" value="${cm:getCadreById(cadreEva.cadreId)}"/>
                <label class="col-xs-3 control-label"><span class="star">*</span> 选择干部</label>
                <div class="col-xs-6">
                    <c:choose>
                        <c:when test="${param._auth=='1'}">
                            <select data-rel="select2-ajax"
                                    data-ajax-url="${ctx}/cadre_selects?status=${CADRE_STATUS_CJ},${CADRE_STATUS_KJ},${CADRE_STATUS_LEADER}" data-width="273"
                                    name="cadreId" data-placeholder="请选择" required disabled>
                                <option value="${param.cadreId}">${cadreView.user.realname}-${cadreView.user.code}-${cadreView.unit.name}</option>
                            </select>
                        </c:when>
                        <c:otherwise>
                            <select data-rel="select2-ajax"
                                    data-ajax-url="${ctx}/cadre_selects?status=${CADRE_STATUS_CJ},${CADRE_STATUS_KJ},${CADRE_STATUS_LEADER}" data-width="273"
                                    name="cadreId" data-placeholder="请选择" required>
                                <option value="${cadreEva.cadreId}">${cadre.user.realname}-${cadre.user.code}-${cadre.user.unit}</option>
                            </select>
                        </c:otherwise>
                    </c:choose>

                </div>
            </div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>考核情况</label>
				<div class="col-xs-6">
                    <select required data-rel="select2" data-width="273"
                            name="type" data-placeholder="请选择">
                        <option></option>
                        <c:import url="/metaTypes?__code=mc_cadre_eva"/>
                    </select>
                    <script type="text/javascript">
                        $("#modal form select[name=type]").val(${cadreEva.type});
                    </script>
				</div>
			</div>
            <div class="form-group">
				<label class="col-xs-3 control-label">时任职务</label>
				<div class="col-xs-6">
                    <textarea class="form-control noEnter" name="title" rows="3">${empty cadreEva?cadre.title:cadreEva.title}</textarea>
				</div>
                <a href="javascript:;" onclick="$('#modalForm textarea[name=title]').val('').focus()">清空</a>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                    <textarea class="form-control limited" name="remark">${cadreEva.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${cadreEva!=null}">确定</c:if><c:if test="${cadreEva==null}">添加</c:if></button>
</div>
<script>
    $.register.user_select($('#modalForm select[name=cadreId]'));
    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid_eva").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    //$("#modalForm :checkbox").bootstrapSwitch();
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    $('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    $('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));
</script>