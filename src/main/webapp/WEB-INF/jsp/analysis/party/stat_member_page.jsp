<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

     <div class="row">
        <div class="col-xs-12">

          <%--<div class="alert alert-block alert-success">
            <button type="button" class="close" data-dismiss="alert">
              <i class="ace-icon fa fa-trash"></i>
            </button>

            <i class="ace-icon fa fa-check green"></i>
            欢迎使用${_plantform_name}
          </div>--%>
<shiro:lacksRole name="${ROLE_ONLY_CADRE_VIEW}">
          <div class="row">
            <div class="col-sm-12">
              <div class="widget-box transparent">
                <div class="widget-header widget-header-flat">
                  <h4 class="widget-title lighter">
                    <i class="ace-icon fa fa-hourglass-end orange"></i>
                    待处理事项
                  </h4>
                  <div class="widget-toolbar">
                    <a href="javascript:;" data-action="collapse">
                      <i class="ace-icon fa fa-chevron-up"></i>
                    </a>
                  </div>
                </div>
                <div class="widget-body">
                  <div class="widget-main padding-4">
                    <div class="col-sm-12">

<c:if test="${studentGrowOdCheckCount+teacherGrowOdCheckCount>0}">
                      <div class="infobox infobox-pink">
                        <div class="infobox-icon">
                          <i class="ace-icon fa fa-hand-lizard-o"></i>
                        </div>
                        <div class="infobox-data">
                          <span class="infobox-data-number">
<c:if test="${studentGrowOdCheckCount>0}">
                            <span data-url="${ctx}/memberApply_layout?stage=${OW_APPLY_STAGE_DRAW}&type=${OW_APPLY_TYPE_STU}&growStatus=-1">
                            <span class="count">${studentGrowOdCheckCount}</span><span style="font-size: 10pt;">学生</span></span>
  </c:if>
<c:if test="${teacherGrowOdCheckCount>0}">
                             <span data-url="${ctx}/memberApply_layout?stage=${OW_APPLY_STAGE_DRAW}&type=${OW_APPLY_TYPE_TEACHER}&growStatus=-1">
                            <span class="count">${teacherGrowOdCheckCount}</span><span style="font-size: 10pt;">教职工</span></span>
  </c:if>
                            </span>
                          <div class="infobox-content">领取志愿书审批</div>
                        </div>
                      </div>
</c:if>
<c:if test="${studentPositiveOdCheckCount+teacherPositiveOdCheckCount>0}">
                      <div class="infobox infobox-red">
                        <div class="infobox-icon">
                          <i class="ace-icon fa fa-star"></i>
                        </div>
                        <div class="infobox-data">
                          <span class="infobox-data-number">
<c:if test="${studentPositiveOdCheckCount>0}">
                            <span data-url="${ctx}/memberApply_layout?stage=${OW_APPLY_STAGE_GROW}&type=${OW_APPLY_TYPE_STU}&positiveStatus=1">
                            <span class="count">${studentPositiveOdCheckCount}</span><span style="font-size: 10pt;">学生</span></span>
  </c:if>
<c:if test="${teacherPositiveOdCheckCount>0}">
                             <span data-url="${ctx}/memberApply_layout?stage=${OW_APPLY_STAGE_GROW}&type=${OW_APPLY_TYPE_TEACHER}&positiveStatus=1">
                            <span class="count">${teacherPositiveOdCheckCount}</span><span style="font-size: 10pt;">教职工</span></span>
</c:if>
                            </span>
                          <div class="infobox-content">预备党员转正审批</div>
                        </div>
                      </div>
  </c:if>

                      <div class="infobox infobox-blue" <c:if test="${memberOutCount>0}">data-url="${ctx}/memberOut?cls=6"</c:if>>
                        <div class="infobox-icon">
                          <i class="ace-icon fa fa-sign-out"></i>
                        </div>
                        <div class="infobox-data">
                          <span class="infobox-data-number"><span class="count">${memberOutCount}</span> <span style="font-size: 10pt;">待审</span></span>

                          <div class="infobox-content">组织关系转出审批</div>
                        </div>
                      </div>
                      <div class="infobox infobox-green" <c:if test="${memberInCount>0}">data-url="${ctx}/memberIn?cls=4"</c:if>>
                        <div class="infobox-icon">
                          <i class="ace-icon fa fa-sign-in"></i>
                        </div>

                        <div class="infobox-data">
                          <span class="infobox-data-number"><span class="count">${memberInCount}</span> <span style="font-size: 10pt;">未处理</span> </span>

                          <div class="infobox-content">
                            组织关系转入审批
                          </div>
                        </div>
                      </div>

                      <div class="infobox infobox-blue2" <c:if test="${memberStayCount_abroad>0}">data-url="${ctx}/memberStay?type=${MEMBER_STAY_TYPE_ABROAD}&cls=3"</c:if>>
                        <div class="infobox-icon">
                          <i class="ace-icon fa fa-plane"></i>
                        </div>
                        <div class="infobox-data">
                          <span class="infobox-data-number"><span class="count">${memberStayCount_abroad}</span> <span style="font-size: 10pt;">未处理</span></span>
                          <div class="infobox-content">${MEMBER_STAY_TYPE_MAP.get(MEMBER_STAY_TYPE_ABROAD)}暂留</div>
                        </div>
                      </div>
                        <div class="infobox infobox-blue2" <c:if test="${memberStayCount_internal>0}">data-url="${ctx}/memberStay?type=${MEMBER_STAY_TYPE_INTERNAL}&cls=3"</c:if>>
                            <div class="infobox-icon">
                                <i class="ace-icon fa fa-plane"></i>
                            </div>
                            <div class="infobox-data">
                                <span class="infobox-data-number"><span class="count">${memberStayCount_internal}</span> <span style="font-size: 10pt;">未处理</span></span>
                                <div class="infobox-content">${MEMBER_STAY_TYPE_MAP.get(MEMBER_STAY_TYPE_INTERNAL)}暂留</div>
                            </div>
                        </div>
                    </div>
                  </div><!-- /.widget-main -->
                </div><!-- /.widget-body -->
              </div><!-- /.widget-box -->

            </div>
            <!-- /.col -->
          </div>
    </shiro:lacksRole>
          <!-- /.row -->

          <div class="row">
            <div class="col-xs-12">
              <div class="widget-box transparent">
                <div class="widget-header widget-header-flat">
                  <h4 class="widget-title lighter">
                    <i class="ace-icon fa fa-star green"></i>
                    党员基本信息统计
                  </h4>
                  <div class="widget-toolbar">
                      <a href="javascript:;" data-action="collapse">
                          <i class="ace-icon fa fa-chevron-up"></i>
                      </a>
                  </div>
                </div>
                <div class="widget-body">
                  <div class="widget-main padding-4">
                    <div class="row">
                      <c:import url="/stat_member_count"/>
                      <c:import url="/stat_member_age"/>
                      <c:import url="/stat_member_apply"/>
                    </div>
                  </div><!-- /.widget-main -->
                </div><!-- /.widget-body -->
              </div><!-- /.widget-box -->
            </div><!-- /.col -->
          </div>
          <div class="row">
            <div class="col-xs-12">
              <div class="widget-box transparent">
                <div class="widget-header widget-header-flat">
                  <h4 class="widget-title lighter">
                    <i class="ace-icon fa fa-signal"></i>
                    党员人数分布（前二十）
                  </h4>
                  <div class="widget-toolbar">
                      <a href="javascript:;" data-action="collapse">
                          <i class="ace-icon fa fa-chevron-up"></i>
                      </a>
                  </div>
                </div>

                <div class="widget-body">
                  <div class="widget-main padding-4">
                    <c:import url="/stat_member_party"/>
                  </div><!-- /.widget-main -->
                </div><!-- /.widget-body -->
              </div><!-- /.widget-box -->
            </div><!-- /.col -->
          </div>
          <%--<div class="row">
            <div class="col-xs-12">
              <div class="widget-box transparent">
                <div class="widget-header widget-header-flat">
                  <h4 class="widget-title lighter">
                    <i class="ace-icon fa fa-signal"></i>
                    发展党员趋势图
                  </h4>

                  <!--<div class="widget-toolbar">
                      <a href="javascript:;" data-action="collapse">
                          <i class="ace-icon fa fa-chevron-up"></i>
                      </a>
                  </div>-->
                </div>

                <div class="widget-body">
                  <div class="widget-main padding-4">
                    <div id="trend-charts"></div>
                  </div><!-- /.widget-main -->
                </div><!-- /.widget-body -->
              </div><!-- /.widget-box -->
            </div><!-- /.col -->
          </div>--%>

          <!-- PAGE CONTENT ENDS -->
        </div>
        <!-- /.col -->
      </div>
      <!-- /.row -->