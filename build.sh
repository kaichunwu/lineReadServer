function checkjava() {
	if type -p java; then
		echo "found java executable in PATH"
		_java=java
	elif [[ -n "$JAVA_HOME" ]] && [[ -x "$JAVA_HOME/bin/java" ]];  then
		echo "found java executable in JAVA_HOME"  
		_java="$JAVA_HOME/bin/java"
	else
		echo "no java"
	fi

	if [[ "$_java" ]]; then
		version=$("$_java" -version 2>&1 | awk -F '"' '/version/ {print $2}')
		echo version "$version"
		if [[ "$version" > "1.8" ]] && [[ "$version" < "1.9" ]]; then
			echo "version is 1.8"
			return 0
		else         
			echo "version is not 1.8"
		fi
	fi
	return 1
}

# java is required
checkjava
if [ $? == 1 ];
then
 	echo "Please install java version 1.8 and try again."
	echo "Press ctrl+c to exit or wait for 5 seconds"
	sleep 5
else
	echo "Can run run.sh directly!"
	echo "Press ctrl+c to exit or wait for 5 seconds"
	sleep 5
fi
