import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String path = "C:/Users/DELL/Desktop/DES2/startup-profit.csv";
        String line;

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));

            br.readLine();

            double[] rdSpend = new double[50];
            double[] marketing = new double[50];
            double[] profit = new double[50];
            int i = 0;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 5) {
                    try {
                        rdSpend[i] = Double.parseDouble(values[0]);
                        marketing[i] = Double.parseDouble(values[2]);
                        profit[i] = Double.parseDouble(values[4]);
                        i++;
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
            br.close();


            double[] rdSpendTrimmed = new double[i];
            double[] marketingSpendTrimmed = new double[i];
            double[] profitTrimmed = new double[i];

            System.arraycopy(rdSpend, 0, rdSpendTrimmed, 0, i);
            System.arraycopy(marketing, 0, marketingSpendTrimmed, 0, i);
            System.arraycopy(profit, 0, profitTrimmed, 0, i);

            double correlacionRdProfit = correlacionPearson(rdSpendTrimmed, profitTrimmed, i);
            double correlacionMarketingProfit = correlacionPearson(marketingSpendTrimmed, profitTrimmed, i);

            System.out.println("Profit y R&D Spend: " + correlacionRdProfit);
            System.out.println("Profit y Marketing Spend: " + correlacionMarketingProfit);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double correlacionPearson(double[] x, double[] y, int n) {
        double sumaX = 0.0, sumaY = 0.0, sumaXY = 0.0;
        double sumaX2 = 0.0, sumaY2 = 0.0;

        for (int i = 0; i < n; i++) {
            sumaX += x[i];
            sumaY += y[i];
            sumaXY += x[i] * y[i];
            sumaX2 += x[i] * x[i];
            sumaY2 += y[i] * y[i];
        }

        double numerador = n * sumaXY - sumaX * sumaY;
        double denominador = Math.sqrt((n * sumaX2 - sumaX * sumaX) * (n * sumaY2 - sumaY * sumaY));

        if (denominador == 0) {
            return 0;
        }

        return numerador / denominador;
    }
}
