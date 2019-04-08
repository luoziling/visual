var PersonalCenter = {
    data: {
        contextPath: null,
        // user:sessionStorage.getItem("current_user"),
        user: null,
        jsGender: false,
    },

    URL: {
        saveInfoUrl: function () {
            return PersonalCenter.data.contextPath + "/user/api/saveInfo";
        },
        savePasswordUrl: function () {
            return PersonalCenter.data.contextPath + "/user/api/savePassword";
        },
        uploadImgUrl: function () {
            return PersonalCenter.data.contextPath + "/user/api/uploadImg";
        },

    },

    init: function (contextPath, user) {

        PersonalCenter.data.contextPath = contextPath;
        // console.log(contextPath);

        PersonalCenter.data.user = user;
        console.log(user);
        // alert(user);
        // var user1 = [[${#httpServletRequest.getSession().getAttribute('current_user')}]];
        // console.log(user1);
        // console.log([[${"current_user"}]]);


        // var user =${session."current_user"};
        // var user = sessionStorage.getItem("current_user");

        // var gender = user.get("gender");
        var gender = user['gender'];
        // alert(gender);
        //gender 0是男（false） 1是女（true）数据库中是tinyint
        if (gender) {
            $('.buttonText').text('女');
            PersonalCenter.data.jsGender = true;
        } else if (!gender) {
            $('.buttonText').text('男');
            PersonalCenter.data.jsGender = false;
        } else {
            $('.buttonText').text('男');
            PersonalCenter.data.jsGender = false;
        }
        // var birthday = user.get("birthday");
        var birthday = user['birthday'];
        console.log(birthday);
        // $('.form-control').text(birthday);
        $('#birthday').val(birthday)

        /**
         * 性别和生日的修改
         * */
        $("#saveInfo").click(function () {
            PersonalCenter.saveInfo();
        });

        /**
         * 密码的修改
         * */
        $('#savePassword').click(function () {
            PersonalCenter.savePassword();
        });

        /**
         * 上传图片
         * */
        $("#inputImage").fileinput({
            //        language: 'zh', //设置语言
            //        uploadUrl: uploadUrl, //上传的地址
            //        allowedFileExtensions: ['jpg', 'gif', 'png'],//接收的文件后缀
            //        showUpload: true, //是否显示上传按钮
            //        showCaption: false,//是否显示标题
            //        browseClass: "btn btn-primary", //按钮样式
            //        //dropZoneEnabled: false,//是否显示拖拽区域
            //        //minImageWidth: 50, //图片的最小宽度
            //        //minImageHeight: 50,//图片的最小高度
            //        //maxImageWidth: 1000,//图片的最大宽度
            //        //maxImageHeight: 1000,//图片的最大高度
            //        //maxFileSize: 0,//单位为kb，如果为0表示不限制文件大小
            //        //minFileCount: 0,
            //        maxFileCount: 10, //表示允许同时上传的最大文件个数
            //        enctype: 'multipart/form-data',
            //        validateInitialCount:true,
            //        previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
            //        msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",

            uploadUrl: PersonalCenter.URL.uploadImgUrl(),
            showUpload: true,
            showRemove: false,
            language: 'zh',
            allowedPreviewTypes: ['image'],
            allowedFileExtensions: ['jpg', 'png', 'gif'],
            //        maxFileSize : 2000,
            maxFileSize: 6000,
        }).on('filepreupload', function (event, data, previewId, index) {     //上传中
            console.log('文件正在上传');
        }).on("fileuploaded", function (event, data, previewId, index) {    //一个文件上传成功
            var form = data.form, files = data.files, extra = data.extra,
                response = data.response, reader = data.reader;
            console.log(response);//打印出返回的json
            console.log(response.status);//打印出路径
        }).on('fileerror', function (event, data, msg) {  //一个文件上传失败
            console.log('文件上传失败！' + data.status);
        });

        // 文件上传框
        // $('#inputImage').each(function() {
        //     var imageurl = $(this).attr("value");
        //
        //     if (imageurl) {
        //         var op = $.extend({
        //             initialPreview : [ // 预览图片的设置
        //                 "<img src='" + imageurl + "' class='file-preview-image'>", ]
        //         }, fileinput());
        //
        //         $(this).fileinput(op);
        //     } else {
        //         $(this).fileinput(projectfileoptions);
        //     }
        // });


    },

    //下拉菜单
    shows: function (a) {
        $('.buttonText').text(a);
        console.log(a);
        if (a == '男') {
            PersonalCenter.data.jsGender = false;
        } else if (a == '女') {
            PersonalCenter.data.jsGender = true;
        } else {
            PersonalCenter.data.jsGender = false;
        }
    },


    /**
     * 性别和生日的修改
     * */

    saveInfo: function () {
        var gender = PersonalCenter.data.jsGender;
        console.log(gender);
        var birthday = $('#birthday').val();
        console.log(birthday);

        //ajax
        $.ajax({
            type: "POST",
            url: PersonalCenter.URL.saveInfoUrl(),
            dataType: "json",
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify({
                gender: gender,
                birthday: birthday
            }),
            success: function (result) {
                console.log(result['success']);
                if (result && result['success']) {
                    alert("保存成功!");
                    //TODO:这边也要重新获取信息

                }

            },
            error: function () {
                alert("保存失败");
            }
        });

    },

    /**
     * 密码的修改
     * */
    savePassword: function () {

        var user = PersonalCenter.data.user;

        var oldPassword = $('#oldPassword').val();
        var newPassword = $('#newPassword').val();
        var conformPassword = $('#conformPassword').val();

        //密码不同则重加载界面
        if (!newPassword == conformPassword) {
            alert("两次新密码输入不同");
            window.location.reload();
        } else if (!(oldPassword == user['password'])) {
            alert("输入的旧密码错误");
            window.location.reload();
        } else {
            //ajax
            $.ajax({
                type: "POST",
                url: PersonalCenter.URL.savePasswordUrl(),
                dataType: "json",
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify({
                    newPassword: newPassword
                }),
                success: function (result) {
                    console.log(result['success']);
                    if (result && result['success']) {
                        alert("保存成功!");
                        //TODO:这个要做个请重新登陆
                        // window.location.href = "/";
                        //或者后台把user的session清空然后重载；
                        window.location.reload();

                    }

                },
                error: function () {
                    alert("保存失败");
                }
            });

        }


    },


};