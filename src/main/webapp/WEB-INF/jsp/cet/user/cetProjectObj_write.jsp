<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/cet/constants.jsp" %>
<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="openView btn btn-xs btn-success"
               data-url="${ctx}/user/cet/cetProjectPlan?projectId=${cetProjectPlan.projectId}">
                <i class="ace-icon fa fa-backward"></i> 返回</a>
        </h4>
        <span class="text text-info bolder" style="cursor: auto;padding-left: 20px;">
                    ${CET_PROJECT_PLAN_TYPE_MAP.get(cetProjectPlan.type)}
                    （${cm:formatDate(cetProjectPlan.startDate, "yyyy-MM-dd")} ~ ${cm:formatDate(cetProjectPlan.endDate, "yyyy-MM-dd")}，${cetProject.name}）
        </span>

        <div class="widget-toolbar no-border">
            <ul class="nav nav-tabs" id="detail-ul">
                <li class="active">
                    <a href="javascript:;">上传心得体会</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="widget-body">
        <div class="rownumbers widget-main padding-12 no-padding-left no-padding-right no-padding-bottom">
            <div class="widget-box" style="width:300px">
                <div class="widget-header">
                    <h4 class="smaller">
                        <i class="fa fa-edit"></i> 上传心得体会
                    </h4>
                </div>
                <div class="widget-body">
                    <div class="widget-main center">
                        <button class="popupBtn btn btn-primary btn-sm tooltip-success"
                                data-url="${ctx}/user/cet/cetProjectObj_uploadWrite?id=${cetProjectObj.id}&planId=${cetProjectPlan.id}">
                            <i class="fa fa-upload"></i> ${not empty cetProjectObj.writeFilePath?"重新":""}上传
                        </button>

                        <c:if test="${not empty cetProjectObj.writeFilePath}">

                        <div style="text-align: left;padding: 20px 0 10px 0;">已上传心得体会：</div>

                                        <button class='linkBtn btn btn-sm btn-success'
                                                data-url='${ctx}/attach/download?path=${cm:encodeURI(cetProjectObj.writeFilePath)}&filename=心得体会'>
                                            <i class="fa fa-download"></i>
                                            下载
                                        </button>


                        </c:if>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>

