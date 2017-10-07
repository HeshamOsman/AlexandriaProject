/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sypron.util;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
 
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.view.JasperViewer;
 

//    public static void main(String[] args) throws Exception {
//        List<StudentVo> list = new ArrayList<StudentVo>();
//        list.add(new StudentVo("1","ashish"));
//        list.add(new StudentVo("2","deepak"));
//        list.add(new StudentVo("3","rabi"));
//        list.add(new StudentVo("4","anil"));
// 
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
// 
//        FileOutputStream fos = null;
//        try {
//            byte[] bytes=new ReportGenerator().generateJasperReportPDF("studentReport", outputStream, list);
//            if(bytes.length>1){
//                File someFile = new File("D:/Temp/testReport.pdf");
//                fos = new FileOutputStream(someFile);
//                fos.write(bytes);
//                fos.flush();
//                fos.close();
//                System.out.println("<<<<<<<<<<<<Report Created>>>>>>>>");
//            }
//        }catch(FileNotFoundException e) {
//            e.printStackTrace();
//        }catch(IOException e){
//            e.printStackTrace();
//        }finally{
//            if(fos!=null){
//                try {
//                    fos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
// 
 
/**
 *
 * @author hisham
 */
public class JasperPdfGenarator {
 
     public byte[]  generateJasperReportPDF(String jasperReportName, ByteArrayOutputStream outputStream, List reportList) {
        JRPdfExporter exporter = new JRPdfExporter();
        try {
            String reportLocation = "D://Temp//"+jasperReportName+".jrxml";
            JasperReport jasperReport = JasperCompileManager.compileReport(reportLocation);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,null, new JRBeanCollectionDataSource(reportList));
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);   
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
            exporter.exportReport();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in generate Report..."+e);
        } finally {
        }
        return outputStream.toByteArray();
    }
}
