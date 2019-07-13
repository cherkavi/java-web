#!/bin/bash
mvn clean
rm -rf src-generated
mkdir src-generated
for FILENAME in DatiMifidService.wsdl PPAnagrafeClienteService.wsdl PPPosizioneCompletaService.wsdl
do
	mvn compile -DskipTests=true -Paxis2-generate-sources -Dwsdl.package=com.epam.bipazci.allianz.arcows2 -Dwsdl.file=src/main/resources/input-wsdl/arcows2/$FILENAME -Daxis2.wsdl2code.generateServerSide=false -Daxis2.wsdl2code.generateServicesXml=true	
done

cp -a target/generated-sources/axis2/wsdl2code/* src-generated/
