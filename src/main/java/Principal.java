import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Principal {

    public static void main(String[] args){

        String nombrePokemon = JOptionPane.showInputDialog("Ingrese el nombre de un pokemon: ");

        try {
            URL url = new URL("https://pokeapi.co/api/v2/pokemon/" + nombrePokemon);

            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();

            conexion.setRequestMethod("GET");

            conexion.connect();

            int codigoRespuestaHttp = conexion.getResponseCode();

            if(codigoRespuestaHttp != 200){
                throw new RuntimeException("Error: " + codigoRespuestaHttp);
            }else {
                StringBuilder pokemonInfo = new StringBuilder();

                Scanner scannerUrl = new Scanner(url.openStream());

                while (scannerUrl.hasNext()){
                    pokemonInfo.append(scannerUrl.nextLine());
                }
                scannerUrl.close();

                JSONObject jsonObject = new JSONObject(pokemonInfo.toString());

                //JSONArray jsonAbilities = jsonObject.getJSONArray("abilities");

                JSONArray jsonStats = jsonObject.getJSONArray("stats");

                String resultado = "";
                resultado += "BASE-STATS-" + nombrePokemon.toUpperCase() + ":\n";
                for(int i = 0; i < jsonStats.length(); i++) {
                    resultado += jsonStats.getJSONObject(i).getJSONObject("stat").get("name") + ": " +
                                     jsonStats.getJSONObject(i).get("base_stat") + "\n";
                }
                JOptionPane.showMessageDialog(null, resultado);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}

