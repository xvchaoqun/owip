<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!-- PAGE CONTENT BEGINS -->
<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="closeView btn btn-mini btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>
        </h4>
        <div class="widget-toolbar no-border">
            <ul class="nav nav-tabs">
                <li class="active">
                    <a href="javascript:;">保险柜编号：${safeBoxMap.get(cm:parseInt(param.safeBoxId)).code}</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main padding-4">
            <div class="tab-content padding-8">
                <div class="table-container">
                    <table style="min-width: 2000px" class="table table-actived table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <th class="center">
                                <label class="pos-rel">
                                    <input type="checkbox" class="ace checkAll">
                                    <span class="lbl"></span>
                                </label>
                            </th>
                            <th>工作证号</th>
                            <th>姓名</th>
                            <th>所在单位及职务</th>
                            <th>证件名称</th>
                            <th>证件号码</th>
                            <th>发证日期</th>
                            <th>有效期</th>
                            <th>集中保管日期</th>
                            <th>证件状态</th>
                            <th>是否借出</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${passports}" var="passport" varStatus="st">
                            <c:set var="cadre" value="${cadreMap.get(passport.cadreId)}"/>
                            <c:set var="sysUser" value="${cm:getUserById(cadre.userId)}"/>
                            <tr>
                                <td class="center">
                                    <label class="pos-rel">
                                        <input type="checkbox" value="${passport.id}" class="ace">
                                        <span class="lbl"></span>
                                    </label>
                                </td>
                                <td>${sysUser.code}</td>
                                <td><a href="javascript:;" class="openView" data-url="${ctx}/cadre_view?id=${passport.cadreId}">
                                        ${sysUser.realname}
                                </a></td>
                                <td style="text-align: left">${cadre.title}</td>
                                <td>${passportTypeMap.get(passport.classId).name}</td>
                                <td>${passport.code}</td>
                                <td>${cm:formatDate(passport.issueDate,'yyyy-MM-dd')}</td>
                                <td>${cm:formatDate(passport.expiryDate,'yyyy-MM-dd')}</td>
                                <td>${cm:formatDate(passport.keepDate,'yyyy-MM-dd')}</td>
                                <td>${PASSPORT_TYPE_MAP.get(passport.type)}</td>
                                <td>${passport.isLent?"借出":"-"}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <wo:page commonList="${commonList}" uri="${ctx}/safeBoxPassportList" target="#item-content" pageNum="5"
                         model="3"/>
            </div>
        </div><!-- /.widget-main -->
    </div><!-- /.widget-body -->
</div><!-- /.widget-box -->
<style>
    table thead tr th, table tbody tr td {
        text-align: center;
    !important;
    }
</style>