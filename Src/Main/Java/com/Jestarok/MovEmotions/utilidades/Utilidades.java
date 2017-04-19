package com.Jestarok.MovEmotions.utilidades;

import com.google.gson.Gson;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;

/**
 * Created by vacax on 03/07/16.
 */
public class Utilidades {

    /**
     * Agrega la serializacion por defecnto para UniRest.
     */
    public static void agregarSerializacionUniRest(){
        Unirest.setObjectMapper(new ObjectMapper() {
            private Gson gson = new Gson();

            public <T> T readValue(String s, Class<T> aClass) {
                //System.out.println("Clase: "+aClass.getName());
                //System.out.println("S: "+s);
                try{
                    return gson.fromJson(s, aClass);
                }catch(Exception e){
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object o) {
                try{
                    return gson.toJson(o);
                }catch(Exception e){
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
