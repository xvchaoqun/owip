<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/pmd/constants.jsp"%>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv"
                 data-url-page="${ctx}/pmd/pmdMember"
                 data-url-export="${ctx}/pmd/pmdMember_data"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.userId ||not empty param.chargeUserId ||not empty param.isOnlinePay
             ||not empty param.hasPay ||not empty param.orderNo ||not empty param.monthId ||not empty param._payTime
             || not empty param.isDelay || not empty param.isSelfPay|| not empty param.partyId}"/>
            <div class="jqgrid-vertical-offset buttons">
                <%--<a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</a>--%>
                    <button id="orderBtn" class="jqOpenViewBtn btn btn-primary btn-sm"
                            data-url="${ctx}/pmd/pmdOrder_orders"
                            data-width="850"
                            data-id-name="memberId">
                        <i class="fa fa-search"></i> 关联支付订单
                    </button>
                    <button class="jqOpenViewBtn btn btn-info btn-sm"
                            data-url="${ctx}/sysApprovalLog"
                            data-width="850"
                            data-querystr="&displayType=1&hideStatus=1&type=<%=SystemConstants.SYS_APPROVAL_LOG_TYPE_PMD_MEMBER%>">
                        <i class="fa fa-history"></i> 操作记录
                    </button>
                    <shiro:hasPermission name="pmdMember:add">
                        <button data-url="${ctx}/pmd/pmdMember_add?type=ow"
                                class="popupBtn btn btn-success btn-sm">
                            <i class="fa fa-plus"></i> 添加
                        </button>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="pmdMember:del">
                        <button id="delBtn" data-url="${ctx}/pmd/pmdMember_del"
                                data-title="删除"
                                data-msg="<div class='model-alert-tip'>确定删除这条缴费记录？（删除后不可恢复，请谨慎操作）</div>"
                                class="jqItemBtn btn btn-danger btn-sm">
                            <i class="fa fa-trash"></i> 删除
                        </button>
                    </shiro:hasPermission>
                    <button id="selectMemberTypeBtn" class="jqOpenViewBatchBtn btn btn-primary btn-sm"
                            data-url="${ctx}/pmd/pmdMember_selectMemberType"
                            data-grid-id="#jqGrid">
                        <i class="fa fa-check-square-o"></i> 选择党员类别
                    </button>
                    <shiro:hasPermission name="pmdMember:setIsOnlinePay">
                    <button id="selectMemberTypeBtn" class="jqOpenViewBatchBtn btn btn-info btn-sm"
                            data-url="${ctx}/pmd/pmdMember_setIsOnlinePay"
                            data-querystr="auth=1"
                            data-grid-id="#jqGrid">
                        <i class="fa fa-edit"></i> 修改缴费方式
                    </button>
                    </shiro:hasPermission>
                    <button id="helpSetSalaryBtn" class="jqOpenViewBtn btn btn-primary btn-sm"
                            data-width="600"
                            data-url="${ctx}/user/pmd/pmdMember_setSalary"
                            data-grid-id="#jqGrid"
                            data-querystr="&isSelf=0"
                            data-id-name="pmdMemberId">
                        <i class="fa fa-rmb"></i> 修改党费应交额</button>
                    <button id="showSalaryBtn" class="jqOpenViewBtn btn btn-success btn-sm"
                            data-width="600"
                            data-url="${ctx}/user/pmd/pmdMember_setSalary"
                            data-grid-id="#jqGrid"
                            data-querystr="&isSelf=0&view=2"
                            data-id-name="pmdMemberId">
                        <i class="fa fa-rmb"></i> 查看党费应交额</button>
            </div>
            <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                <div class="widget-header">
                    <h4 class="widget-title">搜索</h4><span class="widget-note">${note_searchbar}</span>

                    <div class="widget-toolbar">
                        <a href="#" data-action="collapse">
                            <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
                        </a>
                    </div>
                </div>
                <div class="widget-body">
                    <div class="widget-main no-padding">
                        <form class="form-inline search-form" id="searchForm">
                            <input type="hidden" name="cls" value="${cls}">
                            <div class="form-group">
                                <label>缴费订单号</label>
                                <input class="form-control search-query" name="orderNo" type="text" value="${param.orderNo}"
                                       placeholder="请输入缴费订单号" style="width: 200px;">
                            </div>
                            <div class="form-group">
                                <label>缴费月份</label>
                                <select data-rel="select2" name="monthId"
                                        data-width="130"
                                        data-placeholder="请选择">
                                    <option></option>
                                    <c:forEach items="${pmdMonths}" var="pmdMonth">
                                        <option value="${pmdMonth.id}">${cm:formatDate(pmdMonth.payMonth, "yyyy年MM月")}</option>
                                    </c:forEach>
                                </select>
                                <script>
                                    $("#searchForm select[name=monthId]").val("${param.monthId}")
                                </script>
                            </div>
                            <div class="form-group">
                                <label>所在${_p_partyName}</label>
                                <select class="form-control" data-width="250"  data-rel="select2-ajax"
                                        data-ajax-url="${ctx}/party_selects?del=0"
                                        name="partyId" data-placeholder="请选择${_p_partyName}">
                                    <option value="${party.id}" delete="${party.isDeleted}">${party.name}</option>
                                </select>
                            </div>
                            <div class="form-group" style="${(empty branch)?'display: none':''}" id="branchDiv">
                                <label>所在党支部</label>
                                <select class="form-control"  data-rel="select2-ajax" data-ajax-url="${ctx}/branch_selects?del=0"
                                        name="branchId" data-placeholder="请选择党支部">
                                    <option value="${branch.id}" delete="${branch.isDeleted}">${branch.name}</option>
                                </select>
                            </div>
                            <script>
                                $.register.party_branch_select($("#searchForm"), "branchDiv",
                                        '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}" );
                            </script>
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
                                    $("#searchForm select[name=isOnlinePay]").val("${param.isOnlinePay}")
                                </script>
                            </div>
                            <div class="form-group">
                                <label>缴费党员</label>
                                <select data-rel="select2-ajax"
                                        data-ajax-url="${ctx}/member_selects?noAuth=1"
                                        name="userId" data-placeholder="请输入账号或姓名或学工号">
                                    <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>代缴人</label>
                                <select data-rel="select2-ajax"
                                        data-ajax-url="${ctx}/member_selects?noAuth=1"
                                        name="chargeUserId" data-placeholder="请输入账号或姓名或学工号">
                                    <option value="${chargeUser.id}">${chargeUser.realname}-${chargeUser.code}</option>
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
                                    $("#searchForm select[name=hasPay]").val("${param.hasPay}")
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
                                    $("#searchForm select[name=isDelay]").val("${param.isDelay}")
                                </script>
                            </div>
                            <div class="form-group">
                                <label>是否代缴</label>
                                <select data-rel="select2" name="isSelfPay"
                                        data-width="80"
                                        data-placeholder="请选择">
                                    <option></option>
                                    <option value="0">是</option>
                                    <option value="1">否</option>
                                </select>
                                <script>
                                    $("#searchForm select[name=isSelfPay]").val("${param.isSelfPay}")
                                </script>
                            </div>
                            <div class="form-group">
                                <label>缴费日期</label>
                                <div class="input-group tooltip-success" data-rel="tooltip"
                                     title="请选择缴费日期范围">
                                                    <span class="input-group-addon"><i
                                                            class="fa fa-calendar bigger-110"></i></span>
                                    <input placeholder="请选择缴费日期范围" data-rel="date-range-picker"
                                           class="form-control date-range-picker" type="text"
                                           name="_payTime" value="${param._payTime}"/>
                                </div>
                            </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>

                                <c:if test="${_query}">&nbsp;
                                    <button type="button"
                                            data-querystr="cls=${cls}"
                                            class="reloadBtn btn btn-warning btn-sm">
                                        <i class="fa fa-reply"></i> 重置
                                    </button>
                                </c:if>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="space-4"></div>
            <table id="jqGrid" class="jqGrid table-striped"></table>
            <div id="jqGridPager"></div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
<jsp:include page="pmdMember_colModel.jsp?type=admin"/>
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>
    $.register.user_select($('#searchForm select[name=userId]'));
    $.register.user_select($('#searchForm select[name=chargeUserId]'));
    $('#searchForm [data-rel="select2"]').select2();
    $("#jqGrid").jqGrid({
        url: '${ctx}/pmd/pmdMember_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel:colModel,
        onSelectRow: function (id, status) {
            saveJqgridSelected("#" + this.id, id, status);
            _onSelectRow(this)
        },
        onSelectAll: function (aRowids, status) {
            saveJqgridSelected("#" + this.id);
            _onSelectRow(this)
        }
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('[data-rel="tooltip"]').tooltip();

    function _onSelectRow(grid) {
        var ids = $(grid).getGridParam("selarrrow");

        if (ids.length > 1) {
            $("#delBtn,#helpSetSalaryBtn, #showSalaryBtn, #orderBtn").prop("disabled", true);
        } else if (ids.length == 1) {
            var rowData = $(grid).getRowData(ids[0]);
            var hasPay = (rowData.hasPay == "true");
            var isCurrentMonth = (rowData.monthId == '${_pmdMonth.id}');
            var isDelay = (rowData.isDelay == "true");
            var isOnlinePay = (rowData.isOnlinePay == "true");

            $("#delBtn").prop("disabled", hasPay);
            $("#helpSetSalaryBtn, #showSalaryBtn").prop("disabled",
                    (rowData.formulaType!=${PMD_FORMULA_TYPE_ONJOB}
                    &&rowData.formulaType!=${PMD_FORMULA_TYPE_EXTERNAL}
                    &&rowData.formulaType!=${PMD_FORMULA_TYPE_RETIRE}) ||
                    rowData.isSelfSetSalary=="1" || hasPay || !isCurrentMonth || isDelay);

            $("#orderBtn").prop("disabled", !isOnlinePay);
        }

        var configMemberType; // 选择的党员类别（设定党员分类别时的url参数，此时要求是同一类别）
        var selectMemberTypeBtn = true;
        $.each(ids, function(i, id){
            var rowData = $(grid).getRowData(id);
            var hasPay = (rowData.hasPay == "true");
            var isDelay = (rowData.isDelay == "true");
            if(selectMemberTypeBtn){
                if (hasPay || isDelay) {
                    selectMemberTypeBtn = false;
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
            var querystr = "&auth=1&configMemberType=" + configMemberType;
            $(this).data("querystr", querystr);
        });

        $("#selectMemberTypeBtn").prop("disabled", !selectMemberTypeBtn || configMemberType==-1);
    }
</script>