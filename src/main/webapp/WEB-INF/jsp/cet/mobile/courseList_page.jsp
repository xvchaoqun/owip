<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/cet/constants.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div class="row">
            <div class="col-xs-12">
                <div class="alert alert-block alert-success" style="font-size: 18px;font-weight: bolder">
                    <i class="ace-icon fa fa-hourglass-1 green"></i>
                    &nbsp;&nbsp;选课中心
                </div>
            </div>
            <div class="row">
                <div class="col-sm-8 infobox-container">
                    <c:forEach items="${trains}" var="entity">
                        <div class="infobox ${entity.courseCount>0?'infobox-success':'infobox-blue2'}">
                            <div class="infobox-data" style="width: 100%">
                                    <table class="course-list" style="width: 100%">
                                    <tr>
                                        <td class="name ${entity.courseCount>0?'finish':''}">
                                            ${entity.name}
                                        </td>
                                        <td class="status">
                                            <button class="ahref btn btn-success btn-xs"
                                                    data-url="${ctx}/m/cet_eva/eva?trainCourseId=${tc.id}">
                                                <i class="fa fa-hand-o-right"></i> ${entity.courseCount>0?'进入':'选课'}</button>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="2">
                                            <span style="font-size: 10pt;">
                                                    ${cm:formatDate(entity.startDate, "yyyy-MM-dd")}~${cm:formatDate(entity.endDate, "yyyy-MM-dd")}
                                                &nbsp;&nbsp;${cm:formatDate(entity.endTime, "yyyy-MM-dd HH:mm")}
                                        </span>
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
</div>
<style>
    .infobox{
        height: auto;
    }
    .course-list .name{
        font-weight: bold;
        color: black;
    }
    .course-list .name.finish{
        color: grey;
    }
    .course-list .status{
        text-align: right;
        padding-left:10px;
        white-space: nowrap;
    }
</style>