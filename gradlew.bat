@echo off
setlocal
set DIRNAME=%~dp0
if "%DIRNAME%"=="" set DIRNAME=.
set DIRNAME=%DIRNAME:~0,-1%

set CLASSPATH=%DIRNAME%\gradle\wrapper\gradle-wrapper.jar

if exist "%CLASSPATH%" goto :exists

echo Downloading the Gradle wrapper jar...
set DOWNLOAD_URL=https://services.gradle.org/distributions/gradle-8.4.1-bin.zip
if not exist "%DIRNAME%\gradle\wrapper" mkdir "%DIRNAME%\gradle\wrapper"

rem Try to download using bitsadmin, powershell, or certutil
powershell -Command "(New-Object System.Net.WebClient).DownloadFile('%DOWNLOAD_URL%', '%DIRNAME%\\gradle\\wrapper\\gradle-8.4.1-bin.zip')" >nul 2>&1
if exist "%DIRNAME%\gradle\wrapper\gradle-8.4.1-bin.zip" (
    echo Extracting gradle wrapper...
    powershell -Command "Add-Type -AssemblyName System.IO.Compression.FileSystem; [System.IO.Compression.ZipFile]::ExtractToDirectory('%DIRNAME%\\gradle\\wrapper\\gradle-8.4.1-bin.zip','%DIRNAME%\\gradle\\wrapper')" >nul 2>&1
)

:exists
if exist "%CLASSPATH%" (
    java -cp "%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain %*
) else (
    echo Could not find or download gradle-wrapper.jar. Please open the project in Android Studio to generate wrapper files or install Gradle locally.
    exit /b 1
)
