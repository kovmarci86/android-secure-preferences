android-secure-preferences
==========================

About
-----
This project uses the Encryption class from:
http://www.java2s.com/Code/Android/Security/AESEncryption.htm

Gives an implementation of SharedPreferences, which encrypts given values with AES.


Environment setup
-----------------
 - install maven
 - install Android sdk
 - install sdk deployer ( https://github.com/mosabua/maven-android-sdk-deployer )
 - check out source to a directory.

Build
-----
 - mvn clean install

Android compatibility
---------------------
Project requires API level 8 due to Base64 Android API level requirements.

Binary
------
Until GitHub deprecates download section, use:

https://github.com/kovmarci86/android-secure-preferences/downloads
