package Texas_Hold_Em;

import poker.Card;
import poker.DeckOfCards;
import poker.Player;
import poker.PotOfMoney;

import java.util.ArrayList;

public class PrintGame {
    private ArrayList<TexasPlayer> texasPlayers;
    private DeckOfCards deck;
    private PotOfMoney pot;
    private Hand communityCards;


    public PrintGame(ArrayList<TexasPlayer> texasPlayers, DeckOfCards deck, PotOfMoney pot, Hand communityCards){
        this.texasPlayers = texasPlayers;
        this.deck = deck;
        this.pot = pot;
        this.communityCards = communityCards;
    }
    public void cardPrinter(boolean showDown){
        String[] showCard1 = {"╭────╮","|    |", "|    |","╰────╯"};
        String[] showCard2 = {"╭────╮","|    |", "|    |","╰────╯"};
        StringBuilder sb = null;
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        int halfPlayers = texasPlayers.size()/2;
        for (int i = 0; i < showCard1.length; i++) {
            if (i==0) {
                //print name of players
                for (int j = 0; j < texasPlayers.size(); j++) {
                    if (j < halfPlayers && sb != sb1) {
                        sb = sb1;

                    } else if (j >= halfPlayers && sb != sb2){
                        sb1.append("\n");
                        sb = sb2;
                    }

                    if (j==0) {
                        sb.append("| ");
                    }
                    String temp = String.format("%14s","Name = "+texasPlayers.get(j).getName());
                    sb.append(temp).append(" | ");
                }
                sb.append("\n");
            }
            //print cards of players
            for (int j = 0; j < texasPlayers.size(); j++) {
                if (j < halfPlayers && sb != sb1) {
                    sb = sb1;

                } else if (j >= halfPlayers && sb != sb2){
                    sb1.append("\n");
                    sb = sb2;
                }

                if (j==0) {
                    sb.append("| ");
                }
                sb.append(showCard1[i]).append("  ").append(showCard2[i]).append(" | ");
            }
            sb.append("\n");

            if (i == showCard1.length-1) {
                //print stakes of players
                for (int j = 0; j < texasPlayers.size(); j++) {
                    if (j < halfPlayers && sb != sb1) {
                        sb = sb1;

                    } else if (j >= halfPlayers && sb != sb2){
                        sb1.append("\n");
                        sb = sb2;
                    }

                    if (j==0) {
                        sb.append("| ");
                    }
                    sb.append(String.format("%14s","Stake="+texasPlayers.get(j).getStake())).append(" | ");
                }
                sb.append("\n");

                //print Dealer
                for (int j = 0; j < texasPlayers.size(); j++) {
                    if (j < halfPlayers && sb != sb1) {
                        sb = sb1;

                    } else if (j >= halfPlayers && sb != sb2){
                        sb1.append("\n");
                        sb = sb2;
                    }

                    if (j==0) {
                        sb.append("| ");
                    }
                    if(texasPlayers.get(j).isDealer()){
                        sb.append(String.format("%14s","Dealer")).append(" | ");
                    } else {
                        sb.append(String.format("%14s"," ")).append(" | ");
                    }
                }
                sb.append("\n");
            }
        }
        System.out.println(sb1);
        printPublicCard(communityCards);
        System.out.println("\n"+sb2);

    }
//    private String printFaceDownCard(TexasPlayer texasPlayer){
//        String[] showCard1 = {"╭───╮" ,"│   │", "│   │", "╰───╯"};
//        String[] showCard2 = {"╭───╮", "│   │", "│   │", "╰───╯"};
//        if(texasPlayer.hasFolded()){
//            return texasPlayer.getName()+" has folded";
//        }else {
////            if(texasPlayer.isDealer()){
//                cardPrinter(showCard1, showCard2, texasPlayer);
////                String showCard =
////                        "Name:    "+texasPlayer.getName()+"\n"+
////                                "╭───╮" +     "  ╭───╮\n"+
////                                "│" + "   │"+"  │"+"   │\n"+
////                                "│" + "   │"+"  │"+"   │\n"+
////                                "╰───╯"     +"  ╰───╯\n"+
////                                "Dealer and Current Stake="+texasPlayer.getStake();
////                System.out.print(showCard);
//
////                return  "Name:    "+texasPlayer.getName()+"\n"+
////                        "╭───╮" +     "  ╭───╮\n"+
////                        "│" + "   │"+"  │"+"   │\n"+
////                        "│" + "   │"+"  │"+"   │\n"+
////                        "╰───╯"     +"  ╰───╯\n"+
////                        "Dealer and Current Stake="+texasPlayer.getStake()+"\n";
////            }
////            else {
////                cardPrinter(showCard1, showCard2, texasPlayer);
//                /*String showCard =
//                        "Name:    "+texasPlayer.getName()+"\n"+
//                                "╭───╮" +     "  ╭───╮\n"+
//                                "│" + "   │"+"  │"+"   │\n"+
//                                "│" + "   │"+"  │"+"   │\n"+
//                                "╰───╯"     +"  ╰───╯\n"+
//                                "Current Stake="+texasPlayer.getStake()+"\n";
//                System.out.print(showCard);*/
////                return  "Name:    "+texasPlayer.getName()+"\n"+
////                        "╭───╮" +     "  ╭───╮\n"+
////                        "│" + "   │"+"  │"+"   │\n"+
////                        "│" + "   │"+"  │"+"   │\n"+
////                        "╰───╯"     +"  ╰───╯\n"+
////                        "Current Stake="+texasPlayer.getStake()+"\n";
//            }
//        return null;
//    }

    public int[] getIndex(Card card, String[] suits, String[] ranks){
        int[] result = new int[2];
        int suitIndex;
        int rankIndex;
        switch (card.getSuit()){
            case "hearts" -> {
                suitIndex = 0;
            }
            case "diamonds" -> {
                suitIndex = 1;
            }
            case "clubs" -> {
                suitIndex = 2;
            }
            default -> {
                suitIndex = 3;
            }
        }
        if(card.isAce()){
            rankIndex = 0;
        }else if(card.isJack()){
            rankIndex = 10;
        }else if(card.isQueen()){
            rankIndex = 11;
        }else if(card.isKing()){
            rankIndex = 12;
        }else {
            rankIndex = card.getRank()-1;
        }
        result[0] = suitIndex;
        result[1] = rankIndex;
        return result;
    }
    public void printPublicCard(Hand hand){
        final int cardHeight = 3; //counting from 0
        StringBuilder sb = new StringBuilder();
        int suitIndex;
        int rankIndex;
        String[] showCard1 = {"╭────╮", "╰────╯"};
        String[] showCard2 = {"╭────╮", "╰────╯"};
        String[] suits = {"\u001B[31m♥\u001B[0m", "\u001B[32m♦\u001B[0m", "\u001B[33m♣\u001B[0m", "\u001B[34m♠\u001B[0m"};
        //String[] suits = {"♠", "♥", "♦", "♣"};
        String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        for (int j = 0; j <= cardHeight; j++) {
            for(int i=0; i < hand.getHand().length; i++) {
                int[] index = getIndex(hand.getHand()[i], suits, ranks);
                suitIndex = index[0];
                rankIndex = index[1];

                switch (j) {
                    case 0:
                        sb.append(showCard1[0]).append("  ");
                        break;
                    case 1:
                        String tmp = String.format("%2s",ranks[rankIndex]);
                        sb.append("│").append(tmp).append("  │").append("  ");
                        break;
                    case cardHeight:
                        sb.append(showCard1[showCard1.length-1]).append("  ");
                        break;
                    default:
                        sb.append("│   ").append(suits[suitIndex]).append("│").append("  ");
                        break;
                }
            }
            sb.append("\n");
        }
        System.out.print(sb);
    }
//    private void printFaceUpCard(TexasPlayer texasPlayer){
//        Hand hand = texasPlayer.getHand();
//        int suitIndex;
//        int rankIndex;
//        String[] suits = {"\u001B[31m♥\u001B[0m", "\u001B[32m♦\u001B[0m", "\u001B[33m♣\u001B[0m", "\u001B[34m♠\u001B[0m"};
//        //String[] suits = {"♠", "♥", "♦", "♣"};
//        String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
//        for(Card card: hand.getHand()){
//            int[] index = getIndex(card, suits, ranks);
//            suitIndex = index[0];
//            rankIndex = index[1];
//            if(texasPlayer.isDealer()){
//                if(ranks[rankIndex]=="10"){
//                    String showCard =
//                                    "Name:    "+texasPlayer.getName()+"\n"+
//                                    "╭───╮\n" +
//                                    "│" + ranks[rankIndex]  + " │\n" +
//                                    "│  " + suits[suitIndex] +  "│\n" +
//                                    "╰───╯\n"+
//                                    "Dealer and Current Stake="+texasPlayer.getStake();
//                    System.out.print(showCard+" ");
//                }else {
//                    String showCard =
//                                    "Name:    "+texasPlayer.getName()+"\n"+
//                                    "╭───╮\n" +
//                                    "│" + ranks[rankIndex] + "  │\n" +
//                                    "│  " + suits[suitIndex] + "│\n" +
//                                    "╰───╯\n"+
//                                    "Dealer and Current Stake="+texasPlayer.getStake();
//                    System.out.print(showCard+" ");
//                }
//            }else {
//                if(ranks[rankIndex]=="10"){
//                    String showCard =
//                            "Name:    "+texasPlayer.getName()+"\n"+
//                                    "╭───╮\n" +
//                                    "│" + ranks[rankIndex]  + " │\n" +
//                                    "│  " + suits[suitIndex] +  "│\n" +
//                                    "╰───╯\n"+
//                                    "Current Stake="+texasPlayer.getStake();
//                    System.out.print(showCard+" ");
//                }else {
//                    String showCard =
//                            "Name:    "+texasPlayer.getName()+"\n"+
//                                    "╭───╮\n" +
//                                    "│" + ranks[rankIndex] + "  │\n" +
//                                    "│  " + suits[suitIndex] + "│\n" +
//                                    "╰───╯\n"+
//                                    "Current Stake="+texasPlayer.getStake();
//                    System.out.print(showCard+" ");
//                }
//            }
//
//        }
//    }

//    private void printTable(boolean showPublicCards, boolean showFaceDownCards){
//        int count=0;
//        if(showFaceDownCards){
//            for(TexasPlayer player: texasPlayers){
//                if(count==2){
//                    if(showPublicCards){
//                        System.out.println();
//                        printPublicCard(communityCards);
//                        System.out.println();
//                    }
//                    printFaceDownCard(player);
//                    //System.out.print(printFaceDownCard(player)+" ");
//                }
//                else {
//                    printFaceDownCard(player);
////                    System.out.print(printFaceDownCard(player)+"  abc");
//                }
//                count++;
//            }
//        }
//        else {
//            for(TexasPlayer player: texasPlayers){
//                if(count==2){
//                    if(showPublicCards){
//                        System.out.println();
//                        printPublicCard(communityCards);
//                        System.out.println();
//                    }
//                    if(player.hasFolded()){
//                        System.out.print(player.getName()+" has folded  ");
//                    }
//                    else {
//                        printFaceUpCard(player);
//                    }
//                }
//                else {
//                    if(player.hasFolded()){
//                        System.out.print(player.getName()+" has folded  ");
//                    }
//                    else {
//                        printFaceUpCard(player);
//                    }
//                }
//                count++;
//            }
//        }
//        System.out.println();
//    }
    public void table(Rounds currentRound){
        switch (currentRound){
            case PRE_FLOP ->{
                cardPrinter(false);
                break;
            }
            case FLOP ->{
                cardPrinter(false);
                break;
            }
            case TURN ->{
                cardPrinter(false);
                break;
            }
            case RIVER ->{
                cardPrinter(true);
                break;
            }
            //after river round, if there are more than two players left, they need to showDown
            default -> {
                cardPrinter(true);
                break;
            }
        }
    }
}