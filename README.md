# javaWeb
【实验题目】 
设计并实现一个电子商务网站的开发和在线部署. 

【基本功能要求】 
顾客： 用户的注册、登录、注销 购买流程（浏览/查询->添加至购物篮->付款->发送电子邮件确认收货）  
销售： 商品目录的管理（包括最基本的增删改等操作） 后台销售统计报表，销售状态 客户的浏览/购买日志记录  

# 学号：201930344268 杨浩杰

# 文件说明如下：
shop是商城项目，shopManager是商城的管理项目
## 前端
>使用的是Vue3，各依赖如下：  
>>"axios": "^0.24.0",  
>>"core-js": "^3.6.5",  
>>"element-plus": "^1.1.0-beta.24",  
>>"vue": "^3.0.0",  
>>"vue-router": "^4.0.0-0",  
>>"vuex": "^4.0.0-0"  
>util中封装了axios等工具
### shop
>login是登录页  
>register是注册页  
>main是首页，展示商品列表  
>commodity是商品的具体信息页  
>order是购物车和下单页面  
### shopManager
>login是登录页  
>main是首页，包含以下三个子模块  
>>manager商品管理页,包括修改/删除功能(修改时会跳转到modify修改页)  
>>new新增页  
>>summary查看订单页面  

## 后端
>Interact.java封装了gson  
>Email.java封装了发送邮件的相关API  
>DBPool.java封装了数据库连接池相关的API  
### shop
>Login.java包含登录和退出登录的接口  
>User.java包含获取用户信息和新增用户的接口  
>CommodityList.java包含获取商品列表的接口  
>Commodity.java包含获取商品具体信息的接口  
>Order.java包含下单购买的接口  
### shopManager
>Login.java包含登录接口  
>Goods.java包含新增/修改/删除商品的接口  
>OrderList.java包含获取订单信息的接口  

## 数据库
>Query.sql是MySQL建表所用的语句集  
