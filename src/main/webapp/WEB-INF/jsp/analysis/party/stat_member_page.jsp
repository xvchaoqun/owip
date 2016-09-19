<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

     <div class="row">
        <div class="col-xs-12">
          <!-- PAGE CONTENT BEGINS -->
          <%--<div class="alert alert-block alert-success">
            <button type="button" class="close" data-dismiss="alert">
              <i class="ace-icon fa fa-trash"></i>
            </button>

            <i class="ace-icon fa fa-check green"></i>
            欢迎使用组织工作管理与服务一体化平台
          </div>--%>

          <div class="row">
            <div class="col-sm-12">
              <div class="widget-box transparent">
                <div class="widget-header widget-header-flat">
                  <h4 class="widget-title lighter">
                    <i class="ace-icon fa fa-hourglass-end orange"></i>
                    待处理事项
                  </h4>
                  <div class="widget-toolbar">
                    <a href="#" data-action="collapse">
                      <i class="ace-icon fa fa-chevron-up"></i>
                    </a>
                  </div>
                </div>
                <div class="widget-body">
                  <div class="widget-main padding-4">
                    <div class="col-sm-12">

                      <div class="infobox infobox-pink" data-url="${ctx}/memberApply_layout?stage=${APPLY_STAGE_DRAW}">
                        <div class="infobox-icon">
                          <i class="ace-icon fa fa-hand-lizard-o"></i>
                        </div>
                        <div class="infobox-data">
                          <span class="infobox-data-number"><span class="count">${growOdCheckCount}</span> <span style="font-size: 10pt;">未处理</span></span>
                          <div class="infobox-content">领取志愿书审批</div>
                        </div>
                      </div>

                      <div class="infobox infobox-red" data-url="${ctx}/memberOut?cls=6">
                        <div class="infobox-icon">
                          <i class="ace-icon fa fa-sign-out"></i>
                        </div>
                        <div class="infobox-data">
                          <span class="infobox-data-number"><span class="count">${memberOutCount}</span> <span style="font-size: 10pt;">待审</span></span>

                          <div class="infobox-content">组织关系转出审批</div>
                        </div>
                      </div>
                      <div class="infobox infobox-green" data-url="${ctx}/memberIn?cls=4">
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

                      <div class="infobox infobox-blue2" data-url="${ctx}/graduateAbroad?cls=3">
                        <div class="infobox-icon">
                          <i class="ace-icon fa fa-plane"></i>
                        </div>
                        <div class="infobox-data">
                          <span class="infobox-data-number"><span class="count">${graduateAbroadCount}</span> <span style="font-size: 10pt;">未处理</span></span>
                          <div class="infobox-content">党员出国暂留审批</div>
                        </div>
                      </div>
                    </div>
                  </div><!-- /.widget-main -->
                </div><!-- /.widget-body -->
              </div><!-- /.widget-box -->

            </div>
            <!-- /.col -->
          </div>
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
                      <a href="#" data-action="collapse">
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
                      <a href="#" data-action="collapse">
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
                      <a href="#" data-action="collapse">
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