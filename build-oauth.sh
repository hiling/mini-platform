cd oauth
mvn clean package -Dmaven.test.skip=true
cp ./target/oauth-0.0.1-SNAPSHOT.jar ./target/oauth.jar
scp ./target/oauth.jar root@47.104.109.40:///opt/miniapi/oauth