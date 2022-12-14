package com.ssafy.royale.domain.game.service;

import com.ssafy.royale.domain.game.dao.DivisionRepository;
import com.ssafy.royale.domain.game.dao.GameRepository;
import com.ssafy.royale.domain.game.domain.Division;
import com.ssafy.royale.domain.game.domain.Game;
import com.ssafy.royale.domain.game.dto.*;
import com.ssafy.royale.domain.game.exception.DivisionNotFoundException;
import com.ssafy.royale.domain.game.exception.GameNotFoundException;
import com.ssafy.royale.domain.league.dao.LeagueRepository;
import com.ssafy.royale.domain.league.domain.League;
import com.ssafy.royale.domain.league.exception.LeagueNotFoundException;
import com.ssafy.royale.domain.user.dao.ApplyRepository;
import com.ssafy.royale.domain.user.dao.UserRepository;
import com.ssafy.royale.domain.user.domain.Apply;
import com.ssafy.royale.domain.user.domain.User;
import com.ssafy.royale.domain.user.dto.ParticipantsDto;
import com.ssafy.royale.domain.user.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService{

    private final UserRepository userRepository;
    private final ApplyRepository applyRepository;
    private final DivisionRepository divisionRepository;
    private final LeagueRepository leagueRepository;
    private final GameRepository gameRepository;

    @Override
    public Boolean autoMakeGame(Long seq) {
        Set<Division> divisions = new HashSet<>();
        League league = leagueRepository.findById(seq).orElseThrow(LeagueNotFoundException::new);
        List<Apply> applyList = applyRepository.findAllByLeague(league);

        applyList.forEach(apply -> divisions.add(apply.getDivision()));
        for (Division division: divisions) {

            List<Apply> applies = applyRepository.findAllByLeagueAndDivision(league, division);
            applies.sort((e1, e2) -> (int) (e1.getTeam().getTeamSeq() - e2.getTeam().getTeamSeq()));

            PlayerTree tree = new PlayerTree();
            addApplicantDummyData(applies);
            for (Apply apply: applies) {
                tree.insert(apply);
            }
            try {
                applies = tree.inorder(tree.root);
                //applies?????? ??? ????????? ?????? ?????? ???????????? ????????????.
                //????????? ?????? -> ?????? ???????????? ?????? ??? ??????????????? ????????? ?????? ??????
                //?????? ?????? ??? ?????? ?????? ??????
                int matCount = 1;
                int tournamentRoundText = 1;
                //8???????????? 8??? -> ?????? ????????? ???????????????
                int tournamentDepthCount = applies.size();
                int applyCount = 0;
                //8???????????? 7?????? ?????? ??????, ??? ???????????? userSeq??? ?????? ??? ???????????????(?????? ?????????)
                while (tournamentDepthCount > 0) {
                    tournamentDepthCount /= 2;

                    for (int i = 0; i < tournamentDepthCount; i++) {
                        Game game = Game.builder()
                                .league(league)
                                .division(division)
                                .matGameNum(matCount++)
                                .tournamentRoundText(tournamentRoundText)
                                .build();
                        if (applyCount < applies.size()) {
                            game.setAddPlayer(applies.get(applyCount++), applies.get(applyCount++));
                        }
                        gameRepository.save(game);
                    }
                    tournamentRoundText++;
                }
            }catch (Exception e){e.printStackTrace();}
        }
        return true;
    }

    //????????? ????????? ???????????? ???????????? ????????? ?????? ?????????
    //?????? ?????? list??? ????????? ?????????????????? game??? ?????? ?????? ??? ????????? ????????????
    //game??? null?????? ?????? ??? null?????? ?????? ?????? ?????????
    @Override
    public List<TournamentResponseDto> getTournament(Long leagueSeq, Long divisionSeq) {
        Division division = divisionRepository.findById(divisionSeq).orElseThrow(DivisionNotFoundException::new);
        League league = leagueRepository.findById(leagueSeq).orElseThrow(LeagueNotFoundException::new);

        //????????? division????????? ???????????? ???????????? game??? ??????
        List<Game> gameList = gameRepository.findAllByLeagueAndDivision(league, division);
        List<TournamentResponseDto> tournamentResponseDtoList = new ArrayList<>();
        //gameList??? ????????? N-1???
        for (int i=0; i < gameList.size(); i++) {
            try{
            List<ParticipantsDto> participantsDtoList = new ArrayList<>();
            //????????? ????????????, ?????? ?????? ????????? ????????? ???????????? ??? ???????????? ????????????
            if(gameList.get(i).getPlayer1_seq() == null && gameList.get(i).getPlayer2_seq() == null){
                participantsDtoList.add(ParticipantsDto.builder().build());
                participantsDtoList.add(ParticipantsDto.builder().build());
            }else if(gameList.get(i).getPlayer1_seq() == null){
                participantsDtoList.add(ParticipantsDto.builder().build());
                System.out.println(gameList.get(i).getPlayer2_seq().getUser().getUserName());
                Apply aa = gameList.get(i).getPlayer2_seq();
                User user = aa.getUser();
                participantsDtoList.add(insertParticipant(gameList.get(i).getPlayer2_seq(),gameList.get(i).getPlayer2_score(), gameList.get(i)));
            }else if(gameList.get(i).getPlayer2_seq() == null){
                participantsDtoList.add(insertParticipant(gameList.get(i).getPlayer1_seq(),gameList.get(i).getPlayer1_score(), gameList.get(i)));
                participantsDtoList.add(ParticipantsDto.builder().build());
            }
            else{
                participantsDtoList.add(insertParticipant(gameList.get(i).getPlayer1_seq(),gameList.get(i).getPlayer1_score(), gameList.get(i)));
                participantsDtoList.add(insertParticipant(gameList.get(i).getPlayer2_seq(),gameList.get(i).getPlayer2_score(), gameList.get(i)));
            }

            //????????? index??? null??????
            Integer nextMatchId = i == gameList.size()-1 ? null : gameList.get((i/2) + (gameList.size() / 2) + 1).getGameSeq().intValue();
            TournamentResponseDto tournamentResponseDto = TournamentResponseDto.builder()
                    .id(gameList.get(i).getGameSeq().intValue())
                    .name(Integer.toString(gameList.get(i).getMatGameNum()))
                    .nextMatchId(nextMatchId)
                    .tournamentRoundText(Integer.toString(gameList.get(i).getTournamentRoundText()))
                    .startTime(Integer.toString(gameList.get(i).getGameSeq().intValue()))
                    .participants(participantsDtoList)
                    .build();
            tournamentResponseDtoList.add(tournamentResponseDto);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return tournamentResponseDtoList;
    }

    @Override
    public Game insertCurrentGameScore(GameScoreRequestDto dto) {
        Game game = gameRepository.findById(dto.getGameSeq()).orElseThrow(GameNotFoundException::new);
        game.setScoreAndWinner(dto.getPlayer1Score(), dto.getPlayer2Score(), dto.getGameWinner());
        gameRepository.save(game);

        return insertNextGame(game, dto);
    }

    @Override
    public GameResponseDto getGameInfo(Long gameSeq) {
        Game game = gameRepository.findById(gameSeq).orElseThrow(GameNotFoundException::new);

        String player1 = game.getPlayer1_seq().getUser().getUserName();
        String player2 = game.getPlayer2_seq().getUser().getUserName();
        String player1Team = game.getPlayer1_seq().getTeam().getTeamName();
        String player2Team = game.getPlayer2_seq().getTeam().getTeamName();
        return GameResponseDto.builder()
                        .player1Name(player1)
                        .player2Name(player2)
                        .player1Team(player1Team)
                        .player2Team(player2Team)
                        .player1Seq(game.getPlayer1_seq().getApplySeq())
                        .player2Seq(game.getPlayer2_seq().getApplySeq())
                        .game(game)
                        .build();
    }

    @Override
    public List<LastGameDto> getLastGame(Long leagueSeq) {
        List<LastGameDto> result = new ArrayList<>();
        League league = leagueRepository.findById(leagueSeq).get();
        List<Game> list = gameRepository.findTop8GameByLeagueAndGameWinnerIsNotNullOrderByGameSeqDesc(league);
        for(Game game : list) {
            User user1 = userRepository.findById(game.getPlayer1_seq().getUser().getUserSeq()).get();
            User user2 = userRepository.findById(game.getPlayer2_seq().getUser().getUserSeq()).get();
            LastGameDto lastGameDto = LastGameDto.builder()
                    .gameSeq(game.getGameSeq())
                    .apply1name(user1.getUserName())
                    .apply2name(user2.getUserName())
                    .apply1score(game.getPlayer1_score())
                    .apply2score(game.getPlayer2_score())
                    .division(game.getDivision())
                    .build();
            result.add(lastGameDto);
        }
        return result;
    }

    /*
    ???????????? ????????? ?????? ??????
    ?????? ?????? ??? ???????????? ???????????? = (???????????????/2) + (?????? * 2??? ^ 2-??????) + (???????????? + 1 / 2)
    ?????? ?????? ??? ???????????? ????????? ??? ???????????? ????????????= (???????????????/2) + (?????? * 2??? ^ 2-??????) + (???????????? + 1 / 2) - 1
    */
    public Game insertNextGame(Game game, GameScoreRequestDto dto){
        int gameCount = gameRepository.countByLeagueAndDivision(game.getLeague(), game.getDivision()) + 1;
        Optional<Game> firstGame = gameRepository.findTop1ByLeagueAndDivisionAndTournamentRoundText(game.getLeague(), game.getDivision(), game.getTournamentRoundText());
        Long nextGameId = 0L;
        if(firstGame.get().getGameSeq() %2 == 0){
            nextGameId = (game.getGameSeq()/2) + (int)(gameCount *  Math.pow(2, (-1) * game.getTournamentRoundText())) + ((firstGame.get().getGameSeq() + 1) / 2);
        }else{
            if(game.getGameSeq() % 2 == 0){
                nextGameId = (game.getGameSeq()/2) + (int)(gameCount *  Math.pow(2, (-1) * game.getTournamentRoundText())) + ((firstGame.get().getGameSeq() + 1) / 2)-1;
            }else{
                nextGameId = (game.getGameSeq()/2) + (int)(gameCount *  Math.pow(2, (-1) * game.getTournamentRoundText())) + ((firstGame.get().getGameSeq() + 1) / 2);
            }
        }
        try {
            Game nextGame = gameRepository.findById(nextGameId).orElseThrow(GameNotFoundException::new);
            Apply winner = applyRepository.findById(dto.getGameWinner()).orElseThrow(MemberNotFoundException::new);
            nextGame.setGameWinner(winner);
            return gameRepository.save(nextGame);
        }catch (Exception e) {e.printStackTrace();}
        return null;
    }

    public void addApplicantDummyData(List<Apply> applies){
        int size = applies.size();
        if(size < 8){
            for (int i = 0; i < 8 - size; i++) {
                applies.add(null);
            }
        }else if(size > 8 && size < 16){
            for (int i = 0; i < 16 - size; i++) {
                applies.add(null);
            }
        }else if(size > 16 && size < 32)
            for (int i = 0; i < 32 - size; i++) {
                applies.add(null);
            }
    }

    public ParticipantsDto insertParticipant(Apply apply, String score, Game game){
        boolean winner = false;
        String status = apply.isWeightCheck() == true ? "DONE" : "NO_SHOW";
        if(game.getGameWinner() == apply.getApplySeq()) winner = true;

        return ParticipantsDto.builder()
                .id(Long.toString(apply.getApplySeq()))
                .name(apply.getUser().getUserName())
                .resultText(score)
                .isWinner(winner)
                .status(status)
                .build();
    }
}
