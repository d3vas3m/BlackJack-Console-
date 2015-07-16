package dragfire;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

class S
{
	public static void o(String s)
	{
		System.out.println(s);
	}
}

class Card
{
	private String suit, rank;

	public Card(String suit, String rank)
		{
			this.suit = suit;
			this.rank = rank;
			// System.out.println("Card: " + this.suit + " " + this.rank);
		}

	public String getSuit()
	{
		return this.suit;
	}

	public String getRank()
	{
		return this.rank;
	}
}

class Deck
{

	private String[] suits = { "SPADES", "DIAMONDS", "HEARTS", "CLUBS" };
	private int len;
	private Card card;
	private ArrayList<Card> deck;
	protected HashMap<String, String> values = new HashMap<String, String>();;
	private String[] ranks = { "2", "3", "4", "5", "6", "7", "8", "9", "10",
			"J", "Q", "K", "A" };
	private String[] value = { "2", "3", "4", "5", "6", "7", "8", "9", "10",
			"10", "10", "10", "1" };

	public Deck()
		{
			// TODO Auto-generated constructor stub
			initializeDeck();
		}

	private void initializeDeck()
	{
		setValues();
		Card card;
		this.deck = new ArrayList<Card>();
		for (String suit : suits)
		{
			for (String rank : ranks)
			{
				card = new Card(suit, rank);
				this.deck.add(card);
			}
		}
		shuffle(this.deck);
		// printDeck();
	}

	private void printDeck()
	{
		for (Card card : deck)
		{
			System.out.println("Card: " + card.getSuit() + " " + card.getRank()
					+ " : " + values.get(card.getRank()));
		}
	}

	private void setValues()
	{
		for (int i = 0; i < value.length; i++)
		{
			values.put(ranks[i], value[i]);
		}
	}

	private void shuffle(ArrayList<Card> array)
	{
		int index;
		Card temp;
		Random random = new Random();
		for (int i = array.size() - 1; i > 0; i--)
		{
			index = random.nextInt(i + 1);
			temp = array.get(index);
			array.set(index, array.get(i));
			array.set(i, temp);
		}
	}

	public Card dealCard()
	{
		len = this.deck.size() - 1;
		card = this.deck.get(len);
		this.deck.remove(len);
		return card;
	}
}

class Hand extends Deck
{
	private ArrayList<Card> cards;
	private int score = 0;

	public Hand()
		{
			// TODO Auto-generated constructor stub
			cards = new ArrayList<Card>();
		}

	private void addCard(Card card)
	{
		this.cards.add(card);
	}

	private int getValue(Card card)
	{
		return Integer.parseInt(values.get(card.getRank()));
	}

	public int total_sum()
	{
		// add logic for aces
		score = 0;
		for (Card card : this.cards)
		{
			score += getValue(card);
		}
		if (count_aces() == 0)
		{
			return score;
		} else
		{
			if (score + 10 > 21)
				return score;
			else
			{
				return score + 10;
			}
		}
	}

	private int count_aces()
	{
		int aces = 0;
		for (Card card : this.cards)
		{
			if (card.getRank().equalsIgnoreCase("A"))
				++aces;
		}
		return aces;
	}

	public void hit()
	{
		addCard(dealCard());
	}

	public void printhands()
	{
		for (Card card : this.cards)
		{
			System.out.println(card.getSuit() + " : " + card.getRank() + " = "
					+ values.get(card.getRank()));
		}
		S.o("Total Sum: " + total_sum());
	}

	public boolean busted()
	{

		if (total_sum() > 21)
		{
			S.o("Busted!!!");
			return true;
		}
		return false;
	}
}

class Player
{
	private String name;
	public Hand hand;
	public Player(String name, Hand h)
		{
			// TODO Auto-generated constructor stub
			this.name = name;
			this.hand = h;
		}
	public Hand return_hand()
	{
		return this.hand;
	}
	
	public String getName()
	{
		return this.name;
	}
}
public class BlackJack
{
	private Hand player_hand, dealer_hand;
	public static Boolean playing, shown, busted;

	public void hit()
	{
		if (!player_hand.busted())
		{
			player_hand.hit();
			player_hand.printhands();
		}
		if (player_hand.busted())
		{
			System.out.println("\nDealer hand:");
			dealer_hand.printhands();
			S.o("Dealer wins");
			playing = false;
		}
	}

	public void stand()
	{
		S.o("Player STAND!");
		if (playing)
		{
			if (!player_hand.busted())
			{
				while (dealer_hand.total_sum() < 17)
				{
					dealer_hand.hit();
				}
				System.out.println("\nDealer hand:");
				dealer_hand.printhands();
				if (dealer_hand.busted())
				{
					S.o("Dealer busted. You win");
				} else
				{

					if (dealer_hand.total_sum() < player_hand.total_sum())
					{
						S.o("\nYou Win");
					} else if (dealer_hand.total_sum() == player_hand.total_sum())
					{
						S.o("\nDraw (PUSH)!!!");
					}
					else
					{
						S.o("\nDealer Wins");
					}
				}
				playing = false;
			}
		}
	}

	public void start()
	{

		Deck deck = new Deck();
		playing = true;
		player_hand = new Hand();
		dealer_hand = new Hand();
		player_hand.hit();
		player_hand.hit();
		System.out.println("Player hand:");
		player_hand.printhands();
		dealer_hand.hit();
		dealer_hand.hit();

	}
	
	public void init()
	{
		Deck deck = new Deck();
		playing = true;
	}

	public void play()
	{
		int choice;
		Scanner scanner = new Scanner(System.in);
		while (playing)
		{
			S.o("\nDo you want to Hit or Stand.\nEnter:\n1. Hit\n2. Stand");
			choice = scanner.nextInt();

			switch (choice)
			{
			case 1:
				hit();
				break;

			case 2:
				stand();
				break;
			}
		}
	}
}
