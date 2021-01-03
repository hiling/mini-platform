#!/bin/sh
#启动服务
APP_NAME=gateway.jar
rm -f tpid
nohup java -Dloader.path="lib/" -jar /opt/miniapi/gateway/$APP_NAME --spring.profiles.active=node1 > /opt/miniapi/gateway/node1.log
nohup java -Dloader.path="lib/" -jar /opt/miniapi/gateway/$APP_NAME --spring.profiles.active=node2 > /opt/miniapi/gateway/node2.log
nohup java -Dloader.path="lib/" -jar /opt/miniapi/gateway/$APP_NAME --spring.profiles.active=node3 > /opt/miniapi/gateway/node3.log
echo $! > tpid
echo Start Success!