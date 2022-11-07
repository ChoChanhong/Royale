package com.ssafy.royale.domain.game.dto;

import com.ssafy.royale.domain.game.dao.GameRepository;
import com.ssafy.royale.domain.game.domain.Game;
import com.ssafy.royale.domain.game.domain.GameLog;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
@Builder
@ApiModel("GameLogDto")
public class GameLogDto {
    private int score1;
    private int score2;
    private int advantage1;
    private int advantage2;
    private int penalty1;
    private int penalty2;

    public static GameLogDto from(GameLog gameLog) {
        if (gameLog == null) throw new RuntimeException("엔티티가 null입니다.");

        return GameLogDto.builder()
                .score1(gameLog.getScore1())
                .score2(gameLog.getScore2())
                .advantage1(gameLog.getAdvantage1())
                .advantage2(gameLog.getAdvantage2())
                .penalty1(gameLog.getPenalty1())
                .penalty2(gameLog.getPenalty2())
                .build();
    }
}
