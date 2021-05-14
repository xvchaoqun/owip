<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="../constants.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content">
            <div class="row">
                <div class="col-xs-12">
                    <div class="alert alert-block alert-success" style="font-size: 18px;font-weight: bolder;margin-bottom: 10px">
                        <i class="ace-icon fa fa-rmb green"></i>
                        &nbsp;党费补缴列表
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-8 infobox-container">
                        <c:if test="${fn:length(records)==0}">
                            <div class="none" style="font-size: 16px">目前没有缴费记录</div>
                        </c:if>
                        <c:forEach items="${records}" var="record">
                            <div class="infobox infobox-blue2">
                                <div class="infobox-data" style="width: 100%">
                                    <table class="course-list" style="width: 100%">
                                        <tr>
                                            <td class="name">
                                                缴费金额：${record.amt}
                                            </td>
                                            <td class="name">
                                                缴费月份：${cm:formatDate(record.startMonth,'yyyyMM')}
                                                <c:if test="${cm:getDayCountBetweenDate(record.startMonth, record.endMonth)>1}">
                                                ~ ${cm:formatDate(record.endMonth,'yyyyMM')}
                                                </c:if>
                                            </td>
                                            <td class="status" rowspan="2" style="text-align: center;width: 60px">
                                                <c:if test="${record.hasPay}">
                                                    <span class="text-success">已缴费</span>
                                                </c:if>
                                                <c:if test="${!record.hasPay}">
                                                    <button class="payBtn btn btn-success btn-xs"
                                                        data-url="${ctx}/m/pmd/pmdFee_confirm?isMobile=1&id=${record.id}"><i class="fa fa-rmb"></i> 缴费</button>
                                                </c:if>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td colspan="2">
                                            <span style="font-size: 10pt;">
                                                   ${partyMap.get(record.partyId).name}
                                            <c:if test="${not empty record.branchId}">
                                                - ${branchMap.get(record.branchId).name}
                                            </c:if>
                                            </span>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                            </div>
                        </c:forEach>

                    </div>
                    <div class="message-footer clearfix" style="margin: 0 20px">
                        <wo:page commonList="${commonList}" uri="${ctx}/m/pmd/pmdFee_page" target="#page-content" model="4"/>
                    </div>
                    <!-- /.col -->
                </div>
                <!-- /.row -->
                <!-- PAGE CONTENT ENDS -->
            </div>
            <!-- /.col -->
        </div>
        <div id="body-content-view">
        </div>
    </div>
</div>
<div id="payFormDiv"></div>
<style>
    .infobox {
        height: auto;
        padding-left: 2px;
    }

    .course-list .name {
        font-weight: bold;
        color: black;
        overflow: hidden;
    }

    .course-list .status {
        text-align: right;
        /*padding-left:10px;*/
        white-space: nowrap;
    }
</style>
<script type="text/template" id="payFormTpl">
    <jsp:include page="/ext/pmd_payForm.jsp"/>
</script>
<script>
    $(".payBtn").click(function(){
        $.loadModal($(this).data("url"));
    });
</script>