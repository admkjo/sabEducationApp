/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.education.web.pagecontrollers;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Edwin
 */
@ManagedBean(name="fileUpload")
@javax.faces.bean.SessionScoped
//@SessionScoped
public class FileUploadController implements Serializable{

    /** Creates a new instance of FileUpload */
    public FileUploadController() {
    }

    public void handleFileUpload(FileUploadEvent event)
    {
        UploadedFile uploadedFile = event.getFile();

        System.out.println(uploadedFile.getContentType());
        System.out.println(uploadedFile.getFileName());
        System.out.println(uploadedFile.getSize());


    }

}
