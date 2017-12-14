<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>计算应缴党费额度</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" id="modalForm" method="post">
        <input name="isPreview" type="hidden" value="1">
        <table class="table table-bordered table-unhover">
            <c:if test="${pmdNorm.formulaType==PMD_FORMULA_TYPE_ONJOB}">
            <tr>
                <td>岗位工资</td>
                <td>
                    <input required class="number" data-rule-min="0" maxlength="10"
                           type="text" name="gwgz" value="${cm:stripTrailingZeros(pmdConfigMember.gwgz)}">
                </td>
                <td>薪级工资</td>
                <td>
                    <input required class="number" data-rule-min="0" maxlength="10"
                           type="text" name="xjgz" value="${cm:stripTrailingZeros(pmdConfigMember.xjgz)}">
                </td>
            </tr>
            <tr>
                <td>岗位津贴</td>
                <td>
                    <input required class="number" data-rule-min="0" maxlength="10"
                           type="text" name="gwjt" value="${cm:stripTrailingZeros(pmdConfigMember.gwjt)}">
                </td>
                <td>职务补贴</td>
                <td>
                    <input required class="number" data-rule-min="0" maxlength="10"
                           type="text" name="zwbt" value="${cm:stripTrailingZeros(pmdConfigMember.zwbt)}">
                </td>
            </tr>
            <tr>
                <td>职务补贴1</td>
                <td>
                    <input required class="number" data-rule-min="0" maxlength="10"
                           type="text" name="zwbt1" value="${cm:stripTrailingZeros(pmdConfigMember.zwbt1)}">
                </td>
                <td>生活补贴</td>
                <td>
                    <input required class="number" data-rule-min="0" maxlength="10"
                           type="text" name="shbt" value="${cm:stripTrailingZeros(pmdConfigMember.shbt)}">
                </td>
            </tr>
            <tr>
                <td>书报费</td>
                <td>
                    <input required class="number" data-rule-min="0" maxlength="10"
                           type="text" name="sbf" value="${cm:stripTrailingZeros(pmdConfigMember.sbf)}">
                </td>
                <td>洗理费</td>
                <td>
                    <input required class="number" data-rule-min="0" maxlength="10"
                           type="text" name="xlf" value="${cm:stripTrailingZeros(pmdConfigMember.xlf)}">
                </td>
            </tr>
            <tr>
                <td>工资冲销</td>
                <td colspan="3">
                    <input required class="number" data-rule-min="0" maxlength="10"
                           type="text" name="gzcx" value="${cm:stripTrailingZeros(pmdConfigMember.gzcx)}">
                    <span style="font-size: 8px;">工资明细中工资冲销的负号“-”不要录入。</span>
                </td>
            </tr>
            <tr>
                <td>扣个人失业保险</td>
                <td>
                    <input required class="number" data-rule-min="0" maxlength="10"
                           type="text" name="shiyebx" value="${cm:stripTrailingZeros(pmdConfigMember.shiyebx)}">
                </td>
                <td>扣个人养老保险</td>
                <td>
                    <input required class="number" data-rule-min="0" maxlength="10"
                           type="text" name="yanglaobx" value="${cm:stripTrailingZeros(pmdConfigMember.yanglaobx)}">
                </td>
            </tr>
            <tr>
                <td>扣个人职业年金</td>
                <td>
                    <input required class="number" data-rule-min="0" maxlength="10"
                           type="text" name="zynj" value="${cm:stripTrailingZeros(pmdConfigMember.zynj)}">
                </td>
                <td>扣住房公积金</td>
                <td>
                    <input required class="number" data-rule-min="0" maxlength="10"
                           type="text" name="gjj" value="${cm:stripTrailingZeros(pmdConfigMember.gjj)}">
                </td>
            </tr>
            </c:if>
            <c:if test="${pmdNorm.formulaType==PMD_FORMULA_TYPE_EXTERNAL}">
            <tr>
                <td>校聘工资</td>
                <td>
                    <input required class="number" data-rule-min="0" maxlength="10"
                           type="text" name="gwgz" value="${cm:stripTrailingZeros(pmdConfigMember.gwgz)}">
                </td>
                <td>岗位津贴</td>
                <td>
                    <input required class="number" data-rule-min="0" maxlength="10"
                           type="text" name="gwjt" value="${cm:stripTrailingZeros(pmdConfigMember.gwjt)}">
                </td>
            </tr>
            <tr>
                <td>扣个人失业保险</td>
                <td>
                    <input required class="number" data-rule-min="0" maxlength="10"
                           type="text" name="shiyebx" value="${cm:stripTrailingZeros(pmdConfigMember.shiyebx)}">
                </td>
                <td>扣个人养老保险</td>
                <td>
                    <input required class="number" data-rule-min="0" maxlength="10"
                           type="text" name="yanglaobx" value="${cm:stripTrailingZeros(pmdConfigMember.yanglaobx)}">
                </td>
            </tr>
            <tr>
                <td>扣个人医疗保险</td>
                <td>
                    <input required class="number" data-rule-min="0" maxlength="10"
                           type="text" name="yiliaobx" value="${cm:stripTrailingZeros(pmdConfigMember.yiliaobx)}">
                </td>
                <td>扣住房公积金</td>
                <td>
                    <input required class="number" data-rule-min="0" maxlength="10"
                           type="text" name="gjj" value="${cm:stripTrailingZeros(pmdConfigMember.gjj)}">
                </td>
            </tr>
            </c:if>
            <tr class="due-pay">
                <td>应缴纳党费额度</td>
                <td colspan="2" style="text-align: center">
                    <span id="duePaySpan">${duePay}</span>
                </td>
                <td align="center">
                    <button id="previewBtn" class="btn btn-success btn-sm btn-block">
                        <i class="fa fa-rmb"></i> 计&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;算</button>
                </td>
            </tr>
        </table>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>

    <input id="submitBtn" type="button" class="btn btn-primary"
           value="提交"/>
</div>
<style>
    input[type=text] {
        width: 120px;
        padding: 2px;
    }
    .due-pay{
        font-size: 18px;
        font-weight: bolder;
    }
</style>
<script>

    $("#previewBtn").click(function () {
        $("#duePaySpan").html('');
        $("input[name=isPreview]", "#modalForm").val(1);
        $("#modalForm").submit();
        return false;
    });
    $("#submitBtn").click(function () {
        $("input[name=isPreview]", "#modalForm").val(0);
        $("#modalForm").submit();
        return false;
    });
    $("#modalForm").validate({
        submitHandler: function (form) {
            var isPreview = $("input[name=isPreview]", "#modalForm").val();
            var url = "${ctx}/user/pmd/pmdMember_setSalary";
            if (isPreview == 1) {
                url = "${ctx}/user/pmd/pmdMember_calDuePay";
            }
            $(form).ajaxSubmit({
                url: url,
                success: function (ret) {
                    if (ret.success) {
                        if (isPreview == 1) {
                            $("#duePaySpan").html(ret.duePay);
                        } else {
                            $("#modal").modal('hide');
                            $("#jqGrid").trigger("reloadGrid");
                        }
                    }
                }
            });
        }
    });
    $("#modalForm :checkbox").bootstrapSwitch();
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>