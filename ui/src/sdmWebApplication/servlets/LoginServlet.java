package sdmWebApplication.servlets;


import sdm.enums.UserType;
import sdmWebApplication.constants.Constants;
import users.UserManager;
import sdmWebApplication.utils.ServletUtils;
import sdmWebApplication.utils.SessionUtils;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static sdmWebApplication.constants.Constants.USERNAME;
import static sdmWebApplication.constants.Constants.USERTYPE;

public class LoginServlet extends HttpServlet {

    private final String SETTING_PAGE_URL = "../settingPage/settingPage.html";
    private final String SIGN_UP_URL = "/pages/login/login.html";
    private final String LOGIN_ERROR_URL = "/pages/loginerror/login_attempt_after_error.html";  // must start with '/' since will be used in request dispatcher...
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String usernameFromSession = SessionUtils.getUsername(request);
        UserManager userManager = ServletUtils.getUserManager(getServletContext());
        if (usernameFromSession == null) {
            //user is not logged in yet
            String usernameFromParameter = request.getParameter(USERNAME);
            String usertypeFromParameter = request.getParameter(USERTYPE);

            if (usernameFromParameter == null || usernameFromParameter.isEmpty()) {
                String path = request.getContextPath() + SIGN_UP_URL;
                response.sendRedirect(path);
            } else {
                //normalize the username value
                usernameFromParameter = usernameFromParameter.trim().toLowerCase();
                usertypeFromParameter = usertypeFromParameter.trim();

                synchronized (this) {
                    if (userManager.isUserExists(usernameFromParameter)) {
                        String errorMessage = "Username " + usernameFromParameter + " already exists. Please enter a different username.";
                        request.setAttribute(Constants.USER_NAME_ERROR, errorMessage);
                        getServletContext().getRequestDispatcher(LOGIN_ERROR_URL).forward(request, response);
                    } else {
                        UserType userType = UserType.CUSTOMER;
                        //add the new user to the users list
                        if(usertypeFromParameter.equals("0")) {
                           userType = UserType.CUSTOMER;
                        } else if(usertypeFromParameter.equals("1")) {
                            userType = UserType.SHOP_OWNER;
                        }
                        Integer userId = userManager.getUsers().size();
                        userManager.addUser(usernameFromParameter,userType,++userId);
                        //set the username in a session so it will be available on each request
                        //the true parameter means that if a session object does not exists yet
                        //create a new one
                        request.getSession(true).setAttribute(Constants.USERNAME, usernameFromParameter);
                        request.getSession(true).setAttribute(USERTYPE, userType);

                        //redirect the request to the chat room - in order to actually change the URL
                        System.out.println("On login, request URI is: " + request.getRequestURI());
                        response.sendRedirect(SETTING_PAGE_URL);
                    }
                }
            }
        } else {
            //user is already logged in
            response.sendRedirect(SETTING_PAGE_URL);
        }
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
