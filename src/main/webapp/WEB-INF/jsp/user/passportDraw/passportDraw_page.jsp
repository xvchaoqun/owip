<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content">
        <mytag:sort-form css="form-inline hidden-sm hidden-xs" id="searchForm">
            <div class="vspace-12"></div>
            <div class="buttons">
                <a class="openView btn btn-success btn-sm" data-url="${ctx}/user/passportDraw_select"><i class="fa fa-plus"></i> 申请使用因私出国（境）证件</a>
            </div>
        </mytag:sort-form>
        <div class="space-4"></div>
        <div class="tabbable">
            <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                <li  class="<c:if test="${type==1}">active</c:if>">
                    <a href="?type=1"><i class="fa fa-plane"></i> 因私出国（境）</a>
                </li>
                <li  class="<c:if test="${type==2}">active</c:if>">
                    <a href="?type=2"><i class="fa fa-ship"></i> 因公出访台湾</a>
                </li>
                <li  class="<c:if test="${type==3}">active</c:if>">
                    <a href="?type=3"><i class="fa fa-share"></i> 处理其他事务</a>
                </li>
            </ul>

            <div class="tab-content">
                <div id="home4" class="tab-pane in active">
        <div class="myTableDiv"
             data-url-au="${ctx}/user/passportDraw_au"
             data-url-page="${ctx}/user/passportDraw_page"
             data-url-del="${ctx}/user/passportDraw_del"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">

            <div class="space-4"></div>
            <c:if test="${commonList.recNum>0}">
                <table class="table table-actived table-striped table-bordered table-hover table-condensed">
                    <thead>

                    <tr>
                            <th>申请日期</th>
                    <c:if test="${type!=PASSPORT_DRAW_TYPE_TW}">
                            <th>申请领取证件名称</th>
                    </c:if>
                            <c:if test="${type==PASSPORT_DRAW_TYPE_SELF}">
							<th>因私出国（境）行程</th>
                            </c:if>
                             <th>是否签注</th>
                            <c:if test="${type==PASSPORT_DRAW_TYPE_TW}">
							<th>出行时间</th>
							<th>回国时间</th>
							<th>出行天数</th>
							<th>出访事由</th>
                            </c:if>
                        <c:if test="${type==PASSPORT_DRAW_TYPE_OTHER}">
                            <th>使用时间</th>
                            <th>归还时间</th>
                            <th>使用天数</th>
                            <th>事由</th>
                        </c:if>
                        <th>审批状态</th>
                        <th>应交组织部日期</th>
                        <th>实交组织部日期</th>
                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${passportDraws}" var="passportDraw" varStatus="st">
                        <c:set var="cadre" value="${cadreMap.get(passportDraw.cadreId)}"/>
                        <c:set var="sysUser" value="${cm:getUserById(cadre.userId)}"/>
                        <c:set var="passport" value="${cm:getPassport(passportDraw.passportId)}"/>
                        <c:set var="passportType" value="${cm:getMetaType('mc_passport_type', passport.classId)}"/>
                        <tr>
                            <td>${cm:formatDate(passportDraw.applyDate,'yyyy-MM-dd')}</td>
                            <c:if test="${type!=PASSPORT_DRAW_TYPE_TW}">
                                <td>${passportType.name}</td>
                            </c:if>
                            <c:if test="${type==PASSPORT_DRAW_TYPE_SELF}">
								<td>
                                    <a class="openView" href="javascript:;"
                                       data-url="${ctx}/user/applySelf_view?id=${passportDraw.applyId}"> S${passportDraw.applyId}</a>
                                   </td>
                            </c:if>
                            <td>${passportDraw.needSign?"是":"否"}</td>
                                <c:if test="${type!=PASSPORT_DRAW_TYPE_SELF}">
								<td>${cm:formatDate(passportDraw.startDate,'yyyy-MM-dd')}</td>
								<td>${cm:formatDate(passportDraw.endDate,'yyyy-MM-dd')}</td>
								<td>${cm:getDayCountBetweenDate(passportDraw.startDate, passportDraw.endDate)}</td>
								<td>${fn:replace(passportDraw.reason, '+++', ',')}</td>
                                </c:if>
                                <td>${PASSPORT_DRAW_STATUS_MAP.get(passportDraw.status)}</td>
                            <td>${cm:formatDate(passportDraw.returnDate,'yyyy-MM-dd')}</td>
                            <td>${cm:formatDate(passportDraw.realReturnDate,'yyyy-MM-dd')}</td>

                            <td>
                                <div class="hidden-sm hidden-xs action-buttons">
                                    <c:if test="${type!=PASSPORT_DRAW_TYPE_OTHER}">
                                        <c:if test="${passportType.code != 'mt_passport_normal' && passportDraw.needSign}">
                                            <button data-url="${ctx}/user/passportDraw_self_sign?type=view&passportId=${passportDraw.passportId}"
                                                    class="openView btn btn-success btn-mini btn-xs" data-id="${passportDraw.id}">
                                                <i class="fa fa-eye"></i> 签注申请表
                                            </button>
                                            <button class="printBtn btn btn-info btn-mini btn-xs" data-id="${passportDraw.id}">
                                                <i class="fa fa-print"></i> 打印签注申请表
                                            </button>
                                        </c:if>
                                        <c:if test="${passportType.code != 'mt_passport_normal' && !passportDraw.needSign}">
                                            <button data-url="${ctx}/user/passportDraw_self_sign?type=add&id=${passportDraw.id}&passportId=${passportDraw.passportId}"
                                                    class="openView btn btn-default btn-mini btn-xs">
                                                <i class="fa fa-plus"></i> 办理签注
                                            </button>
                                        </c:if>
                                    </c:if>
                                    <c:if test="${passportDraw.status==0}">
                                        <button class="delBtn btn btn-danger btn-mini btn-xs" data-id="${passportDraw.id}">
                                            <i class="fa fa-times"></i> 删除
                                        </button>
                                    </c:if>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <wo:page commonList="${commonList}" uri="${ctx}/passportDraw_page" target="#page-content" pageNum="5"
                         model="3"/>
            </c:if>
            <c:if test="${commonList.recNum==0}">
                <div class="well well-lg center">
                    <h4 class="green lighter">暂无记录</h4>
                </div>
            </c:if>
        </div>
    </div>
    </div></div></div>
    <div id="item-content">
    </div>
    </div>
</div>
<script>
    $(".printBtn").click(function(){
        printWindow("${ctx}/report/passportSign?id="+ $(this).data("id"));
    });
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>