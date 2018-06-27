package ldsoft.hlhh.web.service;

import ldsoft.hlhh.web.entity.Sequence;

public interface SequenceService {

	int updateSequence(String name);

	int insertSequence(Sequence sequence);

	long querySequence(String name);
}
