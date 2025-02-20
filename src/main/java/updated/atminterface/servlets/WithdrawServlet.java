package updated.atminterface.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import updated.atminterface.dao.UserDao;

@WebServlet("/WithdrawServlet")
public class WithdrawServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/withdraw.jsp");
        dispatcher.forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");
        double amount = Double.parseDouble(request.getParameter("amount"));

        if (userId != null) {
            UserDao userDao = new UserDao();
            boolean success = userDao.withdraw(userId, amount);

            if (success) {
                request.setAttribute("message", "Withdrawal successful.");
                request.getRequestDispatcher("/WEB-INF/views/withdraw.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Insufficient funds.");
                request.getRequestDispatcher("/WEB-INF/views/withdraw.jsp").forward(request, response);
            }
        } else {
            response.sendRedirect("login.jsp");
        }
    }
}
