package com.example.baseballprediction.domain.member.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.baseballprediction.domain.member.dto.FairyProjection;
import com.example.baseballprediction.domain.member.dto.ProfileProjection;
import com.example.baseballprediction.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByUsername(String username);

	Optional<Member> findByNickname(String nickname);

	@Query(
		"SELECT new com.example.baseballprediction.domain.member.dto.ProfileProjection(m.nickname, m.profileImageUrl, "
			+ "sum(case when f.type = 'WIN' then 1 else 0 end) as winFairyCount, "
			+ "sum(case when f.type = 'LOSE' then 1 else 0 end) as loseFairyCount, "
			+ "t.name as teamName, m.comment"
			+ ") "
			+ "FROM Member m "
			+ "LEFT JOIN MonthlyFairy f ON f.member = m "
			+ "JOIN Team t ON m.team = t "
			+ "WHERE m.nickname = :nickname "
			+ "GROUP BY m.nickname, m.profileImageUrl")
	Optional<ProfileProjection> findProfile(String nickname);

	@Query(
		value =
			"select row_number() over(order by s.count desc) as totalRank, concat(case when s.type = 'WIN' then '승리요정 ' else '패배요정 ' end, s.fairy_rank, '등') as title, s.count"
				+ "  from (select type, fairy_rank, count(*) as count "
				+ "  from monthly_fairy "
				+ " where member_id = :memberId "
				+ "group by type, fairy_rank) s "
				+ "order by count desc",
		nativeQuery = true
	)
	List<FairyProjection> findFairyStatistics(Long memberId);
}
