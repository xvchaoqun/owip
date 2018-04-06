<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv"
                 data-url-page="${ctx}/user/pmd/pmdMember"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.payMonth
            ||not empty param.hasPay ||not empty param.isDelay ||not empty param.isSelfPay
             || not empty param.code || not empty param.sort}"/>

            <div class="jqgrid-vertical-offset buttons">
                <a class="popupBtn btn btn-warning btn-sm"
                   data-width="850"
                   data-url="${ctx}/hf_content?code=${HF_PMD_MEMBER}">
                    <i class="fa fa-info-circle"></i> 使用说明</a>

                <c:if test="${canSetSalary}">
                <a class="popupBtn btn btn-success btn-sm"
                   data-width="600"
                   data-url="${ctx}/user/pmd/pmdMember_setSalary?view=1">
                    <i class="fa fa-rmb"></i> 查看应缴党费额度</a>
                </c:if>
            </div>
            <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                <div class="widget-header">
                    <h4 class="widget-title">搜索</h4>

                    <div class="widget-toolbar">
                        <a href="#" data-action="collapse">
                            <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
                        </a>
                    </div>
                </div>
                <div class="widget-body">
                    <div class="widget-main no-padding">
                        <form class="form-inline search-form" id="searchForm">
                        <%--<div class="form-group">
                            <label>缴费订单号</label>
                            <input class="form-control search-query" name="orderNo" type="text" value="${param.orderNo}"
                                   placeholder="请输入缴费订单号">
                        </div>--%>
                        <div class="form-group">
                            <label>缴纳月份</label>
                            <div class="input-group" style="width: 120px;">
                                <input required class="form-control date-picker" name="payMonth" type="text"
                                       data-date-format="yyyy-mm"
                                       data-date-min-view-mode="1" value="${param.payMonth}" />
                                <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                            </div>
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
                                <label>缴费方式</label>
                                <select data-rel="select2" name="isSelfPay"
                                        data-width="120"
                                        data-placeholder="请选择">
                                    <option></option>
                                    <option value="0">代缴党费</option>
                                    <option value="1">线上缴费</option>
                                </select>
                                <script>
                                    $("#searchForm select[name=isSelfPay]").val("${param.isSelfPay}")
                                </script>
                            </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>

                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="resetBtn btn btn-warning btn-sm">
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
<jsp:include page="/WEB-INF/jsp/pmd/pmdMember/pmdMember_colModel.jsp"/>
<script>
    $.register.date($('.date-picker'));
    $("#jqGrid").jqGrid({
        multiselect:false,
        url: '${ctx}/user/pmd/pmdMember_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: colModel,
        rowattr: function(rowData, currentObj, rowId)
        {
            if(rowData.monthId=='${_pmdMonth.id}' && !rowData.hasPay) {
                //console.log(rowData)
                return {'class':'danger'}
            }
        }
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>