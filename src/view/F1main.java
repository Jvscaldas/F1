package view;

import java.util.concurrent.Semaphore;

import controller.F1;

public class F1main {


	public static void main(String[] args) {

		int idCarro = 1;
		int permissoes = 5;
		Semaphore semaforoequipes = new Semaphore(permissoes);

		for (int equipes = 1; equipes <= 7; equipes++) {
			Thread tCorrida = new F1(idCarro, equipes, semaforoequipes);
			tCorrida.start();
		}

	}

}
