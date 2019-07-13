echo acrows2

set list= ( DatiMifidService, PPAnagrafeClienteService, PPPosizioneCompletaService )
for %%i in %list% do (
call mvn clean compile -DskipTests=true -Paxis2-generate-sources -Dwsdl.package=com.epam.bipazci.acrows2 -Dwsdl.file=src/main/resources/input-wsdl/acrows2/%%i.wsdl -Daxis2.wsdl2code.generateServerSide=true -Daxis2.wsdl2code.generateServicesXml=true

mkdir src-generated-services\%%i\META-INF\
copy target\generated-sources\axis2\wsdl2code\resources\services.xml src-generated-services\%%i\META-INF\services.xml

rem xcopy /s /y target\generated-sources\axis2\wsdl2code src-generated
robocopy target\generated-sources\axis2\wsdl2code src-generated /E /np /nfl /ndl

)


echo acrows3
rem ArchivioSimulazioniWS,
set list= ( DettaglioSimulazioneWS,MisureProdPortafoglioWS,PtfOnlineWS,RetrieveProfiloMIFIDWS,StampaSimulazione,ValidaSimulazioneWS,VerificaAdeguatezzaGP,VerificaAdeguatezzaWS,VerificaSimulazioneSimGPWS,VerificaSimulazioneSimWS,VerificaSimulazioneWS )
for %%i in %list% do (
call mvn clean compile -DskipTests=true -Paxis2-generate-sources -Dwsdl.package=com.epam.bipazci.acrows3 -Dwsdl.file=src/main/resources/input-wsdl/acrows3/wsdl/%%i.wsdl -Daxis2.wsdl2code.generateServerSide=true -Daxis2.wsdl2code.generateServicesXml=true

mkdir src-generated-services\%%i\META-INF\
copy target\generated-sources\axis2\wsdl2code\resources\services.xml src-generated-services\%%i\META-INF\services.xml

rem xcopy /s /y target\generated-sources\axis2\wsdl2code src-generated
robocopy target\generated-sources\axis2\wsdl2code src-generated /E /np /nfl /ndl

)

echo acrows3.ws
set list= ( AlertService, AzionamentoWorkflowService,CatalogoService,ClienteService,ContiCorrentiService,CreditiService,DecodificaService,DocumentDownloadService,GestionePropostaCCService,GestionePropostaService,GestioneRBVitaService,GrafometricaService,ImpersonificazioneService,NormalizzazioneService,ProvisioningService,RendimentiService,ReportService,SimulazioneService,TrasformazioneDartaService )
for %%i in %list% do (
call mvn clean compile -DskipTests=true -Paxis2-generate-sources -Dwsdl.package=com.epam.bipazci.acrows3 -Dwsdl.file=src/main/resources/input-wsdl/acrows3/wsdl/wc/%%i.wsdl -Daxis2.wsdl2code.generateServerSide=true -Daxis2.wsdl2code.generateServicesXml=true

mkdir src-generated-services\%%i\META-INF\
copy target\generated-sources\axis2\wsdl2code\resources\services.xml src-generated-services\%%i\META-INF\services.xml

rem xcopy /s /y target\generated-sources\axis2\wsdl2code src-generated
robocopy target\generated-sources\axis2\wsdl2code src-generated /E /np /nfl /ndl

)

