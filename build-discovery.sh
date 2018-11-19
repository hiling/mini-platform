cd discovery
mvn clean package -Dmaven.test.skip=true
cp ./target/discovery-0.0.1-SNAPSHOT.jar ./target/discovery.jar
scp ./target/discovery.jar root@47.104.109.40:///opt/miniapi/discovery
