package game_Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Joueur {
	private String nom;
	private ArrayList<Carte> main= new ArrayList<>();
	private ArrayList<Carte> penalite= new ArrayList<>();
	private int dernierepena=0; //permet de connaître à combien s'élève la dernière pénalité d'un joueur

	public Joueur(String nom) {
		this.nom=nom;
		for (int i = 0;i<10;++i) {
			this.main.add(Paquet.piocher());
			main.get(i).setProprio(this);
		}
		Collections.sort(main,Carte.comparateur);

	}
	//ajoute une carte à la pile de pénalité du joueur
	public void punition(Carte c) {
		this.penalite.add(c);
	}
	public int getdernierepena() {
		return this.dernierepena;
	}
	public void setdernierepena(int i) {
		this.dernierepena=i;
	}
	//permet de trouver si une carte est dans la main par recherche dichotomique sinon la fonction retourne null
	public Carte verification_dans_la_main(int a) {
		int b=0;
		int c=main.size()-1;
		while(c-b!=0) {
			if(a==main.get((b+c)/2).getNombre()) {
				Carte temp=main.remove((b+c)/2);
				return temp;
			}
			else if (main.get((b+c)/2).getNombre()>a) {
				c=(c+b)/2;
			}
			else {
				b=(b+c)/2;
			}
		}
		if(a==main.get((b+c)/2).getNombre()) {
			Carte temp=main.remove((b+c)/2);
			return temp;
		}
		return null;
	}
	// permet de connaître le total de tête de boeuf obtenu pour le score final
	public int compte_final() {
		int rep=0;
		for (int i=0;i<penalite.size();++i) {
			rep+=penalite.get(i).getTete();
		}
		return rep;
	}
	//affiche la main d'un joueur
	@Override
	public String toString() {
		String affiche="- Vos cartes : ";
		affiche+=main.get(0).toString();
		for (int i=1;i<main.size();++i) {
			affiche+=", ";
			affiche+=main.get(i).toString();
		}
		return affiche;
	}
	public String getNom() {
		return this.nom;
	}
	public static Comparator<Joueur> comparateur = new Comparator<Joueur>(){
		@Override
		public int compare(Joueur j1, Joueur j2) { //compare en fonction de leur nombre de tête accumulé
			if (j1.compte_final()>j2.compte_final()) {
				return 1;
			}
			else if(j1.compte_final()==j2.compte_final()) {
				return j1.getNom().compareTo(j2.getNom());
			}
			else {
				return -1;
			}
		}
	};
	public static Comparator<Joueur> comparateurdp = new Comparator<Joueur>(){
		@Override
		public int compare(Joueur j1, Joueur j2) { //compare en foncction de la dernière pénalité reçu
			if (j1.getdernierepena()>j2.getdernierepena()) {
				return 1;
			}
			else if(j1.getdernierepena()==j2.getdernierepena()) {
				return j1.getNom().compareTo(j2.getNom());
			}
			else {
				return -1;
			}
		}
	};


}