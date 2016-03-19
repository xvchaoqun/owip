<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
     <div class="row">
        <div class="col-xs-12">
          <!-- PAGE CONTENT BEGINS -->
          <div class="alert alert-block alert-success">
            <button type="button" class="close" data-dismiss="alert">
              <i class="ace-icon fa fa-trash"></i>
            </button>

            <i class="ace-icon fa fa-check green"></i>

            欢迎使用组织工作管理与服务一体化平台
          </div>

          <div class="row">

            <div class="col-sm-8 infobox-container">
              <div class="infobox infobox-green">
                <div class="infobox-icon">
                  <i class="ace-icon fa fa-comments"></i>
                </div>

                <div class="infobox-data">
                  <span class="infobox-data-number">32 <span style="font-size: 10pt;">未回复</span></span>

                  <div class="infobox-content">新消息提醒</div>
                </div>


              </div>

              <div class="infobox infobox-blue">
                <div class="infobox-icon">
                  <i class="ace-icon fa fa-calendar-minus-o"></i>
                </div>

                <div class="infobox-data">
                  <span class="infobox-data-number">11 <span style="font-size: 10pt;">一月内到届</span></span>

                  <div class="infobox-content">干部任职到届提醒</div>
                </div>


              </div>

              <div class="infobox infobox-pink">
                <div class="infobox-icon">
                  <i class="ace-icon fa fa-hourglass-end "></i>
                </div>

                <div class="infobox-data">
                  <span class="infobox-data-number">8 <span style="font-size: 10pt;">待转</span></span>

                  <div class="infobox-content">党员组织关系转接提醒</div>
                </div>
              </div>

              <div class="infobox infobox-red">
                <div class="infobox-icon">
                  <i class="ace-icon fa fa-plane"></i>
                </div>
                <div class="infobox-data">
                  <span class="infobox-data-number">7 <span style="font-size: 10pt;">申请</span></span>

                  <div class="infobox-content">因私出国证件</div>
                </div>
              </div>

              <div class="infobox infobox-orange2">

                <div class="infobox-icon">
                  <i class="ace-icon fa fa-pencil-square-o"></i>
                </div>

                <div class="infobox-data">
                  <span class="infobox-data-number">6 <span style="font-size: 10pt;">申请</span> </span>

                  <div class="infobox-content">干部信息修改</div>
                </div>

              </div>

              <div class="infobox infobox-blue2">
                <div class="infobox-icon">
                  <i class="ace-icon fa fa-exclamation-triangle"></i>
                </div>

                <div class="infobox-data">
                  <span class="infobox-data-number">1 <span style="font-size: 10pt;">人</span> </span>

                  <div class="infobox-content">

                    本月离校人员提醒
                  </div>
                </div>
              </div>
              <div class="infobox infobox-purple">
                <div class="infobox-icon">
                  <i class="ace-icon fa fa-pencil-square-o"></i>
                </div>

                <div class="infobox-data">
                  <span class="infobox-data-number">7 <span style="font-size: 10pt;">申请</span></span>

                  <div class="infobox-content">因私出国证件</div>
                </div>
              </div>

              <div class="infobox infobox-pink">
                <div class="infobox-icon">
                  <i class="ace-icon fa fa-pencil-square-o"></i>
                </div>

                <div class="infobox-data">
                  <span class="infobox-data-number">6 <span style="font-size: 10pt;">申请</span> </span>

                  <div class="infobox-content">干部信息修改</div>
                </div>

              </div>

              <div class="infobox infobox-red">
                <div class="infobox-icon">
                  <i class="ace-icon fa fa-pencil-square-o"></i>
                </div>

                <div class="infobox-data">
                  <span class="infobox-data-number">1 <span style="font-size: 10pt;">人</span> </span>

                  <div class="infobox-content">

                    本月离校人员提醒
                  </div>
                </div>
              </div>


            </div>

            <div class="vspace-12-sm"></div>

            <div class="col-sm-4">
              <div class="widget-box">
                <div class="widget-header widget-header-flat widget-header-small">
                  <h5 class="widget-title">
                    <i class="ace-icon fa fa-signal"></i>
                    党员年龄结构
                  </h5>

                  <div class="widget-toolbar no-border">
                    <div class="inline dropdown-hover">
                      <button class="btn btn-mini btn-xs btn-info">
                        全部
                        <i class="ace-icon fa fa-angle-down icon-on-right bigger-110"></i>
                      </button>

                      <ul class="dropdown-menu dropdown-menu-right dropdown-125 dropdown-lighter dropdown-close dropdown-caret">
                        <li class="active">
                          <a href="#" class="blue">
                            <i class="ace-icon fa fa-caret-right bigger-110">&nbsp;</i>
                            教工
                          </a>
                        </li>

                        <li>
                          <a href="#">
                            <i class="ace-icon fa fa-caret-right bigger-110 invisible">
                              &nbsp;</i>
                            学生
                          </a>
                        </li>

                        <li>
                          <a href="#">
                            <i class="ace-icon fa fa-caret-right bigger-110 invisible">
                              &nbsp;</i>
                            其他
                          </a>
                        </li>
                      </ul>
                    </div>
                  </div>
                </div>

                <div class="widget-body">
                  <div class="widget-main">
                    <div id="piechart-placeholder"></div>
                  </div>
                  <!-- /.widget-main -->
                </div>
                <!-- /.widget-body -->
              </div>
              <!-- /.widget-box -->
            </div>
            <!-- /.col -->
          </div>
          <!-- /.row -->

          <div class="row">
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
          </div>

          <!-- PAGE CONTENT ENDS -->
        </div>
        <!-- /.col -->
      </div>
      <!-- /.row -->
<!-- inline scripts related to this page -->
<script src="assets/js/flot/jquery.flot.js"></script>
<script src="assets/js/flot/jquery.flot.pie.js"></script>
<script src="assets/js/flot/jquery.flot.resize.js"></script>
<script src="${ctx}/assets/js/ace/elements.scroller.js"></script>
<script type="text/javascript">
  jQuery(function ($) {
    $('.easy-pie-chart.percentage').each(function () {
      var $box = $(this).closest('.infobox');
      var barColor = $(this).data('color') || (!$box.hasClass('infobox-dark') ? $box.css('color') : 'rgba(255,255,255,0.95)');
      var trackColor = barColor == 'rgba(255,255,255,0.95)' ? 'rgba(255,255,255,0.25)' : '#E2E2E2';
      var size = parseInt($(this).data('size')) || 50;
      $(this).easyPieChart({
        barColor: barColor,
        trackColor: trackColor,
        scaleColor: false,
        lineCap: 'butt',
        lineWidth: parseInt(size / 10),
        animate: /msie\s*(8|7|6)/.test(navigator.userAgent.toLowerCase()) ? false : 1000,
        size: size
      });
    })

    $('.sparkline').each(function () {
      var $box = $(this).closest('.infobox');
      var barColor = !$box.hasClass('infobox-dark') ? $box.css('color') : '#FFF';
      $(this).sparkline('html',
              {
                tagValuesAttribute: 'data-values',
                type: 'bar',
                barColor: barColor,
                chartRangeMin: $(this).data('min') || 0
              });
    });


    //flot chart resize plugin, somehow manipulates default browser resize event to optimize it!
    //but sometimes it brings up errors with normal resize event handlers
    $.resize.throttleWindow = false;

    var placeholder = $('#piechart-placeholder').css({'width': '90%', 'min-height': '132px'});
    var data = [
      {label: "18以下", data: 8.2, color: "#68BC31"},
      {label: "18~25", data: 24.5, color: "#2091CF"},
      {label: "26~35", data: 38.7, color: "#AF4E96"},
      {label: "36~45", data: 18.6, color: "#DA5430"},
      {label: "45以上", data: 10, color: "#FEE074"}
    ]

    function drawPieChart(placeholder, data, position) {
      $.plot(placeholder, data, {
        series: {
          pie: {
            show: true,
            tilt: 0.8,
            highlight: {
              opacity: 0.25
            },
            stroke: {
              color: '#fff',
              width: 2
            },
            startAngle: 2
          }
        },
        legend: {
          show: true,
          position: position || "ne",
          labelBoxBorderColor: null,
          margin: [-30, 15]
        }
        ,
        grid: {
          hoverable: true,
          clickable: true
        }
      })
    }

    drawPieChart(placeholder, data);

    /**
     we saved the drawing function and the data to redraw with different position later when switching to RTL mode dynamically
     so that's not needed actually.
     */
    placeholder.data('chart', data);
    placeholder.data('draw', drawPieChart);


    //pie chart tooltip example
    var $tooltip = $("<div class='tooltip top in'><div class='tooltip-inner'></div></div>").hide().appendTo('body');
    var previousPoint = null;

    placeholder.on('plothover', function (event, pos, item) {
      if (item) {
        if (previousPoint != item.seriesIndex) {
          previousPoint = item.seriesIndex;
          var tip = item.series['label'] + " : " + item.series['percent'] + '%';
          $tooltip.show().children(0).text(tip);
        }
        $tooltip.css({top: pos.pageY + 10, left: pos.pageX + 10});
      } else {
        $tooltip.hide();
        previousPoint = null;
      }

    });

    /////////////////////////////////////
    $(document).one('ajaxloadstart.page', function (e) {
      $tooltip.remove();
    });

    $('#recent-box [data-rel="tooltip"]').tooltip({placement: tooltip_placement});
    function tooltip_placement(context, source) {
      var $source = $(source);
      var $parent = $source.closest('.tab-content')
      var off1 = $parent.offset();
      var w1 = $parent.width();

      var off2 = $source.offset();
      //var w2 = $source.width();

      if (parseInt(off2.left) < parseInt(off1.left) + parseInt(w1 / 2)) return 'right';
      return 'left';
    }


    $('.dialogs,.comments').ace_scroll({
      size: 300
    });


    //Android's default browser somehow is confused when tapping on label which will lead to dragging the task
    //so disable dragging when clicking on label
    var agent = navigator.userAgent.toLowerCase();
    if ("ontouchstart" in document && /applewebkit/.test(agent) && /android/.test(agent))
      $('#tasks').on('touchstart', function (e) {
        var li = $(e.target).closest('#tasks li');
        if (li.length == 0)return;
        var label = li.find('label.inline').get(0);
        if (label == e.target || $.contains(label, e.target)) e.stopImmediatePropagation();
      });

    $('#tasks').sortable({
              opacity: 0.8,
              revert: true,
              forceHelperSize: true,
              placeholder: 'draggable-placeholder',
              forcePlaceholderSize: true,
              tolerance: 'pointer',
              stop: function (event, ui) {
                //just for Chrome!!!! so that dropdowns on items don't appear below other items after being moved
                $(ui.item).css('z-index', 'auto');
              }
            }
    );
    $('#tasks').disableSelection();
    $('#tasks input:checkbox').removeAttr('checked').on('click', function () {
      if (this.checked) $(this).closest('li').addClass('selected');
      else $(this).closest('li').removeClass('selected');
    });


    //show the dropdowns on top or bottom depending on window height and menu position
    $('#task-tab .dropdown-hover').on('mouseenter', function (e) {
      var offset = $(this).offset();

      var $w = $(window)
      if (offset.top > $w.scrollTop() + $w.innerHeight() - 100)
        $(this).addClass('dropup');
      else $(this).removeClass('dropup');
    });

  })

  //折线图
  $(function() {

    var d1 = [];
    for (var i = 0; i < 12; i++) {
      d1.push([i, Math.floor(Math.random()*20)]);
    }

    var d2 = [];
    for (var i = 0; i < 12; i++) {
      d2.push([i, Math.floor(Math.random()*20)]);
    }

    var plot = $.plot("#trend-charts", [
      { data: d1, label: "入党"},
      { data: d2, label: "申请入党"}
    ], {
      series: {
        lines: {
          show: true
        },
        points: {
          show: true
        }
      },
      grid: {
        hoverable: true,
        clickable: true
      },
      xaxis:{
        axisLabel: "月份",
        axisLabelUseCanvas: true,
        axisLabelFontSizePixels: 16,
        axisLabelFontFamily: '宋体',
        axisLabelPadding: 10,
        min:1,
        max:12
      },
      yaxis: {
        axisLabel: "人数",
        axisLabelUseCanvas: true,
        axisLabelFontSizePixels: 16,
        axisLabelFontFamily: '宋体',
        axisLabelPadding: 10,
        min: 0,
        max: 20
      },
      legend: {
        position: "ne"
        //margin:[0,0]
      },
      grid:{
        borderWidth:0
      }
    });

    $("<div id='tooltip'></div>").css({
      position: "absolute",
      display: "none",
      border: "1px solid #fdd",
      padding: "2px",
      "background-color": "#fee",
      opacity: 0.80
    }).appendTo("body");

    $("#trend-charts").bind("plothover", function (event, pos, item) {


      if (item) {
        var x = item.datapoint[0],
                y = item.datapoint[1];

        $("#tooltip").html(x+"月" +item.series.label + y +"人")
                .css({top: item.pageY-20, left: item.pageX+5})
                .fadeIn(200);
      } else {
        $("#tooltip").hide();
      }
    });
  });
</script>