<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="col-sm-4" id="stat_member_age" style="width:350px;float: left;">
    <div class="widget-box">
        <div class="widget-header widget-header-flat widget-header-small">
            <h5 class="widget-title">
                <i class="ace-icon fa fa-signal"></i>
                党员年龄结构
            </h5>
            <div class="widget-toolbar no-border">
                <div class="inline dropdown-hover">
                    <button class="btn btn-mini btn-xs btn-info">
                        ${empty type?"全部":(type==MEMBER_TYPE_TEACHER)?"教职工":"学生"}
                         <i class="ace-icon fa fa-angle-down icon-on-right bigger-110"></i>
                    </button>

                    <ul class="dropdown-menu dropdown-menu-right dropdown-125 dropdown-lighter dropdown-close dropdown-caret">
                        <li class="active">
                            <a href="#" class="blue">
                                <i class="ace-icon fa fa-caret-right bigger-110 ${empty type?'':'invisible'}">
                                    &nbsp;</i>
                                全部
                            </a>
                        </li>
                        <li data-type="${MEMBER_TYPE_TEACHER}">
                            <a href="#" class="blue">
                                <i class="ace-icon fa fa-caret-right bigger-110 ${type!=MEMBER_TYPE_TEACHER?'invisible':''}">
                                    &nbsp;</i>
                                教职工
                            </a>
                        </li>
                        <li data-type="${MEMBER_TYPE_STUDENT}">
                            <a href="#">
                                <i class="ace-icon fa fa-caret-right bigger-110 ${type!=MEMBER_TYPE_STUDENT?'invisible':''}">
                                    &nbsp;</i>
                                学生
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="widget-body">
            <div class="widget-main">
                <div id="memberAge-placeholder"></div>
            </div>
        </div>
    </div>
</div>
<script>
    $(function () {
        $("#stat_member_age ul li").click(function () {

            $.get("${ctx}/stat_member_age", {type: $(this).data('type')}, function (html) {
                $("#stat_member_age").replaceWith(html);
            });
        });

        var placeholder = $('#memberAge-placeholder').css({'width': '90%', 'min-height': '135px'});
        var data = [
                <c:forEach items="${statAgeMap}" var="age">
                     <c:if test="${not empty age.value}">
                    {label: "${MEMBER_AGE_MAP.get(age.key)}(${age.value})", data: '${age.value}', color:'${PIE_COLOR_MAP.get(age.key)}'},
                     </c:if>
                </c:forEach>
        ]

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