package game_Component;

import java.util.ArrayList;

public class Serie {
	private ArrayList<Carte> contenu=new ArrayList<>();
	static private int cpt=0;
	private int nb;
	
	public Serie() {
		cpt++;
		this.nb=cpt;
		this.contenu.add(Paquet.piocher());
	}
	public Joueur ajouter_carte(Joueur j,Carte c) {
		if (this.contenu.size()>=5) {
			vider_et_penaliser(j,c);
			return j;
		}
			this.contenu.add(c);
			return null;
	}
	// vide la série et pénalise le joueur 
	public void vider_et_penaliser(Joueur j ,Carte c) {
		int compteur=0;
		int taille=contenu.size();
		for (int i=0;i<taille;++i) {
			compteur+=contenu.get(0).getTete();
			j.punition(contenu.get(0));
			contenu.remove(0);
		}
		this.contenu.add(c);
		j.setdernierepena(compteur);
	}
	public int get_derniere_carte() {
		return contenu.get(contenu.size()-1).getNombre();
	}
	//affiche le numero de la serie ainsi que son contenu
	@Override
	public String toString() {
		String affiche="- série n° "+this.nb+" : ";
		affiche+=contenu.get(0).toString();
		for (int i=1;i<contenu.size();++i) {
			affiche+=", ";
			affiche+=contenu.get(i).toString();
		}
		return affiche;
	}
}
