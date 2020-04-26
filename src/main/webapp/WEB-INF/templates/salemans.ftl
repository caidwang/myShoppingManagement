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

<h1 style="text-align: center">销售员管理</h1>
<div class="container">

    <div class="col-lg-6 col-lg-offset-3" style="margin-top: 40px">
        <a href="/" class="col-lg-3">返回主页</a>
        <input class="col-lg-6" type="text" placeholder="请输入姓名查询" name="searchKey">
        <button type="button" class="btn btn-default" aria-label="Left Align" id="search-saleman" autofocus="autofocus">
            <span class="glyphicon glyphicon-arrow-right" aria-hidden="true"></span>
        </button>
        <button type="button" class="btn btn-default" aria-label="Left Align" id="add-saleman">
            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
        </button>
    </div>

    <div class="row col-md-8 col-md-offset-2" style="margin-top: 40px">
        <#if msg?? >
            <div class="alert alert-info" role="alert">${msg}</div></#if>
        <table class="table">
            <thead>
            <tr>
                <th>售货员姓名</th>
                <th>密码</th>
                <th>删除</th>
            </tr>
            </thead>
            <tbody>
            <#list salemen as sm>
                <tr>
                    <td>${sm.sname}</td>
                    <td>${sm.spassword}</td>
                    <td>
                        <button type="button" class="btn btn-default" aria-label="Left Align" id="delete-${sm.sname}">
                            <span class="glyphicon glyphicon-minus" aria-hidden="true"></span>
                        </button>
                    </td>
                </tr>
            </#list>
            <tr hidden="hidden" id="newsm-row">
                <td><input type="text" placeholder="输入姓名" name="newsm-name"/></td>
                <td><input type="text" placeholder="输入密码" name="newsm-password"/></td>
                <td>
                    <button type="button" class="btn btn-default" aria-label="Left Align" id="realAddSaleMan">
                        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
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
<script>
    $("#search-saleman").click(function () {
        window.location.href = "/backend?searchKey=" + $(" input[ name='searchKey' ] ").val();
    });
    $('button[id^=delete]').click(function () {
        var smId = $(this)[0].id.split("-")[1];
        console.log(smId);
        var msg = confirm("确定删除销售员: id=" + smId + "?");
        if (msg === true) {
            window.location.href = "/backend/delete?username=" + smId;
        }
    });
    $("#add-saleman").click(function () {
        $("#newsm-row").show();
    });
    $("#realAddSaleMan").click(function () {
        var name = $("input[name='newsm-name']").val();
        var passwd = $("input[name='newsm-password']").val();
        $.ajax({
            url: "/backend/add",
            type: "post",
            contentType: "application/json; charset=utf-8",
            data: '{"sname":"' + name + '","spassword":"' + passwd + '"}',
            dataType: "json",
            success: function () {
                window.location.href = "/backend";
            },
            error: function () {
                alert("销售员添加失败,用户名已存在");
            }
        })
    });
</script>
</body>
</html>