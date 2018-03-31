<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row" style="width: 1075px">
    <div class="col-xs-12">

        <ul id="detail-ul" class="nav nav-tabs padding-12 tab-color-blue background-blue">

            <li class="active">
                <a href="javascript:;" class="loadPage"
                   data-load-el="#detail-content" data-callback="$.menu.liSelected"
                   data-url="${ctx}/user/crsPost_apply?postId=${param.postId}&type=detail">
                    <i class="green ace-icon fa fa-bullhorn bigger-120"></i> 应聘材料</a>
            </li>
            <li>
                <a href="javascript:;" class="loadPage"
                   data-load-el="#detail-content" data-callback="$.menu.liSelected"
                   data-url="${ctx}/user/crsPost_apply_notice?postId=${param.postId}">
                    <i class="green ace-icon fa fa-calendar bigger-120"></i> 招聘会通告</a>
            </li>
            <div class="pull-right" style="padding: 2px 10px;">
            <a href="javascript:" class="hideView btn btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>
            </div>
        </ul>
        <div class="col-xs-12" id="detail-content">
            <c:import url="${ctx}/user/crsPost_apply"/>
        </div>
        <div style="clear: both"></div>
    </div>
</div>