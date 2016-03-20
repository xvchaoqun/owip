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
                        <div class="widget-box transparent">
                            <div class="widget-header widget-header-flat">
                                <h4 class="widget-title lighter">
                                    <i class="ace-icon fa fa-info-circle"></i>
                                    集中管理证件
                                </h4>

                                <div class="widget-toolbar">
                                    <a href="#" data-action="collapse">
                                        <i class="ace-icon fa fa-chevron-up"></i>
                                    </a>
                                </div>
                            </div>
                            <div class="widget-body">
                                <div class="widget-main no-padding">
                                    <table class="table table-striped table-bordered table-hover">
                                        <thead>
                                        <tr>
                                            <th>证件名称</th>
                                            <th>证件号码</th>
                                            <th>发证机关</th>
                                            <th style="width: 100px">发证日期</th>
                                            <th style="width: 100px">有效期</th>
                                            <th style="width: 120px">集中保管日期</th>
                                            <th>是否借出</th>
                                            <th></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${keepPassports}" var="passport" varStatus="st">
                                            <tr>
                                                <td>${passportTypeMap.get(passport.classId).name}</td>
                                                <td>${passport.code}</td>
                                                <td>${passport.authority}</td>
                                                <td>${cm:formatDate(passport.issueDate,'yyyy-MM-dd')}</td>
                                                <td>${cm:formatDate(passport.expiryDate,'yyyy-MM-dd')}</td>
                                                <td>${cm:formatDate(passport.keepDate,'yyyy-MM-dd')}</td>
                                                <td>${passport.isLent?"借出":"-"}</td>
                                                <td><a class="openView btn btn-info btn-xs"
                                                       data-url="${ctx}/user/passport_useLogs?type=user&id=${passport.id}">
                                                    <i class="fa fa-history"></i> 使用记录
                                                </a></td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>

                        <div class="widget-box transparent">
                            <div class="widget-header widget-header-flat">
                                <h4 class="widget-title lighter">
                                    <i class="ace-icon fa fa-info-circle"></i>
                                    取消集中管理证件
                                </h4>

                                <div class="widget-toolbar">
                                    <a href="#" data-action="collapse">
                                        <i class="ace-icon fa fa-chevron-up"></i>
                                    </a>
                                </div>
                            </div>
                            <div class="widget-body">
                                <div class="widget-main no-padding">
                                    <table class="table table-striped table-bordered table-hover">
                                        <thead>
                                        <tr>
                                            <th>证件名称</th>
                                            <th>证件号码</th>
                                            <th>发证机关</th>
                                            <th style="width: 100px">发证日期</th>
                                            <th style="width: 100px">有效期</th>
                                            <th style="width: 120px">集中保管日期</th>
                                            <th>取消集中保管原因</th>
                                            <th  style="width: 150px">取消集中保管日期</th>
                                            <th nowrap></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${cancelPassports}" var="passport" varStatus="st">
                                            <tr>
                                                <td>${passportTypeMap.get(passport.classId).name}</td>
                                                <td>${passport.code}</td>
                                                <td>${passport.authority}</td>
                                                <td>${cm:formatDate(passport.issueDate,'yyyy-MM-dd')}</td>
                                                <td>${cm:formatDate(passport.expiryDate,'yyyy-MM-dd')}</td>
                                                <td>${cm:formatDate(passport.keepDate,'yyyy-MM-dd')}</td>
                                                <td>${PASSPORT_CANCEL_TYPE_MAP.get(passport.cancelType)}</td>
                                                <td>${cm:formatDate(passport.cancelTime,'yyyy-MM-dd')}</td>
                                                <td>
                                                    <a class="openView btn btn-info btn-xs"
                                                       data-url="${ctx}/user/passport_useLogs?type=user&id=${passport.id}">
                                                        <i class="fa fa-history"></i> 使用记录
                                                    </a>
                                                    <c:if  test="${passport.cancelConfirm}">
                                                        <a class="openView btn btn-success btn-xs"
                                                           data-url="${ctx}/user/passport_cancel?id=${passport.id}">
                                                            <i class="fa fa-check-circle-o"></i> 取消集中管理证明
                                                        </a>
                                                    </c:if>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>

                        <div class="widget-box transparent">
                            <div class="widget-header widget-header-flat">
                                <h4 class="widget-title lighter">
                                    <i class="ace-icon fa fa-info-circle"></i>
                                    丢失的证件
                                </h4>
                                <div class="widget-toolbar">
                                    <a href="#" data-action="collapse">
                                        <i class="ace-icon fa fa-chevron-up"></i>
                                    </a>
                                </div>
                            </div>
                            <div class="widget-body">
                                <div class="widget-main no-padding">
                                    <table class="table table-striped table-bordered table-hover">
                                        <thead>
                                        <tr>
                                            <th>证件名称</th>
                                            <th>证件号码</th>
                                            <th>发证机关</th>
                                            <th style="width: 100px">发证日期</th>
                                            <th style="width: 100px">有效期</th>
                                            <th style="width: 120px">集中保管日期</th>
                                            <th style="width: 120px">登记丢失日期</th>
                                            <th></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${lostPassports}" var="passport" varStatus="st">
                                            <tr>
                                                <td>${passportTypeMap.get(passport.classId).name}</td>
                                                <td>${passport.code}</td>
                                                <td>${passport.authority}</td>
                                                <td>${cm:formatDate(passport.issueDate,'yyyy-MM-dd')}</td>
                                                <td>${cm:formatDate(passport.expiryDate,'yyyy-MM-dd')}</td>
                                                <td>${cm:formatDate(passport.keepDate,'yyyy-MM-dd')}</td>
                                                <td>${cm:formatDate(passport.lostTime,'yyyy-MM-dd')}</td>
                                                <td><a class="openView btn btn-info btn-xs"
                                                       data-url="${ctx}/user/passport_useLogs?type=user&id=${passport.id}">
                                                    <i class="fa fa-history"></i> 使用记录
                                                </a>
                                                    <a class="btn btn-info btn-xs"
                                                       href="${ctx}/user/passport_lostProof_download?id=${passport.id}"
                                                       target="_blank">丢失证明</a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="item-content">

        </div>
    </div>
</div>