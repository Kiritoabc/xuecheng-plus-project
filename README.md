# xuecheng-plus-project

## 环境配置
* 在docker中安装MySQL8(由于本机已经存在3306端口了，所以这里换了一下端口)
~~~powershell
# （1）拉取MySQL8镜像
docker pull mysql:8
# （2） 使用docker创建MySQL容器，创建MySQL数据存储目录并挂载到容器中
mkdir -p /data/mysql
docker run -p 3306:3307 --name hm_mysql -v E:\works\docker\hm\data\mysql:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=root -d mysql:8
~~~

* 安装Nacos1.4.1

~~~powershell
# (1)拉取nacos镜像
docker pull nacos/nacos-server:1.4.1
# (2) 使用docker创建Nacos容器，在宿主机上创建Nacos数据存储目录，并挂载到容器中
mkdir /data/nacos
docker run -p 8848:8848 --name hm_nacos -v E:\works\docker\hm\data\nacos:/home/nacos/nacos-server-1.4.1/nacos-logs -d nacos/nacos-server:1.4.1
~~~

- 安装RabbitMQ容器

~~~powershell
# (1) 拉取镜像
docker pull rabbitmq:3.8.34-management
mkdir /data/rabbitmq

docker run -p 15672:15672 -p 5672:5672 --name hm_rabbitmq -v E:\works\docker\hm\data\rabbitmq:/var/lib/rabbitmq -d rabbitmq:3.8.34-management
~~~

- 安装Redis 6.2.7

~~~powershell
docker pull redis:6.2.7

mkdir /data/redis

docker run -p 6379:6379 --name hm_redis -v E:\works\docker\hm\data\redis:/data -d redis:6.2.7 redis-server --appendonly yes
~~~

- 安装XXL-Job-Admin 2.3.1

~~~powershell
docker pull xuxueli/xxl-job-admin:2.3.1

makedir /data/xxl-job-admin

docker run -p 8080:8080 --name hm_xxl-job-admin -v E:\works\docker\hm\data\xxl-job-admin:/data/applogs -d xuxueli/xxl-job-admin:2.3.1
~~~

- 安装Minio

~~~posershell
  #1、拉取镜像(最新版)
  docker pull minio
  #2、创建本地卷 config配置 data数据
  mkdir -p /data/minio/config
  mkdir -p /data/minio/data
  #3、创建容器
  docker run -p 9000:9000 -p 9001:9001 -d --name hm_minio -v E:\works\docker\hm\data\minio\data:/data -v E:\works\docker\hm\data\minio\config:/root/.minio -e "MINIO_ROOT_USER=minio" -e "MINIO_ROOT_PASSWORD=minio@123456" minio/minio server /data --console-address ":9000" --address ":9001"

#9001端口指的是minio的客户端端口
#MINIO_ACCESS_KEY ：账号
#MINIO_SECRET_KEY ：密码（账号长度必须大于等于5，密码长度必须大于等于8位）
~~~

- 安装Elasticsearch:7.12.1

~~~powershell
docker pull elasticsearch:7.12.1

mkdir /data/elasticsearch

docker run -p 9200:9200 -p 9300:9300 --name hm_elasticsearch -v E:\works\docker\hm\data\elasticsearch:/usr/share/elasticsearch/data -e "discovery.type=single-node" -d elasticsearch:7.12.1
~~~ 

- 安装Kibana 7.12.1

~~~powershell
docker pull kibana:7.12.1

docker run -p 5601:5601 --name hm_kibana -d kibana:7.12.1
~~~

- 安装配置Nginx

~~~powershell
docker pull nginx:1.12.2

mkdir /data/nginx/conf

mkdir /data/nginx/loggs

mkdir /data/nginx/html

docker run -d --name hm_nginx -p 80:80 -v E:\works\docker\hm\data\nginx\html:/usr/share/nginx/html -v E:\works\docker\hm\data\nginx\conf:/etc/nginx -v E:\works\docker\hm\data\nginx\logs:/var/log/nginx -d nginx:1.12.2
~~~
