/**
 * Created by fafa on 2016/3/31.
 */
function _logout(){
    $.post(ctx+"/m/logout", {},function(data){
        if(data.success){
            location.href=ctx+'/m/index';
        }
    })
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