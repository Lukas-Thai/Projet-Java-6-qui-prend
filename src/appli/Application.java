package appli;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import game_Component.*;
public class Application {
	static private Serie[] serie= new Serie[4];
	static private ArrayList<Joueur> joueur =new ArrayList<>();
	static private ArrayList<Carte> carte_attente= new ArrayList<>(); //permet de retenir les cartes qui ont été posé au tour actuelle
	static private Scanner sc=new Scanner(System.in);
	static private ArrayList<Joueur> pile_puni=new ArrayList<>();
	
	public Application() {
		for(int i=0;i<4;++i) {
			serie[i]=new Serie();
		}
	}
	//met la carte dans la série adaptée et gère le cas où une carte vaut moins que la dernière carte de chaque série
	private static void serie_adapte(Joueur j,Carte a) { 
		String reptest;
		Joueur jtest=null;
		int rep=0;
		int diff=999;
		for (int i=0;i<4;++i) {
			if((a.getNombre()>serie[i].get_derniere_carte()) && (diff > (a.getNombre()-serie[i].get_derniere_carte())) ) {
				rep=i+1;
				diff=a.getNombre()-serie[i].get_derniere_carte();
			}
		}
		if(rep!=0) {
			jtest=serie[rep-1].ajouter_carte(j,a);
			if(jtest!=null) {
				liste_joueur(j); //si la fonction retourne un joueur alors il a été pénalisé 
			}
			
		}
		else {
			System.out.print("Les cartes " );
			System.out.print(carte_attente.get(0).getNombre()+" ("+carte_attente.get(0).getProprio()+")");
			for(int i=1;i<joueur.size();++i) {
				System.out.print(", ");
				System.out.print(carte_attente.get(i).getNombre()+" ("+carte_attente.get(i).getProprio()+")");
			}
			System.out.println(" vont être posées.");
			System.out.println("Pour poser la carte "+a.getNombre()+", "+j.getNom()+" doit choisir la série qu'il va ramasser.");
			for (int i=0;i<4;++i) {
				System.out.println(serie[i]);
			}
			System.out.print("Saisissez votre choix : ");
			while(true) {
				try { //gestion de l'exception où le joueur n'a pas rentré de valeur
					reptest=sc.next();
					rep=Integer.parseInt(reptest);
					if(rep>=1 && rep<=4) {
						serie[rep-1].vider_et_penaliser(j,a);
						liste_joueur(j); //on ajoute le joueur à la liste des joueurs pénalisé
						return;
					}
				}
				catch(NumberFormatException e) {
				}
				System.out.print("Ce n'est pas une série valide, saisissez votre choix : ");
			}
		}		
	}
	//permet d'initialiser le jeu
	private static void debut() {
		try{
			new Paquet();
			new Application();
			File fichier=new File("config.txt"); //ouverture du fichier config.txt
			Scanner sc=new Scanner(fichier);
			while(sc.hasNext()) {
				joueur.add(new Joueur(sc.nextLine()));
			}
			sc.close();
			int nbj=joueur.size();
			if (nbj<2) {
				throw new RuntimeException("Le nombre de joueur est insuffisant");
			}
			else if(nbj>10) {
				throw new RuntimeException("Le nombre de joueur est trop élevé");
			}
			System.out.print("Les "+nbj+ " joueurs sont ");
			System.out.print(joueur.get(0).getNom());
			for (int i=1;i<nbj;++i) {
				if(i==nbj-1) {
					System.out.print(" et ");
				}
				else {
					System.out.print(", ");
				}
				System.out.print(joueur.get(i).getNom());
			}
			System.out.println(". Merci de jouer à  6 qui prend !");
		} catch(FileNotFoundException e){ //le fichier n'est pas trouvé, on arrête le programme
			System.out.println("Le fichier de configuration est introuvable");
			System.exit(1);
		}
	}
	//méthode principal de jeu 
	private static void jeu() {
		String test;
		int testint;
		int nb_serie=4;
		int nbj=joueur.size();
		for (int i=0;i<10;++i) { //boucle des 10 tours
			for(int j=0;j<nbj;++j) { //boucle pour chaque joueurs
				System.out.println("A "+joueur.get(j).getNom()+" de jouer.");
				Console.pause();
				affiche_serie(nb_serie);
				System.out.println(joueur.get(j)); //affichage de sa main
				System.out.print("Saisissez votre choix : ");
				while(true) {
					try{
						test=sc.next(); //choix de la carte
						testint=Integer.parseInt(test);
						Carte joue=joueur.get(j).verification_dans_la_main(testint);
						if(joue!=null) {
							carte_attente.add(joue);
							break;
						}
						else { //la carte n'est pas dans la main
							System.out.print("Vous n'avez pas cette carte, saisissez votre choix : ");
						}
					}catch(NumberFormatException e) { //le format est incorrect
						System.out.print("Vous n'avez pas cette carte, saisissez votre choix : ");
					}
				}
				Console.clearScreen();
			}
			Collections.sort(carte_attente,Carte.comparateur); //tri de la pile de jeu
			for(int j=0;j<nbj;++j) {
				serie_adapte(carte_attente.get(j).getProprio(),carte_attente.get(j)); //on joue chaque carte
			}
			affiche_carte_joue(nbj);
			affiche_serie(nb_serie);
			affiche_pena(); //affiche les joueurs qui ont pris une pénalité durant ce tour
			carte_attente.removeAll(carte_attente);
		}
		sc.close();
	}
	//affiche le score final
	public static void fin () {
		Collections.sort(joueur,Joueur.comparateur);
		System.out.println();
		System.out.println("** Score final");
		for(int i=0;i<joueur.size();++i) {
			int temp=joueur.get(i).compte_final();
			if (temp<2) {
				System.out.println(joueur.get(i).getNom()+" a ramassé " + temp + " tête de boeufs");
			}
			else {
				System.out.println(joueur.get(i).getNom()+" a ramassé " + temp + " têtes de boeufs");
			}
		}
	}
	//ajoute un joueur à la liste des joueurs pénalisé ce tour ci
	private static void liste_joueur(Joueur j) {
		pile_puni.add(j);
	}
	//affiche les joueurs pénalisés
	private static void affiche_pena() {
		if(pile_puni.size()==0) {
			System.out.println("Aucun joueur ne ramasse de tête de boeufs.");
		}
		else  {
			Collections.sort(pile_puni,Joueur.comparateurdp);
			for (int i=0;i<pile_puni.size();++i) {
				if(pile_puni.get(i).getdernierepena()>1) {
					System.out.println(pile_puni.get(i).getNom()+" a ramassé "+pile_puni.get(i).getdernierepena()+" têtes de boeufs");
				}
				else if(pile_puni.get(i).getdernierepena()==1){
					System.out.println(pile_puni.get(i).getNom()+" a ramassé "+pile_puni.get(i).getdernierepena()+" tête de boeufs");
				}
			}
			pile_puni.removeAll(pile_puni); //on vide la liste
		}
	}
	//affiche toutes les séries
	private static void affiche_serie(int nb_serie) {
		for(int i=0;i<nb_serie;++i) { //affichage de fin de tour
			System.out.println(serie[i]);
		}	
	}
	//affiche toutes les cartes qui ont été joué
	private static void affiche_carte_joue(int nbj) {
		System.out.print("Les cartes " ); //affichage du récapitulatif à  la fin d'un tour
		System.out.print(carte_attente.get(0).getNombre()+" ("+carte_attente.get(0).getProprio().getNom()+")");
		for(int i=1;i<nbj;++i) {
			System.out.print(", ");
			System.out.print(carte_attente.get(i).getNombre()+" ("+carte_attente.get(i).getProprio().getNom()+")");
		}
		System.out.println(" ont été posées.");
	}
	
	public static void main(String[] args) {
		debut();
		jeu();
		fin();
	}

}
