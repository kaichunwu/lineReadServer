function show_usage() {
    echo "usage: run.sh filename splitLine port_number"
    echo "  filename: the path to the file to be read"
    echo "  splitLine: the split line number for cache, default: 10000"
    echo "  port_number: the port number for the HTTP server, default: 8088"
    echo "  press ctrl+c to exit or wait for 5 seconds"
}
if [ $# -eq 3 ];
then
    clear && java -jar demo/target/demo-0.0.1-SNAPSHOT.jar --file.name=$1 --file.count=$2 --server.port=$3
elif [ $# -eq 2 ];
then
	clear && java -jar demo/target/demo-0.0.1-SNAPSHOT.jar --file.name=$1 --file.count=$2
elif [ $# -eq 1 ];
then
	clear && java -jar demo/target/demo-0.0.1-SNAPSHOT.jar --file.name=$1
else
	show_usage
	sleep 5
fi
