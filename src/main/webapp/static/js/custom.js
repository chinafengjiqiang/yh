/* JS */


/* Navigation */

$(document).ready(function(){

  $(window).resize(function()
     {
     /*if($(window).width() >= 765){
     $(".sidebar #nav").slideDown(350);
     }
     else{
     $(".sidebar #nav").slideUp(350);
     }*/
     });


  $("#nav > li > a").on('click',function(e){
      if($(this).parent().hasClass("has_sub")) {
        e.preventDefault();
      }   

      if(!$(this).hasClass("subdrop")) {
        // hide any open menus and remove all other classes
        $("#nav li ul").slideUp(350);
        $("#nav li a").removeClass("subdrop");
        
        // open our new menu and add the open class
        $(this).next("ul").slideDown(350);
        $(this).addClass("subdrop");
      }
      
      else if($(this).hasClass("subdrop")) {
        $(this).removeClass("subdrop");
        $(this).next("ul").slideUp(350);
      } 
      
  });
});

$(document).ready(function(){
  $(".sidebar-dropdown a").on('click',function(e){
      e.preventDefault();

      if(!$(this).hasClass("open")) {
        // hide any open menus and remove all other classes
        $(".sidebar #nav").slideUp(350);
        $(".sidebar-dropdown a").removeClass("open");
        
        // open our new menu and add the open class
        $(".sidebar #nav").slideDown(350);
        $(this).addClass("open");
      }
      
      else if($(this).hasClass("open")) {
        $(this).removeClass("open");
        $(".sidebar #nav").slideUp(350);
      }
  });

});

/* Widget close */

$('.wclose').click(function(e){
  e.preventDefault();
  var $wbox = $(this).parent().parent().parent();
  $wbox.hide(100);
});

/* Widget minimize */

  $('.wminimize').click(function(e){
    e.preventDefault();
    var $wcontent = $(this).parent().parent().next('.widget-content');
    if($wcontent.is(':visible')) 
    {
      $(this).children('i').removeClass('icon-chevron-up');
      $(this).children('i').addClass('icon-chevron-down');
    }
    else 
    {
      $(this).children('i').removeClass('icon-chevron-down');
      $(this).children('i').addClass('icon-chevron-up');
    }            
    $wcontent.toggle(500);
  }); 

/* Progressbar animation */

    setTimeout(function(){

        $('.progress-animated .progress-bar').each(function() {
            var me = $(this);
            var perc = me.attr("data-percentage");

            //TODO: left and right text handling

            var current_perc = 0;

            var progress = setInterval(function() {
                if (current_perc>=perc) {
                    clearInterval(progress);
                } else {
                    current_perc +=1;
                    me.css('width', (current_perc)+'%');
                }

                me.text((current_perc)+'%');

            }, 600);

        });

    },600);


/* Support */

$(document).ready(function(){
  $("#slist a").click(function(e){
     e.preventDefault();
     $(this).next('p').toggle(200);
  });
});

/**
 * 将form表单元素的值序列化成对象
 *
 * @example fq($('#formId'))
 *
 * @author fq
 *
 * @requires jQuery
 *
 * @returns object
 */
fq.serializeObject = function(form) {
    var o = {};
    $.each(form.serializeArray(), function(index) {
        if (this['value'] != undefined && this['value'].length > 0) {// 如果表单项的值非空，才进行序列化操作
            if (o[this['name']]) {
                o[this['name']] = o[this['name']] + "," + this['value'];
            } else {
                o[this['name']] = this['value'];
            }
        }
    });
    return o;
};

/*
  form submit
  formId:要提交的formId
  url ：数据要提交到的地址
  table ： form所在的datatables
*/

function submitForm(formId,url,table,modal) {
    //定义添加或修改的主键
    url = fq.contextPath + "/"+url;
    $.post(url, fq.serializeObject($('#'+formId)), function(result) {
        if (result.success) {
            table.ajax.reload( null, false ); // 刷新表格数据，分页信息不会重置
            modal.modal('hide')
        } else {
            Alert("操作失败！");
        }

    }, 'json');
}

function submitForm(formId,url,table,modal,callback) {
    //定义添加或修改的主键
    url = fq.contextPath + "/"+url;
    $.post(url, fq.serializeObject($('#'+formId)), function(result) {
        if (result.success) {
            table.ajax.reload( null, false ); // 刷新表格数据，分页信息不会重置
            modal.modal('hide');
            //if (typeof(reValue) != "undefined") {
                callback();
            //}
        } else {
            Alert("操作失败！");
        }

    }, 'json');
}
/*
 form submit
 formId:要提交的formId
 url ：数据要提交到的地址
 refFun ： 加载后需要刷新的函数
 */

function submitFormWRef(formId,url,refFun,modal) {
    url = fq.contextPath + "/"+url;
    $.post(url, fq.serializeObject($('#'+formId)), function(result) {
        if (result.success) {
            //table.ajax.reload( null, false ); // 刷新表格数据，分页信息不会重置
            refFun;
            modal.modal('hide')
        } else {
            Alert("操作失败！");
        }

    }, 'json');
}

/*
 通用批量删除类
 tableName ： 删除数据的表名
 pkName ： 删除数据的主键
 table ： 删除数据所在的DataTables列表
 */
function delBatch(tableName,pkName,table) {
    var delData = table.rows('.selected').data();
    if(delData.length <= 0){
        Alert("请选择要删除的行！");
        return false;
    }

    var ids = [];
    $.each(delData,function (i,obj) {
        ids.push(obj[pkName])
    });
    del(tableName,pkName,table,ids.join(","));

}


function delBatch(tableName,pkName,table,callback) {
    var delData = table.rows('.selected').data();
    if(delData.length <= 0){
        Alert("请选择要删除的行！");
        return false;
    }

    var ids = [];
    $.each(delData,function (i,obj) {
        ids.push(obj[pkName])
    });
    del(tableName,pkName,table,ids.join(","),callback);

}

/*
 通用单个删除类
 tableName ： 删除数据的表名
 pkName ： 删除数据的主键
 table ： 删除数据所在的DataTables列表
 ids : 要删除的主键ID
 */
function del(tableName,pkName,table,ids) {
    var deferred = $.Deferred();
    Confirm({
        msg: '确定要删除数据？',
        onOk: function(){
            delOpt(ids,tableName,pkName,table)
        },
        onCancel: function(){
            deferred.reject();
        }
    })
}

function del(tableName,pkName,table,ids,callback) {
    var deferred = $.Deferred();
    Confirm({
        msg: '确定要删除数据？',
        onOk: function(){
            delOptCallback(ids,tableName,pkName,table,callback)
        },
        onCancel: function(){
            deferred.reject();
        }
    })
}

function delOpt(ids,tableName,pkName,table){
    var url = fq.contextPath + "/del";
    $.post(url, {"ids":ids,"tableName":tableName,"pk":pkName}, function () {
        table.ajax.reload(null, false); // 刷新表格数据，分页信息不会重置
    }, 'json');
}

function delOptCallback(ids,tableName,pkName,table,callback){
    var url = fq.contextPath + "/del";
    $.post(url, {"ids":ids,"tableName":tableName,"pk":pkName}, function () {
        table.ajax.reload(null, false); // 刷新表格数据，分页信息不会重置
        //if (typeof(reValue) != "undefined") {
            callback();
        //}
    }, 'json');
}


/*
 批量删除类
 tableName ： 删除数据的表名
 pkName ： 删除数据的主键
 table ： 删除数据所在的DataTables列表
 */
function delBatchWRef(tableName,pkName,table,refFun) {
    var delData = table.rows('.selected').data();
    if(delData.length <= 0){
        Alert("请选择要删除的项！");
        return false;
    }

    var ids = [];
    $.each(delData,function (i,obj) {
        ids.push(obj[pkName])
    });
    delWRef(tableName,pkName,ids.join(","),refFun);

}
/*
 单个删除类
 tableName ： 删除数据的表名
 pkName ： 删除数据的主键
 table ： 删除数据所在的DataTables列表
 ids : 要删除的主键ID
 */
function delWRef(tableName,pkName,ids,refFun) {
    var deferred = $.Deferred();
    Confirm({
        msg: '确定要删除数据？',
        onOk: function(){
            delOptWRef(ids,tableName,pkName,refFun);
        },
        onCancel: function(){
            deferred.reject();
        }
    })
}


function delOptWRef(ids,tableName,pkName,refFun){
    var url = fq.contextPath + "/del";
    $.post(url, {"ids":ids,"tableName":tableName,"pk":pkName}, function () {
        refFun;
    }, 'json');
}





/*
    下拉列表控件
 */
function attachSelectBox(oInputField,value,url){
    jQuery.getJSON(url,{},function(data){
        for (var i=0;i<data.length;i++){
            option = document.createElement('Option');
            option.text = data[i].iacMap.NAME;
            option.value = data[i].iacMap.NID;
            try{
                oInputField.add(option,null);
            }catch(ex){
                oInputField.add(option);
            }
            if (option.value==value){
                option.selected=true;
            }
        }
    });
}

//checkbox框只让其提交0或1
function attachCheckBox(oCheckField,oHiddenField){
    if (jQuery(oHiddenField).val()==1){
        oCheckField.checked = true;
    }else{
        oCheckField.checked = false;
    }
    jQuery(oCheckField).click(function(){
        if (oCheckField.checked)
            jQuery(oHiddenField).val("1");
        else
            jQuery(oHiddenField).val("0");
    });
}

/**
 * 编辑时获取tbody中列表的字段值
 * @param mTbody : 列表所在tbody
 * @param pos ： 字段在列表中的位置
 * @returns {*|jQuery}
 */
function getTbodyValue(mTbody,pos){
    return $('td', mTbody.parentNode.parentNode).eq(pos).text();
}


function getDicList(type) {
    var url = fq.contextPath+"/dic";
    var map = new Map();
    jQuery.getJSON(url,{type:type},function(data){
        for (var i=0;i<data.length;i++) {
            var name = data[i].iacMap.NAME;
            var nid = data[i].iacMap.NID;
            alert("put :: "+name +"  "+nid);
            map.put(nid,name);
        }
    });
    return map;
}


function Map() {
    this.keys = new Array();
    this.data = new Array();
//添加键值对
    this.put = function (key, value) {
        if (this.data[key] == null) {//如键不存在则身【键】数组添加键名
            this.keys.push(key);
        }
        this.data[key] = value;//给键赋值
    };
//获取键对应的值
    this.get = function (key) {
        return this.data[key];
    };
//去除键值，(去除键数据中的键名及对应的值)
    this.remove = function (key) {
        this.keys.remove(key);
        this.data[key] = null;
    };
//判断键值元素是否为空
    this.isEmpty = function () {
        return this.keys.length == 0;
    };
//获取键值元素大小
    this.size = function () {
        return this.keys.length;
    };
    
    this.getKey = function (value) {
        alert("value is ::"+this.keys);
        $.each(this.data, function(key, val) {
            alert(val);
           if(val == value){
               alert("key::"+key);
               alert(this.keys.get(key));
               return this.keys[key];
           }
        });
    }
}

