start /min cmd /k ".\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties"
timeout 15
start /min cmd /k ".\bin\windows\kafka-server-start.bat .\config\server.properties"
