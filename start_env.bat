set DIRNAME=%~dp0
set DIRNAME=%DIRNAME:~0,-1%

cd %DIRNAME%
start cmd /k "%DIRNAME%\start_back.bat"
start cmd /k "%DIRNAME%\start_front.bat"