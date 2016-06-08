$.ajaxSetup({
    cache: false,
    dataFilter : function(data, type){
        var ret;

        try {
            ret = JSON.parse(data)
        }catch(e){
            //console.log(data)
            return data;
        }
        //console.log(ret)
        if(ret.success) return data;

        if(ret.msg == "login"){
            bootbox.dialog({message:"登陆超时，请您重新登陆",
                closeButton: false,
                buttons: {
                    close: {
                        label: "确定",
                        className: "btn-info",
                        callback: function () {
                            //window.open('','_self','');
                            $("#modal").modal('hide');
                            window.close();
                        }
                    },
                    login: {
                        label: "重新登陆",
                        className: "btn-success",
                        callback: function () {
                            if(window.opener){
                                //window.open('','_self','');
                                window.close();
                            }
                            location.href=ctx+"/login";
                        }
                    }
                }});
            throw  new Error("login");
            //$(".modal").width(300);
        }else if(ret.msg=="filemax"){

            SysMsg.warning('文件大小超过10M。', '文件大小限制');
        }else if(ret.msg=="wrong"){

        }else if(ret.msg=="duplicate"){

            SysMsg.warning('该记录已经存在，请不要重复添加。', '重复');
        }else
            SysMsg.error(ret.msg, '操作失败');

        return data;
    },error:function(jqXHR, textStatus, errorMsg){
        //alert( '发送AJAX请求到"' + this.url + '"时出错[' + jqXHR.status + ']：' + errorMsg );
        //SysMsg.error('系统异常，请稍后再试。', '系统异常');
    }
});


function _logout(){
    location.href = ctx+"/m/logout";
    /*$.post(ctx+"/m/logout", {},function(data){
        if(data.success){
            location.href=ctx+'/m/index';
        }
    })*/
}

var _width;
function loadModal(url, width, dragTarget){ // dragTarget：拖拽位置
    if(width>0){
        _width = width;
        $('#modal .modal-dialog').addClass("modal-width"+width);
    }else{
        $('#modal .modal-dialog').removeClass("modal-width"+_width);
    }
    dragTarget = dragTarget||".modal-header";

    $('#modal .modal-content').load(url,function(data){
        if(!data.startWith("{")) $("#modal").modal('show');
    });
}

// 内页展示
$(document).on("touchend click", "#body-content .openView", function(){
    if(_touch) {
        var openBy = $(this).data("openby");
        var url = $(this).data("url");
        if(openBy=='page'){
            var $container = $("#body-content");
            $container.showLoading({'afterShow':
                function() {
                    setTimeout( function(){
                        $container.hideLoading();
                    }, 2000 );
                }})
            $.get(url,{},function(html){
                $container.hideLoading().hide();
                $("#item-content").hide().html(html).fadeIn("slow");
            })
        }else{
            loadModal(url, $(this).data("width"));
        }
    }
});

$(document).on("click", "#item-content .closeView", function(){
    var $this = $(this);
    $("#item-content").fadeOut("fast",function(){

        if($this.hasClass("reload")) {
            page_reload(function () {
                $("#body-content").show()
            });
        } else
            $("#body-content").show(0,function(){
                $(window).resize(); // 解决jqgrid不显示的问题
            });
    });
});

$(document).on("touchend click", ".popView", function(){
    if(_touch) {
        var url = $(this).data("url");
        loadModal(url, $(this).data("width"));
    }
});

var _touch = false;
$(document).on("touchstart", ".ahref,#body-content .openView, .popView", function(){
    _touch = true;
});
$(document).on("touchmove", ".ahref,#body-content .openView, .popView", function(){
    _touch = false;
});

$(document).on("touchend click", ".ahref", function(){
    if(_touch) {
        var $this = $(this);
        location.href = $this.data("url");
    }
});