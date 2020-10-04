package sdmWebApplication.servlets;

import sdmWebApplication.utils.ServletUtils;
import users.UserManager;
import validation.Validator;
import validation.XmlValidate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Scanner;

public class UploadFileServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String fileName = request.getParameter("file1");
        readFromInputStream(fileName);
        /*
        // we could extract the 3rd member (not the file one) also as 'part' using the same 'key'
        // we used to upload it on the formData object in JS....
        Part name = request.getPart("name");
        String nameValue = readFromInputStream(name.getInputStream());
         */


    }

    private void readFromInputStream(String fileName) {
        UserManager userManager = ServletUtils.getUserManager(getServletContext());
        Validator validator = new XmlValidate(userManager.getEngine());
        try {
            validator.validate(fileName);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }
}
