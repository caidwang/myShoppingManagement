<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>销售员管理</title>

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

<h1 style="text-align: center">商品管理</h1>
<div class="container">

    <div class="col-lg-6 col-lg-offset-3" style="margin-top: 40px">
        <input class="col-lg-6 col-lg-offset-3" type="text" placeholder="请输入商品名" name="searchKey">
        <button type="button" class="btn btn-default" aria-label="Left Align" id="search-good">
            <span class="glyphicon glyphicon-arrow-right" aria-hidden="true"></span>
        </button>
        <button type="button" class="btn btn-default" aria-label="Left Align" id="add-good">
            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
        </button>
    </div>
    <div class="row col-md-8 col-md-offset-2" style="margin-top: 40px">
        <table class="table">
            <thead>
            <tr>
                <th>商品名称</th>
                <th>数量</th>
                <th>单价</th>
                <th>修改</th>
                <th>删除</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>巧克力</td>
                <td>15</td>
                <td>15.50</td>
                <td>
                    <button type="button" class="btn btn-warning" aria-label="Left Align" id="delete-good">
                        修改
                    </button>
                </td>
                <td>
                    <button type="button" class="btn btn-danger" aria-label="Left Align" id="delete-good">
                        <span class="glyphicon glyphicon-minus" aria-hidden="true"></span>
                    </button>
                </td>
            </tr>
            <tr>
                <td>豆豆</td>
                <td>15</td>
                <td>15.60</td>
                <td>
                    <button type="button" class="btn btn-warning" aria-label="Left Align" id="delete-good">
                        修改
                    </button>
                </td>
                <td>
                    <button type="button" class="btn btn-warning" aria-label="Left Align" id="delete-good">
                        <span class="glyphicon glyphicon-minus" aria-hidden="true"></span>
                    </button>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
<script src="https://cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js"></script>
<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"></script>
</body>
</html>