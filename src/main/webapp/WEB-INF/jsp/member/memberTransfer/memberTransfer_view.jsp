<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="MEMBER_TRANSFER_STATUS_MAP" value="<%=MemberConstants.MEMBER_TRANSFER_STATUS_MAP%>"/>
<div class="widget-box">
    <div class="widget-header">
        <h4 class="widget-title"><i class="fa fa-paw blue"></i> 校内组织关系转接申请</h4>

        <div class="widget-toolbar">
            <a href="javascript:;" data-action="collapse">
                <i class="ace-icon fa fa-chevron-up"></i>
            </a>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main">
            <table class="table table-bordered table-striped">
                <tbody>
                    <jsp:include page="memberTransfer_table.jsp"/>
                    <c:if test="${fn:length(memberTransfers)>0}">
                       <tr>
                            <td class="bg-right">
                                历史办理记录
                            </td>
                            <td class="bg-left" colspan="7">
                                <c:forEach items="${memberTransfers}" var="mt" varStatus="vs">
                                    <span class="text text-success bolder" style="margin-right: 5px;text-decoration: underline;">
                                       <a href="javascript:;" class="popupBtn"
                                           data-width="1200"
                                           data-url="${ctx}/memberTransfer_view?popup=1&id=${mt.id}">
                                           <i class="fa fa-hand-o-right"></i> ${cm:formatDate(mt.fromHandleTime,'yyyy.MM.dd')}
                                       </a>
                                    </span>
                                </c:forEach>
                            </td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>
