@echo off
set JAVAFX_SDK=C:\Users\ilse_\Downloads\openjfx-24.0.1_windows-x64_bin-sdk\javafx-sdk-24.0.1
set JAR_PATH=C:\Users\ilse_\Desktop\SistemaVentaZamira\dist\SistemaVentaZamira.jar

java ^
  --module-path "%JAVAFX_SDK%\lib" ^
  --add-modules javafx.controls,javafx.fxml ^
  --enable-native-access=javafx.graphics ^
  -jar "%JAR_PATH%"

pause
