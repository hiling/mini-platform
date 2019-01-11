#!/bin/sh
#启动服务
rm -f tpid
java -Dloader.path="libs/" -jar /opt/miniapi/discovery/discovery.jar --spring.profiles.active=discovery1 > /opt/miniapi/discovery/discovery1.log
echo $! > tpid
echo Start Success!