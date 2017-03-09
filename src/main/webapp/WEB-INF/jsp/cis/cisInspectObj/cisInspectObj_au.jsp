<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cisInspectObj!=null}">编辑</c:if><c:if test="${cisInspectObj==null}">添加</c:if>干部考察材料</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cisInspectObj_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cisInspectObj.id}">

        <div class="form-group">
            <label class="col-xs-3 control-label">年份</label>
            <div class="col-xs-6">
                <div class="input-group">
                    <input required class="form-control date-picker" placeholder="请选择年份" name="year"
                           type="text"
                           data-date-format="yyyy" data-date-min-view-mode="2" value="${cisInspectObj==null?_thisYear:cisInspectObj.year}"/>
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">考察类型</label>

            <div class="col-xs-6">
                <select required data-rel="select2" name="typeId" data-placeholder="请选择" data-width="270">
                    <option></option>
                    <c:import url="/metaTypes?__code=mc_cis_type"/>
                </select>
                <script type="text/javascript">
                    $("#modal form select[name=typeId]").val(${cisInspectObj==null?(cm:getMetaTypeByCode("mt_cis_type_assign").id):cisInspectObj.typeId});
                </script>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">编号</label>
            <div class="col-xs-6">
                <input class="form-control" type="text" name="seq" value="${cisInspectObj.seq}">
                <span class="label-inline"> * 留空自动生成</span>
            </div>
        </div>

        <div class="form-group">
            <label class="col-xs-3 control-label">考察日期</label>
            <div class="col-xs-6">
                <div class="input-group">
                    <input  class="form-control date-picker required" name="_inspectDate"
                            type="text" data-date-format="yyyy-mm-dd"
                            value="${cm:formatDate(cisInspectObj.inspectDate, "yyyy-MM-dd")}"/>
                        <span class="input-group-addon">
                            <i class="fa fa-calendar bigger-110"></i>
                        </span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">考察对象</label>
            <div class="col-xs-6">
                <select data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects?type=0"
                        name="cadreId" data-placeholder="请输入账号或姓名或学工号"  data-width="270">
                    <option value="${cadre.id}">${cadre.user.realname}-${cadre.user.code}</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">考察主体</label>
            <div class="col-xs-6">
                <select required data-rel="select2" name="inspectorType" data-placeholder="请选择"  data-width="270">
                    <option></option>
                    <c:forEach var="type" items="${CIS_INSPECTOR_TYPE_MAP}">
                    <option value="${type.key}">${type.value}</option>
                    </c:forEach>
                </select>
                <script type="text/javascript">
                    $("#modal form select[name=inspectorType]").val(${cisInspectObj==null?CIS_INSPECTOR_TYPE_OW:cisInspectObj.inspectorType});
                </script>
            </div>
        </div>
        <div class="form-group" id="otherInspectorTypeDiv" style="display: none">
            <label class="col-xs-3 control-label">其他考察主体</label>
            <div class="col-xs-6">
                <input required class="form-control" type="text" name="otherInspectorType"
                       value="${cisInspectObj.otherInspectorType}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">考察组负责人</label>
            <div class="col-xs-6">
                <select data-rel="select2-ajax" data-ajax-url="${ctx}/cisInspector_selects"
                        name="chiefInspectorId" data-placeholder="请输入账号或姓名或学工号"  data-width="270">
                    <option value="${chiefInspector.id}">${chiefInspector.realname}-${chiefInspector.code}</option>
                </select>
            </div>
        </div>
        <%--<div class="form-group">
            <label class="col-xs-3 control-label">谈话人数</label>
            <div class="col-xs-6">
                <input required class="form-control digits" type="text" name="talkUserCount"
                       value="${cisInspectObj.talkUserCount}">
            </div>
        </div>--%>
        <div class="form-group">
            <label class="col-xs-3 control-label">备注</label>

            <div class="col-xs-6">
                <textarea class="form-control limited" name="remark">${cisInspectObj.remark}</textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary"
           value="<c:if test="${cisInspectObj!=null}">确定</c:if><c:if test="${cisInspectObj==null}">添加</c:if>"/>
</div>

<script>
    function inspectorTypeChange(){
        var $inspectorType = $("select[name=inspectorType]");
        if($inspectorType.val()=="${CIS_INSPECTOR_TYPE_OTHER}"){
            $("#otherInspectorTypeDiv").show();
            $("input[name=otherInspectorType]").attr("required", "required");
        }else{
            $("#otherInspectorTypeDiv").hide();
            $("input[name=otherInspectorType]").removeAttr("required");
        }
    }
    $("select[name=inspectorType]").change(function(){
        inspectorTypeChange();
    });
    inspectorTypeChange();
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
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    register_date($('.date-picker'));
    register_user_select($('#modalForm select[name=cadreId], #modalForm select[name=chiefInspectorId]'));

</script>