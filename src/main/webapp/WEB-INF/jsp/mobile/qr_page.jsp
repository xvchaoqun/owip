<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>

<script>
    wx.config({
        debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
        appId: 'jxliaom', // 必填，公众号的唯一标识
        timestamp:new Date().getTime , // 必填，生成签名的时间戳
        nonceStr: 'sdfsdfsdf', // 必填，生成签名的随机串
        signature: 'sfasdfasdfasfasf',// 必填，签名
        jsApiList: ['scanQRCode'] // 必填，需要使用的JS接口列表
    });

    $.register.m_click("#ssss", function(){

        wx.scanQRCode({
            needResult: 1,
            desc: 'scanQRCode desc',
            success: function(res) {
                var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果

                if (typeof(result) != 'undefined') {
                    if (result.indexOf(",") > 0) {
                        result = result.split(',')[1];
                        result = result.replace(/[^a-z\d]/ig, "");
                    }
                    _self.$router.push({
                        name: 'BrandIntroduction',
                        params:{
                            traceCode: result
                        }
                    })
                }
            }
        });
    })
</script>

<div class="qr-btn" node-type="jsbridge">扫描二维码1
  <input id="ssss" type="button" onClick="sss()" value="扫描二维码1" />
</div>