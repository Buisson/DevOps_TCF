#Build entities first
cd Artifacts/Entities/
mvn clean install
cd ..

#Build Cuisine
cd Cuisine/
mvn clean install
cd ..

#Build RegistreClient
cd RegistreClient/
mvn clean install
cd ..

#build Catalogue
cd Catalogue/
mvn clean install
cd ..


#build Achat
cd Achat/
mvn clean install
cd ../..




