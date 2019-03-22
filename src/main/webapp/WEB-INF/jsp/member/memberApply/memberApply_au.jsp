<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="OW_APPLY_STAGE_PASS" value="<%=OwConstants.OW_APPLY_STAGE_PASS%>"/>
<c:set var="OW_APPLY_STAGE_ACTIVE" value="<%=OwConstants.OW_APPLY_STAGE_ACTIVE%>"/>
<c:set var="OW_APPLY_STAGE_CANDIDATE" value="<%=OwConstants.OW_APPLY_STAGE_CANDIDATE%>"/>
<c:set var="OW_APPLY_STAGE_PLAN" value="<%=OwConstants.OW_APPLY_STAGE_PLAN%>"/>
<c:set var="OW_APPLY_STAGE_DRAW" value="<%=OwConstants.OW_APPLY_STAGE_DRAW%>"/>
<c:set var="OW_APPLY_STAGE_GROW" value="<%=OwConstants.OW_APPLY_STAGE_GROW%>"/>

<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>
        申请入党
    </h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" autocomplete="off" disableautocomplete id="modalForm" method="post" action="${ctx}/memberApply_au">
        <c:if test="${empty memberApply}">
            <div class="form-group">
                <label class="col-sm-4 control-label no-padding-right"><span class="star">*</span>账号</label>

                <div class="col-sm-7">
                    <select required data-rel="select2-ajax" data-ajax-url="${ctx}/notMember_selects"
                            data-width="300"
                            name="userId" data-placeholder="请输入账号或姓名或学工号">
                        <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                    </select>
                </div>
            </div>

            <div class="form-group" id="party" >
                <label class="col-sm-4 control-label no-padding-right">所属${_p_partyName}</label>
                <div class="col-sm-7">
                    <select data-rel="select2-ajax" data-width="300"  data-ajax-url="${ctx}/party_selects?auth=1"
                            name="partyId" data-placeholder="请选择">
                        <option value="${party.id}" title="${party.isDeleted}">${party.name}</option>
                    </select>
                </div>
            </div>
            <div class="form-group" id="branch" style="${empty branch?'display: none;':''}">
                <div class="col-sm-offset-4 col-sm-7">
                    <select data-rel="select2-ajax" data-width="300"  data-ajax-url="${ctx}/branch_selects?auth=1"
                            name="branchId" data-placeholder="请选择党支部">
                        <option value="${branch.id}" title="${branch.isDeleted}">${branch.name}</option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-4 control-label no-padding-right"><span class="star">*</span>提交书面申请书时间</label>

                <div class="col-sm-3">
                    <div class="input-group">
                        <input required class="form-control date-picker" name="_applyTime" type="text"
                               data-date-format="yyyy-mm-dd" style="width: 100px;"
                               value="${cm:formatDate(memberApply.applyTime,'yyyy-MM-dd')}  "/>
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
                </div>
            </div>
        </c:if>
        <c:if test="${not empty memberApply}">
            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right">账号</label>

                <div class="col-sm-9 label-text">
                        ${sysUser.realname}-${sysUser.code}
                    <input type="hidden" name="userId" value="${sysUser.id}">
                </div>
            </div>
            <div class="form-group" id="party" style="${empty party?'display: none;':''}">
                <label class="col-sm-3 control-label no-padding-right">所属${_p_partyName}</label>
                <div class=" col-sm-9">
                    <select data-rel="select2-ajax" data-width="350"  data-ajax-url="${ctx}/party_selects?auth=1"
                            name="partyId" data-placeholder="请选择">
                        <option value="${party.id}" title="${party.isDeleted}">${party.name}</option>
                    </select>
                </div>
            </div>
            <div class="form-group" id="branch" style="${empty branch?'display: none;':''}">
                <div class="col-sm-offset-3 col-sm-9">
                    <select data-rel="select2-ajax" data-width="350"  data-ajax-url="${ctx}/branch_selects?auth=1"
                            name="branchId" data-placeholder="请选择党支部">
                        <option value="${branch.id}" title="${branch.isDeleted}">${branch.name}</option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-6 control-label no-padding-right"><span class="star">*</span>提交书面申请书时间</label>

                <div class="col-sm-3">
                    <div class="input-group" style="width: 150px">
                        <input required class="form-control date-picker" name="_applyTime" type="text"
                               data-date-format="yyyy-mm-dd"
                               value="${cm:formatDate(memberApply.applyTime,'yyyy-MM-dd')}  "/>
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
                </div>
            </div>
            <c:if test="${param.stage>OW_APPLY_STAGE_PASS}">
                <div class="form-group">
                    <label class="col-xs-6 control-label">确定为入党积极分子时间</label>

                    <div class="col-xs-3">
                        <div class="input-group" style="width: 150px">
                            <input class="form-control date-picker" name="_activeTime" type="text"
                                   data-date-format="yyyy-mm-dd"
                                   value="${cm:formatDate(memberApply.activeTime,'yyyy-MM-dd')}"/>
                            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                        </div>
                    </div>
                </div>
                <c:if test="${param.stage>OW_APPLY_STAGE_ACTIVE}">
                    <div class="form-group">
                        <label class="col-xs-6 control-label">确定为发展对象时间</label>

                        <div class="col-xs-3">
                            <div class="input-group" style="width: 150px">
                                <input class="form-control date-picker" name="_candidateTime" type="text"
                                       data-date-format="yyyy-mm-dd"
                                       value="${cm:formatDate(memberApply.candidateTime,'yyyy-MM-dd')}"/>
                                <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-6 control-label">参加培训时间</label>

                        <div class="col-xs-3">
                            <div class="input-group" style="width: 150px">
                                <input class="form-control date-picker" name="_trainTime" type="text"
                                       data-date-format="yyyy-mm-dd"
                                       value="${cm:formatDate(memberApply.trainTime,'yyyy-MM-dd')}"/>
                                <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                            </div>
                        </div>
                    </div>
                    <c:if test="${param.stage>OW_APPLY_STAGE_CANDIDATE}">
                        <div class="form-group">
                            <label class="col-xs-6 control-label">列入发展计划时间</label>

                            <div class="col-xs-3">
                                <div class="input-group" style="width: 150px">
                                    <input class="form-control date-picker" name="_planTime" type="text"
                                           data-date-format="yyyy-mm-dd"
                                           value="${cm:formatDate(memberApply.planTime,'yyyy-MM-dd')}"/>
                                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                                </div>
                            </div>
                        </div>
                        <c:if test="${param.stage>OW_APPLY_STAGE_PLAN}">
                            <div class="form-group">
                                <label class="col-xs-6 control-label">领取志愿书时间</label>

                                <div class="col-xs-3">
                                    <div class="input-group" style="width: 150px">
                                        <input class="form-control date-picker" name="_drawTime" type="text"
                                               data-date-format="yyyy-mm-dd"
                                               value="${cm:formatDate(memberApply.drawTime,'yyyy-MM-dd')}"/>
                                        <span class="input-group-addon"> <i
                                                class="fa fa-calendar bigger-110"></i></span>
                                    </div>
                                </div>
                            </div>
                            <c:if test="${param.stage>OW_APPLY_STAGE_DRAW}">
                                <div class="form-group">
                                    <label class="col-xs-6 control-label">入党时间</label>

                                    <div class="col-xs-3">
                                        <div class="input-group" style="width: 150px">
                                            <input class="form-control date-picker" name="_growTime" type="text"
                                                   data-date-format="yyyy-mm-dd"
                                                   value="${cm:formatDate(memberApply.growTime,'yyyy-MM-dd')}"/>
                                            <span class="input-group-addon"> <i
                                                    class="fa fa-calendar bigger-110"></i></span>
                                        </div>
                                    </div>
                                </div>
                                <c:if test="${param.stage>OW_APPLY_STAGE_GROW}">
                                    <div class="form-group">
                                        <label class="col-xs-6 control-label">转正时间</label>

                                        <div class="col-xs-3">
                                            <div class="input-group" style="width: 150px">
                                                <input class="form-control date-picker" name="_positiveTime" type="text"
                                                       data-date-format="yyyy-mm-dd"
                                                       value="${cm:formatDate(memberApply.positiveTime,'yyyy-MM-dd')}"/>
                                                <span class="input-group-addon"> <i
                                                        class="fa fa-calendar bigger-110"></i></span>
                                            </div>
                                        </div>
                                    </div>
                                </c:if>
                            </c:if>
                        </c:if>
                    </c:if>
                </c:if>
            </c:if>
        </c:if>
        <c:if test="${empty memberApply}">
            <div class="form-group">
                <label class="col-sm-4 control-label no-padding-right"> 备注</label>

                <div class="col-sm-7">
                    <textarea name="remark" class="form-control" rows="5">${memberApply.remark}</textarea>
                </div>
            </div>
        </c:if>
    </form>
</div>
</div>
<div class="modal-footer">

    <input type="submit" class="btn btn-primary"
           value="<c:if test="${party!=null}">确定</c:if><c:if test="${party==null}">添加</c:if>"/>
</div>
<script>
    $.register.user_select($('#modalForm select[name=userId]'));

    $.register.party_branch_select($("#modalForm"), "branch",
            '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}" );

    $.register.date($('.date-picker'));

    $("#modalForm").validate({
        submitHandler: function (form) {

            if (!$("#party").is(":hidden")) {
                if ($('#modalForm select[name=partyId]').val() == '') {
                    bootbox.alert("请选择${_p_partyName}。");
                    return;
                }
            }
            if (!$("#branch").is(":hidden")) {
                if (!($('#modalForm select[name=branchId]').val() > 0)) {
                    bootbox.alert("请选择支部。");
                    return;
                }
            }

            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $("#modal").modal("hide");
                        <c:if test="${empty memberApply}">
                        //SysMsg.success("添加成功。",'提示',function(){
                        $.hashchange();
                        //});
                        </c:if>
                        <c:if test="${not empty memberApply}">
                        $("#jqGrid").trigger("reloadGrid");
                        </c:if>
                    }
                }
            });
        }
    });
</script>
