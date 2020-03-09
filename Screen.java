package br.victor.screenrecognizer;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;

public class ScreenRecognizer {

	static int[] x, y;
	static int size = 0;

	static Robot robo = new Robot();

	public ScreenRecognizer() {
		// Instruções da classe

		this.GetPixels();
		while (true) {
			ShowColors();
		}
	}

	public static void main(String[] args) throws InterruptedException, AWTException {
		Robot robot = new Robot();
		GetPixels();
		// Delay(1000);
		while (true) {
			ShowColors();
			// Delay(3000);
		}
	}

	public static Point getPoint() {
		return MouseInfo.getPointerInfo().getLocation();
	}

	public static void GetPixels() throws InterruptedException, AWTException {
		y = new int[512];
		x = new int[512];
		Print("Posicione o mouse sobre o meio da nota VERDE");
		Delay(1300);
		// Varredura();
		DrawSquare();
	}

	public static void Varredura() throws AWTException, InterruptedException {
		for (int v = 0; v < 30; v++) {
			Y_MOVE(1);
			y[v] = getPixelCord('y');
			Print("Y [" + v + "] =" + y[v]);
		}
		Delay(1000);
		Y_MOVE(-15);
		for (int v = 0; v < 35; v++) {
			X_MOVE(1);
			x[v] = getPixelCord('x');
			Print("X [" + v + "] =" + x[v]);
		}
		Delay(1000);
		X_MOVE(-35);
		for (int v = 0; v < 35; v++) {
			X_MOVE(-1);
			x[36 + v] = getPixelCord('x');
			Print("X [" + (36 + v) + "] =" + x[36 + v]);
		}
	}

	public static void DrawSquare() throws AWTException, InterruptedException {
		Robot robot = new Robot();
		int x1 = getPixelCord('x');
		int x2 = x1 - 80;
		int y1 = getPixelCord('y');
		int y2 = y1 - 45;
		for (int H = x2; H < x1; H += 10) {
			for (int V = y2; V < y1; V += 10) {
				int cor = robot.getPixelColor(H, V).getGreen();
				if (!(cor < 20 || cor == 74)) {
					robot.mouseMove(H, V);
					x[size] = H;
					y[size] = V;
					size++;
				}

			}
		}
	}

	static Color Cor;
	static int MRGB[];
	static int L_TIME, R, G, B;
	static String COLOR_NAME;

	public static void ShowColors() throws AWTException {
		Robot robot = new Robot();
		MRGB = new int[] { 0, 0, 0 };
		L_TIME = 0;
		for (int i = 0; i < x.length; i++) {
			if (x[i] == 0) {
				break;
			}
			// robot.mouseMove(x[i], y[i]);
			Cor = robot.getPixelColor(x[i], y[i]);
			// Print("COR NA CORD [ X:" + x[i] + " ; Y:" + y[i] + " ] = " +
			// GetColor(x[i] , y[i]) );
			MRGB[0] += Cor.getRed();
			MRGB[1] += Cor.getGreen();
			MRGB[2] += Cor.getBlue();
			L_TIME++;
		}
		R = MRGB[0] / L_TIME;
		G = MRGB[1] / L_TIME;
		B = MRGB[2] / L_TIME;
		COLOR_NAME = GetColorName(R, G, B);
		Print("[" + R + " ; " + G + " ; " + B + " = " + COLOR_NAME);
		/*
		 * if(COLOR_NAME != "") { Print("( " + L_TIME + ") + { " + MRGB[0] +
		 * " ; " + MRGB[1] + " ; " + MRGB[2] + " } [" + R + " ; " + G + " ; " +
		 * B + " = " + COLOR_NAME); }
		 */
		if (COLOR_NAME != "PRETO") {
			robot.keyPress(KeyEvent.VK_A);
			robot.keyRelease(KeyEvent.VK_A);
		}
	}

	public static String GetColorName(int r, int g, int b) {
		if (r > 200 && g < 50 && b < 50) {
			return "VERMELHO";
		}
		if (r < 50 && g > 140 && b < 50) {
			return "VERDE";
		}
		if (r > 150 && g > 140 && b < 50) {
			return "AMARELO";
		}
		if (r < 50 && g > 100 && b > 150) {
			return "AZUL";
		}
		if (r > 210 && g > 210 && b > 210) {
			return "BRANCO";
		}
		if (r < 30 && g < 30 && b < 30) {
			return "PRETO";
		}
		return "";
	}

	public static Color GetColor(int x, int y) throws AWTException {
		return robo.getPixelColor(x, y);
	}

	public static void Y_MOVE(int pixels) throws AWTException {
		robo.mouseMove(X_GET(), (Y_GET() + pixels));
	}

	public static void X_MOVE(int pixels) throws AWTException {
		robo.mouseMove((X_GET() + pixels), Y_GET());
	}

	public static int X_GET() {
		return getPoint().x;
	}

	public static int Y_GET() {
		return getPoint().y;
	}

	public static int getPixelCord(char p) {
		return (p == 'x') ? getPoint().x : getPoint().y;
	}

}
