<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/pmd/constants.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${param.view==1?'查看':'修改'}党费应交额</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" id="modalForm" method="post">
        <input name="isPreview" type="hidden" value="1">
        <input name="pmdMemberId" type="hidden" value="${param.pmdMemberId}">
        <input name="isSelf" type="hidden" value="${param.isSelf}">
        <table class="table table-bordered table-unhover">
            <c:if test="${pmdNorm.formulaType==PMD_FORMULA_TYPE_ONJOB}">
                <tr>
                    <td>岗位工资</td>
                    <td>
                        <c:if test="${param.view==1}">
                            ${cm:stripTrailingZeros(pmdConfigMember.gwgz)}
                        </c:if>
                        <c:if test="${param.view!=1}">
                            <input required class="number" data-rule-min="0" maxlength="10"
                                   type="text" name="gwgz" value="${cm:stripTrailingZeros(pmdConfigMember.gwgz)}">
                        </c:if>
                    </td>
                    <td>薪级工资</td>
                    <td>
                        <c:if test="${param.view==1}">
                            ${cm:stripTrailingZeros(pmdConfigMember.xjgz)}
                        </c:if>
                        <c:if test="${param.view!=1}">
                            <input required class="number" data-rule-min="0" maxlength="10"
                                   type="text" name="xjgz" value="${cm:stripTrailingZeros(pmdConfigMember.xjgz)}">
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td>岗位津贴</td>
                    <td>
                        <c:if test="${param.view==1}">
                            ${cm:stripTrailingZeros(pmdConfigMember.gwjt)}
                        </c:if>
                        <c:if test="${param.view!=1}">
                            <input required class="number" data-rule-min="0" maxlength="10"
                                   type="text" name="gwjt" value="${cm:stripTrailingZeros(pmdConfigMember.gwjt)}">
                        </c:if>
                    </td>
                    <td>职务补贴</td>
                    <td>
                        <c:if test="${param.view==1}">
                            ${cm:stripTrailingZeros(pmdConfigMember.zwbt)}
                        </c:if>
                        <c:if test="${param.view!=1}">
                            <input required class="number" data-rule-min="0" maxlength="10"
                                   type="text" name="zwbt" value="${cm:stripTrailingZeros(pmdConfigMember.zwbt)}">
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td>职务补贴1</td>
                    <td>
                        <c:if test="${param.view==1}">
                            ${cm:stripTrailingZeros(pmdConfigMember.zwbt1)}
                        </c:if>
                        <c:if test="${param.view!=1}">
                            <input required class="number" data-rule-min="0" maxlength="10"
                                   type="text" name="zwbt1" value="${cm:stripTrailingZeros(pmdConfigMember.zwbt1)}">
                        </c:if>
                    </td>
                    <td>生活补贴</td>
                    <td>
                        <c:if test="${param.view==1}">
                            ${cm:stripTrailingZeros(pmdConfigMember.shbt)}
                        </c:if>
                        <c:if test="${param.view!=1}">
                            <input required class="number" data-rule-min="0" maxlength="10"
                                   type="text" name="shbt" value="${cm:stripTrailingZeros(pmdConfigMember.shbt)}">
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td>书报费</td>
                    <td>
                        <c:if test="${param.view==1}">
                            ${cm:stripTrailingZeros(pmdConfigMember.sbf)}
                        </c:if>
                        <c:if test="${param.view!=1}">
                            <input required class="number" data-rule-min="0" maxlength="10"
                                   type="text" name="sbf" value="${cm:stripTrailingZeros(pmdConfigMember.sbf)}">
                        </c:if>
                    </td>
                    <td>洗理费</td>
                    <td>
                        <c:if test="${param.view==1}">
                            ${cm:stripTrailingZeros(pmdConfigMember.xlf)}
                        </c:if>
                        <c:if test="${param.view!=1}">
                            <input required class="number" data-rule-min="0" maxlength="10"
                                   type="text" name="xlf" value="${cm:stripTrailingZeros(pmdConfigMember.xlf)}">
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td>工资冲销</td>
                    <td colspan="3">
                        <c:if test="${param.view==1}">
                            ${cm:stripTrailingZeros(pmdConfigMember.gzcx)}
                        </c:if>
                        <c:if test="${param.view!=1}">
                            <input required class="number" data-rule-min="0" maxlength="10"
                                   type="text" name="gzcx" value="${cm:stripTrailingZeros(pmdConfigMember.gzcx)}">
                            <span style="font-size: 8px;">工资明细中工资冲销的负号“-”不要录入。</span>
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td>扣个人失业保险</td>
                    <td>
                        <c:if test="${param.view==1}">
                            ${cm:stripTrailingZeros(pmdConfigMember.shiyebx)}
                        </c:if>
                        <c:if test="${param.view!=1}">
                            <input required class="number" data-rule-min="0" maxlength="10"
                                   type="text" name="shiyebx" value="${cm:stripTrailingZeros(pmdConfigMember.shiyebx)}">
                        </c:if>
                    </td>
                    <td>扣个人养老保险</td>
                    <td>
                        <c:if test="${param.view==1}">
                            ${cm:stripTrailingZeros(pmdConfigMember.yanglaobx)}
                        </c:if>
                        <c:if test="${param.view!=1}">
                            <input required class="number" data-rule-min="0" maxlength="10"
                                   type="text" name="yanglaobx"
                                   value="${cm:stripTrailingZeros(pmdConfigMember.yanglaobx)}">
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td>扣个人职业年金</td>
                    <td>
                        <c:if test="${param.view==1}">
                            ${cm:stripTrailingZeros(pmdConfigMember.zynj)}
                        </c:if>
                        <c:if test="${param.view!=1}">
                            <input required class="number" data-rule-min="0" maxlength="10"
                                   type="text" name="zynj" value="${cm:stripTrailingZeros(pmdConfigMember.zynj)}">
                        </c:if>
                    </td>
                    <td>扣住房公积金</td>
                    <td>
                        <c:if test="${param.view==1}">
                            ${cm:stripTrailingZeros(pmdConfigMember.gjj)}
                        </c:if>
                        <c:if test="${param.view!=1}">
                            <input required class="number" data-rule-min="0" maxlength="10"
                                   type="text" name="gjj" value="${cm:stripTrailingZeros(pmdConfigMember.gjj)}">
                        </c:if>
                    </td>
                </tr>
            </c:if>
            <c:if test="${pmdNorm.formulaType==PMD_FORMULA_TYPE_EXTERNAL}">
                <tr>
                    <td>校聘工资</td>
                    <td>
                        <c:if test="${param.view==1}">
                            ${cm:stripTrailingZeros(pmdConfigMember.gwgz)}
                        </c:if>
                        <c:if test="${param.view!=1}">
                            <input required class="number" data-rule-min="0" maxlength="10"
                                   type="text" name="gwgz" value="${cm:stripTrailingZeros(pmdConfigMember.gwgz)}">
                        </c:if>
                    </td>
                    <td>岗位津贴</td>
                    <td>
                        <c:if test="${param.view==1}">
                            ${cm:stripTrailingZeros(pmdConfigMember.gwjt)}
                        </c:if>
                        <c:if test="${param.view!=1}">
                            <input required class="number" data-rule-min="0" maxlength="10"
                                   type="text" name="gwjt" value="${cm:stripTrailingZeros(pmdConfigMember.gwjt)}">
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td>扣个人失业保险</td>
                    <td>
                        <c:if test="${param.view==1}">
                            ${cm:stripTrailingZeros(pmdConfigMember.shiyebx)}
                        </c:if>
                        <c:if test="${param.view!=1}">
                            <input required class="number" data-rule-min="0" maxlength="10"
                                   type="text" name="shiyebx" value="${cm:stripTrailingZeros(pmdConfigMember.shiyebx)}">
                        </c:if>
                    </td>
                    <td>扣个人养老保险</td>
                    <td>
                        <c:if test="${param.view==1}">
                            ${cm:stripTrailingZeros(pmdConfigMember.yanglaobx)}
                        </c:if>
                        <c:if test="${param.view!=1}">
                            <input required class="number" data-rule-min="0" maxlength="10"
                                   type="text" name="yanglaobx"
                                   value="${cm:stripTrailingZeros(pmdConfigMember.yanglaobx)}">
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td>扣个人医疗保险</td>
                    <td>
                        <c:if test="${param.view==1}">
                            ${cm:stripTrailingZeros(pmdConfigMember.yiliaobx)}
                        </c:if>
                        <c:if test="${param.view!=1}">
                            <input required class="number" data-rule-min="0" maxlength="10"
                                   type="text" name="yiliaobx"
                                   value="${cm:stripTrailingZeros(pmdConfigMember.yiliaobx)}">
                        </c:if>
                    </td>
                    <td>扣住房公积金</td>
                    <td>
                        <c:if test="${param.view==1}">
                            ${cm:stripTrailingZeros(pmdConfigMember.gjj)}
                        </c:if>
                        <c:if test="${param.view!=1}">
                            <input required class="number" data-rule-min="0" maxlength="10"
                                   type="text" name="gjj" value="${cm:stripTrailingZeros(pmdConfigMember.gjj)}">
                        </c:if>
                    </td>
                </tr>
            </c:if>
            <tr class="due-pay">
                <td>应缴纳党费额度</td>
                <td colspan="2" style="text-align: center">
                    <c:if test="${param.view==1}">
                        ${cm:stripTrailingZeros(pmdConfigMember.duePay)}
                    </c:if>
                    <c:if test="${param.view!=1}">
                        <span id="duePaySpan">${duePay}</span>
                    </c:if>
                </td>
                <td align="center" style="width: 150px">
                    <c:if test="${param.view!=1}">
                        <button id="previewBtn" class="btn btn-success btn-sm btn-block">
                            <i class="fa fa-rmb"></i> 计&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;算
                        </button>
                    </c:if>
                </td>
            </tr>
        </table>
    </form>
</div>
<div class="modal-footer">
    <c:if test="${param.view==1}">
        <a href="#" data-dismiss="modal" class="btn btn-default">关闭</a>

    </c:if>
    <c:if test="${param.view!=1}">
        <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>

        <input id="submitBtn" type="button" class="btn btn-primary"
               value="提交"/>
    </c:if>

</div>
<style>
    input[type=text] {
        width: 120px;
        padding: 2px;
    }

    .due-pay {
        font-size: 18px;
        font-weight: bolder;
    }
</style>
<c:if test="${param.view!=1}">
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
                                <c:if test="${param.isBranchAdmin==1}">
                                $("#jqGrid2").trigger("reloadGrid");
                                </c:if>
                                <c:if test="${param.isBranchAdmin!=1}">
                                $("#jqGrid").trigger("reloadGrid");
                                </c:if>
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
</c:if>