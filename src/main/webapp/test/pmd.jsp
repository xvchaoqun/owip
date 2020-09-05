<%@ page import="domain.pmd.PmdOrder" %>
<%@ page import="domain.pmd.PmdOrderExample" %>
<%@ page import="persistence.pmd.PmdOrderMapper" %>
<%@ page import="service.pmd.PmdOrderService" %>
<%@ page import="sys.tags.CmTag" %>
<%@ page import="sys.utils.DateUtils" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/assets/js/jquery.js"></script>
</head>
<body>
<%
    PmdOrderMapper pmdOrderMapper = CmTag.getBean(PmdOrderMapper.class);
    PmdOrderService pmdOrderService = CmTag.getBean(PmdOrderService.class);

    PmdOrderExample example = new PmdOrderExample();
    example.createCriteria()
            .andIsSuccessEqualTo(false).andIsClosedEqualTo(false)
            .andCreateTimeGreaterThan(DateUtils.parseStringToDate("2019-09-05 00:00:00"));
    List<PmdOrder> pmdOrders = pmdOrderMapper.selectByExample(example);

    List<Map<String, String>> mapList = new ArrayList<>();

    int i=0;
    for (PmdOrder pmdOrder : pmdOrders) {
        String sn = pmdOrder.getSn();
        OrderQueryResult queryResult = pmdOrderService.query(sn);
        //System.out.println("sn = " + sn + " queryResult.isHasPay() = " + queryResult.isHasPay());

        if(queryResult.isHasPay()) {

            Map<String, String> map = new HashMap<>();
            map.put("sn", sn);
            map.put("ret", queryResult.getRet());
            mapList.add(map);
            i++;
            //if(i>2) break;
        }
    }
    request.setAttribute("mapList", mapList);
    //System.out.println("pmdOrders.size() = " + pmdOrders.size());
%>
<script>
    var i=0;
    var f=0;
    var mapList = ${cm:toJSONArray(mapList)};
    console.log(mapList)
    $.each(mapList, function (i, map) {
        var ret = JSON.parse(map.ret);
        var sn = map.sn;
        var params = ret.msg;

        setTimeout(function(){
            $.post("${ctx}/pmd/pmdOrder_query_sign?"+params,function(sign){
                params += "&sign=" + sign;
                    $.post("${ctx}/pmd/pay/callback/newcampuscard?" + params, function (ret) {
                        if (ret == 'pok') {
                            console.log("同步支付通知成功。" + sn);
                            i++;
                        } else {
                            console.log(sn+ "同步失败：" + ret)
                            f++;
                        }
                    });
                });
        }, 1000);
    })
    console.log("i="+i);
    console.log("f="+f);
</script>
</body>
</html>
