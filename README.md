# search
springboot 集成elasticSearch，实现索引增删改查

实现步骤：
1、安装ElasticSearch,安装完成之后在config文件夹elasticsearch.yml文件最下面添加
    http.cors.enabled: true 
    http.cors.allow-origin: "*"
2、安装完成之后访问：localhost:9200,若是能访问成功，则安装成功
3、安装elasticsearch-head-master,在https://github.com/mobz/elasticsearch-head中下载head插件，选择下载zip,解压文件，将文件夹放在elasticsearch-6.2.4
下面,修改elasticsearch-6.2.4\elasticsearch-head-master\Gruntfile.js 在		
        connect: {
              server: {
                options: {
                  hostname:'*',
                  port: 9100,
                  base: '.',
                  keepalive: true
                }
              }
            }
对应的位置加上hostname:'*'
4、在elasticsearch-head-master下执行npm install(需要安装node.js) 安装完成后执行grunt server,在浏览器中输入：localhost:9100

