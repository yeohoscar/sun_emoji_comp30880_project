
package texas_hold_em;

import poker.*;


import java.util.*;

// This package provides classes necessary for implementing a game system for playing poker

// A RoundOfPoker is a single round/deal in a game
// A PokerGame is a sequence of RoundOfPoker's

public class RoundsOfTexas extends RoundController {
    private PrintGame printGame;
    private ArrayList<TexasPlayer> roundPlayers;
    public RoundsOfTexas(DeckOfCards deck, ArrayList<TexasPlayer> texasPlayers, List<Card> communityCards, int dealerIndex) {
        super(deck, texasPlayers, communityCards, dealerIndex);
        this.roundPlayers = texasPlayers;
        //this.printGame = new PrintGame(texasPlayers, deck, pot);

        initComputerPlayerWithCommunityCards(communityCards);
    }

    private void initComputerPlayerWithCommunityCards(List<Card> communityCards) {
        for (TexasPlayer player : roundPlayers) {
            if (player instanceof ComputerTexasPlayer) {
                ((ComputerTexasPlayer) player).setCommunityCards(communityCards);
            }
        }
    }

    @Override
    public void showDown() {
        if (onePlayerLeft()) {
            for (TexasPlayer player : roundPlayers) {
                if (!player.hasFolded()) {
                    for(PotOfMoney pot:pots){
                        player.takePot(pot);
                    }
                }
            }
        } else {
            HashMap<Integer, Integer> valueRank = new HashMap<>();
            // calculate handValue for each player
            for (int i = 0 ;i<roundPlayers.size();i++) {
                TexasPlayer player =roundPlayers.get(i);
                if (!player.hasFolded()) {
                    Card[] communityCardsArr = new Card[communityCards.size()];
                    player.findBestHand(communityCards.toArray(communityCardsArr), deck);
                    System.out.println(player.getCurrentBestHand());
                    int handValue = player.getCurrentBestHand().getValue();
                    valueRank.put(i, handValue);
                }
            }
            // find who has the largest handValue
            System.out.println(valueRank);

            for (int i = pots.size() - 1; i >= 0; i--) {
                PotOfMoney pot = pots.get(i);
                int potAmount = pot.getTotal();
                HashMap<Integer, Integer> winners = new HashMap<>();
                int highestHandValue=-1;
                // Find the eligible winners for this pot based on hand value
                for (int playerId : valueRank.keySet()) {
                    if (pot.getPlayerIds().contains(playerId)) {
                        int handValue = valueRank.get(playerId);
                        if (handValue > highestHandValue) {
                            highestHandValue = handValue;
                            winners.clear();
                            winners.put(playerId, handValue);
                        } else if (handValue == highestHandValue) {
                            winners.put(playerId, handValue);
                        }
                    }
                }

                // Divide the pot amount equally among the winners
                if (!winners.isEmpty()) {
                    int splitAmount = potAmount / winners.size();
                    for (int winnerId : winners.keySet()) {
                        TexasPlayer winner = getPlayerById(roundPlayers,winnerId);
                        winner.winFromPot(splitAmount,pot);

                    }
                }
            }


        }
    }

    public TexasPlayer getPlayerById(List<TexasPlayer> roundPlayers, int playerId) {
        for (TexasPlayer player : roundPlayers) {
            if (player.getId() == playerId) {
                return player;
            }
        }
        return null; // Player with the given ID was not found
    }

    @Override
    public void removePlayer() {
        Iterator<TexasPlayer> iterator = roundPlayers.iterator();
        while (iterator.hasNext()) {
            TexasPlayer player = iterator.next();
            if (player.getBank() < BIG_BLIND_AMOUNT) {
                iterator.remove();
            }
        }

        for(TexasPlayer player : roundPlayers){
            player.reset();
            player.resetDealer();
        }
    }


    @Override
    public void createSidePot() {
        ArrayList<Integer> playerList = getActivePot().getPlayerIds();
        ArrayList<Integer> allInPlayer = new ArrayList<>();
        for(int id : playerList){
            if(getPlayerById(roundPlayers,id).isAllIn()){
                allInPlayer.add(id);
            }
        }
        if (allInPlayer.size()==0){return;}

        Collections.sort(allInPlayer, new Comparator<Integer>() {
            @Override
            public int compare(Integer playerId1, Integer playerId2) {
                Player player1 = getPlayerById(roundPlayers, playerId1);
                Player player2 = getPlayerById(roundPlayers, playerId2);
                return Integer.compare(player1.getStake(), player2.getStake());
            }
        });

        for(int ID : allInPlayer){
            TexasPlayer player = getPlayerById(roundPlayers,ID);
            PotOfMoney sidePot = new PotOfMoney();
            PotOfMoney lastPot = getActivePot();
            ArrayList<Integer> newPlayerIds = new ArrayList<>(lastPot.getPlayerIds());
            newPlayerIds.removeIf(id -> id == player.getId());
            int activePlayer = getActivePot().getPlayerIds().size();
            int previousStake = 0;
            for(PotOfMoney pot :pots){
                previousStake+=pot.getTotal();
            }

            sidePot.setStake(lastPot.getCurrentStake());
            sidePot.setTotal(previousStake-player.getStake()*activePlayer);
            sidePot.setPlayerIds(newPlayerIds);
            lastPot.setTotal(player.getStake()*activePlayer);

            pots.add(sidePot);
        }

    }


}