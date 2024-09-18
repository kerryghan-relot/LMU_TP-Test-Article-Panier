package com.soprasteria.panier.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.soprasteria.panier.model.Article;

/**
 * Classe de tests unitaires de la classe Article
 */
public class TestArticle {
	private static Article art;

	@BeforeAll
	public static void initTests() {
		art = new Article( 100.00 , "ref1" , "lib1" , 19.6 );
	}

	/**
	 * Test unitaire couvrant les exigences EXG_ART_01 , EXG_ART_02
	 */
	@Test
	public void testPrixTTCArticle() {
		// EXG_ART_01 is implicitly tested with the initialisation method with the "@BeforeAll"

		// EXG_ART_02
		Assertions.assertEquals("lib1", art.lireLibelle());
		Assertions.assertEquals(100.00, art.lirePrixHT());
		Assertions.assertEquals(19.6, art.lireTva());

		// Non mandatory
		Assertions.assertEquals("ref1", art.lireReference());
		Assertions.assertEquals(119.6, art.lirePrixTTC());

	}
}
