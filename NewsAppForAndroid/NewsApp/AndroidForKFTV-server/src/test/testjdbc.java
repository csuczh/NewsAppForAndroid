package test;

import gsonUtil.GsonTools;

import java.sql.Time;
import java.util.List;

import service.MusicListItem;
import service.NewsCommentService;
import service.NewsDetalistservice;
import service.NewsPicturesService;
import service.NewsRelatedService;
import service.NewsTopService;
import service.NewsTypeService;
import service.Newservice;
import service.PictureDetalistService;
import service.PictureItemService;
import service.PictureTypeService;
import service.SingleCommentService;
import service.SingleVideoService;
import service.UserService;
import service.VideoListItemService;
import service.VideoRelatedService;
import service.VideoTypeService;

import dao.MusicListItemDao;
import dao.NewsCommentDao;
import dao.NewsDetalistDao;
import dao.NewsRelatedDao;
import dao.NewsTopDao;
import dao.NewsTypesDao;
import dao.PictureDetalistDao;
import dao.PictureItemDao;
import dao.PictureTypeDao;
import dao.SingleCommentDao;
import dao.SingleVideoDao;
import dao.VideoListItemDao;
import dao.VideoRelatedDao;
import dao.VideoTypeDao;
import dao.usersDao;
import domin.NewsComment;
import domin.NewsD;
import domin.NewsDetalist;
import domin.NewsTop;
import domin.PictureType;
import domin.VideoItem;
import domin.VideoRelated;

public class testjdbc {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Newservice newservice=new dao.NewsDao();
       // String str=GsonTools.createjsonString2(newservice.getNewsDs());
//        String str="";
//        String value="admin_海底世5";
//        String [] messages=value.split("_");
//        System.out.println(messages[0]+messages[1]);
//		SingleCommentService singleCommentService=new SingleCommentDao();
//		boolean ok=singleCommentService.search(messages[0], messages[1]);
//		if(ok)
//		{
//			str="ok";
//		}
//		else {
//			str="faile";
//		}
//		System.out.println("search"+str);
        
//        NewsPicturesService newsPicturesService=new dao.NewsPictruesDao();
//        String str2=GsonTools.createjsonString2(newsPicturesService.getNewsPictures("海底世界"));
      // NewsDetalistDao
        // NewsDetalistservice newsDetalistservice=new NewsDetalistDao();
       // String str2=GsonTools.createJsonString((Object)newsDetalistservice.getDetalists("海底世界"));
       //newsRelatedService
     //   NewsRelatedService newsRelatedService=new NewsRelatedDao();
       // String str2=GsonTools.createjsonString2(newsRelatedService.getRelateds("海底世界"));
       
      //newstop 
//     NewsCommentService commentService =new NewsCommentDao();
//     String str2=GsonTools.createjsonString2(commentService.getComments("海世界"));
//    
        //newstopService
      /*  NewsTopService topService =new NewsTopDao();
        String str2=GsonTools.createJsonString(topService.getTop("开封新闻"));*/
        /*
        NewsTypeService newsTypeService=new NewsTypesDao();
        String str2=GsonTools.createjsonString2(newsTypeService.getTypes());*/
//        UserService userService=new usersDao();
//       String str2=GsonTools.createJsonString(userService.getuser("sss"));
//       
//        
//      NewsTypeService newsTypeService=new NewsTypesDao();
// 	    String str2=GsonTools.createjsonString2(newsTypeService.getTypes());
// 	  System.out.println(str2); 
 	  //videotype测试
//        VideoTypeService videoTypeService=new VideoTypeDao();
//        String str2=GsonTools.createjsonString2(videoTypeService.getTypes());
        //videoItem测试
//        VideoListItemService videoItemService=new VideoListItemDao();
//        String str2=GsonTools.createjsonString2(videoItemService.getiItems("电影"));
       //videwrelate测试
//        VideoRelatedService videoRelatedService=new VideoRelatedDao();
//        String str2=GsonTools.createjsonString2(videoRelatedService.getRelateds("一代枭雄1"));
        
        
//        SingleVideoService service=new SingleVideoDao();
//       String str2=GsonTools.createJsonString(service.getItem("一代枭雄1"));
//		PictureTypeService piService=new PictureTypeDao();
//		String str2=GsonTools.createjsonString2(piService.getTypes());
// 	  
//        System.out.println(str2);
//		PictureItemService pictureItemService=new PictureItemDao();
//		String str2=GsonTools.createjsonString2(pictureItemService.getItems("明星"));
//		System.out.println(str2);
//		PictureDetalistService pService=new PictureDetalistDao();
//		String str2=GsonTools.createjsonString2(pService.getList("黄晓明"));
		
		MusicListItem musicListItem=new MusicListItemDao();
		String str2=GsonTools.createjsonString2(musicListItem.getInfos());
		System.out.println(str2);
        
       
        
	}

}
