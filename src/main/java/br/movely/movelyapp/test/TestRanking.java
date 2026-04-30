package br.movely.movelyapp.test;

import br.movely.movelyapp.ranking.Ranking;
import br.movely.movelyapp.ranking.RankingService;
import br.movely.movelyapp.rankingentry.RankingEntry;

import java.util.List;
import java.util.Map;

public class TestRanking {

    public static void main(String[] args) {

        RankingService service = new RankingService();

        List<Integer> usuarios = List.of(1, 2, 3, 4);

        Map<Integer, Double> pontuacao = Map.of(
                1, 100.0,
                2, 200.0,
                3, 150.0,
                4, 200.0 // empate
        );

        Ranking ranking = service.gerarRanking(10, 5, usuarios, pontuacao);

        for (RankingEntry entry : ranking.getEntries()) {
            System.out.println(
                    "Usuario: " + entry.getUsuarioId() +
                            " | Pontos: " + entry.getPontuacao() +
                            " | Posição: " + entry.getPosicao()
            );
        }
    }
}