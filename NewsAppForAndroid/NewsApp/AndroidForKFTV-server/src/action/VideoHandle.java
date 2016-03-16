package action;

import gsonUtil.GsonTools;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.SingleVideoService;
import service.VideoListItemService;
import service.VideoRelatedService;
import service.VideoTypeService;
import dao.SingleVideoDao;
import dao.VideoListItemDao;
import dao.VideoRelatedDao;
import dao.VideoTypeDao;

/**
 * Servlet implementation class VideoHandle
 */
@WebServlet("/VideoHandle")
public class VideoHandle extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public VideoHandle() {
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
		if (action_flag.equals("videotypes")) {
			VideoTypeService videoTypeService = new VideoTypeDao();
			str = GsonTools.createjsonString2(videoTypeService.getTypes());
			System.out.println(str);
		} else if (action_flag.equals("videolist")) {
			VideoListItemService videoListItemService = new VideoListItemDao();
			str = GsonTools
					.createjsonString2(videoListItemService.getItems(value));
			System.out.println(str);
		} else if (action_flag.equals("videorelate")) {
			VideoRelatedService videoRelatedService = new VideoRelatedDao();
			str= GsonTools.createjsonString2(videoRelatedService
					.getRelateds(value));
			System.out.println(str);
		}else if(action_flag.equals("singlevideo"))
		{
			  SingleVideoService service=new SingleVideoDao();
		      str=GsonTools.createJsonString(service.getItem(value));
		      System.out.println(str);
		}
		out.print(str);
		out.flush();
		out.close();
	}

}
