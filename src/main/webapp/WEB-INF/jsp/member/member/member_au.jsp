<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div style="width: 900px">
<h3>${op}党员</h3>
<hr/>
<form class="form-horizontal" action="${ctx}/member_au" id="modalForm" method="post">
    <div class="row">
        <div class="col-xs-7">
            <div class="form-group">
                <label class="col-xs-4 control-label"><span class="star">*</span>账号</label>
                <div class="col-xs-6">
                    <c:if test="${not empty member}">
                        <input type="hidden" value="${member.userId}" name="userId">
                    </c:if>
                    <select ${not empty member?"disabled data-theme='default'":""} required data-rel="select2-ajax"
                                                                                   data-ajax-url="${ctx}/notMember_selects"
                                                                                   name="userId" data-width="320"
                                                                                   data-placeholder="请输入账号或姓名或学工号">
                        <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label"><span class="star">*</span>所属分党委</label>
                <div class="col-xs-6">
                    <select required class="form-control" data-rel="select2-ajax"
                            data-ajax-url="${ctx}/party_selects?auth=1"
                            name="partyId" data-placeholder="请选择" data-width="320">
                        <option value="${party.id}">${party.name}</option>
                    </select>
                </div>
            </div>
            <div class="form-group" style="${(empty branch)?'display: none':''}" id="branchDiv">
                <label class="col-xs-4 control-label"><span class="star">*</span>所属党支部</label>
                <div class="col-xs-6">
                    <select class="form-control" data-rel="select2-ajax" data-ajax-url="${ctx}/branch_selects?auth=1"
                            name="branchId" data-placeholder="请选择" data-width="320">
                        <option value="${branch.id}">${branch.name}</option>
                    </select>
                </div>
            </div>
            <script>
                $.register.party_branch_select($("#modalForm"), "branchDiv",
                    '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}");
            </script>
            <div class="form-group">
                <label class="col-xs-4 control-label"><span class="star">*</span>党籍状态</label>
                <c:if test="${not empty member.politicalStatus}">
                    <div class="col-xs-6 label-text">
                            ${MEMBER_POLITICAL_STATUS_MAP.get(member.politicalStatus)}
                    </div>
                </c:if>
                <c:if test="${empty member.politicalStatus}">
                    <div class="col-xs-6">

                        <select required data-rel="select2" name="politicalStatus" data-placeholder="请选择"
                                data-width="150">
                            <option></option>
                            <c:forEach items="${MEMBER_POLITICAL_STATUS_MAP}" var="_status">
                                <option value="${_status.key}">${_status.value}</option>
                            </c:forEach>
                        </select>
                        <script>
                            $("#modalForm select[name=politicalStatus]").val(${member.politicalStatus});
                        </script>
                    </div>
                </c:if>
            </div>

            <div class="form-group">
                <label class="col-xs-4 control-label">组织关系转入时间</label>
                <div class="col-xs-6">
                    <div class="input-group" style="width: 150px">
                        <input class="form-control date-picker" name="_transferTime" type="text"
                               data-date-format="yyyy-mm-dd"
                               value="${cm:formatDate(member.transferTime,'yyyy-MM-dd')}"/>
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label">提交书面申请书时间</label>
                <div class="col-xs-6">
                    <div class="input-group" style="width: 150px">
                        <input class="form-control date-picker" name="_applyTime" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(member.applyTime,'yyyy-MM-dd')}"/>
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label">确定为入党积极分子时间</label>
                <div class="col-xs-6">
                    <div class="input-group" style="width: 150px">
                        <input class="form-control date-picker" name="_activeTime" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(member.activeTime,'yyyy-MM-dd')}"/>
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label">确定为发展对象时间</label>
                <div class="col-xs-6">
                    <div class="input-group" style="width: 150px">
                        <input class="form-control date-picker" name="_candidateTime" type="text"
                               data-date-format="yyyy-mm-dd"
                               value="${cm:formatDate(member.candidateTime,'yyyy-MM-dd')}"/>
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label">入党时间</label>
                <div class="col-xs-6">
                    <div class="input-group" style="width: 150px">
                        <input class="form-control date-picker" name="_growTime" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(member.growTime,'yyyy-MM-dd')}"/>
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label">转正时间</label>
                <div class="col-xs-6">
                    <div class="input-group" style="width: 150px">
                        <input class="form-control date-picker" name="_positiveTime" type="text"
                               data-date-format="yyyy-mm-dd"
                               value="${cm:formatDate(member.positiveTime,'yyyy-MM-dd')}"/>
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
                </div>
            </div>

        </div>
        <div class="col-xs-5">
            <div class="form-group">
                <label class="col-xs-3 control-label">党内职务</label>
                <div class="col-xs-8">
						<textarea class="form-control limited noEnter" type="text"
                                  name="partyPost" rows="3">${member.partyPost}</textarea>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">党内奖励</label>
                <div class="col-xs-8">
						<textarea class="form-control limited noEnter" type="text"
                                  name="partyReward" rows="3">${member.partyReward}</textarea>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">其他奖励</label>
                <div class="col-xs-8">
						<textarea class="form-control limited noEnter" type="text"
                                  name="otherReward" rows="3">${member.otherReward}</textarea>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label"><span class="star">*</span>增加类型</label>
                <div class="col-xs-8">
                    <select required data-rel="select2" name="addType"
                            data-width="235"
                            data-placeholder="请选择">
                        <option></option>
                        <c:import url="/metaTypes?__code=mc_member_add_type"/>
                    </select>
                    <script type="text/javascript">
                        $("#cadreForm select[name=addType]").val('${member.addType}');
                    </script>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label"><span class="star">*</span>添加/更新<br/>原因</label>
                <div class="col-xs-8">
						<textarea required class="form-control limited noEnter" type="text"
                                  name="reason" rows="3"></textarea>
                </div>
            </div>
        </div>
    </div>
</form>
<div class="clearfix form-actions center">
        <button class="btn btn-info" type="submit">
            <i class="ace-icon fa fa-check bigger-110"></i>
            提交
        </button>

        &nbsp; &nbsp; &nbsp;
        <button class="hideView btn" type="button">
            <i class="ace-icon fa fa-reply bigger-110"></i>
            返回
        </button>
</div>
</div>
<script>
    jgrid_left = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollLeft();
    jgrid_top = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollTop();
    $('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'), {endDate: '${_today}'});
    $("#body-content-view button[type=submit]").click(function () {
        $("#modalForm").submit();
        return false;
    });
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        //SysMsg.success('提交成功。', '成功',function(){
                        $("#jqGrid").trigger("reloadGrid");
                        $.hideView();
                        //});
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();

    $.register.user_select($('#modalForm select[name=userId]'));
</script>