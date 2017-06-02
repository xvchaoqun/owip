<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

            <div class="col-xs-12" id="stat_online_day_div">
                <div class="widget-box">
                    <div class="widget-header widget-header-flat widget-header-small">
                        <h5 class="widget-title">
                            <i class="ace-icon fa fa-signal"></i>
                            每日最高在线人数统计
                        </h5>
                        <div class="widget-toolbar no-border">
                            <div class="inline dropdown-hover">
                                <button class="btn btn-xs btn-info">
                                    ${type==1?"最近三个月":(type==2)?"最近半年":"最近一年"}
                                    <i class="ace-icon fa fa-angle-down icon-on-right bigger-110"></i>
                                </button>

                                <ul class="dropdown-menu dropdown-menu-right dropdown-125 dropdown-lighter dropdown-close dropdown-caret">
                                    <li class="${type==1?'active':''}" data-type="1">
                                        <a href="javascript:;" class="blue">
                                            <i class="ace-icon fa fa-caret-right bigger-110 ${type==1?'':'invisible'}">
                                                &nbsp;</i>
                                            最近三个月
                                        </a>
                                    </li>
                                    <li class="${type==2?'active':''}" data-type="2">
                                        <a href="javascript:;" class="blue">
                                            <i class="ace-icon fa fa-caret-right bigger-110 ${type==2?'':'invisible'}">
                                                &nbsp;</i>
                                            最近半年
                                        </a>
                                    </li>
                                    <li class="${type==3?'active':''}" data-type="3">
                                        <a href="javascript:;" class="blue">
                                            <i class="ace-icon fa fa-caret-right bigger-110 ${type==3?'':'invisible'}">
                                                &nbsp;</i>
                                            最近一年
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="widget-body">
                        <div class="widget-main">
                            <c:import url="/stat_online_day"/>
                        </div>
                    </div>
                </div>
            </div>
<script>
    $(function () {
        $("#stat_online_day_div ul li").click(function () {
            $.get("${ctx}/stat_sys_page", {type: $(this).data('type')}, function (html) {
                $("#stat_online_day_div").replaceWith(html);
            });
        });
    });
</script>