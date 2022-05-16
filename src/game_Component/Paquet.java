package game_Component;

import java.util.ArrayList;
import java.util.Collections;

public class Paquet {
	static private ArrayList<Carte> paquet= new ArrayList<>();
	
	//remplie le paquet des 104 cartes
	public Paquet() {
		for (int i =0;i<104;++i) {
			paquet.add(new Carte());
		}
		melanger();
	}
	// melange le paquet
	static private void melanger() {
		Collections.shuffle(paquet);
	}
	// retire une carte du paquet et la retourne
	static public Carte piocher() {
		if(paquet.isEmpty()) {
			throw new RuntimeException("Le paquet est vide");
		}
		Carte carte=paquet.remove(0);
		return carte;
	}
}
