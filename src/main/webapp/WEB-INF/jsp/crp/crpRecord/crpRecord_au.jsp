<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=CrpConstants.CRP_RECORD_TYPE_OUT%>" var="CRP_RECORD_TYPE_OUT"/>
<c:set value="<%=CrpConstants.CRP_RECORD_TYPE_IN%>" var="CRP_RECORD_TYPE_IN"/>
<c:set value="<%=CrpConstants.CRP_RECORD_TYPE_TRANSFER%>" var="CRP_RECORD_TYPE_TRANSFER"/>
<c:set value="<%=CrpConstants.CRP_RECORD_TYPE_MAP%>" var="CRP_RECORD_TYPE_MAP"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${crpRecord!=null}">编辑</c:if><c:if test="${crpRecord==null}">添加</c:if>
        ${CRP_RECORD_TYPE_MAP.get(type)}</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/crpRecord_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${crpRecord.id}">
        <input type="hidden" name="type" value="${type}">
        <input type="hidden" name="isFinished" value="${param.isFinished}">
        <div class="col-xs-12">
            <div class="col-xs-6">
                <c:if test="${type!=CRP_RECORD_TYPE_TRANSFER}">
                    <div class="form-group">
                        <label class="col-xs-4 control-label">是否现任干部</label>

                        <div class="col-xs-6">
                            <input type="checkbox" class="big"
                                   name="isPresentCadre" ${(crpRecord==null ||crpRecord.isPresentCadre)?"checked":""}/>
                        </div>
                    </div>
                    <div class="form-group" id="cadreSelectsDiv">
                        <label class="col-xs-4 control-label"><span class="star">*</span> 选择干部</label>

                        <div class="col-xs-7">
                            <select data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects" data-width="220"
                                    name="cadreId" data-placeholder="请输入账号或姓名或学工号">
                                <option value="${cadre.id}">${sysUser.realname}-${sysUser.code}</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group" id="userSelectsDiv">
                        <label class="col-xs-4 control-label"><span class="star">*</span> 选择教职工</label>

                        <div class="col-xs-7">
                            <select data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects?types=${USER_TYPE_JZG},${USER_TYPE_RETIRE}"
                                    data-width="220"
                                    name="userId" data-placeholder="请输入账号或姓名或工作证号">
                                <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                            </select>
                        </div>
                    </div>
                </c:if>
                <c:if test="${type==CRP_RECORD_TYPE_TRANSFER}">
                    <div class="form-group">
                        <label class="col-xs-4 control-label"><span class="star">*</span>姓名</label>

                        <div class="col-xs-7">
                            <input required class="form-control" type="text" name="realname" value="${crpRecord.realname}">
                        </div>
                    </div>
                </c:if>
                <div class="form-group">
                    <label class="col-xs-4 control-label"><span class="star">*</span>时任职务</label>

                    <div class="col-xs-7">
                <textarea required class="form-control limited noEnter" type="text"
                          name="presentPost" maxlength="100">${crpRecord.presentPost}</textarea>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-4 control-label">联系电话</label>

                    <div class="col-xs-7">
                        <input class="form-control" type="text" name="phone" value="${crpRecord.phone}">
                    </div>
                </div>
                <c:if test="${type==CRP_RECORD_TYPE_OUT}">
                    <c:set var="unitCode" value="mc_temppost_out_unit"/>
                    <c:set var="postCode" value="mc_temppost_out_post"/>
                    <c:set var="unitCodeOther" value="mt_temppost_out_unit_other"/>
                    <c:set var="postCodeOther" value="mt_temppost_out_post_other"/>
                </c:if>
                <c:if test="${type==CRP_RECORD_TYPE_IN}">
                    <c:set var="unitCode" value="mc_temppost_in_unit"/>
                    <c:set var="postCode" value="mc_temppost_in_post"/>
                    <c:set var="unitCodeOther" value="mt_temppost_in_unit_other"/>
                    <c:set var="postCodeOther" value="mt_temppost_in_post_other"/>
                </c:if>
                <c:if test="${type==CRP_RECORD_TYPE_TRANSFER}">
                    <c:set var="unitCode" value="mc_temppost_transfer_unit"/>
                    <c:set var="postCode" value="mc_temppost_transfer_post"/>
                    <c:set var="unitCodeOther" value="mt_temppost_transfer_unit_other"/>
                    <c:set var="postCodeOther" value="mt_temppost_transfer_post_other"/>
                </c:if>
                <c:if test="${type!=CRP_RECORD_TYPE_IN}">
                    <div class="form-group">
                        <label class="col-xs-4 control-label"><span class="star">*</span>委派单位</label>

                        <div class="col-xs-7">
                            <select required data-rel="select2" name="toUnitType" data-width="220" data-placeholder="请选择">
                                <option></option>
                                <c:import url="/metaTypes?__code=${unitCode}"/>
                            </select>
                            <script type="text/javascript">
                                $("#modalForm select[name=toUnitType]").val(${crpRecord.toUnitType});
                            </script>
                        </div>
                    </div>
                    <div class="form-group" id="toUnitDiv">
                        <label class="col-xs-4 control-label">单位名称</label>

                        <div class="col-xs-7">
                            <input class="form-control" type="text" name="toUnit" value="${crpRecord.toUnit}">
                        </div>
                    </div>
                </c:if>
                <c:if test="${type==CRP_RECORD_TYPE_IN}">
                    <div class="form-group">
                        <label class="col-xs-4 control-label">委派单位</label>

                        <div class="col-xs-7">
                            <input class="form-control" type="text" name="toUnit" value="${crpRecord.toUnit}">
                        </div>
                    </div>
                </c:if>
                <div class="form-group">
                    <label class="col-xs-4 control-label"><span class="star">*</span>挂职类别</label>

                    <div class="col-xs-7">
                        <select required data-rel="select2" name="tempPostType" data-width="220" data-placeholder="请选择">
                            <option></option>
                            <c:import url="/metaTypes?__code=${postCode}"/>
                        </select>
                        <script type="text/javascript">
                            $("#modalForm select[name=tempPostType]").val(${crpRecord.tempPostType});
                        </script>
                    </div>
                </div>
                <div class="form-group" id="tempPostDiv">
                    <label class="col-xs-4 control-label">挂职类型</label>

                    <div class="col-xs-7">
                        <input class="form-control" type="text" name="tempPost" value="${crpRecord.tempPost}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-4 control-label">挂职项目</label>

                    <div class="col-xs-7">
                         <textarea class="form-control noEnter" type="text"
                          name="project">${crpRecord.project}</textarea>
                    </div>
                </div>
            </div>
            <div class="col-xs-6">
                <div class="form-group">
                    <label class="col-xs-4 control-label"><span class="star">*</span>挂职单位</label>
                    <div class="col-xs-7">
                        <c:if test="${type==CRP_RECORD_TYPE_OUT}">
                            <textarea required class="form-control noEnter" type="text"
                          name="unit">${crpRecord.unit}</textarea>
                        </c:if>
                        <c:if test="${type!=CRP_RECORD_TYPE_OUT}">
                        <select required name="unitId" data-rel="select2-ajax"
                                data-width="222"
                                data-ajax-url="${ctx}/unit_selects"
                                data-placeholder="请选择">
                            <option value="${unit.id}" delete="${unit.status==UNIT_STATUS_HISTORY}">${unit.name}</option>
                        </select>
                        <script>
                            $.register.del_select($("#modalForm select[name=unitId]"), 350)
                        </script>
                        </c:if>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-4 control-label"><span class="star">*</span>所任单位及职务</label>
                    <div class="col-xs-7">
                        <textarea required class="form-control" type="text" name="post" rows="3">${crpRecord.post}</textarea>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-4 control-label"><span class="star">*</span>挂职开始时间</label>

                    <div class="col-xs-7">
                        <div class="input-group">
                            <input required class="form-control date-picker" name="startDate" type="text"
                                   data-date-min-view-mode="1"
                                   data-date-format="yyyy-mm" value="${cm:formatDate(crpRecord.startDate,'yyyy-MM')}"/>
                            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-4 control-label"><span class="star">*</span>挂职拟结束时间</label>

                    <div class="col-xs-7">
                        <div class="input-group">
                            <input required class="form-control date-picker" name="endDate" type="text"
                                   data-date-min-view-mode="1"
                                   data-date-format="yyyy-mm" value="${cm:formatDate(crpRecord.endDate,'yyyy-MM')}"/>
                            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                        </div>
                    </div>
                </div>

                <c:if test="${param.isFinished==1}">
                    <div class="form-group">
                        <label class="col-xs-4 control-label"><span class="star">*</span>挂职实际结束时间</label>
                        <div class="col-xs-7">
                            <div class="input-group">
                                <input class="form-control date-picker required" type="text"  name="realEndDate"
                                       data-date-min-view-mode="1"
                                       data-date-format="yyyy-mm" value="${cm:formatDate(crpRecord.realEndDate, "yyyy-MM")}">
							<span class="input-group-addon">
                            <i class="fa fa-calendar bigger-110"></i>
                        </span>
                            </div>
                        </div>
                    </div>
                </c:if>
                <div class="form-group">
                    <label class="col-xs-4 control-label">是否存入干部简历</label>

                    <div class="col-xs-8">
                        <input type="checkbox" class="big"
                               name="isAddForm" ${(crpRecord==null ||crpRecord.isAddForm)?"checked":""}/>
                        <span class="help-block">注：选“是”，此条挂职记录将显示在干部任免审批表中</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-4 control-label">备注</label>

                    <div class="col-xs-7">
                <textarea class="form-control limited" type="text"
                          name="remark" maxlength="200">${crpRecord.remark}</textarea>
                    </div>
                </div>
            </div>
        </div>


    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary"
           value="<c:if test="${crpRecord!=null}">确定</c:if><c:if test="${crpRecord==null}">添加</c:if>"/>
</div>

<script>

    function isPresentCadreChange() {
        if ($("input[name=isPresentCadre]").bootstrapSwitch("state")) {
            $("#cadreSelectsDiv").show();
            $("#userSelectsDiv").hide();
            $("select[name=cadreId]").attr("required", "required");
            $("select[name=userId]").removeAttr("required");
            $("textarea[name=presentPost]").requireField(true);
        } else {
            $("#userSelectsDiv").show();
            $("#cadreSelectsDiv").hide();

            $("select[name=userId]").attr("required", "required");
            $("select[name=cadreId]").removeAttr("required");
            $("textarea[name=presentPost]").requireField(false);
        }
    }
    $('input[name=isPresentCadre]').on('switchChange.bootstrapSwitch', function (event, state) {
        isPresentCadreChange();
        $("select[name=userId], select[name=cadreId]").val(null).trigger("change")
    });
    isPresentCadreChange();

    function toUnitTypeChange() {
        if ($("#modalForm select[name=toUnitType]").val() ==
                '${cm:getMetaTypeByCode(unitCodeOther).id}') {
            $("#toUnitDiv").show();
            $("#modalForm input[name=toUnit]").attr("required", "required");
        } else {
            $("#toUnitDiv").hide();
            $("#modalForm input[name=toUnit]").removeAttr("required");
        }
    }
    $("#modalForm select[name=toUnitType]").change(function () {
        toUnitTypeChange();
    });
    toUnitTypeChange();

    function tempPostTypeChange() {
        if ($("#modalForm select[name=tempPostType]").val() ==
                '${cm:getMetaTypeByCode(postCodeOther).id}') {
            $("#tempPostDiv").show();
            $("#modalForm input[name=tempPost]").attr("required", "required");
        } else {
            $("#tempPostDiv").hide();
            $("#modalForm input[name=tempPost]").removeAttr("required");
        }
    }
    $("#modalForm select[name=tempPostType]").change(function () {
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
    $.register.user_select($('[data-rel="select2-ajax"]'), function (state) {
        var $status = state.status;
        //alert($("input[name=isPresentCadre]").bootstrapSwitch("state"))
        if (($status == '${CADRE_STATUS_CJ}' || $status == '${CADRE_STATUS_LEADER}') &&
                $("input[name=isPresentCadre]").bootstrapSwitch("state")) {

            $("#modalForm textarea[name=presentPost]").val(state.title);
            $("#modalForm input[name=phone]").val(state.mobile);
        }
        return $.register.templateSelection(state);
    });
    $.register.date($(".date-picker"))
    $('textarea.limited').inputlimiter();
</script>