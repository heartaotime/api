class Utils {

    static setVue(Vue) {
        this.Vue = Vue
        this.Utils = this
    }

    /**
     * 判断字符串是否为空
     * @param str
     * @returns {boolean}
     */
    static isNull(str) {
        return str == null || str.length === 0 || str === ''
    }

    /**
     *
     * @desc  判断是否为身份证号
     * @param  {String|Number} str
     * @return {Boolean}
     */
    static isIdCard(str) {
        return /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/.test(str)
    }

    /**
     *
     * @desc   判断是否为手机号
     * @param  {String|Number} str
     * @return {Boolean}
     */
    static isPhoneNum(str) {
        return /^(0|86|17951)?(1[3-9][0-9])[0-9]{8}$/.test(str)
    }

    static postJson(url, data, config) {
        let defaultConfig = {
            headers: {
                'Cache-Control': 'no-cache'
            }
        }
        if (config) {
            defaultConfig = config
        }

        // if (url.indexOf('setUserExtInfo') < 0) {
        //     this.showLoading();
        // }

        let loadingDom = this.Vue.$loading()

        return new Promise((resolve, reject) => {
            window.console.info('req url, ', url, '\nreq param, ', data)
            this.Vue.axios.post(url, data, defaultConfig).then(res => {
                loadingDom.remove()
                window.console.info('get resopnse sucess, ', res.data)
                resolve(res.data)
            }).catch(error => {
                loadingDom.remove()
                window.console.error('axios post error, ', error)
                reject(error)
            })
        })
    }

    static getStream(url, params) {
        return new Promise((resolve, reject) => {
            this.Vue.axios.get(url, {
                params: params,
                responseType: 'arraybuffer'
            }).then(response => {
                return 'data:image/png;base64,' + btoa(new Uint8Array(response.data)
                    .reduce((data, byte) => data + String.fromCharCode(byte), '')
                )
            }).then(data => {
                resolve(data)
            }).catch(error => {
                window.console.error('axios post error, ', error)
                reject(error)
            })

        })
    }

    // static postJson(url, data, callback) {
    //     window.console.log('req url, ', url, '\nreq param, ', data);
    //     axios.post(url, data).then(function (response) {
    //         window.console.log('get resopnse sucess, ', response.data);
    //         callback(response.data);
    //     }).catch(function (error) {
    //         window.console.error('axios post error, ', error);
    //     });
    // }

    static getJson(url, data) {
        let loadingDom = this.Vue.$loading()
        return new Promise((resolve, reject) => {
            // window.console.info('req url, ', url, '\nreq param, ', data);
            this.Vue.axios.get(url, {params: data}).then(res => {
                loadingDom.remove()
                // window.console.info('get resopnse sucess, ', res.data);
                resolve(res.data)
            }).catch(error => {
                loadingDom.remove()
                window.console.error('axios getJson error, ', error)
                reject(error)
            })
        })
    }

    // static getJson(url, data, callback) {
    //     window.console.log('req url, ', url, '\nreq param, ', data);
    //     this.Vue.axios.get(url, {params: data}).then(function (response) {
    //         window.console.log('get resopnse sucess, ', response.data);
    //         callback(response.data);
    //     }).catch(function (error) {
    //         window.console.error('axios getJson error, ', error);
    //     });
    // }

    static setUserInfo(openUserInfo) {
        if (localStorage) {
            localStorage.setItem('openUserInfo', JSON.stringify(openUserInfo))
        }
    }

    static removeUserInfo() {
        if (localStorage) {
            localStorage.removeItem('openUserInfo')

        }
        window.location.reload()
    }

    static clearLocalStorage() {
        if (localStorage) {
            localStorage.clear()
        }
        window.location.reload()
    }

    static getReqParam(name) {
        const reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)')
        const r = window.location.search.substr(1).match(reg)//search,查询？后面的参数，并匹配正则
        if (r != null) return unescape(r[2])
        return null
    }

    static convertToJson(result) {
        for (let i = 0; i < result.length; i++) {
            const extInfo = result[i].extInfo
            if (extInfo && extInfo !== '') {
                result[i].extInfo = JSON.parse(extInfo)
                // that.$set(result[i], 'extInfo', JSON.parse(extInfo));
                Utils.convertToJson(extInfo)
            }
        }
    }

    static getYYYYMMDD() {
        const date = new Date()
        const year = date.getFullYear()
        const month = date.getMonth() > 9 ? date.getMonth() : '0' + date.getMonth()
        const day = date.getDate()
        return year + '' + month + '' + day
    }

    static generateUUID() {
        let d = new Date().getTime()
        if (window.performance && typeof window.performance.now === 'function') {
            d += performance.now() //use high-precision timer if available
        }
        const uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
            const r = (d + Math.random() * 16) % 16 | 0
            d = Math.floor(d / 16)
            return (c === 'x' ? r : (r & 0x3 | 0x8)).toString(16)
        })
        // return uuid.replaceAll('-', '')
        return uuid
    }

    static scrollToNav() {
        let scrollNav = document.getElementById('scrollNav')
        let endPoint = scrollNav.offsetTop - 80
        window.scrollTo({
            top: endPoint,
            behavior: 'smooth'
        })
    }

    static getActiveIndex(list) {
        for (let i = 0; i < list.length; i++) {
            if (list[i].active) {
                return i
            }
        }
        return 0
    }

    static getActiveGuideCode(list) {
        for (let i = 0; i < list.length; i++) {
            if (list[i].active) {
                return list[i].guideCode
            }
        }
        return ''
    }

    static uCardStyle(cardTransparency) {
        let elements = document.querySelectorAll('.card')
        for (let i = 0; i < elements.length; i++) {
            elements[i].style.backgroundColor = 'rgba(255, 255, 255, ' + (1 - cardTransparency / 10) + ')'
        }
    }

    static pop(config) {
        // history.pushState({page: 1}, config.title, "?page=" + config.componentName);
        // history.pushState(null, config.title, null);
        this.Vue.$store.commit('uPopConfig', config)
    }

    static closePop() {
        // 只更新 show 一个 字段为 false， 其他 字段 置空（componentName），可以保证每次点击显示时，总是能重新加载对应的子组件
        this.Vue.$store.commit('uPopConfig', {
            show: false
        })
    }

    static tips(title, content, msgType) {
        this.Vue.$store.commit('uTipsConfig', {
            show: true,
            title: title,
            content: content,
            msgType: msgType
        })
    }

    static successTips(content) {
        this.Vue.$store.commit('uTipsConfig', {
            show: true,
            title: '成功',
            content: content,
            msgType: 'success'
        })
    }

    static warnTips(content) {
        this.Vue.$store.commit('uTipsConfig', {
            show: true,
            title: '警告',
            content: content,
            msgType: 'warn'
        })
    }

    static errorTips(content) {
        this.Vue.$store.commit('uTipsConfig', {
            show: true,
            title: '失败',
            content: content,
            msgType: 'error'
        })
    }

    static showLoading() {
        this.Vue.$store.commit('uLoadingConfig', {
            show: true
        })
    }

    static hideLoading() {
        this.Vue.$store.commit('uLoadingConfig', {
            show: false
        })
    }

    static saveUserInfoExt() {
        // window.console.log(this.Vue.$store.getters.openUserInfo);

        // 保存数据到数据库
        let url = '/common-server/user/api/v1/saveUserInfoExt'
        // openUserInfo.user.id

        let data = {
            param: {
                userId: this.Vue.$store.getters.openUserInfo.user.id,
                saveData: this.Vue.$store.getters.openUserInfo
            }
        }
        this.Vue.axios.post(url, data).then(res => {
            let response = res.data
            if (!response || response.code !== '0') {
                this.errorTips('同步失败')
                return
            }
            this.successTips('同步成功')
            // window.console.log(response);
        }).catch(error => {
            this.errorTips(error)
            window.console.error('axios post error, ', error)
        })
    }

    static getCommonReq(param) {
        return {
            // appCode: 'b198c18f49354a51984581cc124d985b',
            appCode: 'f0fb4bed6b664cfebbb30a688a4c8e3f',
            param
        }
    }

    static host() {
        return 'https://www.ystel.cn/'
    }

    static basicUrl() {
        return 'https://www.ystel.cn/api'
        // return 'http://127.0.0.1:5555/api';
        // return 'http://139.186.66.121/api';
    }

    static isPhone() {
        let clientWidth = document.body.clientWidth // 网页可见区域宽
        if (clientWidth > 600) {
            return false
        }
        return true
    }

    static upLoadFile(ele) {
        if (ele.value === '') {
            return
        }
        let appCode = this.getCommonReq({}).appCode

        let param = new FormData()
        param.append('appCode', appCode)
        // 通过append向form对象添加数据
        param.append('file', ele.files[0])
        // FormData私有类对象，访问不到，可以通过get判断值是否传进去
        // console.log(param.get("file"));

        let config = {
            //添加请求头
            headers: {'Content-Type': 'multipart/form-data'},
            //添加上传进度监听事件
            onUploadProgress: e => {
                var completeProgress = ((e.loaded / e.total * 100) | 0) + '%'
                console.log(completeProgress)
                // this.progress = completeProgress;
            }
        }

        return new Promise((resolve, reject) => {
            this.postJson('https://www.ystel.cn/api/common/v1/upload', param, config).then(response => {
                if (!response || response.code !== '0') {
                    this.Vue.$toast.error(response.message)
                    return
                }
                resolve(response.data)

                // 把 上传文件的 file 框 置空
                ele.value = ''
            }).catch(error => {
                reject(error)
            })
        })
        // this.postJson('https://www.myindex.top/api/common/v1/upload', param, config).then(response => {
        //     if (!response || response.code !== '0') {
        //         this.Vue.$toast(response.message);
        //         return;
        //     }
        //     url(response.data);
        // });
    }

    static isType(obj) {
        var type = Object.prototype.toString.call(obj)
        if (type == '[object Array]') {
            return 'Array'
        } else if (type == '[object Object]') {
            return 'Object'
        } else {
            return 'param is no object type'
        }
    }

    static isObject(obj) {
        var type = Object.prototype.toString.call(obj)
        if (type == '[object Object]') {
            return true
        }
        return false
    }

    static isArray(obj) {
        var type = Object.prototype.toString.call(obj)
        if (type == '[object Array]') {
            return true
        }
        return false
    }

    static isString(obj) {
        var type = Object.prototype.toString.call(obj)
        if (type == '[object String]') {
            return true
        }
        return false
    }

    static isNumber(obj) {
        var type = Object.prototype.toString.call(obj)
        if (type == '[object Number]') {
            return true
        }
        return false
    }

    static isBoolean(obj) {
        var type = Object.prototype.toString.call(obj)
        if (type == '[object Boolean]') {
            return true
        }
        return false
    }

    static isUndefined(obj) {
        var type = Object.prototype.toString.call(obj)
        if (type == '[object Undefined]') {
            return true
        }
        return false
    }

    static isDate(obj) {
        var type = Object.prototype.toString.call(obj)
        if (type == '[object Date]') {
            return true
        }
        return false
    }

    static go2Link(url, target) {
        let a = document.createElement('a')
        a.href = url
        if (target) {
            a.target = target
        }
        a.click()
        a.remove()
    }

    static submitForm(url, params, method = 'post') {
        //创建form元素并设置其相关属性。参数url：表单提交去向；参数target：链接打开方式。
        let tempForm = document.createElement('form')
        tempForm.action = url
        tempForm.method = method
        tempForm.style.display = 'none'
        // if (target) {
        //   tempForm.target = target
        // }
        //创建input元素并设置提交的数据。参数params：提交的数据（JSON结构）。
        for (let x in params) {
            let ipt = document.createElement('input')
            ipt.name = x
            ipt.value = params[x]
            tempForm.appendChild(ipt)
        }
        //创建提交按钮元素
        let ipt = document.createElement('input')
        ipt.type = 'submit'
        tempForm.appendChild(ipt)
        //创建->提交->删除
        document.body.appendChild(tempForm)
        // console.log('开始提交')
        tempForm.submit()
        // console.log('提交完成')
        document.body.removeChild(tempForm)
    }

    static exportExcel(req) {
        if (!req.pageSize) {
            req.pageSize = '10000'
        }
        req.uuid = Utils.generateUUID()
        console.log(req)
        // 调用接口导出
        Utils.submitForm("../export/excel", {req: JSON.stringify(req)})
    }

    static exportCsv(req) {
        req.pageSize = '10000'
        req.uuid = this.Utils.generateUUID()
        console.log(req)

        // 调用接口导出
        this.Utils.submitForm(process.env.VUE_APP_BASE_API + '/export/csv', {req: JSON.stringify(req)})

        // //创建内存中的表单对象
        // var form = new FormData()
        // //向其中添加要传输的数据
        // form.append('req', JSON.stringify(req))
        // axios.post(process.env.VUE_APP_BASE_API + '/export/csv', form
        //   , {
        //     responseType: 'blob'
        //   }).then(res => {
        //   let blob = res.data
        //   let reader = new FileReader()
        //   reader.readAsDataURL(blob)
        //   reader.onload = (e) => {
        //     let a = document.createElement('a')
        //     a.download = '文件名'
        //     a.href = e.target.result
        //     document.body.appendChild(a)
        //     a.click()
        //     document.body.removeChild(a)
        //   }
        // })

        // var formdata = new FormData()
        // formdata.append('req', JSON.stringify(req))
        // var xhr = new XMLHttpRequest()
        // xhr.open('post', process.env.VUE_APP_BASE_API + '/export/csv')
        // xhr.send(formdata)
        // xhr.onload = function() {
        //   if (this.status == 200) {
        //     // //接受二进制文件流
        //     // var blob = this.response
        //     // //封装成对应的文件对象
        //     // blob.name = this.filename
        //     // that.files.push(blob)
        //     // var node = $('<div class="filelist-item  col-xs-2"/>')
        //     // node.append(that.closeButton.clone(true))
        //     // //将二进制数据流转换成一个url
        //     // node.prepend($('<img style="height:40px;width:40px"/>').attr('src', window.URL.createObjectURL(blob)))
        //     //   .on('click', function(e) {
        //     //     window.open($(e.target).attr('src'))
        //     //   })
        //     // node.append('<div class="info">' + this.filename + '</div>')
        //     // node.prependTo(that.fileList)
        //
        //     // var content = "Blob Data";
        //     // var blob = new Blob([content]);
        //     var blob = this.response
        //     let link = document.createElement('a')
        //     link.download = 'file'
        //     link.href = URL.createObjectURL(blob)
        //   }
        // }

        // 调用接口导出
        // this.Utils.go2Link(process.env.VUE_APP_BASE_API + '/export/csv?req=' + encodeURIComponent(JSON.stringify(req)))

        // 展示进度条
        this.Vue.$progress.start()

        // 每隔1s 获取下当前导出的进度
        // 启动定时器
        let intervalFun = setInterval(() => {
            getProgress({
                data: req.uuid
            }).then(response => {
                console.log(response.data)
                this.Vue.$progress.setProgress(parseInt(response.data))
                if (response.data === '100') {
                    if (intervalFun && intervalFun != null) {
                        clearInterval(intervalFun)
                    }
                    setTimeout(() => {
                        this.Vue.$progress.end()
                    }, 1000)
                }
            }).catch(err => {
                console.log(err)
                if (intervalFun && intervalFun != null) {
                    clearInterval(intervalFun)
                }
                setTimeout(() => {
                    this.Vue.$progress.end()
                }, 1000)
            })
        }, 1000)

    }

    static scrollToTop() {
        window.scrollTo({
            top: 0,
            behavior: 'smooth'
        })
    }

    static go2Router(name) {
        this.Vue.$router.push({name})
    }

    static convert(json) {
        return JSON.parse(JSON.stringify(json))
    }

    static refresh() {
        window.location.reload()
    }

    static compreObj(obj1, obj2) {
        // 判断类型是否一致
        if (
            (this.isUndefined(obj1) && !this.isUndefined(obj2))
            || (this.isObject(obj1) && !this.isObject(obj2))
            || (this.isArray(obj1) && !this.isArray(obj2))
            || (this.isString(obj1) && !this.isString(obj2))
            || (this.isNumber(obj1) && !this.isNumber(obj2))
            || (this.isBoolean(obj1) && !this.isBoolean(obj2))
            || (this.isDate(obj1) && !this.isDate(obj2))
        ) {
            console.log('compreObj:类型不一致')
            return false
        }

        if (this.isUndefined(obj1)) {
            return true
        }

        let result = true

        if (this.isObject(obj1)) {
            // 先判断 对象 key 的个数是否一致
            if (Object.keys(obj1).length !== Object.keys(obj2).length) {
                console.log('compreObj:对象key的个数不一致')
                return false
            }
            for (let key in obj1) {
                if (Object.prototype.hasOwnProperty.call(obj2, key)) {
                    // 获取 对应 key 的 value 判断
                    result = this.compreObj(obj1[key], obj2[key])
                    if (!result) {
                        // 如果 失败 直接 返回即可
                        return result
                    }
                } else {
                    console.log('compreObj:对应的key没有')
                    return false
                }
            }
        } else if (this.isArray(obj1)) {
            // 先判断 数组长度是否一致
            if (obj1.length !== obj2.length) {
                console.log('compreObj:数组长度不一致')
                return false
            }
            for (let i in obj1) {
                result = this.compreObj(obj1[i], obj2[i])
                if (!result) {
                    // 如果 失败 直接 返回即可
                    return result
                }
            }
        } else {
            if (obj1 !== obj2) {
                console.log('compreObj:值不相等')
                return false
            }
        }
        return result
    }

    static formatDate(value, format) {
        if (value && value != '' && value != null) {
            return dayjs(value).format(format)
        }
        return ''
    }

    static isEmpty(param) {
        if (!param || param == null || param === '') {
            return true
        }
        return false
    }

    static isNotEmpty(param) {
        return !Utils.isEmpty(param)
    }

    // static getYear(offset) {
    //     let now = new Date()
    //     let month = now.getMonth()
    //     let year = now.getFullYear()
    //
    //     let nowOffset = new Date(year, month + offset)
    //     // console.log(nowOffset);
    //     let monthOffset = nowOffset.getMonth() + 1
    //     let yearOffset = nowOffset.getFullYear()
    //
    //     if (monthOffset < 10) {
    //         monthOffset = '0' + monthOffset
    //     }
    //     this.period = yearOffset + '' + monthOffset
    //     // console.log(this.period);
    // }

    static getYear(offset) {
        var now = new Date();
        var month = now.getMonth();
        var year = now.getFullYear();
        return year + offset;
    }

}
