<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<div class="row" id="cartogram">
    <div class="row">
        <div class="col-xs-12">
            <div class="widget-box transparent">
                <div class="widget-header widget-header-flat">
                    <h4 class="widget-title lighter">
                        <i class="ace-icon fa fa-star green"></i>
                        处级干部基本信息统计
                    </h4>
                    <div class="widget-toolbar">
                        <a href="javascript:;" data-action="collapse">
                            <i class="ace-icon fa fa-chevron-up"></i>
                        </a>
                    </div>
                </div>
                <div class="widget-body">
                    <div class="widget-main padding-4" >

                        <c:import url="/stat_cadre_count?cadreCategory=1"/>
                        <c:import url="/stat_cadreOther_count?type=1&cadreCategory=1"/>
                        <c:import url="/stat_cadreAge_count?cadreCategory=1"/>
                        <c:import url="/stat_cadreOther_count?type=2&cadreCategory=1"/>
                        <c:import url="/stat_cadreDp_count?cadreCategory=1"/>
                        <c:import url="/stat_cadrePost_count?cadreCategory=1"/>
                        <c:import url="/stat_cadreDegree_count?cadreCategory=1"/>
                        <c:import url="/stat_cadreEdu_count?cadreCategory=1"/>
                    </div><!-- /.widget-main -->
                </div><!-- /.widget-body -->
            </div><!-- /.widget-box -->
        </div><!-- /.col -->
    </div>
<c:if test="${_p_hasKjCadre}">
    <div class="row">
        <div class="col-xs-12">
            <div class="widget-box transparent">
                <div class="widget-header widget-header-flat">
                    <h4 class="widget-title lighter">
                        <i class="ace-icon fa fa-star green"></i>
                        科级干部基本信息统计
                    </h4>
                    <div class="widget-toolbar">
                        <a href="javascript:;" data-action="collapse">
                            <i class="ace-icon fa fa-chevron-up"></i>
                        </a>
                    </div>
                </div>
                <div class="widget-body">
                    <div class="widget-main padding-4">
                        <c:import url="/stat_cadre_count?cadreCategory=2"/>
                        <c:import url="/stat_cadreOther_count?type=1&cadreCategory=2"/>
                        <c:import url="/stat_cadreAge_count?cadreCategory=2"/>
                        <c:import url="/stat_cadreOther_count?type=2&cadreCategory=2"/>
                        <c:import url="/stat_cadreDp_count?cadreCategory=2"/>
                        <c:import url="/stat_cadrePost_count?cadreCategory=2"/>
                        <c:import url="/stat_cadreDegree_count?cadreCategory=2"/>
                        <c:import url="/stat_cadreEdu_count?cadreCategory=2"/>
                    </div><!-- /.widget-main -->
                </div><!-- /.widget-body -->
            </div><!-- /.widget-box -->
        </div><!-- /.col -->
    </div>
</c:if>

    <div class="row">
        <div class="col-xs-12">
            <div class="widget-box transparent">
                <div class="widget-header widget-header-flat">
                    <h4 class="widget-title lighter">
                        <i class="ace-icon fa fa-star green"></i>
                        各级别平均年龄
                    </h4>
                    <div class="widget-toolbar">
                        <a href="javascript:;" data-action="collapse">
                            <i class="ace-icon fa fa-chevron-up"></i>
                        </a>
                    </div>
                </div>

                <div class="widget-body">
                    <div class="widget-main padding-4">
                        <c:import url="/stat_cadre_avgAge?cadreCategory=1"/>
                       <%-- <c:if test="${_p_hasKjCadre}">
                        <c:import url="/stat_cadre_avgAge?cadreCategory=2"/>
                        </c:if>--%>
                    </div><!-- /.widget-main -->
                </div><!-- /.widget-body -->
            </div><!-- /.widget-box -->
        </div><!-- /.col -->
    </div>
</div>