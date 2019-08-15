<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="DP_MEMBER_POLITICAL_STATUS_MAP" value="<%=DpConstants.DP_MEMBER_POLITICAL_STATUS_MAP%>"/>
<c:set var="DP_MEMBER_POLITICAL_STATUS_POSITIVE" value="<%=DpConstants.DP_MEMBER_POLITICAL_STATUS_POSITIVE%>"/>
<c:set var="member_needGrowTime" value="${_pMap['member_needGrowTime']=='true'}"/>
<div style="width: 900px">
<h3>${dpMember!=null?'修改党籍信息':'添加党派成员'}</h3>
<hr/>
<form class="form-horizontal" action="${ctx}dp/dpMember_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
    <div class="row">
        <div class="col-xs-7">
            <div class="form-group">
                <label class="col-xs-4 control-label"><span class="star">*</span>账号</label>
                <div class="col-xs-6">
                    <c:if test="${not empty dpMember}">
                        <input type="hidden" value="${dpMember.userId}" name="userId">
                    </c:if>
                    <select ${not empty dpMember?"disabled data-theme='default'":""} required data-rel="select2-ajax"
                                                                                   data-ajax-url="${ctx}/dp/notDpMember_selects"
                                                                                   name="userId" data-width="270"
                                                                                   data-placeholder="请输入账号或姓名或学工号">
                        <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label"><span class="star">*</span>所属民主党派</label>
                <c:if test="${empty dpParty}">
                    <div class="col-xs-6">
                        <select required data-rel="select2-ajax" data-width="270"
                                data-ajax-url="${ctx}/dp/dpParty_selects?auth=1"
                                name="partyId" data-placeholder="请选择">
                            <option></option>
                        </select>
                    </div>
                </c:if>
                <c:if test="${not empty dpParty}">
                    <div class="col-xs-6">
                        <select required data-rel="select2-ajax" data-width="270"
                                data-ajax-url="${ctx}/dp/dpParty_selects?auth=1"
                                name="partyId">
                            <option>${dpParty.name}</option>
                        </select>
                    </div>
                </c:if>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label"><span class="star">*</span>党籍状态</label>
                <div class="col-xs-6">
                    <select required data-rel="select2" name="politicalStatus" data-placeholder="请选择"  data-width="120">
                        <option></option>
                        <c:forEach items="${DP_MEMBER_POLITICAL_STATUS_MAP}" var="_status">
                            <option value="${_status.key}">${_status.value}</option>
                        </c:forEach>
                    </select>
                    <script>
                        $("#modalForm select[name=politicalStatus]").val(${dpMember.politicalStatus});
                    </script>
                </div>
            </div>

            <div class="form-group">
                <label class="col-xs-4 control-label">组织关系转入时间</label>
                <div class="col-xs-6">
                    <div class="input-group" style="width: 150px">
                        <input class="form-control date-picker" name="_transferTime" type="text"
                               data-date-format="yyyy-mm-dd"
                               value="${cm:formatDate(dpMember.transferTime,'yyyy-MM-dd')}"/>
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label">提交书面申请书时间</label>
                <div class="col-xs-6">
                    <div class="input-group" style="width: 150px">
                        <input class="form-control date-picker" name="_applyTime" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(dpMember.applyTime,'yyyy-MM-dd')}"/>
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label">确定为入党积极分子时间</label>
                <div class="col-xs-6">
                    <div class="input-group" style="width: 150px">
                        <input class="form-control date-picker" name="_activeTime" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(dpMember.activeTime,'yyyy-MM-dd')}"/>
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
                               value="${cm:formatDate(dpMember.candidateTime,'yyyy-MM-dd')}"/>
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
                </div>
            </div>
             <div class="form-group">
                <label class="col-xs-4 control-label">入党介绍人</label>
                <div class="col-xs-6">
                    <input class="form-control" style="width: 150px" type="text" name="sponsor" value="${dpMember.sponsor}"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label">${dpMember_needGrowTime?'<span class="star">*</span>':''} 入党时间</label>
                <div class="col-xs-6">
                    <div class="input-group" style="width: 150px">
                        <input ${dpMember_needGrowTime?'required':''} class="form-control date-picker" name="_growTime" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(dpMember.growTime,'yyyy-MM-dd')}"/>
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-xs-5">
            <div class="form-group">
                <label class="col-xs-3 control-label">${dpMember_needGrowTime&&dpMember.politicalStatus==DP_MEMBER_POLITICAL_STATUS_POSITIVE?'<span class="star">*</span>':''} 转正时间</label>
                <div class="col-xs-8">
                    <div ${dpMember_needGrowTime&&dpMember.politicalStatus==DP_MEMBER_POLITICAL_STATUS_POSITIVE?'required':''} class="input-group" style="width: 150px">
                        <input class="form-control date-picker" name="_positiveTime" type="text"
                               data-date-format="yyyy-mm-dd"
                               value="${cm:formatDate(dpMember.positiveTime,'yyyy-MM-dd')}"/>
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">党内职务</label>
                <div class="col-xs-8">
						<textarea class="form-control limited noEnter" type="text"  maxlength="50"
                                  name="partyPost" rows="2">${dpMember.partyPost}</textarea>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">党内奖励</label>
                <div class="col-xs-8">
						<textarea class="form-control limited noEnter" type="text" maxlength="100"
                                  name="partyReward" rows="3">${dpMember.partyReward}</textarea>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">其他奖励</label>
                <div class="col-xs-8">
						<textarea class="form-control limited noEnter" type="text" maxlength="100"
                                  name="otherReward" rows="3">${dpMember.otherReward}</textarea>
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

    <c:if test="${dpMember_needGrowTime}">
    $("#modalForm select[name=politicalStatus]").change(function(){

        var politicalStatus = $(this).val();
        if(politicalStatus=='${DP_MEMBER_POLITICAL_STATUS_POSITIVE}'){
            $("#modalForm input[name=_positiveTime]").closest(".form-group")
                .find("label").html('<span class="star">*</span> 转正时间');
             $("#modalForm input[name=_positiveTime]").attr("required", "required")
        }else{
            $("#modalForm input[name=_positiveTime]").closest(".form-group")
                .find(".star").remove();
            $("#modalForm input[name=_positiveTime]").removeAttr("required")
        }
    })
    </c:if>

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
    $.register.user_select($('[data-rel="select2-ajax"]'));

    $.register.user_select($('#modalForm select[name=userId]'));
</script>