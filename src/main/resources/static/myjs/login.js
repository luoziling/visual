var login = {
    data: {
        nowTime: null,
        contextPath: null
    },
    //封装url
    URL: {
        homeUrl: function () {
            return login.data.contextPath + "/"
        },

        checkLoginUrl: function () {
            return login.data.contextPath + "/user/api/login"
        },
    },
    /**
     * 初始化可以在这边添加模态框
     * */
    init: function (contextPath) {
        login.data.contextPath = contextPath;
        console.log(contextPath.toString());

        console.log("contextPathL:%s", contextPath);


        /**
         * 记住登陆
         * */
        $('checkbox').checkbox

        /**
         * 初始化数据
         * */
        var username = $.cookie('SatsukiUsername');
        var password = $.cookie('SatsukiPassword');
        $('#Username').val(username);
        $('#Password').val(password);

        console.log("username0 : %s", username);
        //rememberMe的设置属性成功
        // $('#rememberMe').attr("checked",true);
        console.log("password0: %s", password);

        console.log("checklogURL:%s", login.URL.checkLoginUrl());

        /**
         * 登陆按钮触发
         * */
        $('#loginSubmitButton').click(function () {
            console.log("click loginSubmitButton");
            // window.location.href="cover.html";
            login.checkLogin()
            // if(login.checkLogin()){
            //     window.location.href="cover.html";
            // }
        });
        /**
         *记住登陆
         * */
        $('#rememberMe').checkbox();
    },

    /**
     * 验证用户名和密码是否合法
     */
    checkUsernameAndPassword: function (username, password) {
        if (username == null || username == ''
            || username.replace(/(^s*)|(s*$)/g, "").length == 0) {
            // $('#loginModalErrorMessage').html('<i class="close icon"></i><div class="header">错误提示</div>\n' +
            //     '                <p>'+'账号不能为空'+'</p>');
            // $('#loginModalErrorMessage').removeClass('hidden');
            return false;
        }
        if (password == null || password == ''
            || password.replace(/(^s*)|(s*$)/g, "").length == 0) {
            // $('#loginModalErrorMessage').html('<i class="close icon"></i><div class="header">错误提示</div>\n' +
            //     '                <p>'+'密码不能为空'+'</p>');
            // $('#loginModalErrorMessage').removeClass('hidden');
            return false;
        }
        return true;
    },

    /**
     * 验证登录
     */
    checkLogin: function () {
        // window.location.href="/cover.html";
        var flag = false;

        console.log("checkLogin");

        var username = $('#Username').val();
        var password = $('#Password').val();

        console.log("username : %s", username);
        console.log("password: %s", password);
        // alert("事件监听0");


        if (login.checkUsernameAndPassword(username, password)) {
            console.log("准备调用API");

            // alert("事件监听");
            // 调用后端API
            //     $.post(login.URL.checkLoginUrl(),{
            //         username: username,
            //         password: password
            //     },function (result) {
            //         // window.location.href="cover.html";
            //         console.log("message:%s",result.message);
            //         console.log("success:%s",result.success);
            //         alert("事件监听");
            //         if(result&&result['success']){
            //             if($('#rememberMe').is(":checked")){
            //                 //把账号信息写入cookie
            //                 $.cookie('SatsukiUsername',username,{expires: 7, path: '/'});
            //                 $.cookie('SatsukiPassword',password,{expires: 7, path: '/'});
            //             }
            //             //通过验证，刷新页面
            //             // window.location.reload();
            //
            //             //跳转页面
            //             window.location.href="cover.html";
            //         }else {
            //             // window.location.href="cover.html";
            //             //错误提示
            //             console.log(result.message);
            //         }
            //     });
            // }

            if (login.checkUsernameAndPassword(username, password)) {
                $.ajax({
                    type: "POST",
                    url: login.URL.checkLoginUrl(),
                    // url:"/user/api/login",
                    // async: false,
                    dataType: "json",
                    contentType: "application/json;charset=UTF-8",
                    // data:{
                    //     username: username,
                    //     password: password
                    // },
                    data: JSON.stringify({
                        username: username,
                        password: password
                    }),
                    // data: username,
                    success: function (result) {
                        console.log(result['success']);
                        // alert("success");
                        if (result && result['success']) {
                            console.log("success!!!");
                            alert("success1");
                            if ($('#rememberMe').is(":checked")) {
                                //把账号信息写入cookie
                                $.cookie('SatsukiUsername', username, {expires: 7, path: '/'});
                                $.cookie('SatsukiPassword', password, {expires: 7, path: '/'});
                            }

                            // window.location.href="/cover.html";
                            window.location.href = "/PersonalCenter";
                            // return false;
                            // return true;
                            // window.location.href="/CoverTest";
                            // window.location.href="cover.html";
                            // $(window).attr('location',contextPath+"/CoverTest");

                            // window.location.href("cover.html");

                            // $.ajax({
                            //     // type:"GET",
                            //     url:"/CoverTest"
                            // });
                            flag = true;
                            // alert("success2");

                        }

                    },
                    error: function (result) {
                        //todo:失败之后应该提示用户是什么出错了
                        alert("error");
                        console.log("error");
                    }

                });

            }
            // console.log(flag);
            // if(flag==true){
            //     alert("success3");
            //     window.location.href="cover.html";
            // }
        }
        // window.location.href="cover.html";
    }


}