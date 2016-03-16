package action;

import gsonUtil.GsonTools;

import java.beans.Encoder;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.sampled.AudioFormat.Encoding;

import org.apache.commons.codec.Decoder;

import service.NewsCommentService;
import service.NewsDetalistservice;
import service.NewsPicturesService;
import service.NewsRelatedService;
import service.NewsTopService;
import service.NewsTypeService;
import service.Newservice;
import service.SingleCommentService;
import service.UserService;
import dao.NewsCommentDao;
import dao.NewsDao;
import dao.NewsDetalistDao;
import dao.NewsPictruesDao;
import dao.NewsRelatedDao;
import dao.NewsTopDao;
import dao.NewsTypesDao;
import dao.SingleCommentDao;
import dao.usersDao;
import domin.NewsD;
import domin.SingleComment;
import domin.User;

import com.google.gson.reflect.TypeToken;

public class HandleRequest extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public HandleRequest() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// request.setAttribute("text/html","utf-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String str = "";
		String action_flag = request.getParameter("action_flag");
		String value = request.getParameter("value");
		if (action_flag.equals("types")) {
			NewsTypeService newsTypeService = new NewsTypesDao();
			str = GsonTools.createjsonString2(newsTypeService.getTypes());
			System.out.println(str);
		} else if (action_flag.equals("type")) {
			NewsTopService newsTopService = new NewsTopDao();
			str = GsonTools.createJsonString(newsTopService.getTop(value));
		} else if (action_flag.equals("news")) {
			Newservice newservice = new dao.NewsDao();
			str = GsonTools.createjsonString2(newservice.getNewsDs());
			System.out.println(str);
		} else if (action_flag.equals("new")) {
			NewsDetalistservice newsDetalistservice = new NewsDetalistDao();
			str = GsonTools.createJsonString(newsDetalistservice
					.getDetalists(value));
			System.out.println(str);
		} else if (action_flag.equals("comments")) {
			NewsCommentService newsCommentService = new NewsCommentDao();
			str = GsonTools.createjsonString2(newsCommentService
					.getComments(value));
		} else if (action_flag.equals("pictures")) {
			NewsPicturesService newsPicturesService = new NewsPictruesDao();
			str = GsonTools.createjsonString2(newsPicturesService
					.getNewsPictures(value));
			
		} else if (action_flag.equals("login")) {
			UserService userService = new usersDao();
			str = GsonTools.createJsonString(userService.getuser(value));
			if (str.equals("null"))
				str = "empty";
			System.out.println(str);
		} else if (action_flag.equals("insertuser")) {
			User user = GsonTools.GetSingle(value, User.class);
			UserService userService = new usersDao();
			boolean ok = userService.inseruser(user);
			if (ok) {
				str = "ok";
				System.out.println(str);
			} else {
				str = "faile";
			}
			System.out.println(str);
		} else if (action_flag.equals("updatecomment")) {
			SingleComment singleComment = GsonTools.GetSingle(value,
					SingleComment.class);
			SingleCommentService service = new SingleCommentDao();
			boolean ok = service.updatecomment(singleComment);
			if (ok) {
				str = "ok";
				System.out.println(str);
			} else {
				str = "faile";
			}
			System.out.println(str);
		} else if (action_flag.equals("insertcomment")) {
			SingleComment singleComment = GsonTools.GetSingle(value,
					SingleComment.class);
			SingleCommentService service = new SingleCommentDao();
			boolean ok = service.insertcomment(singleComment);
			if (ok)
				str = "ok";
			else {
				str = "faile";
			}
			System.out.println(str);
		} else if (action_flag.equals("searchcomment")) {
			String[] messages = value.split("_");
			SingleCommentService singleCommentService = new SingleCommentDao();
			boolean ok = singleCommentService.search(messages[0], messages[1]);
			if (ok) {
				str = "ok";
			} else {
				str = "faile";
			}
			System.out.println(str);
		} else if (action_flag.equals("allcomments")) {
			String[] messages = value.split("_");
			NewsCommentService newsCommentService = new NewsCommentDao();
			str = GsonTools.createjsonString2(newsCommentService
					.getComments(value));
			System.out.println(str);
		} else if (action_flag.equals("relatednews")) {

			System.out.println(action_flag);
			NewsRelatedService newsRelatedService = new NewsRelatedDao();
			str = GsonTools.createjsonString2(newsRelatedService
					.getRelateds(value));
			System.out.println(str);
		}

		out.print(str);
		out.flush();
		out.close();

	}

	private String inputStreamToString(InputStream is, String encoding) {
		try {
			byte[] b = new byte[1024];
			String res = "";
			if (is == null) {
				return "";
			}

			int bytesRead = 0;
			while (true) {
				bytesRead = is.read(b, 0, 1024); // return final read bytes
													// counts
				if (bytesRead == -1) {// end of InputStream
					return res;
				}
				res += new String(b, 0, bytesRead, encoding); // convert to
																// string using
																// bytes
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print("Exception: " + e);
			return "";
		}
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
