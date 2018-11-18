#!/bin/sh
#启动服务
APP_NAME=discovery.jar
rm -f tpid
nohup java -Dloader.path="lib/" -jar /opt/miniapi/discovery/$APP_NAME --spring.profiles.active=discovery1 > /opt/miniapi/discovery/node1.log
nohup java -Dloader.path="lib/" -jar /opt/miniapi/discovery/$APP_NAME --spring.profiles.active=discovery2 > /opt/miniapi/discovery/node2.log
nohup java -Dloader.path="lib/" -jar /opt/miniapi/discovery/$APP_NAME --spring.profiles.active=discovery3 > /opt/miniapi/discovery/node3.log
echo $! > tpid
echo Start Success!