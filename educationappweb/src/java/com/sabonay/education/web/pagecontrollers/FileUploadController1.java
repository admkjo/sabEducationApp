/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.education.web.pagecontrollers;

import com.sabonay.education.common.utils.idSetter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.servlet.http.Part;

/**
 *
 * @author thony
 */
@ManagedBean
@RequestScoped
public class FileUploadController1 implements Serializable {
//private static final long serialVersionUID = 9040359120893077422L;

    public static int f = 8;
    public static String resourceurl = "";
    private Part part;
    private String statusMessage;

    public boolean uploadFile(Part part, String schNo, String studNo) throws IOException, SQLException {
        try {
            studNo = studNo.replace("/", "-").replace("\\", "-");
            String basePath = System.getProperty("com.sun.aas.instanceRoot") + File.separator + "docroot" + File.separator + "edures" + File.separator + schNo + File.separator + "student_pix" + File.separator + "";

            // Extract file name from content-disposition header of file part
            String fileName = idSetter.studId;
            String filetype = "";
            if ("image/gif".equals(part.getContentType()) || "image/png".equals(part.getContentType()) || "image/jpeg".equals(part.getContentType()) || "image/pjpeg".equals(part.getContentType()) || "image/jpeg 2000".equals(part.getContentType()) || "image/exif".equals(part.getContentType()) || "image/tiff".equals(part.getContentType()) || "image/rif".equals(part.getContentType()) || "image/PNG".equals(part.getContentType())) {

//		System.out.println("***** fileName: " + fileName + "ext = "+part.getContentType());
                File outputFilePath = new File(basePath + File.separator + studNo + ".jpg");
                if (outputFilePath.exists()) {
                    outputFilePath.delete();
                    outputFilePath = new File(basePath + File.separator + studNo + ".jpg");
                    System.out.println(".................. " + outputFilePath.toString());
                }

                resourceurl = filetype + "/" + fileName;
                System.out.println("id: " + studNo);
                System.out.println("image link" + outputFilePath.toString());

                // Copy uploaded file to destination path
                InputStream inputStream = null;
                OutputStream outputStream = null;
                try {
                    inputStream = part.getInputStream();
                    outputStream = new FileOutputStream(outputFilePath);

                    int read = 0;
                    final byte[] bytes = new byte[1024];
                    while ((read = inputStream.read(bytes)) != -1) {
                        outputStream.write(bytes, 0, read);
                    }

                    statusMessage = "File upload successfull !!";

                } catch (IOException e) {
                    e.printStackTrace();
                    statusMessage = "File upload failed !!";
                } finally {
                    if (outputStream != null) {
                        outputStream.close();
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }
                }
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;    // return to same page
        }
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    // Extract file name from content-disposition header of file part
    public static String getFileName(Part part) {
        final String partHeader = part.getHeader("content-disposition");
        System.out.println("***** partHeader: " + partHeader);
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim()
                        .replace("\"", "");
            }
        }
        return null;
    }
}
