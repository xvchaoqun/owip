<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/pmd/constants.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${param.confirm==1?'确认缴费额度':'选择党员类别'}</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/pmd/pmdMember_selectMemberType" id="modalForm" method="post">
        <input type="hidden" name="ids[]" value="${param['ids[]']}">
        <input type="hidden" name="configMemberType" value="${param.configMemberType}">
        <input type="hidden" name="confirm" value="${param.confirm}">
        <c:set var="num" value='${fn:length(fn:split(param["ids[]"],","))}'/>
        <c:if test="${num==1}">
            <div class="form-group">
                <label class="col-xs-4 control-label">姓名</label>

                <div class="col-xs-6 label-text">
                        ${pmdMember.user.realname}
                </div>
            </div>
        </c:if>
        <c:if test="${num>1}">
            <div class="form-group">
                <label class="col-xs-4 control-label">已选择党员数量</label>

                <div class="col-xs-6 label-text">
                        ${num}人
                </div>
            </div>
        </c:if>
        <div class="form-group">
            <label class="col-xs-4 control-label">党员类别</label>

            <div class="col-xs-6 label-text">
                ${PMD_MEMBER_TYPE_MAP.get(configMemberType)}
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">党员分类别</label>

            <div class="col-xs-6">
                <c:if test="${param.confirm!=1}">
                    <select required data-rel="select2" name="configMemberTypeId"
                            data-width="270"
                            data-placeholder="请选择">
                        <option></option>
                        <c:forEach items="${configMemberTypes}" var="_type">
                            <option value="${_type.id}"
                                    data-amount="${_type.pmdNorm.pmdNormValue.amount}"
                                    data-set-type="${_type.pmdNorm.setType}">${_type.name}</option>
                        </c:forEach>
                    </select>
                </c:if>
                <c:if test="${param.confirm==1}">
                    ${pmdConfigMemberType.name}
                    <input type="hidden" name="configMemberTypeId" value="${pmdConfigMemberType.id}">
                </c:if>
            </div>
        </div>
        <c:if test="${param.configMemberType==PMD_MEMBER_TYPE_STUDENT}">
            <div class="form-group">
                <label class="col-xs-4 control-label">是否带薪就读</label>

                <div class="col-xs-6">
                    <select required data-rel="select2" name="hasSalary"
                            data-width="270"
                            data-placeholder="请选择">
                        <option></option>
                        <option value="0">不带薪就读</option>
                        <option value="1">带薪就读</option>
                    </select>
                    <c:if test="${not empty pmdMember.hasSalary}">
                        <script>
                            $("#modalForm select[name=hasSalary]").val(${pmdMember.hasSalary?1:0});
                        </script>
                    </c:if>
                </div>
            </div>
        </c:if>
        <div class="form-group">
            <label class="col-xs-4 control-label">额度（单位：人民币）</label>

            <div class="col-xs-6">
                <input class="form-control float"
                       type="text" name="amount" value="${param.confirm==1?(cm:stripTrailingZeros(pmdConfigMember.duePay)):''}"
                       data-rule-min="0.01" maxlength="10"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">备注</label>

            <div class="col-xs-6">
            <textarea class="form-control limited" type="text"
                      name="remark" maxlength="100"></textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="确定"/>
</div>

<script>
    $("select[name=configMemberTypeId]", "#modalForm").change(function () {
        var $selected = $(this).find(':selected');
        var setType = $selected.data("set-type");
        var amount = $selected.data("amount");
        //console.log(setType)

        if (setType == "${PMD_NORM_SET_TYPE_SET}") {
            $("input[name=amount]", "#modalForm").val('')
                    .prop("disabled", false)
                    .attr("required", "required");
        } else {
            $("input[name=amount]", "#modalForm").val(amount)
                    .prop("disabled", true).removeAttr("required");
        }
    })
    $("#modalForm select[name=configMemberTypeId]").val('${pmdMember.configMemberTypeId}').trigger("change");

    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $("#modal").modal('hide');
                        <c:if test="${param.auth==1}">
                        $("#jqGrid").trigger("reloadGrid");
                        </c:if>
                        <c:if test="${param.auth!=1}">
                        $("#jqGrid2").trigger("reloadGrid");
                        </c:if>
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('textarea.limited').inputlimiter();
</script>