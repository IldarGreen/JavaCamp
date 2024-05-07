################################################################################################
# You may run this app by using the following command `chmod 777 readmeMac.txt; ./readmeMac.txt`
WHITE=RED
BLACK=YELLOW
################################################################################################
#1 remove old target folder
rm -rf target

#2 make target directory
mkdir target

#3 compiles files, and put them into target directory
#javac -cp ".:./lib/JColor-5.5.1.jar:./lib/jcommander-1.82.jar" -d ./target/ src/java/edu/school21/printer/*/*.java
javac -cp ./lib/JColor-5.5.1.jar:./lib/jcommander-1.82.jar -d ./target/ src/java/edu/school21/printer/*/*.java

#4 copy sources and lib
cp -r src/resources target/.; cp -r lib target/.

#5 unpacks jar files to target folder
cd target ; jar xf lib/JColor-5.5.1.jar com ; jar xf lib/jcommander-1.82.jar com ; cd ..

#6 create jar
jar -cfm ./target/images-to-chars-printer.jar src/manifest.txt -C target .

#7 change access to jar file
chmod 777 target/images-to-chars-printer.jar

#8 run program
java -jar target/images-to-chars-printer.jar --white=${WHITE} --black=${BLACK}
# or
#8.1 if you want manual run program
#java -jar target/images-to-chars-printer.jar --white=RED --black=YELLOW
