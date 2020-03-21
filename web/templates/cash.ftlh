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
        <div class="col-lg-4 col-lg-offset-4 text-center">收银员: <span id="saleman">${saleman}</span></div>
        <div class="col-lg-6 col-lg-offset-3" style="margin-top: 40px">
            <input class="col-lg-6 col-lg-offset-3" type="text" placeholder="请输入商品" id="searchKey">
            <button type="button" class="btn btn-default" aria-label="Left Align" id="search-good-button">
                <span class="glyphicon glyphicon-arrow-right" aria-hidden="true"></span>
            </button>
            <button type="button" class="btn btn-default" aria-label="Left Align" id="add-good">
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
            </button>
            <div class="candidates"></div>
        </div>
        <div class="row col-md-8 col-md-offset-2" style="margin-top: 40px">
            <table class="table">
                <thead>
                <tr>
                    <th>商品</th>
                    <th>商品id</th>
                    <th>购买数量</th>
                    <th>删除</th>
                </tr>
                </thead>
                <tbody>

                </tbody>
            </table>

        </div>

    </div>
    <div class="row col-lg-8 col-lg-offset-2 " >
        <div class="col-lg-2 col-lg-offset-8 text-center" style="line-height: 2;"><b>总计<span>12.99</span>元</b></div>
        <div><button type="button" class="btn btn-primary ">结账</button> </div>
    </div>
    <!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
    <script src="https://cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js"></script>
    <!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"></script>
    <script>
        var searchData;
        $("#search-good-button").click(function () {
            var str = $("#searchKey").val();
            $.get("/goods/" + str + "?pattern=fuzzy", function (data, status) {
                if (status === "success") {
                    if(data !=null){
                        searchData = data;
                        $(".candidates").html("");
                        for(elemt in data){
                            $(".candidates").append("<div onclick=\"select("+ elemt + ")\" >" + data[elemt].gName + "</div>");
                        }
                    }else{
                        $(".candidates").html("");
                    }
                }
            })
        });
        function select (index) {
            $("tbody").append("<tr>\n" +
                "                    <td>"+ searchData[index].gName +"</td>\n" +
                "                    <td>"+ searchData[index].gId +"</td>\n" +
                "                    <td><div class=\"col-lg-6\"><input class=\"form-control\" type=\"number\" min=\"1\" max=\""+ searchData[index].gNum +"\"></div></td>\n" +
                "                    <td>\n" +
                "                        <button type=\"button\" class=\"btn btn-default\" aria-label=\"Left Align\" id=\"delete-good\">\n" +
                "                            <span class=\"glyphicon glyphicon-minus\" aria-hidden=\"true\"></span>\n" +
                "                        </button>\n" +
                "                    </td>\n" +
                "                </tr>");
            searchData = null;
            $(".candidates").html("");
            $("#searchKey").val("");
        };
    </script>
    </body>
</html>

<#--<tr>-->
<#--    <td>豆豆</td>-->
<#--    <td>153341</td>-->
<#--    <td>-->
<#--        <div class="col-lg-6">-->
<#--            <input class="form-control" type="number" min="1" max="5">-->
<#--        </div>-->
<#--    </td>-->
<#--    <td>-->
<#--        <button type="button" class="btn btn-default" aria-label="Left Align" id="delete-good">-->
<#--            <span class="glyphicon glyphicon-minus" aria-hidden="true"></span>-->
<#--        </button>-->
<#--    </td>-->
<#--</tr>-->