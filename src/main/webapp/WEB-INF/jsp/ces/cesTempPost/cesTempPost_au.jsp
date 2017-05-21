<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cesTempPost!=null}">编辑</c:if><c:if test="${cesTempPost==null}">添加</c:if>
        ${CES_TEMP_POST_TYPE_MAP.get(type)}</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cesTempPost_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cesTempPost.id}">
        <input type="hidden" name="type" value="${type}">

        <c:if test="${type!=CES_TEMP_POST_TYPE_TRANSFER}">
        <div class="form-group">
            <label class="col-xs-4 control-label">是否现任干部</label>

            <div class="col-xs-6">
                <input type="checkbox" class="big"
                       name="isPresentCadre" ${(cesTempPost==null ||cesTempPost.isPresentCadre)?"checked":""}/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">选择干部</label>

            <div class="col-xs-6">
                <select data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects"
                        name="cadreId" data-placeholder="请输入账号或姓名或学工号" data-width="270">
                    <option value="${cadre.id}">${sysUser.realname}-${sysUser.code}</option>
                </select>
            </div>
        </div>
        </c:if>
        <c:if test="${type==CES_TEMP_POST_TYPE_TRANSFER}">
        <div class="form-group">
            <label class="col-xs-4 control-label">姓名</label>
            <div class="col-xs-6">
                <input required class="form-control" type="text" name="realname" value="${cesTempPost.realname}">
            </div>
        </div>
        </c:if>
        <div class="form-group">
            <label class="col-xs-4 control-label">时任职务</label>

            <div class="col-xs-6">
                <textarea required class="form-control limited" type="text"
                          name="presentPost" maxlength="100">${cesTempPost.presentPost}</textarea>
            </div>
        </div>
        <c:if test="${type==CES_TEMP_POST_TYPE_OUT}">
            <c:set var="unitCode" value="mc_temppost_out_unit"/>
            <c:set var="postCode" value="mc_temppost_out_post"/>
            <c:set var="unitCodeOther" value="mt_temppost_out_unit_other"/>
            <c:set var="postCodeOther" value="mt_temppost_out_post_other"/>
        </c:if>
        <c:if test="${type==CES_TEMP_POST_TYPE_IN}">
            <c:set var="unitCode" value="mc_temppost_in_unit"/>
            <c:set var="postCode" value="mc_temppost_in_post"/>
            <c:set var="unitCodeOther" value="mt_temppost_in_unit_other"/>
            <c:set var="postCodeOther" value="mt_temppost_in_post_other"/>
        </c:if>
        <c:if test="${type==CES_TEMP_POST_TYPE_TRANSFER}">
            <c:set var="unitCode" value="mc_temppost_transfer_unit"/>
            <c:set var="postCode" value="mc_temppost_transfer_post"/>
            <c:set var="unitCodeOther" value="mt_temppost_transfer_unit_other"/>
            <c:set var="postCodeOther" value="mt_temppost_transfer_post_other"/>
        </c:if>
        <div class="form-group">
            <label class="col-xs-4 control-label">委派单位</label>

            <div class="col-xs-6">
                <select required data-rel="select2" name="toUnitType" data-placeholder="请选择">
                    <option></option>
                    <c:import url="/metaTypes?__code=${unitCode}"/>
                </select>
                <script type="text/javascript">
                    $("#modalForm select[name=toUnitType]").val(${cesTempPost.toUnitType});
                </script>
            </div>
        </div>
        <div class="form-group" id="toUnitDiv">
            <label class="col-xs-4 control-label">单位名称</label>

            <div class="col-xs-6">
                <input class="form-control" type="text" name="toUnit" value="${cesTempPost.toUnit}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">挂职类别</label>

            <div class="col-xs-6">
                <select required data-rel="select2" name="tempPostType" data-placeholder="请选择">
                    <option></option>
                    <c:import url="/metaTypes?__code=${postCode}"/>
                </select>
                <script type="text/javascript">
                    $("#modalForm select[name=tempPostType]").val(${cesTempPost.tempPostType});
                </script>
            </div>
        </div>
        <div class="form-group" id="tempPostDiv">
            <label class="col-xs-4 control-label">挂职类型</label>

            <div class="col-xs-6">
                <input  class="form-control" type="text" name="tempPost" value="${cesTempPost.tempPost}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">挂职单位及所任职务</label>

            <div class="col-xs-6">
                <textarea required class="form-control limited" type="text"
                          name="title" maxlength="100">${cesTempPost.title}</textarea>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">挂职开始时间</label>
            <div class="col-xs-6">
                <div class="input-group">
                    <input required class="form-control date-picker" name="startDate" type="text"
                           data-date-format="yyyy-mm-dd" value="${cm:formatDate(cesTempPost.startDate,'yyyy-MM-dd')}" />
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">挂职拟结束时间</label>
            <div class="col-xs-6">
                <div class="input-group">
                    <input required class="form-control date-picker" name="endDate" type="text"
                           data-date-format="yyyy-mm-dd" value="${cm:formatDate(cesTempPost.endDate,'yyyy-MM-dd')}" />
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>

        <div class="form-group">
            <label class="col-xs-4 control-label">备注</label>

            <div class="col-xs-6">
                <textarea required class="form-control limited" type="text"
                          name="remark" maxlength="200">${cesTempPost.remark}</textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary"
           value="<c:if test="${cesTempPost!=null}">确定</c:if><c:if test="${cesTempPost==null}">添加</c:if>"/>
</div>

<script>

    function toUnitTypeChange(){
        if($("#modalForm select[name=toUnitType]").val() ==
                '${cm:getMetaTypeByCode(unitCodeOther).id}'){
            $("#toUnitDiv").show();
            $("#modalForm input[name=toUnit]").attr("required", "required");
        }else{
            $("#toUnitDiv").hide();
            $("#modalForm input[name=toUnit]").removeAttr("required");
        }
    }
    $("#modalForm select[name=toUnitType]").change(function(){
        toUnitTypeChange();
    });
    toUnitTypeChange();

    function tempPostTypeChange(){
        if($("#modalForm select[name=tempPostType]").val() ==
                '${cm:getMetaTypeByCode(postCodeOther).id}'){
            $("#tempPostDiv").show();
            $("#modalForm input[name=tempPost]").attr("required", "required");
        }else{
            $("#tempPostDiv").hide();
            $("#modalForm input[name=tempPost]").removeAttr("required");
        }
    }
    $("#modalForm select[name=tempPostType]").change(function(){
        tempPostTypeChange();
    });
    tempPostTypeChange();
    
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
    $("#modalForm :checkbox").bootstrapSwitch();
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    register_user_select($('[data-rel="select2-ajax"]'), function (state) {
        var $status = state.status;
        //alert($("input[name=isPresentCadre]").bootstrapSwitch("state"))
        if (($status == '${CADRE_STATUS_MIDDLE}' || $status == '${CADRE_STATUS_LEADER}') &&
                $("input[name=isPresentCadre]").bootstrapSwitch("state")) {
            $("#modalForm textarea[name=presentPost]").val(state.title);
        }
        return templateSelection(state);
    });
    register_date($(".date-picker"))
    $('textarea.limited').inputlimiter();
</script>