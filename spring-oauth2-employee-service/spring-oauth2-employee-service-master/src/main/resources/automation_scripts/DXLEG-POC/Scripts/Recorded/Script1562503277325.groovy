import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.openBrowser('')

WebUI.maximizeWindow()

WebUI.navigateToUrl('https://assets-es-uat.myvdf.aws.cps.vodafone.com/')

WebUI.setText(findTestObject('Object Repository/Page_Mi Vodafone/input_Empleado empresa_ManualL'), '605181818')

WebUI.setEncryptedText(findTestObject('Object Repository/Page_Mi Vodafone/input_Empleado empresa_ManualL_1'), 'rprI1qIOn6Q=')

WebUI.setEncryptedText(findTestObject('Object Repository/Page_Mi Vodafone/input_Empleado empresa_ManualL_2'), 'ZAGLTnbdHb4=')

WebUI.setEncryptedText(findTestObject('Object Repository/Page_Mi Vodafone/input_Empleado empresa_ManualL_3'), 'wvHjBERDQhM=')

WebUI.setEncryptedText(findTestObject('Object Repository/Page_Mi Vodafone/input_Empleado empresa_ManualL_4'), 'VHIuWaags9w=')

WebUI.setEncryptedText(findTestObject('Object Repository/Page_Mi Vodafone/input_Empleado empresa_ManualL_5'), 'wA/B7oYYDI0=')

WebUI.setEncryptedText(findTestObject('Object Repository/Page_Mi Vodafone/input_Empleado empresa_ManualL_6'), 'ccd9bdYVmBM=')

WebUI.setEncryptedText(findTestObject('Object Repository/Page_Mi Vodafone/input_Empleado empresa_ManualL_7'), 'Zv8MOTFQBfc=')

WebUI.setEncryptedText(findTestObject('Object Repository/Page_Mi Vodafone/input_Empleado empresa_ManualL_8'), 'GTi1KntttcnDX1Xv+jyMRQ==')

WebUI.setEncryptedText(findTestObject('Object Repository/Page_Mi Vodafone/input_Empleado empresa_ManualL_9'), 'GTi1KntttcnRM6nqGY4NaA==')

WebUI.setEncryptedText(findTestObject('Object Repository/Page_Mi Vodafone/input_Empleado empresa_ManualL_10'), 'GTi1Kntttclz9rov7DLiqg==')

WebUI.setEncryptedText(findTestObject('Object Repository/Page_Mi Vodafone/input_Empleado empresa_ManualL_11'), 'GTi1Kntttclz9rov7DLiqg==')

WebUI.click(findTestObject('Object Repository/Page_Mi Vodafone/button_Acceder (1)'))

WebUI.click(findTestObject('Object Repository/Page_Mi Vodafone/span_Cancelar_icon icon-close'))

WebUI.click(findTestObject('Object Repository/Page_Mi Vodafone/span_App Mi Vodafone_icon icon'))

WebUI.click(findTestObject('Object Repository/Page_Mi Vodafone/div_Buenas tardes_Burgermenu-s'))

WebUI.click(findTestObject('Object Repository/Page_Mi Vodafone/span_Mi cuenta'))

WebUI.click(findTestObject('Object Repository/Page_Mi Vodafone/div_Emails y direcciones'))

