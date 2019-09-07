<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-body">
    <div class="widget-box transparent" id="view-box">
        <div class="widget-header">
            <h4 class="widget-title lighter smaller">
                <a href="javascript:;" class="hideView btn btn-xs btn-success">
                    <i class="ace-icon fa fa-backward"></i>
                    返回</a>
            </h4>
            <div class="widget-toolbar no-border">
                <ul class="nav nav-tabs">
                    <li class="active">
                        <a href="javascript:;">意见和建议详情</a>
                    </li>
                </ul>
            </div>
        </div>
        <div class="widget-body">
            <div class="widget-main padding-4">
                <div class="tab-content padding-8">
                    <div class="row">
                        <div class="col-xs-12 col-sm-10 col-sm-offset-1">
                            <div class="timeline-container timeline-style2">
                                <div class="alert alert-block alert-success" style="overflow: hidden">
								    <b>${feedbacks[0].title}</b>
                                    <button class="popupBtn btn btn-info btn-sm pull-right"  data-url="${ctx}/feedback_au?fid=${feedbacks[0].id}"><i class="fa fa-plus"></i> 添加回复</button>
								</div>
                                <div class="timeline-items">
                                    <c:forEach items="${feedbacks}" var="feedback" varStatus="vs">
                                    <div class="timeline-item clearfix">
                                        <div class="timeline-info">
                                            <span class="timeline-date">${cm:formatDate(feedback.createTime, "yyyy.MM.dd HH:mm")}</span>

                                            <i class="timeline-indicator btn btn-success no-hover"></i>
                                        </div>
                                        <div class="widget-box transparent">
                                            <div class="widget-body">
                                                <div class="widget-main no-padding">
                                                    <div class="clearfix">
                                                        <div class="pull-left">
                                                            <span class="purple bolder">[${feedback.user.realname}]</span> ${feedback.content}
                                                        </div>
                                                        <div class="pull-right">
                                                            <c:if test="${not empty feedback.pics}">

                                                            <i class="ace-icon fa fa-chevron-left blue bigger-110"></i>
                                                            &nbsp;
                                                             <c:forEach items="${fn:split(feedback.pics, ',')}" var="pic" varStatus="picIdx">
                                                                 <a class="various" rel="group_${feedback.id}"
                                                                    title="图片${picIdx.count}" data-path="${cm:encodeURI(pic)}"
                                                                    data-fancybox-type="image" href="${ctx}/pic?path=${pic}">
                                                                     <img alt="Image ${picIdx.count}" width="36"
                                                                         src="${ctx}/pic?path=${cm:getShortPic(pic)}"/>
                                                                 </a>
                                                            </c:forEach>
                                                            &nbsp;
                                                            <i class="ace-icon fa fa-chevron-right blue bigger-110"></i>
                                                            </c:if>
                                                            <c:if test="${vs.index>0}">
                                                                <c:if test="${cm:isPermitted('feedback:edit') || feedback.selfCanEdit}">
                                                                    <a class="popupBtn btn btn-primary btn-xs"
                                                                       data-url="${ctx}/feedback_au?id=${feedback.id}"><i class="fa fa-edit"></i></a>
                                                                </c:if>
                                                                <c:if test="${cm:isPermitted('feedback:del') || feedback.selfCanEdit}">
                                                                    <button data-url="${ctx}/feedback_del?id=${feedback.id}"
                                                                        data-title="删除"
                                                                        data-msg="确定删除？"
                                                                        data-callback="_reloadDetail"
                                                                        class="confirm btn btn-danger btn-xs">
                                                                         <i class="fa fa-times"></i></button>
                                                                </c:if>
                                                            </c:if>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    </c:forEach>
                                </div><!-- /.timeline-items -->
                            </div><!-- /.timeline-container -->
                        </div>
                    </div>
                </div>
            </div><!-- /.widget-main -->
        </div><!-- /.widget-body -->
    </div><!-- /.widget-box -->
</div>
<style>
    a:hover{
        text-underline: none;
    }
</style>
<script>
function _reloadDetail(){
    $.openView("${ctx}/feedback_detail?id=${param.id}")
    if (typeof _reloadList != 'undefined'
        && _reloadList instanceof Function) {
        _reloadList();
    }
}
$.register.fancybox();
</script>

