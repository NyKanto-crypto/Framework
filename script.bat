cd framework/build
javac -d framework ../src/*.java
cd framework
jar -cf ../Framework.jar ./
cd ../
xcopy /y .\Framework.jar ..\..\test-Framework\WEB-INF\lib
xcopy /y .\Framework.jar ..\..\
cd ../../
SET CLASSPATH=.\test-Framework\WEB-INF\lib\Framework.jar
javac -parameters -d test-Framework/WEB-INF/classes test-Framework/src/*.java
cd test-Framework
jar -cf ../framework6.war .
cd ..
xcopy /y .\framework6.war "C:\Program Files\Apache Software Foundation\Tomcat 10.1\webapps"
timeout 60
