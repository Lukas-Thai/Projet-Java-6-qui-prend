package game_Component;

import java.util.Comparator;

public class Carte {
	private static int compteur=0;
	private int nombre;
	private int tete;
	private Joueur proprio; //permet de connaître à qui appartient une carte

	public Carte() {
		compteur++;
		nombre=compteur;
		tete=calcul_tete();

	}
	//determine le nombre de tête de boeuf qu'une carte vaut 
	private int calcul_tete() {
		int valeur=0;
		int unite=this.nombre%10;
		int dizaine=this.nombre/10;
		int centaine = dizaine/10;
		dizaine=dizaine%10;
		if(dizaine==unite && centaine==0) {
			valeur+=5;
		}
		if (unite==5) {
			valeur+=2;
		}
		else if(unite==0) {
			valeur=3;
		}
		else if(valeur==0) {
			valeur++;
		}
		return valeur;
	}
	@Override //affiche la valeur d'une carte ainsi que le nombre de tête de boeuf
	public String toString() {
		String affiche=""+this.nombre;
		if (this.tete>1) {
			affiche+=" ("+this.tete+")";
		}
		return affiche;
	}
	public int getNombre() {
		return this.nombre;
	}

	public int getTete() {
		return tete;
	}
	public Joueur getProprio() {
		return proprio;
	}
	public void setProprio(Joueur j) {
		this.proprio=j;
	}
	//permet de comparer deux cartes pour trier
	public static Comparator<Carte> comparateur = new Comparator<Carte>(){
		@Override
		public int compare(Carte o1, Carte o2) {
			if (o1.nombre>o2.nombre) {
				return 1;
			}
			else if(o1.nombre==o2.nombre) {
				return 0;
			}
			else {
				return -1;
			}


		}

	};

}


