<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/member/constants.jsp" %>
<div style="width: 900px">
    <h3>${empty memberApply?"添加":"修改"}(${OW_APPLY_STAGE_MAP.get(cm:toByte(param.stage))})</h3>
    <hr/>
    <form class="form-horizontal" autocomplete="off" disableautocomplete id="applyAuForm" method="post"
          action="${ctx}/memberApply_au">
        <input type="hidden" name="stage" value="${param.stage}">
        <input type="hidden" name="op" value="${param.op}">
        <div class="row">
            <div class="col-xs-7">
                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right"><span class="star">*</span> 账号</label>
                    <c:if test="${not empty memberApply}">
                        <div class="col-sm-9 label-text">
                                ${sysUser.realname}-${sysUser.code}
                            <input type="hidden" name="userId" value="${sysUser.id}">
                            <span class="help-block">账号类别：${USER_TYPE_MAP.get(sysUser.type)}</span>
                        </div>
                    </c:if>
                    <c:if test="${empty memberApply}">
                        <div class="col-sm-9">
                            <select required data-rel="select2-ajax" data-ajax-url="${ctx}/notMember_selects"
                                    data-width="300"
                                    name="userId" data-placeholder="请输入账号或姓名或学工号">
                                <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                            </select>
                            <span id="userTypeSpan" class="help-block"></span>
                        </div>
                    </c:if>

                    <script>
                        var userId = $.trim($('#applyAuForm select[name=userId]').val());
                        if (userId == null || userId == undefined || userId == "") {
                            $('#userTypeSpan').text("账号类别：无");
                        }else {
                            $('#userTypeSpan').text('账号类别：${USER_TYPE_MAP.get(sysUser.type)}');
                        }
                    </script>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label no-padding-right"><span class="star">*</span>联系基层党组织</label>
                    <div class="col-xs-9 ">
                        <select required class="form-control" data-width="300" data-rel="select2-ajax" data-ajax-url="${ctx}/party_selects?del=0"
                                name="partyId" data-placeholder="请选择">
                            <option value="${party.id}">${party.name}</option>
                        </select>
                    </div>
                </div>
                <div class="form-group" style="${(empty branch)?'display: none':''}" id="branchDiv">
                    <label class="col-xs-3 control-label"><span class="star">*</span>联系党支部</label>
                    <div class="col-xs-9">
                        <select class="form-control" data-width="300" data-rel="select2-ajax" data-ajax-url="${ctx}/branch_selects?del=0"
                                name="branchId" data-placeholder="请选择党支部">
                            <option value="${branch.id}">${branch.name}</option>
                        </select>
                    </div>
                </div>
                <script>
                    $.register.party_branch_select($("#applyAuForm"), "branchDiv",
                        '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}", "partyId", "branchId", true);
                </script>
                <div class="form-group">
                    <label class="col-sm-6 control-label no-padding-right"> 入党申请时间</label>

                    <div class="col-sm-3">
                        <div class="input-group" style="width: 150px">
                            <input class="form-control date-picker" name="joinApplyTime" type="text"
                                   data-date-format="yyyy.mm.dd"
                                   value="${cm:formatDate(memberApply.joinApplyTime,'yyyy.MM.dd')}"/>
                            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-6 control-label no-padding-right"><span class="star">*</span>提交书面申请书时间</label>

                    <div class="col-sm-3">
                        <div class="input-group" style="width: 150px">
                            <input required class="form-control date-picker" name="applyTime" type="text"
                                   data-date-format="yyyy.mm.dd"
                                   value="${cm:formatDate(memberApply.applyTime,'yyyy.MM.dd')}"/>
                            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-6 control-label no-padding-right"> 入党志愿书接收人</label>

                    <div class="col-sm-3">
                        <div class="input-group" style="width: 150px">
                            <input style="width: 150px" class="form-control" type="text" name="drawAcceptor"
                                   value="${memberApply.drawAcceptor}">
                        </div>
                    </div>
                </div>

                <c:if test="${param.stage>OW_APPLY_STAGE_PASS}">
                <div class="form-group">
                    <label class="col-xs-6 control-label">确定为入党积极分子时间</label>

                    <div class="col-xs-3">
                        <div class="input-group" style="width: 150px">
                            <input class="form-control date-picker" name="activeTime" type="text"
                                   data-date-format="yyyy.mm.dd"
                                   value="${cm:formatDate(memberApply.activeTime,'yyyy.MM.dd')}"/>
                            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                        </div>
                    </div>
                </div>
                    <c:if test="${_p_contactUsers_count>0}">
                <div class="form-group">
                    <label class="col-xs-6 control-label">培养联系人
                    <button type="button" class="popupBtn btn btn-xs btn-warning"
                                data-url="${ctx}/apply_active_contact?ids=${sysUser.id}&gotoNext=2"><i class="fa fa-edit"></i></button>
                    </label>
                    <div class="col-xs-6 label-text">
                        <input type="hidden" name="contactUsers" value="${memberApply.contactUsers}">
                        <input type="hidden" name="contactUserIds" value="${memberApply.contactUserIds}">
                       <label class="contactUsers">${memberApply.contactUsers}</label>
                    </div>
                </div>
                    </c:if>
                <c:if test="${_pMap['memberApply_needActiveTrain']=='true'}">
                    <div class="form-group">
                        <label class="col-xs-6 control-label">积极分子参加培训时间</label>

                        <div class="col-xs-5">
                            <div class="input-group" style="width: 150px">
                                <input class="form-control date-picker" name="activeTrainStartTime" type="text"
                                       data-date-format="yyyy.mm.dd"
                                       value="${cm:formatDate(memberApply.activeTrainStartTime,'yyyy.MM.dd')}"/>
                                <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                            </div>
                            至
                            <div class="input-group" style="width: 150px">
                                <input class="form-control date-picker" name="activeTrainEndTime" type="text"
                                       data-date-format="yyyy.mm.dd"
                                       value="${cm:formatDate(memberApply.activeTrainEndTime,'yyyy.MM.dd')}"/>
                                <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-6 control-label">积极分子结业考试成绩</label>
                        <div class="col-xs-5">
                            <input style="width: 150px" class="form-control" type="text" name="activeGrade"
                                   value="${memberApply.activeGrade}">
                        </div>
                    </div>
                </c:if>
                </c:if>

                <c:if test="${param.stage>OW_APPLY_STAGE_ACTIVE}">
                    <div class="form-group">
                        <label class="col-xs-6 control-label">确定为发展对象时间</label>

                        <div class="col-xs-3">
                            <div class="input-group" style="width: 150px">
                                <input class="form-control date-picker" name="candidateTime" type="text"
                                       data-date-format="yyyy.mm.dd"
                                       value="${cm:formatDate(memberApply.candidateTime,'yyyy.MM.dd')}"/>
                                <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                            </div>
                        </div>
                    </div>
                    <c:if test="${_p_sponsorUsers_count>0}">
                    <div class="form-group">
                        <label class="col-xs-6 control-label">入党介绍人
                        <button type="button" class="popupBtn btn btn-xs btn-warning"
                                    data-url="${ctx}/apply_candidate_sponsor?ids=${sysUser.id}&gotoNext=2"><i class="fa fa-edit"></i></button>
                        </label>
                        <div class="col-xs-6 label-text">
                           <input type="hidden" name="sponsorUsers" value="${memberApply.sponsorUsers}">
                           <input type="hidden" name="sponsorUserIds" value="${memberApply.sponsorUserIds}">
                           <label class="sponsorUsers">${memberApply.sponsorUsers}</label>
                        </div>
                    </div>
                        </c:if>
                    <div class="form-group">
                        <label class="col-xs-6 control-label">发展对象参加培训时间</label>

                        <div class="col-xs-5">
                            <div class="input-group" style="width: 150px">
                                <input class="form-control date-picker" name="candidateTrainStartTime" type="text"
                                       data-date-format="yyyy.mm.dd"
                                       value="${cm:formatDate(memberApply.candidateTrainStartTime,'yyyy.MM.dd')}"/>
                                <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                            </div>
                            <c:if test="${_memberApply_needCandidateTrain}">
                                至
                                <div class="input-group" style="width: 150px">
                                    <input class="form-control date-picker" name="candidateTrainEndTime" type="text"
                                           data-date-format="yyyy.mm.dd"
                                           value="${cm:formatDate(memberApply.candidateTrainEndTime,'yyyy.MM.dd')}"/>
                                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                                </div>
                            </c:if>
                        </div>
                    </div>
                    </c:if>
                    <c:if test="${_memberApply_needCandidateTrain}">
                        <div class="form-group">
                            <label class="col-xs-6 control-label">发展对象结业考试成绩</label>
                            <div class="col-xs-4">
                                <input style="width: 150px" class="form-control" type="text" name="candidateGrade"
                                       value="${memberApply.candidateGrade}">
                            </div>
                        </div>
                    </c:if>
            </div>
            <div class="col-xs-5">
                    <c:if test="${param.stage>OW_APPLY_STAGE_CANDIDATE&&!_ignore_plan_and_draw}">
                        <div class="form-group">
                            <label class="col-xs-6 control-label">列入发展计划时间</label>

                            <div class="col-xs-3">
                                <div class="input-group" style="width: 150px">
                                    <input class="form-control date-picker" name="planTime" type="text"
                                           data-date-format="yyyy.mm.dd"
                                           value="${cm:formatDate(memberApply.planTime,'yyyy.MM.dd')}"/>
                                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                                </div>
                            </div>
                        </div>
                        </c:if>
                        <c:if test="${param.stage>OW_APPLY_STAGE_PLAN&&!_ignore_plan_and_draw}">
                            <div class="form-group">
                                <label class="col-xs-6 control-label">领取志愿书时间</label>

                                <div class="col-xs-3">
                                    <div class="input-group" style="width: 150px">
                                        <input class="form-control date-picker" name="drawTime" type="text"
                                               data-date-format="yyyy.mm.dd"
                                               value="${cm:formatDate(memberApply.drawTime,'yyyy.MM.dd')}"/>
                                        <span class="input-group-addon"> <i
                                                class="fa fa-calendar bigger-110"></i></span>
                                    </div>
                                </div>
                            </div>
                            </c:if>
                            <c:if test="${(param.stage>OW_APPLY_STAGE_DRAW&&!_ignore_plan_and_draw)||(param.stage>OW_APPLY_STAGE_CANDIDATE&&_ignore_plan_and_draw)}">
                            <div class="form-group">
                                <label class="col-xs-6 control-label">入党时间</label>

                                <div class="col-xs-3">
                                    <div class="input-group" style="width: 150px">
                                        <input class="form-control date-picker" name="growTime" type="text"
                                               data-date-format="yyyy.mm.dd"
                                               value="${cm:formatDate(memberApply.growTime,'yyyy.MM.dd')}"/>
                                        <span class="input-group-addon"> <i
                                                class="fa fa-calendar bigger-110"></i></span>
                                    </div>
                                </div>
                            </div>
                                <c:if test="${_p_growContactUsers_count>0}">
                                    <div class="form-group">
                                        <label class="col-xs-6 control-label">培养联系人
                                        <button type="button" class="popupBtn btn btn-xs btn-warning"
                                                    data-url="${ctx}/apply_grow_contact?ids=${sysUser.id}&gotoNext=2"><i class="fa fa-edit"></i></button>
                                        </label>
                                        <div class="col-xs-6 label-text">
                                            <input type="hidden" name="growContactUsers" value="${memberApply.growContactUsers}">
                                            <input type="hidden" name="growContactUserIds" value="${memberApply.growContactUserIds}">
                                           <label class="growContactUsers">${memberApply.growContactUsers}</label>
                                        </div>
                                    </div>
                                        </c:if>
                            </c:if>
                            <c:if test="${param.stage>OW_APPLY_STAGE_GROW}">
                                <div class="form-group">
                                    <label class="col-xs-6 control-label">转正时间</label>

                                    <div class="col-xs-3">
                                        <div class="input-group" style="width: 150px">
                                            <input class="form-control date-picker" name="positiveTime" type="text"
                                                   data-date-format="yyyy.mm.dd"
                                                   value="${cm:formatDate(memberApply.positiveTime,'yyyy.MM.dd')}"/>
                                            <span class="input-group-addon"> <i
                                                    class="fa fa-calendar bigger-110"></i></span>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                <div class="form-group">
                    <label class="col-sm-4 control-label no-padding-right"> 备注</label>
                    <div class="col-sm-7">
                        <textarea name="remark" class="form-control" rows="5">${memberApply.remark}</textarea>
                    </div>
                </div>
            </div>
        </div>
    </form>
    <div class="clearfix form-actions center">
        <button id="applyAuBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> 确定</button>
        &nbsp; &nbsp; &nbsp;
        <button class="hideView btn btn-default" type="button">
            <i class="ace-icon fa fa-reply bigger-110"></i>
            返回
        </button>
    </div>
</div>
<script>
    var $select = $.register.user_select($('#applyAuForm select[name=userId]'));
    $select.on("change",function(){
        //console.log($(this).select2("data")[0].type)
        $('#userTypeSpan').text("账号类别：" + $(this).select2("data")[0].type);
    });
    $.register.date($('.date-picker'));
    $("#applyAuBtn").click(function () {
        $("#applyAuForm").submit();
        return false;
    });
    $("#applyAuForm").validate({
        submitHandler: function (form) {
            var $btn = $("#applyAuBtn").button('loading');
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $("#jqGrid").trigger("reloadGrid");
                        $.hideView();
                    }
                    $btn.button('reset');
                }
            });
        }
    });
</script>