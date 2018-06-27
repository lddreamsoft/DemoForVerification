package ldsoft.hlhh.web.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ldsoft.hlhh.web.dao.SequenceDAO;
import ldsoft.hlhh.web.entity.Sequence;
import ldsoft.hlhh.web.service.SequenceService;


@Service
public class SequenceServiceImpl implements SequenceService{
	
	@Autowired
	private SequenceDAO sequenceDAO;

	@Override
	public int updateSequence(String name) {
		return sequenceDAO.updateSequence(name);
	}

	@Override
	public int insertSequence(Sequence sequence) {
		return sequenceDAO.insertSequence(sequence);
	}

	@Override
	public long querySequence(String name) {
		return sequenceDAO.querySequence(name);
	}
	
}
