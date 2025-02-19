package com.example.baseballprediction.domain.chat.minigame.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.baseballprediction.domain.chat.minigame.entity.MiniGame;
import com.example.baseballprediction.global.constant.Status;

public interface MiniGameRepository extends JpaRepository<MiniGame, Long> {
	
	List<MiniGame> findByGameIdAndStatus(Long gameId, Status status);
	List<MiniGame> findByGameIdAndStatusOrderByCreatedAtAsc(Long gameId, Status status);
	List<MiniGame> findByGameIdAndStatusIn(Long gameId, Collection<Status> statuses);
	
	@Query("SELECT mg FROM MiniGame mg JOIN FETCH mg.member m JOIN FETCH m.team WHERE mg.game.id = :gameId AND mg.status = :status")
	List<MiniGame> findByGameIdAndStatusWithMemberAndTeam(@Param("gameId") Long gameId, @Param("status") Status status);

}