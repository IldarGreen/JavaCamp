##########################################################################################
# You may run this app by using the following command `chmod 777 readme.txt; ./readme.txt`
##########################################################################################
mvn clean
mvn install

#server
#java -jar SocketServer/target/socket-server.jar --port=8081

#client
#java -jar SocketClient/target/socket-client.jar --server-port=8081





#kill -9 $(lsof -iTCP -sTCP:LISTEN -n -P | grep -e 808 | awk '{print $2}')

