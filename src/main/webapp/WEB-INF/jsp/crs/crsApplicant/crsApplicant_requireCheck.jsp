<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>资格审核（岗位：${crsApplicant.post.name}， 报名人：${crsApplicant.user.realname}-${crsApplicant.user.code}）</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/crsApplicant_requireCheck" id="modalForm" method="post">
        <input type="hidden" name="id" value="${param.applicantId}">

        <div class="col-xs-12" style="padding-bottom: 15px;">
            <table id="checkTable" class="table table-unhover2 table-bordered table-striped">
                <thead>
                <tr>
                    <th width="50"></th>
                    <th width="250">项目</th>
                    <th width="250">岗位要求</th>
                    <th width="250">实际情况</th>
                    <th width="150">审核</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${crsPostRequire.rules}" var="rule" varStatus="vs">
                    <c:forEach items="${rule.ruleItems}" var="item" varStatus="vs2">
                        <tr>
                            <c:if test="${vs2.first}">
                                <td rowspan="${fn:length(rule.ruleItems)}" class="idx">
                                        ${vs.count}
                                    <c:if test="${fn:length(rule.ruleItems)>1}">
                                        <div class="or">（或）</div>
                                    </c:if>
                                </td>
                            </c:if>
                            <td>
                                    ${CRS_POST_RULE_TYPE_MAP.get(item.type)}
                            </td>
                            <td>
                                    ${item.val}
                            </td>
                            <td>
                                    ${realValMap.get(item.type)}
                            </td>
                            <c:if test="${vs2.first}">
                                <c:set var="_checkBean" value="${checkMap.get(rule.id)}"/>
                                <td rowspan="${fn:length(rule.ruleItems)}" class="op" data-rule-id="${rule.id}">
                                    <a href="javascript:;" data-status="1"
                                            class="btn btn-xs agree ${(_checkBean!=null && _checkBean.pass)?'disabled':''}">
                                        <i class="fa fa-check-circle"></i> 符合
                                    </a>
                                    <a href="javascript:;" data-status="0"
                                            class="btn btn-xs disagree ${(_checkBean!=null && !_checkBean.pass)?'disabled':''}">
                                        <i class="fa fa-times-circle"></i> 不符合
                                    </a>
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="form-group">
            <label class="col-xs-3 control-label">审核结果</label>

            <div class="col-xs-6 label-text" style="font-size: 15px;">
                <input type="checkbox" class="big" checked name="status" data-off-text="不通过" data-on-text="通过"/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-xs-3 control-label">审核说明</label>

            <div class="col-xs-6">
                <textarea class="form-control limited" rows="6" name="remark">${crsApplicant.requireCheckRemark}</textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="确定"/>
</div>
<style>
    #checkTable .idx, #checkTable .op {
        vertical-align: middle;
        text-align: center;
    }

    #checkTable .op a {
        color: darkgrey;
    }

    #checkTable .op a.disabled {
        font-weight: bolder;
        opacity: 1;
    }

    #checkTable .op a.agree.disabled {
        color: darkgreen;
    }

    #checkTable .op a.disagree.disabled {
        color: darkred;
    }

    #checkTable thead th {
        text-align: center;
    }

    #checkTable .or {
        font-weight: bolder;
        color: darkgreen;
    }
</style>
<script>

    $("#checkTable .op .agree, #checkTable .op .disagree").click(function () {
        var $this = $(this);
        var ruleId = $this.closest("td").data("rule-id");
        var status = $this.data("status");
        $.post("${ctx}/crsApplicant_requireCheckItem", {
            applicantId: "${crsApplicant.id}",
            ruleId: ruleId, status: status
        }, function (ret) {
            if (ret.success) {
                $this.closest("td").find(".disabled").removeClass("disabled");
                $this.addClass("disabled");
            }
        });
    });

    $("#modal :checkbox").bootstrapSwitch();
    $("#modalForm").validate({
        submitHandler: function (form) {

            var ruleCount = $("#checkTable .op").length;
            var checkedRuleCount = $("#checkTable .op .disabled").length;
            var passRuleCount = $("#checkTable .op .agree.disabled").length;
            //console.log("ruleCount="+ruleCount +" checkedRuleCount="+checkedRuleCount +" passRuleCount="+passRuleCount);
            if(ruleCount != checkedRuleCount){
                $.tip({$target:$("#checkTable .op:not(:has(.disabled))"), msg:"请对该项进行审核"})
                return;
            }

            if(ruleCount != passRuleCount){
                var $remark = $("textarea[name=remark]", form);
                //console.log("$remark=" + $remark.val());
                if($.trim($remark.val())=="") {
                    $remark.focus();
                    $.tip({$target:$remark, msg: "请填写审核说明"});
                    return;
                }
            }

            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $("#modal").modal('hide');
                        _stepReload()
                    }
                }
            });
        }
    });
</script>