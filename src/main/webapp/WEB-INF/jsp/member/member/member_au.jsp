<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/member/constants.jsp" %>
<c:set var="member_needGrowTime" value="${_pMap['member_needGrowTime']=='true'}"/>
<div style="width: 900px">
<h3>${empty member?'添加党员':'修改党籍信息'}</h3>
<hr/>
<form class="form-horizontal" action="${ctx}/member_au" autocomplete="off" disableautocomplete id="memberForm" method="post">
    <div class="row">
        <div class="col-xs-7">
            <div class="form-group">
                <label class="col-xs-4 control-label"><span class="star">*</span>账号
                <span class="prompt" data-title="账号说明" data-width="400"
							  data-prompt="<ul>
							  <li>此处显示的是还未加入党员库中的账号，如果账号已经加入了党员库中则无须在此重复添加</li>
							  <li>教职工账号将自动归入“在职教职工党员库”，学生账号将自动归入“学生党员库”</li>
							  <li>如需添加离退休党员，请先添加教职工账号，然后在“在职教职工党员库”中，使用“修改基础信息”功能，将其修改为已退休状态后，该账号自动归入“离退休党员库”</li>
							  </ul>"><i class="fa fa-question-circle-o"></i></span>
                </label>
                <div class="col-xs-6">
                    <c:if test="${not empty member}">
                        <input type="hidden" value="${member.userId}" name="userId">
                    </c:if>
                    <select ${not empty member?"disabled data-theme='default'":""} required data-rel="select2-ajax"
                                                                                   data-ajax-url="${ctx}/notMember_selects"
                                                                                   name="userId" data-width="270"
                                                                                   data-placeholder="请输入账号或姓名或学工号">
                        <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                    </select>
                    <span id="userTypeSpan" class="help-block"></span>
                    <span class="help-block">
                         <a href="javascript:;" class="popupBtn" data-url="${ctx}/member/search">找不到？可能已在党员库中，点此查询</a>
                    </span>
                    <script>
                        var userId = $.trim($('#memberForm select[name=userId]').val());
                        if (userId == null || userId == undefined || userId == "") {
                            $('#userTypeSpan').text("");
                        }else {
                            $('#userTypeSpan').text('账号类别：${USER_TYPE_MAP.get(sysUser.type)}');
                        }
                    </script>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label"><span class="star">*</span>所属${_p_partyName}</label>
                <div class="col-xs-6">
                    <select required class="form-control" data-rel="select2-ajax"
                            data-ajax-url="${ctx}/party_selects?auth=1"
                            name="partyId" data-placeholder="请选择" data-width="270">
                        <option value="${party.id}">${party.name}</option>
                    </select>
                </div>
            </div>
            <div class="form-group" style="${(empty branch)?'display: none':''}" id="branchDiv">
                <label class="col-xs-4 control-label"><span class="star">*</span>所属党支部</label>
                <div class="col-xs-6">
                    <select class="form-control" data-rel="select2-ajax" data-ajax-url="${ctx}/branch_selects?auth=1"
                            name="branchId" data-placeholder="请选择" data-width="270">
                        <option value="${branch.id}">${branch.name}</option>
                    </select>
                </div>
            </div>
            <script>
                $.register.party_branch_select($("#memberForm"), "branchDiv",
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
                            $("#memberForm select[name=politicalStatus]").val(${member.politicalStatus});
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
                    <span class="help-block">注：本校发展党员请留空</span>
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
                <label class="col-xs-4 control-label">入党介绍人</label>
                <div class="col-xs-6">
                    <input class="form-control" style="width: 150px" type="text" name="sponsor" value="${member.sponsor}"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label">${member_needGrowTime?'<span class="star">*</span>':''} 入党时间</label>
                <div class="col-xs-6">
                    <div class="input-group" style="width: 150px">
                        <input ${member_needGrowTime?'required':''} class="form-control date-picker" name="_growTime" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(member.growTime,'yyyy-MM-dd')}"/>
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label">入党时所在党支部</label>
                <div class="col-xs-6">
                    <textarea class="form-control limited noEnter" type="text" maxlength="100"
                                  name="growBranch" rows="3">${member.growBranch}</textarea>
                </div>
            </div>

        </div>
        <div class="col-xs-5">
            <c:if test="${empty member.politicalStatus || member.politicalStatus==MEMBER_POLITICAL_STATUS_POSITIVE}">
            <div class="form-group">
                <label class="col-xs-3 control-label">${member_needGrowTime&&member.politicalStatus==MEMBER_POLITICAL_STATUS_POSITIVE?'<span class="star">*</span>':''} 转正时间</label>
                <div class="col-xs-8">
                    <div class="input-group" style="width: 150px">
                        <input ${member_needGrowTime&&member.politicalStatus==MEMBER_POLITICAL_STATUS_POSITIVE?'required':''} class="form-control date-picker" name="_positiveTime" type="text"
                               data-date-format="yyyy-mm-dd"
                               value="${cm:formatDate(member.positiveTime,'yyyy-MM-dd')}"/>
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">转正时所在党支部</label>
                <div class="col-xs-8">
                    <textarea class="form-control limited noEnter" type="text"  maxlength="100"
                                  name="positiveBranch">${member.positiveBranch}</textarea>
                </div>
            </div>
            </c:if>
            <div class="form-group">
                <label class="col-xs-3 control-label">党内职务</label>
                <div class="col-xs-8">
						<textarea class="form-control limited noEnter" type="text"  maxlength="50"
                                  name="partyPost">${member.partyPost}</textarea>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">党内奖励</label>
                <div class="col-xs-8">
						<textarea class="form-control limited noEnter" type="text" maxlength="100"
                                  name="partyReward">${member.partyReward}</textarea>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">其他奖励</label>
                <div class="col-xs-8">
						<textarea class="form-control limited noEnter" type="text" maxlength="100"
                                  name="otherReward">${member.otherReward}</textarea>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">备注1</label>
                <div class="col-xs-8">
                    <input class="form-control" type="text"  maxlength="100"
                                  name="remark1" value="${member.remark1}"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">备注2</label>
                <div class="col-xs-8">
                    <input class="form-control" type="text"  maxlength="100"
                                  name="remark2" value="${member.remark2}"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">备注3</label>
                <div class="col-xs-8">
                    <input class="form-control" type="text"  maxlength="100"
                                  name="remark3" value="${member.remark3}"/>
                </div>
            </div>
            <%--<div class="form-group">
                <label class="col-xs-3 control-label">备注4</label>
                <div class="col-xs-8">
                    <input class="form-control" type="text"  maxlength="100"
                                  name="remark4" value="${member.remark4}"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">备注5</label>
                <div class="col-xs-8">
                    <input class="form-control" type="text"  maxlength="100"
                                  name="remark5" value="${member.remark5}"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">备注6</label>
                <div class="col-xs-8">
                    <input class="form-control" type="text"  maxlength="100"
                                  name="remark6" value="${member.remark6}"/>
                </div>
            </div>--%>
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
                        $("#memberForm select[name=addType]").val('${member.addType}');
                    </script>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">添加/<br/>更新原因</label>
                <div class="col-xs-8">
						<textarea class="form-control limited noEnter" type="text"
                                  name="reason"></textarea>
                </div>
            </div>
        </div>
    </div>
</form>
<div class="clearfix form-actions center">
        <button class="btn btn-info" type="submit"
                data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口">
            <i class="ace-icon fa fa-check bigger-110"></i>
            提交
        </button>

        &nbsp; &nbsp; &nbsp;
        <button class="hideView btn btn-default" type="button">
            <i class="ace-icon fa fa-reply bigger-110"></i>
            返回
        </button>
</div>
</div>
<script>
    $('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'), {endDate: '${_today}'});

    <c:if test="${member_needGrowTime}">
    $("#memberForm select[name=politicalStatus]").change(function(){

        var politicalStatus = $(this).val();
        if(politicalStatus=='${MEMBER_POLITICAL_STATUS_POSITIVE}'){
            $("#memberForm input[name=_positiveTime]").closest(".form-group")
                .find("label").html('<span class="star">*</span> 转正时间');
             $("#memberForm input[name=_positiveTime]").attr("required", "required")
        }else{
            $("#memberForm input[name=_positiveTime]").closest(".form-group")
                .find(".star").remove();
            $("#memberForm input[name=_positiveTime]").removeAttr("required")
        }
    })
    </c:if>

    $("#body-content-view button[type=submit]").click(function () {
        $("#memberForm").submit();
        return false;
    });
    $("#memberForm").validate({
        submitHandler: function (form) {
            var $btn = $("#body-content-view button[type=submit]").button('loading');
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        //SysMsg.success('提交成功。', '成功',function(){
                        $("#jqGrid").trigger("reloadGrid");
                        $.hideView();
                        //});
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    $('#memberForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();

    var $select = $.register.user_select($('#memberForm select[name=userId]'));
    $select.on("change",function(){
        //console.log($(this).select2("data")[0].type)
        var $this = $(this).val();
        if ($.trim($this)=="") {
            $('#userTypeSpan').text("");
        } else {
            $('#userTypeSpan').text("账号类别：" + $(this).select2("data")[0].type);
        }
    });
</script>