package trader.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import trader.*;

import javax.servlet.RequestDispatcher;

/**
 * Servlet implementation class CustomerController
 */
@WebServlet(urlPatterns = { "/CustomerController", "/AllCustomers" })
public class CustomerController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CustomerController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			processReques(request, response);
		} catch (BrokerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			processReques(request, response);
		} catch (BrokerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void processReques(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			BrokerException {
		BrokerModel model = BrokerModelImpl.getInstance();
		String path = request.getServletPath();
		BrokerModel brokerModel = BrokerModelImpl.getInstance();

		// CustomerController Request
		if (path.equals("/CustomerController")) {

			// Acquire parameter values
			String id = request.getParameter("customerIdentity");
			String name = request.getParameter("customerName");
			String address = request.getParameter("customerAddress");
			String submit = request.getParameter("submit");

			try {

				// Get Customer
				if (submit.equals("Get Customer")) {
					Customer customer = brokerModel.getCustomer(id);
					request.setAttribute("customer", customer);
					// Update Customer
				} else if (submit.equals("Update Customer")) {
					brokerModel.updateCustomer(new Customer(id, name, address));
					Customer customer = brokerModel.getCustomer(id);
					request.setAttribute("customer", customer);

					// Add Customer
				} else if (submit.equals("Add Customer")) {
					brokerModel.addCustomer(new Customer(id, name, address));

					Customer customer = brokerModel.getCustomer(id);
					request.setAttribute("customer", customer);

					// Delete Customer
				} else if (submit.equals("Delete Customer")) {
					brokerModel.deleteCustomer(new Customer(id, name, address));

				} else {
					System.out.println("Wrong selection has been made!");
				}
			} catch (BrokerException e) {
				request.setAttribute("message", e.getMessage());
			}

			RequestDispatcher requestDispatcher = request
					.getRequestDispatcher("CustomerDetails");
			requestDispatcher.forward(request, response);
			// AllCustomers Request
		} else if (path.equals("/AllCustomers")) {
			try {
				Customer[] customers = brokerModel.getAllCustomers();
				request.setAttribute("customer", customers);

			} catch (BrokerException e) {
				System.out.println(e);
			}
			RequestDispatcher requestDispatcher = request
					.getRequestDispatcher("AllCustomers.jsp");
			requestDispatcher.forward(request, response);

		} else {
			// Wrong controller value
			System.out.println("Wrong controller selection");
		}
	}

}
