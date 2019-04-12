/*index.js简单的Login Form登录页html模板_01。index.html中js（index.js）*/
/*单顶部右侧图标取消功能
fadeOut() 方法使用淡出效果来隐藏被选元素，假如该元素是隐藏的。
$(selector).fadeOut(speed,callback)
参数描述:
speed  可选。规定元素从可见到隐藏的速度。默认为 "normal"。
可能的值：
    毫秒 （比如 1500）
    "slow"
    "normal"
    "fast"
在设置速度的情况下，元素从可见到隐藏的过程中，会逐渐地改变其透明度（这样会创造淡出效果）。
callback   可选。fadeOut 函数执行完之后，要执行的函数。
    除非设置了 speed 参数，否则不能设置该参数。
*/
$(function(c) {
    $('.alert-close').on('click', function(c){
        $('.message').fadeOut('slow', function(c){
            $('.message').remove();
        });
    });
});
/*
js中querySelectorAll事件更加详细：https://blog.csdn.net/tel13259437538/article/details/79049191
js中querySelectorAll事件：通过css选择器获取所有a元素，返回一个类数组
*/
var __links = document.querySelectorAll('a');
for(var i = 0, l = __links.length; i < l; i++) {
    if ( __links[i].getAttribute('data-t') == '_blank' ) {
        __links[i].addEventListener('click', __linkClick, false);
    }
}
/* window.postMessage
我们知道：浏览器限制不同窗口之间的通信，除非同一个域名下的网页。
为了解决这一问题，HTML5新出了一个API: window.postMessage, 实现了不同域名的窗口通信。
*/
function __linkClick(e) {
    parent.window.postMessage(this.href, '*');
}
/*登录按钮功能实现*/
$(function () {
    $("#btn-sign-in").click(function () {

    });
});

/*账号密码框--焦点获得、失去事件*/
function  doFocus(e) {
    e.value = '';
}
function  doBlur(e) {
    if(e.value == '') {
        e.value = 'Username';
    }
}
