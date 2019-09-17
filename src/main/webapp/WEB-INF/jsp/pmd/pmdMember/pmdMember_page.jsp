<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/pmd/constants.jsp"%>
<div class="widget-box transparent" id="useLogs">
    <div class="widget-header">
        <c:if test="${param.backToPartyList==1}">
            <h4 class="widget-title lighter smaller">
                <a href="javascript:;" data-url="/pmd/pmdParty?cls=2&monthId=${param.monthId}" class="openView btn btn-xs btn-success">
                    <i class="ace-icon fa fa-backward"></i>
                    返回</a>
            </h4>
        </c:if>
        <c:if test="${param.backToPartyList!=1}">
            <h4 class="widget-title lighter smaller">
                <a href="javascript:;" class="hideView btn btn-xs btn-success">
                    <i class="ace-icon fa fa-backward"></i>
                    返回</a>
            </h4>
        </c:if>

        <div class="widget-toolbar no-border">
            <jsp:include page="menu.jsp"/>
        </div>
    </div>
    <form class="form-inline search-form" id="searchForm2" style="position: absolute;top:-6px;left:80px;">
        <input type="hidden" name="partyId" value="${param.partyId}">
        <input type="hidden" name="branchId" value="${param.branchId}">
        <input type="hidden" name="monthId" value="${param.monthId}">
        <div class="form-group">
            <label>缴费方式</label>
            <select data-rel="select2" name="isOnlinePay"
                    data-width="120"
                    data-placeholder="请选择">
                <option></option>
                <option value="1">线上缴费</option>
                <option value="0">现金缴费</option>
            </select>
            <script>
                $("#searchForm2 select[name=isOnlinePay]").val("${param.isOnlinePay}")
            </script>
        </div>
        <div class="form-group">
            <label>姓名</label>
            <select data-rel="select2-ajax"
                    data-ajax-url="${ctx}/member_selects?noAuth=1&partyId=${param.partyId}&branchId=${param.branchId}&status=${MEMBER_STATUS_NORMAL}"
                    name="userId" data-placeholder="请输入账号或姓名或学工号">
                <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
            </select>
        </div>
        <div class="form-group">
            <label>缴费状态</label>
            <select data-rel="select2" name="hasPay"
                    data-width="100"
                    data-placeholder="请选择">
                <option></option>
                <option value="0">未缴费</option>
                <option value="1">已缴费</option>
            </select>
            <script>
                $("#searchForm2 select[name=hasPay]").val("${param.hasPay}")
            </script>
        </div>
        <div class="form-group">
            <label>按时/延迟缴费</label>
            <select data-rel="select2" name="isDelay"
                    data-width="120"
                    data-placeholder="请选择">
                <option></option>
                <option value="0">按时缴费</option>
                <option value="1">延迟缴费</option>
            </select>
            <script>
                $("#searchForm2 select[name=isDelay]").val("${param.isDelay}")
            </script>
        </div>
        <div class="form-group">
            <label>线上缴费方式</label>
            <select data-rel="select2" name="isSelfPay"
                    data-width="120"
                    data-placeholder="请选择">
                <option></option>
                <option value="0">代缴党费</option>
                <option value="1">线上缴费</option>
            </select>
            <script>
                $("#searchForm2 select[name=isSelfPay]").val("${param.isSelfPay}")
            </script>
        </div>
        <c:set var="_query" value="${not empty param.userId ||not empty param.isOnlinePay ||not empty param.hasPay
             || not empty param.isDelay || not empty param.isSelfPay}"/>
        <div class="form-group">
            <button type="button" data-url="${ctx}/pmd/pmdMember?cls=${param.cls}&backToPartyList=${param.backToPartyList}"
                    data-target="#body-content-view" data-form="#searchForm2"
                    class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找
            </button>
            <c:if test="${_query}">
                <button type="button"
                        data-url="${ctx}/pmd/pmdMember"
                        data-querystr="cls=${param.cls}&backToPartyList=${param.backToPartyList}&partyId=${param.partyId}&branchId=${param.branchId}&monthId=${param.monthId}"
                        data-target="#body-content-view"
                        class="reloadBtn btn btn-warning btn-sm">
                    <i class="fa fa-reply"></i> 重置
                </button>
            </c:if>
        </div>
    </form>
    <div class="widget-body">
        <div class="widget-main padding-4">
            <div class="tab-content padding-8">
                <div class="jqgrid-vertical-offset buttons">
                    <c:if test="${canAdmin && param.monthId==_pmdMonth.id}">
                        <%--<button class="jqOpenViewBtn btn btn-warning btn-sm"
                                data-url="${ctx}/"
                                data-id-name="userId">
                            <i class="fa fa-send"></i> 通知
                        </button>--%>
                        <button class="popupBtn btn btn-danger btn-sm tooltip-error"
                                data-rel="tooltip" data-placement="top" title="本月党费收缴已经启动，通知本支部党员缴纳党费。"
                                data-url="${ctx}/pmd/pmdSendMsg_notifyMembers?partyId=${param.partyId}&branchId=${param.branchId}"
                                data-grid-id="#jqGrid2">
                            <i class="fa fa-send"></i> 发送通知
                        </button>
                        <button class="jqOpenViewBatchBtn btn btn-warning btn-sm tooltip-warning"
                                data-rel="tooltip" data-placement="top" title="提醒未缴纳党费的党员尽快缴纳党费。"
                                data-url="${ctx}/pmd/pmdSendMsg_urgeMembers"
                                data-grid-id="#jqGrid2"
                                data-querystr="partyId=${param.partyId}&branchId=${param.branchId}"
                                data-need-id="false"><i class="fa fa-send"></i> 发送提醒
                        </button>

                       <%-- <button id="setDuePayBtn" class="jqOpenViewBatchBtn btn btn-success btn-sm"
                                data-url="${ctx}/pmd/pmdMember_setDuePay"
                                data-grid-id="#jqGrid2">
                            <i class="fa fa-edit"></i> 设定缴纳额度
                        </button>--%>
                        <button id="selectMemberTypeBtn" class="jqOpenViewBatchBtn btn btn-primary btn-sm"
                                data-url="${ctx}/pmd/pmdMember_selectMemberType"
                                data-grid-id="#jqGrid2">
                            <i class="fa fa-check-square-o"></i> 选择党员类别
                        </button>
                        <button id="helpSetSalaryBtn" class="jqOpenViewBtn btn btn-success btn-sm"
                                data-width="600"
                                data-url="${ctx}/user/pmd/pmdMember_setSalary"
                                data-grid-id="#jqGrid2"
                                data-querystr="&isBranchAdmin=1&isSelf=0"
                                data-id-name="pmdMemberId">
                            <i class="fa fa-rmb"></i> 修改党费应交额</button>
                        <button id="helpPayBtn" class="jqOpenViewBtn btn btn-success btn-sm"
                                data-url="${ctx}/user/pmd/payConfirm"
                                data-querystr="&isSelfPay=0"
                                data-grid-id="#jqGrid2">
                            <i class="fa fa-rmb"></i> 代缴党费
                        </button>
                        <button id="helpBatchPayBtn" class="jqOpenViewBatchBtn btn btn-success btn-sm"
                                data-url="${ctx}/user/pmd/payConfirm_batch"
                                data-querystr="isDelay=0"
                                data-grid-id="#jqGrid2">
                            <i class="fa fa-rmb"></i> 批量代缴党费
                        </button>
                        <shiro:hasPermission name="pmdMember:delay">
                            <button id="delayBtn" class="jqOpenViewBtn btn btn-info btn-sm"
                                    data-url="${ctx}/pmd/pmdMember_delay" data-grid-id="#jqGrid2">
                                <i class="fa fa-hourglass-1"></i> 延迟缴费
                            </button>
                            <button id="unDelayBtn" class="jqItemBtn btn btn-warning btn-sm"
                                    data-url="${ctx}/pmd/pmdMember_unDelay"
                                    data-title="取消延迟缴费"
                                    data-msg="确认取消延迟缴费？"
                                    data-callback="_reload2"
                                    data-grid-id="#jqGrid2">
                                <i class="fa fa-hourglass-3"></i> 取消延迟缴费
                            </button>
                        </shiro:hasPermission>
                        <button id="selectReduceNormBtn" class="jqOpenViewBatchBtn btn btn-danger btn-sm"
                                data-url="${ctx}/pmd/pmdMember_selectReduceNorm"
                                data-grid-id="#jqGrid2">
                            <i class="fa fa-minus-circle"></i> 党费减免
                        </button>
                        <shiro:hasPermission name="pmdMember:setIsOnlinePay">
                        <button id="selectMemberTypeBtn" class="jqOpenViewBatchBtn btn btn-info btn-sm"
                                data-url="${ctx}/pmd/pmdMember_setIsOnlinePay"
                                data-grid-id="#jqGrid2">
                            <i class="fa fa-edit"></i> 变更缴费方式
                        </button>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="pmdMember:add">
                            <button data-url="${ctx}/pmd/pmdMember_add?partyId=${param.partyId}&branchId=${param.branchId}"
                                    class="popupBtn btn btn-success btn-sm">
                                <i class="fa fa-plus"></i> 增加
                            </button>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="pmdMember:del">
                            <button id="delBtn" data-url="${ctx}/pmd/pmdMember_del"
                                    data-title="删除"
                                    data-msg="<div class='model-alert-tip'>确定删除这条未缴费记录？（删除后不可恢复，请谨慎操作）</div>"
                                    data-grid-id="#jqGrid2"
                                    data-callback="_reload2"
                                    class="jqItemBtn btn btn-danger btn-sm">
                                <i class="fa fa-remove"></i> 删除
                            </button>
                        </shiro:hasPermission>
                    </c:if>
                    <button class="jqOpenViewBtn btn btn-info btn-sm"
                            data-grid-id="#jqGrid2"
                            data-url="${ctx}/sysApprovalLog"
                            data-width="850"
                            data-querystr="&displayType=1&hideStatus=1&type=<%=SystemConstants.SYS_APPROVAL_LOG_TYPE_PMD_MEMBER%>">
                        <i class="fa fa-history"></i> 操作记录
                    </button>
                </div>
                <div class="space-4"></div>
                <table id="jqGrid2" class="jqGrid2 table-striped"></table>
                <div id="jqGridPager2"></div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="pmdMember_colModel.jsp?type=admin"/>
<script>

    $('[data-rel="tooltip"]').tooltip();

    $.register.user_select($('#searchForm2 select[name=userId]'));
    $('#searchForm2 [data-rel="select2"]').select2();
    function _reload2() {
        $("#jqGrid2").trigger("reloadGrid");
    }
    $("#jqGrid2").jqGrid({
        <c:if test="${!canAdmin || param.monthId!=_pmdMonth.id}">
        multiselect: false,
        </c:if>
        pager: "jqGridPager2",
        url: '${ctx}/pmd/pmdMember_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: colModel,
        onSelectRow: function (id, status) {
            saveJqgridSelected("#" + this.id, id, status);
            _onSelectRow(this)
        },
        onSelectAll: function (aRowids, status) {
            saveJqgridSelected("#" + this.id);
            _onSelectRow(this)
        }
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");

    function _onSelectRow(grid) {
        var ids = $(grid).getGridParam("selarrrow");

        if (ids.length > 1) {
            $("#helpPayBtn,#delayBtn,#unDelayBtn,#helpSetSalaryBtn,#delBtn").prop("disabled", true);
        } else if (ids.length == 1) {
            var rowData = $(grid).getRowData(ids[0]);
            var isCurrentMonth = (rowData.monthId == '${_pmdMonth.id}');
            var isDelay = (rowData.isDelay == "true");
            var hasPay = (rowData.hasPay == "true");
            var notSetDuePay = (rowData.duePay==undefined || rowData.duePay <= 0);
            var needConfirmDuePay = (rowData.needConfirmDuePay == "true");
            //console.log(rowData.configMemberTypeId)
            $("#helpPayBtn").prop("disabled", needConfirmDuePay||notSetDuePay || hasPay || (isCurrentMonth && isDelay));
            $("#delBtn").prop("disabled", hasPay || (isCurrentMonth && isDelay));
            $("#delayBtn").prop("disabled", needConfirmDuePay||notSetDuePay || hasPay || !isCurrentMonth || isDelay);
            $("#unDelayBtn").prop("disabled", needConfirmDuePay||notSetDuePay || hasPay || !isCurrentMonth || !isDelay);
            //$("#notifyBtn").prop("disabled", needConfirmDuePay||$.trim(rowData.configMemberTypeId)=='' || hasPay || !isCurrentMonth || isDelay);
            //console.log(rowData.isSelfSetSalary)
            //console.log("formulaType="+rowData.formulaType)
            $("#helpSetSalaryBtn").prop("disabled",
                    (rowData.formulaType!=${PMD_FORMULA_TYPE_ONJOB}
                    &&rowData.formulaType!=${PMD_FORMULA_TYPE_EXTERNAL}
                    &&rowData.formulaType!=${PMD_FORMULA_TYPE_RETIRE}) ||
                    rowData.isSelfSetSalary=="1" || hasPay || !isCurrentMonth || isDelay);
        }

        var configMemberType; // 选择的党员类别（设定党员分类别时的url参数，此时要求是同一类别）

        var hasSetDuePay = true;
        var selectMemberTypeBtn = true;
        var selectReduceNormBtn = true;
        $.each(ids, function(i, id){
            var rowData = $(grid).getRowData(id);

            var isDelay = (rowData.isDelay == "true");
            var hasPay = (rowData.hasPay == "true");
            var normType = rowData.normType;

            if(hasSetDuePay){
                if (rowData.duePay==undefined || rowData.duePay <= 0) {
                    hasSetDuePay = false;
                }
            }

            if(selectMemberTypeBtn){
                if (hasPay || isDelay) {
                    selectMemberTypeBtn = false;
                }
            }

            if(selectReduceNormBtn){
                if (hasPay || isDelay) {
                    selectReduceNormBtn = false;
                }
            }

            if(configMemberType != -1){
                if(configMemberType!=undefined && rowData.type != configMemberType){
                    configMemberType = -1; // 选择的党员不是同一类别
                }else {
                    configMemberType = rowData.type;
                }
            }
        })

        $("#selectMemberTypeBtn").each(function(){
            var querystr = "&configMemberType=" + configMemberType;
            $(this).data("querystr", querystr);
        });

        $("#helpBatchPayBtn").prop("disabled", !selectMemberTypeBtn || !hasSetDuePay || ids.length==1);
        $("#selectMemberTypeBtn").prop("disabled", !selectMemberTypeBtn || configMemberType==-1);
        $("#selectReduceNormBtn").prop("disabled", needConfirmDuePay|| !selectReduceNormBtn || !hasSetDuePay);
    }
</script>