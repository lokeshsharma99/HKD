package utility;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import utility.Log;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import config.Constants;
import executionEngine.DriverScript;
    public class ExcelUtils {
                private static XSSFSheet ExcelWSheet;
                private static XSSFWorkbook ExcelWBook;
                private static org.apache.poi.ss.usermodel.Cell Cell;
                private static XSSFRow Row;
               
           
            public static void setExcelFile(String Path) throws Exception {
            	try {
            	
                    FileInputStream ExcelFile = new FileInputStream(Path);
                    ExcelWBook = new XSSFWorkbook(ExcelFile);
               
            	} catch (Exception e){
            		Log.error("Class Utils | Method setExcelFile | Exception desc : "+e.getMessage());
            		DriverScript.bResult = false;
                	}
            	}
            
            public static String getCellData(int RowNum, int ColNum, String SheetName ) throws Exception{
             try{
                	DataFormatter formatter = new DataFormatter(); 
                	ExcelWSheet = ExcelWBook.getSheet(SheetName);
                   	Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
                   	String CellData = Cell.getStringCellValue();
                    return CellData;
                 }catch(Exception e2){
                	 try{
                		 //FormulaEvaluator evaluator = ExcelWBook.getCreationHelper().createFormulaEvaluator();
                		 DataFormatter formatter = new DataFormatter(); 
                		 DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                		Date today = Calendar.getInstance().getTime();  
                		String CellData = df.format(today);

                         return CellData;	
                		 
                	 } catch(Exception e){
                     Log.error("Class Utils | Method getCellData | Exception desc : "+e.getMessage());
                     e.printStackTrace();
                     DriverScript.bResult = false;
                     return"";
                     }
                	 finally{}
                	 }
            }
                	 
            
        	
        	public static int getRowCount(String SheetName){
        		int iNumber=0;
        		try {
        			ExcelWSheet = ExcelWBook.getSheet(SheetName);
        			iNumber=ExcelWSheet.getLastRowNum()+1;
        		} catch (Exception e){
        			Log.error("Class Utils | Method getRowCount | Exception desc : "+e.getMessage());
        			DriverScript.bResult = false;
        			}
        		return iNumber;
        		}
        	
        	public static int getRowContains(String sTestCaseName, int colNum,String SheetName) throws Exception{
        		int iRowNum=0;	
        		try {
        		    //ExcelWSheet = ExcelWBook.getSheet(SheetName);
        			int rowCount = ExcelUtils.getRowCount(SheetName);
        			for (; iRowNum<rowCount; iRowNum++){
        				if  (ExcelUtils.getCellData(iRowNum,colNum,SheetName).equalsIgnoreCase(sTestCaseName)){
        					break;
        				}
        			}       			
        		} catch (Exception e){
        			Log.error("Class Utils | Method getRowContains | Exception desc : "+e.getMessage());
        			DriverScript.bResult = false;
        			}
        		return iRowNum;
        		}
        	
        	public static int getTestStepsCount(String SheetName, String sTestCaseID, int iTestCaseStart) throws Exception{
        		try {
	        		for(int i=iTestCaseStart;i<=ExcelUtils.getRowCount(SheetName);i++){
	        			if(!sTestCaseID.equals(ExcelUtils.getCellData(i, Constants.Col_TestCaseID, SheetName))){
	        				int number = i;
	        				return number;      				
	        				}
	        			}
	        		ExcelWSheet = ExcelWBook.getSheet(SheetName);
	        		int number=ExcelWSheet.getLastRowNum()+1;
	        		return number;
        		} catch (Exception e){
        			Log.error("Class Utils | Method getRowContains | Exception desc : "+e.getMessage());
        			DriverScript.bResult = false;
        			return 0;
                }
        	}
        	
        	@SuppressWarnings("static-access")
        	public static void setCellData(String Result,  int RowNum, int ColNum, String SheetName) throws Exception    {
                   try{
                	   
                	   ExcelWSheet = ExcelWBook.getSheet(SheetName);
                       Row  = ExcelWSheet.getRow(RowNum);
                       Cell = Row.getCell(ColNum, Row.RETURN_BLANK_AS_NULL);
                       if (Cell == null) {
                    	   Cell = Row.createCell(ColNum);
                    	   Cell.setCellValue(Result);
                        } else {
                            Cell.setCellValue(Result);
                        }
                         FileOutputStream fileOut = new FileOutputStream(Constants.Path_TestData);
                         ExcelWBook.write(fileOut);
                         //fileOut.flush();
                         fileOut.close();
                         ExcelWBook = new XSSFWorkbook(new FileInputStream(Constants.Path_TestData));
                     }catch(Exception e){
                    	 DriverScript.bResult = false;
              
                     }
                }

            public static void refreshData() throws Exception{
                try{
                	
                	String SheetName=Constants.Sheet_TestSteps;
                	ExcelWSheet = ExcelWBook.getSheet(SheetName);
                //	FormulaEvaluator evaluator1 = ExcelWBook.getCreationHelper().createFormulaEvaluator();
                //	evaluateAllFormulaCells(ExcelWBook, evaluator1);
                	FormulaEvaluator evaluator = ExcelWBook.getCreationHelper().createFormulaEvaluator();
                	evaluator.evaluateAll();
                 	                    
                 }catch (Exception e){
                     Log.error("Class Utils | Method refreshData | Exception desc : "+e.getMessage());
                     DriverScript.bResult = false;
                     
                 }
               }
            
           
    	}