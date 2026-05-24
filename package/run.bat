
@echo on
setlocal

lms server start

set "BASEDIR=%~dp0"
set "JAR=%BASEDIR%lpadmin-facture-1.0.0-SNAPSHOT.jar"
set "configdir=%BASEDIR%\\config"

java -jar "%JAR%" --spring.config.additional-location="file:%configdir%\\"

endlocal

