<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content">
            <div class="tabbable">
                <jsp:include page="menu.jsp"/>
                <div class="tab-content">
                    <div id="home4" class="tab-pane in active">
                        <div class="row dispatch_au">
                            <div class="preview">
                                <div class="widget-box">
                                    <div class="widget-header">
                                        <h4 class="smaller">
                                            干部工作小组会成立文件
                                            <div class="buttons pull-right" style="padding-right: 10px;">
                                                <button type="button"
                                                        data-url="${ctx}/sc/scGroupFile_page"
                                                        class="popupBtn btn btn-xs btn-primary">
                                                    <i class="ace-icon fa fa-upload"></i>
                                                    管理文件
                                                </button>
                                            </div>
                                        </h4>
                                    </div>
                                    <div class="widget-body">
                                        <div class="widget-main">
                                            <div id="dispatch-file-view">
                                                <c:import url="${ctx}/swf/preview?type=html&path=${scGroupFile.filePath}"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="au">
                                <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                                    <li class="active">
                                        <a href="javascript:;" class="loadPage"
                                           data-load-el="#inner-body-content-view" data-callback="$.menu.liSelected"
                                           data-url='${ctx}/sc/scGroupMember?isCurrent=1'><i
                                                class="fa fa-bullhorn"></i> 小组会现有成员
                                        </a>
                                    </li>
                                    <li>
                                        <a href="javascript:;" class="loadPage"
                                           data-load-el="#inner-body-content-view" data-callback="$.menu.liSelected"
                                           data-url='${ctx}/sc/scGroupMember?isCurrent=0'><i
                                                class="fa fa-calendar-o"></i> 小组会过去成员
                                        </a>
                                    </li>
                                </ul>
                                <div class="space-4"></div>
                                <div class="col-xs-12" id="inner-body-content-view" <%--style="min-height: 500px;"--%>>

                                    <c:import url="${ctx}/sc/scGroupMember"/>
                                </div>
                                <div style="clear: both"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>