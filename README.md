<html lang="en"><head>
    <meta charset="UTF-8">
    <title></title>
<style id="system" type="text/css">h1,h2,h3,h4,h5,h6,p,blockquote {    margin: 0;    padding: 0;}body {    font-family: "Helvetica Neue", Helvetica, "Hiragino Sans GB", Arial, sans-serif;    font-size: 13px;    line-height: 18px;    color: #737373;    margin: 10px 13px 10px 13px;}a {    color: #0069d6;}a:hover {    color: #0050a3;    text-decoration: none;}a img {    border: none;}p {    margin-bottom: 9px;}h1,h2,h3,h4,h5,h6 {    color: #404040;    line-height: 36px;}h1 {    margin-bottom: 18px;    font-size: 30px;}h2 {    font-size: 24px;}h3 {    font-size: 18px;}h4 {    font-size: 16px;}h5 {    font-size: 14px;}h6 {    font-size: 13px;}hr {    margin: 0 0 19px;    border: 0;    border-bottom: 1px solid #ccc;}blockquote {    padding: 13px 13px 21px 15px;    margin-bottom: 18px;    font-family:georgia,serif;    font-style: italic;}blockquote:before {    content:"C";    font-size:40px;    margin-left:-10px;    font-family:georgia,serif;    color:#eee;}blockquote p {    font-size: 14px;    font-weight: 300;    line-height: 18px;    margin-bottom: 0;    font-style: italic;}code, pre {    font-family: Monaco, Andale Mono, Courier New, monospace;}code {    background-color: #fee9cc;    color: rgba(0, 0, 0, 0.75);    padding: 1px 3px;    font-size: 12px;    -webkit-border-radius: 3px;    -moz-border-radius: 3px;    border-radius: 3px;}pre {    display: block;    padding: 14px;    margin: 0 0 18px;    line-height: 16px;    font-size: 11px;    border: 1px solid #d9d9d9;    white-space: pre-wrap;    word-wrap: break-word;}pre code {    background-color: #fff;    color:#737373;    font-size: 11px;    padding: 0;}@media screen and (min-width: 768px) {    body {        width: 748px;        margin:10px auto;    }}</style><style id="custom" type="text/css"></style></head>
<body marginheight="0"><p>1.本项目是一个网页即时通讯聊天室。支持聊天室群聊以及私聊，发送接收离线消息等功能。如果你想要完整使用本项目须至少进去聊天室一次，才能享受到全部功能。
<br>2.本项目也有JAR包文件，将其导入项目，即可实现你当前项目集成聊天室功能。<br>
3.离线消息读取说明：只有至少进入过一次此系统的用户才有权限读取离线消息，且离线消息只能读一次。<br>
3.项目集成说明：在springboot运行类中SpringApplication.run(DemoApplication.class, args);代码下设置项目IP及端口地
址，如果项目用于内网请输入本机内网IP地址，相应的用于外网，请输入外网IP地址。举例---<br>
        SpringApplication.run(DemoApplication.class, args);
        Dbconstant.setDbrecord(false);
        Constant.setUrl("192.168.10.185:8005");<br>
其中dbrecord属性为true时开启将聊天信息录入数据库功能，但默认值为false。如果需要开启聊天信息记录功能需在之前代码下添加<br>

</p>
<pre><code>    Dbconstant.setDriverClassName("com.mysql.jdbc.Driver");//用于mysql数据库&lt;/br&gt;
    Dbconstant.setUrl("jdbc:mysql://localhost:3306/practicee?characterEncoding=UTF8&amp;useUnicode=true&amp;useSSL=false");//设置消息存于哪个数据库，且数据库中必须有message表，其sql文件在项目根目录中
    Dbconstant.setUsername("root");//输入数据库用户名
    Dbconstant.setPassword("1234567");//输入对应用户密码
    Dbconstant.setUrlBean("com.socket.demo.po.");//默认值
    Convetutil.connectdata();</code></pre>
<p>4.项目使用说明:访问地址为IP:端口号/c.html，其页面下内置三个聊天用户悟空，八戒，沙僧。如果你想传入自己项目的用户，请把ID及用户名传入/main地址下,且传输方式为post。
<br>
</p>


</br>项目用户页面</br>


![在这里插入图片描述](https://img-blog.csdnimg.cn/20190829105041863.PNG?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQ1MTMwOTU2,size_16,color_FFFFFF,t_70)



</br>群发及私聊消息页面</br>



![在这里插入图片描述](https://img-blog.csdnimg.cn/20190829105207706.PNG?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQ1MTMwOTU2,size_16,color_FFFFFF,t_70)



</br>
离线消息1
</br>



![在这里插入图片描述](https://img-blog.csdnimg.cn/20190829105240507.PNG?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQ1MTMwOTU2,size_16,color_FFFFFF,t_70)



</br>离线消息2</br>



![在这里插入图片描述](https://img-blog.csdnimg.cn/20190829105335541.PNG?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQ1MTMwOTU2,size_16,color_FFFFFF,t_70)





</br>
如果开启数据库消息记录功能,消息记录1
</br>



![在这里插入图片描述](https://img-blog.csdnimg.cn/20190829110030220.PNG?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQ1MTMwOTU2,size_16,color_FFFFFF,t_70)



</br>
消息记录2</br>




![在这里插入图片描述](https://img-blog.csdnimg.cn/20190829110102893.PNG?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzQ1MTMwOTU2,size_16,color_FFFFFF,t_70)




</br>
数据库的SQL文件暂未上传，请等待。

<h1>友情提示：此项目只是一个Demo,仅供参考，学习，交流</h1>

</br>[项目GitHub地址](https://github.com/Shprins/Webchat)

</br>原创项目，转载须注明出处。
</body></html>
