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
    <div class="col-lg-6 col-lg-offset-3" style="margin-top: 40px">
        <input class="col-lg-6 col-lg-offset-3" type="text" placeholder="请输入商品" id="searchKey">
        <button type="button" class="btn btn-default" aria-label="Left Align" id="search-good">
            <span class="glyphicon glyphicon-arrow-right" aria-hidden="true"></span>
        </button>
    </div>
    <div class="row col-md-8 col-md-offset-2" style="margin-top: 40px">
        <#if msg?? >
            <div class="alert alert-info" role="alert">${msg}</div>
        </#if>
        <table class="table">
            <thead>
            <tr>
                <th>商品</th>
                <th>单价</th>
                <th>数量</th>
                <th>选择</th>
            </tr>
            </thead>
            <tbody>
            <#list goods as good>
                <tr>
                    <td>${good.GName}</td>
                    <td>${good.GPrice}</td>
                    <td><input type="number" id="input-${good.GName}" max="${good.GNum}"/></td>
                    <td>
                        <button type="button" class="btn btn-primary" id="select-${good.GName}">选择</button>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>

</div>
<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
<script src="https://cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js"></script>
<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"></script>
<script>
    $("#search-good").click(function () {
        var str = $("#searchKey").val();
        window.location.href = "/cash/details?searchKey=" + str;
    });
    $('button[id^=select-]').click(function () {
        let name = $(this)[0].id.split("-")[1];
        var amountInputId = "#input-" + name;
        var amount = $(amountInputId).val();
        if (amount === "") {
            alert("购买数量不为空");
        } else {
            window.location.href = "/cash/addItem?goodName=" + name + "&amount=" + amount;
        }
    });
</script>
</body>
</html>
