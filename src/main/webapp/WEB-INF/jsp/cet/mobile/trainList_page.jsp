<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/cet/constants.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content">
            <div class="row">
                <div class="col-xs-12">
                    <div class="alert alert-block alert-success" style="font-size: 18px;font-weight: bolder;margin-bottom: 10px">
                        <i class="ace-icon fa fa-book green"></i>
                        &nbsp;选课中心
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-8 infobox-container">
                        <c:forEach items="${trains}" var="entity">
                            <div class="infobox ${entity.courseCount>0?'infobox-success':'infobox-blue2'}">
                                <div class="infobox-data" style="width: 100%">
                                    <table class="course-list" style="width: 100%">
                                        <tr>
                                            <td class="name" colspan="2">
                                                    ${entity.name}
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                            <span style="font-size: 10pt;">
                                                    ${cm:formatDate(entity.startDate, "MM.dd")}~${cm:formatDate(entity.endDate, "MM.dd")}
                                                &nbsp;&nbsp;${cm:formatDate(entity.endTime, "yyyy-MM-dd HH:mm")}
                                        </span>
                                            </td>
                                            <td class="status">
                                                <button class="openView btn btn-${entity.courseCount>0?'primary':'success'} btn-xs" data-open-by="page"
                                                        data-url="${ctx}/m/cet/courseList_page?trainId=${entity.id}">
                                                    <i class="fa fa-sign-in"></i> 进入
                                                </button>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                            </div>
                        </c:forEach>
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