#!/bin/sh
#启动服务
APP_NAME=eureka.jar
rm -f tpid
nohup java -Dloader.path="lib/" -jar /opt/miniapi/eureka/$APP_NAME --spring.profiles.active=peer1 > /opt/miniapi/eureka/node1.log
nohup java -Dloader.path="lib/" -jar /opt/miniapi/eureka/$APP_NAME --spring.profiles.active=peer2 > /opt/miniapi/eureka/node2.log
nohup java -Dloader.path="lib/" -jar /opt/miniapi/eureka/$APP_NAME --spring.profiles.active=peer3 > /opt/miniapi/eureka/node3.log
echo $! > tpid
echo Start Success!