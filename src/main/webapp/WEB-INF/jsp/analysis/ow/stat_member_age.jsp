<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/analysis/constants.jsp" %>
<div class="col-sm-4" id="stat_member_age" style="width:550px;float: left;">
    <div class="widget-box">
        <div class="widget-header widget-header-flat widget-header-small">
            <h5 class="widget-title">
                <i class="ace-icon fa fa-pie-chart"></i>
                党员年龄结构
            </h5>
            <div class="widget-toolbar no-border">
                <div class="inline dropdown-hover">
                    <button class="btn btn-xs btn-info">
                        ${empty type?"全部":(type==MEMBER_TYPE_TEACHER)?"教职工":"学生"}
                         <i class="ace-icon fa fa-angle-down icon-on-right bigger-110"></i>
                    </button>
                    <ul class="dropdown-menu dropdown-menu-right dropdown-125 dropdown-lighter dropdown-close dropdown-caret">
                        <li class="${empty type?'active':''}">
                            <a href="javascript:;" class="blue">
                                <i class="ace-icon fa fa-caret-right bigger-110 ${empty type?'':'invisible'}">
                                    &nbsp;</i>
                                全部
                            </a>
                        </li>
                        <li data-type="${MEMBER_TYPE_TEACHER}"  class="${type!=MEMBER_TYPE_TEACHER?'':'active'}">
                            <a href="javascript:;" class="blue">
                                <i class="ace-icon fa fa-caret-right bigger-110 ${type!=MEMBER_TYPE_TEACHER?'invisible':''}">
                                    &nbsp;</i>
                                教职工
                            </a>
                        </li>
                        <li data-type="${MEMBER_TYPE_STUDENT}"  class="${type!=MEMBER_TYPE_STUDENT?'':'active'}">
                            <a href="javascript:;" class="blue">
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
                <div id="memberAge-placeholder" style="height: 252px"></div>
            </div>
        </div>
    </div>
</div>
<script>

    $("#stat_member_age ul li").click(function () {

        var url = "${ctx}/${isPartyAdmin?'stat_party_member_age':'stat_member_age'}";

        $.get(url, {type: $(this).data('type'),partyId: "${partyId}"}, function (html) {
            $("#stat_member_age").replaceWith(html);
        });
    });

    var memberAgeChart = echarts.init($('#memberAge-placeholder').get(0));
    memberAgeChart.showLoading({text: '正在加载数据'});

    var dataUrl = "${ctx}/${isPartyAdmin?'stat_party_member_age_data':'stat_member_age_data'}";

    $.get(dataUrl, {type:"${type}",partyId:"${partyId}",branchId:"${branchId}"}, function (statAgeMap) {

        var legendData = [];
        var seriesData = [];
        $.each(statAgeMap, function (key, value) {
            /*console.log(key);*/
            var item = _cMap.MEMBER_AGE_MAP[key] + '(' + value + ')';
            legendData.push(item);
            seriesData.push({
                name: item,
                value: value
            });
        });

        var option = {
            tooltip: {
                trigger: 'item',
                formatter: '{a} <br/>{b} : {c} ({d}%)'
            },
            legend: {
                orient: 'vertical',
                left: -5,
                top: 5,
                bottom: 5,
                data: legendData
            },
            series: [
                {
                    name: '党员年龄结构',
                    type: 'pie',
                    radius: '60%',
                    center: ['60%', '60%'],
                    data:seriesData,
                    emphasis: {
                        itemStyle: {
                            shadowBlur:10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                },
                {
                    name: '党员年龄结构',
                    type: 'pie',
                    radius: '60%',
                    center: ['60%', '60%'],
                    data: seriesData,
                    itemStyle:{            //饼图图形上的文本标签
                        normal: {
                            label: {
                                show: true,
                                position: 'inner',
                                fontSize: 12,
                                align: "left",
                                textStyle: {
                                    fontWeight: 300,
                                    fontSize: 12,   //文字的字体大小
                                    color:'white'
                                },
                                formatter: '{d}%'
                            },
                            labelLine:{
                                show : false //显示饼状图上的文本时，指示线不显示，在第一个data时显示指示线
                            }
                        }
                    }
                }
            ]
        };
        memberAgeChart.setOption(option, true);
        memberAgeChart.hideLoading();
    })
</script>