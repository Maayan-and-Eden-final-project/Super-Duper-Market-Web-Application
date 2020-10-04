package sdmWebApplication.servlets;

import sdmWebApplication.utils.ServletUtils;
import sdmWebApplication.utils.SessionUtils;
import users.UserManager;
import validation.Validator;
import validation.XmlValidate;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Scanner;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class UploadFileServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        Collection<Part> parts = request.getParts();

        for (Part part : parts) {
            readFromInputStream(part.getInputStream(), request, out);
        }
    }

    private void readFromInputStream(InputStream inputStream, HttpServletRequest request,PrintWriter out ) {
        UserManager userManager = ServletUtils.getUserManager(getServletContext());
        Validator validator = new XmlValidate(userManager.getEngine());
        ((XmlValidate)validator).setUserManager(userManager);
        String usernameFromSession = SessionUtils.getUsername(request);

        try {
            validator.validate(inputStream, usernameFromSession);

        } catch (Exception exception) {
            out.println(exception.getMessage());
            out.flush();
        }
    }
}
