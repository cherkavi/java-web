set list= ( DatiMifidService, PPAnagrafeClienteService, PPPosizioneCompletaService )
for %%i in %list% do (
call mvn clean compile -DskipTests=true -Paxis2-generate-sources -Dwsdl.package=com.epam.bipazci.allianz.acrows2 -Dwsdl.file=src/main/resources/input-wsdl/acrows2/%%i.wsdl -Daxis2.wsdl2code.generateServerSide=false -Daxis2.wsdl2code.generateServicesXml=true

rem xcopy /s /y target\generated-sources\axis2\wsdl2code src-generated
robocopy target\generated-sources\axis2\wsdl2code src-generated /E /np /nfl /ndl

)
