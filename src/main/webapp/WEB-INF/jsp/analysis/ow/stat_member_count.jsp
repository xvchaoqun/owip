<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="MEMBER_POLITICAL_STATUS_GROW" value="<%=MemberConstants.MEMBER_POLITICAL_STATUS_GROW%>"/>
<c:set var="MEMBER_POLITICAL_STATUS_POSITIVE" value="<%=MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE%>"/>

<div class="col-sm-4" id="stat_member_other" style="width:350px;float: left;">
    <div class="widget-box">
        <div class="widget-header widget-header-flat widget-header-small">
            <h5 class="widget-title">
                <i class="ace-icon fa fa-signal"></i>
                党员基本情况统计
            </h5>
            <div class="widget-toolbar no-border">
                <div class="inline dropdown-hover">
                    <button class="btn btn-xs btn-info">
                        ${empty type?"基本情况":(type==1)?"性别":"民族"}
                        <i class="ace-icon fa fa-angle-down icon-on-right bigger-110"></i>
                    </button>

                    <ul class="dropdown-menu dropdown-menu-right dropdown-125 dropdown-lighter dropdown-close dropdown-caret">
                        <li class="${empty type?'active':''}">
                            <a href="javascript:;" class="blue">
                                <i class="ace-icon fa fa-caret-right bigger-110 ${empty type?'':'invisible'}">
                                    &nbsp;</i>
                                基本情况
                            </a>
                        </li>
                        <li data-type="1"  class="${type!=1?'':'active'}">
                            <a href="javascript:;" class="blue">
                                <i class="ace-icon fa fa-caret-right bigger-110 ${type!=1?'invisible':''}">
                                    &nbsp;</i>
                                性别
                            </a>
                        </li>
                        <li data-type="2"  class="${type!=2?'':'active'}">
                            <a href="javascript:;" class="blue">
                                <i class="ace-icon fa fa-caret-right bigger-110 ${type!=2?'invisible':''}">
                                    &nbsp;</i>
                                民族
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="widget-body">
            <c:if test="${not empty type}">
                <div class="widget-main">
                    <div id="memberOther-placeholder"></div>
                </div>
            </c:if>
            <c:if test="${empty type}">
                <div class="widget-main">
                    <div style="min-height: 130px;">
                        <div class="stat-row">党员总数：<span class="count">${statPoliticalStatusMap.get(MEMBER_POLITICAL_STATUS_POSITIVE) + statPoliticalStatusMap.get(MEMBER_POLITICAL_STATUS_GROW)}</span></div>
                        <div class="stat-row">正式党员人数：<span class="count">${statPoliticalStatusMap.get(MEMBER_POLITICAL_STATUS_POSITIVE)}</span><br/>（其中教职工<span class="count">${statPositiveMap.get(MEMBER_TYPE_TEACHER)}</span>人，学生<span class="count">${statPositiveMap.get(MEMBER_TYPE_STUDENT)}</span>人）</div>
                        <div class="stat-row">预备党员人数：<span class="count">${statPoliticalStatusMap.get(MEMBER_POLITICAL_STATUS_GROW)}</span><br/>（其中教职工<span class="count">${statGrowMap.get(MEMBER_TYPE_TEACHER)}</span>人，学生<span class="count">${statGrowMap.get(MEMBER_TYPE_STUDENT)}</span>人）</div>
                    </div>
                </div>
            </c:if>
        </div>
    </div>
</div>
<script>
    $(function () {
        $("#stat_member_other ul li").click(function () {

            var partyId = "${partyId}";

            //console.log(partyId);
            var url = "${ctx}/stat_member_count";

            <shiro:hasPermission name="stat:party">
                url = "${ctx}/stat_party_member_count";
            </shiro:hasPermission>

            $.get(url, {type: $(this).data('type'),partyId: partyId}, function (html) {
                $("#stat_member_other").replaceWith(html);
            });
        });

        var placeholder = $('#memberOther-placeholder').css({'width': '90%', 'min-height': '135px'});
        var data = [
            <c:forEach items="${otherMap}" var="other">
            <c:if test="${not empty other.value}">
            {label: "${other.key}(${other.value})", data: '${other.value}'/*, color:'${PIE_COLOR_MAP.get(other.key)}'*/},
            </c:if>
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