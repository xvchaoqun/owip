<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="col-sm-4" id="stat_member_apply" style="width:350px;float: left;">
    <div class="widget-box">
        <div class="widget-header widget-header-flat widget-header-small">
            <h5 class="widget-title">
                <i class="ace-icon fa fa-signal"></i>
                申请入党情况统计
            </h5>
        </div>
        <div class="widget-body">
            <div class="widget-main">
                <div id="memberApply-placeholder"></div>
            </div>
        </div>
    </div>
</div>
<script>
    $(function () {
        $("#stat_member_apply ul li").click(function () {

            $.get("${ctx}/stat_member_apply", {type: $(this).data('type')}, function (html) {
                $("#stat_member_apply").replaceWith(html);
            });
        });

        var placeholder = $('#memberApply-placeholder').css({'width': '90%', 'min-height': '135px'});
        var data = [
                <c:forEach items="${statApplyMap}" var="apply">
                     <c:if test="${apply.key>=APPLY_STAGE_INIT && apply.key<=APPLY_STAGE_DRAW && not empty apply.value}">
                    {label: "${APPLY_STAGE_MAP.get(apply.key)}(${apply.value})", data: '${apply.value}', color:'${PIE_COLOR_MAP.get(apply.key)}'},
                     </c:if>
                </c:forEach>
        ];

        function drawPieChart(placeholder, data, position) {
            $.plot(placeholder, data, {
                series: {
                    pie: {
                        show: true,
                        tilt: 0.7,
                        highlight: {
                            opacity: 0.25
                        },
                        stroke: {
                            color: '#fff',
                            width: 2
                        },
                        startAngle: 2,
                        radius: 1,
                        label: {
                            show: true,
                            radius: 2/3,
                            formatter: function(label, series) {
                                // series is the series object for the label
                                return '<div style="color:#fff">' + series['percent'].toFixed(2) + '%</div>';
                            },
                            threshold: 0.1
                        }
                    }
                },
                legend: {
                    show: true,
                    position: position || "ne",
                    labelBoxBorderColor: null,
                    margin: [-30, 0]
                }
                ,
                grid: {
                    hoverable: true,
                    clickable: true
                }
            })
        }

        drawPieChart(placeholder, data);

        //pie chart tooltip example
        var $tooltip = $("<div class='tooltip top in'><div class='tooltip-inner'></div></div>").hide().appendTo('body');
        var previousPoint = null;
        placeholder.on('plothover', function (event, pos, item) {
            if (item) {
                //console.log(item)
                if (previousPoint != item.seriesIndex) {
                    previousPoint = item.seriesIndex;
                    var tip = item.series['label'] + " : " + item.series['percent'].toFixed(2) + '%';
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
    })
</script>