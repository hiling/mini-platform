#!/bin/bash
cd ../discovery
cp ./target/discovery-0.0.1-SNAPSHOT.jar ./target/discovery.jar
scp ./target/discovery.jar root@47.104.109.40:///opt/miniapi/discovery

cd ../gateway
cp ./target/gateway-0.0.1-SNAPSHOT.jar ./target/gateway.jar
scp ./target/gateway.jar root@47.104.109.40:///opt/miniapi/gateway
