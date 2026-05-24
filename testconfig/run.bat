
@echo off
setlocal

set "BASEDIR=%~dp0"
set "JAR=%BASEDIR%lpadmin-facture-1.0.0-SNAPSHOT.jar"
set "configdir=%BASEDIR%config\"
set "promptdir=%BASEDIR%prompt\"

java -cp "%promptdir%" -jar "%JAR%" --spring.config.additional-location="file:%configdir%"

endlocal



