package game_Component;

import org.junit.Test;

class testSerieJoueur {

	@Test
	void test() {
		new Paquet();
		Joueur j=new Joueur("Lukas");
		assert(j.compte_final()==0); //vérification que le joueur n'a pas de carte dans sa pile de pénalité
		j.punition(Paquet.piocher()); //on y ajoute une carte au hasard
		int a =j.compte_final(); // on vérifie que le compteur a bien augmenté
		assert(a>0); 
		Serie s=new Serie();
		for(int i=0;i<4;++i) { //on remplie la série a 5 cartes, sa limite
			s.ajouter_carte(j, Paquet.piocher());
		}
		assert(j.compte_final()==a); //on vérifie que le joueur n'a pas encore été sanctionné
		s.ajouter_carte(j, Paquet.piocher()); //on fait débordé la série
		assert(j.compte_final()>a); //on vérifie que le joueur a bien été sanctionné
	}

}
