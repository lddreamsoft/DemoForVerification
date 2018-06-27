package ldsoft.hlhh.web.dao;

import ldsoft.hlhh.web.entity.Sequence;

public interface SequenceDAO {

	int updateSequence(String name);

	int insertSequence(Sequence sequence);

	long querySequence(String name);

}
