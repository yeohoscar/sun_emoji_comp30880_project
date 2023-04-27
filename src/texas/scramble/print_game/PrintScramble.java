package texas.scramble.print_game;

import poker.PotOfMoney;
import texas.TexasPlayer;
import texas.TexasPrintGame;
import texas.scramble.deck.Tile;

import java.util.ArrayList;
import java.util.List;

public class PrintScramble extends TexasPrintGame {
    private final String[] cardEdge = {"╭────╮", "╰────╯"};

    final int cardHeight = 3; //counting from 0

    private ArrayList<TexasPlayer> texasPlayers;

    private ArrayList<PotOfMoney> pots;

    private List<Tile> communityCards;


    public PrintScramble(ArrayList<TexasPlayer> texasPlayers, ArrayList<PotOfMoney> pots, List<Tile> communityCards) {
        this.texasPlayers = texasPlayers;
        this.pots = pots;
        this.communityCards = communityCards;
    }

    /************************ this method will print the game table and cards of each player and public cards *****************************/
    public void cardPrinter(boolean showDown) {

        //showDown = true;
        StringBuilder sb = null;
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        int halfPlayers = texasPlayers.size() / 2;
        System.out.println("********** Game Table **********");
        for (int i = 0; i <= cardHeight; i++) {
            if (i == 0) {
                /************************ print border of players *****************************/
                int count = 0;
                for (int j = 0; j < texasPlayers.size(); j++) {
                    String temp = String.format("__________________");
                    if (j < halfPlayers && sb != sb1) {
                        sb = sb1;

                    } else if (j >= halfPlayers && sb != sb2) {
                        sb1.append("\n");
                        sb = sb2;
                    }
                    if (j < halfPlayers) {
                        sb.append(temp);
                    } else {
                        sb.append(temp);
                        count++;
                        if(texasPlayers.size()%2==0){
                            if (count == halfPlayers) {
                                sb.append("\n");
                            }
                        }else {
                            if(count == halfPlayers+1){
                                sb.append("\n");
                            }
                        }
                    }
                }

                /************************ print name of players *****************************/
                for (int j = 0; j < texasPlayers.size(); j++) {
                    if (j < halfPlayers && sb != sb1) {
                        sb = sb1;

                    } else if (j >= halfPlayers && sb != sb2) {
                        sb1.append("\n");
                        sb = sb2;
                    }

                    if (j == 0 || j == halfPlayers) {
                        sb.append("│ ");
                    }

                    String temp = String.format("%14s", "Name = " + texasPlayers.get(j).getName());
                    sb.append(temp).append(" │ ");
                }
                sb.append("\n");
            }
            /************************ print cards of players *****************************/
            for (int j = 0; j < texasPlayers.size(); j++) {
                int[] index1, index2;
                if (j < halfPlayers && sb != sb1) {
                    sb = sb1;
                } else if (j >= halfPlayers && sb != sb2) {
                    sb1.append("\n");
                    sb = sb2;
                }

                if (j == 0 || j == halfPlayers) {
                    sb.append("│ ");
                }

                if (texasPlayers.get(j).hasFolded()) {
                    if (i == cardHeight - 1) {
                        String tmp = String.format("%14s", " Folded ");
                        sb.append(tmp).append(" │ ");
                    } else {
                        String tmp = String.format("%14s", " ");
                        sb.append(tmp).append(" │ ");
                    }
                } else {

                    if (i == 0) {
                        sb.append(cardEdge[0]).append("  ").append(cardEdge[0]).append(" │ ");
                    } else if (i == cardHeight) {
                        sb.append(cardEdge[1]).append("  ").append(cardEdge[1]).append(" │ ");
                    } else {
                        if (showDown) {
//                            index1 = getIndex((Card) texasPlayers.get(j).getHand().getHand()[0], suits, ranks);
//                            index2 = getIndex((Card) texasPlayers.get(j).getHand().getHand()[1], suits, ranks);
                            if (i == 1) {
                                String letter1 = String.format("%2s", (texasPlayers.get(j).getHand().getHand()[0]).toString());
                                String letter2 = String.format("%2s", (texasPlayers.get(j).getHand().getHand()[1]).toString());
//                                String tmp = String.format("%2s", ranks[index1[1]]);
//                                String tmp1 = String.format("%2s", ranks[index2[1]]);
                                sb.append("│").append(letter1).append("  │").append("  ").append("│").append(letter2).append("  │").append(" │ ");
                            } else if (i == 2) {
                                int value1 = texasPlayers.get(j).getHand().getHand()[0].getValue();
                                int value2 = texasPlayers.get(j).getHand().getHand()[1].getValue();
                                sb.append("│   ").append(value1).append("│").append("  ").append("│   ").append(value2).append("│").append(" │ ");
                            }
                        } else {
                            //if (texasPlayers.get(j) instanceof HumanTexasPlayer) {
                            if (texasPlayers.get(j).isOnTurn()) {
//                                index1 = getIndex((Card) texasPlayers.get(j).getHand().getHand()[0], suits, ranks);
//                                index2 = getIndex((Card) texasPlayers.get(j).getHand().getHand()[1], suits, ranks);
                                if (i == 1) {
                                    String letter1 = String.format("%2s", (texasPlayers.get(j).getHand().getHand()[0]).toString());
                                    String letter2 = String.format("%2s", (texasPlayers.get(j).getHand().getHand()[1]).toString());
//                                    String tmp = String.format("%2s", ranks[index1[1]]);
//                                    String tmp1 = String.format("%2s", ranks[index2[1]]);
                                    sb.append("│").append(letter1).append("  │").append("  ").append("│").append(letter2).append("  │").append(" │ ");
                                } else if (i == 2) {
                                    int value1 = texasPlayers.get(j).getHand().getHand()[0].getValue();
                                    int value2 = texasPlayers.get(j).getHand().getHand()[1].getValue();
                                    sb.append("│   ").append(value1).append("│").append("  ").append("│   ").append(value2).append("│").append(" │ ");
                                }
                            } else {
                                sb.append("│    │").append("  ").append("│    │").append(" │ ");
                            }
                        }
                    }
                }
            }
            sb.append("\n");

            if (i == cardHeight) {
                /************************ print stakes of players *****************************/
                for (int j = 0; j < texasPlayers.size(); j++) {
                    if (j < halfPlayers && sb != sb1) {
                        sb = sb1;

                    } else if (j >= halfPlayers && sb != sb2) {
                        sb1.append("\n");
                        sb = sb2;
                    }

                    if (j == 0 || j == halfPlayers) {
                        sb.append("│ ");
                    }
                    sb.append(String.format("%14s", "Stake=" + texasPlayers.get(j).getStake())).append(" │ ");
                }
                sb.append("\n");

                /************************ print bank of players *****************************/
                for (int j = 0; j < texasPlayers.size(); j++) {
                    if (j < halfPlayers && sb != sb1) {
                        sb = sb1;

                    } else if (j >= halfPlayers && sb != sb2) {
                        sb1.append("\n");
                        sb = sb2;
                    }

                    if (j == 0 || j == halfPlayers) {
                        sb.append("│ ");
                    }
                    sb.append(String.format("%14s", "Bank=" + texasPlayers.get(j).getBank())).append(" │ ");
                }
                sb.append("\n");

                /************************ print Dealer *****************************/
                for (int j = 0; j < texasPlayers.size(); j++) {
                    if (j < halfPlayers && sb != sb1) {
                        sb = sb1;

                    } else if (j >= halfPlayers && sb != sb2) {
                        sb1.append("\n");
                        sb = sb2;
                    }

                    if (j == 0 || j == halfPlayers) {
                        sb.append("│ ");
                    }
                    if (texasPlayers.get(j).isDealer()) {
                        sb.append(String.format("%14s", "Dealer")).append(" │ ");
                    } else {
                        sb.append(String.format("%14s", " ")).append(" │ ");
                    }
                }
                sb.append("\n");
                /************************ print border *****************************/
                int count = 0;
                for (int j = 0; j < texasPlayers.size(); j++) {
                    String temp = String.format("------------------");

                    if (j < halfPlayers && sb != sb1) {
                        sb = sb1;

                    } else if (j >= halfPlayers && sb != sb2) {
                        sb1.append("\n");
                        sb = sb2;
                    }
                    if (j < halfPlayers) {
                        sb.append(temp);
                    } else {
                        sb.append(temp);
                        count++;
                        if(texasPlayers.size()%2==0){
                            if (count == halfPlayers) {
                                sb.append("\n");
                            }
                        }else {
                            if(count == halfPlayers+1){
                                sb.append("\n");
                            }
                        }
                    }
                }
            }
        }
        System.out.println(sb1);
        if (communityCards.isEmpty()) {
            System.out.println("Public cards not shown yet");
        } else {
            System.out.println("Public cards: ");
        }
        printPublicCard(communityCards);
        System.out.print("\n" + sb2);


        for (int i = 0; i < pots.size(); i++) {
            if (i == 0) {
                System.out.println("| Main Pot:\n|    CurrentStake=" + pots.get(i).getCurrentStake() + "     Total=" + pots.get(i).getTotal());
            } else {
                System.out.println("| Side Pot:\n|    CurrentStake=" + pots.get(i).getCurrentStake() + "     Total=" + pots.get(i).getTotal());
            }
        }
        System.out.println("\n");
    }
//    /************************ this method will return the index of suit and rank of one card in suits[] and ranks[] *****************************/
//    public int[] getIndex(Tile card, String[] suits, String[] ranks) {
//        int[] result = new int[2];
//        int suitIndex;
//        int rankIndex;
//        switch (card.getSuit()) {
//            case "hearts" -> {
//                suitIndex = 0;
//            }
//            case "diamonds" -> {
//                suitIndex = 1;
//            }
//            case "clubs" -> {
//                suitIndex = 2;
//            }
//            default -> {
//                suitIndex = 3;
//            }
//        }
//        if (card.isAce()) {
//            rankIndex = 0;
//        } else if (card.isJack()) {
//            rankIndex = 10;
//        } else if (card.isQueen()) {
//            rankIndex = 11;
//        } else if (card.isKing()) {
//            rankIndex = 12;
//        } else {
//            rankIndex = card.getRank() - 1;
//        }
//        result[0] = suitIndex;
//        result[1] = rankIndex;
//        return result;
//    }

    /************************ this method will print all public cards on game table *****************************/
    public void printPublicCard(List<Tile> communityCards) {
        StringBuilder sb = new StringBuilder();
        int suitIndex;
        int rankIndex;
        for (int j = 0; j <= cardHeight; j++) {
            for (int i = 0; i < communityCards.size(); i++) {
//                int[] index = getIndex(communityCards.get(i), suits, ranks);
//                suitIndex = index[0];
//                rankIndex = index[1];
                String letter1 = String.format("%2s", communityCards.get(i).toString());
                int value1 = communityCards.get(i).getValue();
                switch (j) {
                    case 0:
                        sb.append(cardEdge[0]).append("  ");
                        break;
                    case 1:
                        String tmp = String.format("%2s", letter1);
                        sb.append("│").append(tmp).append("  │").append("  ");
                        break;
                    case cardHeight:
                        sb.append(cardEdge[cardEdge.length - 1]).append("  ");
                        break;
                    default:
                        sb.append("│   ").append(value1).append("│").append("  ");
                        break;
                }
            }
            sb.append("\n");
        }
        System.out.print(sb);
    }
}