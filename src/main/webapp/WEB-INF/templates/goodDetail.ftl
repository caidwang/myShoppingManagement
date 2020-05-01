<!DOCTYPE html>
<#import "/spring.ftl" as spring/>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>商品管理</title>

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


<div class="container">
    <div class="col-md-8 col-md-offset-2" style="margin-top: 40px">
        <a href="/good" class="col-md-1">返回</a>
        <h1 style="text-align: center">商品详情</h1>
    </div>

    <div class="row col-md-8 col-md-offset-2" style="margin-top: 40px">
        <form name="good" method="post" action="/good/submitGood<#if goodname??>?goodname=${goodname}</#if>">

            <@spring.bind "good.gName" />
            <label for="gName">商品名</label>
            <input type="text" name="gName" id="gName" class="form-control" placeholder="输入商品"
                   <#if goodname??>value="${good.GName}</#if>" required autofocus>
            <@spring.showErrors " " />
            <br>
            <@spring.bind "good.gPrice" />
            <label for="gPrice">商品价格</label>
            <input type="text" name="gPrice" id="gPrice" class="form-control" placeholder="输入商品价格"
                   <#if goodname?? >value="${good.GPrice}"</#if> required>
            <@spring.showErrors " " />
            <br>
            <@spring.bind "good.gNum" />
            <label for="gNum">商品库存</label>
            <input type="text" name="gNum" id="gNum" class="form-control" placeholder="输入商品库存数量"
                   <#if goodname?? >value="${good.GNum}"</#if> required>
            <@spring.showErrors " " />
            <br>
            <button class="btn btn-lg btn-primary btn-block" type="submit">提交</button>
            <@spring.formHiddenInput "good.gId", 'name="gId" value="${good.GId}"'/>
        </form>
    </div>
</div>
<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
<script src="https://cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js"></script>
<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"></script>
</body>
</html>