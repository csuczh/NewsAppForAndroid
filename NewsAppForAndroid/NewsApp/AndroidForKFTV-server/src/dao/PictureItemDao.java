package dao;

import java.util.ArrayList;
import java.util.List;

import jdbc.JdbcUtils;

import domin.NewsD;
import domin.PictureItem;
import service.PictureItemService;

public class PictureItemDao implements PictureItemService{

	@Override
	public List<PictureItem> getItems(String type) {
		// TODO Auto-generated method stub
		List<PictureItem> list=new ArrayList<PictureItem>();
		String sql="SELECT  pnew.pnewtitle,pnew.picturesource,pnew.pnewdate ,  pic1.picturetitle as pic1,pic2.picturetitle as pic2 ,pic3.picturetitle as pic3 ,pnew.picturecount "+
				",nc.count picturecommentcount "+
               "  FROM picture pic1,picture pic2,picture pic3,pnew LEFT JOIN (SELECT newscomments.newstitle as title,COUNT(newscomments.newstitle) as count FROM newscomments GROUP BY newscomments.newstitle)as nc ON pnew.pnewtitle=nc.title"+
				" WHERE pic1.picturetitle < pic2.picturetitle and pic2.picturetitle<pic3.picturetitle "+
				" and pnew.pnewtitle=pic1.pnewtitle and pnew.pnewtitle=pic2.pnewtitle "+
				" and pnew.pnewtitle=pic3.pnewtitle "+
				" AND pnew.picturetype=? LIMIT 6";
		List<Object> params=new ArrayList<Object>();
		params.add(type);
		list=JdbcUtils.findMoreRefResult(sql,params,PictureItem.class);
		return list ;
	}

}
