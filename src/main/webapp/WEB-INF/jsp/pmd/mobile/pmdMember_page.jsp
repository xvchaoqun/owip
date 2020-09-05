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
                        &nbsp;党费缴纳列表
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
                                                缴费金额：${record.duePay}
                                            </td>
                                            <td class="name">
                                                缴费月份：${cm:formatDate(record.payMonth,'yyyyMM')}
                                            </td>
                                            <td class="status" rowspan="2" style="text-align: center;width: 60px">
                                                <c:if test="${record.payStatus==0}">
                                                    <c:if test="${record.hasPay}">
                                                        ${record.isDelay?'<span class="text-success">已补缴</span>':'<span class="text-success">已缴费</span>'}
                                                    </c:if>
                                                    <c:if test="${!record.hasPay}">--</c:if>
                                                </c:if>
                                                <c:if test="${record.payStatus!=0}">
                                                <button class="payBtn btn btn-success btn-xs" data-id="${record.id}">
                                                    <i class="fa fa-rmb"></i> ${record.payStatus==1?'缴费':'补缴'}</button>
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
                        <wo:page commonList="${commonList}" uri="${ctx}/m/pmd/pmdMember_page" target="#page-content" model="4"/>
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
<form id="payForm" action="<%=Pay.payURL%>" method="post"></form>
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
    <input type="hidden" name="tranamt" value="{{=order.tranamt}}"/>
    <input type="hidden" name="account" value="{{=order.account}}"/>
    <input type="hidden" name="sno" value="{{=order.sno}}"/>
    <input type="hidden" name="toaccount" value="{{=order.toaccount}}"/>
    <input type="hidden" name="thirdsystem" value="{{=order.thirdsystem}}"/>
    <input type="hidden" name="thirdorderid" value="{{=order.thirdorderid}}"/>
    <input type="hidden" name="ordertype" value="{{=order.ordertype}}"/>
    <input type="hidden" name="orderdesc" value="{{=order.orderdesc}}"/>
    <input type="hidden" name="praram1" value="{{=order.praram1}}"/>
    <input type="hidden" name="thirdurl" value="{{=thirdurl}}"/>
    <input type="hidden" name="sign" value="{{=order.sign}}"/>
</script>
<script>
    $(".payBtn").click(function () {
        var $this = $(this);
        $.ajax({
            type : "post",
            url : "${ctx}/m/pmd/payConfirm",
            data:{id:$this.data("id"), isSelfPay:1, isMobile:1},
            async : false, // 同步方法
            dataType:"json",
            success : function(data){
                if(data.success){

                    console.log(data.order)

                    $("#payForm").html(_.template($("#payFormTpl").html())({order: data.order, thirdurl:data.thirdurl}))
                    $("#payForm").submit();
                }
            }
        });
    });
</script>