package game_Component;

import org.junit.Test;

class testSerieJoueur {

	@Test
	void test() {
		new Paquet();
		Joueur j=new Joueur("Lukas");
		assert(j.compte_final()==0); //v�rification que le joueur n'a pas de carte dans sa pile de p�nalit�
		j.punition(Paquet.piocher()); //on y ajoute une carte au hasard
		int a =j.compte_final(); // on v�rifie que le compteur a bien augment�
		assert(a>0); 
		Serie s=new Serie();
		for(int i=0;i<4;++i) { //on remplie la s�rie a 5 cartes, sa limite
			s.ajouter_carte(j, Paquet.piocher());
		}
		assert(j.compte_final()==a); //on v�rifie que le joueur n'a pas encore �t� sanctionn�
		s.ajouter_carte(j, Paquet.piocher()); //on fait d�bord� la s�rie
		assert(j.compte_final()>a); //on v�rifie que le joueur a bien �t� sanctionn�
	}

}
