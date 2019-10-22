<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="OW_APPLY_STAGE_MAP" value="<%=OwConstants.OW_APPLY_STAGE_MAP%>"/>
<c:set var="PIE_COLOR_MAP" value="<%=SystemConstants.PIE_COLOR_MAP%>"/>
<c:set var="OW_APPLY_STAGE_INIT" value="<%=OwConstants.OW_APPLY_STAGE_INIT%>"/>
<c:set var="OW_APPLY_STAGE_DRAW" value="<%=OwConstants.OW_APPLY_STAGE_DRAW%>"/>

<div class="col-sm-4" id="stat_ow_branch_type" style="width:350px;float: left;">
    <div class="widget-box">
        <div class="widget-header widget-header-flat widget-header-small">
            <h5 class="widget-title">
                <i class="ace-icon fa fa-signal"></i>
                支部类型统计
            </h5>
        </div>
        <div class="widget-body">
            <div class="widget-main">
                <div id="branchType-placeholder"></div>
            </div>
        </div>
    </div>
</div>
<script>
    $(function () {
        $("#stat_ow_branch_type ul li").click(function () {

            $.get("${ctx}/stat_ow_branch_type", {type: $(this).data('type')}, function (html) {
                $("#stat_ow_branch_type").replaceWith(html);
            });
        });

        var placeholder = $('#branchType-placeholder').css({'width': '90%', 'min-height': '135px'});
        var data = [
                <c:forEach items="${branchTypeMap}" var="type">

                    {label: "${metaTypes.get(type.key).name}(${type.value})", data: '${type.value}'/*, color:'${PIE_COLOR_MAP.get(apply.key)}'*/},
                </c:forEach>
        ];

        function drawPieChart(placeholder, data, position) {
            if(data.length==0) return;
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
        $(document).one('ajaxStart.page', function (e) {
            $tooltip.remove();
        });
    })
</script>