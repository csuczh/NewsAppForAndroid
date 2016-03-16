package action;

import gsonUtil.GsonTools;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.MusicListItem;
import service.PictureTypeService;
import dao.MusicListItemDao;
import dao.PictureTypeDao;

/**
 * Servlet implementation class MusicHandle
 */
@WebServlet("/MusicHandle")
public class MusicHandle extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MusicHandle() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String str = "";
		String action_flag = request.getParameter("action_flag");
		String value = request.getParameter("value");
		System.out.println(value);
		if (action_flag.equals("mp3s")) {
			MusicListItem musicListItem=new MusicListItemDao();
			str=GsonTools.createjsonString2(musicListItem.getInfos());
			System.out.println(str);

		}
		out.print(str);
		out.flush();
		out.close();
	}

}
