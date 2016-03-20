<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content">
            <div class="tabbable">
                <jsp:include page="/WEB-INF/jsp/user/passport/passport_menu.jsp"/>
                <div class="tab-content">
                    <div id="home4" class="tab-pane in active">
                        <div class="buttons">
                            <button data-url="${ctx}/user/passportApply_begin"
                                    class="openView btn btn-success btn-sm"><i class="fa fa-plus"></i> 申请办理因私出国（境）证件
                            </button>
                        </div>
        <div class="myTableDiv"
             data-url-page="${ctx}/user/passportApply_page"
             data-url-del="${ctx}/user/passportApply_del">

            <div class="space-4"></div>
            <c:if test="${commonList.recNum>0}">
                <table class="table table-actived table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                            <th>申请日期</th>
                            <th>申办证件名称</th>
                            <th>申请表</th>
							<th>审批状态</th>
							<th>应交组织部日期</th>
							<th>实交组织部日期</th>
                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${passportApplys}" var="passportApply" varStatus="st">
                        <c:set var="cadre" value="${cadreMap.get(passportApply.cadreId)}"/>
                        <c:set var="sysUser" value="${cm:getUserById(cadre.userId)}"/>
                        <tr>
                                <td>${cm:formatDate(passportApply.applyDate,'yyyy-MM-dd')}</td>
								<td>${passportTypeMap.get(passportApply.classId).name}</td>
								<td>
                                    <button class="openView btn btn-success btn-mini btn-xs"
                                            data-url="${ctx}/user/passportApply_confirm?type=view&id=${passportApply.id}">
                                        <i class="fa fa-file-o"></i> 申请表
                                    </button>
                                </td>

								<td>${PASSPORT_APPLY_STATUS_MAP.get(passportApply.status)}</td>
								<td>${cm:formatDate(passportApply.expectDate,'yyyy-MM-dd')}</td>
								<td>${cm:formatDate(passportApply.handleDate,'yyyy-MM-dd')}</td>
                            <td>
                                <div class="hidden-sm hidden-xs action-buttons">
                                    <c:if test="${passportApply.status==PASSPORT_APPLY_STATUS_NOT_PASS}">
                                        <button data-remark="${passportApply.remark}" class="remarkBtn btn btn-warning btn-mini btn-xs">
                                            <i class="fa fa-info-circle"></i> 未批准原因
                                        </button>
                                    </c:if>
                                    <c:if test="${passportApply.status==PASSPORT_APPLY_STATUS_PASS}">
                                        <button data-id="${passportApply.id}" class="printBtn btn btn-info btn-mini btn-xs">
                                            <i class="fa fa-print"></i> 打印审批表
                                        </button>
                                    </c:if>
                                     <c:if test="${passportApply.status==PASSPORT_APPLY_STATUS_INIT}">
                                    <button class="delBtn btn btn-danger btn-mini btn-xs" data-id="${passportApply.id}">
                                        <i class="fa fa-trash"></i> 删除
                                    </button>
                                     </c:if>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <wo:page commonList="${commonList}" uri="${ctx}/passportApply_page" target="#page-content" pageNum="5"
                         model="3"/>
            </c:if>
            <c:if test="${commonList.recNum==0}">
                <div class="well well-lg center">
                    <h4 class="green lighter">暂无记录</h4>
                </div>
            </c:if>
        </div>
                    </div></div></div>
        </div>
        <div id="item-content">

        </div>
    </div>
</div>
<script>
    $(".printBtn").click(function(){
        printWindow("${ctx}/report/passportApply?id="+ $(this).data("id"))
    });
    $(".remarkBtn").click(function(){
        SysMsg.info('<p style="padding:20px;font-size:25px;text-indent: 2em; ">'+$(this).data("remark")+'</p>'
                ,'<h3 class="label label-warning" style="font-size: 20px; height: 30px;">未批准原因</h3>');
    });


    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>