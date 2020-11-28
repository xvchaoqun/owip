<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="OW_APPLY_STAGE_DENY" value="<%=OwConstants.OW_APPLY_STAGE_DENY%>"/>
<c:set var="OW_APPLY_STAGE_DENY" value="<%=OwConstants.OW_APPLY_STAGE_DENY%>"/>
<c:set var="OW_APPLY_CONTINUE_MAP" value="<%=OwConstants.OW_APPLY_CONTINUE_MAP%>"/>
<c:set var="OW_APPLY_STAGE_ACTIVE" value="<%=OwConstants.OW_APPLY_STAGE_ACTIVE%>"/>
<c:set var="OW_APPLY_STAGE_CANDIDATE" value="<%=OwConstants.OW_APPLY_STAGE_CANDIDATE%>"/>
<c:set var="OW_APPLY_STAGE_PLAN" value="<%=OwConstants.OW_APPLY_STAGE_PLAN%>"/>
<c:set var="OW_APPLY_STAGE_DRAW" value="<%=OwConstants.OW_APPLY_STAGE_DRAW%>"/>
<c:if test="${memberApply.stage==OW_APPLY_STAGE_DENY}">
    <div class="alert alert-danger">
        <button type="button" class="close" data-dismiss="alert">
            <i class="ace-icon fa fa-times"></i>
        </button>
        <strong><i class="ace-icon fa fa-times"></i>返回修改</strong><c:if
            test="${not empty memberApply.reason}">: ${memberApply.reason}</c:if>
        <br>
    </div>
</c:if>
<div class="page-header">
      <h1>
        申请入党
      </h1>
    </div>
<form class="form-horizontal" autocomplete="off" disableautocomplete id="modalForm" method="post"
      action="${ctx}/m/memberApply">
    <c:if test="${_pMap['memberApply_needContinueDevelop']=='true'}">
        <div class="form-group">
            <label class="col-sm-3 control-label no-padding-right">申请类型</label>
            <div class="col-sm-9">
                <div class="input-group">
                    <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                        <input required checked type="radio" name="applyType" id="type1" value="1">
                        <label for="type1">申请入党</label>
                    </div>
                    <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                        <input required type="radio" name="applyType" id="type2" value="2">
                        <label for="type2">申请继续培养</label>
                    </div>
                </div>
            </div>
        </div>
    </c:if>
    <div class="form-group">
        <label class="col-xs-5 control-label"> ${(_user.type==USER_TYPE_JZG)?"工作证号":"学号"}</label>
        <div class="col-xs-7 label-text">
            ${_user.code}
        </div>
    </div>

    <c:if test="${_pMap['memberApply_needContinueDevelop']=='true'}">
        <div class="form-group" hidden id="appiyStageDiv">
            <label class="col-xs-5 control-label no-padding-right"><span class="star">*</span>请选择培养阶段</label>
            <div class="col-xs-7">
                <select name="applyStage" data-rel="select2" data-placeholder="请选择" data-width="150">
                    <option></option>
                    <c:forEach items="${OW_APPLY_CONTINUE_MAP}" var="appiyStage">
                        <option value="${appiyStage.key}">${appiyStage.value}</option>
                    </c:forEach>
                </select>
                <script>
                    $("#modalForm select[name=applyStage]").val("${memberApply.applyStage}")
                </script>
            </div>
        </div>
    </c:if>

    <div class="form-group">
        <label class="col-xs-5 control-label no-padding-right"><span class="star">*</span>提交书面申请书时间</label>
        <div class="col-xs-7">
            <div class="input-group" style="width: 150px">
                <input required class="form-control date-picker" name="_applyTime" type="text"
                       data-date-format="yyyy-mm-dd"
                       data-date-end-date="${_today}"
                       value="${cm:formatDate(memberApply.applyTime,'yyyy-MM-dd')}"/>
                <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
            </div>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right"><span class="star">*</span>请选择组织机构</label>
        <div class="col-sm-9">
            <select required name="classId" data-rel="select2" data-placeholder="请选择" data-width="100%">
                <option></option>
                <c:import url="/metaTypes?__code=mc_party_class"/>
            </select>
            <script>
                $("#modalForm select[name=classId]").val("${party.classId}")
            </script>
        </div>
    </div>
    <div class="form-group" id="party" style="${empty party?'display: none;':''}">
        <div class="col-sm-offset-3 col-sm-9">
            <select data-rel="select2-ajax" data-width="100%" data-ajax-url="${ctx}/m/party_selects?del=0"
                    name="partyId" data-placeholder="请选择${_p_partyName}">
                <option value="${party.id}">${party.name}</option>
            </select>
        </div>
    </div>
    <div class="form-group" id="branch" style="${empty branch?'display: none;':''}">
        <div class="col-sm-offset-3 col-sm-9">
            <select data-rel="select2-ajax" data-width="100%" data-ajax-url="${ctx}/m/branch_selects?del=0"
                    name="branchId" data-placeholder="请选择党支部">
                <option value="${branch.id}">${branch.name}</option>
            </select>
        </div>
    </div>

    <div id="requiresDiv">

    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right"> 备注</label>
        <div class="col-sm-9">
            <textarea name="remark" class="form-control" rows="2">${memberApply.remark}</textarea>
        </div>
    </div>

    <div class="clearfix form-actions center">
        <button class="btn btn-info" type="button" id="submitBtn" data-loading-text="提交中..."
                data-success-text="您的申请已提交成功" autocomplete="off">
            <i class="ace-icon fa fa-check bigger-110"></i>
            提交
        </button>
        &nbsp; &nbsp; &nbsp;
          <button class="hideView btn btn-default" type="button">
            <i class="ace-icon fa fa-undo bigger-110"></i>
            返回
          </button>
    </div>
</form>

<script type="text/template" id="contentDiv_tpl">

    {{if($.inArray('${OW_APPLY_STAGE_ACTIVE}', codes)>=0){}}
    <div class="form-group">
        <label class="col-xs-5 control-label no-padding-right">确定为入党积极分子时间</label>
        <div class="col-xs-7">
            <div class="input-group" style="width: 150px">
                <input class="form-control date-picker" name="activeTime" type="text"
                       data-date-format="yyyy.mm.dd"
                       value="${cm:formatDate(memberApply.activeTime,'yyyy.MM.dd')}"/>
                <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
            </div>
        </div>
    </div>
    <c:if test="${_pMap['memberApply_needActiveTrain']=='true'}">
        <div class="form-group">
            <label class="col-xs-5 control-label no-padding-right">积极分子参加培训时间</label>

            <div class="col-xs-7">
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
            <label class="col-xs-5 control-label no-padding-right">积极分子结业考试成绩</label>
            <div class="col-xs-7">
                <input style="width: 150px" class="form-control" type="text" name="activeGrade"
                       value="${memberApply.activeGrade}">
            </div>
        </div>
    </c:if>
    {{}}}

    {{if($.inArray('${OW_APPLY_STAGE_CANDIDATE}', codes)>=0){}}
    <div class="form-group">
        <label class="col-xs-5 control-label no-padding-right">确定为发展对象时间</label>

        <div class="col-xs-7">
            <div class="input-group" style="width: 150px">
                <input class="form-control date-picker" name="candidateTime" type="text"
                       data-date-format="yyyy.mm.dd"
                       value="${cm:formatDate(memberApply.candidateTime,'yyyy.MM.dd')}"/>
                <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
            </div>
        </div>
    </div>
    <div class="form-group">
        <label class="col-xs-5 control-label no-padding-right">发展对象参加培训时间</label>

        <div class="col-xs-7">
            <div class="input-group" style="width: 150px">
                <input class="form-control date-picker" name="candidateTrainStartTime" type="text"
                       data-date-format="yyyy.mm.dd"
                       value="${cm:formatDate(memberApply.candidateTrainStartTime,'yyyy.MM.dd')}"/>
                <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
            </div>
            <c:if test="${_pMap['memberApply_needCandidateTrain']=='true'}">
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
    <c:if test="${_pMap['memberApply_needCandidateTrain']=='true'}">
        <div class="form-group">
            <label class="col-xs-5 control-label no-padding-right">发展对象结业考试成绩</label>
            <div class="col-xs-7">
                <input style="width: 150px" class="form-control" type="text" name="candidateGrade"
                       value="${memberApply.candidateGrade}">
            </div>
        </div>
    </c:if>
    {{}}}

    {{if($.inArray('${OW_APPLY_STAGE_PLAN}', codes)>=0){}}
    <div class="form-group">
        <label class="col-xs-5 control-label no-padding-right">列入发展计划时间</label>

        <div class="col-xs-7">
            <div class="input-group" style="width: 150px">
                <input class="form-control date-picker" name="planTime" type="text"
                       data-date-format="yyyy.mm.dd"
                       value="${cm:formatDate(memberApply.planTime,'yyyy.MM.dd')}"/>
                <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
            </div>
        </div>
    </div>
    {{}}}

    {{if($.inArray('${OW_APPLY_STAGE_DRAW}', codes)>=0){}}
    <div class="form-group">
        <label class="col-xs-5 control-label no-padding-right">领取志愿书时间</label>

        <div class="col-xs-7">
            <div class="input-group" style="width: 150px">
                <input class="form-control date-picker" name="drawTime" type="text"
                       data-date-format="yyyy.mm.dd"
                       value="${cm:formatDate(memberApply.drawTime,'yyyy.MM.dd')}"/>
                <span class="input-group-addon"> <i
                        class="fa fa-calendar bigger-110"></i></span>
            </div>
        </div>
    </div>
    {{}}}
</script>

<script>
    $('#modalForm [data-rel="select2"]').select2();

    $('#modalForm input[name=applyType]').on('change', function () {
        $("#appiyStageDiv").hide();
        $("#modalForm select[name=appiyStage]").removeAttr("required", "required");
        var type = $(this).val();
        if (type == 2) {
            $("#modalForm select[name=appiyStage]").attr("required", "required");
            $("#appiyStageDiv").show();
        }
    });

    var applyMap = ${cm:toJSONObject(OW_APPLY_CONTINUE_MAP)};

    $("#modalForm select[name=applyStage]").on('change', function () {
        var codes = [];

        for (var key in applyMap) {
            codes.push(key);
            if (key == $(this).val()) break;
        }
        $("#requiresDiv").html(_.template($("#contentDiv_tpl").html())({codes: codes}));

        console.log(codes);

        $.register.date($('.date-picker'));
    });

    $.register.class_party_branch_select($("#modalForm"), "party", "branch",
        '${cm:getMetaTypeByCode("mt_direct_branch").id}', '${party.id}');
    $.register.date($('.date-picker'));
    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            if (!$("#party").is(":hidden")) {
                if ($('select[name=partyId]').val() == '') {
                    SysMsg.success("请选择${_p_partyName}。");
                    return;
                }
            }
            if (!$("#branch").is(":hidden")) {
                if (!($('select[name=branchId]').val() > 0)) {
                    SysMsg.success("请选择支部。");
                    return;
                }
            }
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        SysMsg.success("提交成功。", function () {
                            $btn.button("success").addClass("btn-success");
                            location.reload();
                        });
                    } else {
                        $btn.button('reset');
                    }
                }
            });
        }
    });
</script>
