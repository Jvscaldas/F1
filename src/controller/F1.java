package controller;

import java.util.concurrent.Semaphore;

public class F1 extends Thread {

	private int idCarro;
	private int equipes;

	private static int classificados = 0;
	private static double[][] tabela = new double[14][3];

	Semaphore semaforoequipes;

	public F1(int idCarro, int equipes, Semaphore semaforoequipes) {
		this.idCarro = idCarro;
		this.equipes = equipes;
		this.semaforoequipes = semaforoequipes;
	}

	@Override
	public void run() {
		do {

			try {
				semaforoequipes.acquire();
				carroPista();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				semaforoequipes.release();
				sairPista();
				idCarro++;
				if (classificados == 14) {
					exibeTabela();
				}

			}
		} while (idCarro == 2);
	}

	private void carroPista() {

		double time;
		double bestTime = 0;

		System.out.println("O carro #" + idCarro + " da equipe " + equipes + " começou a classificação");

		for (int i = 0; i <= 3; i++) {
			time = volta();
			if (bestTime == 0) {
				bestTime = time;
			} else if (time < bestTime) {
				bestTime = time;
			}

			System.out.println("O carro #" + idCarro + " da equipe " + equipes + " terminou sua " + i + " volta em "
					+ time + " minutos.");

			System.out.println("O carro #" + idCarro + " da equipe " + equipes + " fez sua melhor volta em " + bestTime
					+ " minutos.");
			gravaTabela(bestTime);

		}

	}

	private double volta() {

		int percurso = 2000;
		int c = 0;
		int tempo = 100;

		double deslocamento = 0;
		double vm = 0;
		double v = (int) ((Math.random() * 100) + 1);

		while (deslocamento < percurso) {

			deslocamento += v;

			try {
				sleep(tempo);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			c++;
			vm += v;
		}

		vm = (vm / c);
		double time = ((percurso / vm) / 60);
		return time;
	}

	private void gravaTabela(double bestTime) {

		int l = 0;
		int c = 0;

		if (idCarro == 1) {
			l = (equipes - 1);
		} else if (idCarro == 2) {
			l = (equipes + 6);
		}

		tabela[l][c] = idCarro;
		tabela[l][c + 1] = equipes;
		tabela[l][c + 2] = bestTime;

	}

	public void sairPista() {
		System.out.println("O carro #" + idCarro + " da equipe " + equipes + " saiu da pista");
		classificados++;
	}

	public static void exibeTabela() {
		ordenaTabela(tabela);
		for (int l = 0; l <= 13; l++) {
			System.err.println("O carro #" + tabela[l][0] + " da equipe " + tabela[l][1] + " ficou em " + (l + 1)
					+ " lugar com tempo: " + tabela[l][2]);
		}

	}

	private static double[][] ordenaTabela(double[][] tabela) {
		double aux[] = new double[3];

		for (int l = 0; l <= 12; l++) {
			for (int laux = (l + 1); laux <= 13; laux++) {
				if (tabela[laux][2] < tabela[l][2]) {
					for (int c = 0; c <= 2; c++) {
						aux[c] = tabela[laux][c];
						tabela[laux][c] = tabela[l][c];
						tabela[l][c] = aux[c];
					}
				}
			}
		}

		return tabela;

	}

}
