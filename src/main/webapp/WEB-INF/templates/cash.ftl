<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>结算页</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- HTML5 shim 和 Respond.js 是为了让 IE8 支持 HTML5 元素和媒体查询（media queries）功能 -->
    <!-- 警告：通过 file:// 协议（就是直接将 html 页面拖拽到浏览器中）访问页面时 Respond.js 不起作用 -->
    <!--[if lt IE 9]>
    <script src="https://cdn.jsdelivr.net/npm/html5shiv@3.7.3/dist/html5shiv.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/respond.js@1.4.2/dest/respond.min.js"></script>
    <![endif]-->
</head>
<body>

<h1 style="text-align: center">收银平台</h1>
<div class="container">
    <div class="col-lg-4 col-lg-offset-4 text-center">收银员: <span id="saleman">${saleman.sname}</span></div>
    <div class="row col-md-8 col-md-offset-2" style="margin-top: 40px">
        <table class="table">
            <thead>
            <tr>
                <th>商品</th>
                <th>单价</th>
                <th>购买数量</th>
                <th>金额</th>
                <th>删除</th>
            </tr>
            </thead>
            <tbody>
            <#list cartItems as item>
                <tr>
                    <td>${item.goodName}</td>
                    <td>${item.goodPrice}</td>
                    <td>${item.amount}</td>
                    <td>${item.sumMoney}</td>
                    <td>
                        <button type="button" class="btn btn-danger" aria-label="Left Align" id="delete-${item?index}">
                            <span class="glyphicon glyphicon-minus" aria-hidden="true"></span>
                        </button>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</div>
<div class="row col-lg-8 col-lg-offset-2 ">
    <div class="col-lg-2 col-lg-offset-6 text-center" style="line-height: 2;"><b>总计<span id="totalBill">${total}</span>元</b>
    </div>
    <div>
        <button type="button" class="btn btn-primary" id="addItem">继续添加</button>
        <button type="button" class="btn btn-primary" id="check">结账</button>
    </div>
</div>
<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
<script src="https://cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js"></script>
<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"></script>
<script>
    $("#addItem").click(function () {
        window.location.href = "/cash/details";
    });
    $("#check").click(function () {
        window.location.href = "/cash/check";
    });
    $('button[id^=delete]').click(function () {
        var id = $(this)[0].id.split("-")[1];
        console.log(id);
        var msg = confirm("确定删除商品?");
        if (msg === true) {
            window.location.href = "/cash/delete/" + id;
        }
    });
</script>
</body>
</html>