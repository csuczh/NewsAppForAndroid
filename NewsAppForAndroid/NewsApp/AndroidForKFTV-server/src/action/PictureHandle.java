package action;

import gsonUtil.GsonTools;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.PictureDetalistDao;
import dao.PictureItemDao;
import dao.PictureTypeDao;

import service.PictureDetalistService;
import service.PictureItemService;
import service.PictureTypeService;

/**
 * Servlet implementation class PictureHandle
 */
@WebServlet("/PictureHandle")
public class PictureHandle extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PictureHandle() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String str = "";
		String action_flag = request.getParameter("action_flag");
		String value = request.getParameter("value");
		System.out.println(value);
		if(action_flag.equals("picturetypes"))
		{
			PictureTypeService piService=new PictureTypeDao();
			str=GsonTools.createjsonString2(piService.getTypes());
	        System.out.println(str);
			
		}else if(action_flag.equals("picturelist"))
		{
			PictureItemService pictureItemService=new PictureItemDao();
			str=GsonTools.createjsonString2(pictureItemService.getItems(value));
			System.out.println(str);
		}else if(action_flag.equals("showpicture"))
		{
			PictureDetalistService pService=new PictureDetalistDao();
			str=GsonTools.createjsonString2(pService.getList(value));
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

}
