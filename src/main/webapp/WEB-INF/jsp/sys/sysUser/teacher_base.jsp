<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="widget-box transparent">
    <div class="widget-header widget-header-flat">
        <h4 class="widget-title lighter">
            <i class="ace-icon fa fa-paw blue "></i>
            基本信息
        </h4>
        <div class="widget-toolbar">
            <a href="javascript:;" data-action="collapse">
                <i class="ace-icon fa fa-chevron-up"></i>
            </a>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main no-padding">
            <jsp:include page="/ext/teacher_info_table.jsp"/>
        </div>
    </div>
</div>
<div class="widget-box transparent">
    <div class="widget-header widget-header-flat">
        <h4 class="widget-title lighter">
            <i class="ace-icon fa fa-info-circle blue"></i>
            人事信息
        </h4>

        <div class="widget-toolbar">
            <a href="javascript:;" data-action="collapse">
                <i class="ace-icon fa fa-chevron-up"></i>
            </a>
        </div>
    </div>

    <div class="widget-body">
        <div class="widget-main no-padding">
            <table class="table table-unhover table-bordered table-striped">
                <tbody>
                <tr>
                    <td>
                        所在单位
                    </td>
                    <td style="min-width: 80px">
                        ${uv.unit}
                    </td>
                     <td>
                        专业技术职务
                    </td>
                    <td>
                        ${teacherInfo.proPost}
                    </td>
                    <td>参加工作时间</td>
                    <td colspan="3">
                        ${cm:formatDate(teacherInfo.workTime, "yyyy.MM")}
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>