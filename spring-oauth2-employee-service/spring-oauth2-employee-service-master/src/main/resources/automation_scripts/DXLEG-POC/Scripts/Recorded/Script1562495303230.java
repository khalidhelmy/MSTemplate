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

WebUI.navigateToUrl('https://assets-es-uat.myvdf.aws.cps.vodafone.com/')

WebUI.setText(findTestObject('Object Repository/New Folder/Page_Mi Vodafone/txt_username'), '605181818')

WebUI.setEncryptedText(findTestObject('Object Repository/New Folder/Page_Mi Vodafone/input_txt_password'), 'GTi1Kntttclz9rov7DLiqg==')

WebUI.click(findTestObject('Object Repository/New Folder/Page_Mi Vodafone/button_Acceder'))

WebUI.click(findTestObject('Object Repository/New Folder/Page_Mi Vodafone/span_Cancelar_icon-full-close'))

WebUI.click(findTestObject('Object Repository/New Folder/Page_Mi Vodafone/span_App Mi Vodafone-full-close'))

WebUI.click(findTestObject('Object Repository/New Folder/Page_Mi Vodafone/div_Buenos das_Burgermenu-side-menu'))

WebUI.click(findTestObject('Object Repository/New Folder/Page_Mi Vodafone/span_Facturas'))

WebUI.click(findTestObject('Object Repository/New Folder/Page_Mi Vodafone/span_Pago de facturas'))

WebUI.click(findTestObject('Object Repository/New Folder/Page_Mi Vodafone/div_467144953'))

WebUI.switchToWindowUrl('google.com')

WebUI.setText(findTestObject('Object Repository/New Folder/Page_Google/input_Anmelden_q'), 'Test')

WebUI.click(findTestObject('Object Repository/New Folder/Page_Google/span_test'))

WebUI.click(findTestObject('Object Repository/New Folder/Page_test - Google-Suche/a_News'))

