
JAR=../target/lpadmin-facture-1.0.0-SNAPSHOT.jar
configdir="$(pwd)/config/"
promptdir="$(pwd)/prompt/"

java -cp "$promptdir" -jar "$JAR" --spring.config.additional-location="file:$configdir"



