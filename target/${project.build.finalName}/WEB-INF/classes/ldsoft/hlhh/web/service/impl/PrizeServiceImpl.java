package ldsoft.hlhh.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ldsoft.hlhh.web.dao.PrizeDAO;
import ldsoft.hlhh.web.entity.Prize;
import ldsoft.hlhh.web.service.PrizeService;


@Service
public class PrizeServiceImpl implements PrizeService{
	
	@Autowired
	private PrizeDAO prizeDAO;

	@Override
	public int insertPrize(Prize prize) {
		return prizeDAO.insertPrize(prize);
	}


	@Override
	public Prize queryById(int id) {
		
		return prizeDAO.queryById(id);
	}


	@Override
	public List<Prize> queryAll(int offset, int limit) {		

		return prizeDAO.queryAll(offset,limit);
	}


	@Override
	public int deletePrize(int id) {
		
		return prizeDAO.deletePrize(id);
		
	}


	@Override
	public int updatePrize(Prize prize) {
		
		return prizeDAO.updatePrize(prize);
	}


	@Override
	public int updateOnlyPicturesOfPrize(Prize prize) {
		
		return prizeDAO.updateOnlyPicturesOfPrize(prize.getId(),prize.getPictures());
	}


	@Override
	public List<Prize> selectAvailablePrizes(int activityID) {
		
		return prizeDAO.selectAvailablePrizes(activityID);
	}
	
}
