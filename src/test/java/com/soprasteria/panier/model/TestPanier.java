package com.soprasteria.panier.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.soprasteria.panier.model.Article;
import com.soprasteria.panier.model.Panier;
import com.soprasteria.panier.model.exceptions.ArticleInexistantException;
import com.soprasteria.panier.model.exceptions.MontantTropEleveException;
import com.soprasteria.panier.model.exceptions.QuantiteArticleTropGrandeException;
import com.soprasteria.panier.model.exceptions.TropDeReferencesException;
import com.soprasteria.tools.OutilsChaine;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de tests unitaires de la classe Panier. Cette classe implémente différente méthode de test afin de
 * vérifier la conformité de notre application aux exigences suivantes :
 *  - EXG_PAN_01
 *  - EXG_PAN_02
 *  - EXG_PAN_03
 *  - EXG_PAN_04
 *  - EXG_PAN_05
 *  - EXG_PAN_06
 *  - EXG_REMISE_01
 *  - EXG_TICKET_01
 *
 * Comme On le voit dans les tests ci-dessous 3 tests échoue
 *  - testAjouterQuantiteArticleEnTrop()
 *  - testVerificationRemise()
 *  - testImpressionTicket()
 * Comme je n'était pas sûr de si il fallait laisser les tests en échecs ou non (n'ayant pas eu le
 * temps de demandé en TP), j'ai décidé de les laisser en échecs avec les vraies valeurs attendus
 * d'après les exigences qui nous étaient fournies.
 *
 * @author : Kerryghan Relot
 * @version : 1.0
 * @see : Panier
 */
public class TestPanier {
	/**
	 * Les donnees de test
	 */
	static Panier pan;
	static Article art1, art2, art3;
	static ArrayList<Article> liste;

	/**
	 * Initialisation des donnees de test avant l'ensemble des tests
	 */
	@BeforeAll
	public static void initTests() {
		pan = new Panier( );
		art1 = new Article( 100.00 , "REF001" , "LIBELLE01" , 9.99 );
		art2 = new Article( 100.00 , "REF002" , "LIBELLE02" , 10.00 );
		liste = new ArrayList<Article>( );
		for (int i = 0; i < 100000; i++)
			liste.add( new Article( 100.00 , "REF00" + i , "LIB" + i , 10.00 ) );
	}

	/**
	 * Remise a zero du panier avant chaque test
	 */
	@BeforeEach
	public void avantTest() {
		pan.vider();
	}

	/**
	 * Test unitaire couvrant le service fonctionnel d'ajout d'articles de l'exigence EXG_PAN_01
	 * On doit pouvoir ajouter un article dans un panier et verifier que le panier contient
	 * un élément de plus que précédemment
	 */
	@Test
	public void testAjouterArticle() throws TropDeReferencesException, QuantiteArticleTropGrandeException, MontantTropEleveException {
		// Preparation
		// Pas de préparation nécessaire ici puisque le panier est vidé avec le @BeforeEach

		// Execution
		pan.ajouterArticle(art1, 1);

		// Verification
		Assertions.assertEquals(1, pan.nbReferences());
		Assertions.assertEquals(1, pan.nbArticles("REF001"));
	}

	/**
	 * Test unitaire couvrant le service fonctionnel de retrait d'articles de l'exigence EXG_PAN_01
	 * On doit pouvoir retirer un article d'un panier et verifier que le panier contient
	 * un élément de moins que précédemment
	 */
	@Test
	public void testRetirerArticle() throws TropDeReferencesException, QuantiteArticleTropGrandeException, MontantTropEleveException, ArticleInexistantException {
		// Preparation
		pan.ajouterArticle(art1, 1);  // On démarre avec un panier contenant un article

		// Execution
		pan.retirerArticle("REF001");

		// Verification
		Assertions.assertEquals(0, pan.nbReferences());
	}

	/**
	 * Test unitaire couvrant le service fonctionnel de fixation de quantité d'articles de l'exigence EXG_PAN_01
	 * On doit pouvoir ajouter plusieurs articles dans un panier au moment de leur ajout et verifier que le
	 * panier contient bien autant d'élément en plus que précédemment
	 */
	@Test
	public void testFixerQuantiteArticle() throws TropDeReferencesException, QuantiteArticleTropGrandeException, MontantTropEleveException {
		// Preparation
		// Pas de préparation nécessaire ici puisque le panier est vidé avec le @BeforeEach

		// Execution
		pan.ajouterArticle(art1, 5);

		// Verification
		Assertions.assertEquals(1, pan.nbReferences());
		Assertions.assertEquals(5, pan.nbArticles("REF001"));
	}

	/**
	 * Test unitaire couvrant le service fonctionnel de modification de quantité d'articles de l'exigence EXG_PAN_01
	 * On doit pouvoir modifier la quantité d'un article dans notre panier et verifier que le panier contient
	 * bien le nouveau nombre d'article choisi
	 */
	@Test
	public void testModifierQuantiteArticle() throws TropDeReferencesException, QuantiteArticleTropGrandeException, MontantTropEleveException, ArticleInexistantException {
		// Preparation
		pan.ajouterArticle(art1, 1);  // On prépare un panier avec un article dont on modifiera sa quantité

		// Execution
		pan.modifierQuantiteArticle("REF001", 5);

		// Verification
		Assertions.assertEquals(1, pan.nbReferences());
		Assertions.assertEquals(5, pan.nbArticles("REF001"));
	}

	/**
	 * Test unitaire couvrant le service fonctionnel de vidage de panier de l'exigence EXG_PAN_02
	 * Un panier doit pouvoir être vidé en une seule opération
	 */
	@Test
	public void testViderPanier() throws TropDeReferencesException, QuantiteArticleTropGrandeException, MontantTropEleveException {
		// Preparation
		pan.ajouterArticle(art1, 1);
		pan.ajouterArticle(art2, 3);
		pan.ajouterArticle(art1, 6); // On prépare un panier avec plusieurs articles dedans

		// Execution
		pan.vider();

		// Verification
		Assertions.assertEquals(0, pan.nbReferences());
	}

	/**
	 * Test unitaire couvrant le service fonctionnel de création de panier de l'exigence EXG_PAN_03
	 * Le système devra permettre de créer des paniers.
	 */
	@Test
	public void testCreationPanier() throws TropDeReferencesException, QuantiteArticleTropGrandeException, MontantTropEleveException {
		// Preparation
		// Pas de préparation nécessaire ici, on exécute directement le constructeur de l'objet

		// Execution
		Panier p = new Panier( );

		// Verification
		assertEquals(0, p.nbReferences());
		// Optionnel, non demandé
		assertFalse(p.remiseActive());
		assertEquals(0, p.lirePourcentageRemise());
		assertEquals(0, p.lireMontantSeuil());
	}

	/**
	 * Test unitaire couvrant l'exigence EXG_PAN_04
	 * On ne peut mettre plus de 5 références par panier.
	 * @throws TropDeReferencesException : On ne peut mettre que 5 références d'articles différentes dans un panier au maximum.
	 */
	@Test
	public void testAjouterReferenceEnTrop() throws TropDeReferencesException, QuantiteArticleTropGrandeException, MontantTropEleveException {
		// Preparation
		// On va préparer une liste avec 5 article différents dedans
		for (int i = 0; i < 5; i++) {
			pan.ajouterArticle(liste.get(i), 1);
		}

		// Execution Puis Verification
		TropDeReferencesException e = assertThrows(TropDeReferencesException.class, () -> {
			pan.ajouterArticle(liste.get(5), 1);  // Puis on y ajoute un sixième
		});
	}

	/**
	 * Test unitaire couvrant l'exigence EXG_PAN_05
	 * n ne peut mettre plus de 10 articles par référence par panier.
	 * @throws QuantiteArticleTropGrandeException : Chaque article dans le panier ne peut être commandé qu'en 10 quatité maximum.
	 */
	@Test
	public void testAjouterQuantiteArticleEnTrop() throws TropDeReferencesException, QuantiteArticleTropGrandeException, MontantTropEleveException {
		// Preparation
		// On préprare un panier avec un article en 10 quantités
		pan.ajouterArticle(art1, 10);  // Une erreur se produit ici à cause d'un bug qui fait qu'on ne poursuit pas le test

		// Execution Puis Verification
		QuantiteArticleTropGrandeException e = assertThrows(QuantiteArticleTropGrandeException.class, () -> {
			pan.ajouterArticle(art1, 1);  // Puis on essaye d'en rajouter un onzième.
		});

		/**
		 * Comme on peut le voir, il y a un bug dans la fonction ajouterArticle() car le document
		 * d'exigence spécifie que nous devrions pouvoir avoir 10 articles au maximum dans notre
		 * panier (mais pas 11) (par référence). Hors, le 10e article n'est en fait pas accépté (la limite maximum
		 * effective est de 9 articles par panier).
		 * On peut le voir avec la verification suivante.
		 * On ne nous demande pas de corriger le bug pour le cadre de ce TP, je le laisse donc intact et
		 * j'écris ce commentaire pour l'explication.
		 */

		// Execution Puis Verification n°2
		QuantiteArticleTropGrandeException e2 = assertThrows(QuantiteArticleTropGrandeException.class, () -> {
			pan.ajouterArticle(art2, 10);  // Ici on ajoute directement 10 quantités d'un article à un panier vide.
		});
	}

	/**
	 * Test unitaire couvrant l'exigence EXG_PAN_06
	 * La valeur marchande HT (hors remise) d’un panier ne peut excéder 1000€.
	 * Ici, on va faire deux vérifications :
	 *  — On va d'abord vérifier avec un panier qui contient initialement pile 1000€ de valeur marchande HT.
	 *  — Puis on va vérifier avec un panier qui est initialement vide, et ajouter un article de plus de 1000€ HT.
	 * @throws MontantTropEleveException : La valeur marchande d'un panier est limitée à 1000€ HT.
	 */
	@Test
	public void testValeurMarchandeHTTropElevee() throws TropDeReferencesException, QuantiteArticleTropGrandeException, MontantTropEleveException {
		// Preparation
		pan.ajouterArticle(art1, 5);
		pan.ajouterArticle(art2, 5);
		Article artPerso1 = new Article(0.01, "REF6969", "LIB6969", 0.00);
		Article artPerso2 = new Article(1000.01, "REF666", "LIB666", 0.00);
		Panier pan2 = new Panier( );

		// Execution Puis Verification
		MontantTropEleveException e = assertThrows(MontantTropEleveException.class, () -> {
			pan.ajouterArticle(artPerso1, 1);  // Ajout d'un article a un panier déjà rempli.
		});

		// Execution Puis Verification
		MontantTropEleveException e2 = assertThrows(MontantTropEleveException.class, () -> {
			pan2.ajouterArticle(artPerso2, 1);  // Ajout d'un article à un panier vide
		});

	}

	/**
	 * Test unitaire couvrant l'exigence EXG_REMISE_01
	 * Le système doit permettre de gérer une remise R exprimée en pourcentage du montant total TTC des
	 * articles contenus dans un panier, et un seuil S de déclenchement de la remise. Cette remise devra
	 * pouvoir être paramétrée par le système. La remise de R% est déclenchée lorsque le montant total TTC
	 * d’un panier est supérieur ou égale au seuil S.
	 */
	@Test
	public void testVerificationRemise() throws TropDeReferencesException, QuantiteArticleTropGrandeException, MontantTropEleveException {
		// Preparation
		// On prépare deux paniers pour tester les cas extremes (cas passant et erreur)
		Panier pan1 = new Panier( );
		Panier pan2 = new Panier( );
		// On active la remise au deux paniers
		pan1.ecrireRemiseActive(true);
		pan2.ecrireRemiseActive(true);
		// On contrôle le seuil d'activation avec une variable
		double S1 = 550.00;
		double S2 = 549.99;
		pan1.ecrireMontantSeuil(S1);
		pan2.ecrireMontantSeuil(S2);
		// On contrôle le montant de la remise avec une seconde variable, commune pour les deux paniers
		double R = 10.00;
		pan1.ecrirePourcentageRemise(R);
		pan2.ecrirePourcentageRemise(R);
		// On ajoute 5 articles d'une valeur de 500€ HT à 10% de TVA soit 550€ TTC à chaque panier
		pan1.ajouterArticle(art2, 5);
		pan2.ajouterArticle(art2, 5);

		// Execution
		double montantRemise1 = pan1.montantRemise();
		double montantRemise2 = pan2.montantRemise();

		// Verification
		assertEquals(55.00, montantRemise1, 0.001);  // Ici la verification devrait passer.
		assertEquals(55.00, montantRemise2, 0.001);

		/**
		 * Comme on peut le voir, il y a un bug dans la methode de calcul montantRemise() car le seuil d'activation
		 * n'est pas inclus, hors notre document d'exigence stipule bien que la remise est appliquée
		 * "lorsque le montant total TTC d’un panier est supérieur ou EGALE au seuil S."
		 * C'est pourquoi j'ai fait deux paniers pour le montrer explicitement. Les deux paniers sont similaires en
		 * tout point sauf pour le seuil d'activation qui diffère d'un centime.
		 * Après inspection du code dans le fichier Panier.java, on peut voir que c'est la ligne 307 qui comporte
		 * une erreur : il est écrit `if (ttc > montantSeuil)` au lieu de `if (ttc >= montantSeuil)`
		 */
	}

	/**
	 * Test unitaire couvrant l'exigence EXG_TICKET_01
	 * Le système doit permettre, l’impression à l’écran d’un ticket de récapitulation d’un panier. On devra
	 * prendre en compte la remise éventuelle.
	 * Ici, on va vérifier la bonne impression d'un ticket avec trois articles :
	 *  — manga (1 qtt)
	 *  — coca (2 qtt)
	 *  — riz (1 qtt).
	 */
	@Test
	public void testImpressionTicket() throws TropDeReferencesException, QuantiteArticleTropGrandeException, MontantTropEleveException {
		// Preparation
		Article manga = new Article(5.82, "REF7456", "One Piece", 19.99);
		Article coca = new Article(1.27, "REF1257", "Coca Cola", 10.0);
		Article riz = new Article(2.03, "REF8563", "Riz Basmati", 10.0);
		pan.ajouterArticle(manga, 1);
		pan.ajouterArticle(coca, 2);
		pan.ajouterArticle(riz, 1);
		Date maDate = new Date();
		String stringDate = OutilsChaine.formatDateFrance.format(maDate);
		String stringHeure = OutilsChaine.formatHeure.format(maDate);

		String expectedTicket = "------------------------------------\n" +
								"| LE BEAU PANIER                   |\n" +
								"| " + stringDate + " - " + stringHeure + "               |\n" +
								"------------------------------------\n" +
								"| LIBELLE      PU_HT   QTE     TTC |\n" +
								"|                                  |\n" +
								"| One Piece     5.82     1    6.98 |\n" +
								"| Coca Cola     1.27     2    2.79 |\n" +
								"| Riz Basmat    2.03     1    2.23 |\n" +  // On peut voir ici qu'il y a un léger décalage d'un caractère.
								"|                                  |\n" +
								"------------------------------------\n" +
								"| TOTAL                      12.01 |\n" +
								"------------------------------------\n" +
								"| REMISE 0.00%                0.00 |\n" +
								"------------------------------------\n" +
								"| TVA                         1.62 |\n" +
								"------------------------------------\n" +
								"| NET A PAYER                12.01 |\n" +
								"------------------------------------\n" +
								"| Les prix s'entendent en Euro     |\n" +
								"------------------------------------";

		assertEquals(expectedTicket, pan.lireTicket());

		/**
		 * On peut voir qu'il y a un décalage d'un caractère sur la ligne 9 de mon ticket de caisse.
		 * La longueur du libellé est censé être de taille 10 d'après ce qu'on peut voir dans le code
		 * source (fichier Panier.java ligne 330), mais lorsque notre libellé a strictement plus de 10
		 * caractères (ce qui est le cas ici avec "Riz basmati" qui en a 11, seule les 9 premiers caractères
		 * semblent s'afficher au lieu de 10, ce qui provoque ce décalage d'un caractère.
		 */
	}
}
