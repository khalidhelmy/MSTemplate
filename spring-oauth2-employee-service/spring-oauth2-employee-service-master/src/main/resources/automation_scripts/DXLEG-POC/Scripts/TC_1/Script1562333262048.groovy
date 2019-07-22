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

WebUI.navigateToUrl('https://assets-es-uat.myvdf.aws.cps.vodafone.com')

WebUI.setText(findTestObject('Page_Mi Vodafone_uat/input_Empleado empresa_ManualLoginComp_txt_username'), '605181818')

WebUI.setText(findTestObject('Page_Mi Vodafone_uat/input_Empleado empresa_ManualLoginComp_txt_password'), 'Prueba2468')

WebUI.click(findTestObject('Page_Mi Vodafone_uat/button_Acceder'))

WebUI.click(findTestObject('Page_Mi Vodafone_after_Login/span_Cancelar_icon icon-close modal-full-close'))

WebUI.click(findTestObject('Page_Mi Vodafone/span_App Mi Vodafone_icon icon-close modal-full-close'))

WebUI.navigateToUrl('https://google.com')

