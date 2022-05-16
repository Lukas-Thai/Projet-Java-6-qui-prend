package game_Component;

import static org.junit.Assert.fail;

import org.junit.Test;

class testPaquetCarte {

	@Test
	void test() {
		new Paquet();
		for(int i=0;i<104;++i) { //vidage du paquet
			Carte c=Paquet.piocher();
			int a=c.getNombre();
			assert(a>=1); //v�rification que la carte est conforme
			assert(a<=104);
			int b=c.getTete();
			assert(b>=1);
			assert(b<=7);
		}
		try {
			@SuppressWarnings("unused")
			Carte c=Paquet.piocher(); //v�rification que le paquet est vide
			fail("Le paquet est cens� �tre vide");
		}
		catch(RuntimeException e) {
			return;
		}
	}

}
